package com.knoldus.services

import akka.event.LoggingAdapter
import com.knoldus.models.entities.ProtocolMapper
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.ProtocolMapperRepresentation

import scala.concurrent.Future
import scala.jdk.CollectionConverters.{ListHasAsScala, SeqHasAsJava}

class ProtocolMapperServices(keycloak: Keycloak)(implicit val logger: LoggingAdapter) {
  def getProtocolsUtils(realm: String, id: String): Future[List[ProtocolMapperRepresentation]] =
    try{
      Future.successful(keycloak.realm(realm).clients().get(id).getProtocolMappers.getMappers.asScala.toList)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch protocol mappers for client $id in realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def findProtocolUtils(realm: String, id: String, mapperId: String): Future[ProtocolMapperRepresentation] =
    try {
      Future.successful(keycloak.realm(realm).clients().get(id).getProtocolMappers.getMapperById(mapperId))
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch protocol mapper $mapperId for client $id in realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def createProtocolsUtils(realm: String, id: String, mappers: List[ProtocolMapper]): Future[String] =
    try {
      keycloak
        .realm(realm).clients().get(id).getProtocolMappers.createMapper(mappers.map(ProtocolMapper.toRepresentation(_)).asJava)
      Future.successful(s"Created protocol mapper for client $id, realm $realm")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to create protocols for client $id in realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def updateProtocolUtils(realm: String, id: String, mapper: ProtocolMapper): Future[String] =
    try {
      val mapperId = mapper.id.getOrElse(throw new Exception("Protocol missing field: 'id'"))
      keycloak.realm(realm).clients().get(id).getProtocolMappers.update(mapperId, ProtocolMapper.toRepresentation(mapper, true))
      Future.successful(s"Update protocol mapper ${mapper.name} for client $id, realm $realm")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to update protocol mapper ${mapper.name} for client $id in realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def deleteProtocolUtils(realm: String, id: String, mapperId: String): Future[String] =
    try {
      keycloak.realm(realm).clients().get(id).getProtocolMappers.delete(mapperId)
      Future.successful(s"Deleted protocol mapper $mapperId for client $id, realm $realm")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to delete protocol mapper $mapperId for client $id in realm $realm, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
}
