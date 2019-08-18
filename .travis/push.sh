#!/bin/sh

setup_git() {
  git config --global user.email "info@tango-controls.org"
  git config --global user.name "Tango CI"
  git remote rm origin
  git remote add origin https://tango-ci:$GITHUB_TOKEN@github.com/tango-controls/JTango.git
}

commit_website_files() {
  git add . pom.xml
  git commit --message "[Tango CI] Next development iteration"
}

upload_files() {
  git push --quiet
}

setup_git
commit_website_files
upload_files
