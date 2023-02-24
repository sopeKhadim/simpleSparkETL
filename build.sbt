ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

import sbtassembly.MergeStrategy

lazy val sparkVersion = sys.env.getOrElse("SPARK_VERSION", "2.4.7")

lazy val root = (project in file("."))
  .settings(
    name := "simpleSparkETL",
    idePackagePrefix := Some("com.atos.cisa"),
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
      "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
      "com.typesafe" % "config" % "1.4.2",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "org.apache.commons" % "commons-lang3" % "3.12.0"
    ),
    assembly / mainClass := Some("com.atos.cisa.Main"),
    assembly / assemblyOption ~= {
      _.withIncludeScala(false)
        .withIncludeDependency(false)
    },
    assembly / assemblyJarName := s"${name.value}_${scalaBinaryVersion.value}-${sparkVersion}_${version.value}.jar"
  )

 ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}




