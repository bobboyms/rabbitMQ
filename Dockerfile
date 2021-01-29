FROM openjdk:11
# FROM openjdk:11-jdk-alpine
# COPY . /usr/src/myapp
COPY build/libs/record-0.0.1-SNAPSHOT.jar /app.jar
# ENV JAVA_OPTS="-Xmx:256m"
# RUN echo JAVA_OPTS
#WORKDIR /usr/src/myapp
#RUN javac Main.java
# CMD ["java", "Main"]
CMD ["java", "-jar", "/app.jar"]