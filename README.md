[![logo](http://www.tango-controls.org/static/tango/img/logo_tangocontrols.png)](http://www.tango-controls.org)



[![Build Status](https://travis-ci.org/tango-controls/JTango.svg?branch=jtango-9-lts)](https://travis-ci.org/tango-controls/JTango)
[![Documentation Status](https://readthedocs.org/projects/jtango/badge/?version=jtango-9-lts)](http://jtango.readthedocs.io/en/jtango-9-lts/?badge=jtango-9-lts)

[![Download](https://api.bintray.com/packages/tango-controls/jtango/JTango/images/download.svg) ](https://github.com/tango-controls/JTango/releases/tag/9.6.6)
[![Maven Central](https://img.shields.io/maven-central/v/org.tango-controls/JTango/9.6.0.svg?label=Maven%20Central)](https://search.maven.org/artifact/org.tango-controls/JTangoServer/9.6.0/jar)
[![Bintray](https://img.shields.io/badge/bintray-9.6.6-blue)](https://bintray.com/tango-controls/jtango/JTangoServer/_latestVersion)

[![License](https://img.shields.io/badge/license-LGPL--3.0-orange.svg)](https://github.com/tango-controls/JTango/blob/jtango-9-lts/LICENSE)

## SonarCloud

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=alert_status)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=security_rating)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)

[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=bugs)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=code_smells)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=sqale_index)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)


[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=ncloc)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=org.tango-controls%3AJTango-root&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=org.tango-controls%3AJTango-root)

# JavaDoc

You can choose the specific version of the library via drop down box after following the links below:

[![Javadocs](https://www.javadoc.io/badge/org.tango-controls/JTangoServer/9.6.6.svg?color=yellowgreen&label=JTangoServer)](https://www.javadoc.io/doc/org.tango-controls/JTangoServer/9.6.6)
[![Javadocs](https://www.javadoc.io/badge/org.tango-controls/JTangoClientLang/9.6.6.svg?label=JTangoClientLang&color=blue)](https://www.javadoc.io/doc/org.tango-controls/JTangoClientLang/9.6.6)
[![Javadocs](https://www.javadoc.io/badge/org.tango-controls/JTangoCommons/9.6.6.svg?color=yellow&label=JTangoCommons)](https://www.javadoc.io/doc/org.tango-controls/JTangoCommons/9.6.6)
[![Javadocs](https://www.javadoc.io/badge/org.tango-controls/TangORB/9.6.6.svg?color=orange&label=TangORB)](https://www.javadoc.io/doc/org.tango-controls/TangORB/9.6.6)


# JTango

TANGO kernel Java implementation. Compatible with Java 8 and onwards.

# Using JTango

## Setup using maven

To develop a Tango device server, add the following code snippet in your pom.xml:

```xml

<dependency>
    <groupId>org.tango-controls</groupId>
    <artifactId>JTangoServer</artifactId>
    <version>9.6.6</version>
</dependency>

```

To develop a Tango client, add the following code snippet in your pom.xml:

```xml

<dependency>
    <groupId>org.tango-controls</groupId>
    <artifactId>JTangoClientLang</artifactId>
    <version>9.6.1</version>
</dependency>

```

## Setup using fat jar

JTango provides fat jar file one can download from the releases page (aka JTango-9.2.7-shaded.jar). This jar includes all required class files so just add it to your class path.

## Developing Tango Java client application

//TODO

## Developing Tango Java server application

[Read-the-Docs](https://jtango.readthedocs.io/en/latest/)
