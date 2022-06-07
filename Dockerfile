#########################################
# Packaged spring boot app using maven
#########################################
#FROM openjdk:16-jdk-alpine as as builder
FROM maven:3.6-openjdk-17-slim as builder

RUN mkdir -p /app
WORKDIR /app
ADD pom.xml .
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn package -DskipTests

ENTRYPOINT ["java","-jar","/app.jar"]

FROM openjdk:17-jdk-alpine as runner
ENV MYSQL_DATABASE=url_compress
ENV MYSQL_USER=root
ENV MYSQL_ROOT_PASSWORD=mypass
ENV MYSQL_URL=jdbc:mysql://localhost:3306/${MYSQL_DATABASE}
ENV SERVER_PORT=8080
ENV HIBERNATE_DDL_AUTO=update
COPY --from=builder /app/target/*.jar /app.jar
EXPOSE ${SERVER_PORT}



