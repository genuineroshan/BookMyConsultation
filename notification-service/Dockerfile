FROM openjdk:14-jdk-alpine
MAINTAINER upgrad
ADD ./target/notificationService.jar /usr/src/app/notificationService.jar
WORKDIR /usr/src/app
ENV PATH="${PATH}:${JAVA_HOME}/bin"
EXPOSE 8085
ENTRYPOINT [ "java", "-jar", "/usr/src/app/notificationService.jar"]