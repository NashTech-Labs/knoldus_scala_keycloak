package com.knoldus.models.entities

import org.keycloak.representations.idm.RoleRepresentation
import spray.json.JsValue

object Role {
  def roleToRoleRepresentation(role: Role): RoleRepresentation = {
    val roleRep = new RoleRepresentation()
    roleRep.setName(role.name)
    roleRep.setId(role.id.getOrElse(""))
    roleRep.setDescription(role.description.getOrElse(""))
    roleRep.setComposite(role.composite)
    roleRep.setClientRole(role.clientRole)
    roleRep.setContainerId(role.containerId.getOrElse(""))
    roleRep
  }

  def representationToRole(roleRep: RoleRepresentation): Role = {
    Role(
      Option(roleRep.getName).getOrElse(""),
      Option(roleRep.getId),
      Option(roleRep.getDescription),
      Option(roleRep.isComposite).getOrElse(false),
      Boolean.unbox(Option(roleRep.getClientRole).getOrElse(new java.lang.Boolean(false))),
      Option(roleRep.getContainerId),
      Some(List.empty),
      Some(Map.empty)
    )
  }
}

case class Role(name: String,
                id: Option[String],
                description: Option[String],
                composite: Boolean,
                clientRole: Boolean,
                containerId: Option[String],
                compositeRealmRoles: Option[List[JsValue]],
                compositeClientRoles: Option[Map[String, List[JsValue]]])


