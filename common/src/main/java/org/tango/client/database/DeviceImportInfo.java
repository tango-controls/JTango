package org.tango.client.database;

import fr.esrf.Tango.DevVarLongStringArray;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Class Description: This class is an object containing the imported device information.
 *
 */
public final class DeviceImportInfo {
    /**
     * The device name.
     */
    private final String name;
    /**
     * ior connection as String.
     */
    private final String ior;
    /**
     * true if device is exported.
     */
    private final boolean exported;
    /**
     * Server PID
     */
    private final int pid;
    /**
     * Server name and intance name
     */
    private final String server;

    public DeviceImportInfo(final String name, final boolean exported, final String ior, final String server,
            final int pid) {
        this.name = name;
        this.ior = ior;
        this.exported = exported;
        this.server = server;
        this.pid = pid;
    }

    public DeviceImportInfo(final DevVarLongStringArray info) {
        name = info.svalue[0];
        ior = info.svalue[1];
        exported = info.lvalue[0] == 1;
        if (info.lvalue.length > 1) {
            pid = info.lvalue[1];
        } else {
            pid = 0;
        }
        if (info.svalue.length > 3) {
            server = info.svalue[3];
        } else {
            server = "unknown";
        }
    }

    @Override
    public String toString() {
        final ToStringBuilder strBuilder = new ToStringBuilder(this);
        strBuilder.append("IOR", ior);
        strBuilder.append("server", server);
        strBuilder.append("PID", pid);
        strBuilder.append("exported", exported);
        return strBuilder.toString();
    }

    public String getName() {
        return name;
    }

    public String getIor() {
        return ior;
    }

    public boolean isExported() {
        return exported;
    }

    public int getPid() {
        return pid;
    }

    public String getServer() {
        return server;
    }
}
