FROM gradle:6.0.1-jdk11

WORKDIR /usr/src/app
COPY . .
RUN gradle wrapper --parallel

CMD [ "./gradlew", "run"]
