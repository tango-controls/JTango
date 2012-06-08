//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
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
// Revision 1.3  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.2  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.1  2008/09/12 11:20:53  pascal_verdier
// Tango 7 first revision.
//
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.LockerLanguage;
import fr.esrf.Tango.DevVarLongStringArray;
import fr.esrf.TangoDs.Except;

import java.util.UUID;

/**
 * Class Description: This class discribe the device locker information.
 */

public class LockerInfo
{
	private LockerLanguage	language;
	private int				cpp_pid = 0;
	private UUID			uuid;
	private String			hostname;
	private String			classname;
	private String			status;
	private boolean			locked;

	//	String part
	private static final int STATUS_IDX  = 0;
	private static final int ADD_IDX     = 1;
	private static final int CLASS_IDX   = 2;

	//	Numeric part
	private static final int LOCK_IDX    = 0;
	private static final int PID_IDX     = 1;
	private static final int UUID_IDX    = 2;


	//==========================================================================
	/**
	 *	Object constructor
	 *	@param	lsa	DevVarLongStringArray returned by DevLockStatus command.
	 */
	//==========================================================================
	LockerInfo(DevVarLongStringArray lsa)
	{
		//==========================
		//	Numeric part
		//==========================
		locked  = (lsa.lvalue[LOCK_IDX]!=0);
		cpp_pid = lsa.lvalue[PID_IDX];

		//	Check language
		if (cpp_pid==0)
		{
			//	No PID --> Java
			language = LockerLanguage.JAVA;
			long	msl = build64(lsa.lvalue[UUID_IDX],   lsa.lvalue[UUID_IDX+1]);
			long	lsl = build64(lsa.lvalue[UUID_IDX+2], lsa.lvalue[UUID_IDX+3]);
			uuid = new UUID(msl, lsl);
		}
		else
			language = LockerLanguage.CPP;


		//==========================
		//	String part
		//==========================
		status   = lsa.svalue[STATUS_IDX];
		hostname = parseHostAddress(lsa.svalue[ADD_IDX]);
		if (language==LockerLanguage.JAVA)
			classname = lsa.svalue[CLASS_IDX];
	}
	//==========================================================================
	//==========================================================================
	private String parseHostAddress(String str)
	{
		String	header = "giop:tcp:";
		if (str.startsWith(header))
			str = str.substring(header.length());

		int		end = str.lastIndexOf(':');
		if (end>0)
			str = str.substring(0, end);
		return str;
	}
	//==========================================================================
	//==========================================================================
	private long build64(int i1, int i2)
	{
		long	msi = (long)i1;
		long	lsi = (long)i2 & 0xFFFFFFFFL;
		return	(msi << 32) + lsi;
	}
	//==========================================================================
	/**
	 *	Returns true if locked
	 */
	//==========================================================================
	public boolean isLocked()
	{
		return locked;
	}
	//==========================================================================
	/**
	 *	Returns true if locker is a java process.
	 */
	//==========================================================================
	public boolean isJavaProcess()
	{
		return (language==LockerLanguage.JAVA);
	}
	//==========================================================================
	/**
	 *	Returns true if locker is a cpp process.
	 */
	//==========================================================================
	public boolean isCppProcess()
	{
		return (language==LockerLanguage.CPP);
	}
	//==========================================================================
	/**
	 *	Returns the locker UUID if java
	 *
	 *	@throws	DevFailed if locker is not a java process.
	 */
	//==========================================================================
	public UUID getJavaUUID() throws DevFailed
	{
		if (language==LockerLanguage.CPP)
			Except.throw_exception("API_WrongData",
				"The locker is not a java process",
				"LockerInfo.getJavaUUID()");
		return uuid;
	}
	//==========================================================================
	/**
	 *	Returns the locker Pricess ID if Cpp
	 *
	 *	@throws	DevFailed if locker is not a cpp process.
	 */
	//==========================================================================
	public int getCppPID() throws DevFailed
	{
		if (language==LockerLanguage.JAVA)
			Except.throw_exception("API_WrongData",
				"The locker is not a Cpp process",
				"LockerInfo.getCppPID()");
		return cpp_pid;
	}
	//==========================================================================
	/**
	 *	Returns the locker class name
	 */
	//==========================================================================
	public String getClassName()
	{
		return classname;
	}
	//==========================================================================
	/**
	 *	Returns the locker host name
	 */
	//==========================================================================
	public String getHostName()
	{
		return hostname;
	}
	//==========================================================================
	/**
	 *	Returns the locker host name
	 */
	//==========================================================================
	public String getStatus()
	{
		return status;
	}
	// ==========================================================================
	/**
	 *	Returns true if the device is locked by this process
	 */
	// ==========================================================================
	boolean isMe()
	{
		DevLockManager	dlm = DevLockManager.getInstance();
		//	Check main class name
		if (!dlm.getMainClass().equals(classname))
		{
			System.out.println(dlm.getMainClass() + "!=" + classname);
			return false;
		}
		//	Check UUID long values (msl and lsl)
		if (uuid.compareTo(dlm.getUUID())!=0)
		{
			System.out.println("UUID different");
			return false;
		}

		//	Check IP address
		return hostname.equals(dlm.getHost());
	}
	//==========================================================================
	//==========================================================================
	public String toString()
	{
		return status;
	}
	//==========================================================================
	//==========================================================================
}
