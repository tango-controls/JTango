package fr.soleil.tango.attributecomposer;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceAttribute;

public interface IAttributeGroupTaskListener {

    public void updateDeviceAttribute(DeviceAttribute[] resultGroup);

    public void updateReadValue(String completeAttributeName, Object value);

    public void updateErrorMessage(String completeAttributeName, String errorMessage);

    public void updateWriteValue(String completeAttributeName, Object value);

    public void updateWriteValueErrorMessage(String completeAttributeName, String errorMessage);

    public void updateAttributeInfoEx(String completeAttributeName, AttributeInfoEx attributeInfo);

    public void updateAttributeInfoErrorMessage(String completeAttributeName, String errorMessage);

    public void updateQuality(String completeAttributeName, AttrQuality quality);

    public void catchException(Exception exception);

    public void catchDevFailed(DevFailed exception);

    public void readingLoopFinished();

}
