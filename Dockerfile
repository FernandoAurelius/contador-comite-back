FROM container-registry.oracle.com/java/jdk:21 AS build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests

RUN mkdir -p target/extracted && \
    java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

FROM container-registry.oracle.com/java/jdk:21-slim
WORKDIR /app

COPY --from=build /workspace/app/target/extracted/dependencies/ ./
COPY --from=build /workspace/app/target/extracted/spring-boot-loader/ ./
COPY --from=build /workspace/app/target/extracted/snapshot-dependencies/ ./
COPY --from=build /workspace/app/target/extracted/application/ ./

ENV PORT=8080
EXPOSE ${PORT}

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]