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
// $Revision: 25297 $
//
//-======================================================================


package fr.esrf.TangoDs;

/**
 *	This class is used to synchronise device access between
 *	polling thread and CORBA request. It is used only for
 *	the command_inout and read_attribute calls	
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25297 $
 */

import fr.esrf.Tango.DevFailed;

public class TangoMonitor implements TangoConst {
    boolean used;
    long _timeout;
    int locked_ctr;
    // ===============================================================
    // ===============================================================
    private boolean interrupted;

    // omni_condition cond;
    // omni_thread locking_thread;
    // ===============================================================
    // ===============================================================
    public TangoMonitor() {
	// cond(this);
	used = false;
	// locking_thread(NULL)
	locked_ctr = 0;
	_timeout = Tango_DEFAULT_TIMEOUT;
    }

    // ===============================================================
    // ===============================================================
    void timeout(final long new_to) {
	_timeout = new_to;
    }

    // ===============================================================
    // ===============================================================
    long timeout() {
	return _timeout;
    }

    // ===============================================================
    // ===============================================================
    synchronized void get_monitor() throws DevFailed {
	boolean interupted;
	Util.out4.println("In get_monitor(), used = " + used);

	final boolean owner_thread = false;
	/*
	 * if ((locking_thread != NULL) && (omni_thread::self() ==
	 * locking_thread)) { owner_thread = true;
	 * Util.out4.println("owner_thread !!"); }
	 */

	while (used == true && owner_thread == false) {
	    Util.out4.println("waiting !!");
	    interupted = wait_it(_timeout);

	    if (used == true && interupted == false) {
		Util.out4.println("TIME OUT for thread ");
		Except.throw_exception("API_CommandTimedOut", "Not able to acquire device monitor",
			"TangoMonitor.get_monitor");
	    }
	}
	locked_ctr++;
	if (!owner_thread) {
	    // locking_thread = omni_thread::self();
	    used = true;
	}
    }

    // ===============================================================
    // ===============================================================
    synchronized void rel_monitor() {
	Util.out4.println("In rel_monitor(), used = " + used + ", ctr = " + locked_ctr);
	if (used == true) {
	    locked_ctr--;
	    if (locked_ctr == 0) {
		Util.out4.println("Signalling !");
		used = false;
		// locking_thread = NULL;
		notify();
	    }
	}
    }

    // ===============================================================
    // ===============================================================
    synchronized void signal() {
	interrupted = true;
	notify();
    }

    // ===============================================================
    // ===============================================================
    synchronized void wait_it() {
	try {
	    wait();
	} catch (final InterruptedException e) {
	}
    }

    synchronized boolean wait_it(final long ms) {
	interrupted = false;
	try {
	    wait(ms);
	} catch (final InterruptedException e) {
	}

	return interrupted;
    }
    // ===============================================================
    // ===============================================================
}
