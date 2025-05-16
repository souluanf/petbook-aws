FROM maven:3.9.7-eclipse-temurin-21 AS build-env
WORKDIR /app

COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn

RUN ./mvnw -B -ntp dependency:go-offline

COPY src ./src

RUN ./mvnw -B -ntp package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build-env /app/target/*.jar /app/app.jar

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]