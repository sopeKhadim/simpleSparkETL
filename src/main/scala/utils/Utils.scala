package com.atos.cisa.utils
import com.concurrentthought.cla._


object Utils {
  def parseArgs(args: Array[String] ): Args = {
    val initialArgs: Args =
      """
        |run-main CLASampleMain [options]
        |
        |Demonstrates the CLA API.
        |   -c | --hiveConf    path               Path to hive config file.
        |   -j | --jobConf     path               Path to job config file.
        |   -t | --tableName   string             Log level to use.
        |   -f | --filename    string             Filename of the data source seperated with extension.
        |  [-v | --verbose     flag]              Verbose output.
        |                      other              Other arguments.
        |Note that --hiveConf, --jobConf, --tableName, --filename and "others" are required.
        |""".stripMargin.toArgs
    initialArgs.process(args)
  }

  def show_results(parsedArgs: Args): Unit = {
    if (parsedArgs.getOrElse("quiet", false)) {
      println("(... I'm being very quiet...)")
    } else {

      // Print all the default values or those specified by the user.
      parsedArgs.printValues()

      // Print all the values including repeats.
      parsedArgs.printAllValues()

      // Repeat the "other" arguments (not associated with flags).
      println("\nYou gave the following \"other\" arguments: " +
        parsedArgs.remaining.mkString(", "))
    }

  }
  def main(args: Array[String]): Unit = {
    val strings: Array[String] = "runMain --hiveConf hive_config.conf --jobConf=./config/job_config.conf -t table4g -f FTreport_3G_PLMN_Heure2.csv".split(" ")
    parseArgs(strings)

  }
}
