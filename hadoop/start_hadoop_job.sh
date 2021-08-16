#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

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
docker exec namenode bash /app/hadoop/script/submit_jar.sh

rm -rf "$DIR"/output
mkdir "$DIR"/output
docker cp namenode:/person-output "$DIR"/output/person-output
docker cp namenode:/subject-output "$DIR"/output/subject-output