import sbt._
import sbt.Keys._

object Build extends Build {

  val appName          = "waterhouse"
  val appVersion       = "1.0"
  val scalaLangVersion = "2.10.3"

  val root = Project("root", file(".")).settings(
    name                := appName,
    version             := appVersion,
    scalaVersion        := scalaLangVersion,
    //scalacOptions       += "-feature",
    //scalacOptions       += "-deprecation",
    resolvers           += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.6",
    libraryDependencies += "org.scalaz" %% "scalaz-effect" % "7.0.6",
    libraryDependencies += "org.scalaz" %% "scalaz-concurrent" % "7.0.6",
    libraryDependencies += "org.scalaz" %% "scalaz-iteratee" % "7.0.6"
  )

}
