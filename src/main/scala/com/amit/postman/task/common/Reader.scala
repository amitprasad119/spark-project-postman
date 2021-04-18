package com.amit.postman.task.common

import org.apache.spark.sql.{DataFrame, SparkSession}

trait Reader {
  def readCSV(sourceFilePaths: Seq[String],options: Map[String, String]): DataFrame =  {
    throw new UnsupportedOperationException("readCSV() method is not implemented.")
  }
  def readJDBC(options: Map[String, String],sourceTable:String): DataFrame =  {
    throw new UnsupportedOperationException("readJDBC() method is not implemented.")
  }

}
