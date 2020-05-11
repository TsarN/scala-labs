name := "lab10"

version := "0.1"

scalaVersion := "2.12.11"
scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.3"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"