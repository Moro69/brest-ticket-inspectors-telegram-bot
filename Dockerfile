FROM java:8-jdk-alpine
COPY ./target/tib-tbot.war /usr/app/
WORKDIR /usr/app
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "tib-tbot.war"]