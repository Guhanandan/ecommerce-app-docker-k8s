FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/order-service-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/orderdb
ENV SPRING_DATASOURCE_USERNAME=guhanandan
ENV SPRING_DATASOURCE_PASSWORD=Guha@6542060
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV PRODUCT_SERVICE_URL=http://localhost:5000
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=default

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]