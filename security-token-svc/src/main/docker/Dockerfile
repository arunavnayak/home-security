FROM java:8
VOLUME /tmp
ADD security-token-svc-*.jar token-svc.jar
RUN bash -c 'touch /token-svc.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/token-svc.jar"]
EXPOSE 8080