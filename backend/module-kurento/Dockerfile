FROM openjdk:11\
ENV JAVA_OPTS='-Dkms.url=ws://i8c205.p.ssafy.io:8888/kurento'
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8086
ENTRYPOINT java $JAVA_OPTS -jar /app.jar

