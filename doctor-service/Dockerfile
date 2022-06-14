FROM openjdk:14-jdk-alpine
MAINTAINER upgrad
ADD ./target/doctorService.jar /usr/src/app/doctorService.jar
WORKDIR /usr/src/app
ENV PATH="${PATH}:${JAVA_HOME}/bin"
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/usr/src/app/doctorService.jar"]