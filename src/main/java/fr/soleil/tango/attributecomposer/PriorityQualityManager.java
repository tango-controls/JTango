package fr.soleil.tango.attributecomposer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevState;
import fr.esrf.TangoApi.QualityUtilities;

/**
 * Manage tango attribute qualities with priorities to obtain a single quality.
 * 
 * @author ABEILLE
 * 
 */
public final class PriorityQualityManager {

    /**
     * The table of priority <AttrQuality, Priority>
     */
    private final Map<AttrQuality, Integer> qualityPriorityMap = new HashMap<AttrQuality, Integer>();
    /**
     * The table of the quality and their associated device State
     */
    private final Map<AttrQuality, DevState> qualityStateMap = new HashMap<AttrQuality, DevState>();

    /**
     * The table of the attribute name and their associated qualities <attributeName, AttrQuality>
     */
    private final Map<String, AttrQuality> attributeQualityMap = new LinkedHashMap<String, AttrQuality>();

    public PriorityQualityManager() {

	qualityStateMap.put(AttrQuality.ATTR_CHANGING, DevState.MOVING);
	qualityStateMap.put(AttrQuality.ATTR_ALARM, DevState.ALARM);
	qualityStateMap.put(AttrQuality.ATTR_WARNING, DevState.ALARM);
	qualityStateMap.put(AttrQuality.ATTR_INVALID, DevState.FAULT);
	qualityStateMap.put(AttrQuality.ATTR_VALID, DevState.ON);

	qualityPriorityMap.put(AttrQuality.ATTR_VALID, 0);
	qualityPriorityMap.put(AttrQuality.ATTR_CHANGING, 0);
	qualityPriorityMap.put(AttrQuality.ATTR_WARNING, 0);
	qualityPriorityMap.put(AttrQuality.ATTR_ALARM, 0);
	qualityPriorityMap.put(AttrQuality.ATTR_INVALID, 0);
    }

    public void putQualityPriority(final AttrQuality quality, final int priority) {
	qualityPriorityMap.put(quality, priority);
    }

    public void putAttributeQuality(final String attributeName, final AttrQuality quality) {
	attributeQualityMap.put(attributeName, quality);
    }

    public AttrQuality getHighestPriorityQuality() {
	AttrQuality highestPriorityQuality = null;
	for (final Map.Entry<String, AttrQuality> entry : attributeQualityMap.entrySet()) {
	    final int currentPriority = qualityPriorityMap.get(entry.getValue());
	    if (highestPriorityQuality != null) {
		final int highestPriority = qualityPriorityMap.get(highestPriorityQuality);
		if (highestPriority < currentPriority) {
		    highestPriorityQuality = entry.getValue();
		}
	    } else {
		highestPriorityQuality = entry.getValue();
	    }
	}

	return highestPriorityQuality;
    }

    public String getHighestPriorityQualityAsString() {
	return QualityUtilities.getNameForQuality(getHighestPriorityQuality());
    }

    public DevState getHighestPriorityState() {
	return qualityStateMap.get(getHighestPriorityQuality());
    }

    public AttrQuality getQualityForAttribute(final String attributeName) {
	return attributeQualityMap.get(attributeName);
    }

    public int getPriorityForQuality(final AttrQuality quality) {
	return qualityPriorityMap.get(quality);
    }

    public int getPriorityForQuality(final String quality) {
	final AttrQuality attrQuality = QualityUtilities.getQualityForName(quality);
	return qualityPriorityMap.get(attrQuality);
    }

    public String[] getQualityArray() {
	final String[] array = new String[attributeQualityMap.size()];
	int i = 0;
	for (final Map.Entry<String, AttrQuality> entry : attributeQualityMap.entrySet()) {
	    final String attrName = entry.getKey();
	    final AttrQuality quality = entry.getValue();
	    array[i++] = attrName + " - " + QualityUtilities.getNameForQuality(quality);
	}
	return array;
    }

    public short[] getQualityNumberArray() {
	final short[] array = new short[attributeQualityMap.size()];
	int i = 0;
	for (final Map.Entry<String, AttrQuality> entry : attributeQualityMap.entrySet()) {
	    final AttrQuality quality = entry.getValue();
	    array[i++] = (short) getPriorityForQuality(quality);
	}
	return array;
    }
}
