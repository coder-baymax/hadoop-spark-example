#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

pushd "$DIR"/../hadoop/docker
docker-compose up -d
popd

pushd "$DIR"/docker
docker-compose up -d
popd

pushd "$DIR"/../data
python3 score_generator.py
popd

pushd "$DIR"/calculator
mvn clean install
popd

docker exec namenode rm -rf /app
docker cp "$DIR"/../ namenode:/app
docker exec namenode bash /app/spark/script/upload_data.sh

docker exec spark-master rm -rf /app
docker cp "$DIR"/../ spark-master:/app
docker exec spark-master bash /app/spark/script/submit_jar.sh

rm -rf "$DIR"/output
mkdir "$DIR"/output
docker exec namenode bash /app/spark/script/download_data.sh
docker cp namenode:/spark-output "$DIR"/output/spark-output