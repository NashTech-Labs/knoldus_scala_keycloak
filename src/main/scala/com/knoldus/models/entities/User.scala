package com.knoldus.models.entities

import org.keycloak.representations.idm.UserRepresentation

import java.time.Instant
import java.util
import scala.jdk.CollectionConverters._

object User {

  val view = "view"
  val manage = "manage"
  val mapRoles = "mapRoles"
  val manageGroupMembership = "manageGroupMembership"
  val impersonate = "impersonate"

  def convertToUserRep(user: User): UserRepresentation = {
    val access: java.util.Map[String, java.lang.Boolean] = {
      user.access match {
        case Some(access) =>
          Map[String, java.lang.Boolean](
            view -> access.view,
            manage -> access.manage,
            mapRoles -> access.mapRoles,
            manageGroupMembership -> access.manageGroupMembership,
            impersonate -> access.impersonate
          ).asJava
        case None => new util.HashMap[String, java.lang.Boolean]()
      }
    }
    val enabled = user.enabled.getOrElse(true)
    val emailVerified = user.emailVerified.getOrElse(false)
    val totp = user.totp.getOrElse(true)
    val notBefore = user.notBefore.getOrElse(0)
    val timeStamp = user.createdTimestamp.getOrElse(Instant.now.toEpochMilli)

    val userRep = new UserRepresentation()
    userRep.setId(user.userId.getOrElse(""))
    userRep.setLastName(user.lastName)
    userRep.setFirstName(user.firstName)
    userRep.setEnabled(enabled)
    userRep.setEmailVerified(emailVerified)
    userRep.setTotp(totp)
    userRep.setEmail(user.email)
    userRep.setUsername(user.username)
    userRep.setRealmRoles(user.realmRoles.map(_.asJava).getOrElse(new util.ArrayList[String]()))
    userRep.setNotBefore(notBefore)
    userRep.setCreatedTimestamp(timeStamp)
    userRep.setDisableableCredentialTypes(user.disableableCredentialTypes.map(_.toSet.asJava).orNull)
    userRep.setRequiredActions(user.requiredActions.map(_.asJava).getOrElse(new util.ArrayList[String]()))
    userRep.setAttributes(user.attributes.map(m => m.map(p => (p._1,p._2.asJava)).asJava).getOrElse(new util.HashMap[String, java.util.List[String]]))
    userRep.setAccess(access)
    userRep
  }

  def convertToUser(userRep: UserRepresentation): User = {

    val accessClass: Access =
      Option(userRep.getAccess)
        .map(_.asScala.toMap.map(pair => (pair._1, Boolean.unbox(pair._2)))) match {
      case Some(access) =>
        Access(
          access.getOrElse(view, true),
          access.getOrElse(manage, false),
          access.getOrElse(mapRoles, false),
          access.getOrElse(manageGroupMembership, false),
          access.getOrElse(impersonate, false))
    }

    val isEnabled: Boolean = userRep.isEnabled
    val isTotp: Boolean = userRep.isTotp
    val isEmailVerified: Boolean = userRep.isEmailVerified

    User(
      Option(userRep.getId),
      Option(userRep.getUsername).getOrElse(""),
      Option(userRep.getFirstName).getOrElse(""),
      Option(userRep.getLastName).getOrElse(""),
      Option(userRep.getEmail).getOrElse(""),
      Option(isEnabled),
      Option(isTotp),
      Option(isEmailVerified),
      Option(userRep.getCreatedTimestamp),
      Option(userRep.getDisableableCredentialTypes).map(_.asScala.toList),
      Option(userRep.getRequiredActions).map(_.asScala.toList),
      Option(userRep.getNotBefore),
      Option(accessClass),
      Option(userRep.getRealmRoles).map(_.asScala.toList),
      Option(userRep.getAttributes).map(_.asScala.toMap.map(p => (p._1,p._2.asScala.toList)))
    )
  }
}

case class User(
                 userId: Option[String],
                 username: String,
                 firstName: String,
                 lastName: String,
                 email: String,
                 enabled: Option[Boolean],
                 totp: Option[Boolean],
                 emailVerified: Option[Boolean],
                 createdTimestamp: Option[Long],
                 disableableCredentialTypes: Option[List[String]],
                 requiredActions: Option[List[String]],
                 notBefore: Option[Int],
                 access: Option[Access],
                 realmRoles: Option[List[String]],
                 attributes: Option[Map[String,List[String]]]
)

case class Access(
  manageGroupMembership: Boolean,
  view: Boolean,
  mapRoles: Boolean,
  impersonate: Boolean,
  manage: Boolean
)

