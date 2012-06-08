//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 1.7  2005/12/02 09:54:04  pascal_verdier
// java import have been optimized.
//
// Revision 1.6  2005/08/10 08:26:09  pascal_verdier
// Synchronized done by a global object.
//
// Revision 1.5  2005/05/18 12:53:46  pascal_verdier
// device_name() method added.
//
// Revision 1.4  2004/09/16 14:01:06  pascal_verdier
// New constructor with a DeviceProxy instance added.
//
// Revision 1.3  2004/07/06 09:22:58  pascal_verdier
// subscribe event is now thread safe.
// notify daemon reconnection works.
//
// Revision 1.2  2004/03/19 10:24:34  ounsy
// Modification of the overall Java event client Api for synchronization with tango C++ Release 4
//
// Revision 1.1  2004/03/08 11:43:23  pascal_verdier
// *** empty log message ***
//
//
// Copyleftt 2003 by Synchrotron Soleil, France
//-======================================================================
/*
 * TangoEventsAdapter.java
 *
 * Created on September 25, 2003, 9:56 AM
 */

package fr.esrf.TangoApi.events;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceProxy;

import java.util.Hashtable;

/**
 *
 * @author  ounsy
 */
public class TangoEventsAdapter implements java.io.Serializable {
    
    /**
	 *	Creates a new instance of TangoEventsAdapter
	 *
	 *	@param device_name the device used name.
	 */
    public TangoEventsAdapter(String device_name) throws DevFailed 
    {
            device_proxy = new DeviceProxy(device_name);
            tango_periodic_source = new Hashtable();
            tango_change_source = new Hashtable();
            tango_quality_change_source = new Hashtable();
			tango_archive_source = new Hashtable();
			tango_user_source = new Hashtable();
    }
    /**
	 *	Creates a new instance of TangoEventsAdapter
	 *
	 *	@param dev the device used proxy instance.
	 */
   public TangoEventsAdapter(DeviceProxy dev) throws DevFailed 
    {
            device_proxy = dev;
            tango_periodic_source = new Hashtable();
            tango_change_source = new Hashtable();
            tango_quality_change_source = new Hashtable();
			tango_archive_source = new Hashtable();
			tango_user_source = new Hashtable();
    }

	/**
	 *	Add listner for periodic event
	 */
    public void addTangoPeriodicListener(ITangoPeriodicListener listener, String attr_name, String[] filters)
                throws DevFailed
    {
		TangoPeriodic tango_periodic = null;
		if ( (tango_periodic = (TangoPeriodic)tango_periodic_source.get(attr_name)) == null)
		{
			tango_periodic = new TangoPeriodic(device_proxy,attr_name,filters);
			tango_periodic_source.put(attr_name,tango_periodic);
		}

		synchronized(moni) {
			tango_periodic.addTangoPeriodicListener(listener);
		}
    }
    
	/**
	 *	remove listner for periodic event
	 */
    public void removeTangoPeriodicListener(ITangoPeriodicListener listener, String attr_name)
                throws DevFailed
    {
		synchronized(moni) {
			TangoPeriodic tango_periodic = null;
			if ( (tango_periodic = (TangoPeriodic)tango_periodic_source.get(attr_name)) != null)
				tango_periodic.removeTangoPeriodicListener(listener);
		}
    }


	/**
	 *	Add listner for change event
	 */
    public void addTangoChangeListener(ITangoChangeListener listener, String attr_name, String[] filters)
                throws DevFailed
    {
		TangoChange tango_change = null;
		if ( (tango_change = (TangoChange)tango_change_source.get(attr_name)) == null)
		{
			tango_change = new TangoChange(device_proxy,attr_name,filters);
			tango_change_source.put(attr_name,tango_change);
		}

		synchronized(moni) {
			tango_change.addTangoChangeListener(listener);
		}
    }
	/**
	 *	Remove listner for change event
	 */
    public void removeTangoChangeListener(ITangoChangeListener listener, String attr_name) 
                throws DevFailed
    {
		synchronized(moni) {
			TangoChange tango_change = null;
			if ( (tango_change = (TangoChange)tango_change_source.get(attr_name)) != null)
    			tango_change.removeTangoChangeListener(listener);
		}
	}
   


	/**
	 *	Add listner for archive event
	 */
    public void addTangoArchiveListener(ITangoArchiveListener listener, String attr_name,String[] filters)
                throws DevFailed
    {
		TangoArchive tango_archive = null;
		if ( (tango_archive = (TangoArchive)tango_archive_source.get(attr_name)) == null)
		{
			tango_archive = new TangoArchive(device_proxy,attr_name,filters);
			tango_archive_source.put(attr_name,tango_archive);
		}

		synchronized(moni) {
			tango_archive.addTangoArchiveListener(listener);
		}
    }
    
	/**
	 *	Remove listner for archive event
	 */
     public void removeTangoArchiveListener(ITangoArchiveListener listener, String attr_name) 
                throws DevFailed
    {
		synchronized(moni) {
			TangoArchive tango_archive = null;
			if ( (tango_archive = (TangoArchive)tango_archive_source.get(attr_name)) != null)
				tango_archive.removeTangoArchiveListener(listener);
        }
    }



	/**
	 *	Add listner for quality change event
	 */
    public void addTangoQualityChangeListener(ITangoQualityChangeListener listener, String attr_name,String[] filters)
                throws DevFailed
    {
		TangoQualityChange tango_quality_change = null;
		if ( (tango_quality_change = (TangoQualityChange)tango_quality_change_source.get(attr_name)) == null)
		{
			tango_quality_change = new TangoQualityChange(device_proxy,attr_name,filters);
			tango_quality_change_source.put(attr_name,tango_quality_change);
		}
		
		synchronized(moni) {
			tango_quality_change.addTangoQualityChangeListener(listener);
		}
    }
    
	/**
	 *	Remove listner for quality change event
	 */
    public void removeTangoQualityChangeListener(ITangoQualityChangeListener listener, String attr_name) 
                throws DevFailed
    {
		synchronized(moni) {
			TangoQualityChange tango_quality_change = null;
			if ( (tango_quality_change = (TangoQualityChange)tango_quality_change_source.get(attr_name)) != null)
				tango_quality_change.removeTangoQualityChangeListener(listener);
		}
    }


  
	/**
	 *	Add listner for user event
	 */
	public void addTangoUserListener(ITangoUserListener listener, String attr_name, String[] filters)
				throws DevFailed
	{
		TangoUser tango_user = null;
		if ( (tango_user = (TangoUser)tango_user_source.get(attr_name)) == null)
		{
			tango_user = new TangoUser(device_proxy,attr_name,filters);
			tango_user_source.put(attr_name,tango_user);
		}
		
		synchronized(moni) {
			tango_user.addTangoUserListener(listener);
		}
	}

	/**
	 *	Remove listner for user event
	 */
	public void removeTangoUserListener(ITangoUserListener listener, String attr_name)
				throws DevFailed
	{
		synchronized(moni) {
			TangoUser tango_user = null;
			if ( (tango_user = (TangoUser)tango_user_source.get(attr_name)) != null)
				tango_user.removeTangoUserListener(listener);
		}
	}



	public String device_name()
	{
		return device_proxy.name();
	}    

    

    private DeviceProxy	device_proxy = null;
    private Hashtable	tango_periodic_source = null;
    private Hashtable	tango_change_source = null;
    private Hashtable	tango_quality_change_source = null;
	private Hashtable	tango_archive_source = null;
	private Hashtable	tango_user_source = null;
	static  Object		moni = new Object();
    
}
