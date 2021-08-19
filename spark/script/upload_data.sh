#!/bin/bash

hdfs dfs -rm -r /input /spark-output /eventLog /spark-calculator.jar
hdfs dfs -mkdir /input /spark-output /eventLog
hdfs dfs -put /app/data/scores*.csv /input
hdfs dfs -put /app/spark/calculator/target/calculator-1.0.jar /spark-calculator.jar
