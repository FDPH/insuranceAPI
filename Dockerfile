FROM openjdk:17-jdk

WORKDIR /app

COPY target/insuranceAPI-0.0.1-SNAPSHOT.jar /app/insuranceAPI-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "insuranceAPI-0.0.1-SNAPSHOT.jar"]