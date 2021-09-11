FROM openjdk:11.0.12-jre-slim
copy ./target/School-Registration-0.0.1-SNAPSHOT.jar School-Registration-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","School-Registration-0.0.1-SNAPSHOT.jar"]