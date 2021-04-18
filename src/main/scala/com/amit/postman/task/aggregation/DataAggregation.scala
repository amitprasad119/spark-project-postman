package com.amit.postman.task.aggregation

import com.amit.postman.task.common.Writer
import com.typesafe.config.Config
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

import scala.collection.mutable

class DataAggregation (implicit sparkSession: SparkSession, rootConfig: Config) extends Writer {
  override def writeAggregate(dataFrame:DataFrame,sourceTable:String,destinationTable:String,options: Map[String, String],format:String="csv",saveMode:SaveMode): Unit = {
    val updatedOptions: mutable.Map[String, String] = collection.mutable.Map(options.toSeq: _*)
    //update the dbtable for new destination
    updatedOptions("dbTable") = destinationTable
    dataFrame.write
      .format(format)
      .options(updatedOptions)
      .mode(saveMode)
      .save()
    }
}
