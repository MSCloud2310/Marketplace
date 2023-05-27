FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ENV  SERVER_PORT=9003 \
  DATA_SOURCE_URL= \
  URL_SERVICE= \
  URL_CLIENT= \
  JAR_NAME=reserve
COPY ./target/reserve-0.0.1-SNAPSHOT.jar ${JAR_NAME}.jar
CMD java -jar ${JAR_NAME}.jar \ 
  --spring.datasource.password=${DATA_SOURCE_PASSWORD} \
  --spring.datasource.username=${DATA_SOURCE_USERNAME} \
  --spring.datasource.url=${DATA_SOURCE_URL} \
  --server.port=${SERVER_PORT} \
  --urlService=${URL_SERVICE} \
  --urlClient=${URL_CLIENT}


