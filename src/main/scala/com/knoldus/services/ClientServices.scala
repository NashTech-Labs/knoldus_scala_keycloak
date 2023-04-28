package com.knoldus.services

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.knoldus.models.entities.Client
import com.knoldus.response.ResponseUtil
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.ClientRepresentation

import javax.ws.rs.core.Response
import scala.concurrent.Future
import scala.jdk.CollectionConverters.ListHasAsScala

class ClientServices(keycloak: Keycloak)(implicit val system: ActorSystem, mat: Materializer, logger: LoggingAdapter) {


  def addPolicy: Route = ???

  def getPolicy: Route = ???

  def updatePolicy: Route = ???

  def deletePolicy: Route = ???

  def createClient(client: Client, realm: String): Future[String] = {
    try {
      println("clientId: " + client.clientId)
      val resp: Response = keycloak.realm(realm).clients().create(Client.convertToClientRep(client))
      val messages = ResponseUtil.defaultMessages ++ Map(201 -> s"Created client, id: ${client.clientId}")
      val msg = ResponseUtil.checkResponseStatusGetMessage(resp, messages)
      Future.successful(msg)
    } catch {
      case e: Throwable =>
        logger.error("Failed to create client: " + e.getLocalizedMessage)
        Future.failed(e)
    }
  }

  def getSingleClient( realmName: String, clientId: String): Future[ClientRepresentation] = {
    try {
      Future.successful(keycloak.realm(realmName).clients().findByClientId(clientId).get(0))
    } catch {
      case e: Throwable =>
        logger.error("Failed to get clients: " + e.getLocalizedMessage)
        Future.failed(e)
    }
  }

  def getAllClientsUtils(realmName: String): Future[List[ClientRepresentation]] = {
    try {
      Future.successful(Option(keycloak.realm(realmName).clients().findAll()) match {
        case Some(value) => value.asScala.toList
        case None => List.empty[ClientRepresentation]
      })
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch clients from realm $realmName with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def updateClientUtil(realm: String, updatedClient: Client): Future[String] = {
    try {
      val clientRep = keycloak.realm(realm).clients().findByClientId(updatedClient.clientId).get(0)
      keycloak.realm(realm).clients().get(clientRep.getId).update(Client convertToClientRep updatedClient)
      Future.successful(s"Updated client ${updatedClient.name}, id: ${updatedClient.clientId}")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to update client  ${updatedClient.name}, id ${updatedClient.clientId}, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def deleteClientUtil(realm: String, clientId: String): Future[String] = {
    try {
      val clientRep = keycloak.realm(realm).clients().findByClientId(clientId).get(0)
      keycloak.realm(realm).clients().get(clientRep.getId).remove()
      Future.successful(s"Deleted client id: $clientId")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to delete client id $clientId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }
}
