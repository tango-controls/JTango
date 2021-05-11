#!/bin/bash

set -e

function exit_if_null() {
    if [ -z "$1" ]
    then
      echo "$2"
      exit 1
    fi
}

function get_inputs() {
  exit_if_null "$1" "No versions supplied"
  exit_if_null "$2" "No remote URL supplied"

  RELEASE_VERSION=$1
  REMOTE_URL=$2
}

get_inputs "$@"

cp .gitlab-ci/settings.xml "${HOME}"/.m2/settings.xml
#
## update to RELEASE(TAG) VERSION
mvn versions:set versions:update-child-modules -DnewVersion="${RELEASE_VERSION}" -DprocessAllModule -DgenerateBackupPoms=false -Drelease
#
## deploy artifacts
mvn deploy -Drelease
#
#cd parent
mvn versions:set versions:update-child-modules -DprocessAllModules -DnextSnapshot -DgenerateBackupPoms=false -Drelease
#cd ..

#https://tango-ci:"${GITLAB_TOKEN}"@github.com/tango-controls/JTango.git
.gitlab-ci/push.sh "$REMOTE_URL"
