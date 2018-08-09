FROM openjdk:8-jdk

ENV DB_HOST 127.0.0.1

ARG JAR_FILE

VOLUME /tmp

RUN mkdir setcard

COPY ${JAR_FILE} setcard/ump.jar

ENTRYPOINT ["/bin/bash", "-c", "java -Dspring.data.mongodb.uri=mongodb://$DB_HOST/SetCard -Djava.security.egd=file:/dev/./urandom -jar /setcard/ump.jar"]