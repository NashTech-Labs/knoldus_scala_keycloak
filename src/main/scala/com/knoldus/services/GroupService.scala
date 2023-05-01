package com.knoldus.services

import akka.event.LoggingAdapter
import com.knoldus.models.entities.{Group, Role}
import org.keycloak.admin.client.Keycloak

import scala.concurrent.Future
import scala.jdk.CollectionConverters._

class GroupService(keycloak: Keycloak)(implicit val logger: LoggingAdapter) {

  def createGroup(group: Group, realmName: String): Future[String] =
    try {
      keycloak.realm(realmName).groups.add(Group.convertToGroupRep(group))
      Future.successful(group.name)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed create group ${group.groupId} for realm $realmName, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def getAllGroupsFromRealm(realmName: String): Future[List[Group]] =
    try {
      Future.successful(keycloak.realm(realmName).groups().groups().asScala.toList.map(Group.convertToGroup))
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch groups, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def getOneGroupFromRealm(realmName: String, groupId: String): Future[Group] =
    try {
      Future.successful(Group.convertToGroup(keycloak.realm(realmName).groups().group(groupId).toRepresentation))
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to fetch group $groupId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def addRoleToGroup(realm: String, groupId: String, role: Role): Future[String] =
    try {
      keycloak.realm(realm).groups().group(groupId).roles().realmLevel().add(List(Role.roleToRoleRepresentation(role)).asJava)
      Future.successful(s"Added role ${role.name}, id ${role.id} to group $groupId")
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to add role ${role.name}, id ${role.id} to group $groupId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def updateOneGroup(realmName: String, groupId: String, group: Group): Future[String] =
    try {
      val groupRepresentation = Group.convertToGroupRep(group)
      keycloak.realm(realmName).groups().group(groupId).update(groupRepresentation)
      Future.successful(groupId)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to update group $groupId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }

  def deleteOneGroup(realmName: String, groupId: String): Future[String] =
    try {
      keycloak.realm(realmName).groups().group(groupId).remove()
      Future.successful(groupId)
    } catch {
      case e: Throwable =>
        logger.error(s"Failed to delete group $groupId, with exception: ${e.getLocalizedMessage}")
        Future.failed(e)
    }


}
