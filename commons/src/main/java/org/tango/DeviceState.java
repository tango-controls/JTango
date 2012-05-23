package org.tango;

import fr.esrf.Tango.DevState;

/**
 * Utility to use {@link DevState} as an enum
 * 
 * @author ABEILLE
 * 
 */
public enum DeviceState {
    ON(DevState.ON), OFF(DevState.OFF), CLOSE(DevState.CLOSE), OPEN(DevState.OPEN), INSERT(DevState.INSERT), EXTRACT(
	    DevState.EXTRACT), MOVING(DevState.MOVING), STANDBY(DevState.STANDBY), FAULT(DevState.FAULT), INIT(
	    DevState.INIT), RUNNING(DevState.RUNNING), ALARM(DevState.ALARM), DISABLE(DevState.DISABLE), UNKNOWN(
	    DevState.UNKNOWN);

    private final DevState state;

    DeviceState(final DevState state) {
	this.state = state;
    }

    public DevState getDevState() {
	return state;
    }

    /**
     * convert a {@link DevState} to a {@link DeviceState}
     * 
     * @param state
     * @return
     */
    public static DeviceState getDeviceState(final DevState state) {
	DeviceState result = null;
	for (final DeviceState stateName : DeviceState.values()) {
	    if (state.equals(stateName.getDevState())) {
		result = stateName;
		break;
	    }
	}
	return result;
    }

    /**
     * Convert {@link DevState} to a string
     * 
     * @param state
     * @return
     */
    public static String toString(final DevState state) {
	String result = null;
	for (final DeviceState stateName : DeviceState.values()) {
	    if (state.equals(stateName.getDevState())) {
		result = stateName.toString();
		break;
	    }
	}
	return result;
    }

    /**
     * Convert a string to a {@link DevState}
     * 
     * @param state
     * @return
     */
    public static DevState toDevState(final String state) {
	DevState devState = null;
	for (final DeviceState stateName : DeviceState.values()) {
	    if (state.compareTo(stateName.toString()) == 0) {
		devState = DevState.from_int(stateName.ordinal());
		break;
	    }
	}
	return devState;
    }
}
