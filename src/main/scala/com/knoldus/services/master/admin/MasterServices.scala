package com.knoldus.services.master.admin

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.server.Directives.onComplete
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.knoldus.models.entities.{Realm, Role}
import com.knoldus.response.EmptyResponse
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.RealmRepresentation

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
 *
 * @param keycloakMasterAdmin Should be configured to your keycloak admin within realm master
 * @param baseUrl
 * @param system
 * @param mat
 * @param logger
 */
class MasterServices(keycloakMasterAdmin: Keycloak, baseUrl: String)(implicit override val system: ActorSystem, mat: Materializer, logger: LoggingAdapter)
  extends KeycloakService(baseUrl) {

  def addRealm(realmName: String): Route = {
    onComplete(createRealmUtil(realmName)) {
      case Success(resp) => logAndSendResponse(resp.getId, EmptyResponse(resp.getId), "Add Realm")
      case Failure(e) => logAndSendError(s"log message ${e.getMessage}", e.getLocalizedMessage, "Add Realm")
    }
  }

  def updateRealm(realmName: String, realm: Realm): Route = {
    onComplete(updateRealmByName(realmName, realm)) {
      case Success(resp) => logAndSendResponse(resp, EmptyResponse(resp), "Update Realm")
      case Failure(e) => logAndSendError(s"log message ${e.getMessage}", e.getLocalizedMessage, "Update Realm")
    }
  }

  def deleteRealm(realmName: String): Route = {
    onComplete(deleteRealmByName(realmName)) {
      case Success(resp) => logAndSendResponse(resp, EmptyResponse(resp), "Delete Realm")
      case Failure(e) => logAndSendError(s"log message ${e.getMessage}", e.getLocalizedMessage, "Delete Realm")
    }
  }

  def createRealmRole(realm: String, role: Role): Route = {
    onComplete(createRealmRoleUtil(realm, role)) {
      case Success(resp) => logAndSendResponse(s"Created realm role ${role.name}, id ${role.id}", EmptyResponse(resp), "Add Role To Realm")
      case Failure(e) => logAndSendError(s"log message ${e.getMessage}", e.getLocalizedMessage, "Add Role To Realm")
    }
  }

  def updateRealmRole(realm: String, roleName: String, role: Role): Route = {
    onComplete(updateRealmRoleUtil(realm, roleName, role)) {
      case Success(resp) => logAndSendResponse(s"Updated realm role ${role.name}", EmptyResponse(resp), "Update Realm Role")
      case Failure(e) => logAndSendError(s"log message ${e.getMessage}", e.getLocalizedMessage, "Update Realm Role")
    }
  }

  def deleteRealmRole(realm: String, roleName: String): Route = {
    onComplete(deleteRealmRoleUtil(realm, roleName)) {
      case Success(resp) => logAndSendResponse(s"Deleted realm role $roleName", EmptyResponse(resp), "Deleted Realm Role")
      case Failure(e) => logAndSendError(s"log message ${e.getMessage}", e.getLocalizedMessage, "Deleted Realm Role")
    }
  }

  // -------------------------------------------------
  // ---------------- Helper Methods -----------------
  // -------------------------------------------------

  def createRealmUtil(realmName: String): Future[RealmRepresentation] = {
    try {
      val realmRepresentation = new RealmRepresentation()
      realmRepresentation.setRealm(realmName)
      realmRepresentation.setId(realmName)
      keycloakMasterAdmin.realms().create(realmRepresentation)
      Future.successful(realmRepresentation)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to create realm ${realmName} with exception: ${e.getMessage}")
        Future.failed(e)
    }
  }

  def updateRealmByName(realmName: String, realm: Realm): Future[String] = {
    try {
      val realmRepresentation = Realm.updateRealmRepresentation(realm)
      keycloakMasterAdmin.realm(realmName).update(realmRepresentation)
      Future.successful(realmName)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to update realm ${realm.realm} with exception: ${e.getMessage}")
        Future.failed(e)
    }
  }

  def deleteRealmByName(realm: String): Future[String] = {
    try {
      keycloakMasterAdmin.realm(realm).remove()
      Future.successful(realm)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to delete realm $realm with exception: ${e.getMessage}")
        Future.failed(e)
    }
  }

  def createRealmRoleUtil(realm: String, role: Role): Future[String] = {
    try {
      keycloakMasterAdmin.realm(realm).roles().create(Role.roleToRoleRepresentation(role))
      Future.successful(s"Added role ${role.name}, id ${role.id} to realm $realm")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to add role ${role.name}, id ${role.id} to realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def updateRealmRoleUtil(realm: String, roleName: String, role: Role): Future[String] = {
    try {
      keycloakMasterAdmin.realm(realm).roles().get(roleName).update(Role.roleToRoleRepresentation(role))
      Future.successful(s"Updated realm role $roleName")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to update role $roleName, in realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def deleteRealmRoleUtil(realm: String, roleName: String): Future[String] = {
    try {
      keycloakMasterAdmin.realm(realm).roles().get(roleName).remove()
      Future.successful(s"Deleted realm role $roleName")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to delete role $roleName, in realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

}
