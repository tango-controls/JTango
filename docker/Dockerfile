FROM openjdk:8

WORKDIR /usr/local/lib

COPY JTangoServer-*.jar JTangoServer.jar
COPY entrypoint.sh .

# DEFAULT DEVICE SERVER
ENV DEVICE_SERVER_CLASS org.tango.server.testserver.TestDevice

ENTRYPOINT ["./entrypoint.sh"]