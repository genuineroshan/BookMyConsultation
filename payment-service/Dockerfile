FROM openjdk:14-jdk-alpine
MAINTAINER upgrad
ADD ./target/paymentService.jar /usr/src/app/paymentService.jar
WORKDIR /usr/src/app
ENV PATH="${PATH}:${JAVA_HOME}/bin"
EXPOSE 8083
ENTRYPOINT [ "java", "-jar", "/usr/src/app/paymentService.jar"]