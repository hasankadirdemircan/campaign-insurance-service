FROM maven:3.8.3-openjdk-17 AS MAVEN_BUILD

MAINTAINER Hasan Kadir Demircan

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/insurance-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "insurance-0.0.1-SNAPSHOT.jar"]