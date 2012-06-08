//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: This object is one of the Mode class fields.
//				This class describes the 'on calculation mode' (the archived value is the result of one or several treated values).
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
 * This object is one of the <I>Mode class</I> fields.
 * This class describes the 'on calculation mode' (the archived value is the result of one or several treated values).<BR>
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne
 */
public class ModeCalcul extends ModeRoot
{
	private int range;
	private int calcul_type;

	/**
	 * Default constructor
	 *
	 * @see #ModeCalcul(int, int)
	 * @see #ModeCalcul(int, int, int)
	 */
	public ModeCalcul()
	{
	}

	/**
	 * This constructor takes two parameters as inputs.
	 *
	 * @param ra  Number of events taken into account to archive one value
	 * @param c_t Type of calculation applied to the data
	 * @see #ModeCalcul()
	 * @see #ModeCalcul(int, int, int)
	 */
	public ModeCalcul(int ra , int c_t)
	{
		range = ra;
		calcul_type = c_t;
	}

	/**
	 * This constructor takes three parameters as inputs :
	 *
	 * @param pe  Archiving (polling) pe time
	 * @param ra  Number of events taken into account to archive one value
	 * @param c_t Type of calculation applied to the data
	 * @see #ModeCalcul()
	 * @see #ModeCalcul(int, int)
	 */
	public ModeCalcul(int pe , int ra , int c_t)
	{
		super.period = pe;
		range = ra;
		calcul_type = c_t;
	}

	/**
	 * Gets the number of events taken into account to archive one value
	 *
	 * @return the number of events taken into account to archive one value
	 */
	public int getRange()
	{
		return range;
	}

	/**
	 * Sets the number of events taken into account to archive one value
	 *
	 * @param ra the number of events taken into account to archive one value
	 */
	public void setRange(int ra)
	{
		range = ra;
	}

	/**
	 * Gets the calculation type applied to the data
	 *
	 * @return the calculation type applied to the data
	 */
	public int getTypeCalcul()
	{
		return calcul_type;
	}

	/**
	 * Sets the calculation type applied to the data
	 *
	 * @param c_t the calculation type applied to the data
	 */
	public void setTypeCalcul(int c_t)
	{
		calcul_type = c_t;
	}

	/**
	 * Returns a string representation of the object <I>ModeCalcul</I>.
	 *
	 * @return a string representation of the object <I>ModeCalcul</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		buf.append(Tag.MODE_C_TAG + "\r\n" +
		           "\t" + Tag.MODE_P0_TAG + " = \"" + this.getPeriod() + "\" " + Tag.TIME_UNIT + "\r\n" +
		           "\t" + Tag.MODE_C1_TAG + " = \"" + this.getRange() + "\" \r\n" +
		           "\t" + Tag.MODE_C2_TAG + " = \"" + this.getTypeCalcul() + "\"");
		return buf.toString();
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof ModeCalcul ) ) return false;
		if ( !super.equals(o) ) return false;

		final ModeCalcul modeCalcul = ( ModeCalcul ) o;

		if ( calcul_type != modeCalcul.calcul_type ) return false;
		if ( range != modeCalcul.range ) return false;

		return true;
	}

	public int hashCode()
	{
		int result = super.hashCode();
		result = 29 * result + range;
		result = 29 * result + calcul_type;
		return result;
	}
}