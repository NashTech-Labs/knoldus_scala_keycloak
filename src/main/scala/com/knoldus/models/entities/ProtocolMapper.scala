package com.knoldus.models.entities

import org.keycloak.representations.idm.ProtocolMapperRepresentation

import scala.jdk.CollectionConverters.{MapHasAsJava, MapHasAsScala}

object ProtocolMapper {
  def toRepresentation(pro: ProtocolMapper, update: Boolean = false): ProtocolMapperRepresentation = {
    val rep = new ProtocolMapperRepresentation()
    // We only want an Id if we are updating, otherwise id needs to be null for keycloak to instantiate a random id
    if (update)
      rep.setId(pro.id.getOrElse(throw new Exception("Protocol missing field: 'id'")))
    rep.setName(pro.name)
    rep.setProtocol(pro.protocol)
    rep.setProtocolMapper(pro.protocolMapper)
    rep.setConfig(pro.config.getOrElse(Map.empty).asJava)
    rep
  }

  def toProtocolMapper(rep: ProtocolMapperRepresentation): ProtocolMapper = {
    ProtocolMapper(
      Option(rep.getName).getOrElse(throw new Exception("Protocol missing field: 'name'")),
      Option(rep.getProtocol).getOrElse(throw new Exception("Protocol missing field: 'protocol'")) ,
      Option(rep.getProtocolMapper).getOrElse(throw new Exception("Protocol missing field: 'protocolMapper'")),
      Option(rep.getId),
      Option(rep.getConfig.asScala.toMap)
    )
  }
}

case class ProtocolMapper(name: String, protocol: String, protocolMapper: String, id: Option[String], config: Option[Map[String,String]])
