#!/bin/bash

rm -rf /spark-output
hdfs dfs -get /spark-output /
