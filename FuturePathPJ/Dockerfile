FROM jelastic/maven:3.9.5-openjdk-21 AS build
WORKDIR /app  
COPY FuturePathPJ/pom.xml .  
RUN mvn dependency:go-offline  
COPY FuturePathPJ/. . 
RUN mvn clean package -DskipTests

FROM openjdk:21-slim
COPY --from=build /app/target/FuturePathPJ-0.0.1-SNAPSHOT.jar FuturePathPJ.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "FuturePathPJ.jar"]