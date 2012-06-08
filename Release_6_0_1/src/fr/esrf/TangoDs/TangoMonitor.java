//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description: This class is used to synchronise device access between
//			polling thread and CORBA request. It is used only for
//			the command_inout and read_attribute calls	
//
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 3.2  2005/12/02 09:55:46  pascal_verdier
// java import have been optimized.
//
// Revision 3.1  2004/05/14 13:47:56  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
//
// Copyleft 2000 by European Synchrotron Radiation Facility, Grenoble, France
//-======================================================================
package fr.esrf.TangoDs;

/**
 *	This class is used to synchronise device access between
 *	polling thread and CORBA request. It is used only for
 *	the command_inout and read_attribute calls	
 *
 * @author	$Author$
 * @version	$Revision$
 */

import fr.esrf.Tango.DevFailed;

class TangoMonitor implements TangoConst
{
	boolean		used;
	long 		_timeout;
	int			locked_ctr;
	//omni_condition 	cond;
	//omni_thread	locking_thread;
	//===============================================================
	//===============================================================
	TangoMonitor()
	{
		//cond(this);
		used = false;
		//locking_thread(NULL)
		locked_ctr = 0;
		_timeout = Tango_DEFAULT_TIMEOUT;
	}
	//===============================================================
	//===============================================================
	void timeout(long new_to)
	{
		_timeout = new_to;
	}
	//===============================================================
	//===============================================================
	long timeout()
	{
		return _timeout;
	}
	//===============================================================
	//===============================================================
	synchronized void get_monitor() throws DevFailed
	{
		boolean interupted;
		Util.out4.println("In get_monitor(), used = " + used );

		boolean owner_thread = false;
		/*
		if ((locking_thread != NULL) && (omni_thread::self() == locking_thread))
		{
			owner_thread = true;
			Util.out4.println("owner_thread !!");
		}
		*/

		while ((used == true) && (owner_thread == false))
		{
			Util.out4.println("waiting !!");
			interupted = wait_it(_timeout);

			if ((used == true) && (interupted == false))
			{			
				Util.out4.println("TIME OUT for thread ");
				Except.throw_exception("API_CommandTimedOut",
					        	"Not able to acquire device monitor",
					        	"TangoMonitor.get_monitor");
			}
		}
		locked_ctr++;
		if (!owner_thread)
		{
			//locking_thread = omni_thread::self();
			used = true;
		}
	}
	//===============================================================
	//===============================================================
	synchronized void rel_monitor()
	{
		Util.out4.println("In rel_monitor(), used = " + used + ", ctr = " + locked_ctr);
		if ((used == true) /*&& (omni_thread::self() == locking_thread)*/)
		{
			locked_ctr--;
			if (locked_ctr == 0)
			{
				Util.out4.println("Signalling !");
				used = false;
				//locking_thread = NULL;
				notify();
			}
		}
	}
	//===============================================================
	//===============================================================
	synchronized void signal()
	{
		interrupted = true;
		notify();
	}
	//===============================================================
	//===============================================================
	synchronized void wait_it()
	{
		try {
			wait();
		}
		catch(InterruptedException e) { }
	}
	//===============================================================
	//===============================================================
	private boolean	interrupted;
	synchronized boolean wait_it(long ms)
	{
		interrupted = false;
		try {
			wait(ms);
		}
		catch(InterruptedException e) {
		}

		return interrupted;
	}
	//===============================================================
	//===============================================================
}
