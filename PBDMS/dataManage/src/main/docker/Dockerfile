FROM java:8
VOLUME /tmp
COPY target/dm-0.0.1-SNAPSHOT.jar dm.jar
RUN bash -c "touch /dm.jar"
EXPOSE 8021
ENTRYPOINT ["java","-jar","dm.jar"]

#docker run -d -p 18080:8021 --name docker-analysis leesia/dm:1.0