package com.knoldus.models.entities

import com.knoldus.utils.Constants.DEFAULT_PROTOCOL
import org.apache.commons.lang3.builder.{ToStringBuilder, ToStringStyle}
import org.keycloak.representations.idm.ClientRepresentation

import scala.jdk.CollectionConverters._

object Client {
  def clientRepresentationToClientClass(client: ClientRepresentation): Client = {
    Client(
      Option(client.getClientId).getOrElse(throw new Exception("Client is missing id")),
      Option(client.getProtocol).getOrElse(DEFAULT_PROTOCOL),
      Option(client.getName),
      Option(client.getAdminUrl),
      Option(client.getAccess).map(m => m.asScala.toMap.map(t => (t._1 , Boolean.unbox(t._2)))),
      Option(client.getAttributes).map(_.asScala.toMap),
      Option(client.getAuthenticationFlowBindingOverrides).map(_.asScala.toMap),
      Option(client.getDescription),
      Option(client.getRootUrl),
      Option(client.getBaseUrl),
      Option(client.getClientAuthenticatorType),
      Option(client.getDefaultRoles),
      Option(client.getRedirectUris).map(_.asScala.toList),
      Option(client.getWebOrigins).map(_.asScala.toList),
      Option(client.getNotBefore).map(_.toInt),
      Option(client.getNodeReRegistrationTimeout).map(_.toInt),
      Option(client.getDefaultClientScopes).map(_.asScala.toList),
      Option(client.getOptionalClientScopes).map(_.asScala.toList),
      Some(Enables(
        Option(client.isAlwaysDisplayInConsole),
        Option(client.getAuthorizationServicesEnabled),
        Option(client.isBearerOnly),
        Option(client.isDirectAccessGrantsEnabled),
        Option(client.isEnabled),
        Option(client.isSurrogateAuthRequired),
        Option(client.isConsentRequired),
        Option(client.isStandardFlowEnabled),
        Option(client.isImplicitFlowEnabled),
        Option(client.isServiceAccountsEnabled),
        Option(client.isPublicClient),
        Option(client.isFrontchannelLogout),
        Option(client.isFullScopeAllowed))))
  }

  def convertToClientRep(client: Client): ClientRepresentation = {
    val rep = new ClientRepresentation()

    def setEnables(enables: Enables): Unit = {
      rep.setAlwaysDisplayInConsole(Boolean.box(enables.alwaysDisplayInConsole.getOrElse(false)))
      rep.setAuthorizationServicesEnabled(Boolean.box(enables.authorizationServicesEnabled.getOrElse(false)))
      rep.setBearerOnly(Boolean.box(enables.bearerOnly.getOrElse(false)))
      rep.setDirectAccessGrantsEnabled(Boolean.box(enables.directAccessGrantsEnabled.getOrElse(true)))
      rep.setEnabled(Boolean.box(enables.enabled.getOrElse(true)))
      rep.setSurrogateAuthRequired(Boolean.box(enables.surrogateAuthRequired.getOrElse(false)))
      rep.setConsentRequired(Boolean.box(enables.consentRequired.getOrElse(false)))
      rep.setStandardFlowEnabled(Boolean.box(enables.standardFlowEnabled.getOrElse(true)))
      rep.setImplicitFlowEnabled(Boolean.box(enables.implicitFlowEnabled.getOrElse(false)))
      rep.setServiceAccountsEnabled(Boolean.box(enables.serviceAccountsEnabled.getOrElse(false)))
      rep.setPublicClient(Boolean.box(enables.publicClient.getOrElse(false)))
      rep.setFrontchannelLogout(Boolean.box(enables.frontchannelLogout.getOrElse(false)))
      rep.setFullScopeAllowed(Boolean.box(enables.fullScopeAllowed.getOrElse(false)))
    }

    setEnables(client.enables.getOrElse(Enables.apply()))
    rep.setClientId(client.clientId)
    rep.setName(client.name.getOrElse(client.clientId))
    rep.setAdminUrl(client.adminUrl.getOrElse(""))
    rep.setAccess(client.access.getOrElse(Map.empty).map(p => (p._1, Boolean.box(p._2))).asJava)
    rep.setAuthenticationFlowBindingOverrides(client.authenticationFlowBindingOverrides.getOrElse(Map.empty).asJava)
    rep.setProtocol(client.protocol)
    rep.setDescription(client.description.getOrElse(""))
    rep.setRootUrl(client.rootUrl.getOrElse(""))
    rep.setBaseUrl(client.baseUrl.getOrElse(""))
    rep.setClientAuthenticatorType(client.clientAuthenticatorType.getOrElse(""))
    rep.setDefaultRoles(client.defaultRoles.getOrElse(Array[String]()))
    rep.setRedirectUris(client.redirectUris.getOrElse(List.empty).asJava)
    rep.setWebOrigins(client.webOrigins.getOrElse(List.empty).asJava)
    rep.setNotBefore(client.notBefore.getOrElse[Int](0))
    rep.setNodeReRegistrationTimeout(client.nodeReRegistrationTimeout.getOrElse[Int](720))
    rep.setDefaultClientScopes(client.defaultClientScopes.getOrElse(List.empty).asJava)
    rep.setOptionalClientScopes(client.optionalClientScopes.getOrElse(List.empty).asJava)
    rep

  }

  def updateClient(client: Client, clientRep: ClientRepresentation): Unit = {
    clientRep
  }
}

case class Client(clientId: String,
                  protocol: String,
                  name: Option[String],
                  adminUrl: Option[String],
                  access: Option[Map[String, Boolean]],
                  attributes: Option[Map[String, String]],
                  authenticationFlowBindingOverrides: Option[Map[String,String]],
                  description: Option[String],
                  rootUrl: Option[String],
                  baseUrl: Option[String],
                  clientAuthenticatorType: Option[String],
                  defaultRoles: Option[Array[String]],
                  redirectUris: Option[List[String]],
                  webOrigins: Option[List[String]],
                  notBefore: Option[Int],
                  nodeReRegistrationTimeout: Option[Int],
                  defaultClientScopes: Option[List[String]],
                  optionalClientScopes: Option[List[String]],
                  enables: Option[Enables]) {
}

// These are default settings
case class Enables(alwaysDisplayInConsole: Option[Boolean] = Some(false),
                   authorizationServicesEnabled: Option[Boolean] = Some(false),
                   bearerOnly: Option[Boolean] = Some(false),
                   directAccessGrantsEnabled: Option[Boolean] = Some(true),
                   enabled:Option[Boolean] = Some(true),
                   surrogateAuthRequired: Option[Boolean] = Some(false),
                   consentRequired: Option[Boolean] = Some(false),
                   standardFlowEnabled: Option[Boolean] = Some(true),
                   implicitFlowEnabled: Option[Boolean] = Some(false),
                   serviceAccountsEnabled: Option[Boolean] = Some(false),
                   publicClient: Option[Boolean] = Some(false),
                   frontchannelLogout: Option[Boolean] = Some(false),
                   fullScopeAllowed: Option[Boolean] = Some(false))
