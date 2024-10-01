#!/bin/bash
mvn clean package -Dmaven.test.skip=true
if [ -f "`ls target/*.jar`" ]
then
  ps -ef | grep `ls target/*.jar` | grep "java -" | awk '{print $2}' | xargs -i kill -9 {}
  nohup java -Xms128m -Xmx256m -jar -Dspring.profiles.active=labdev `ls target/*.jar` > /home/gitlab-runner/yudian-app-log.log 2>&1 &
fi
