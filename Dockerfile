# Этап 1 - сборка проекта в jar
FROM maven:3.8-openjdk-17 as maven
WORKDIR /app
COPY . /app
RUN mvn package -Dmaven.test.skip=true

# Этап 2 - запуск проекта
FROM openjdk:17.0.2-jdk
WORKDIR /app
COPY --from=maven /app/target/sweater-1.0.0.jar app.jar
CMD java -jar app.jar