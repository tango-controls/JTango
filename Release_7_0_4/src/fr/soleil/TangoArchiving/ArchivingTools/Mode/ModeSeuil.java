//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: This object is one of the Mode class fields.
//				This class describes the absolute mode (the archiving occurs each time the received value is upper/lower a specified value).
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2006/10/30 14:36:06  ounsy
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
 * This class describes the absolute mode (the archiving occurs each time the received value is upper/lower a specified value).<BR>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 * @see Mode
 * @see ModePeriode
 * @see ModeRelatif
 * @see ModeCalcul
 * @see ModeDifference
 * @see ModeExterne
 */
public class ModeSeuil extends ModeRoot
{
	/**
	 * the <I>upper limit</I> field of the object
	 */
	private double threshold_inf = 0;
	/**
	 * the <I>lower limit</I> field of the object
	 */
	private double threshold_sup = 0;

	/**
	 * Default constructor
	 *
	 * @see #ModeSeuil(double, double)
	 * @see #ModeSeuil(int, double, double)
	 */
	public ModeSeuil()
	{
	}

	/**
	 * This constructor takes two parameters as inputs.
	 *
	 * @param i Any received event upper value below this parameter is archived
	 * @param s Any received event with value upper this parameter is archived
	 * @see #ModeSeuil()
	 * @see #ModeSeuil(int, double, double)
	 */
	public ModeSeuil(double i , double s)
	{
		threshold_inf = i;
		threshold_sup = s;
	}

	/**
	 * This constructor takes three parameters as inputs :
	 *
	 * @param p Archiving (polling) period time
	 * @param i Any received event upper value below this parameter  is archived
	 * @param s Any received event with value upper this parameter  is archived
	 * @see #ModeSeuil()
	 * @see #ModeSeuil(int, double, double)
	 */
	public ModeSeuil(int p , double i , double s)
	{
		super.period = p;
		threshold_inf = i;
		threshold_sup = s;
	}

	/**
	 * Returns the <I>lower limit</I> field of the object.
	 *
	 * @return the <I>lower limit</I> field of the object.
	 * @see #setThresholdInf
	 * @see #getThresholdSup
	 * @see #setThresholdSup
	 */
	public double getThresholdInf()
	{
		return threshold_inf;
	}

	/**
	 * Sets the <I>lower limit</I> field of the object.
	 *
	 * @param i : the <I>lower limit</I> field to set.
	 * @see #getThresholdInf
	 * @see #getThresholdSup
	 * @see #setThresholdSup
	 */
	public void setThresholdInf(double i)
	{
		threshold_inf = i;
	}

	/**
	 * Returns the <I>upper limit</I> field of the object.
	 *
	 * @return the <I>upper limit</I> field of the object.
	 * @see #getThresholdInf
	 * @see #setThresholdInf
	 * @see #setThresholdSup
	 */
	public double getThresholdSup()
	{
		return threshold_sup;
	}

	/**
	 * Sets the <I>upper limit</I> field of the object.
	 *
	 * @param s : the <I>upper limit</I> field to set..
	 * @see #getThresholdInf
	 * @see #setThresholdInf
	 * @see #getThresholdSup
	 */
	public void setThresholdSup(double s)
	{
		threshold_sup = s;
	}

	/**
	 * Returns a string representation of the object <I>ModeSeuil</I>.
	 *
	 * @return a string representation of the object <I>ModeSeuil</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		buf.append("[" + Tag.MODE_T_TAG + "\r\n" +
		           "\t(" + Tag.MODE_P0_TAG + " = \"" + this.getPeriod() + "\" " + Tag.TIME_UNIT + "\r\n" +
		           "\t" + Tag.MODE_T1_TAG + " = \"" + this.getThresholdInf() + "\" \r\n" +
		           "\t" + Tag.MODE_T2_TAG + " = \"" + this.getThresholdSup() + "\"" + ")]");
		return buf.toString();
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof ModeSeuil ) ) return false;
		if ( !super.equals(o) ) return false;

		final ModeSeuil ModeSeuil = ( ModeSeuil ) o;

		if ( threshold_inf != ModeSeuil.threshold_inf ) return false;
		if ( threshold_sup != ModeSeuil.threshold_sup ) return false;

		return true;
	}

	public int hashCode()
	{
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(threshold_inf);
		result = 29 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
		temp = Double.doubleToLongBits(threshold_sup);
		result = 29 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
		return result;
	}

    public String toStringWatcher() 
    {
        return "MODE_T + " + super.getPeriod() + " + " + this.getThresholdInf() + " + " + this.getThresholdSup();
    }
}