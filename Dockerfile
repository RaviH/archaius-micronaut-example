FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY target/arrchaius-example*.jar arrchaius-example.jar
CMD java ${JAVA_OPTS} -jar arrchaius-example.jar