FROM openjdk:14-jdk-alpine
MAINTAINER upgrad
ADD ./target/appointmentService.jar /usr/src/app/appointmentService.jar
WORKDIR /usr/src/app
ENV PATH="${PATH}:${JAVA_HOME}/bin"
EXPOSE 8082
ENTRYPOINT [ "java", "-jar", "/usr/src/app/appointmentService.jar"]