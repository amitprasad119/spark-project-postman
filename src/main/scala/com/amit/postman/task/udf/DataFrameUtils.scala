package com.amit.postman.task.udf

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.monotonically_increasing_id

object DataFrameUtils {
  def createPrimaryKeyColumn(sourceDF:DataFrame): DataFrame = {
    val productDF =  sourceDF.na.fill("Unknown",Array("sku")).select("sku").distinct()
    productDF.withColumn("sku_id",monotonically_increasing_id())
   }

  def createProductDFWithForeignKey(skuLookUpDF:DataFrame,productDF:DataFrame):DataFrame = {
    val productDFWithoutNull =  productDF.na.fill("Unknown",Array("sku"))
    val df = skuLookUpDF.join(productDFWithoutNull, skuLookUpDF("sku") === productDFWithoutNull("sku"))
     df.select(skuLookUpDF.col("sku_id"),productDFWithoutNull.col("name"),productDFWithoutNull.col("description"))
  }

  def createAggregateProductDF(sourceDF:DataFrame)(implicit sparkSession: SparkSession):DataFrame = {
     val tempTableView = "temp_table_"+System.currentTimeMillis()
     sourceDF.createTempView(tempTableView)
     sparkSession.sql(s"SELECT count(*) as total,name FROM $tempTableView GROUP BY name ORDER BY total DESC")
    }

}
