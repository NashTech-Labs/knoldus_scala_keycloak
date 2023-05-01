package com.knoldus.services

import akka.event.LoggingAdapter
import akka.http.scaladsl.model.{FormData, HttpMethods, HttpRequest, Uri}
import com.knoldus.models.{LoginAccessToken, LoginCredentials}
import org.keycloak.admin.client.Keycloak

import scala.concurrent.Future

class LoginAndAuthenticationServices(keycloak: Keycloak)(implicit val logger: LoggingAdapter) {

  /*def loginUserUtils(realm: String, credentials: LoginCredentials): Future[LoginAccessToken] =
    try {
      val request = HttpRequest(
        uri = Uri(baseUrl + s"/realms/$realm/protocol/openid-connect/token"),
        entity = FormData(
          "client_id" -> credentials.client_id,
          "username" -> credentials.username,
          "password" -> credentials.password,
          "grant_type" -> credentials.grant_type).toEntity,
        method = HttpMethods.POST
      )
      sendRequest[LoginAccessToken](request) match {
        case Right(token) => Future.successful(token)
        case Left(e) => Future.failed(new Throwable(e.errorMessage))
      }
    } catch {
      case otherError: Throwable =>
        logger.error("Failed login user: " + otherError.getLocalizedMessage)
        Future.failed(otherError)
    }*/
}
