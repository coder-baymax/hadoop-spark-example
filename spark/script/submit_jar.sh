#!/bin/bash

apk --no-cache add procps
apk --update add coreutils
mkdir /tmp/spark-events
echo -e "spark.eventLog.enabled true" > /spark/conf/spark-defaults.conf
bash /spark/sbin/start-history-server.sh

/spark/bin/spark-submit \
  --master spark://host.docker.internal:7077 \
  --deploy-mode client \
  --name spark-calculator \
  --class cool.baymax.Calculator \
  hdfs://host.docker.internal:9000/spark-calculator.jar