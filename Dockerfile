FROM maven:3.6.3-jdk-14 as MAVEN_BUILD

LABEL maintainer="EM1K"

RUN mkdir -p /xymatic-back/build/

WORKDIR /xymatic-back/build/

COPY pom.xml /xymatic-back/build/pom.xml

RUN mvn dependency:go-offline

COPY src /xymatic-back/build/src/

RUN mvn clean package

ARG JAR_FILE=target/*.jar

RUN cp -R ${JAR_FILE} app.jar

FROM tomcat:10.0.0-M9-jdk14-openjdk-oraclelinux7

WORKDIR /app

EXPOSE 8080

COPY --from=MAVEN_BUILD /xymatic-back/build/app.jar /usr/local/tomcat/webapps/

RUN sh -c 'touch /usr/local/tomcat/webapps/app.jar'

ENTRYPOINT ["java","-jar","/usr/local/tomcat/webapps/app.jar"]
