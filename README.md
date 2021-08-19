# Example Task

Given a list of name, subject & score. Get average score of every person and subject.

## Generate Data

See data/score_generator.py to find out the data structure.

## Hadoop Mapreduce

Use https://github.com/big-data-europe/docker-hadoop to start hadoop cluster in docker.

Run hadoop/start_hadoop_job.sh to submit jobs to hadoop and download result to hadoop/output folder.

Default Ports (Can be changed in docker-compose file):

- namenode: 9870
- resourcemanager: 8088
- historyserver: 8188

## Spark

Use https://github.com/big-data-europe/docker-spark to start spark cluster in docker.

Run spark/start_spark_job.sh to submit application to spark and download result to spark/output folder.

Default Ports (Can be changed in docker-compose file):

- master: 8080
- worker: 8081
- historyserver: 18080