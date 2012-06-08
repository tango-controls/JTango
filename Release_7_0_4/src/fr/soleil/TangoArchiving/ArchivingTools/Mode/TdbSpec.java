//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: This object completes the Mode's structure when archiving is not 'historic' but 'temporary'.
//				This class adds the 'export time' and the 'keeping time' notions to the Mode concetp.
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

public class TdbSpec
{

	private long exportPeriod = 0;
	private long keepingPeriod = 0;

	public TdbSpec(long exportPeriod , long keepingPeriod)
	{
		this.exportPeriod = exportPeriod;
		this.keepingPeriod = keepingPeriod;
	}

	public long getExportPeriod()
	{
		return exportPeriod;
	}

	public void setExportPeriod(long exportPeriod)
	{
		this.exportPeriod = exportPeriod;
	}

	public long getKeepingPeriod()
	{
		return keepingPeriod;
	}

	public void setKeepingPeriod(long keepingPeriod)
	{
		this.keepingPeriod = keepingPeriod;
	}

	/**
	 * Returns a string representation of the object <I>ModePeriode</I>.
	 *
	 * @return a string representation of the object <I>ModePeriode</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		buf.append("[" + Tag.MODE_SPEC_TAG + "\r\n" +
		           "\t(" + Tag.MODE_SPEC1_TAG + " = \"" + this.getExportPeriod() + "\" \r\n" +
		           "\t(" + Tag.MODE_SPEC2_TAG + " = \"" + this.getKeepingPeriod() + "\" )]\r\n");
		return buf.toString();
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof TdbSpec ) ) return false;

		final TdbSpec tdbSpec = ( TdbSpec ) o;

		if ( exportPeriod != tdbSpec.exportPeriod ) return false;
		if ( keepingPeriod != tdbSpec.keepingPeriod ) return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = ( int ) ( exportPeriod ^ ( exportPeriod >>> 32 ) );
		result = 29 * result + ( int ) ( keepingPeriod ^ ( keepingPeriod >>> 32 ) );
		return result;
	}
}
