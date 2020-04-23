#This repo is dedicated to copying files on to Dumbo and doing data analysis on the Reddit Data.

#To Copy Data:
1. run the curl_data.sh (It will copy data from the r-coronavirus_ingest repo) into a folder called reddit_Data

#To run .scala:
1. Import the data set to HDFS of your choosing
2. open a spark2 terminal
3. Modify import.scala with the HDFS destination
4. run :load import.scala
