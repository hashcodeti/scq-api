#!/bin/bash

DIR=$(dirname $0)

cd $DIR

git pull | grep Already && exit

mvn clean package

#restart app
ps -ef | grep "SCQ-API.*.jar" | awk '{print $2}' | xargs kill -9
