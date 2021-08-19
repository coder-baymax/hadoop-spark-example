package cool.baymax

import cool.baymax.Calculator._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{mean, sqrt}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

class Calculator {
  def calculate(spark: SparkSession): Unit = {
    val customSchema = StructType(Array(
      StructField("name", StringType, false),
      StructField("subject", StringType, false),
      StructField("score", IntegerType, false))
    )
    val df = spark.read.format("com.databricks.spark.csv")
      .option("delimiter", ",").schema(customSchema).load(HDFS_INPUT)
    val person = df.groupBy("name").agg(mean("score")
      .alias("average_score")).select("name", "average_score")
    val subject = df.groupBy("subject").agg(mean("score")
      .alias("average_score")).select("subject", "average_score")
    val balanced = df.withColumn("balanced_score", sqrt("score") * 10)
      .groupBy("subject").agg(mean("balanced_score")
      .alias("average_score")).select("subject", "average_score")

    person.repartition(1).write.format("com.databricks.spark.csv")
      .option("header", "true").save(HDFS_OUTPUT.format("person"))
    subject.repartition(1).write.format("com.databricks.spark.csv")
      .option("header", "true").save(HDFS_OUTPUT.format("subject"))
    balanced.repartition(1).write.format("com.databricks.spark.csv")
      .option("header", "true").save(HDFS_OUTPUT.format("balanced"))
  }
}

object Calculator {
  val HDFS_INPUT = "hdfs://host.docker.internal:9000/input"
  val HDFS_OUTPUT = "hdfs://host.docker.internal:9000/spark-output/%s"

  def main(args: Array[String]): Unit = {
    new Calculator().calculate(SparkSession.builder.getOrCreate())
  }
}
