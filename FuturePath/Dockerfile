FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /app  
COPY FuturePath/pom.xml .  
RUN mvn dependency:go-offline  
COPY FuturePath/. . 
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /app/target/FuturePath-0.0.1-SNAPSHOT.jar FuturePath.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "FuturePath.jar"]