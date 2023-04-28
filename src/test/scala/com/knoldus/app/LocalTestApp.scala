package com.knoldus.app

import akka.Done
import akka.actor.CoordinatedShutdown.Reason
import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.pathPrefix
//import com.knoldus.initialisers.{RoutesInstantiator, ServiceInstantiator}
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.{Keycloak, KeycloakBuilder}
import spray.json.{JsBoolean, JsNumber, JsObject, JsString}

import scala.concurrent.Future
import scala.util.Failure

object LocalTestApp extends App {
  implicit val actorSystem: ActorSystem = ActorSystem("test-actor-system")
  implicit val executionContext = actorSystem.dispatcher
  implicit val logger: LoggingAdapter = Logging(actorSystem, "logger-adapter")


  val serverUrl: String = "http://localhost:8080/"
  val realm: String = "master"
  val clientId: String = "admin-cli"
  val resourceServer = "admin-cli"
  val username = "admin"
  val password = "admin"
  val clientSecret = "jUik2Igayg2O9waizYg0rRdbQm82wJZx"

  val keycloakClientConfig = JsObject(
    "realm" -> JsString(realm),
    "auth-server-url" -> JsString(serverUrl),
    "ssl-required" -> JsString("external"),
    "resource" -> JsString(resourceServer),
    "public-client" -> JsBoolean(true),
    "confidential-port" -> JsNumber(0)
  )

  val keycloak: Keycloak = KeycloakBuilder.builder()
    .serverUrl(serverUrl)
    .realm(realm)
    .grantType(OAuth2Constants.PASSWORD)
    //.clientSecret(clientSecret)
    .clientId(clientId)
    .username(username)
    .password(password)
    .build()

  def getAdminToken: String = keycloak.tokenManager().getAccessTokenString
  println(getAdminToken)
 // val servicesInstantiaor: ServiceInstantiator = new ServiceInstantiator(keycloak, keycloak, serverUrl)
  //val routesInstantiator: RoutesInstantiator = new RoutesInstantiator(servicesInstantiaor, keycloakClientConfig, keycloak, keycloakClientConfig, keycloak)
  val akkaShutdown = CoordinatedShutdown(actorSystem)

  //val routes = routesInstantiator.routes
  val interface = "0.0.0.0"
  val port = 9000
  val prefix = "rest"
  //val prefixedRoutes = pathPrefix(prefix).apply(routes)
  //val serverBindingHttp = Http().newServerAt(interface, port).bind(prefixedRoutes)

 /* val serverBinding: Future[Http.ServerBinding] = serverBindingHttp.andThen {
    case Failure(ex) => shutdown(ex)
  }*/

  private def shutdown(e: Throwable): Future[Done] = {
    logger.error(e, "Error starting application:")
    akkaShutdown.run(new Reason {
      override def toString: String = "Error starting application: " ++ e.getMessage
    })
  }
}