#!/bin/sh

#
# Shell Script used to startup an application once the image is started
#

#
# Setting JAVA Options and appending any information already setup on the environment
# variables
#
JAVA_OPTIONS="${JAVA_OPTIONS} -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTIONS="${JAVA_OPTIONS} -Xdiag"

#
# Setting Classpath
#
CLASSPATH="${CLASSPATH}:/config"
CLASSPATH="${CLASSPATH}:/logs"
CLASSPATH="${CLASSPATH}:/app.jar"

mkdir /config
mkdir /logs

#
# Starting application
#
java ${JAVA_OPTIONS} -cp ${CLASSPATH} -jar app.jar
