#!/bin/bash

set -e

cp .travis/settings.xml $HOME/.m2/settings.xml

mvn deploy -Dmaven.test.skip=true -Prelease

cd parent
mvn versions:set versions:update-child-modules -DprocessAllModules -DnextSnapshot -DgenerateBackupPoms=false
cd ..

.travis/push.sh
