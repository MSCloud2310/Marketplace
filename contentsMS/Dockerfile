FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ENV  SERVER_PORT= \
  DATA_SOURCE_URL= \
  JAR_NAME=contenidos
COPY ./target/contenidos-0.0.1-SNAPSHOT.jar ${JAR_NAME}.jar
CMD java -jar ${JAR_NAME}.jar \ 
  --spring.datasource.password=${DATA_SOURCE_PASSWORD} \
  --spring.datasource.username=${DATA_SOURCE_USERNAME}\
  --spring.datasource.url=${DATA_SOURCE_URL} \
  --server.port=${SERVER_PORT}


