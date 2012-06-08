//+============================================================================
//
// file :               KillThread.java
//
// description :        java source code for the KillThread class. 
//			This class derived from the java Thread class. This
//			thread is created and started by the Kill command
//			of the dserver object automatically associated to
//			each device server process.
//			This thread simply kills the server
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 3.5  2004/03/12 14:07:57  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:57  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:24  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:37  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:21  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:53  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:23:01  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 09:10:00  taurel
// Just update revision number
//
// Revision 1.1.1.1  2000/02/04 09:08:24  taurel
// Imported sources
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;


class KillThread extends Thread
{

	public void run()
	{
		Util.out4.println("In the killer thread !!!");

		try
		{	
			sleep(1000);
		}
		catch (InterruptedException ex)
		{
		}

//
// Unregister server device(s) from database
//

		Util.instance().unregister_server();
	
//
// Now, the suicide
//
	
		System.exit(-1);
	}
}
