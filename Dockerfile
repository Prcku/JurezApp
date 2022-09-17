FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/classes/sk.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]