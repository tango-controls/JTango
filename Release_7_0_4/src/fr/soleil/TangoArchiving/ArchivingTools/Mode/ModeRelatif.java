//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: This object is one of the Mode class fields.
//				This class describes the 'relative mode' (the archiving occurs each time the received value is upper a specified proportion of the last archived value).
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
 * This class describes the 'relative mode' (the archiving occurs each time the received value is upper a specified proportion of the last archived value).<BR>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne
 */

public class ModeRelatif extends ModeRoot
{
	/**
	 * the <I>lower proportion limit</I> field of the object
	 */
	private double percent_inf = 0;
	/**
	 * the <I>upper proportion limit</I> field of the object
	 */
	private double percent_sup = 0;

	/**
	 * Default constructor
	 *
	 * @see #ModeRelatif(double, double)
	 * @see #ModeRelatif(int, double, double)
	 */
	public ModeRelatif()
	{
	}

	/**
	 * This constructor takes two parameters as inputs.
	 *
	 * @param p_inf the lower proportion limit
	 * @param p_sup the upper proportion limit
	 */
	public ModeRelatif(double p_inf , double p_sup)
	{
		percent_inf = Math.abs(p_inf);
		percent_sup = Math.abs(p_sup);
	}

	/**
	 * This constructor takes two parameters as inputs.
	 *
	 * @param pe    the archiving (polling) period time
	 * @param p_inf the lower proportion limit
	 * @param p_sup the upper proportion limit
	 */
	public ModeRelatif(int pe , double p_inf , double p_sup)
	{
		super.period = pe;
		percent_inf = Math.abs(p_inf);
		percent_sup = Math.abs(p_sup);
	}

	/**
	 * Gets the lower proportion limit
	 *
	 * @return the lower proportion limit
	 */
	public double getPercentInf()
	{
		return percent_inf;
	}

	/**
	 * Sets the lower proportion limit
	 *
	 * @param p_inf the lower proportion limit
	 */
	public void setPercentInf(double p_inf)
	{
		percent_inf = Math.abs(p_inf);
	}

	/**
	 * Gets the upper proportion limit
	 *
	 * @return the upper proportion limit
	 */
	public double getPercentSup()
	{
		return percent_sup;
	}

	/**
	 * Sets the upper proportion limit
	 *
	 * @param p_sup the upper proportion limit
	 */
	public void setPercentSup(double p_sup)
	{
		percent_sup = Math.abs(p_sup);
	}

	/**
	 * Returns a string representation of the object <I>ModeRelatif</I>.
	 *
	 * @return a string representation of the object <I>ModeRelatif</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		buf.append("[" + Tag.MODE_R_TAG + "\t\r\n" +
		           "\t(" + Tag.MODE_P0_TAG + " = \"" + this.getPeriod() + "\" " + Tag.TIME_UNIT + "\r\n" +
		           "\t" + Tag.MODE_R1_TAG + " = \"" + this.getPercentInf() + "\" \r\n" +
		           "\t" + Tag.MODE_R2_TAG + " = \"" + this.getPercentSup() + "\" )]\r\n");
		return buf.toString();
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof ModeRelatif ) ) return false;
		if ( !super.equals(o) ) return false;

		final ModeRelatif modeRelatif = ( ModeRelatif ) o;

		if ( percent_inf != modeRelatif.percent_inf ) return false;
		if ( percent_sup != modeRelatif.percent_sup ) return false;

		return true;
	}

	public int hashCode()
	{
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(percent_inf);
		result = 29 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
		temp = Double.doubleToLongBits(percent_sup);
		result = 29 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
		return result;
	}

    public String toStringWatcher() 
    {
        return "MODE_R + " + super.getPeriod() + " + " + this.getPercentInf() + " + " + this.getPercentSup();
    }
}