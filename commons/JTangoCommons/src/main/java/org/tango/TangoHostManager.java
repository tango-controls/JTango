package org.tango;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.DevFailed;

/**
 * Management of tango host
 * 
 * @author ABEILLE
 * 
 */
public final class TangoHostManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TangoHostManager.class);
    /**
     * Host and port
     */
    public static final Map<String, String> TANGO_HOST_PORT_MAP = new HashMap<String, String>();
    /**
     * TANGO property name (TANGO_HOST)
     */
    public static final String TANGO_HOST_NAME = "TANGO_HOST";
    /**
     * host1:port1,host2:port2,...
     */
    private static final String TANGO_HOST = System.getProperty(TANGO_HOST_NAME, System.getenv(TANGO_HOST_NAME));

    private static boolean isChecked = false;

    private TangoHostManager() {
    }

    public static String getTangoHost() {
	return TANGO_HOST;
    }

    private static void checkTangoHost() throws DevFailed {
	if (!isChecked) {
	    LOGGER.debug("TANGO_HOST is {} ", TANGO_HOST);
	    if (TANGO_HOST_PORT_MAP.isEmpty()) {
		if (TANGO_HOST == null) {
		    DevFailedUtils.throwDevFailed("CONFIG_ERROR", "tango host not defined");
		}
		if (TANGO_HOST.indexOf(':') < 0) {
		    DevFailedUtils
			    .throwDevFailed("CONFIG_ERROR", "tango host not defined correctly. Must be host:port");
		}
		// Check if there is more than one Tango Host
		StringTokenizer stk;
		if (TANGO_HOST.indexOf(',') > 0) {
		    stk = new StringTokenizer(TANGO_HOST, ",");
		} else {
		    stk = new StringTokenizer(TANGO_HOST);
		}

		while (stk.hasMoreTokens()) {
		    // Get each Tango_host
		    final String th = stk.nextToken();
		    final StringTokenizer stk2 = new StringTokenizer(th, ":");
		    final String host = stk2.nextToken();
		    final String port = stk2.nextToken();
		    // System.out.println(tangoHostPortMap);
		    TANGO_HOST_PORT_MAP.put(host, port);
		}
	    }
	    isChecked = true;
	}
    }

    public static String getFirstTangoHost() throws DevFailed {
	checkTangoHost();
	final Map.Entry<String, String> entrySet = TANGO_HOST_PORT_MAP.entrySet().iterator().next();
	return entrySet.getKey() + ":" + entrySet.getValue();
    }

    public static String getFirstHost() throws DevFailed {
	checkTangoHost();
	final Map.Entry<String, String> entrySet = TANGO_HOST_PORT_MAP.entrySet().iterator().next();
	return entrySet.getKey();
    }

    public static String getFirstPort() throws DevFailed {
	checkTangoHost();
	final Map.Entry<String, String> entrySet = TANGO_HOST_PORT_MAP.entrySet().iterator().next();
	return entrySet.getValue();
    }

    public static Map<String, String> getTangoHostPortMap() {
	return new HashMap<String, String>(TANGO_HOST_PORT_MAP);
    }
}
