package fr.soleil.tango.util;

import org.tango.DeviceState;

import fr.esrf.Tango.DevState;

/**
 * Utility to use DevState as an enum
 * 
 * @author ABEILLE
 * @deprecated use {@link DeviceState}
 */
@Deprecated
public enum TangoState {
    ON(DevState.ON), OFF(DevState.OFF), CLOSE(DevState.CLOSE), OPEN(DevState.OPEN), INSERT(DevState.INSERT), EXTRACT(
	    DevState.EXTRACT), MOVING(DevState.MOVING), STANDBY(DevState.STANDBY), FAULT(DevState.FAULT), INIT(
	    DevState.INIT), RUNNING(DevState.RUNNING), ALARM(DevState.ALARM), DISABLE(DevState.DISABLE), UNKNOWN(
	    DevState.UNKNOWN);

    private final DevState state;

    TangoState(final DevState state) {
	this.state = state;
    }

    public DevState getDevState() {
	return state;
    }

    public static String getStringFromDevState(final DevState state) {
	String result = null;
	for (final TangoState stateName : TangoState.values()) {
	    if (state.equals(stateName.getDevState())) {
		result = stateName.toString();
		break;
	    }
	}
	return result;
	// return TangoConst.Tango_DevStateName[state.value()];
    }

    public static DevState getDevStateFromString(final String state) {
	DevState devState = null;
	for (final TangoState stateName : TangoState.values()) {
	    if (state.compareTo(stateName.toString()) == 0) {
		devState = DevState.from_int(stateName.ordinal());
		break;
	    }
	}
	return devState;
    }
}

/*
 * public static final int _ON = 0; public static final DevState ON = new
 * DevState(_ON); public static final int _OFF = 1; public static final DevState
 * OFF = new DevState(_OFF); public static final int _CLOSE = 2; public static
 * final DevState CLOSE = new DevState(_CLOSE); public static final int _OPEN =
 * 3; public static final DevState OPEN = new DevState(_OPEN); public static
 * final int _INSERT = 4; public static final DevState INSERT = new
 * DevState(_INSERT); public static final int _EXTRACT = 5; public static final
 * DevState EXTRACT = new DevState(_EXTRACT); public static final int _MOVING =
 * 6; public static final DevState MOVING = new DevState(_MOVING); public static
 * final int _STANDBY = 7; public static final DevState STANDBY = new
 * DevState(_STANDBY); public static final int _FAULT = 8; public static final
 * DevState FAULT = new DevState(_FAULT); public static final int _INIT = 9;
 * public static final DevState INIT = new DevState(_INIT); public static final
 * int _RUNNING = 10; public static final DevState RUNNING = new
 * DevState(_RUNNING); public static final int _ALARM = 11; public static final
 * DevState ALARM = new DevState(_ALARM); public static final int _DISABLE = 12;
 * public static final DevState DISABLE = new DevState(_DISABLE); public static
 * final int _UNKNOWN = 13; public static final DevState UNKNOWN = new
 * DevState(_UNKNOWN);
 * 
 * public static final String[] Tango_DevStateName = { "ON", "OFF", "CLOSE",
 * "OPEN", "INSERT", "EXTRACT", "MOVING", "STANDBY", "FAULT", "INIT", "RUNNING",
 * "ALARM", "DISABLE", "UNKNOWN" };
 */

