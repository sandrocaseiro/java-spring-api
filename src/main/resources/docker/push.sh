#!/bin/bash

set -x #echo on

cd ../../../../

./mvnw clean install -P docker

mv target/*.war src/main/resources/docker/api.war

cd src/main/resources/docker

docker cp api.war wildfly-fd:/opt/jboss/wildfly/standalone/deployments/api.war

rm api.war
