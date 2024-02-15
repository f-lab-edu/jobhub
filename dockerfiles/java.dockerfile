FROM alpine:3.16.0
# Install OpenJDK 17
RUN apk add --no-cache java-cacerts openjdk17-jdk
# Install Python and pip
RUN apk add --update --no-cache python3 py3-pip
# Install Selenium
RUN pip3 install selenium
WORKDIR /app
COPY ./app/build/libs/app-1.0-SNAPSHOT.jar spring-boot-application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]

