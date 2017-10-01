#!/bin/bash

./gradlew distTar

# docker build -t roguepanda/myapp .
#
# docker push roguepanda/myapp

docker build -f Dockerfile-no-key -t roguepanda/myapp .

docker push roguepanda/myapp
