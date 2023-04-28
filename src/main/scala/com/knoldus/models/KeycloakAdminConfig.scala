package com.knoldus.models

/*
masterRealmServerURL = "https://auth.knoldus.com/auth"
masterRealmServerURL = ${?KEYCLOAK_SERVER_URL}
masterRealmName = "master"
masterRealmName = ${?KEYCLOAK_REALM_NAME}
masterClientId = "masterclientid"
masterClientId = ${?KEYCLOAK_MASTER_CLIENT_ID}
masterClientSecret = "masterclientsecret"
masterClientSecret = ${?KEYCLOAK_MASTER_CLIENT_SECRET}
masterUserName = "defaultusername"
masterUserName = ${?KEYCLOAK_USER_NAME}
clientId = "clientId"
clientId = ${?KEYCLOAK_CLIENT_ID}
clientSecret = "clientSecret"
clientSecret = ${?KEYCLOAK_CLIENT_SECRET}
masterPwd = "defaultpassword"
masterPwd = ${?KEYCLOAK_PASSWORD}
clientRoles = ["admin", "employee"]
 */

case class KeycloakAdminConfig(masterRealmServerUrl: String,
                               masterRealmName: String,
                               masterClientId: String,
                               masterClientSecret: String,
                               masterUserName: String,
                               clientId: String,
                               clientSecret: String,
                               masterPassword: String,
                               clientRiles: List[String]
                              )
