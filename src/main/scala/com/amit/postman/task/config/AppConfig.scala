package com.amit.postman.task.config

import com.typesafe.config.{Config, ConfigFactory}

import scala.jdk.CollectionConverters.asScalaSetConverter

trait AppConfig {
  implicit val rootConfig: Config = ConfigFactory.load("application.conf")
  protected val appName = "read_large_file"
  protected val dbConfig: Config = rootConfig.getConfig("db")
  protected val csvConfig: Config = rootConfig.getConfig("csv")
  protected val tableConfig: Config = rootConfig.getConfig("table")
  protected val partitionConfig: Config = rootConfig.getConfig("partition")
  protected val jdbcOptions: Map[String, String] = dbConfig.root.keySet.asScala.map(key ⇒ key → dbConfig.getString(key)).toMap
  protected val csvOptions: Map[String, String] = csvConfig.root.keySet.asScala.map(key ⇒ key → csvConfig.getString(key)).toMap
  protected val partitionOptions:  Map[String, String] = partitionConfig.root.keySet.asScala.map(key ⇒ key → partitionConfig.getString(key)).toMap
}
