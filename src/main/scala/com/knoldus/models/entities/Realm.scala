package com.knoldus.models.entities

import org.keycloak.representations.idm.RealmRepresentation

import scala.jdk.CollectionConverters._

object Realm {
  def updateRealmRepresentation(realm: Realm): RealmRepresentation = {

    // TODO almost all of these fields to so seem to work. Values are not reflected in keycloak
    val realmRepresentation = new RealmRepresentation()
    realmRepresentation.setDisplayName(realm.realm)
    realmRepresentation.setEnabled(Boolean.box(realm.enabled.getOrElse(true)))
    realmRepresentation.setRegistrationAllowed(Boolean.box(realm.registrationAllowed.getOrElse(true)))
    realmRepresentation.setRememberMe(Boolean.box(realm.rememberMe.getOrElse(true)))
    realmRepresentation.setVerifyEmail(Boolean.box(realm.verifyEmail.getOrElse(false)))
    // TODO the java library does not seem to work. Even setting to only true does not reflect on keycloak server
    realmRepresentation.setUserManagedAccessAllowed(Boolean.box(realm.userManagedAccessAllowed.getOrElse(true)))


   // realmRepresentation.setSslRequired(realm.sslRequired.getOrElse("external"))
   // realmRepresentation.setRequiredCredentials(realm.requiredCredentials.map(_.toSet).getOrElse(Set.empty).asJava)
    realmRepresentation
  }

  def convertToRealm(realmRep: RealmRepresentation): Realm = {
    val isEnabled: Boolean = realmRep.isEnabled
    val isRegistrationAllowed: Boolean = realmRep.isRegistrationAllowed
    val isRememberMe: Boolean = realmRep.isRememberMe
    val isVerifyEmail: Boolean = realmRep.isVerifyEmail
    val isRegistrationEmailAsUsername: Boolean = realmRep.isRegistrationEmailAsUsername
    val isUserManagedAccessAllowed: Boolean = realmRep.isUserManagedAccessAllowed
    val isLoginWithEmailAllowed: Boolean = realmRep.isLoginWithEmailAllowed
    val internationalizationEnabled: Boolean = realmRep.isInternationalizationEnabled
    val duplicateEmailsAllowed: Boolean = realmRep.isDuplicateEmailsAllowed
    val resetPasswordAllowed: Boolean = realmRep.isResetPasswordAllowed
    val editUsernameAllowed: Boolean = realmRep.isEditUsernameAllowed
    val bruteForceProtected: Boolean = realmRep.isBruteForceProtected
    val permanentLockout: Boolean = realmRep.isPermanentLockout
    val revokeRefreshToken: Boolean = realmRep.getRevokeRefreshToken

    Realm(
      Option(realmRep.getId),
      Option(realmRep.getRealm).getOrElse(""),
      Option(isEnabled),
      Option(realmRep.getSslRequired),
      Option(isRegistrationAllowed),
      Option(isRegistrationEmailAsUsername),
      Option(isRememberMe),
      Option(isVerifyEmail),
      Option(realmRep.getDefaultRoles).map(_.asScala.toList),
      Option(realmRep.getRequiredCredentials).map(_.asScala.toList),
      Option(internationalizationEnabled),
      Option(isUserManagedAccessAllowed),
      Option(isLoginWithEmailAllowed),
      Option(duplicateEmailsAllowed),
      Option(resetPasswordAllowed),
      Option(editUsernameAllowed),
      Option(bruteForceProtected),
      Option(permanentLockout),
      Option(revokeRefreshToken),
      Option(realmRep.getRefreshTokenMaxReuse.intValue()),
      Option(realmRep.getClientAuthenticationFlow),
      Option(realmRep.getFailureFactor.intValue())
    )
  }

}

case class Realm(
  id: Option[String],
  realm: String,
  enabled: Option[Boolean],
  sslRequired: Option[String],
  registrationAllowed: Option[Boolean],
  registrationEmailAsUsername: Option[Boolean],
  rememberMe: Option[Boolean],
  verifyEmail: Option[Boolean],
  defaultRoles: Option[List[String]],
  requiredCredentials: Option[List[String]],
  internationalizationEnabled: Option[Boolean],
  userManagedAccessAllowed: Option[Boolean],
  loginWithEmailAllowed: Option[Boolean],
  duplicateEmailsAllowed: Option[Boolean],
  resetPasswordAllowed: Option[Boolean],
  editUsernameAllowed: Option[Boolean],
  bruteForceProtected: Option[Boolean],
  permanentLockout: Option[Boolean],
  revokeRefreshToken: Option[Boolean],
  refreshTokenMaxReuse: Option[Int],
  clientAuthenticationFlow: Option[String],
  failureFactor: Option[Int]
)
