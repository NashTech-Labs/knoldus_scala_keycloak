package com.knoldus.services

import akka.event.LoggingAdapter
import com.knoldus.models.entities.Role
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.RoleRepresentation

import scala.concurrent.Future
import scala.jdk.CollectionConverters.ListHasAsScala

class RoleServices(keycloak: Keycloak)(implicit val logger: LoggingAdapter) {

  def createClientRoleUtil(realm: String, clientId: String, role: Role): Future[String] = {
    try {
      val clientRep = keycloak.realm(realm).clients().findByClientId(clientId).get(0)
      keycloak.realm(realm).clients().get(clientRep.getId).roles().create(Role.roleToRoleRepresentation(role))
      Future.successful(s"Added role ${role.name}, id ${role.id} to client $clientId")
    } catch {
      case e: IndexOutOfBoundsException =>
        logger.error(s"Failed to add role ${role.name}, id ${role.id} to client $clientId: Client does not exist")
        Future.failed(e)
      case e: Throwable =>
        logger.error(s"Failed to add role ${role.name}, id ${role.id} to client $clientId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def getRealmRolesUtil(realm: String): Future[List[RoleRepresentation]] = {
    try {
      Future.successful(keycloak.realm(realm).roles().list().asScala.toList)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch $realm roles, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def getClientRolesUtil(realm: String, clientId: String): Future[List[RoleRepresentation]]= {
    try {
      val clientRep = keycloak.realm(realm).clients().findByClientId(clientId).get(0)
      Future.successful(keycloak.realm(realm).clients().get(clientRep.getId).roles().list().asScala.toList)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch $clientId roles, in realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def updateClientRoleUtil(realm: String, clientId: String, roleName: String, role: Role): Future[String]  = {
    try {
      val clientRep = keycloak.realm(realm).clients().findByClientId(clientId).get(0)
      keycloak.realm(realm).clients().get(clientRep.getId).roles().get(roleName).update(Role.roleToRoleRepresentation(role))
      Future.successful(s"Updated client role $roleName, for client $clientId")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to update role $roleName, in client $clientId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def deleteClientRoleUtil(realm: String, clientId: String, roleName: String): Future[String]  = {
    try {
      val clientRep = keycloak.realm(realm).clients().findByClientId(clientId).get(0)
      keycloak.realm(realm).clients().get(clientRep.getId).roles().get(roleName).remove()
      Future.successful(s"Deleted client role $roleName, for client $clientId")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to delete role $roleName, in client $clientId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }
}
