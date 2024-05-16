#!/bin/bash

set -e

currentDir=$(cd -P -- "$(dirname -- "$0")" && pwd -P)

cd "$currentDir"

index="$currentDir/frontend/index.html"

cd backend

./gradlew assemble

echo "file:///$index"

java -jar build/libs/backend-0.0.1-SNAPSHOT.jar