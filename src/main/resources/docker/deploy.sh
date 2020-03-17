#!/bin/bash

set -x #echo on

cd ../../../../

./mvnw clean install -P docker

mv target/*.war src/main/resources/docker/api.war

cd src/main/resources/docker

docker stop wildfly-fd

docker rm -f wildfly-fd

docker build -t wildfly-fd .

rm api.war

docker run -d -p 33200:8080 -p 33201:9990 --name wildfly-fd --link oracle-xe-11g:oracle wildfly-fd
