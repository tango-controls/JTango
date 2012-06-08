//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: Of this class inherit all the modes containing the notion of "period".
//
// $Author$
//
// $Revision$
//
// $Log$
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
 * Of this class inherit all the modes containing the notion of "period".<BR>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne
 */

class ModeRoot
{
	int period = 0;

	/**
	 * Default constructor
	 *
	 * @see #ModeRoot(int)
	 */
	public ModeRoot()
	{
	}

	/**
	 * This constructor takes one parameter as inputs.
	 *
	 * @param pe Archiving (polling) period time
	 */
	public ModeRoot(int pe)
	{
		period = pe;
	}

	/**
	 * Gets the archiving (polling) period time
	 *
	 * @return the archiving (polling) period time
	 */
	public int getPeriod()
	{
		return period;
	}

	/**
	 * Sets the archiving (polling) period time
	 */
	public void setPeriod(int pe)
	{
		period = pe;
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof ModeRoot ) ) return false;

		final ModeRoot modeRoot = ( ModeRoot ) o;

		if ( period != modeRoot.period ) return false;

		return true;
	}

	public int hashCode()
	{
		return period;
	}
}