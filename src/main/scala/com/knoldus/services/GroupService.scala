package com.knoldus.services

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.Materializer
import com.knoldus.models.entities.{Group, Role}
import org.keycloak.admin.client.Keycloak

import scala.concurrent.Future
import scala.jdk.CollectionConverters._

class GroupService(keycloak: Keycloak)(implicit val system: ActorSystem, mat: Materializer, logger: LoggingAdapter) {

  def createGroup(group: Group, realmName: String): Future[String] = {
    try {
      keycloak.realm(realmName).groups.add(Group.convertToGroupRep(group))
      Future.successful(group.name)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed create group ${group.groupId} for realm $realmName, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }
  //TODO These all need try-catch blocks or some kind of error handling, and error responses
  def getAllGroupsFromRealm(realmName: String): Future[List[Group]] = {
    val realm = keycloak.realm(realmName)
    val response = realm.groups().groups().asScala.toList.map(Group.convertToGroup)
    Future.successful(response)
  }

  def getOneGroupFromRealm(realmName: String, groupId: String): Future[Group] = {
    val realm = keycloak.realm(realmName)
    val response = Group.convertToGroup(realm.groups().group(groupId).toRepresentation)
    Future.successful(response)
  }

  def addRoleToGroup(realm: String, groupId: String, role: Role): Future[String] = {
    try {
      keycloak.realm(realm).groups().group(groupId).roles().realmLevel().add(List(Role.roleToRoleRepresentation(role)).asJava)
      Future.successful(s"Added role ${role.name}, id ${role.id} to group $groupId")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to add role ${role.name}, id ${role.id} to group $groupId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }
  }

  def updateOneGroup(realmName: String, groupId: String, group: Group): Future[String] = {
    val groupRepresentation = Group.convertToGroupRep(group)
    keycloak.realm(realmName).groups().group(groupId).update(groupRepresentation)
    Future.successful(groupId)
  }

  def deleteOneGroup(realmName: String, groupId: String): Future[String] = {
    keycloak.realm(realmName).groups().group(groupId).remove()
    Future.successful(groupId)
  }

}
