# Start with a base image containing Maven 
FROM ubuntu

ARG DEBIAN_FRONTEND=noninteractive
# Add Maintainer Info
MAINTAINER sachin 

# Creating Code repo
RUN mkdir /home/app

# Working Directory
WORKDIR /home/app

# Copying files from repo to Working Directory
RUN apt-get update 
RUN apt-get install wget -y
RUN apt-get install git -y
RUN apt-get install default-jre -y 
RUN git clone https://github.com/akeotech/devops-kubernetes.git
WORKDIR java-springboot


# Creating JAR package
RUN mvn clean package

# Add a volume 
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080
RUN wget https://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/1.18.0.RC1/elastic-apm-agent-1.18.0.RC1.jar
# The application's jar file
ARG JAR_FILE=/home/app/target/sample-websocket-demo-0.0.1.jar

# Copying JAR file
RUN cp /home/app/target/sample-websocket-demo-0.0.1.jar /sample-websocket.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sample-websocket.jar"]
