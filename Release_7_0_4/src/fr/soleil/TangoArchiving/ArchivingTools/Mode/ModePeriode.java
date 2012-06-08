//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: This object is one of the Mode class fields.
//				This class describes the 'periodic mode' (the archiving occurs folowing a specified period).
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2006/10/30 14:36:07  ounsy
// added a toStringWatcher method used by the ArchivingWatcher's getAllArchivingAttributes command
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.16.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2  2005/01/26 15:35:38  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :   Synchrotron SOLEIL
//			    L'Orme des Merisiers
//			    Saint-Aubin - BP 48
//			    91192 GIF-sur-YVETTE CEDEX
//              FRANCE
//
//+============================================================================

package fr.soleil.TangoArchiving.ArchivingTools.Mode;

/**
 * <p/>
 * <B>Description :</B><BR>
 * This object is one of the <I>Mode class</I> fields.
 * This class describes the 'periodic mode' (the archiving occurs folowing a specified period).<BR>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne
 */

public class ModePeriode extends ModeRoot
{
	/**
	 * Default constructor
	 */
	public ModePeriode()
	{
	}

	/**
	 * This constructor takes one parameter as inputs.
	 *
	 * @param p the archiving (polling) period time
	 */
	public ModePeriode(int p)
	{
		super(p);
	}

	/**
	 * Returns a string representation of the object <I>ModePeriode</I>.
	 *
	 * @return a string representation of the object <I>ModePeriode</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		buf.append("[" + Tag.MODE_P_TAG + "\r\n" +
		           "\t(" + Tag.MODE_P0_TAG + " = \"" + this.getPeriod() + "\" " + Tag.TIME_UNIT + ")]");
		return buf.toString();
	}

    public String toStringWatcher() 
    {
        return "MODE_P + " + super.getPeriod(); 
    }


}