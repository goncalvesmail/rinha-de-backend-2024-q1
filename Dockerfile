FROM openjdk:21-slim
MAINTAINER goncalvesmail
WORKDIR .
COPY target/*.jar rinhabackend.jar
ENTRYPOINT ["java","-jar","/rinhabackend.jar"]