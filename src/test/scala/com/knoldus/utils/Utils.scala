package com.knoldus.utils

import org.keycloak.representations.idm.{RealmRepresentation, RoleRepresentation}

import java.util
import scala.jdk.CollectionConverters.{SeqHasAsJava, SetHasAsJava}

object Utils {
 val testRealm = new RealmRepresentation
  testRealm.setId("heores")
  testRealm.setRealm("heroes")
  testRealm.setNotBefore(0)
  testRealm.setRevokeRefreshToken(false)
  testRealm.setRefreshTokenMaxReuse(0)
  testRealm.setAccessTokenLifespan(300)
  testRealm.setAccessTokenLifespanForImplicitFlow(900)

  testRealm.setSsoSessionIdleTimeout(1800)
  testRealm.setSsoSessionMaxLifespan(36000)
  testRealm.setSsoSessionIdleTimeoutRememberMe(0)
  testRealm.setSsoSessionMaxLifespanRememberMe(0)

  testRealm.setOfflineSessionIdleTimeout(2592000)
  testRealm.setOfflineSessionMaxLifespanEnabled(false)
  testRealm.setOfflineSessionMaxLifespan(5184000)
  testRealm.setClientSessionIdleTimeout(0)
  testRealm.setClientSessionMaxLifespan(0)

  testRealm.setAccessCodeLifespan(60)
  testRealm.setAccessCodeLifespanUserAction(300)
  testRealm.setAccessCodeLifespanLogin(1800)

  testRealm.setActionTokenGeneratedByAdminLifespan(43200)
  testRealm.setActionTokenGeneratedByUserLifespan(300)

  testRealm.setEnabled(true)
  testRealm.setSslRequired("external")
  testRealm.setRegistrationAllowed(false)
  testRealm.setRegistrationEmailAsUsername(false)
  testRealm.setRememberMe(false)

  testRealm.setVerifyEmail(false)
  testRealm.setLoginWithEmailAllowed(true)
  testRealm.setDuplicateEmailsAllowed(false)

  testRealm.setResetPasswordAllowed(false)
  testRealm.setEditUsernameAllowed(false)
  testRealm.setBruteForceProtected(false)
  testRealm.setPermanentLockout(false)

  testRealm.setMaxFailureWaitSeconds(900)
  testRealm.setMinimumQuickLoginWaitSeconds(60)
  testRealm.setWaitIncrementSeconds(60)
  testRealm.setQuickLoginCheckMilliSeconds(1000)
  testRealm.setMaxDeltaTimeSeconds(43200)
  testRealm.setFailureFactor(30)

  testRealm.setDefaultRoles(List[String]("offline_access","uma_authorization").asJava)
  testRealm.setRequiredCredentials(Set[String]("password").asJava)

  testRealm.setOtpPolicyType("totp")
 testRealm.setOtpPolicyAlgorithm("HmacSHA1")
 testRealm.setOtpPolicyInitialCounter(0)
 testRealm.setOtpPolicyDigits(6)
 testRealm.setOtpPolicyLookAheadWindow(1)
 testRealm.setOtpPolicyPeriod(30)
 testRealm.setOtpSupportedApplications(List[String]("FreeOTP","Google Authenticator").asJava)

 testRealm.setWebAuthnPolicyRpId("")
}
