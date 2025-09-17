scalaVersion := "2.13.16"
version := "0.0.5"
organization := "nothing"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.12" % Test,
  "com.ibm.icu" % "icu4j" % "72.1",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
)

enablePlugins(JavaAppPackaging)
