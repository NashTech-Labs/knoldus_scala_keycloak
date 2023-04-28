package com.knoldus.services

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.Materializer
import com.knoldus.models.Credentials
import com.knoldus.models.entities.{Role, User}
import com.knoldus.response.ResponseUtil.checkResponseStatusGetMessage
import com.knoldus.response.ResponseUtil
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import spray.json.{JsArray, JsBoolean, JsNumber, JsObject, JsString}

import java.time.Instant
import javax.ws.rs.core.Response
import scala.concurrent.Future
import scala.jdk.CollectionConverters._

class UserServices(keycloak: Keycloak)(implicit val system: ActorSystem, mat: Materializer, logger: LoggingAdapter) {

  def createUser(user: User, realm: String): Future[JsObject] = {
    try {
      val cred = new CredentialRepresentation
      cred.setType(OAuth2Constants.PASSWORD)
      cred.setTemporary(false)
      cred.setValue(Credentials.generatePassword)

      val credentials = List(cred).asJava
      val userRepresentation = User.convertToUserRep(user)
      userRepresentation.setCredentials(credentials)

      val resp: Response = keycloak.realm(realm).users().create(userRepresentation)
      val respMsgs = ResponseUtil.defaultMessages + (401 -> "Cannot create user. Unauthorised access")
      checkResponseStatusGetMessage(resp, respMsgs)
      Future.successful(JsObject("status" -> JsString(resp.getStatusInfo.toString), "credentials" -> JsArray(
        credentials.asScala.map(r =>
          JsObject(
            "credential_type" -> JsString(r.getType),
            "password" -> JsString(r.getValue),
            "created" -> JsNumber(Instant.now.toEpochMilli),
            "is_temporary" -> JsBoolean(r.isTemporary)
          )).toVector
      )))
    } catch {
      case e: Throwable =>
        e.printStackTrace()
        logger.error(s"Failed to create user ${user.username}, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def getAllUsers(realmName: String): Future[List[User]] = {
    val realm = keycloak.realm(realmName)
    val response = realm.users().list().asScala.toList.map(User.convertToUser)
    Future.successful(response)
  }

  def addGroupToUser(realmName: String, userId: String, groupId: String): Future[String] = {
    try {
      val realm = keycloak.realm(realmName)
      realm.users().get(userId).joinGroup(groupId)
      if (realm.users().get(userId).groups().asScala.toList.map(g => g.getId).contains(groupId)) {
        Future.successful("Success")
      } else {
        Future.failed(new Exception(s"Failed to add group to user: Group id ${groupId} not found"))
      }
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to add group to user $userId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def removeGroupFromUser(realmName: String, userId: String, groupId: String): Future[String] = {
    val realm = keycloak.realm(realmName)
    realm.users().get(userId).leaveGroup(groupId)
    Future.successful(userId)
  }

  def getOneUser(realmName: String, userId: String): Future[User] = {
    val realm = keycloak.realm(realmName)
    val response = User.convertToUser(realm.users().get(userId).toRepresentation)
    Future.successful(response)
  }

  def addRoleToUser(realm: String, userId: String, role: Role, clientIdO: Option[String]): Future[String] = {
    try {
      val roles = keycloak.realm(realm).users().get(userId).roles()

      clientIdO.map(clientId => roles.clientLevel(clientId).add(List(Role.roleToRoleRepresentation(role)).asJava))
        .getOrElse(roles.realmLevel().add(List(Role.roleToRoleRepresentation(role)).asJava))

      Future.successful(s"Added role ${role.name}, id ${role.id} to user $userId")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to add role ${role.name}, id ${role.id} to user $userId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def removeRoleFromUser(realm: String, userId: String, role: Role, clientIdO: Option[String]): Future[String] = {
    try {
      val roles = keycloak.realm(realm).users().get(userId).roles()

      clientIdO.map(clientId => roles.clientLevel(clientId).remove(List(Role.roleToRoleRepresentation(role)).asJava))
        .getOrElse(roles.realmLevel().remove(List(Role.roleToRoleRepresentation(role)).asJava))

      Future.successful(s"Removed role ${role.name}, id ${role.id} from user $userId")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to remove role ${role.name}, id ${role.id} from user $userId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def updateUser(realmName: String, userId: String, user: User): Future[String] = {
    val groupRepresentation = User.convertToUserRep(user)
    keycloak.realm(realmName).users().get(userId).update(groupRepresentation)
    Future.successful(userId)
  }

  def deleteUser(realmName: String, userId: String): Future[String] = {
    keycloak.realm(realmName).users().get(userId).remove()
    Future.successful(userId)
  }
}
