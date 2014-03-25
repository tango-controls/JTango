//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author$
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
// $Revision$
//
//-======================================================================


package fr.esrf.TangoApi.events;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;
import fr.esrf.TangoApi.DeviceProxyFactory;

import java.util.Hashtable;

/**
 * @author pascal_verdier
 */
@SuppressWarnings({"UnusedDeclaration"})
public class TangoEventsAdapter implements java.io.Serializable {

    private DeviceProxy device_proxy = null;
    private Hashtable<String, TangoPeriodic>
            tango_periodic_source = new Hashtable<String, TangoPeriodic>();
    private Hashtable<String, TangoChange>
            tango_change_source = new Hashtable<String, TangoChange>();
    private Hashtable<String, TangoQualityChange>
            tango_quality_change_source = new Hashtable<String, TangoQualityChange>();
    private Hashtable<String, TangoArchive>
            tango_archive_source = new Hashtable<String, TangoArchive>();
    private Hashtable<String, TangoUser>
            tango_user_source = new Hashtable<String, TangoUser>();
    private Hashtable<String, TangoAttConfig>
            tango_att_config_source = new Hashtable<String, TangoAttConfig>();
    private Hashtable<String, TangoDataReady>
            tango_data_ready_source = new Hashtable<String, TangoDataReady>();
    static final Object moni = new Object();

    //=======================================================================
    /**
     * Creates a new instance of TangoEventsAdapter
     *
     * @param device_name the device used name.
     * @throws DevFailed if device does not exist
     */
    //=======================================================================
    public TangoEventsAdapter(String device_name) throws DevFailed {
        device_proxy = DeviceProxyFactory.get(device_name);
    }
    //=======================================================================
    /**
     * Creates a new instance of TangoEventsAdapter
     *
     * @param dev the device used proxy instance.
     * @throws DevFailed  (never thrown)
     */
    //=======================================================================
    public TangoEventsAdapter(DeviceProxy dev) throws DevFailed {
        device_proxy = dev;
    }

    //=======================================================================
    /**
     * Add listener for periodic event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoPeriodicListener(ITangoPeriodicListener listener, String attr_name, String[] filters)
            throws DevFailed {
        addTangoPeriodicListener(listener, attr_name, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for Periodic event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoPeriodicListener(ITangoPeriodicListener listener, String attr_name, boolean stateless)
            throws DevFailed {
        addTangoPeriodicListener(listener, attr_name, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for Periodic event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoPeriodicListener(ITangoPeriodicListener listener, String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        TangoPeriodic tango_periodic;
        if ((tango_periodic = tango_periodic_source.get(attr_name)) == null) {
            tango_periodic = new TangoPeriodic(device_proxy, attr_name, filters);
            tango_periodic_source.put(attr_name, tango_periodic);
        }

        synchronized (moni) {
            tango_periodic.addTangoPeriodicListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * remove listener for periodic event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoPeriodicListener(ITangoPeriodicListener listener, String attr_name)
            throws DevFailed {
        synchronized (moni) {
            TangoPeriodic tango_periodic;
            if ((tango_periodic = tango_periodic_source.get(attr_name)) != null)
                tango_periodic.removeTangoPeriodicListener(listener);
        }
    }


    //=======================================================================
    /**
     * Add listener for change event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoChangeListener(ITangoChangeListener listener, String attr_name, String[] filters)
            throws DevFailed {
        addTangoChangeListener(listener, attr_name, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for Change event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoChangeListener(ITangoChangeListener listener, String attr_name, boolean stateless)
            throws DevFailed {
        addTangoChangeListener(listener, attr_name, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for Change event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoChangeListener(ITangoChangeListener listener, String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        TangoChange tango_change;
        if ((tango_change = tango_change_source.get(attr_name)) == null) {
            tango_change = new TangoChange(device_proxy, attr_name, filters);
            tango_change_source.put(attr_name, tango_change);
        }

        synchronized (moni) {
            tango_change.addTangoChangeListener(listener, stateless);
        }
    }
    //=======================================================================
    /**
     * Remove listener for change event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoChangeListener(ITangoChangeListener listener, String attr_name)
            throws DevFailed {
        synchronized (moni) {
            TangoChange tango_change;
            if ((tango_change = tango_change_source.get(attr_name)) != null)
                tango_change.removeTangoChangeListener(listener);
        }
    }

    //=======================================================================
   /**
     * Add listener for archive event
    * @param  listener the specified listener
    * @param attr_name the attribute name
    * @param filters filter array
    * @throws DevFailed in case of connection failed
    */
    //=======================================================================
    public void addTangoArchiveListener(ITangoArchiveListener listener, String attr_name, String[] filters)
            throws DevFailed {
        addTangoArchiveListener(listener, attr_name, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for Archive event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoArchiveListener(ITangoArchiveListener listener, String attr_name, boolean stateless)
            throws DevFailed {
        addTangoArchiveListener(listener, attr_name, new String[0], stateless);
    }

    //=======================================================================
    /**
     * Add listener for Archive event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoArchiveListener(ITangoArchiveListener listener, String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        TangoArchive tango_archive;
        if ((tango_archive = tango_archive_source.get(attr_name)) == null) {
            tango_archive = new TangoArchive(device_proxy, attr_name, filters);
            tango_archive_source.put(attr_name, tango_archive);
        }

        synchronized (moni) {
            tango_archive.addTangoArchiveListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * Remove listener for archive event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoArchiveListener(ITangoArchiveListener listener, String attr_name)
            throws DevFailed {
        synchronized (moni) {
            TangoArchive tango_archive;
            if ((tango_archive = tango_archive_source.get(attr_name)) != null)
                tango_archive.removeTangoArchiveListener(listener);
        }
    }

    //=======================================================================
    /**
     * Add listener for quality change event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     * @deprecated Event type does not exist anymore.
     */
    //=======================================================================
    public void addTangoQualityChangeListener(ITangoQualityChangeListener listener, String attr_name, String[] filters)
            throws DevFailed {
        //addTangoQualityChangeListener(listener, attr_name, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for QualityChange event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     * @deprecated Event type does not exist anymore.
     */
    //=======================================================================
    public void addTangoQualityChangeListener(ITangoQualityChangeListener listener, String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        TangoQualityChange tango_quality_change;
        if ((tango_quality_change = tango_quality_change_source.get(attr_name)) == null) {
            tango_quality_change = new TangoQualityChange(device_proxy, attr_name, filters);
            tango_quality_change_source.put(attr_name, tango_quality_change);
        }

        synchronized (moni) {
            tango_quality_change.addTangoQualityChangeListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * Remove listener for quality change event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoQualityChangeListener(ITangoQualityChangeListener listener, String attr_name)
            throws DevFailed {
        synchronized (moni) {
            TangoQualityChange tango_quality_change;
            if ((tango_quality_change = tango_quality_change_source.get(attr_name)) != null)
                tango_quality_change.removeTangoQualityChangeListener(listener);
        }
    }

    //=======================================================================
    /**
     * Add listener for user event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoUserListener(ITangoUserListener listener, String attr_name, String[] filters)
            throws DevFailed {
        addTangoUserListener(listener, attr_name, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for User event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoUserListener(ITangoUserListener listener, String attr_name, boolean stateless)
            throws DevFailed {
        addTangoUserListener(listener, attr_name, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for User event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoUserListener(ITangoUserListener listener, String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        TangoUser tango_user;
        if ((tango_user = tango_user_source.get(attr_name)) == null) {
            tango_user = new TangoUser(device_proxy, attr_name, filters);
            tango_user_source.put(attr_name, tango_user);
        }

        synchronized (moni) {
            tango_user.addTangoUserListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * Remove listener for user event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoUserListener(ITangoUserListener listener, String attr_name)
            throws DevFailed {
        synchronized (moni) {
            TangoUser tango_user;
            if ((tango_user = tango_user_source.get(attr_name)) != null)
                tango_user.removeTangoUserListener(listener);
        }
    }


    //=======================================================================
    /**
     * Add listener for AttConfig event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoAttConfigListener(ITangoAttConfigListener listener, String attr_name, String[] filters)
            throws DevFailed {
        addTangoAttConfigListener(listener, attr_name, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for Config event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoAttConfigListener(ITangoAttConfigListener listener, String attr_name, boolean stateless)
            throws DevFailed {
        addTangoAttConfigListener(listener, attr_name, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for Config event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoAttConfigListener(ITangoAttConfigListener listener, String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        TangoAttConfig tango_att_config;
        if ((tango_att_config = tango_att_config_source.get(attr_name)) == null) {
            tango_att_config = new TangoAttConfig(device_proxy, attr_name, filters);
            tango_att_config_source.put(attr_name, tango_att_config);
        }

        synchronized (moni) {
            tango_att_config.addTangoAttConfigListener(listener, stateless);
        }
    }
    //=======================================================================
    /**
     * Remove listener for att_config event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoAttConfigListener(ITangoAttConfigListener listener, String attr_name)
            throws DevFailed {
        synchronized (moni) {
            TangoAttConfig tango_att_config;
            if ((tango_att_config = tango_att_config_source.get(attr_name)) != null)
                tango_att_config.removeTangoAttConfigListener(listener);
        }
    }

    //=======================================================================
    /**
     * Add listener for DataReady event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @throws DevFailed in case of connection failed
     */
    //=======================================================================
    public void addTangoDataReadyListener(ITangoDataReadyListener listener, String attr_name, String[] filters)
            throws DevFailed {
        addTangoDataReadyListener(listener, attr_name, filters, false);
    }

    //=======================================================================
    /**
     * Add listener for DataReady event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoDataReadyListener(ITangoDataReadyListener listener, String attr_name, boolean stateless)
            throws DevFailed {
        addTangoDataReadyListener(listener, attr_name, new String[0], stateless);
    }
    //=======================================================================
    /**
     * Add listener for DataReady event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @param filters filter array
     * @param stateless if true: will re-try if failed
     * @throws DevFailed in case of connection failed and stateless is false
     */
    //=======================================================================
    public void addTangoDataReadyListener(ITangoDataReadyListener listener, String attr_name, String[] filters, boolean stateless)
            throws DevFailed {
        TangoDataReady tango_data_ready;
        if ((tango_data_ready = tango_data_ready_source.get(attr_name)) == null) {
            tango_data_ready = new TangoDataReady(device_proxy, attr_name, filters);
            tango_data_ready_source.put(attr_name, tango_data_ready);
        }

        synchronized (moni) {
            tango_data_ready.addTangoDataReadyListener(listener, stateless);
        }
    }

    //=======================================================================
    /**
     * Remove listener for DataReady event
     * @param  listener the specified listener
     * @param attr_name the attribute name
     * @throws DevFailed if specified listener not found
     */
    //=======================================================================
    public void removeTangoDataReadyListener(ITangoDataReadyListener listener, String attr_name)
            throws DevFailed {
        synchronized (moni) {
            TangoDataReady tango_data_ready;
            if ((tango_data_ready = tango_data_ready_source.get(attr_name)) != null)
                tango_data_ready.removeTangoDataReadyListener(listener);
        }
    }


    //=======================================================================
    //=======================================================================
    public String device_name() {
        return device_proxy.name();
    }
}
