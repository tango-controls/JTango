package org.tango.client.database;

/**
 * Tango Device export info return by the database
 * 
 * @author ABEILLE
 * 
 */
public final class DeviceExportInfo {
    /**
     * The device name.
     */
    private final String name;
    /**
     * ior connection as String.
     */
    private final String ior;
    /**
     * Host name where device will be exported.
     */
    private final String host;
    /**
     * TANGO protocol version number.
     */
    private final String version;
    /**
 * 
 */
    private final String pid;
    private final String classname;

    // public DeviceExportInfo() {
    // name = "";
    // ior = "";
    // host = "";
    // version = "";
    // pid = "";
    // classname = "";
    // }

    public DeviceExportInfo(final String name, final String ior, final String host, final String version,
	    final String pid, final String className) {
	this.name = name;
	this.ior = ior;
	this.host = host;
	this.version = version;
	this.pid = pid;
	classname = className;
    }

    // public DeviceExportInfo(final String name, final String ior, final String host, final String version) {
    // this.name = name;
    // this.ior = ior;
    // this.host = host;
    // this.version = version;
    // }

    public String[] toStringArray() {
	String[] argout;
	argout = new String[6];
	int i = 0;
	argout[i++] = name;
	argout[i++] = ior;
	argout[i++] = host;
	argout[i++] = pid;
	argout[i++] = version;
	argout[i] = classname;
	return argout;
    }
}
