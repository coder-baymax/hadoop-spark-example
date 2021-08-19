#!/bin/bash

hdfs dfs -rm -r /person-output /subject-output /input
hdfs dfs -mkdir /input
hdfs dfs -put /app/data/scores*.csv /input

hadoop jar /app/hadoop/calculator/target/calculator-1.0.jar cool.baymax.calculator.Person /input /person-output
rm -rf /person-output
hdfs dfs -get /person-output /

hadoop jar /app/hadoop/calculator/target/calculator-1.0.jar cool.baymax.calculator.Subject /input /subject-output
rm -rf /subject-output
hdfs dfs -get /subject-output /