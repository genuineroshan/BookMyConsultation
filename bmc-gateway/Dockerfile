FROM openjdk:14-jdk-alpine
MAINTAINER upgrad
ADD ./target/bmcGateway.jar /usr/src/app/bmcGateway.jar
WORKDIR /usr/src/app
ENV PATH="${PATH}:${JAVA_HOME}/bin"
ENTRYPOINT [ "java", "-jar", "/usr/src/app/bmcGateway.jar"]
