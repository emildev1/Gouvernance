FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/SpringMVCHibernate.war /app.war
CMD ["java","-war","/app.war"]
EXPOSE 2222
