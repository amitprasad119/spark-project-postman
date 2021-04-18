package com.amit.postman.task.common

import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession

object SparkInit extends LazyLogging{
  def getSparkSession(appName:String): SparkSession = {
    logger.info("************* Starting Spark Application**********")
    SparkSession.builder()
      .appName(appName)
      .master("local[*]")
      .getOrCreate()
  }
}
