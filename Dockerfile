FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/SpringMVCHibernate.jar /app.jar
CMD ["java","-jar","/app.jar"]
EXPOSE 2222
