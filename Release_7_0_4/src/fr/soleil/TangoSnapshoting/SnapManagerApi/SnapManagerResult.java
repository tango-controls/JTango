//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  SnapManagerResult.
//						(Chinkumo Jean) - Nov 2, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.16.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoSnapshoting.SnapManagerApi;


public interface SnapManagerResult
{
	/**
	 * SnapLaunch command succeed
	 */
	public static final short OK_SNAPLAUNCH = 0;
	/**
	 * SnapLaunch command failed because the driver sent a DevFailed exception
	 */
	public static final short ERROR_SNAPLAUNCH = 1;
	/**
	 * CreateContext  command succeed
	 */
	public static final short OK_CONTEXTCREATION = 2;
	/**
	 * CreateContext command failed because the driver sent a DevFailed exception
	 */
	public static final short ERROR_CONTEXTCREATION = 3;

	/**
	 * Command failed because there is no running SnapArchiver device
	 */
	public static final short INEXISTANT_ARCHIVER = 4;

	/**
	 * Command failed because the given attribute does not exist in TANGO
	 */
	public static final short INEXISTANT_ATTRIBUTE = 5;
	/**
	 * Attribute registration command succeed
	 */
	public static final short OK_ATTRIBUTE_REGISTRATION = 6;
	/**
	 * Attribute registration failed
	 */
	public static final short ERROR_ATTRIBUTE_REGISTRATION = 7;
	/**
	 * Command failed because the connection to the snap database is not open
	 */
	public static final short UNCONNECTECTED_SNAPDATABASE = 8;

	/**
	 * Command failed because there is a probleme with database dialog (SQL exception)
	 */
	public static final short CANNOT_TALK_TO_SNAPDATABASE = 9;
	/**
	 * There is no specified result yet
	 */
	public static final short NO_RESULT = -1;

}
