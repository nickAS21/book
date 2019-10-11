# https://docs.docker.com/engine/reference/builder/
# The FROM instruction initializes a new build stage and sets the Base Image for subsequent instructions
FROM openjdk:11-jre
# Add VOLUME pointing to “/ tmp” because this is where the Spring Boot application creates working directories for Tomcat by default.
VOLUME /tmp
# The project JAR file is ADDed to the container as "app.jar" and then executed in the ENTRYPOINT
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
# EXPOSE <port> [<port>/<protocol>...] EXPOSE 80/tcp EXPOSE 8080/tcp EXPOSE 80/udp
EXPOSE 8080/tcp
