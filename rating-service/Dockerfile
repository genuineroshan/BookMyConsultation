FROM openjdk:14-jdk-alpine
MAINTAINER upgrad
ADD ./target/ratingService.jar /usr/src/app/ratingService.jar
WORKDIR /usr/src/app
ENV PATH="${PATH}:${JAVA_HOME}/bin"
EXPOSE 8084
ENTRYPOINT [ "java", "-jar", "/usr/src/app/ratingService.jar"]