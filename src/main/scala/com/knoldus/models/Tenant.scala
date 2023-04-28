package com.knoldus.models

final case class Tenant(name : String, idp : Option[IdpDetails], application : String, user : UserDetails)

final case class IdpDetails(providerId: String, clientId: String, secret : String)

final case class UserDetails(userName: String, firstName : String, lastName : String, email : String)