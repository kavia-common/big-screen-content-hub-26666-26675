#!/bin/bash
cd /home/kavia/workspace/code-generation/big-screen-content-hub-26666-26675/smart_search_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

