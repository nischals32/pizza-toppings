FROM openjdk:17-oracle
VOLUME /tmp
COPY build/libs/toppings-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
