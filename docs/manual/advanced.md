# Around Invoke

_org.tango.server.annotation.AroundInvoke_

It defines a public void method with a single parameter of class org.tango.server.InvocationContext. It is called before and after every command and attributes execution. This functionality is known as “always executed hook” in C++.

```java
@AroundInvoke
public void aroundInvoke(final InvocationContext ctxt) {
    System.out.println("called at " + ctxt.getContext());
    System.out.println("called command or attributes " + 
			Arrays.toString(ctxt.getNames()));
}
```

# State machine

_org.tango.server.annotation.StateMachine_

The StateMachine annotation allows to define some denied states, and some state changes:

* For an __@Init__, it is possible to define the state at the end of its execution

* For a command, its execution can be disallowed for some states and the state at the end of its execution can be defined.

* For an attribute, it can be disallowed to write it for some states and the state at the end of its execution.

```java
@Attribute
@StateMachine(endState = DeviceState.RUNNING)
private double value;

@Init
@StateMachine(endState = DeviceState.OFF)
public void init() {
}

@Command
@StateMachine(deniedStates = { DeviceState.FAULT, DeviceState.UNKNOWN }, endState = DeviceState.ON)
public int on() {
  return 0;
}
```

# Device Manager

_org.tango.server.annotation.DeviceManagement_

DeviceManager contains common utilities for a device. For example, it provides its name, its admin device name, a way to change attribute properties…

```java
@DeviceManagement
private DeviceManager deviceManager;
@Init
public void init() {
   System.out.println(deviceManager.getName());
}

public void setDeviceManager(final DeviceManager deviceManager) {
   this.deviceManager = deviceManager;
}
```

# Dynamic API

Attributes and commands can be created dynamically with the class org.tango.server.dynamic.DynamicManager that will be injected by using the annotation org.tango.server.annotation.DynamicManagement. It provides methods to add or remove attributes and commands. Typically, the add methods will be called in the __@Init__ method and remove will be called in __@Delete__ method:

```java
@DynamicManagement
private DynamicManager dynamicManagement;

public void setDynamicManagement(DynamicManager dynamicManagement) {
    this.dynamicManagement = dynamicManagement;
}
@Init
public void init() throws DevFailed {
   dynamicManager.addAttribute(new TestDynamicAttribute());
   dynamicManager.addCommand(new TestDynamicCommand());
}
@Delete
public void delete() throws DevFailed {
   dynamicManager.clearAll();
}
```

>NB: If a server is running with several devices in the same process, the dynamic commands or attributes can be different for each device. 

The following paragraphs explain in details how to create attribute and commands.

## Dynamic Command

A dynamic command is a class that must implement the interface:

_org.tango.server.command.ICommandBehavior_ 

See annexes for a full sample code.

### Configuration

The method getConfiguration is used to define a command configuration like its name, its type… see javadoc of org.tango.server.command.CommandConfiguration for details). Here is an example a command called testDynCmd with no parameter and a returned value of type DEVDOUBLE: 

```java
public CommandConfiguration getConfiguration() throws DevFailed {
    final CommandConfiguration config = new CommandConfiguration();
    config.setName("testDynCmd");
    config.setInType(void.class);
    config.setOutType(double.class);
    return config;
}
```

The command types may be declared in two different ways:


* setInType(Class<?> type) or setOutType: as table in chapter “Command”, the java class defines the command type.

* setTangoInType(int tangoType)or setTangoOutType: defines the type with an integer (constants are defined in class fr.esrf.TangoConst). This method is more flexible as some Tango types do not have equivalent in Java classes: DEVULONG, DEVULONG64, DEVUSHORT, DEVVARULONGARRAY, DEVVARULONG64ARRAY, and DEVVARUSHORTARRAY.

### StateMachine

It is optional and can return “null”. It works like the StateMachine annotation. See its chapter for details.

```java
public StateMachineBehavior getStateMachine() throws DevFailed {
  final StateMachineBehavior stateMachine = new StateMachineBehavior();
  stateMachine.setDeniedStates(DeviceState.FAULT);
  stateMachine.setEndState(DeviceState.ON);
  return stateMachine;    
}
```

### Execution

The input and output types of the execute method is defined by the configuration above. If the type is void, the parameter or returned value may be null.

```java
public Object execute(final Object arg) throws DevFailed {
   return 10.0;
}
```


## Dynamic Attribute

A dynamic attribute is a class that must implement:

 _org.tango.server.attribute.IAttributeBehavior_
  
See annexes for a full sample code.

### Configuration

The method “getConfiguration” returns the full configuration of the attribute (see javadoc of org.tango.server.attribute.AttributeConfiguration for details). Here is an example for a scalar, DevDouble, READ_WRITE attribute:

```java
public AttributeConfiguration getConfiguration() throws DevFailed {
	final AttributeConfiguration config = new AttributeConfiguration();
	config.setName("testDynAttr");
	// attribute testDynAttr is a DevDouble
	config.setType(double.class);
	// attribute testDynAttr is READ_WRITE
	config.setWritable(AttrWriteType.READ_WRITE);
	return config;
}
```

The attribute type and format may be declared in two different ways:

* setType(Class<?>  type): as table in chapter “Attribute”, the java class defines the attribute type and format.

* setTangoType(int tangoType, AttrDataFormat format): defines the type with an integer (constants are defined in class fr.esrf.TangoConst). The format is defined by the class fr.esrf.AttrDataFormat. This method is more flexible as some Tango types do not have equivalent in Java classes: DEVULONG, DEVULONG64, DEVUSHORT, DEVENUM. Example of DEVENUM:

```java
final AttributePropertiesImpl props = new AttributePropertiesImpl();
props.setLabel("DevEnumDynamic");
props.setEnumLabels(new String[] { "label1", "label2" });
configAttr.setTangoType(TangoConst.Tango_DEV_ENUM, AttrDataFormat.SCALAR);
```

### StateMachine

Not mandatory, can return “null”. It works like the StateMachine annotation. See its chapter for details.

```java
public StateMachineBehavior getStateMachine() throws DevFailed {
  final StateMachineBehavior stateMachine = new StateMachineBehavior();
  stateMachine.setDeniedStates(DeviceState.FAULT);
  stateMachine.setEndState(DeviceState.ON);
  return stateMachine;    
}
```

### Read attribute

The “getValue” method is used to read the attribute.  It must return an org.tango.server.attribute.AttributeValue (see javadoc for details). Of course, the inserted value must be of the same type as the attribute type (defined in “getConfiguration”).

```java
private double readValue = 0;
private double writeValue = 0;

public AttributeValue getValue() throws DevFailed {
	readValue = readValue + writeValue;
	return new AttributeValue(readValue);
 }
```


### Write attribute

The method “setValue” will be called only if the attribute has been defined as writable in “getConfiguration”. 

```java
public void setValue(final AttributeValue value) throws DevFailed {
    writeValue = (Double) value.getValue();
}
```


### Update write part
In some specific cases, the write part has to be updated from the device (i.e.  the last set point of an equipment). This is possible by implementing the interface org.tango.server.attribute.ISetValueUpdater which has one method:

```java
public AttributeValue getSetValue() throws DevFailed {
  return new AttributeValue(writeValue);
}
```

### Forwarded Attribute

To create a forwarded attribute, just use _org.tango.server.attribute.ForwardedAttribute_:

```java
@DynamicManagement
private DynamicManager dynamicManagement;

@Init
public void init() throws DevFailed {
   dynamicManager.addAttribute(new ForwardedAttribute(fullRootAttributeName, attributeName, defaulltLabel);
}
```


# Default dynamic attributes and commands

Some default dynamic attributes and commands are already in the library JTangoServerLang, i.e.:

* Attribute and command proxies

* Group command

* Log attribute to send logs to an attribute

Example: _org.tango.server.dynamic.command.ProxyCommand_ will create a Command that is connected to another command. The input and output types will be calculated automatically.

# Events

The detailed concepts of events are described in the Tango kernel documentation. This section is just a reminder of the key concepts and how to apply it in Java.

An event is send from a device’s attribute to the clients that have subscribed to it. There are six different types of events:

* CHANGE_EVENT:  Sends an event according to the criteria defined in the attribute properties “abs_change” and/or “rel_change”. Sends also an event if the attribute‘s quality changes.

* PERIODIC_EVENT: Sends an event at the period specified by the attribute property “event_period”

* ARCHIVE_EVENT: Archived event. Can either:
  - Sends a periodic event at period configured in the property “archive_period”. 
  - Or/and change event with values from “archive_rel_change” and/or “archive_abs_change”

* USER_EVENT: The developer of the device can choose when to send this event.

* ATT_CONF_EVENT: Attribute configuration event. Sends an event if an attribute's properties change.

* DATA_READY_EVENT:  The developer of the device can choose when to send the event. It is used to notify the client that some data is ready.

* INTERFACE_CHANGE: Each time the lists of commands or attributes change, an event is fire.

There are two ways to send events from a server to clients:

* Polled events: the cache mechanism will take care of sending events.

* Pushed events: the events will be sent directly for the device’s code.

## Polled events

To send a polled event, the polling has to be configured. Only CHANGE_EVENT, PERIODIC_EVENT and ARCHIVE_EVENT can be send by the polling mechanism. Some default values can be set directly in the device’s code. In the following example, the attribute ‘doubleAtt’ is polled at a 100 milliseconds rate and will send a change event if its value varies at least of 1 since the last time it was sent:

```java
@Attribute(isPolled = true, pollingPeriod = 100)
@AttributeProperties(changeEventAbsolute = "1")
private double doubleAtt = 0;
```


## Pushed events

The event types that can be sent from the device’s code are CHANGE_EVENT, ARCHIVING_EVENT, DATA_READY_EVENT and USER_EVENT. For the CHANGE and ARCHIVING events types, it is possible to activate the check of the attribute properties criteria before firing it. In this case, it is done by the API before sending the event.

In the following example, a change event is pushed on the attribute ‘doubleAttr’. The API will check if the event must be send according to the criteria ‘changeEventAbsolute’ and ‘changeEventRelative’:

```java
@DeviceManagement
DeviceManager deviceManager;
public void setDeviceManager(final DeviceManager deviceManager) {
    this.deviceManager = deviceManager;
}
@Attribute(pushChangeEvent = true, checkChangeEvent = true)
@AttributeProperties(changeEventAbsolute = "1", changeEventRelative = "0.3")
private double doubleAttr;
…
doubleAttr++;
deviceManager.pushEvent("doubleAttr", new AttributeValue(doubleAttr), EventType.CHANGE_EVENT);
…
```

Here is an example for pushing data ready events:

```java
private int counter;

@Attribute(pushDataReady = true)
private double doubleAttr;

… 
    counter++;
…
    deviceManager.pushDataReadyEvent("doubleAttr", counter);
…   
```

Here is an example that sends a user event:

```java
@Attribute
public String getUserEvent() throws DevFailed {
        return "Hello";
}

 …
deviceManager.pushEvent("userEvent",new AttributeValue("test"), EventType.USER_EVENT);
```

# Error management

The standard exception in Tango is _fr.esrf.DevFailed_. The class _org.tango.DevFailedUtils_ is useful to throw it. It will, for instance, fill the origin field.  See javadoc for details.

```java
@Command
public int off() throws DevFailed {
  throw DevFailedUtils.newDevFailed("DEVICE_ERROR", "an example error");
}
```

# Logging
The Java Tango server API uses SLF4J (http://www.slf4j.org/). The underlying libraries use also SLF4J (i.e. jacorb, ehcache…).  Here is a declaration example of a logger class:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

…
private final Logger logger = LoggerFactory.getLogger(TestDevice.class);
```

For details about SLF4J, please refer to its documentation: http://www.slf4j.org/docs.html

SLF4J is an abstraction layer for various logging frameworks (ie. logback, log4j, java.util.logging…).  It allows the end user to choose the logging framework at deployment time. Nevertheless, the logging configuration is framework dependent. 

A configuration file allows configuring the logging output to be directed to the console, files, e-mails… It also configures the logging level. This file has to be in the class path of the device. See annexes for an example of a logback configuration file and http://logback.qos.ch/ for details about configuration.

LIMITATION: JTangoServer depends directly on logback, because it has to implement some particularities to configure it:

* Configuration of the logging level

* Configuration of logging into file or into another device (for logviewer application).

So logback may be used to benefit from the above configuration topics (accessible through the administration device).