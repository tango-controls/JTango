/**
 * Copyright (C) :     2012
 * <p>
 * Synchrotron Soleil
 * L'Orme des merisiers
 * Saint Aubin
 * BP48
 * 91192 GIF-SUR-YVETTE CEDEX
 * <p>
 * This file is part of Tango.
 * <p>
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.idl;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevPipeBlob;
import fr.esrf.Tango.DevPipeData;
import fr.esrf.Tango.PipeConfig;
import fr.esrf.Tango.TimeVal;
import fr.esrf.TangoApi.PipeBlob;
import org.tango.server.pipe.PipeConfiguration;
import org.tango.server.pipe.PipeImpl;
import org.tango.server.pipe.PipeValue;

public final class TangoIDLUtil {

    private static final int FACTOR_1000 = 1000;

    private TangoIDLUtil() {
    }

    public static TimeVal getTime(final long nbMillisec) {
        final TimeVal timeVal = new TimeVal();
        timeVal.tv_sec = (int) (nbMillisec / FACTOR_1000);
        timeVal.tv_usec = (int) ((nbMillisec - nbMillisec / FACTOR_1000 * FACTOR_1000) * FACTOR_1000);
        timeVal.tv_nsec = 0;
        return timeVal;
    }

    public static int getTime(final TimeVal timeVal) {
        return timeVal.tv_nsec * FACTOR_1000 + timeVal.tv_usec * FACTOR_1000 * FACTOR_1000;
    }

    public static PipeConfig toPipeConfig(final PipeImpl pipe) throws DevFailed {
        final PipeConfiguration props = pipe.getConfiguration();
        return new PipeConfig(pipe.getName(), props.getDescription(), props.getLabel(), props.getDisplayLevel(),
                props.getWriteType(), props.getExtensions());
    }

    public static DevPipeData toDevPipeData(final String pipeName, final PipeValue pipeValue) throws DevFailed {
        final DevPipeBlob devPipeBlob = pipeValue.getValue().getDevPipeBlobObject();
        return new DevPipeData(pipeName, TangoIDLUtil.getTime(pipeValue.getTime()), devPipeBlob);
    }

    public static PipeValue toPipeValue(final DevPipeData pipeData) throws DevFailed {
        final PipeValue value = new PipeValue();
        value.setValue(new PipeBlob(pipeData.data_blob), TangoIDLUtil.getTime(pipeData.time));
        return value;
    }

}
