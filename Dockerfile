FROM tomcat:9-jdk17-openjdk-slim

RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/REST-Service.war /usr/local/tomcat/webapps/app.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
