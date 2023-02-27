package com.atos.cisa
package Ingestion

import com.atos.cisa.utils.Logger
import org.apache.spark.sql.{DataFrame, SparkSession}
import com.atos.cisa.utils.SparkSessionInit

trait ExtractionTrait {

    /**
     * This function loadCsvFile() is used for loading CVS file from HDFS
     *
     * @param pathCsv
     * @param delimiter
     * @return
     */
    def read_source_file(spark : SparkSession, path: String, format : String, delimiter: String = ","): DataFrame = {

      try {
        val df = spark.read.format(format).
          option("header", "true").
          option("inferSchema", "true").
          option("delimiter", delimiter).load(path)
        df
      } catch {
        case e: Exception => {
          Logger._log.error(s"The input path define in the SMC.conf is incorrect or " +
            s"the file inserted in HDFS is outdated : " +
            s"or file format is incorrect. " + e.getMessage)
          throw e
        }
      }
    }
}