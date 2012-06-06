package fr.soleil.tango.clientapi.attribute;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;

public interface IExtractor {
    Object extract(final DeviceAttribute da) throws DevFailed;

    Object extractRead(final DeviceAttribute da, AttrDataFormat format) throws DevFailed;

    Object extractReadArray(final DeviceAttribute da, AttrDataFormat format) throws DevFailed;

    Object extractWrite(final DeviceAttribute da, final AttrWriteType writeType, AttrDataFormat format)
	    throws DevFailed;

    Object extractWriteArray(final DeviceAttribute da, final AttrWriteType writeType, AttrDataFormat format)
	    throws DevFailed;

}
