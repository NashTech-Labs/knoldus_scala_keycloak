package com.knoldus.models.entities

import org.keycloak.representations.idm.{ClientScopeRepresentation, ProtocolMapperRepresentation}

import java.util
import scala.jdk.CollectionConverters.{ListHasAsScala, MapHasAsJava, MapHasAsScala, SeqHasAsJava}

object ClientScope {
  def toRepresentation(clientScope: ClientScope): ClientScopeRepresentation = {
    val scope = new ClientScopeRepresentation()
    scope.setId(clientScope.id.getOrElse(""))
    scope.setName(clientScope.name)
    scope.setDescription(clientScope.description.getOrElse(""))
    scope.setProtocol(clientScope.protocol)
    scope.setAttributes(clientScope.attributes.getOrElse(Map.empty).asJava)
    scope.setProtocolMappers(clientScope.protocolMapper.map(_.map(p => ProtocolMapper.toRepresentation(p))).getOrElse(List.empty).asJava)
    scope
  }

  def toClientScope(rep: ClientScopeRepresentation): ClientScope = {
    ClientScope(
      Option(rep.getId),
      Option(rep.getName).getOrElse(throw new Exception("Client Scope: Missing field 'name'")),
      Option(rep.getDescription),
      Option(rep.getProtocol).getOrElse(throw new Exception("Client Scope: Missing field 'protocol'")),
      Option(rep.getAttributes).map(_.asScala.toMap),
      Option(rep.getProtocolMappers).map(_.asScala.toList.map(p => ProtocolMapper.toProtocolMapper(p)))
    )
  }
}

case class ClientScope(id: Option[String],
                       name: String,
                       description: Option[String],
                       protocol: String,
                       attributes: Option[Map[String, String]],
                       protocolMapper: Option[List[ProtocolMapper]])