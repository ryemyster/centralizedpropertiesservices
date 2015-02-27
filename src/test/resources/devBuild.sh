#!/bin/bash
PROJECT_HOME=/Users/ryanmcdonald/Documents/workspace/CentralizedPropertiesService
ACTIVE_MQ_HOME=/usr/local/apache-activemq-5.10.0/bin
GRADLE_HOME=/usr/local/gradle-1.12/bin

export GRADLE_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=9999,server=y,suspend=n"

#MOVE TO PROJECT HOME DIRECTORY
cd $PROJECT_HOME

#Start Active MQ
echo "Starting Active MQ..."
$ACTIVE_MQ_HOME/activemq start

#Start Redis (for stitching service only)
#debug command $>redis-cli monitor
echo "Starting Redis Database..."
nohup redis-server &

#EXECUTE GRADLE BUILD AND TEST
$GRADLE_HOME/gradle jettyRun

#FUture enhancement ... 
# trap ctrl-c and call ctrl_c() to be able to stop the redis and active mq processes.

# function called by trap
other_commands() {
    echo "\rSIGINT caught      "
    sleep 1
    echo "\rType a command >>> "
}

trap 'other_commands' SIGINT