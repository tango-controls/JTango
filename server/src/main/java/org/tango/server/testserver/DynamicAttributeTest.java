/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.testserver;

import java.util.HashMap;
import java.util.Map;

import org.tango.DeviceState;
import org.tango.server.StateMachineBehavior;
import org.tango.server.attribute.AttributeConfiguration;
import org.tango.server.attribute.AttributeValue;
import org.tango.server.attribute.IAttributeBehavior;
import org.tango.server.attribute.ISetValueUpdater;

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevEncoded;
import fr.esrf.Tango.DevFailed;

/**
 * To test dynamic attributes
 * 
 * @author ABEILLE
 * 
 */
public final class DynamicAttributeTest implements IAttributeBehavior, ISetValueUpdater {

    private AttributeValue value = new AttributeValue();
    private final AttributeConfiguration configAttr = new AttributeConfiguration();

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

    public DynamicAttributeTest(final Class<?> clazz) throws DevFailed {
        configAttr.setType(clazz);
        configAttr.setName(clazz.getSimpleName() + "Dynamic");
        configAttr.setWritable(AttrWriteType.READ_WRITE);
        value.setValue(RESULTS_MAP.get(configAttr.getType()));
    }

    public DynamicAttributeTest(final Class<?> clazz, final String attributeName) throws DevFailed {
        configAttr.setType(clazz);
        configAttr.setName(attributeName);
        configAttr.setWritable(AttrWriteType.READ_WRITE);
        value.setValue(RESULTS_MAP.get(configAttr.getType()));
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
        return value;
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
        this.value = value;
    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
        return configAttr;
    }

    @Override
    public StateMachineBehavior getStateMachine() {
        final StateMachineBehavior stateMachine = new StateMachineBehavior();
        stateMachine.setDeniedStates(DeviceState.FAULT);
        stateMachine.setEndState(DeviceState.ON);
        return stateMachine;
    }

    @Override
    public AttributeValue getSetValue() throws DevFailed {
        if (configAttr.getType().equals(double.class)) {
            return new AttributeValue(120.3);
        } else {
            return value;
        }
    }
}
