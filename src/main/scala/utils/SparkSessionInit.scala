package com.example
package utils

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import java.io.File

object SparkSessionInit {

  class Settings(config: Config) extends Serializable {
    val hive_exec_dynamic_partition: Boolean = config.getBoolean("hive.exec.dynamic.partition")
    val hive_exec_max_dynamic_partitions: Int = config.getInt("hive.exec.max.dynamic.partitions")
    val hive_exec_max_dynamic_partitions_pernode: Int = config.getInt("hive.exec.max.dynamic.partitions.pernode")
    val hive_exec_dynamic_partition_mode: String = config.getString("hive.exec.dynamic.partition.mode")
    val hive_warehouse_subdir_inherit_perms: Boolean = config.getBoolean("hive.warehouse.subdir.inherit.perms")
  }

  private def getConfig(filename : String) = ConfigFactory.parseFile(new File(filename))

  def initSparkSession(hiveConfigFilename: String, jobConfigFilename: String): SparkSession = {
    val hiveConfig = getConfig(hiveConfigFilename)
    val jobConfig = getConfig(jobConfigFilename)

    val sparkConf: SparkConf = new SparkConf().setAppName(jobConfig.getString("ETL.APPS.NAME"))
    val settings: Settings = new Settings(hiveConfig)
    lazy val spark: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config(sparkConf)
      .config("hive.exec.dynamic.partition", settings.hive_exec_dynamic_partition)
      .config("hive.exec.max.dynamic.partitions", settings.hive_exec_max_dynamic_partitions)
      .config("hive.exec.max.dynamic.partitions.pernode", settings.hive_exec_max_dynamic_partitions_pernode)
      .config("hive.exec.dynamic.partition.mode", settings.hive_exec_dynamic_partition_mode)
      .config("hive.warehouse.subdir.inherit.perms", settings.hive_warehouse_subdir_inherit_perms)
      .getOrCreate()

    spark

  }
}
