package com.amit.postman.task.common

import org.apache.spark.sql.{DataFrame, SaveMode}

trait Writer {
  def writeAggregate(dataFrame:DataFrame,sourceTable:String,destinationTable: String,options: Map[String, String],format:String="csv",saveMode:SaveMode): Unit = {
    throw new UnsupportedOperationException("writeAggregate() method is not implemented.")
  }
  def write(dataFrame:DataFrame,options: Map[String, String],tableName:String,saveMode:SaveMode = SaveMode.ErrorIfExists,format:String="parquet"):Unit = {
    throw new UnsupportedOperationException("write() method is not implemented.")
  }
}
