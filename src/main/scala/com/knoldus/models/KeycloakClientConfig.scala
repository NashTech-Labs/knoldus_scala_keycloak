package com.knoldus.models

/*{
"realm": "knoldus",
"auth-server-url": "https://auth.knoldus.com/auth",
"ssl-required": "external",
"resource": "leaderboard-ui",
"public-client": true,
"confidential-port": 0
}*/
case class KeycloakClientConfig(realm: String,
                                url: String,
                                sllRequired: String,
                                resource: String,
                                publicClient: Boolean,
                                port: Int) {
  override def toString: String =
    s"""
      |"realm": "$realm",
      |"auth-server-url": "$url",
      |"ssl-required": "$sllRequired",
      |"resource": "$resource",
      |"public-client": $publicClient,
      |"confidential-port": $port
      |""".stripMargin

}
