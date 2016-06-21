#!/usr/bin/env bash

mvn clean package
cp -u -r target/sonar-example-plugin-0.1-SNAPSHOT.jar B:/Users/Mathias/Documents/SonarQube/sonarqube-5.6/extensions/plugins/sonar-example-plugin-0.1-SNAPSHOT.jar
echo -e "\nCopied stuff"
