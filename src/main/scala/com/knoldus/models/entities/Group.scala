package com.knoldus.models.entities

import org.keycloak.representations.idm.GroupRepresentation

import scala.jdk.CollectionConverters._

object Group {
  def convertToGroupRep(group: Group): GroupRepresentation = {
    val subGroups = group.subGroups.map(_.map { subGroupName =>
      val subGroupRep = new GroupRepresentation()
      subGroupRep.setName(subGroupName)
      subGroupRep
    })

    val rep = new GroupRepresentation()
    rep.setName(group.name)
    rep.setPath(group.path.getOrElse(""))
    rep.setSubGroups(subGroups.getOrElse(List.empty).asJava)
    rep
  }

  def convertToGroup(groupRep: GroupRepresentation): Group = {
    Group(
      Option(groupRep.getId),
      Option(groupRep.getName).getOrElse(""),
      Option(groupRep.getPath),
      Option(groupRep.getSubGroups).map(_.asScala.toList.map(_.getName))
    )
  }
}

case class Group(
  groupId: Option[String],
  name: String,
  path: Option[String],
  subGroups: Option[List[String]]
)
