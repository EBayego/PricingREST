FROM openjdk:21-jdk

WORKDIR /app

COPY target/pricing-service.jar /app/pricing-service.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/pricing-service.jar"]