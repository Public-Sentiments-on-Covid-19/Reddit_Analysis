gimport org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{StringType, IntegerType, DoubleType}

val spark = org.apache.spark.sql.SparkSession.builder.master("local").appName("Spark CSV Reader").getOrCreate;

val df = (spark.read.format("csv")
        .option("header", "true")
        .option("multiline","true")
        .option("mode", "DROPMALFORMED")
        //loads ALL data
        .load("reddit_Data_H/reddit_Data/*.csv")
        //loads smaller sample data (1-20, 1-21, 3-18)
        // .load("reddit_Data_H/*.csv")
        )

var splitDF = (df.withColumn("Day",split(col("Publish Date")," ").getItem(0))
            .withColumn("Time",split(col("Publish Date")," ").getItem(1))
            .withColumn("Title_VS",col("Title_VS").cast(DoubleType))
            .drop("Publish Date"))
// splitDF.show()

val titleDF = splitDF.select("Title_VS", "Title","Day","Time").dropDuplicates()
// titleDF.show(100)

//Get how many (qualifying - 100 upvotes) posts are posted each day
val postCountsDayDF = (titleDF.groupBy("Day")
    .count())
postCountsDayDF.show(100)

//get the average qualifying post title sentiments each day
val averagePostVSDayDF = (titleDF.groupBy("Day")
    .avg("Title_VS"))
averagePostVSDayDF.show(100)
