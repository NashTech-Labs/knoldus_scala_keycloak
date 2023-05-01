ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "knoldus_scala_keycloak"
  )

val akkaHttpVersion = "10.5.0"
val akkaHttpCorsVersion = "1.2.0"
val akkaVersion = "2.8.0"
val keycloakVersion = "17.0.1"
val jdbcAndLiftJsonVersion = "3.4.1"

libraryDependencies ++= List(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "ch.megard" %% "akka-http-cors" % akkaHttpCorsVersion,

  "io.spray" %% "spray-json" % "1.3.6",

  "org.keycloak" % "keycloak-authz-client" % keycloakVersion,
  "org.keycloak" % "keycloak-core" % keycloakVersion,
  "org.keycloak" % "keycloak-adapter-core" % keycloakVersion,
  "org.keycloak" % "keycloak-admin-client" % keycloakVersion,

  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",
  "net.liftweb" %% "lift-json" % jdbcAndLiftJsonVersion,
  "com.github.jwt-scala" %% "jwt-circe" % "9.2.0",
  "org.apache.commons" % "commons-lang3" % "3.12.0",

  "com.fullfacing" %% "keycloak4s-core" % "3.2.4",
  "com.fullfacing" %% "keycloak4s-admin" % "3.2.4",
  "com.fullfacing" %% "keycloak4s-admin-monix" % "3.2.4",
  "com.fullfacing" %% "keycloak4s-admin-monix-bio" % "3.2.4",
  "com.fullfacing" %% "keycloak4s-auth-akka-http" % "3.2.4",
)
