package com.knoldus

import akka.event.LoggingAdapter
import com.knoldus.models.KeycloakClientConfig
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.{Keycloak, KeycloakBuilder}
import com.knoldus.services._
import com.knoldus.services.master.admin.MasterServices

class KeycloakService(conf: KeycloakClientConfig)(implicit val logger: LoggingAdapter) {

  private val keycloak: Keycloak = KeycloakBuilder.builder()
    .serverUrl(conf.url)
    .realm(conf.realm)
    .grantType(OAuth2Constants.PASSWORD)
    .clientId("clientId")
    .username("username")
    .password("password")
    .build()

  private val masterKeycloak: Keycloak = KeycloakBuilder.builder()
    .serverUrl(conf.url)
    .realm(conf.realm)
    .grantType(OAuth2Constants.PASSWORD)
    .clientId("clientId")
    .username("username")
    .password("password")
    .build()

  val masterRealm: MasterServices = new MasterServices(masterKeycloak)
  val client: ClientServices = new ClientServices(keycloak)
  val user: UserServices = new UserServices(keycloak)
  val group: GroupService = new GroupService(keycloak)
  val realm: RealmService = new RealmService(keycloak)
  val clientScope: ClientScopeServices = new ClientScopeServices(keycloak)
  val role: RoleServices = new RoleServices(keycloak)
  val authentication: LoginAndAuthenticationServices = new LoginAndAuthenticationServices(keycloak)
  val protocolMapper: ProtocolMapperServices = new ProtocolMapperServices(keycloak)
}


