//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision: 28159 $
//
//-======================================================================


package fr.esrf.TangoApi.events;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

import java.util.Hashtable;

/**
 * @author pascal_verdier
 */
@SuppressWarnings({"UnusedDeclaration"})
public class TangoEventsAdapter implements java.io.Serializable {

    private DeviceProxy deviceProxy = null;
    private String deviceName = null;
    /*
     * A static table for each event type
     */
    private static Hashtable<String, TangoPeriodic>
            tango_periodic_source = new Hashtable<String, TangoPeriodic>();
    private static Hashtable<String, TangoPipe>
            tango_pipe_source = new Hashtable<String, TangoPipe>();
    private static Hashtable<String, TangoChange>
            tango_change_source = new Hashtable<String, TangoChange>();
    private static Hashtable<String, TangoQualityChange>
            tango_quality_change_source = new Hashtable<String, TangoQualityChange>();
    private static Hashtable<String, TangoArchive>
            tango_archive_source = new Hashtable<String, TangoArchive>();
    private static Hashtable<String, TangoUser>
            tango_user_source = new Hashtable<String, TangoUser>();
    private static Hashtable<String, TangoAttConfig>
            tango_att_config_source = new Hashtable<String, TangoAttConfig>();
    private static Hashtable<String, TangoDataReady>
            tango_data_ready_source = new Hashtable<String, TangoDataReady>();
    private static Hashtable<String, TangoInterfaceChange>
            tango_interface_change_source = new Hashtable<String, TangoInterfaceChange>();

    static final Object moni = new Object();
    //=======================================================================
    /**
     * Creates a new instance of TangoEventsAdapter
     *
     * @param deviceName the device used name.
     * @throws DevFailed if device does not exist
     */
    //=======================================================================
    public TangoEventsAdapter(String deviceName) throws DevFailed {
        this.deviceName = deviceName;
        deviceProxy = new DeviceProxy(deviceName);
    }
    //=======================================================================
    /**
     * Creates a new instance of TangoEventsAdapter
     *
     * @param deviceProxy the device used proxy instance.
     * @throws DevFailed  (never thrown)
     */
    //=======================================================================
    public TangoEventsAdapter(DeviceProxy deviceProxy) throws DevFailed {
        this.deviceProxy = deviceProxy;
        deviceName = this.deviceProxy.name();
    }

    //=======================================================================
    /**
     * Add listener for periodic event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoPeriodicListener(ITangoPeriodicListener listener, String attrName, String[] filters)
            throws DevFailed {
        addTangoPeriodicListener(listener, attrName, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for Periodic event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoPeriodicListener(ITangoPeriodicListener listener, String attrName, boolean stateless)
            throws DevFailed {
        addTangoPeriodicListener(listener, attrName, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for Periodic event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoPeriodicListener(ITangoPeriodicListener listener, String attrName, String[] filters, boolean stateless)
            throws DevFailed {
        TangoPeriodic tangoPeriodic;

        String key = deviceName+"/"+attrName;
        if ((tangoPeriodic = tango_periodic_source.get(key)) == null) {
            tangoPeriodic = new TangoPeriodic(deviceProxy, attrName, filters);
            tango_periodic_source.put(key, tangoPeriodic);
        }

        synchronized (moni) {
            tangoPeriodic.addTangoPeriodicListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * remove listener for periodic event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoPeriodicListener(ITangoPeriodicListener listener, String attrName)
            throws DevFailed {
        synchronized (moni) {
            TangoPeriodic tangoPeriodic;
            String key = deviceName+"/"+attrName;
            if ((tangoPeriodic = tango_periodic_source.get(key)) != null)
                tangoPeriodic.removeTangoPeriodicListener(listener);
        }
    }

    //=======================================================================
    /**
     * Add listener for pipe event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoPipeListener(ITangoPipeListener listener,
                                     String attrName, String[] filters) throws DevFailed {
        addTangoPipeListener(listener, attrName, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for pipe event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoPipeListener(ITangoPipeListener listener,
                                     String attrName, boolean stateless) throws DevFailed {
        addTangoPipeListener(listener, attrName, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for Pipe event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoPipeListener(ITangoPipeListener listener,
                                     String attrName, String[] filters, boolean stateless) throws DevFailed {
        TangoPipe tangoPipe;
        String key = deviceName+"/"+attrName;
        if ((tangoPipe = tango_pipe_source.get(key)) == null) {
            tangoPipe = new TangoPipe(deviceProxy, attrName, filters);
            tango_pipe_source.put(key, tangoPipe);
        }

        synchronized (moni) {
            tangoPipe.addTangoPipeListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * remove listener for pipe event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoPipeListener(ITangoPipeListener listener, String attrName)
            throws DevFailed {
        synchronized (moni) {
            TangoPipe tangoPipe;
            String key = deviceName+"/"+attrName;
            if ((tangoPipe = tango_pipe_source.get(key)) != null)
                tangoPipe.removeTangoPipeListener(listener);
        }
    }


    //=======================================================================
    /**
     * Add listener for change event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoChangeListener(ITangoChangeListener listener, String attrName, String[] filters)
            throws DevFailed {
        addTangoChangeListener(listener, attrName, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for Change event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoChangeListener(ITangoChangeListener listener, String attrName, boolean stateless)
            throws DevFailed {
        addTangoChangeListener(listener, attrName, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for Change event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoChangeListener(ITangoChangeListener listener, String attrName, String[] filters, boolean stateless)
            throws DevFailed {
        TangoChange tangoChange;
        String key = deviceName+"/"+attrName;
        if ((tangoChange = tango_change_source.get(key)) == null) {
            tangoChange = new TangoChange(deviceProxy, attrName, filters);
            tango_change_source.put(key, tangoChange);
        }

        synchronized (moni) {
            tangoChange.addTangoChangeListener(listener, stateless);
        }
    }
    //=======================================================================
    /**
     * Remove listener for change event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoChangeListener(ITangoChangeListener listener, String attrName)
            throws DevFailed {
        synchronized (moni) {
            TangoChange tangoChange;
            String key = deviceName+"/"+attrName;
            if ((tangoChange = tango_change_source.get(key)) != null)
                tangoChange.removeTangoChangeListener(listener);
        }
    }

    //=======================================================================
    /**
     * Add listener for archive event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoArchiveListener(ITangoArchiveListener listener, String attrName, String[] filters)
            throws DevFailed {
        addTangoArchiveListener(listener, attrName, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for Archive event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoArchiveListener(ITangoArchiveListener listener, String attrName, boolean stateless)
            throws DevFailed {
        addTangoArchiveListener(listener, attrName, new String[0], stateless);
    }

    //=======================================================================
    /**
     * Add listener for Archive event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoArchiveListener(ITangoArchiveListener listener, String attrName, String[] filters, boolean stateless)
            throws DevFailed {
        TangoArchive tangoArchive;
        String key = deviceName+"/"+attrName;
        if ((tangoArchive = tango_archive_source.get(key)) == null) {
            tangoArchive = new TangoArchive(deviceProxy, attrName, filters);
            tango_archive_source.put(key, tangoArchive);
        }

        synchronized (moni) {
            tangoArchive.addTangoArchiveListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * Remove listener for archive event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoArchiveListener(ITangoArchiveListener listener, String attrName)
            throws DevFailed {
        synchronized (moni) {
            TangoArchive tangoArchive;
            String key = deviceName+"/"+attrName;
            if ((tangoArchive = tango_archive_source.get(key)) != null)
                tangoArchive.removeTangoArchiveListener(listener);
        }
    }

    //=======================================================================
    /**
     * Add listener for quality change event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     * @deprecated Event type does not exist anymore.
     */
    //=======================================================================
    public void addTangoQualityChangeListener(ITangoQualityChangeListener listener, String attrName, String[] filters)
            throws DevFailed {
        //addTangoQualityChangeListener(listener, attrName, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for QualityChange event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     * @deprecated Event type does not exist anymore.
     */
    //=======================================================================
    public void addTangoQualityChangeListener(ITangoQualityChangeListener listener, String attrName, String[] filters, boolean stateless)
            throws DevFailed {
        TangoQualityChange tangoQualityChange;
        String key = deviceName+"/"+attrName;
        if ((tangoQualityChange = tango_quality_change_source.get(key)) == null) {
            tangoQualityChange = new TangoQualityChange(deviceProxy, attrName, filters);
            tango_quality_change_source.put(key, tangoQualityChange);
        }

        synchronized (moni) {
            tangoQualityChange.addTangoQualityChangeListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * Remove listener for quality change event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoQualityChangeListener(ITangoQualityChangeListener listener, String attrName)
            throws DevFailed {
        synchronized (moni) {
            TangoQualityChange tangoQualityChange;
            String key = deviceName+"/"+attrName;
            if ((tangoQualityChange = tango_quality_change_source.get(key)) != null)
                tangoQualityChange.removeTangoQualityChangeListener(listener);
        }
    }

    //=======================================================================
    /**
     * Add listener for user event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoUserListener(ITangoUserListener listener, String attrName, String[] filters)
            throws DevFailed {
        addTangoUserListener(listener, attrName, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for User event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoUserListener(ITangoUserListener listener, String attrName, boolean stateless)
            throws DevFailed {
        addTangoUserListener(listener, attrName, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for User event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoUserListener(ITangoUserListener listener, String attrName, String[] filters, boolean stateless)
            throws DevFailed {
        TangoUser tangoUser;
        String key = deviceName+"/"+attrName;
        if ((tangoUser = tango_user_source.get(key)) == null) {
            tangoUser = new TangoUser(deviceProxy, attrName, filters);
            tango_user_source.put(key, tangoUser);
        }

        synchronized (moni) {
            tangoUser.addTangoUserListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * Remove listener for user event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoUserListener(ITangoUserListener listener, String attrName)
            throws DevFailed {
        synchronized (moni) {
            TangoUser tangoUser;
            String key = deviceName+"/"+attrName;
            if ((tangoUser = tango_user_source.get(key)) != null)
                tangoUser.removeTangoUserListener(listener);
        }
    }


    //=======================================================================
    /**
     * Add listener for AttConfig event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoAttConfigListener(ITangoAttConfigListener listener, String attrName, String[] filters)
            throws DevFailed {
        addTangoAttConfigListener(listener, attrName, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for Config event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoAttConfigListener(ITangoAttConfigListener listener, String attrName, boolean stateless)
            throws DevFailed {
        addTangoAttConfigListener(listener, attrName, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for Config event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoAttConfigListener(ITangoAttConfigListener listener, String attrName, String[] filters, boolean stateless)
            throws DevFailed {
        TangoAttConfig tangoAttConfig;
        String key = deviceName+"/"+attrName;
        if ((tangoAttConfig = tango_att_config_source.get(key)) == null) {
            tangoAttConfig = new TangoAttConfig(deviceProxy, attrName, filters);
            tango_att_config_source.put(key, tangoAttConfig);
        }

        synchronized (moni) {
            tangoAttConfig.addTangoAttConfigListener(listener, stateless);
        }
    }
    //=======================================================================
    /**
     * Remove listener for att_config event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoAttConfigListener(ITangoAttConfigListener listener, String attrName)
            throws DevFailed {
        synchronized (moni) {
            TangoAttConfig tangoAttConfig;
            String key = deviceName+"/"+attrName;
            if ((tangoAttConfig = tango_att_config_source.get(key)) != null)
                tangoAttConfig.removeTangoAttConfigListener(listener);
        }
    }

    //=======================================================================
    /**
     * Add listener for DataReady event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoDataReadyListener(ITangoDataReadyListener listener, String attrName, String[] filters)
            throws DevFailed {
        addTangoDataReadyListener(listener, attrName, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for DataReady event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoDataReadyListener(ITangoDataReadyListener listener, String attrName, boolean stateless)
            throws DevFailed {
        addTangoDataReadyListener(listener, attrName, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for DataReady event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoDataReadyListener(ITangoDataReadyListener listener, String attrName, String[] filters, boolean stateless)
            throws DevFailed {
        TangoDataReady tangoDataReady;
        String key = deviceName+"/"+attrName;
        if ((tangoDataReady = tango_data_ready_source.get(key)) == null) {
            tangoDataReady = new TangoDataReady(deviceProxy, attrName, filters);
            tango_data_ready_source.put(key, tangoDataReady);
        }

        synchronized (moni) {
            tangoDataReady.addTangoDataReadyListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * Remove listener for DataReady event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoDataReadyListener(ITangoDataReadyListener listener, String attrName)
            throws DevFailed {
        synchronized (moni) {
            TangoDataReady tangoDataReady;
            String key = deviceName+"/"+attrName;
            if ((tangoDataReady = tango_data_ready_source.get(key)) != null)
                tangoDataReady.removeTangoDataReadyListener(listener);
        }
    }
    //=======================================================================
    /**
     * Add listener for change event
     * @param  listener the specified listener
     * @param attrName the attribute name
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoInterfaceChangeListener(ITangoInterfaceChangeListener listener, String attrName)
            throws DevFailed {
        addTangoInterfaceChangeListener(listener, attrName, false);
    }
    //=======================================================================
    /**
     * Add listener for Change event
     * @param  listener the specified listener
     * @param deviceName the device name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoInterfaceChangeListener(ITangoInterfaceChangeListener listener, String deviceName, boolean stateless)
            throws DevFailed {
        TangoInterfaceChange interfaceChange;
        if ((interfaceChange = tango_interface_change_source.get(deviceName)) == null) {
            interfaceChange = new TangoInterfaceChange(deviceProxy);
            tango_interface_change_source.put(deviceName, interfaceChange);
        }
        synchronized (moni) {
            interfaceChange.addTangoInterfaceChangeListener(listener, stateless);
        }
    }
    //=======================================================================
    /**
     * Remove listener for change event
     * @param  listener the specified listener
     * @param deviceName the device name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoInterfaceChangeListener(ITangoInterfaceChangeListener listener, String deviceName)
            throws DevFailed {
        synchronized (moni) {
            TangoInterfaceChange interfaceChange_change;
            if ((interfaceChange_change = tango_interface_change_source.get(deviceName)) != null)
                interfaceChange_change.removeTangoInterfaceChangeListener(listener);
        }
    }
    //=======================================================================
    //=======================================================================
    public String device_name() {
        return deviceProxy.name();
    }
}
