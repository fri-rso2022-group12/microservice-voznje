FROM maven:3.6.3-openjdk-17 AS build
COPY ./pom.xml /app/pom.xml
WORKDIR /app
RUN mvn dependency:go-offline -B
COPY ./src /app/src

ARG db_hostname
ARG db_port
ARG db_database
ARG db_username
ARG db_password

ENV DB_HOSTNAME $db_hostname
ENV DB_PORT $db_port
ENV DB_DATABASE $db_database
ENV DB_USERNAME $db_username
ENV DB_PASSWORD $db_password

RUN mvn --show-version --update-snapshots --batch-mode clean package


FROM amazoncorretto:17

RUN mkdir /app && echo $db_hostname
WORKDIR /app
COPY --from=build ./app/target/Trips-*.jar /app
EXPOSE 8080
CMD ["java", "-jar", "Trips-0.0.1-SNAPSHOT.jar"]
