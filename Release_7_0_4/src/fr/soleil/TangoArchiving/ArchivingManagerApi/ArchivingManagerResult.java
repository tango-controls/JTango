//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ArchivingManagerResult.
//						(HO) - 18 mai 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.5  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.4.12.2  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.4.12.1  2005/09/09 08:32:07  chinkumo
// Minor changes.
//
// Revision 1.4  2005/06/14 10:12:17  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.3.4.1  2005/06/13 15:14:24  chinkumo
// This class chenged since many constants were moved to the ConfigConst class.
// This is a consequence of managing exceptions.
//
// Revision 1.3  2005/02/04 17:23:04  chinkumo
// The grouped stopping functionnality was added.
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
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

package fr.soleil.TangoArchiving.ArchivingManagerApi;

/**
 * @author HO
 *         <p/>
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ArchivingManagerResult
{
	/**
	 * ArchivingStartHdb/Tdb command succeed
	 */
	public static final short OK_ARCHIVINGSTART = 0;

	/**
	 * ArchivingStopHdb/Tdb command succeed
	 */
	public static final short OK_ARCHIVINGSTOP = 3;

	/**
	 * ArchivingModif command succeed
	 */
	public static final short OK_ARCHIVINGMODIF = 6;


	public static final String CONNECTION_SUCCEED = "Connection succeed";
	public static final String CONNECTION_FAILED = "Connection succeed";


}
