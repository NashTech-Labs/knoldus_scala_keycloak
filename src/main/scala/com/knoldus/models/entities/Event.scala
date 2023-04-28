package com.knoldus.models.entities

import org.keycloak.representations.idm.{AdminEventRepresentation, AuthDetailsRepresentation, EventRepresentation}

import java.util
import scala.jdk.CollectionConverters.{MapHasAsJava, MapHasAsScala}

object Event {
  def toRepresentation(event: Event): EventRepresentation = {
    val e = new EventRepresentation
    e.setTime(event.time)
    e.setType(event.eventType)
    e.setRealmId(event.realmId)
    e.setClientId(event.clientId)
    e.setUserId(event.userId)
    e.setSessionId(event.sessionId)
    e.setIpAddress(event.ipAddress)
    e.setDetails(event.details.asJava)
    e
  }

  def toEvent(event: EventRepresentation): Event = {
    Event(
      Option(event.getTime).getOrElse(0),
      Option(event.getType).getOrElse(""),
      Option(event.getRealmId).getOrElse(""),
      Option(event.getClientId).getOrElse(""),
      Option(event.getUserId).getOrElse(""),
      Option(event.getSessionId).getOrElse(""),
      Option(event.getIpAddress).getOrElse(""),
      Option(event.getDetails).getOrElse(new util.HashMap[String, String]()).asScala.toMap
    )
  }
}

case class Event(time: Long,
                eventType: String,
                realmId: String,
                clientId: String,
                userId: String,
                sessionId: String,
                ipAddress: String,
                details: Map[String, String])

object AdminEvent {
  def toRepresentation(event: AdminEvent): AdminEventRepresentation = {
    val e = new AdminEventRepresentation
    e.setTime(event.time)
    e.setRealmId(event.realmId)
    e.setAuthDetails(AuthDetails.toRepresentation(event.authDetails))
    e.setOperationType(event.operationType)
    e.setResourceType(event.resourceType)
    e.setResourcePath(event.resourcePath)
    e
  }

  def toAdminEvent(event: AdminEventRepresentation): AdminEvent = {
    AdminEvent(
      Option(event.getTime).getOrElse(0L),
      Option(event.getRealmId).getOrElse(""),
      AuthDetails.toAuthDetails(Option(event.getAuthDetails).getOrElse(new AuthDetailsRepresentation())),
      Option(event.getOperationType).getOrElse(""),
      Option(event.getResourceType).getOrElse(""),
      Option(event.getResourcePath).getOrElse("")
    )
  }
}

case class AdminEvent(time: Long,
                      realmId: String,
                      authDetails: AuthDetails,
                      operationType: String,
                      resourceType: String,
                      resourcePath: String)


object AuthDetails {
  def toRepresentation(details: AuthDetails): AuthDetailsRepresentation = {
    val d = new AuthDetailsRepresentation()
    d.setRealmId(details.realmId)
    d.setClientId(details.clientId)
    d.setUserId(details.userId)
    d.setIpAddress(details.ipAddress)
    d
  }

  def toAuthDetails(details: AuthDetailsRepresentation): AuthDetails = {
    AuthDetails(
      Option(details.getRealmId).getOrElse(""),
      Option(details.getClientId).getOrElse(""),
      Option(details.getUserId).getOrElse(""),
      Option(details.getIpAddress).getOrElse("")
    )
  }
}
case class AuthDetails(realmId: String, clientId: String, userId: String, ipAddress: String)

