# Используем официальный образ Tomcat с JDK 17
FROM tomcat:9-jdk17-openjdk-slim

# Очищаем директорию webapps и копируем наш WAR-файл туда
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/REST-Service.war /usr/local/tomcat/webapps/app.war

# Открываем порт 8080 для веб-сервера
EXPOSE 8080

# Команда по умолчанию запускает Tomcat
CMD ["catalina.sh", "run"]
