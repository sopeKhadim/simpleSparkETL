package com.atos.cisa
package load

object LoadDateIntoTable {

  def insertIntoTable(): Nothing = ???

   def saveDataIntoHive(df : DataFrame, database : String, table : String, isPartitionTable : Boolean = true ): Unit = {

    Logger.info("-- Debug : Inserting data into table  " +table + "...")

    if (isPartitionTable) {
    val final_df = df.withColumn("temp_date", to_date(col("eventdate"), "yyyy-MM-dd"))

    // dfResult is the result of adding three fields as year, month and day for partitions to the CsvFile loaded df
    val dfResult = final_df.withColumn("year", year(col("temp_date"))).
      withColumn("month", month(col("temp_date"))).
      withColumn("day", dayofmonth(col("temp_date"))).
      drop("temp_date")
    dfResult.show()

    // insert into hive table
//    dfResult
//      .write
//      .format("parquet")
//      .option("compression","snappy")
//      .mode("append")
//      .insertInto(s"$database.$table")

    dfResult
      .write
      .partitionBy("year", "month", "day")
      .format("parquet")
      .option("compression","snappy")
      .mode("append")
      .saveAsTable(s"$database.$table")
    } else {
      df.show()
      df.write
        .format("parquet")
        .option("compression","snappy")
        .mode("overwrite")
        .saveAsTable(s"$database.$table")

    }

    Logger.info("-- Debug : Data written into partition successfully.")

  }

}
