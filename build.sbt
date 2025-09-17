scalaVersion := "2.13.16"
version := "0.1.0"
organization := "nothing"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.19" % Test,
  "com.ibm.icu" % "icu4j" % "77.1",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.4.0"
)

enablePlugins(JavaAppPackaging)
