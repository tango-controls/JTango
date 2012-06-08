//+============================================================================
//
// file :               StopLoggingCmd.java
//
// description :        This structure is used to shared data between
//                      the polling thread and the main thread
//
// project :            TANGO
//
// author(s) :          P. Verdier
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;



public class PollThCmd
{
	/**
	 *	The new command flag
	 */
	boolean		cmd_pending;
	/**
	 *	The command code
	 */
	int			cmd_code;
	/**
	 *	The device pointer (servant)
	 */
	DeviceImpl	dev;
	/**
	 *	Index in the device poll_list
	 */
	int			index;
	/**
	 *	Object name
	 */
	String		name;
	/**
	 *	Object type (cmd/attr)
	 */
	int			type;
	/**
	 *	New update period (For upd period com.)
	 */
	int			new_upd;
	/**
	 *	is a trigger ?
	 */
	boolean		trigger = false;
}
