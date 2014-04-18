package org.tango.server.dynamic.attribute;

import java.util.HashMap;
import java.util.Map;

import org.tango.DeviceState;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributePropertiesImpl;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.utils.DevFailedUtils;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;

public final class PublisherAttribute implements IAttributeBehavior {
    private static final Map<String, Class<?>> JAVA_TYPES_MAP = new HashMap<String, Class<?>>();
    static {
	JAVA_TYPES_MAP.put("int", int.class);
	JAVA_TYPES_MAP.put("long", long.class);
	JAVA_TYPES_MAP.put("double", double.class);
	JAVA_TYPES_MAP.put("float", float.class);
	JAVA_TYPES_MAP.put("boolean", boolean.class);
	JAVA_TYPES_MAP.put("byte", byte.class);
	JAVA_TYPES_MAP.put("short", short.class);
	JAVA_TYPES_MAP.put("string", String.class);
	JAVA_TYPES_MAP.put("devstate", DeviceState.class);
	JAVA_TYPES_MAP.put("state", DeviceState.class);
	JAVA_TYPES_MAP.put("devencoded", DevEncoded.class);
	JAVA_TYPES_MAP.put("int[]", int[].class);
	JAVA_TYPES_MAP.put("long[]", long[].class);
	JAVA_TYPES_MAP.put("double[]", double[].class);
	JAVA_TYPES_MAP.put("float[]", float[].class);
	JAVA_TYPES_MAP.put("boolean[]", boolean[].class);
	JAVA_TYPES_MAP.put("byte[]", byte[].class);
	JAVA_TYPES_MAP.put("short[]", short[].class);
	JAVA_TYPES_MAP.put("string[]", String[].class);
	JAVA_TYPES_MAP.put("devstate[]", DeviceState[].class);
	JAVA_TYPES_MAP.put("state[]", DeviceState[].class);
	JAVA_TYPES_MAP.put("devencoded[]", DevEncoded[].class);
	JAVA_TYPES_MAP.put("int[][]", int[][].class);
	JAVA_TYPES_MAP.put("long[][]", long[][].class);
	JAVA_TYPES_MAP.put("double[][]", double[][].class);
	JAVA_TYPES_MAP.put("float[][]", float[][].class);
	JAVA_TYPES_MAP.put("boolean[][]", boolean[][].class);
	JAVA_TYPES_MAP.put("byte[][]", byte[][].class);
	JAVA_TYPES_MAP.put("short[][]", short[][].class);
	JAVA_TYPES_MAP.put("string[][]", String[][].class);
	JAVA_TYPES_MAP.put("state[][]", DeviceState[][].class);
	JAVA_TYPES_MAP.put("devstate[][]", DeviceState[][].class);
	JAVA_TYPES_MAP.put("devencoded[][]", DevEncoded[][].class);
    }

    private static final Map<Class<?>, Object> RESULTS_MAP = new HashMap<Class<?>, Object>();
    static {
	RESULTS_MAP.put(boolean.class, false);
	RESULTS_MAP.put(short.class, (short) 0);
	RESULTS_MAP.put(int.class, 0);
	RESULTS_MAP.put(byte.class, (byte) 0);
	RESULTS_MAP.put(float.class, 0.0F);
	RESULTS_MAP.put(long.class, 0L);
	RESULTS_MAP.put(double.class, 0.0);
	RESULTS_MAP.put(String.class, "");
	RESULTS_MAP.put(DeviceState.class, DeviceState.ON);
	RESULTS_MAP.put(DevEncoded.class, new DevEncoded());
	RESULTS_MAP.put(short[].class, new short[] {});
	RESULTS_MAP.put(int[].class, new int[] {});
	RESULTS_MAP.put(byte[].class, new byte[] {});
	RESULTS_MAP.put(float[].class, new float[] {});
	RESULTS_MAP.put(double[].class, new double[] {});
	RESULTS_MAP.put(long[].class, new long[] {});
	RESULTS_MAP.put(String[].class, new String[] {});
	RESULTS_MAP.put(boolean[].class, new boolean[] {});
	RESULTS_MAP.put(DeviceState[].class, new DeviceState[] {});
	RESULTS_MAP.put(DevEncoded[].class, new DevEncoded[] {});
	RESULTS_MAP.put(short[][].class, new short[][] { {} });
	RESULTS_MAP.put(int[][].class, new int[][] { {} });
	RESULTS_MAP.put(byte[][].class, new byte[][] { {} });
	RESULTS_MAP.put(float[][].class, new float[][] { {} });
	RESULTS_MAP.put(double[][].class, new double[][] { {} });
	RESULTS_MAP.put(long[][].class, new long[][] { {} });
	RESULTS_MAP.put(String[][].class, new String[][] { {} });
	RESULTS_MAP.put(boolean[][].class, new boolean[][] { {} });
	RESULTS_MAP.put(DeviceState[][].class, new DeviceState[][] { {} });
	RESULTS_MAP.put(DevEncoded[][].class, new DevEncoded[][] { {} });
    }

    private AttributeValue readValue;
    private final AttributeConfiguration configAttr = new AttributeConfiguration();

    /**
     * Create a dynamic attribute publisher
     * 
     * @param config
     *            config[0] attribute name; config[1] type (ie. double, double[], double[][])
     * @throws ClassNotFoundException
     * @throws DevFailed
     */
    public PublisherAttribute(final String... config) throws DevFailed {

	if (config.length >= 1) {
	    configAttr.setName(config[0]);
	} else {
	    configAttr.setName("");
	    DevFailedUtils.throwDevFailed("DEVICE_PROP_ERROR", "unknown attribute config");
	}
	if (config.length == 2) {
	    final Class<?> c = JAVA_TYPES_MAP.get(config[1].toLowerCase());
	    configAttr.setType(c);
	} else if (config.length >= 3) {
	    int typeAttr = 0;
	    try {
		typeAttr = Integer.parseInt(config[1]);
	    } catch (final NumberFormatException e) {
		typeAttr = org.tango.utils.TangoTypeUtils.getAttributeType(config[1]);
	    }

	    if (config[2].equalsIgnoreCase("SCALAR")) {
		configAttr.setTangoType(typeAttr, AttrDataFormat.SCALAR);
	    } else if (config[2].equalsIgnoreCase("SPECTRUM")) {
		configAttr.setTangoType(typeAttr, AttrDataFormat.SPECTRUM);
	    } else if (config[2].equalsIgnoreCase("IMAGE")) {
		configAttr.setTangoType(typeAttr, AttrDataFormat.IMAGE);
	    } else {
		DevFailedUtils.throwDevFailed("DEVICE_PROP_ERROR", "unknown attribute format: " + config[1]);
	    }
	} else {
	    DevFailedUtils.throwDevFailed("DEVICE_PROP_ERROR", "unknown attribute config: " + config[1]);
	}

	readValue = new AttributeValue();
	readValue.setValue(RESULTS_MAP.get(configAttr.getType()));
    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
	configAttr.setWritable(AttrWriteType.WRITE);
	configAttr.setMemorized(true);
	final AttributePropertiesImpl prop = new AttributePropertiesImpl();
	prop.setLabel(configAttr.getName());
	configAttr.setAttributeProperties(prop);
	return configAttr;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
	return readValue;
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
	readValue = value;
    }

    @Override
    public StateMachineBehavior getStateMachine() {
	// final StateMachineBehavior stateM = new StateMachineBehavior();
	// stateM.setEndState(DeviceState.DISABLE);
	return null;
    }

}
