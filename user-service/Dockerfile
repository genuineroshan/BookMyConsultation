FROM openjdk:14-jdk-alpine
MAINTAINER upgrad
ADD ./target/userService.jar /usr/src/app/userService.jar
WORKDIR /usr/src/app
ENV PATH="${PATH}:${JAVA_HOME}/bin"
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "/usr/src/app/userService.jar"]