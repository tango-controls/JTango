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
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne
 */
public class ModeAbsolu extends ModeRoot
{
	/**
	 * the <I>upper limit</I> field of the object
	 */
	private double val_inf = 0;
	/**
	 * the <I>lower limit</I> field of the object
	 */
	private double val_sup = 0;

	/**
	 * Default constructor
	 *
	 * @see #ModeAbsolu(double, double)
	 * @see #ModeAbsolu(int, double, double)
	 */
	public ModeAbsolu()
	{
	}

	/**
	 * This constructor takes two parameters as inputs.
	 *
	 * @param i Any received event upper value below this parameter is archived
	 * @param s Any received event with value upper this parameter is archived
	 * @see #ModeAbsolu()
	 * @see #ModeAbsolu(int, double, double)
	 */
	public ModeAbsolu(double i , double s)
	{
		val_inf = i;
		val_sup = s;
	}

	/**
	 * This constructor takes three parameters as inputs :
	 *
	 * @param p Archiving (polling) period time
	 * @param i Any received event upper value below this parameter  is archived
	 * @param s Any received event with value upper this parameter  is archived
	 * @see #ModeAbsolu()
	 * @see #ModeAbsolu(int, double, double)
	 */
	public ModeAbsolu(int p , double i , double s)
	{
		super.period = p;
		val_inf = i;
		val_sup = s;
	}

	/**
	 * Returns the <I>lower limit</I> field of the object.
	 *
	 * @return the <I>lower limit</I> field of the object.
	 * @see #setValInf
	 * @see #getValSup
	 * @see #setValSup
	 */
	public double getValInf()
	{
		return val_inf;
	}

	/**
	 * Sets the <I>lower limit</I> field of the object.
	 *
	 * @param i : the <I>lower limit</I> field to set.
	 * @see #getValInf
	 * @see #getValSup
	 * @see #setValSup
	 */
	public void setValInf(double i)
	{
		val_inf = i;
	}

	/**
	 * Returns the <I>upper limit</I> field of the object.
	 *
	 * @return the <I>upper limit</I> field of the object.
	 * @see #getValInf
	 * @see #setValInf
	 * @see #setValSup
	 */
	public double getValSup()
	{
		return val_sup;
	}

	/**
	 * Sets the <I>upper limit</I> field of the object.
	 *
	 * @param s : the <I>upper limit</I> field to set..
	 * @see #getValInf
	 * @see #setValInf
	 * @see #getValSup
	 */
	public void setValSup(double s)
	{
		val_sup = s;
	}

	/**
	 * Returns a string representation of the object <I>ModeAbsolu</I>.
	 *
	 * @return a string representation of the object <I>ModeAbsolu</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		buf.append("[" + Tag.MODE_A_TAG + "\r\n" +
		           "\t(" + Tag.MODE_P0_TAG + " = \"" + this.getPeriod() + "\" " + Tag.TIME_UNIT + "\r\n" +
		           "\t" + Tag.MODE_A1_TAG + " = \"" + this.getValInf() + "\" \r\n" +
		           "\t" + Tag.MODE_A2_TAG + " = \"" + this.getValSup() + "\"" + ")]");
		return buf.toString();
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof ModeAbsolu ) ) return false;
		if ( !super.equals(o) ) return false;

		final ModeAbsolu modeAbsolu = ( ModeAbsolu ) o;

		if ( val_inf != modeAbsolu.val_inf ) return false;
		if ( val_sup != modeAbsolu.val_sup ) return false;

		return true;
	}

	public int hashCode()
	{
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(val_inf);
		result = 29 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
		temp = Double.doubleToLongBits(val_sup);
		result = 29 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
		return result;
	}

    public String toStringWatcher() 
    {
        return "MODE_A + " + super.getPeriod() + " + " + this.getValInf() + " + " + this.getValSup();
    }
}