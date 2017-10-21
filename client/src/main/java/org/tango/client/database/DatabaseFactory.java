package org.tango.client.database;

import fr.esrf.Tango.DevFailed;
import org.tango.TangoHostManager;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Factory to connect to the tango database device
 * 
 * @author ABEILLE
 * 
 */
public final class DatabaseFactory {
    /**
     * key: tangohost value: database
     */
    private static Map<String, ITangoDB> databaseMap = new HashMap<String, ITangoDB>();
    private static ITangoDB fileDatabase;
    private static boolean useDb = true;

    private DatabaseFactory() {

    }

    /**
     * Get the database object created for specified host and port.
     * 
     * @param host
     *            host where database is running.
     * @param port
     *            port for database connection.
     */
    public static synchronized ITangoDB getDatabase(final String host, final String port) throws DevFailed {
        final ITangoDB dbase;
        if (useDb) {
            final String tangoHost = host + ":" + port;
            // Search if database object already created for this host and port
            if (databaseMap.containsKey(tangoHost)) {
                return databaseMap.get(tangoHost);
            }
            // Else, create a new database object
            dbase = new Database(host, port);
            databaseMap.put(tangoHost, dbase);
        } else {
            dbase = fileDatabase;
        }
        return dbase;
    }

    /**
     * Get the database object using tango_host system property.
     * 
     * @return
     * @throws DevFailed
     */
    public static synchronized ITangoDB getDatabase() throws DevFailed {
        ITangoDB tangoDb = null;
        if (useDb) {
            final String tangoHost = TangoHostManager.getFirstTangoHost();
            // Search if database object already created for this host and port
            if (databaseMap.containsKey(tangoHost)) {
                tangoDb = databaseMap.get(tangoHost);
            } else {
                // Else, create a new database object
                DevFailed lastError = null;
                // try all tango hosts until connection is established
                final Map<String, String> tangoHostMap = TangoHostManager.getTangoHostPortMap();
                for (final Entry<String, String> entry : tangoHostMap.entrySet()) {
                    try {
                        lastError = null;
                        tangoDb = new Database(entry.getKey(), entry.getValue());
                        databaseMap.put(tangoHost, tangoDb);
                        break;
                    } catch (final DevFailed e) {
                        lastError = e;
                    }
                }
                if (lastError != null) {
                    throw lastError;
                }
            }
        } else {
            tangoDb = fileDatabase;
        }
        return tangoDb;
    }

    public static boolean isUseDb() {
        return useDb;
    }

    /**
     * Build a mock tango db with a file containing the properties
     * 
     * @param dbFile
     * @param devices
     * @param classes
     * @throws DevFailed
     */
    public static void setDbFile(final File dbFile, final String[] devices, final String className) throws DevFailed {
        DatabaseFactory.useDb = false;
        DatabaseFactory.fileDatabase = new FileTangoDB(dbFile, Arrays.copyOf(devices, devices.length), className);
    }

    /**
     * Build a mock tango db
     * 
     * @param devices
     * @param classes
     */
    public static void setNoDbDevices(final String[] devices, final String className) {
        DatabaseFactory.useDb = false;
        DatabaseFactory.fileDatabase = new FileTangoDB(Arrays.copyOf(devices, devices.length), className);
    }

    public static void setUseDb(final boolean useDb) {
        DatabaseFactory.useDb = useDb;
    }
}
