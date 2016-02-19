FROM java:8
VOLUME /tmp
ADD security-user-svc-*.jar user-svc.jar
RUN bash -c 'touch /user-svc.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/user-svc.jar"]