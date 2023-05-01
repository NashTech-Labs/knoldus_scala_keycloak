package com.knoldus.services.master.admin

import akka.event.LoggingAdapter
import com.knoldus.models.entities.{Realm, Role}
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.RealmRepresentation

import scala.concurrent.Future

class MasterServices(keycloakMasterAdmin: Keycloak)(implicit val logger: LoggingAdapter) {

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
