FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/SpringMVCHibernate.jar /app.jar
CMD ["java","-jar","/app.war"]
EXPOSE 2222
