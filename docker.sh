#!/bin/bash

./gradlew distTar

docker build -t roguepanda/myapp .

docker push roguepanda/myapp
