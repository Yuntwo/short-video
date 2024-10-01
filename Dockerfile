FROM hub.c.163.com/library/java:8-alpine

ADD target/*.jar app.jar

VOLUME /tmp

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

EXPOSE 8091

ENTRYPOINT ["java", "-jar","-Xms1024m","-Xmx1024m","-Dspring.profiles.active=aliyunprod","/app.jar","> /log/yudian.log &"]
#CMD nohup java -Xms1024m -Xmx1024m -Dspring.profiles.active=labdev -jar app.jar > /log/yudian.log 2>&1 &