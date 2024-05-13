FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/quiz-back.jar /app/quiz-back.jar

EXPOSE 8081

CMD ["java", "-jar", "quiz-back.jar"]
