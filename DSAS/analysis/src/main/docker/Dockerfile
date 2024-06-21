FROM java:8
VOLUME /tmp
COPY target/analysis-0.0.1-SNAPSHOT.jar analysis.jar
RUN bash -c "touch /analysis.jar"
EXPOSE 8021
ENTRYPOINT ["java","-jar","analysis.jar"]

#docker run -d -p 18080:8021 --name docker-analysis leesia/analysis:1.0