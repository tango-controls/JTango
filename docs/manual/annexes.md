## Full sample device code

```java
package org.tango.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.ServerManager;
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.Init;

@Device
public class TestDevice {

    private final Logger logger = LoggerFactory.getLogger(TestDevice.class);
    /**
     * Attribute myAttribute READ WRITE, type DevDouble.
     */
    @Attribute
    public double myAttribute;

    /**
     * Starts the server.
     */
    public static void main(final String[] args) {
	ServerManager.getInstance().start(args, TestDevice.class);
    }

    /**
     * init device
     */
    @Init
    public void init() {
	logger.debug("init");
    }

    /**
     * delete device
     */
    @Delete
    public void delete() {
	logger.debug("delete");
    }

    /**
     * Execute command start. Type VOID-VOID
     */
    @Command
    public void start() {
	logger.debug("start");
    }

    /**
     * Read attribute myAttribute.  
     *
     * @return
     */
    public double getMyAttribute() {
	logger.debug("getMyAttribute {}", myAttribute);
	return myAttribute;
    }

    /**
     * Write attribute myAttribute
     * 
     * @param myAttribute
     */
    public void setMyAttribute(final double myAttribute) {
	logger.debug("setMyAttribute {}", myAttribute);
	this.myAttribute = myAttribute;
    }

}
```

## Command with ICommandBehavior

```java
package org.tango.test;

import org.tango.server.StateMachineBehavior;
import org.tango.server.command.CommandConfiguration;
import org.tango.server.command.ICommandBehavior;

import fr.esrf.Tango.DevFailed;

public class TestDynamicCommand implements ICommandBehavior {

    @Override
    public CommandConfiguration getConfiguration() throws DevFailed {
	final CommandConfiguration config = new CommandConfiguration();
	config.setName("testDynCmd");
	config.setInType(void.class);
	config.setOutType(double.class);
	return config;
    }

    @Override
    public Object execute(final Object arg) throws DevFailed {
	return 10.0;
    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
	return null;
    }

}
```

## Attribute with IAttributeBehavior

```java
package org.tango.test;

import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;

/**
 * A sample attribute 
 * 
 */
public class TestDynamicAttribute implements IAttributeBehavior {

    private double readValue = 0;
    private double writeValue = 0;

    /**
     * Configure the attribute
     */
    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
	final AttributeConfiguration config = new AttributeConfiguration();
	config.setName("testDynAttr");
	// attribute testDynAttr is a DevDouble
	config.setType(double.class);
	// attribute testDynAttr is READ_WRITE
	config.setWritable(AttrWriteType.READ_WRITE);
	return config;
    }

    /**
     * Read the attribute
     */
    @Override
    public AttributeValue getValue() throws DevFailed {
	readValue = readValue + writeValue;
	return new AttributeValue(readValue);
    }

    /**
     * Write the attribute
     */
    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
	writeValue = (Double) value.getValue();
    }

    /**
     * Configure state machine if needed
     */
    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
	final StateMachineBehavior stateMachine = new StateMachineBehavior();
	stateMachine.setDeniedStates(DeviceState.FAULT);
	stateMachine.setEndState(DeviceState.ON);
	return stateMachine;    
    }

    @Override
    public AttributeValue getSetValue() throws DevFailed {
	return new AttributeValue(writeValue);
    }

}
```
## Extended example
```java
package org.tango.test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.DeviceState;
import org.tango.server.ServerManager;
import org.tango.server.annotation.Attribute;
import org.tango.server.annotation.ClassProperty;
import org.tango.server.annotation.Command;
import org.tango.server.annotation.Delete;
import org.tango.server.annotation.Device;
import org.tango.server.annotation.DeviceProperties;
import org.tango.server.annotation.DeviceProperty;
import org.tango.server.annotation.DynamicManagement;
import org.tango.server.annotation.Init;
import org.tango.server.annotation.State;
import org.tango.server.annotation.StateMachine;
import org.tango.server.dynamic.DynamicManager;
import org.tango.server.testserver.JTangoTest;

import fr.esrf.Tango.DevFailed;

@Device
public class TestDevice {

    private final Logger logger = LoggerFactory.getLogger(TestDevice.class);

    /**
     * A device property
     */
    @DeviceProperty(defaultValue = "", description = "an example device property")
    private String myProp;

    @ClassProperty(defaultValue = "0", description = "an example class property")
    private int myClassProp;

    @DeviceProperties
    private Map<String, String[]> deviceProperties;

    /**
     * Attribute myAttribute READ WRITE, type DevDouble.
     */
    @Attribute
    public double myAttribute;

    /**
     * Manage dynamic attributes and commands
     */
    @DynamicManagement
    public DynamicManager dynamicManager;
    /**
     * Manage state of the device
     */
    @State
    private DeviceState state = DeviceState.OFF;

    /**
     * Starts the server.
     */
    public static void main(final String[] args) {
	ServerManager.getInstance().start(args, TestDevice.class);
    }

    public static final String NO_DB_DEVICE_NAME = "1/1/1";
    public static final String NO_DB_GIOP_PORT = "12354";
    public static final String NO_DB_INSTANCE_NAME = "1";

  
    /**
     * Starts the server in nodb mode.
     * 
     * @throws DevFailed
     */
    public static void startNoDb() {
	System.setProperty("OAPort", NO_DB_GIOP_PORT);
	ServerManager.getInstance().start(new String[] { NO_DB_INSTANCE_NAME, "-nodb", "-dlist", NO_DB_DEVICE_NAME },
		TestDevice.class);
    }
    
    /**
     * Starts the server in nodb mode with a file for device and class properties
     * 
     * @throws DevFailed
     */
    public static void startNoDbFile() throws DevFailed {
	System.setProperty("OAPort", NO_DB_GIOP_PORT);
	ServerManager.getInstance().start(
		new String[] { NO_DB_INSTANCE_NAME, "-nodb", "-dlist", NO_DB_DEVICE_NAME,
			"-file=" + JTangoTest.class.getResource("/noDbproperties.txt").getPath() }, TestDevice.class);
    }

    /**
     * init device
     * 
     * @throws DevFailed
     */
    @Init
    @StateMachine(endState = DeviceState.ON)
    public void init() throws DevFailed {
	logger.debug("myProp value = {}", myProp);
	logger.debug("myClassProp value = {}", myClassProp);
	logger.debug("deviceProperties value = {}", deviceProperties);
	// create a new dynamic attribute
	dynamicManager.addAttribute(new TestDynamicAttribute());
	// create a new dynamic command
	dynamicManager.addCommand(new TestDynamicCommand());
	logger.debug("init done");
    }

    /**
     * delete device
     * 
     * @throws DevFailed
     */
    @Delete
    public void delete() throws DevFailed {
	logger.debug("delete");
	// remove all dynamic commands and attributes
	dynamicManager.clearAll();
    }

    /**
     * Execute command start.
     */
    @Command
    @StateMachine(endState = DeviceState.RUNNING, deniedStates = DeviceState.FAULT)
    public void start() {
	logger.debug("start");
    }

    /**
     * Read attribute myAttribute.
     * 
     * @return
     */
    public double getMyAttribute() {
	logger.debug("getMyAttribute {}", myAttribute);
	return myAttribute;
    }

    /**
     * Write attribute myAttribute
     * 
     * @param myAttribute
     */
    public void setMyAttribute(final double myAttribute) {
	logger.debug("setMyAttribute {}", myAttribute);
	this.myAttribute = myAttribute;
    }

    public void setMyProp(final String myProp) {
	this.myProp = myProp;
    }

    public void setMyClassProp(final int myClassProp) {
	this.myClassProp = myClassProp;
    }

    public Map<String, String[]> getDeviceProperties() {
	return deviceProperties;
    }

    public DeviceState getState() {
	return state;
    }

    public void setState(final DeviceState state) {
	this.state = state;
    }
}
```
## Logging configuration with logback

In this example, the logging is output to the console. The underlying APIs Jacorb and ehcache will log only errors while the classes “org.tango.test” will log in debug level.  And the rest of classes will log in debug (root level). See http://logback.qos.ch/manual/configuration.html for details.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>

	<jmxConfigurator />

	<appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
		<layout class="ch.qos.logback.classic.PatternLayout">
			<pattern>%-5level %d{HH:mm:ss.SSS} [%thread - %X{deviceName}] %logger{36}.%M:%L - %msg%n</pattern>
		</layout>
	</appender>

	<logger name="jacorb" level="ERROR" />
	<logger name="net.sf.ehcache" level="ERROR" />
	<logger name="org.tango" level="ERROR" />
	<logger name="org.tango.test" level="DEBUG" />	
	
	<root level="DEBUG">
		<appender-ref ref="CONSOLE" />
	</root>

</configuration>
```

## Properties file for a device without Tango Database

```
# --- 1/1/1 properties
1/1/1->myProp:titi


CLASS/TestDevice->myClassProp: 10
```