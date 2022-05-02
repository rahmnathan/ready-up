FROM openjdk:17-slim

RUN apt-get update && \
    apt-get -y install apt-utils software-properties-common wget tar && \
    apt-get clean

RUN groupadd ready-up && useradd ready-up -g ready-up && mkdir -p /opt/ready-up/config

ARG JAR_FILE
ADD target/$JAR_FILE /opt/ready-up/ready-up.jar

RUN chown -R ready-up:ready-up /opt/ready-up

USER ready-up

WORKDIR /opt/ready-up/
ENTRYPOINT java -jar ready-up.jar