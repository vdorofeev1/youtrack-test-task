#!/bin/bash

set -e

currentDir=$(cd -P -- "$(dirname -- "$0")" && pwd -P)

cd "$currentDir"

cd backend

./gradlew build
java -jar build/libs/backend-0.0.1-SNAPSHOT.jar