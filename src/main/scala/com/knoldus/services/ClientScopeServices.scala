package com.knoldus.services

import akka.event.LoggingAdapter
import com.knoldus.models.entities.ClientScope
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.ClientScopeRepresentation

import scala.concurrent.Future
import scala.jdk.CollectionConverters.ListHasAsScala

class ClientScopeServices(keycloak: Keycloak)(implicit val logger: LoggingAdapter) {

  def createClientScopeUtil(realm: String, clientScope: ClientScope): Future[String] = {
    try {
      keycloak.realm(realm).clientScopes().create(ClientScope.toRepresentation(clientScope))
      Future.successful(s"Created client scope ${clientScope.name}, id ${clientScope.id}")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to create client scope ${clientScope.name}, id ${clientScope.id}, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def getClientScopesUtil(realm: String): Future[List[ClientScopeRepresentation]] = {
    try {
      Future.successful(keycloak.realm(realm).clientScopes().findAll().asScala.toList)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch all client scopes, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def getSingleClientScopeUtil(realm: String, scopeName: String): Future[ClientScopeRepresentation] = {
    try {
      val scope = keycloak.realm(realm).clientScopes().findAll().asScala.toList.filter(s => s.getName == scopeName)
      Future.successful(if (scope.isEmpty) new ClientScopeRepresentation()
                        else scope.head)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch client scope with name $scopeName, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def updateClientScopeUtil(realm: String, clientScopeId: String, clientScope: ClientScope): Future[String] = {
    try {
      keycloak.realm(realm).clientScopes().get(clientScopeId).update(ClientScope.toRepresentation(clientScope))
      Future.successful(s"Updated client scope ${clientScope.name}")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to update client scope ${clientScope.name}, id ${clientScope.id}, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def deleteClientScopeUtil(realm: String, clientScopeId: String): Future[String] = {
    try {
      keycloak.realm(realm).clientScopes().get(clientScopeId).remove()
      Future.successful(s"Deleted client scope with id $clientScopeId")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to delete client scope with id $clientScopeId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }
}
