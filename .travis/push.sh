#!/bin/sh

setup_git() {
  git config --global user.email "info@tango-controls.org"
  git config --global user.name "Tango CI"
}

commit_website_files() {
  git add . pom.xml
  git commit --message "[Tango CI] Next development iteration"
}

upload_files() {
  git push --quiet origin HEAD:$TRAVIS_BRANCH
}

setup_git
commit_website_files
upload_files
