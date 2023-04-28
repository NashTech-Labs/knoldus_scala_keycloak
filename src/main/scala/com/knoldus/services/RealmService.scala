package com.knoldus.services

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.Materializer
import com.knoldus.models.entities.Realm
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.{AdminEventRepresentation, EventRepresentation}

import scala.concurrent.Future
import scala.jdk.CollectionConverters.ListHasAsScala

class RealmService(keycloak: Keycloak)(implicit val system: ActorSystem, mat: Materializer, logger: LoggingAdapter) {

  def getRealms: Future[List[Realm]] = {
    val realms = keycloak.realms().findAll().asScala.toList.map(Realm.convertToRealm)
    Future.successful(realms)
  }

  def getOneRealm(realmName: String): Future[Realm] = {
    val realms = Realm.convertToRealm(keycloak.realms().realm(realmName).toRepresentation)
    Future.successful(realms)
  }

  def getAdminEventsUtil(realm: String): Future[List[AdminEventRepresentation]] = {
    try {
      Future.successful(keycloak.realm(realm).getAdminEvents().asScala.toList)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch admin events from realm $realm with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def getEventsUtil(realm: String): Future[List[EventRepresentation]] = {
    try {
      Future.successful(keycloak.realm(realm).getEvents.asScala.toList)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch events from realm $realm with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }
}
