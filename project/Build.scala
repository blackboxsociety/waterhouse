import sbt._
import sbt.Keys._

import bintray.Plugin._
import bintray.Keys._

object Build extends Build {

  val customBintraySettings = bintrayPublishSettings ++ Seq(
    packageLabels in bintray       := Seq("crypto"),
    bintrayOrganization in bintray := Some("blackboxsociety"),
    repository in bintray          := "releases"
  )

  val root = Project("root", file("."))
    .settings(customBintraySettings: _*)
    .settings(
      name                := "waterhouse",
      organization        := "com.blackboxsociety",
      version             := "0.3.0",
      scalaVersion        := "2.11.0",
      licenses            += ("MIT", url("http://opensource.org/licenses/MIT")),
      //scalacOptions       += "-feature",
      //scalacOptions       += "-deprecation",
      resolvers           += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
      libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.6",
      libraryDependencies += "org.scalaz" %% "scalaz-effect" % "7.0.6",
      libraryDependencies += "org.scalaz" %% "scalaz-concurrent" % "7.0.6"
    )

}
