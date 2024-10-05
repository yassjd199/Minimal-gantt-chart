FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-17 -y
COPY . .
RUN ./gradlew bootjar --no-daemon