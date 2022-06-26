scalaVersion := "2.13.8"

organization := "nothing"

libraryDependencies += "com.ibm.icu" % "icu4j" % "71.1"

enablePlugins(JavaAppPackaging)
enablePlugins(GraalVMNativeImagePlugin)


