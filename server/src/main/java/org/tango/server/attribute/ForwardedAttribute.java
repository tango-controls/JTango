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
package org.tango.server.attribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tango.server.ExceptionMessages;
import org.tango.server.StateMachineBehavior;
import org.tango.server.dynamic.attribute.TangoConverter;
import org.tango.server.events.EventManager;
import org.tango.server.events.EventType;
import org.tango.server.servant.DeviceImpl;
import org.tango.utils.DevFailedUtils;
import org.tango.utils.TangoUtil;

import fr.esrf.Tango.AttributeValue_5;
import fr.esrf.Tango.DevAttrHistory_5;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.Device_5;
import fr.esrf.TangoApi.AttributeInfoEx;
import fr.esrf.TangoApi.CallBack;
import fr.esrf.TangoApi.Connection;
import fr.esrf.TangoApi.events.EventData;
import fr.soleil.tango.clientapi.TangoAttribute;

/**
 * Attribute that is a proxy to another attribute. Works only for IDL5 device and client
 * 
 * @author ABEILLE
 * 
 */
public class ForwardedAttribute implements IAttributeBehavior {
    private final Logger logger = LoggerFactory.getLogger(ForwardedAttribute.class);

    private TangoAttribute proxy;
    private String fullRootAttributeName;
    private String rootAttributeName;
    // private final String deviceRootName;
    private final String attributeName;
    private String localLabel;
    private String remoteLabel;
    private String deviceName;

    /**
     * Create a forwarded attribute.
     * 
     * @param fullRootAttributeName The default value of the root attribute. May be overriden by __root_attr attribute
     *            property
     * @param attributeName The name of the fowarded attribute
     * @param defaultLabel Its default label
     * @throws DevFailed if creation failed
     */
    public ForwardedAttribute(final String fullRootAttributeName, final String attributeName, final String defaultLabel)
            throws DevFailed {
        this.attributeName = attributeName;
        this.fullRootAttributeName = fullRootAttributeName;
        this.localLabel = defaultLabel;
    }

    public void init(final String deviceName) throws DevFailed {
        if (fullRootAttributeName == null || fullRootAttributeName.isEmpty()) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.FWD_MISSING_ROOT, "root attribute name is empty");
        }
        this.deviceName = deviceName;
        rootAttributeName = TangoUtil.getAttributeName(fullRootAttributeName);
        proxy = new TangoAttribute(fullRootAttributeName);
        if (proxy.getAttributeProxy().get_idl_version() != DeviceImpl.SERVER_VERSION) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.FWD_TOO_OLD_ROOT_DEVICE,
                    "root device must have an IDL version 5");
        }
        logger.debug("forwarded attribute {} created ", fullRootAttributeName);
    }

    public void init(final String deviceName, final String rootAttributeProperty) throws DevFailed {
        this.deviceName = deviceName;
        this.fullRootAttributeName = rootAttributeProperty;
        rootAttributeName = TangoUtil.getAttributeName(fullRootAttributeName);
        proxy = new TangoAttribute(rootAttributeProperty);
        if (proxy.getAttributeProxy().get_idl_version() != DeviceImpl.SERVER_VERSION) {
            throw DevFailedUtils.newDevFailed(ExceptionMessages.FWD_TOO_OLD_ROOT_DEVICE,
                    "root device must have an IDL version 5");
        }
        logger.debug("forwarded attribute {} created ", fullRootAttributeName);
    }

    @Override
    public AttributeConfiguration getConfiguration() throws DevFailed {
        final AttributeConfiguration config;
        if (proxy != null) {
            config = TangoConverter.toAttributeConfigurationEx(proxy.getAttributeProxy().get_info_ex());
            remoteLabel = config.getAttributeProperties().getLabel();
        } else {
            config = new AttributeConfiguration();
        }
        config.setName(attributeName);
        config.getAttributeProperties().setRootAttribute(fullRootAttributeName);
        config.getAttributeProperties().setLabel(localLabel);
        return config;
    }

    @Override
    public AttributeValue getValue() throws DevFailed {
        final AttributeValue readValue = new AttributeValue();
        readValue.setValue(proxy.read());
        readValue.setQuality(proxy.getDeviceAttribute().getQuality());
        readValue.setTime(proxy.getTimestamp());
        return readValue;
    }

    public AttributeValue_5 getValue5() throws DevFailed {
        final AttributeValue_5 read5 = proxy.getAttributeProxy().read().getAttributeValueObject_5();
        read5.name = attributeName;
        return read5;
    }

    @Override
    public void setValue(final AttributeValue value) throws DevFailed {
        proxy.write(value.getValue());
    }

    @Override
    public StateMachineBehavior getStateMachine() throws DevFailed {
        return null;
    }

    void setLabel(final String label) {
        localLabel = label;
    }

    void setAttributeConfiguration(final AttributeConfiguration config) throws DevFailed {
        final AttributeInfoEx info = TangoConverter.toAttributeConfigurationEx(config);
        info.name = rootAttributeName;
        localLabel = info.label;
        info.label = remoteLabel;
        proxy.getAttributeProxy().set_info(new AttributeInfoEx[] { info });
    }

    AttributePropertiesImpl getProperties() throws DevFailed {
        return getConfiguration().getAttributeProperties();
    }

    public DevAttrHistory_5 getAttributeHistory(final int maxSize) throws DevFailed {
        DevAttrHistory_5 result = null;
        final Connection conn = proxy.getAttributeProxy().getDeviceProxy();
        conn.build_connection();
        final Device_5 device5 = conn.getDevice_5();
        if (device5 != null) {
            result = device5.read_attribute_history_5(rootAttributeName, maxSize);
        }
        return result;
    }

    public String getRootName() {
        return fullRootAttributeName;
    }

    public void subscribe(final EventType eventType) throws DevFailed {
        logger.info("fowarded attribute \"{}\" event subscribe {}", attributeName, eventType);
        @SuppressWarnings("serial")
        final CallBack callback = new CallBack() {
            @Override
            public void push_event(final EventData evt) {
                try {
                    if (evt.errors != null && evt.errors.length > 0) {
                        EventManager.getInstance().pushAttributeErrorEvent(deviceName, attributeName,
                                new DevFailed(evt.errors));
                    } else {
                        final EventType evtT = EventType.getEvent(evt.event_type);
                        switch (evtT) {
                            case ARCHIVE_EVENT:
                            case PERIODIC_EVENT:
                            case CHANGE_EVENT:
                            case USER_EVENT:
                                EventManager.getInstance().pushAttributeValueIDL5Event(deviceName, attributeName, evt.attr_value.getAttributeValueObject_5(), evtT);
                                break;
                            case ATT_CONF_EVENT:
                                EventManager.getInstance().pushAttributeConfigIDL5Event(deviceName, attributeName,evt.attr_config.get_attribute_config_obj_5());
                                break;
                            case DATA_READY_EVENT:
                                EventManager.getInstance().pushAttributeDataReadyEvent(deviceName, attributeName,
                                        evt.data_ready.ctr);
                                break;
                            case INTERFACE_CHANGE_EVENT: // INTERFACE_CHANGE_EVENT is not possible in attribute
                            default:
                                break;
                        }
                    }
                } catch (final DevFailed e) {
                    logger.error("error in forwarded event", e);
                    logger.error(DevFailedUtils.toString(e));
                }
            }
        };
        proxy.getAttributeProxy().subscribe_event(eventType.getValue(), callback, new String[] {});
    }

}
