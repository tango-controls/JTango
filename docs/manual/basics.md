# Device

A Tango device class must have the following Java annotation:

 _org.tango.server.annotation.Device_

```java
@Device
public class TestDevice {
}
```

This class can only have a no-arguments constructor.

This annotation has an option to configure how the server will manage client transactions. Default value is “NONE”. Here is an example for one client request at a time per device:

```java
@Device(transactionType = TransactionType.DEVICE)
```

All transaction values are:
* TransactionType.DEVICE: One client request per device. 
* TransactionType.CLASS: One client request per device class (that may contain several devices). 
* TransactionType.SERVER: One client request per server (that may contain several classes). 
* TransactionType.ATTRIBUTE: One client request per attribute.
* TransactionType.COMMAND: One client request per command.    
* TransactionType.ATTRIBUTE_COMMAND: One client request per attribute or command.
* TransactionType.NONE: Default value.  All client requests can be done at the same time.

> NB: A good choice has to be made between performance and thread-safety of the device depending of the use-cases: 
> 
> * Using TransactionType.NONE means that several clients can modify values, states in the device at the same time. In this case, the developer has to implement the thread-safety by himself if necessary. A good use case for this configuration is a “stateless” device where each request is an independent transaction that is unrelated to any previous request.
> * Using TransactionType.DEVICE means that only one client can do a request on the device at a time. So, if a lot of clients are connected to the device, their performance can be drastically reduced while waiting for other clients. The main use case for this configuration is a “statefull” device that contains a conversation state that is retained across transactions.

# Command

A tango command is created with this Java annotation on a method:

 _org.tango.server.annotation.Command_
 
Example code of a command with a parameter of type DEVVARDOUBLEARRAY and a returned type of DEVLONG:

```java
@Command
public int testCmd(final double[] in){
    return 0;
}
```

The command name is by default the method name. The Command annotation has some parameters to change its name, its description, its polling configuration… See javadoc for details.

The method has to be public.

The input and output types are defined by the method definition. Here are the Tango types for each Java type:

 Java type | Tango type 
------------|-----------
void      | DEVVOID
boolean   |       DEVBOOLEAN
long |     DEVLONG64
long[] | DEVVARLONG64ARRAY
short | DEVSHORT
short[] | DEVVARSHORTARRAY
float | DEVFLOAT
float[] | DEVVARFLOATARRAY
double | DEVDOUBLE
double[] | DEVVARDOUBLEARRAY
String | DEVSTRING
String[] | DEVVARSTRINGARRAY
int | DEVLONG
Int[] | DEVVARLONGARRAY
DevState or DeviceState | DEVSTATE
byte | DEVUCHAR
byte[] | DEVVARCHARARRAY
DevEncoded | DEVENCONDED
DevVarLongStringArray | DEVVARLONGSTRINGARRAY
DevVarDoubleStringArray | DEVVARDOUBLESTRINGARRAY
	
> NB: Full class names of Tango commands:
>
> - fr.esrf.Tango.DevState
> - fr.esrf.Tango.DevEncoded
> - fr.esrf.Tango.DevVarLongStringArray
> - fr.esrf.Tango.DevVarDoubleStringArray
> - org.tango.DeviceState

Tango provides also other types that do not have equivalent in Java types: DEVULONG, DEVULONG64, DEVUSHORT, DEVVARULONGARRAY, DEVVARULONG64ARRAY, DEVVARUSHORTARRAY. It is possible to define these types with a dynamic command (Cf chapter dynamic API for details).

>NB: The wrappers objects of primitives (Integer, Double…) can also be used, but it could lead to performance issues.

# Attribute

A tango attribute is created with this Java annotation on a method or a field:
 
 _org.tango.server.annotation.Attribute_

Example code of a DEVDOUBLE scalar read and write attribute:

```java
@Attribute
private double testAttribute;

public doube getTestAttribute(){
    return teastAttribute;
}

public void setTestAttribute(double v){
    this.testAttribute = v;
}
```

Example code for DEVENUM attribute:

```java
public enum TestType{
    VALUE1, VALUE2
}

@Attribute
private TestType enumAttribute = TestType.VALUE1;

public TestType getEnumAttribute(){
    return enumAttribute;
}

public void setEnumAttribute(TestType v){
    this.enumAttribute = v;
}
```

As defined by the Java bean convention, the setter and getter must contain the name of the field and manage the same type as the field (reminder: a getter for a boolean starts by “is”). The getter and setter have to be public while the field is private.

* If this field has a getter, it is a READ attribute; 

* If it has a setter, it is a WRITE attribute; 

* If it has both, it is a READ/WRITE attribute.

It is also possible to place the annotation on the getter method.

The attribute name is by default the field name. The annotation has some parameters to change its name, its polling configuration, its memorization configuration… See javadoc for details.

Here are the Tango types for each Java type:

Java type | Tango type | Tango format
----------|------------|-------------
Boolean   | DEVBOOLEAN | SCALAR
boolean[] | DEVBOOLEAN | SPECTRUM
boolean[][] | DEVBOOLEAN | IMAGE
Long | DEVLONG64 | SCALAR
long[] | DEVLONG64 | SPECTRUM
long[][] | DEVLONG64 | IMAGE
Short | DEVSHORT | SCALAR
short[] | DEVSHORT | SPECTRUM
short[][] | DEVSHORT | IMAGE
Float | DEVFLOAT | SCALAR
float[] | DEVFLOAT | SPECTRUM
float[][] | DEVFLOAT | IMAGE
Double | DEVDOUBLE | SCALAR
double[] | DEVDOUBLE | SPECTRUM
double[][] | DEVDOUBLE | IMAGE
String | DEVSTRING | SCALAR
String[] | DEVSTRING | SPECTRUM
String[][] | DEVSTRING | IMAGE
Int | DEVLONG | SCALAR
int[] | DEVLONG | SPECTRUM
int[][] | DEVLONG | IMAGE
DevState or DeviceState | DEVSTATE | SCALAR
DevState[] or DeviceState[] | DEVSTATE | SPECTRUM
DevState[][] or DeviceState[][] | DEVSTATE | IMAGE
Byte | DEVUCHAR | SCALAR
byte[] | DEVUCHAR | SPECTRUM
byte[][] | DEVUCHAR | IMAGE
DevEncoded | DEVENCONDED | SCALAR
Enum | DEVENUM | SCALAR

> NB: Full class names of tango attributes:
>
> - fr.esrf.Tango.DevState
> - fr.esrf.Tango.DevEncoded
> - fr.esrf.Tango.DevVarLongStringArray
> - fr.esrf.Tango.DevVarDoubleStringArray
> - org.tango.DeviceState

Tango provides also other types that do not have equivalent in Java types: DEVULONG, DEVULONG64, and DEVUSHORT, DEVENUM (with enumerated value configurable from its attribute property). It is possible to define these types with a dynamic attribute (Cf chapter dynamic API for details). Please also refer to this section if the write part of the attribute has to be changed from the device.

> NB: The wrappers objects of primitives (Integer, Double…) can also be used, but it could lead to performance issues.

A Tango attribute has also a quality and a timestamp. The default behavior is a valid quality, and the timestamp is the read time.  To access these properties, the getter method can return a container for the attribute value, quality and timestamp. The container is: org.tango.server.attribute.AttributeValue. It contains constructors and methods to set the value, quality and timestamp. Please refer to its javadoc for details.

```java
@Attribute
private double myAttribute;

public AttributeValue getMyAttribute(){
    AttributeValue value = new AttributeValue(myAttribute);
    value.setQuality(AttrQuality.ATTR_CHAMGING);
    value.setTime(System.currentTimeMillis());
    return value;
}
```

The default attribute properties are configurable with this annotation:

 _org.tango.server.annotation.AttributeProperties_

Please refer to javadoc for details. Example:

```java
@Attribute
@AttributeProperties(format = "%6.4f", description = "a test attribute")
private double testAttribute;
```

# Pipe

A tango pipe is created with this Java annotation on a method or a field:

 _org.tango.server.annotation.Pipe_
 
Example code of a read pipe:

```java
@Pipe
private PipeValue myPipeRO;
…
final PipeBlob myPipeBlob = new PipeBlob("A");
myPipeBlob.add(new PipeDataElement("C", "B"));
myPipeRO = new PipeValue(myPipeBlob);
…
public PipeValue getMyPipeRO() {return myPipeRO;}
```

# Init

_org.tango.server.annotation.Init_

```java
@Init
public void init() {
}
```

This method must be public with no parameters. It is called:

* At server startup 

* And when “Init” command is called. 

If this method throws an exception, the device will automatically switch to the “FAULT” state and the status will provide the stack trace.

This annotation has a boolean option called “lazyLoading”. Its default value is false. If the init method takes a lot a time, its execution can be detached with this option set to true. The device will automatically switch in state “INIT” during its execution. This option avoids timeouts when executing the “Init” command as well as a rapid device startup and consequently a rapid control system startup.

# Delete

_org.tango.server.annotation.Delete_

```java
@Delete
public void delete() {
}
```

Method must be public with no parameters. It is called:

* When “Init” command is called before @Init method 

* At server shutdown. 

The delete method is generally used to close resources.

# State

_org.tango.server.annotation.State_

```java
@State
private DeviceState state;

public DeviceState getState() {
  return state;
}

public void setState(final DeviceState state) {
   this.state = state;
}
```

The state annotation defines the state of the device, which will appear in the default command and attribute “State”. The field can be fr.esrf.Tango.DevState or org.tango.DeviceState:

* DevState is the Tango standard type defined by the IDL. 

* DeviceState is java Enum that provides easiness to manage a State.

Getter and setter are mandatory.

The device property “StateCheckAttrAlarm” is defined for all Java devices. If set to true, each times a client request the state or the status of the device, all attributes are read to check if some attributes are in ALARM or WARNING quality. If alarms are detected, the state and the status will be updated consequently. The default value of this property is false. WARNING: if some attributes requests are slow, it could lead to performance issues. 

# Status

_org.tango.server.annotation.Status_

```java
@Status
private String status;

public String getStatus() {
   return status;
}

public void setStatus(String status) {
   this.status = status;
}
```

The status annotation defines the status of the device, which will appear in the default command and attribute “Status”. The status field must be a String, getter and setter are mandatory.

# Device property

_org.tango.server.annotation.DeviceProperty_
>NB: Tango reminder:  loading order of a device property:
> * Value defined at device level
> * If does not exists; value defined at class level
> * If does not exists; default value

```java
@DeviceProperty (defaultValue = "", description = "an example")
private String devicePropTest;

public void setDevicePropTest(String devicePropTest) {
   this.devicePropTest = devicePropTest;
}
```

The field can be of any standard java type (int, double …), as scalar or array. 

The property has some parameters, details are in javadoc. 

A setter is mandatory, so that the value can be injected at device initialization.

# Device properties

_org.tango.server.annotation.DeviceProperties_

It is possible to retrieve all device properties at once. It can be useful if some device properties are not known in advance (Example: some dynamic attributes that have their names as a device property name).

```java
@DeviceProperties
private Map<String, String[]> devicePropTest;

public void setDevicePropTest(final Map<String, String[]> devicePropTest) {
  this.devicePropTest = devicePropTest;
}
```

The field has to be a java.util.Map with a “String” key and a “String[]” value.

A setter is mandatory, so that the value can be injected at device initialization.

# Class property

_org.tango.server.annotation.ClassProperty_

```java
@ClassProperty
private double[] classPropTest;

public void setClassPropTest(double[] classPropTest) {
   this.classPropTest = classPropTest;
}
```

The field can be of any standard java type (int, double …), as scalar or array.

The property has some parameters, details are in javadoc. 

A setter is mandatory, so that the value can be injected at device initialization.

