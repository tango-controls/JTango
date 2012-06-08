//+============================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description: This public class factorise certain number of redundant names.
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
 * This public class factorise certain number of redundant names.
 */
public class Tag
{
	/**
	 * The constructor is here private to forbid any attempt of instanciation.
	 */
	private Tag()
	{
	}

	protected final static String MODE_P = "MODE_P";	// Periodic
	protected final static String MODE_A = "MODE_A";// Absolute
	protected final static String MODE_R = "MODE_R";	// Relative
	protected final static String MODE_T = "MODE_T";  // Threshold
	protected final static String MODE_C = "MODE_C";	// Calcul
	protected final static String MODE_D = "MODE_D";	// Difference
	protected final static String MODE_E = "MODE_E";	//	Extern
	protected final static String TDB_SPEC = "TDB_SPEC";

	/** Tag introduisant les modes imputés à  l'attribut. */
	//public static final String MODES_TAG = "modes";

	/**
	 * String which describes the <I>periodic mode.</I>
	 */
	public static final String MODE_P_TAG = "Periodic Mode";
	/**
	 * String which describes the <I>period</I> parameter of the periodic mode.
	 */
	public static final String MODE_P0_TAG = "period";

	/**
	 * String which describes the <I>absolute mode.</I>
	 */
	public static final String MODE_A_TAG = "Absolute Mode";
	/**
	 * String which describes the <I>lower limit</I> parameter of the absolute mode.
	 */
	public static final String MODE_A1_TAG = "val_inf";
	/**
	 * String which describes the <I>upper limit</I> parameter of the absolute mode.
	 */
	public static final String MODE_A2_TAG = "val_sup";

	/**
	 * String which describes the <I>relative mode.</I>
	 */
	public static final String MODE_R_TAG = "Relative Mode";
	/**
	 * String which describes the <I>lower proportion limit</I> parameter of the relative mode.
	 */
	public static final String MODE_R1_TAG = "percent_inf";
	/**
	 * String which describes the <I>upper proportion limit</I> parameter of the relative mode.
	 */
	public static final String MODE_R2_TAG = "percent_sup";

	/**
	 * String which describes the <I>absolute mode.</I>
	 */
	public static final String MODE_T_TAG = "Threshold Mode";
	/**
	 * String which describes the <I>lower limit</I> parameter of the absolute mode.
	 */
	public static final String MODE_T1_TAG = "low_lim";
	/**
	 * String which describes the <I>upper limit</I> parameter of the absolute mode.
	 */
	public static final String MODE_T2_TAG = "high_lim";

	/**
	 * String which describes the <I>'on calculation mode'</I>.
	 */
	public static final String MODE_C_TAG = "On Calculation Mode";
	/**
	 * String which describes the <I>range</I> parameter of the 'on calculation mode'.
	 */
	public static final String MODE_C1_TAG = "range";
	/**
	 * String which describes the <I>calculation type</I> parameter of the 'on calculation mode'.
	 */
	public static final String MODE_C2_TAG = "calcul_type";

	/**
	 * String which describes the <I>'on difference mode'</I>.
	 */
	public static final String MODE_D_TAG = "On Difference Mode";
	/**
	 * String which describes the <I>'external mode'</I>.
	 */
	public static final String MODE_E_TAG = "External Mode";
	/**
	 * String which describes the <I>description</I> parameter of the 'external mode'.
	 */
	public static final String MODE_E1_TAG = "description";

	public static final String MODE_SPEC_TAG = "Temporary DB";
	public static final String MODE_SPEC1_TAG = "Export Period";
	public static final String MODE_SPEC2_TAG = "Keeping Period";
	/**
	 * String which describes the <I>time unit</I> used.
	 */
	public static final String TIME_UNIT = "milliseconds";
}
