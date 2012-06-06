package fr.soleil.tango.statecomposer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.StateUtilities;

/**
 * this class is not synchronized.
 * 
 * @author GRAMER
 * 
 */
public final class PriorityStateManager {
    /**
     * the user defined priorities between states
     */
    private final Map<DevState, Integer> definedPriorityMap = new HashMap<DevState, Integer>();
    /**
     * the states of monitored devices
     */
    private final Map<String, DevState> deviceStateMap = new LinkedHashMap<String, DevState>();

    public PriorityStateManager() {
	definedPriorityMap.put(DevState.ON, 0);
	definedPriorityMap.put(DevState.OFF, 1);
	definedPriorityMap.put(DevState.OPEN, 0);
	definedPriorityMap.put(DevState.CLOSE, 1);
	definedPriorityMap.put(DevState.INSERT, 0);
	definedPriorityMap.put(DevState.EXTRACT, 1);
	definedPriorityMap.put(DevState.STANDBY, 2);
	definedPriorityMap.put(DevState.INIT, 3);
	definedPriorityMap.put(DevState.MOVING, 4);
	definedPriorityMap.put(DevState.RUNNING, 4);
	definedPriorityMap.put(DevState.DISABLE, 5);
	definedPriorityMap.put(DevState.ALARM, 6);
	definedPriorityMap.put(DevState.FAULT, 7);
	definedPriorityMap.put(DevState.UNKNOWN, 8);
    }

    /**
     * set priority for state
     * 
     * @param state
     * @param priority
     */
    public void putStatePriority(final DevState state, final int priority) {
	definedPriorityMap.put(state, priority);
    }

    /**
     * set state for deviceName
     * 
     * @param deviceName
     * @param state
     */
    public void putDeviceState(final String deviceName, final DevState state) {

	deviceStateMap.put(deviceName, state);
    }

    public void putAllDeviceState(final Map<String, DevState> aDeviceStateMap) {

	deviceStateMap.putAll(aDeviceStateMap);
    }

    /**
     * 
     * @return DevState the Highest Priority State
     */
    public DevState getHighestPriorityState() {
	DevState highestPriorityState = null;
	for (final Map.Entry<String, DevState> entry : deviceStateMap.entrySet()) {
	    final int currentPriority = definedPriorityMap.get(entry.getValue());
	    if (highestPriorityState != null) {
		final int highestPriority = definedPriorityMap.get(highestPriorityState);
		if (highestPriority < currentPriority) {
		    highestPriorityState = entry.getValue();

		}
	    } else {
		highestPriorityState = entry.getValue();
	    }
	}
	return highestPriorityState;
    }

    /**
     * return the priority for state passed in parameter.
     * 
     * @param state
     * @return int the priority for state
     */
    public int getPriorityForState(final DevState state) {
	return definedPriorityMap.get(state);
    }

    /**
     * return the states of monitored devices
     * 
     * @return Map<String, DevState>
     */
    public Map<String, DevState> getDeviceStateMap() {
	return new HashMap<String, DevState>(deviceStateMap);
    }

    /**
     * return an array of String which contains deviceName and his state.
     * 
     * @return String[]
     */
    public String[] getDeviceStateArray() {
	final String[] array = new String[deviceStateMap.size()];
	int i = 0;
	for (final Map.Entry<String, DevState> deviceStateEntry : deviceStateMap.entrySet()) {

	    final String deviceName = deviceStateEntry.getKey();
	    final DevState deviceState = deviceStateEntry.getValue();
	    array[i++] = deviceName + " - " + StateUtilities.getNameForState(deviceState);
	}
	return array;
    }

    /**
     * return an array of short which contains the priority of state of monitored devices.
     * 
     * @return short[]
     */
    public short[] getDeviceStateNumberArray() {
	final short[] array = new short[deviceStateMap.size()];
	int i = 0;

	for (final Map.Entry<String, DevState> deviceStateEntry : deviceStateMap.entrySet()) {
	    final DevState deviceState = deviceStateEntry.getValue();
	    array[i++] = (short) getPriorityForState(deviceState);
	}
	return array;
    }
}
