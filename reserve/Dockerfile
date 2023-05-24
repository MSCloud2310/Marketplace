FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ENV REGISTRY_HOST=localhost \
  REGISTRY_PORT=8761 \
  SERVER_PORT=9003 \
  DATA_SOURCE_URL= \
  URL_SERVICE= \
  URL_CLIENT= \
  JAR_NAME=reserve
COPY ./target/reserve-0.0.1-SNAPSHOT.jar ${JAR_NAME}.jar
CMD java -jar ${JAR_NAME}.jar \ 
  --registry.host=${REGISTRY_HOST} \
  --registry.port=${REGISTRY_PORT} \
  --spring.datasource.password=root \
  --spring.datasource.username=root \
  --spring.datasource.url=${DATA_SOURCE_URL} \
  --server.port=${SERVER_PORT} \
  --urlService=${URL_SERVICE} \
  --urlClient=${URL_CLIENT}



