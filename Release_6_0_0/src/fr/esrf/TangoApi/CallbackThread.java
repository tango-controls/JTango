//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the CallbackThread class definition .
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:27  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;



/**
 *	This class get the asynchronous call result and
 *	send it to a CallBack object.
 */


public class  CallbackThread extends Thread implements ApiDefs
{
	private AsyncCallObject	aco;

	//===============================================================
	/**
	 *	Object constructor
	 *
	 *	@param	dev		Device Proxy instance to get result.
	 *	@param	id		CORBA.Request identificator.
	 *	@param	cmdname	Command to get result.
	 *	@param	cb		Object to send result
	 */
	//===============================================================
	public CallbackThread(AsyncCallObject aco)
	{
		this.aco = aco;
	}
	//===============================================================
	/**
	 *	Start thread.
	 */
	//===============================================================
	public  void run()
	{
		//System.out.println("I am in thread!");

		//	All info are in AsyncCallObjec. It does everything itself
		aco.manage_reply(0);
	}
	//===============================================================
	//===============================================================
}
