package fr.esrf.TangoApi.Group;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.DeviceAttribute;

public interface IAttributeGroupTaskListener {

    public void updateDeviceAttribute(DeviceAttribute[] resultGroup);

    public void updateReadValue(String completeAttributeName, double value);

    public void updateWriteValue(String completeAttributeName, double value);

    public void updateErrorMessage(String completeAttributeName, String errorMessage);

    public void updateAttributeInfoEx(String completeAttributeName, AttributeInfoEx attributeInfo);

    public void updateQuality(String completeAttributeName, AttrQuality quality);

    public void catchException(Exception exception);

    public void readingLoopFinished();

}
