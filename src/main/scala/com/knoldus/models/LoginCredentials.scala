package com.knoldus.models

import scala.util.Random

object Credentials {
  private val chars = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890=+?!@#$%^&*"
  def generatePassword: String = {
    Random.nextInt(chars.length)
    val size = 0 until (8 + Random.nextInt(8))
    for (_ <- size) yield chars.charAt(Random.nextInt(chars.length))
  }.mkString("")
}
case class LoginCredentials(client_id: String,
                            username: String,
                            password: String,
                            grant_type: String)

case class LoginAccessToken(access_token: String,
                            expires_in: Int,
                            refresh_expires_in: Int,
                            refresh_token: String,
                            token_type: String,
                            session_state: String,
                            scope: String)