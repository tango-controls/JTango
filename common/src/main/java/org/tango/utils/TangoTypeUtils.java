package org.tango.utils;

import org.tango.attribute.AttributeTangoType;
import org.tango.command.CommandTangoType;

import fr.esrf.Tango.DevFailed;

public final class TangoTypeUtils {
    private TangoTypeUtils() {

    }

    public static int getAttributeType(final String string) throws DevFailed {
	AttributeTangoType result = null;
	try {
	    result = AttributeTangoType.valueOf(string.toUpperCase());
	} catch (final IllegalArgumentException e) {
		throw DevFailedUtils.newDevFailed("TYPE_ERROR", string + " is not supported");
	}
	return result.getTangoIDLType();
    }

    public static int getCommandType(final String string) throws DevFailed {
	CommandTangoType result = null;
	try {
	    result = CommandTangoType.valueOf(string.toUpperCase());
	} catch (final IllegalArgumentException e) {
		throw DevFailedUtils.newDevFailed("TYPE_ERROR", string + " is not supported");
	}
	return result.getTangoIDLType();
    }

}
