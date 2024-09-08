package com.example
package Ingestion

import org.apache.spark.sql.{DataFrame, SparkSession}
import com.typesafe.config.{Config, ConfigFactory}

class  ExtractData(val config : Config) extends ExtractionTrait {

   val path: String = config.getString("ETL.INPUT.HDFS.SOURCE.PATH")
   val filename: String = config.getString("ETL.INPUT.HDFS.SOURCE.FILENAME")
   val format: String = config.getString("ETL.INPUT.HDFS.SOURCE.FORMAT")
   val delimiter: String = config.getString("ETL.INPUT.HDFS.SOURCE.DELIMITER")
   private val completePath = path.concat("/").concat(filename).concat(".").concat(format)

  def extract_data(spark : SparkSession): DataFrame = super.read_source_file(spark,
                                                                             completePath,
                                                                             format,
                                                                             delimiter)




}
