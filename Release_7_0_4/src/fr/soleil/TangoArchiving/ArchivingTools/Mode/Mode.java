//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:	A Mode is an object that describes the way an attribute is archived.
//				Let us note that a 'Mode' is a combination 'modes'.
//				A 'mode' can be periodic, absolute, relative, on calculation, on difference or external.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.5  2007/02/16 08:26:29  pierrejoseph
// The HDB minimal period is 10 seconds (instead of 1s).
//
// Revision 1.4  2006/10/30 14:36:07  ounsy
// added a toStringWatcher method used by the ArchivingWatcher's getAllArchivingAttributes command
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.16.2  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2.16.1  2005/09/26 08:39:14  chinkumo
// checkMode(..) method was added. This method checks if the current mode is valid (function of archiving type - historical/temporary).
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

import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.esrf.Tango.ErrSeverity;


/**
 * <p/>
 * <B>Description :</B><BR>
 * A Mode is an object that describes the way an attribute is archived.
 * Let us note that a 'Mode' is a combination 'modes'.
 * A 'mode' can be periodic, absolute, relative, on calculation, on difference or external.
 * </p>
 *
 * @author Jean CHINKUMO - Synchrotron SOLEIL
 * @version	$Revision$
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference
 * @see fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne
 */


public class Mode
{
	//private static int modeLengh = 18;
	private ModePeriode mode_p = null;
	private ModeAbsolu mode_a = null;
	private ModeRelatif mode_r = null;
	private ModeSeuil mode_s = null;
	private ModeCalcul mode_c = null;
	private ModeDifference mode_d = null;
	private ModeExterne mode_e = null;

	private TdbSpec tdbSpec = null;

	private static final int HDB_PERIOD_MIN_VALUE = 10000; // ms
	private static final int TDB_PERIOD_MIN_VALUE = 100; // ms
	/**
	 * Default constructor
	 *
	 * @see #Mode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode mp, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu ma, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif mr, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeSeuil ms, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul mc, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference md, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne me ).
	 */
	public Mode()
	{
	}

	/**
	 * Constructor
	 *
	 * @param mp : periodic mode
	 * @param ma : absolute mode
	 * @param mr : relative mode
	 * @param mc : on calculation mode
	 * @param md : on difference mode
	 * @param me : external mode
	 * @see #Mode().
	 */
	public Mode(ModePeriode mp , ModeAbsolu ma , ModeRelatif mr , ModeSeuil ms , ModeCalcul mc , ModeDifference md , ModeExterne me)
	{
		mode_p = mp;
		mode_a = ma;
		mode_r = mr;
		mode_s = ms;
		mode_c = mc;
		mode_d = md;
		mode_e = me;
	}

	/**
	 * Constructor
	 *
	 * @param mp : periodic mode
	 * @param ma : absolute mode
	 * @param mr : relative mode
	 * @param mc : on calculation mode
	 * @param md : on difference mode
	 * @param me : external mode
	 * @see #Mode().
	 */
	public Mode(ModePeriode mp , ModeAbsolu ma , ModeRelatif mr , ModeSeuil ms , ModeCalcul mc , ModeDifference md , ModeExterne me , TdbSpec ts)
	{
		mode_p = mp;
		mode_a = ma;
		mode_r = mr;
		mode_s = ms;
		mode_c = mc;
		mode_d = md;
		mode_e = me;
		tdbSpec = ts;
	}

	public void reinitialize()
	{
		mode_p = null;
		mode_a = null;
		mode_r = null;
		mode_s = null;
		mode_c = null;
		mode_d = null;
		mode_e = null;
		tdbSpec = null;
	}

	/**
	 * Constructor
	 *
	 * @param mode_array : an array containing all information defining the mode. This constructor will be primarily called when building a Mode from data that are coming from the database.
	 * @see #Mode().
	 * @see #Mode(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode mp, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu ma, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif mr, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeSeuil ms, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul mc, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference md, fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne me ).
	 */
	public Mode(String[] mode_array)
	{
		int index = 0;
		int my_array_size = mode_array.length;
		while ( index < my_array_size )
		{
			if ( mode_array[ index ].equals(Tag.MODE_P) )
			{
				// Mode Périodique
				mode_p = new ModePeriode(Integer.parseInt(mode_array[ index + 1 ]));
				index = index + 2;
			}
			else if ( mode_array[ index ].equals(Tag.MODE_A) )
			{
				// Mode Absolu
				mode_a = new ModeAbsolu(Integer.parseInt(mode_array[ index + 1 ]) ,
				                        Double.parseDouble(mode_array[ index + 2 ]) , Double.parseDouble(mode_array[ index + 3 ]));
				index = index + 4;
			}
			else if ( mode_array[ index ].equals(Tag.MODE_R) )
			{
				// Mode Relatif
				mode_r = new ModeRelatif(Integer.parseInt(mode_array[ index + 1 ]) ,
				                         Double.parseDouble(mode_array[ index + 2 ]) , Double.parseDouble(mode_array[ index + 3 ]));
				index = index + 4;
			}
			else if ( mode_array[ index ].equals(Tag.MODE_T) )
			{
				// Mode Relatif
				mode_s = new ModeSeuil(Integer.parseInt(mode_array[ index + 1 ]) ,
				                       Double.parseDouble(mode_array[ index + 2 ]) , Double.parseDouble(mode_array[ index + 3 ]));
				index = index + 4;
			}
			else if ( mode_array[ index ].equals(Tag.MODE_C) )
			{
				// Mode Calcul
				mode_c = new ModeCalcul(Integer.parseInt(mode_array[ index + 1 ]) ,
				                        Integer.parseInt(mode_array[ index + 2 ]) , Integer.parseInt(mode_array[ index + 3 ]));
				// Warning : there is one more field for this mode type
				index = index + 5;
			}
			else if ( mode_array[ index ].equals(Tag.MODE_D) )
			{
				// Mode Différent
				mode_d = new ModeDifference(Integer.parseInt(mode_array[ index + 1 ]));
				index = index + 2;
			}
			else if ( mode_array[ index ].equals(Tag.MODE_E) )
			{
				// Mode externe
				mode_e = new ModeExterne();
				index = index + 1;
			}
			else if ( mode_array[ index ].equals(Tag.TDB_SPEC) )
			{
				// Specific temporary database
				tdbSpec = new TdbSpec(Long.parseLong(mode_array[ index + 1 ]) , Long.parseLong(mode_array[ index + 2 ]));
				index = index + 3;
			}
			else
			{
				index++;
			}
		}
	}

	/*public static int getModeLengh() {
	    return modeLengh;
	}*/

	/**
	 * getModeP returns the object which corresponds to the 'periodic' field.
	 *
	 * @return ModePeriode
	 * @see #setModeP(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode)
	 * @see #getModeA()
	 * @see #getModeR()
	 * @see #getModeC()
	 * @see #getModeD()
	 * @see #getModeE()
	 */
	public ModePeriode getModeP()
	{
		return mode_p;
	}

	/**
	 * setModeP sets the object which corresponds to the 'periodic' field.
	 *
	 * @see #getModeP()
	 * @see #setModeA(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu)
	 * @see #setModeR(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif)
	 * @see #setModeC(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul)
	 * @see #setModeD(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference)
	 * @see #setModeE(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne)
	 */
	public void setModeP(ModePeriode mp)
	{
		mode_p = mp;
	}

	/**
	 * getModeA returns the object which corresponds to the 'absolute' field.
	 *
	 * @return ModeAbsolu
	 * @see #setModeA(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu)
	 * @see #getModeP()
	 * @see #getModeR()
	 * @see #getModeC()
	 * @see #getModeD()
	 * @see #getModeE()
	 */
	public ModeAbsolu getModeA()
	{
		return mode_a;
	}

	/**
	 * setModeA sets the object which corresponds to the 'absolute' field.
	 *
	 * @see #getModeA()
	 * @see #setModeP(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode)
	 * @see #setModeR(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif)
	 * @see #setModeC(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul)
	 * @see #setModeD(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference)
	 * @see #setModeE(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne)
	 */
	public void setModeA(ModeAbsolu ma)
	{
		mode_a = ma;
	}

	/**
	 * getModeR returns the object which corresponds to the 'relative' field.
	 *
	 * @return ModeRelatif
	 * @see #setModeR(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif)
	 * @see #getModeP()
	 * @see #getModeA()
	 * @see #getModeC()
	 * @see #getModeD()
	 * @see #getModeE()
	 */
	public ModeRelatif getModeR()
	{
		return mode_r;
	}

	/**
	 * setModeR sets the object which corresponds to the 'relative' field.
	 *
	 * @see #getModeR()
	 * @see #setModeP(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode)
	 * @see #setModeA(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu)
	 * @see #setModeC(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul)
	 * @see #setModeD(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference)
	 * @see #setModeE(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne)
	 */
	public void setModeR(ModeRelatif mr)
	{
		mode_r = mr;
	}

	/**
	 * getModeT returns the object which corresponds to the 'threshold' field.
	 *
	 * @return ModeCalcul
	 * @see #setModeC(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul)
	 * @see #getModeP()
	 * @see #getModeA()
	 * @see #getModeR()
	 * @see #getModeC()
	 * @see #getModeD()
	 * @see #getModeE()
	 */
	public ModeSeuil getModeT()
	{
		return mode_s;
	}

	/**
	 * setModeR sets the object which corresponds to the 'relative' field.
	 *
	 * @see #getModeR()
	 * @see #setModeP(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode)
	 * @see #setModeA(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu)
	 * @see #setModeR(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif)
	 * @see #setModeC(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul)
	 * @see #setModeD(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference)
	 * @see #setModeE(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne)
	 */
	public void setModeT(ModeSeuil mode_s)
	{
		this.mode_s = mode_s;
	}

	/**
	 * getModeC returns the object which corresponds to the 'on calculation' field.
	 *
	 * @return ModeCalcul
	 * @see #setModeC(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul)
	 * @see #getModeP()
	 * @see #getModeA()
	 * @see #getModeR()
	 * @see #getModeD()
	 * @see #getModeE()
	 */
	public ModeCalcul getModeC()
	{
		return mode_c;
	}

	/**
	 * setModeC sets the object which corresponds to the 'on calculation' field.
	 *
	 * @see #getModeC()
	 * @see #setModeP(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode)
	 * @see #setModeA(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu)
	 * @see #setModeR(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif)
	 * @see #setModeD(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference)
	 * @see #setModeE(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne)
	 */
	public void setModeC(ModeCalcul mc)
	{
		mode_c = mc;
	}

	/**
	 * getModeD returns the object which corresponds to the 'on difference' field.
	 *
	 * @return ModeDifference
	 * @see #setModeD(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference)
	 * @see #getModeP()
	 * @see #getModeA()
	 * @see #getModeR()
	 * @see #getModeC()
	 * @see #getModeE()
	 */
	public ModeDifference getModeD()
	{
		return mode_d;
	}

	/**
	 * setModeD sets the object which corresponds to the 'on difference' field.
	 *
	 * @see #getModeD()
	 * @see #setModeP(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode)
	 * @see #setModeA(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu)
	 * @see #setModeR(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif)
	 * @see #setModeC(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul)
	 * @see #setModeE(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne)
	 */
	public void setModeD(ModeDifference md)
	{
		mode_d = md;
	}

	/**
	 * getModeE returns the object which corresponds to the 'external' field.
	 *
	 * @return ModeExterne
	 * @see #setModeE(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeExterne)
	 * @see #getModeP()
	 * @see #getModeA()
	 * @see #getModeR()
	 * @see #getModeC()
	 * @see #getModeD()
	 */
	public ModeExterne getModeE()
	{
		return mode_e;
	}

	/**
	 * setModeE sets the object which corresponds to the 'external' field.
	 *
	 * @see #getModeE()
	 * @see #setModeP(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModePeriode)
	 * @see #setModeA(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeAbsolu)
	 * @see #setModeR(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeRelatif)
	 * @see #setModeC(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeCalcul)
	 * @see #setModeD(fr.soleil.TangoArchiving.ArchivingTools.Mode.ModeDifference)
	 */
	public void setModeE(ModeExterne me)
	{
		mode_e = me;
	}

	public TdbSpec getTdbSpec()
	{
		return tdbSpec;
	}

	public void setTdbSpec(TdbSpec tdbSpec)
	{
		this.tdbSpec = tdbSpec;
	}

	/**
	 * Returns a string representation of the object <I>Mode</I>.
	 *
	 * @return a string representation of the object <I>Mode</I>.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer("");
		if ( mode_p != null ) buf.append(mode_p.toString() + "\r\n");
		if ( mode_a != null ) buf.append(mode_a.toString() + "\r\n");
		if ( mode_r != null ) buf.append(mode_r.toString() + "\r\n");
		if ( mode_s != null ) buf.append(mode_s.toString() + "\r\n");
		if ( mode_c != null ) buf.append(mode_c.toString() + "\r\n");
		if ( mode_d != null ) buf.append(mode_d.toString() + "\r\n");
		if ( mode_e != null ) buf.append(mode_e.toString() + "\r\n");
		if ( tdbSpec != null ) buf.append(tdbSpec.toString() + "\r\n");
		return buf.toString();
	}

	public String toStringSimple()
	{
		StringBuffer buf = new StringBuffer("[ ");
		if ( mode_p != null ) buf.append(" " + Tag.MODE_P_TAG + " ");
		if ( mode_a != null ) buf.append(" " + Tag.MODE_A_TAG + " ");
		if ( mode_r != null ) buf.append(" " + Tag.MODE_R_TAG + " ");
		if ( mode_s != null ) buf.append(" " + Tag.MODE_T_TAG + " ");
		if ( mode_c != null ) buf.append(" " + Tag.MODE_C_TAG + " ");
		if ( mode_d != null ) buf.append(" " + Tag.MODE_D_TAG + " ");
		if ( mode_e != null ) buf.append(" " + Tag.MODE_E_TAG + " ");
		if ( tdbSpec != null ) buf.append(" " + Tag.MODE_SPEC_TAG + " ");
		buf.append(" " + " ]");
		return buf.toString();
	}

	/**
	 * Returns an array representation of the object <I>Mode</I>.
	 * This array only contains not-null canonicals components of this Object.
	 *
	 * @return an array representation of the object <I>Mode</I>.
	 */
	public String[] toArray()
	{
		String[] mode_array;
		mode_array = new String[ getArraySizeSmall() ];
		int index = 0;
		if ( this.getModeP() != null )
		{
			mode_array[ index ] = Tag.MODE_P;
			index++;
			mode_array[ index ] = Integer.toString(this.getModeP().getPeriod());
			index++;
		}
		if ( this.getModeA() != null )
		{
			mode_array[ index ] = Tag.MODE_A;
			index++;
			mode_array[ index ] = Integer.toString(this.getModeA().getPeriod());
			index++;
			mode_array[ index ] = Double.toString(this.getModeA().getValInf());
			index++;
			mode_array[ index ] = Double.toString(this.getModeA().getValSup());
			index++;
		}
		if ( this.getModeR() != null )
		{
			mode_array[ index ] = Tag.MODE_R;
			index++;
			mode_array[ index ] = Integer.toString(this.getModeR().getPeriod());
			index++;
			mode_array[ index ] = Double.toString(this.getModeR().getPercentInf());
			index++;
			mode_array[ index ] = Double.toString(this.getModeR().getPercentSup());
			index++;
		}
		if ( this.getModeT() != null )
		{
			mode_array[ index ] = Tag.MODE_T;
			index++;
			mode_array[ index ] = Integer.toString(this.getModeT().getPeriod());
			index++;
			mode_array[ index ] = Double.toString(this.getModeT().getThresholdInf());
			index++;
			mode_array[ index ] = Double.toString(this.getModeT().getThresholdSup());
			index++;
		}
		if ( this.getModeC() != null )
		{
			mode_array[ index ] = Tag.MODE_C;
			index++;
			mode_array[ index ] = Integer.toString(this.getModeC().getPeriod());
			index++;
			mode_array[ index ] = Double.toString(this.getModeC().getRange());
			index++;
			mode_array[ index ] = Double.toString(this.getModeC().getTypeCalcul());
			index++;
			mode_array[ index ] = "NULL";
			index++;//Algo
		}
		if ( this.getModeD() != null )
		{
			mode_array[ index ] = Tag.MODE_D;
			index++;
			mode_array[ index ] = Integer.toString(this.getModeD().getPeriod());
			index++;
		}
		if ( this.getModeE() != null )
		{
			mode_array[ index ] = Tag.MODE_E;
			index++;
		}
		if ( this.getTdbSpec() != null )
		{
			mode_array[ index ] = Tag.TDB_SPEC;
			index++;
			mode_array[ index ] = Long.toString(this.getTdbSpec().getExportPeriod());
			index++;
			mode_array[ index ] = Long.toString(this.getTdbSpec().getKeepingPeriod());
			index++;
		}
		return mode_array;
	}

	/**
	 * Returns the smallest size of this object's array representation.
	 *
	 * @return the smallest size of this object's array representation.
	 */
	public int getArraySizeSmall()
	{
		int my_size = 0;
		if ( this.getModeP() != null )
			my_size = my_size + 2;
		if ( this.getModeA() != null )
			my_size = my_size + 4;
		if ( this.getModeR() != null )
			my_size = my_size + 4;
		if ( this.getModeT() != null )
			my_size = my_size + 4;
		if ( this.getModeC() != null )
			my_size = my_size + 5;
		if ( this.getModeD() != null )
			my_size = my_size + 2;
		if ( this.getModeE() != null )
			my_size = my_size + 1;
		if ( this.getTdbSpec() != null )
			my_size = my_size + 3;
		return my_size;
	}

	public boolean equals(Object o)
	{
		if ( this == o ) return true;
		if ( !( o instanceof Mode ) ) return false;

		final Mode mode = ( Mode ) o;

		if ( mode_a != null ? !mode_a.equals(mode.mode_a) : mode.mode_a != null ) return false;
		if ( mode_c != null ? !mode_c.equals(mode.mode_c) : mode.mode_c != null ) return false;
		if ( mode_d != null ? !mode_d.equals(mode.mode_d) : mode.mode_d != null ) return false;
		if ( mode_e != null ? !mode_e.equals(mode.mode_e) : mode.mode_e != null ) return false;
		if ( mode_p != null ? !mode_p.equals(mode.mode_p) : mode.mode_p != null ) return false;
		if ( mode_r != null ? !mode_r.equals(mode.mode_r) : mode.mode_r != null ) return false;
		if ( mode_s != null ? !mode_s.equals(mode.mode_s) : mode.mode_s != null ) return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = ( mode_p != null ? mode_p.hashCode() : 0 );
		result = 29 * result + ( mode_a != null ? mode_a.hashCode() : 0 );
		result = 29 * result + ( mode_r != null ? mode_r.hashCode() : 0 );
		result = 29 * result + ( mode_s != null ? mode_s.hashCode() : 0 );
		result = 29 * result + ( mode_c != null ? mode_c.hashCode() : 0 );
		result = 29 * result + ( mode_d != null ? mode_d.hashCode() : 0 );
		result = 29 * result + ( mode_e != null ? mode_e.hashCode() : 0 );
		return result;
	}

	public void checkMode(boolean historic) throws ArchivingException
	{
		if ( ( mode_p == null ) &&
		     ( ( mode_a != null ) || ( mode_r != null ) || ( mode_s != null ) || ( mode_c != null ) || ( mode_d != null ) || ( mode_e != null ) ) )
			throw generateException("Periodic mode missing !!");

		if ( historic )
		{
			if ( mode_p != null )
				if ( mode_p.getPeriod() < HDB_PERIOD_MIN_VALUE )
					throw generateException("Periodic mode  : archiving period to low !!" +
					                        "(" + (mode_p.getPeriod() / 1000 ) + "< " + (HDB_PERIOD_MIN_VALUE / 1000 )+ " s" + ")");
			if ( mode_a != null )
				if ( mode_a.getPeriod() < HDB_PERIOD_MIN_VALUE )
					throw generateException("Absolute mode  : archiving period to low !!" +
					                        "(" + (mode_a.getPeriod() / 1000 ) + "< " + (HDB_PERIOD_MIN_VALUE / 1000 )+ " s" + ")");
			if ( mode_r != null )
				if ( mode_r.getPeriod() < HDB_PERIOD_MIN_VALUE )
					throw generateException("Relative mode  : archiving period to low !!" +
					                        "(" + (mode_r.getPeriod() / 1000 ) + "< " + (HDB_PERIOD_MIN_VALUE / 1000 )+ " s" + ")");
			if ( mode_s != null )
				if ( mode_s.getPeriod() < HDB_PERIOD_MIN_VALUE )
					throw generateException("Threshold mode  : archiving period to low !!" +
					                        "(" + (mode_s.getPeriod() / 1000 ) + "< " + (HDB_PERIOD_MIN_VALUE / 1000 )+ " s" + ")");
			if ( mode_c != null )
				if ( mode_c.getPeriod() < HDB_PERIOD_MIN_VALUE )
					throw generateException("On Calculation mode  : archiving period to low !!" +
					                        "(" + (mode_c.getPeriod() / 1000 ) + "< " + (HDB_PERIOD_MIN_VALUE / 1000 )+ " s" + ")");
			if ( mode_d != null )
				if ( mode_d.getPeriod() < HDB_PERIOD_MIN_VALUE )
					throw generateException("On Difference mode  : archiving period to low !!" +
					                        "(" + (mode_d.getPeriod() / 1000 ) + "< " + (HDB_PERIOD_MIN_VALUE / 1000 )+ " s" + ")");
		}
		else
		{
			if ( mode_p != null )
				if ( mode_p.getPeriod() < TDB_PERIOD_MIN_VALUE )
					throw generateException("Periodic mode  : archiving period to low !!" +
					                        "(" + mode_p.getPeriod() +  "< " + TDB_PERIOD_MIN_VALUE + " ms" + ")");
			if ( mode_a != null )
				if ( mode_a.getPeriod() < TDB_PERIOD_MIN_VALUE )
					throw generateException("Absolute mode  : archiving period to low !!" +
					                        "(" + mode_a.getPeriod() + "< " + TDB_PERIOD_MIN_VALUE + " ms" + ")");
			if ( mode_r != null )
				if ( mode_r.getPeriod() < TDB_PERIOD_MIN_VALUE )
					throw generateException("Relative mode  : archiving period to low !!" +
					                        "(" + mode_r.getPeriod() + "< " + TDB_PERIOD_MIN_VALUE + " ms" + ")");
			if ( mode_s != null )
				if ( mode_s.getPeriod() < TDB_PERIOD_MIN_VALUE )
					throw generateException("Threshold mode  : archiving period to low !!" +
					                        "(" + mode_s.getPeriod() + "< " + TDB_PERIOD_MIN_VALUE + " ms" + ")");
			if ( mode_c != null )
				if ( mode_c.getPeriod() < TDB_PERIOD_MIN_VALUE )
					throw generateException("On Calculation mode  : archiving period to low !!" +
					                        "(" + mode_c.getPeriod() + "< " + TDB_PERIOD_MIN_VALUE + " ms" + ")");
			if ( mode_d != null )
				if ( mode_d.getPeriod() < TDB_PERIOD_MIN_VALUE )
					throw generateException("On Difference mode  : archiving period to low !!" +
					                        "(" + mode_d.getPeriod() + "< " + TDB_PERIOD_MIN_VALUE + " ms" + ")");
			if ( tdbSpec == null )
				throw generateException("Temporary archiving specific parameters missing !!" + "\r\n" +
				                        "Export period and Keeping period should be set...");
		}
	}

	private static ArchivingException generateException(String cause_msg)
	{
		String cause = "MODE_EXCEPTION";
		String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + cause;
		String reason = "Failed while executing Mode.checkMode()...";
		String desc = cause + " (" + cause_msg + ")";
		return new ArchivingException(message , reason , ErrSeverity.WARN , desc , "");
	}

    public String toStringWatcher() 
    {
        StringBuffer ret = new StringBuffer ();
        
        if ( this.getModeP () != null )
        {
            ret.append ( this.getModeP ().toStringWatcher () );
        }
        if ( this.getModeA () != null )
        {
            ret.append ( " " );
            ret.append ( this.getModeA ().toStringWatcher () );
        }
        if ( this.getModeR () != null )
        {
            ret.append ( " " );
            ret.append ( this.getModeR ().toStringWatcher () );
        }
        if ( this.getModeT () != null )
        {
            ret.append ( " " );
            ret.append ( this.getModeT ().toStringWatcher () );
        }
        if ( this.getModeD () != null )
        {
            ret.append ( " " );
            ret.append ( this.getModeD ().toStringWatcher () );
        }
        
        return ret.toString ();
    }
}