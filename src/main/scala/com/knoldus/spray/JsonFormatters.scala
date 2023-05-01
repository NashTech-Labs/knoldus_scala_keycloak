package com.knoldus.spray

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.models._
import com.knoldus.models.entities._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonFormatters extends DefaultJsonProtocol with SprayJsonSupport{

  // -----------------------------------------------------
  // -------------------  Models  ------------------------
  //------------------------------------------------------

  implicit val userJson: RootJsonFormat[Access] = jsonFormat5(Access)
  implicit val accessJson: RootJsonFormat[User] = jsonFormat15(User.apply)
  implicit val groupJson: RootJsonFormat[Group] = jsonFormat4(Group.apply)
  implicit val enablesFormat: RootJsonFormat[Enables] = jsonFormat13(Enables)
  implicit val clientFormat: RootJsonFormat[Client] = jsonFormat19(Client.apply)
  implicit val keycloakClientConfigFormat: RootJsonFormat[KeycloakClientConfig] = jsonFormat6(KeycloakClientConfig)
  implicit val roleFormat: RootJsonFormat[Role] = jsonFormat8(Role.apply)
  implicit val realmFormat: RootJsonFormat[Realm] = jsonFormat22(Realm.apply)
  implicit val protocolMapperFormat: RootJsonFormat[ProtocolMapper] = jsonFormat5(ProtocolMapper.apply)
  implicit val clientScopeRepresentationFormat: RootJsonFormat[ClientScope] = jsonFormat6(ClientScope.apply)
  implicit val authDetailsFormat: RootJsonFormat[AuthDetails] = jsonFormat4(AuthDetails.apply)
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat8(Event.apply)
  implicit val eventAdminFormat: RootJsonFormat[AdminEvent] = jsonFormat6(AdminEvent.apply)
  implicit val loginAccessTokenFormat: RootJsonFormat[LoginAccessToken] = jsonFormat7(LoginAccessToken.apply)
  implicit val loginCredentialsFormat: RootJsonFormat[LoginCredentials] = jsonFormat4(LoginCredentials.apply)

}