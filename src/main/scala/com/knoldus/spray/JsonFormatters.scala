package com.knoldus.spray

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.models._
import com.knoldus.models.entities._
import com.knoldus.response._
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
  implicit val statusResWrites: RootJsonFormat[StatusResponse] = jsonFormat2(StatusResponse)
  implicit val roleFormat: RootJsonFormat[Role] = jsonFormat6(Role.apply)
  implicit val realmFormat: RootJsonFormat[Realm] = jsonFormat22(Realm.apply)
  implicit val protocolMapperFormat: RootJsonFormat[ProtocolMapper] = jsonFormat6(ProtocolMapper.apply)
  implicit val clientScopeRepresentationFormat: RootJsonFormat[ClientScope] = jsonFormat6(ClientScope.apply)
  implicit val authDetailsFormat: RootJsonFormat[AuthDetails] = jsonFormat4(AuthDetails.apply)
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat8(Event.apply)
  implicit val eventAdminFormat: RootJsonFormat[AdminEvent] = jsonFormat6(AdminEvent.apply)
  implicit val loginAccessTokenFormat: RootJsonFormat[LoginAccessToken] = jsonFormat7(LoginAccessToken.apply)
  implicit val loginCredentialsFormat: RootJsonFormat[LoginCredentials] = jsonFormat4(LoginCredentials.apply)

  // -----------------------------------------------------
  // -------------------  Responses  ---------------------
  //------------------------------------------------------

  implicit val emptyResponseFormat: RootJsonFormat[EmptyResponse] = jsonFormat1(EmptyResponse)
  implicit val clientResponseFormat: RootJsonFormat[ClientRepResponse] = jsonFormat1(ClientRepResponse)
  implicit val clientListResponseFormat: RootJsonFormat[ClientListResponse] = jsonFormat1(ClientListResponse)
  implicit val clientScopeResponseFormat: RootJsonFormat[ClientScopeResponse] = jsonFormat1(ClientScopeResponse)
  implicit val clientScopeListResponseFormat: RootJsonFormat[ClientScopeListResponse] = jsonFormat1(ClientScopeListResponse)
  implicit val realmResponseFormat: RootJsonFormat[RealmResponse] = jsonFormat1(RealmResponse)
  implicit val groupResponseFormat: RootJsonFormat[GroupResponse] = jsonFormat1(GroupResponse)
  implicit val userResponseFormat: RootJsonFormat[UserResponse] = jsonFormat1(UserResponse)
  implicit val realmListResponseFormat: RootJsonFormat[RealmListResponse] = jsonFormat1(RealmListResponse)
  implicit val groupListResponseFormat: RootJsonFormat[GroupListResponse] = jsonFormat1(GroupListResponse)
  implicit val userListResponseFormat: RootJsonFormat[UserListResponse] = jsonFormat1(UserListResponse)
  implicit val roleListResponseFormat: RootJsonFormat[RoleListResponse] = jsonFormat1(RoleListResponse)
  implicit val roleResponseFormat: RootJsonFormat[RoleResponse] = jsonFormat1(RoleResponse)
  implicit val eventsResponseFormat: RootJsonFormat[EventsResponse] = jsonFormat1(EventsResponse)
  implicit val adminEventsResponseFormat: RootJsonFormat[AdminEventsResponse] = jsonFormat1(AdminEventsResponse)
  implicit val loginAccessTokenResponseFormat: RootJsonFormat[LoginAccessTokenResponse] = jsonFormat1(LoginAccessTokenResponse)
  implicit val createUserResponseFormat: RootJsonFormat[CreateUserResponse] = jsonFormat1(CreateUserResponse)

}