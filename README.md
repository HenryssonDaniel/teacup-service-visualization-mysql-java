# [User Guide](https://henryssondaniel.github.io/teacup.github.io/)
[![Build Status](https://travis-ci.com/HenryssonDaniel/teacup-service-visualization-mysql-java.svg?branch=master)](https://travis-ci.com/HenryssonDaniel/teacup-service-visualization-mysql-java)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=HenryssonDaniel_teacup-service-visualization-mysql-java&metric=coverage)](https://sonarcloud.io/dashboard?id=HenryssonDaniel_teacup-service-visualization-mysql-java)
[![latest release](https://img.shields.io/badge/release%20notes-1.0.4-yellow.svg)](https://github.com/HenryssonDaniel/teacup-service-visualization-mysql-java/blob/master/doc/release-notes/official.md)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.henryssondaniel.teacup.service.visualization/mysql.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22io.github.henryssondaniel.teacup.service.visualization%22%20AND%20a%3A%22mysql%22)
[![Javadocs](https://www.javadoc.io/badge/io.github.henryssondaniel.teacup.service.visualization/mysql.svg)](https://www.javadoc.io/doc/io.github.henryssondaniel.teacup.service.visualization/mysql)
## What ##
This project makes it possible to interact with a MySQL database containing your Teacup
visualization data.
## Why ##
The visualization back-end does not, and should not, know anything about where the data comes from.
This makes it easy to switch from one database to another.
## How ##
Follow the steps below:
1. Deploy the war file on your server  

For developers: 
1. Add plugin: id 'org.gretty' version 'x.x.x' 
1. Add dependency compile 'org.jboss.resteasy:resteasy-jaxrs:x.x.x'
1. Add dependency compile 'org.jboss.resteasy:resteasy-servlet-initializer:x.x.x'
1. Run: gradle appRun