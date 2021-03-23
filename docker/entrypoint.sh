#!/bin/bash

echo "Running the command java -DTANGO_HOST='${TANGO_HOST}' -cp '${DEVICE_SERVER_CLASS}' to start the Device Server"

java -DTANGO_HOST="${TANGO_HOST}" -cp JTangoServer.jar "${DEVICE_SERVER_CLASS}"
