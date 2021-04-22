#!/bin/bash

function get_remote_url() {
  if [ -z "$1" ]
  then
    echo "No remote URL supplied"
    exit 1
  fi

  REMOTE_URL=$1
}

function setup_git() {
  git config --global user.email "info@tango-controls.org"
  git config --global user.name "Tango CI"
  git remote rm origin
  git remote add origin "$REMOTE_URL"
}

function commit_website_files() {
  git add . pom.xml
  git commit --message "[Tango CI] Next development iteration"
}

function upload_files() {
  git push --quiet origin HEAD:jtango-9-lts
}

get_remote_url "$@"
setup_git
commit_website_files
upload_files
