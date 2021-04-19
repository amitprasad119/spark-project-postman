package com.amit.postman.task

import com.amit.postman.task.aggregation.DataAggregation
import com.amit.postman.task.common.SparkInit
import com.amit.postman.task.config.AppConfig
import com.amit.postman.task.ingestion.DataIngestion
import com.amit.postman.task.udf.DataFrameUtils
import com.typesafe.scalalogging.LazyLogging
import io.netty.util.internal.PlatformDependent
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.log4j.BasicConfigurator

object Driver extends AppConfig with LazyLogging{
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "/")
    BasicConfigurator.configure()
    PlatformDependent.getContextClassLoader()
   val sourceFiles =   if(args.length  == 0) Array("src/main/resources/products.csv") else args
    logger.info(s"Input files are:${sourceFiles.mkString("\n")}")
    runApp(sourceFiles)
  }

  def runApp(sourceFiles:Seq[String]): Unit = {
    implicit val sparkSession: SparkSession = SparkInit.getSparkSession("read_large_file")
    val dataIngestion = new DataIngestion()
    val dataAggregation = new DataAggregation()
    val productDF =  dataIngestion.readCSV(sourceFiles,csvOptions)
     //create primary key DF with sku -> id
    val dfWithPrimaryKey =  DataFrameUtils.createPrimaryKeyColumn(productDF)
    //write the sku_lookup table
     dataIngestion.write(dfWithPrimaryKey,jdbcOptions,"sku_lookup",saveMode = SaveMode.Ignore,"jdbc")

     val skuLookupDF = dataIngestion.readJDBC(jdbcOptions,"sku_lookup")

    // create DF with foreign key (sku_id) by referencing sku_lookup table
    val productDFWithFkey = DataFrameUtils.createProductDFWithForeignKey(skuLookupDF,productDF)
     //write the product table to DB
    dataIngestion.write(productDFWithFkey,jdbcOptions ++ partitionOptions,"product",saveMode = SaveMode.Overwrite,"jdbc")

      // write aggregate product count by name
     val aggregatedProductDF = DataFrameUtils.createAggregateProductDF(productDF)
        dataAggregation.writeAggregate(aggregatedProductDF,tableConfig.getString("sourceTable"),
      tableConfig.getString("destinationTable"),jdbcOptions,format = "jdbc",saveMode = SaveMode.Overwrite)
  }
}
