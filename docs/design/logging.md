The logging is realized with SLF4J (http://www.slf4j.org/).  SLF4J is an abstraction layer for various logging frameworks (ie. logback, log4j, java.util.logging…).  It allows the end user to choose the logging framework at deployment time. Nevertheless, the logging configuration is framework dependent. 
Each class has its own logger. It allows a fine tuning for debugging:
```java
private final Logger logger = LoggerFactory.getLogger(DeviceImpl.class);
```

Here is an example (extract of a logback xml logging file http://logback.qos.ch/) to have debug logs for a class:
```xml
<logger name="org.tango.server.servant.DeviceImpl" level="DEBUG" />
```

Since all server code is located in packages starting by “org.tango”, it is also possible to configure like this:
```xml
<logger name="org.tango" level="DEBUG" />
```
 
The JaCORB and EHCache libraries logging can also be configured. JaCORB has only one logger:
```xml
<logger name="jacorb" level="ERROR" />
```

Please refer the logging libraries documentations for further details on their configuration.

An extension of sfl4j (class org.slf4j.ext.XLogger) is also used to log all entries and exits of methods. It will be visible if the logging level is set to “TRACE”.

## Special appenders

Some logging appenders have been added to:

* Send logs of a device to the logging device (which is provided with the tango logviewer application): the logs are sent asynchrounsly with the command “Log”.
* Send logs of a device to a file.

These appenders are configurable through commands of the administration device (AddLoggingTarget and RemoveLoggingTarget). 

As SLF4J is just an abstraction, it does not define interfaces for appenders. So the appenders have been designed by implementing interfaces of the logging API logback (http://logback.qos.ch/ ).