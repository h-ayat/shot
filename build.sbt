scalaVersion := "2.13.8"
version := "0.0.2"
organization := "nothing"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.12" % Test,
  "com.ibm.icu" % "icu4j" % "71.1",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
)

enablePlugins(JavaAppPackaging)
