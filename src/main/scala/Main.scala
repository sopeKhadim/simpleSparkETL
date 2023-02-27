package com.atos.cisa

import com.atos.cisa.utils.SparkSessionInit
import com.atos.cisa.utils.Logger
import com.atos.cisa.utils.SparkSessionInit.initSparkSession
import org.apache.spark.sql.SparkSession
import com.atos.cisa.utils.Utils

import java.io.File

object Main extends App {

  var hiveConfigFilename = ""
  var jobconfigFilename = ""

  val arguments = Utils.parseArgs(args)
  try {
     hiveConfigFilename = arguments.getOrElse("hiveConf", "hive_config.conf")
     jobconfigFilename =  arguments.getOrElse("jobConf", "job_config.conf")

  }catch {
    case e: Exception => {
      Logger._log.error("Number of arguments is incorrect.")
      Logger._log.error(s"You have to give the path of config files. Ex : " +
        s"run Main --hiveConf hive_config.conf --jobConf=./config/job_config.conf \n" + e.getMessage)
      throw e
    }
  }

   val  spark: SparkSession = SparkSessionInit.initSparkSession(hiveConfigFilename,jobconfigFilename)



  // Ingesting data into Hive table
  // SelectedSource.getInstance(tableName)
  }