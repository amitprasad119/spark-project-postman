name := "spark-project-postman"

version := "0.1"

scalaVersion := "2.12.12"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "org.apache.spark" %% "spark-core" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.spark" %% "spark-mllib" % "3.0.1",
  "mysql" % "mysql-connector-java" % "8.0.22",
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
  "org.apache.spark" %% "spark-hive" % "3.0.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)
mainClass in Compile := Some("com.amit.postman.task.Driver")

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)