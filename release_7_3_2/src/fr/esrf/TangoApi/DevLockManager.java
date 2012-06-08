//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the DevLockManager class definition .
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
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
// $Log$
// Revision 1.4  2009/03/25 13:32:08  pascal_verdier
// ...
//
// Revision 1.3  2009/01/16 12:43:59  pascal_verdier
// PID added to the identification class.
//
// Revision 1.2  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.1  2008/09/12 11:32:16  pascal_verdier
// Tango 7 first revision.
//
//-======================================================================

package fr.esrf.TangoApi;


/** 
 *	This class is able to to generate a UUID and
 *	retreive the main class of this JVM to
 *	identify the client to use the device lock features.
 *	It also manage a thread class for all admin devices needed
 *	to relock devices.
 *
 * @author  verdier
 */

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;
import java.util.Vector;


class  DevLockManager
{
	static private	DevLockManager	instance = null;
	static private	UUID			uuid;
	static private	long[]			l_uuid;
	static private	String			mainClass = null;
	static private	String			host_add = null;
	static private	JavaClntIdent	j_ident;
	static private	ClntIdent		ident;


	//===============================================================
	/**
	 *	Returns instance of the Object.
	 */
	//===============================================================
	static DevLockManager getInstance()
	{
		if (instance==null)
			instance = new DevLockManager();
		return instance;
	}
	//===============================================================
	/**
	 *	The object constrauctor
	 */
	//===============================================================
	private DevLockManager()
	{
		uuid = UUID.randomUUID();
		l_uuid = new long[2];
		l_uuid[0] = uuid.getMostSignificantBits();
		l_uuid[1] = uuid.getLeastSignificantBits();

		//	Try to get Process ID
		String	pid = null;
		RuntimeMXBean mx = ManagementFactory.getRuntimeMXBean();
		String	str = mx.getName();
		int	pos = str.indexOf('@');
		if (pos>0)
			pid = str.substring(0, pos);

		//	Generate an exception to check stack to find main class
		try {
			int x = 0;
			x = 1/x;
			System.out.println(x);	//	Only to prevent warning compiler
			//throw new RuntimeException();
		}
		catch(Exception e)
		{
			StackTraceElement[]	trace = e.getStackTrace();

			//	Search main method
			String	tag = ".main";
			String	s = trace[trace.length-1].toString();
			int	 idx = s.indexOf(tag);
			if (idx>0)
			{
				//	Main class found
				mainClass = s.substring(0, idx);
				if (pid!=null)
					mainClass += " - PID=" + pid;
			}
			else
			if (pid!=null)
				//	Not found, set with PID
				mainClass = "PID=" + pid;
			else
				//	Main class and pid not found, 
				//	put the highest info in stack
				mainClass = s.substring(s.lastIndexOf('('));
		}
		j_ident = new JavaClntIdent(mainClass, l_uuid);
		ident = new ClntIdent();
		ident.java_clnt(j_ident);

		try {
			host_add = java.net.InetAddress.getLocalHost().getHostAddress();
		}
		catch(java.net.UnknownHostException e) {}
	}

	//===============================================================
	//
	//	Cient identification management
	//
	//===============================================================

	//===============================================================
	/**
	 *	Returns the java client identifier object
	 */
	//===============================================================
	JavaClntIdent getJavaClntIdent()
	{
		return j_ident;
	}
	//===============================================================
	/**
	 *	Returns the IDL client identifier object
	 */
	//===============================================================
	ClntIdent getClntIdent()
	{
		return ident;
	}
	//===============================================================
	//===============================================================
	String getMainClass()
	{
		return mainClass;
	}
	//===============================================================
	//===============================================================
	String getHost()
	{
		return host_add;
	}
	//===============================================================
	//===============================================================
	UUID getUUID()
	{
		return uuid;
	}
	//===============================================================
	//===============================================================
	public String toString()
	{
		String	str = mainClass + ":\n";
		for (long an_uuid : l_uuid)
			str += an_uuid + "\n";
		return str.trim();
	}
	//===============================================================
	//===============================================================



	//===============================================================
	//
	//	Device access management
	//
	//===============================================================
	// ==========================================================================
	/**
	 *	Lock the device
	 *
	 *	@param	validity	Lock validity (in seconds)
	 */
	// ==========================================================================
	void lock(DeviceProxy deviceProxy, int validity) throws DevFailed
	{
		deviceProxy.import_admin_device("lock");
		
		DevVarLongStringArray	lsa = new DevVarLongStringArray();
		lsa.svalue = new String[1];
		lsa.lvalue = new int[1];
		lsa.svalue[0] = deviceProxy.get_name();
		lsa.lvalue[0] = validity;
		DeviceData	argin = new DeviceData();
		argin.insert(lsa);
		deviceProxy.getAdm_dev().command_inout("LockDevice", argin);
		
		addToRelockList(deviceProxy, validity);
	}

	// ==========================================================================
	/**
	 *	Unlock the device
	 */
	// ==========================================================================
	int unlock(DeviceProxy deviceProxy) throws DevFailed
	{
		deviceProxy.import_admin_device("unlock");
		DevVarLongStringArray	lsa = new DevVarLongStringArray();
		lsa.svalue = new String[1];
		lsa.lvalue = new int[1];
		lsa.svalue[0] = deviceProxy.get_name();
		lsa.lvalue[0] = 0;	//	Do not force
		DeviceData	argin = new DeviceData();
		argin.insert(lsa);
		DeviceData	argout =
			deviceProxy.getAdm_dev().command_inout("UnlockDevice", argin);
		//	Check lock counter for the device itself (not the proxy)
		int	nb = argout.extractLong();
		if (nb==0)
			removeToRelockList(deviceProxy);
		return nb;
	}
	// ==========================================================================
	/**
	 *	Returns true if the device is locked
	 */
	// ==========================================================================
	boolean isLocked(DeviceProxy deviceProxy) throws DevFailed
	{
		LockerInfo	info = getLockerInfo(deviceProxy);
		return info.isLocked();
	}

	// ==========================================================================
	/**
	 *	Returns true if the device is locked by this process
	 */
	// ==========================================================================
	boolean isLockedByMe(DeviceProxy deviceProxy) throws DevFailed
	{
		LockerInfo	info = getLockerInfo(deviceProxy);
		return info.isMe();
	}
	// ==========================================================================
	/**
	 *	Returns the device lock status
	 */
	// ==========================================================================
	String getLockerStatus(DeviceProxy deviceProxy) throws DevFailed
	{
		LockerInfo	info = getLockerInfo(deviceProxy);
		return info.getStatus();
	}
	// ==========================================================================
	/**
	 *	Returns the device lock info
	 */
	// ==========================================================================
	LockerInfo getLockerInfo(DeviceProxy deviceProxy) throws DevFailed
	{
		deviceProxy.import_admin_device("getLockerInfo");
		DeviceData	argin = new DeviceData();
		argin.insert(deviceProxy.get_name());
		DeviceData	argout = deviceProxy.getAdm_dev().command_inout("DevLockStatus", argin);
		return new LockerInfo(argout.extractLongStringArray());
	}
	// ==========================================================================
	// ==========================================================================



	//===============================================================
	//
	//	Relock vectors and threads  management
	//
	//===============================================================
	private static Hashtable	relock_list = null;
	private static final long	VALIDITY_DELAY = 500;	//	500 ms before
	//===============================================================
	//===============================================================
	void addToRelockList(DeviceProxy dev, int validity) throws DevFailed
	{
		//	Check if it is the first relock
		if (relock_list==null)
		{
			//	Create hash table for admin devices object
			relock_list = new Hashtable();

			//	Create a thread to unlock all devices at exit
			Runtime.getRuntime().addShutdownHook(new ShutdownThread());

			//	Create thread fo garbage callector call preiodicaly
			new GarbageThread().start();
		}
		//	Check if admin device already exists.
		String	adm;
		try {
			adm = dev.adm_name();
		} catch(DevFailed e) {
			//	Give up
			return;
		}
		LockedDevice		ld  = new LockedDevice(dev.get_name(), validity);
		LockedDeviceAmin	lda;
		if (!relock_list.containsKey(adm))
		{
			//	if not, crate and add th the list
			lda = new LockedDeviceAmin(adm, ld);
			lda.start();
			relock_list.put(adm, lda);
		}
		else
		{
			lda = (LockedDeviceAmin)relock_list.get(adm);
			lda.add(ld);
		}
	}
	//===============================================================
	//===============================================================
	void removeToRelockList(DeviceProxy dev)
	{
		//	Check if admin device already exists.
		String	adm;
		try {
			adm = dev.adm_name();
		} catch(DevFailed e) {
			//	Give up
			return;
		}
		if (relock_list.containsKey(adm))
		{
			LockedDeviceAmin	lda = (LockedDeviceAmin)relock_list.get(adm);
			lda.remove(dev.get_name());
		}
	}
	//===============================================================
	//===============================================================


	//===============================================================
	/**
	 *	A thread to unlock all devices at exit
	 */
	//===============================================================
	private class ShutdownThread extends Thread
	{
		public void run()
		{
			System.out.println("exiting.....");
			Enumeration keys = relock_list.keys();
			while (keys.hasMoreElements())
			{
				String	key = (String) keys.nextElement();
				LockedDeviceAmin	lda =
					(LockedDeviceAmin)relock_list.get(key);
				lda.cleanUp();
			}
		}
	}
	//===============================================================
	/**
	 *	A thread to activate the garbage collector
	 *	to do not re-lock the unsuded devices (finalize() will be called).
	 */
	//===============================================================
	private class GarbageThread extends Thread
	{
		public void run()
		{
			long	period = 
				1000*TangoConst.DEFAULT_LOCK_VALIDITY-VALIDITY_DELAY;
			//noinspection InfiniteLoopStatement
			while(true)
			{
				//	Call GC only if any device to relock
				if (relock_list.size()>0)
					System.gc();
				try { sleep(period); }
				catch (InterruptedException e) {}
			}
		}
	}
	//===============================================================
	/**
	 *	Loccked device object to relocked periodicaly
	 */
	//===============================================================
	private class LockedDevice
	{
		private String		name;
		private int			validity;
		//===========================================================
		LockedDevice(String devname, int valid)
		{
			name     = devname;
			validity = valid;
		}
	}
	//===============================================================
	/**
	 *	One thread class for each admin device, to ReLock devices.
	 */
	//===============================================================
	private class LockedDeviceAmin	extends Thread
	{
		private DeviceProxy	device = null;
		private String		name;
		private long		t_relock;
		private Vector<LockedDevice>	devices;
		//==========================================================
		LockedDeviceAmin(String name, LockedDevice ld) throws DevFailed
		{
			this.device = DeviceProxyFactory.get(name);
			this.name   = name;
			devices  = new Vector<LockedDevice>();
			devices.add(ld);
			t_relock = System.currentTimeMillis();
		}
		//==========================================================
		private void add(LockedDevice ld_in)
		{
			//	Check if Already exists.
			for (LockedDevice ld_tmp : devices)
				if (ld_tmp.name.equals(ld_in.name))
					return;	//	if yes -> do not relock more than once

			devices.add(ld_in);
			wakeUp();
		}
		//==========================================================
		private String[] getDeviceNames()
		{
			String[]	array = new String[devices.size()];
			for (int i=0 ; i<devices.size() ; i++)
				array[i] = devices.get(i).name;
			return array;
		}
		//==========================================================
		private void remove(String devname)
		{
			for (int i=0 ; i<devices.size() ; i++)
			{
				LockedDevice	ld = devices.get(i);
				if (ld.name.equals(devname))
				{
					System.out.println("------- >removing " + devname);
					devices.remove(ld);
				}
			}
			//	If no device any more remove itself.
			if (devices.size()==0)
				relock_list.remove(name);
			wakeUp();
		}
		//==========================================================
		private void cleanUp()
		{
			//	Unlock and remove all devices.			
			try
			{
				DevVarLongStringArray	lsa = new DevVarLongStringArray();
				lsa.svalue = new String[devices.size()];
				for (int i=0 ; i<devices.size() ; i++)
					lsa.svalue[i] = devices.get(0).name;
				lsa.lvalue = new int[1];
				lsa.lvalue[0] = 1;	//	Forced
				DeviceData	argin = new DeviceData();
				argin.insert(lsa);

				DeviceProxy	dev = DeviceProxyFactory.get(name);
				dev.import_admin_device("CleanUp");
				dev.getAdm_dev().command_inout("UnlockDevice", argin);
				System.out.println("all devices unlocked.");
			}
			catch (DevFailed e) {
				Except.print_exception(e);
			}
		}
		//===========================================================
		/**
		 *	Compute the minimum value to sleep for all devices
		 */
		//==========================================================
		private int getMinValidity()
		{
			int	min = 0xFFFFFFF;
			for (LockedDevice dev : devices)
				if (min > dev.validity)
					min = dev.validity;
			
			if (min==0xFFFFFFF)	//	case of noting found
				min = 1;
			return min;
		}
		//===========================================================
		private void traceRelock(String[] devnames)
		{
			System.out.print(name + ":	RelockDevices for ");
			for (String devname : devnames)
				System.out.print(" " + devname);
			System.out.println();
		}
		//===========================================================
		private void relock()
		{
			String[]	devnames = getDeviceNames();
			traceRelock(devnames);
			try
			{
				DeviceData	argin = new DeviceData();
				argin.insert(devnames);
				device.command_inout("RelockDevices", argin);
				
			}
			catch(DevFailed e)
			{
				//	Check exception for special cases
				for (DevError error : e.errors)
				{
					String reason = error.reason;
					if (reason.equals("TangoApi_DEVICE_NOT_EXPORTED"))
					{
						//	If admin device not exported,
						//		remove alle devices.
						for (String devname : devnames)
							remove(devname);
					}
					else if (reason.equals("API_DeviceNotLocked") ||	//	Server could have been restarted.
							reason.equals("API_DeviceLocked"))	//	Another client has lokced (Is it possible ??)
					{
						//	Parse for device name
						String desc = error.desc;
						int idx = desc.indexOf(':');
						if (idx > 0)
						{
							String devname = desc.substring(0, idx).trim();
							remove(devname);
						}
					}
				}
			}
		}
		//==========================================================
		private synchronized void wakeUp()
		{
			notify();
		}
		//===========================================================
		private long getTimeToSleep()
		{
			long	now = System.currentTimeMillis();
			int		minValidity = getMinValidity() * 1000;
			return minValidity - (now-t_relock) - VALIDITY_DELAY;	//	few ms before
		}         
		//===========================================================
		private synchronized void waitNext()
		{
			long	t_sleep = getTimeToSleep();
			while (t_sleep>VALIDITY_DELAY && devices.size()>0)
			{
				try {
					wait(t_sleep);
				}
				catch (InterruptedException e) {}
				t_sleep = getTimeToSleep();
			}
		}
		//===========================================================
		public void run()
		{
			while (devices.size()>0)
			{
				//	RE lock will be done later
				waitNext();

				if (devices.size()>0)
				{
					relock();
					t_relock = System.currentTimeMillis();
				}
			}
			System.out.println("thread for " + name + "  exiting....");
		}
		//===========================================================
	}
	//===============================================================
	//===============================================================
}
