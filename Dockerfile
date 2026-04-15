# 1-кезең: Жобаны жинау
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2-кезең: Іске қосу
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/*.jar app.jar
# Render портты осы жерден оқиды
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]