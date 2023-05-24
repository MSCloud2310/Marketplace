FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ENV REGISTRY_HOST= \
  REGISTRY_PORT=8761 \
  SERVER_PORT= \
  DATA_SOURCE_URL= \
  JAR_NAME=contenidos
COPY ./target/contenidos-0.0.1-SNAPSHOT.jar ${JAR_NAME}.jar
CMD java -jar ${JAR_NAME}.jar \ 
  --registry.host=${REGISTRY_HOST} \
  --registry.port=${REGISTRY_PORT} \
  --spring.datasource.password=root \
  --spring.datasource.username=root \
  --spring.datasource.url=${DATA_SOURCE_URL} \
  --server.port=${SERVER_PORT}


