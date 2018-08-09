#!/bin/bash

JAR_FILE='com.wgu.setcard.ump-0.1.0-SNAPSHOT.jar'

cd ..

cp ../build/libs/*.jar .

docker build --build-arg JAR_FILE=$JAR_FILE -t setcard/setcard-ump:latest .

rm *.jar