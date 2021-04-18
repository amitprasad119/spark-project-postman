package com.amit.postman.task.ingestion

import com.amit.postman.task.common.{Reader, Writer}
import com.amit.postman.task.config.AppConfig
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

import java.time.LocalDateTime
import scala.collection.mutable

class DataIngestion(implicit sparkSession: SparkSession, rootConfig: Config)  extends AppConfig with Reader with Writer with LazyLogging {

   override def readCSV(sourceFilePaths: Seq[String],options: Map[String, String]):DataFrame = {
    logger.info(s"Reading CSV Files: ${sourceFilePaths.mkString}, with options: $options")
    val df: DataFrame =  sparkSession.read.format("csv")
      .options(options)
      .load(sourceFilePaths:_*)
    logger.info(s"Schema for current DF: ${df.printSchema()}")
     df
  }

  override def readJDBC(options: Map[String, String],sourceTable:String):DataFrame = {
    val updatedOptions: mutable.Map[String, String] = collection.mutable.Map(options.toSeq: _*)
    //update the dbtable for new destination
    updatedOptions("dbTable") = sourceTable
    logger.info(s"Reading readJDBC  with options: $updatedOptions")
     sparkSession.read.format("jdbc")
      .options(updatedOptions)
      .load()
  }

  override def write(dataFrame:DataFrame,options: Map[String, String],tableName:String,saveMode:SaveMode,format:String) = {
    val dfWithMetaData =   dataFrame.withColumn("created_at", lit(LocalDateTime.now().toString))
    dfWithMetaData.printSchema()
    val updatedOptions: mutable.Map[String, String] = collection.mutable.Map(options.toSeq: _*)
    //update the dbtable for new destination
    updatedOptions("dbTable") = tableName
    logger.info(s"Writing DF to $format, with options: $updatedOptions")
    dfWithMetaData.write
      .format(format)
      .options(updatedOptions)
      .mode(saveMode)
      .save()
   }
}
