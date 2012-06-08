//+============================================================================
//
// file :               AttrManip.java
//
// description :        java source code for the AttrManip class. 
//			This class is used to format attribute value according
//			to their format property.
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.6  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:57  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:22  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:35  taurel
// Many changes due to new release
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import java.text.DecimalFormat;
import java.util.StringTokenizer;


/**
 * This class is a TANGO core class used to print attribute value follwing
 * the format defined as one of the attribute property
 *
 * @author	$Author$
 * @version	$Revision$
 */
 
public class AttrManip implements java.io.Serializable
{
	static DecimalFormat df = null;
	static boolean pos = false;
	static int prec = 0;
	static boolean point = false;

/**
 * Format the attribute value (value is an int).
 *
 * This method returns the input value as a string formatted according to the
 * format parameter. The syntax of this format string is the syntax defined
 * for the attribute format property.
 *
 * @param format The format string (as defined in the attribute property format)
 * @param value The attribute value
 * @return The input value as a string
 */
  	
	public static String format(String format,int value)
	{
		prec = 0;
		point = false;
		df = null;

		analyse_format(format);
					
		StringBuffer out = new StringBuffer();		

		if (df != null)						
			out.append(df.format(value));
		else
			out.append(value);
		if (pos == true)
		{
			if (value > 0)
				out.insert(0,"+");
			pos = false;
		}
		return out.toString();			
	}

	
/**
 * Format the attribute value (value is a double).
 *
 * This method returns the input value as a string formatted according to the
 * format parameter. The syntax of this format string is the syntax defined
 * for the attribute format property.
 *
 * @param format The format string (as defined in the attribute property format)
 * @param value The attribute value
 * @return The input value as a string
 */
 			
	public static String format(String format,double value)
	{
		prec = 0;
		point = false;
		df = null;
		
		analyse_format(format);
		
		StringBuffer out = new StringBuffer();		

		if (df != null)						
			out.append(df.format(value));
		else
			out.append(value);
			
		if (pos == true)
		{
			if (value > 0)
				out.insert(0,"+");
			pos = false;
		}
		return out.toString();
	}

/**
 * Format the attribute value (value is a string).
 *
 * This method does nothing and only returns the input string. It has been
 * written for compatibility with the other attribuute data type.
 *
 * @param format The format string (not used in this case)
 * @param value The attribute value
 * @return The input value
 */
 	
	public static String format(String format,String value)
	{
		return value;
	}

//+-------------------------------------------------------------------------
//
// method : 		analyse_format
// 
// description : 	This method parse the inout string and for each
//			token inside the input string calls the execute_manip
//			method
//
// argument : in : 	- format : The format property string
//
//--------------------------------------------------------------------------

	private static void analyse_format(String format)
	{
		StringTokenizer st = new StringTokenizer(format.toLowerCase(),";\n");

		int token_nb = st.countTokens();
		String[] m_array = new String[token_nb];
		int i = 0;
                while (st.hasMoreTokens())
                {
                        m_array[i] = st.nextToken();
			i++;
                }

		for (i = 0;i < token_nb;i++)
			execute_manip(m_array[i]);
	}

//+-------------------------------------------------------------------------
//
// method : 		execute_manip
// 
// description : 	This method creates and/or set the DecimalFormat
//			class parameters according to the C++ like manipulator
//			defined for the Tango attribute format property. The
//			supported manipulator are :
//				- showpos
//				- fixed
//				- scientific
//				- setprecision(xx)
//				- showpoint
//
// argument : in : 	- s : The C++ like manipulator
//
//--------------------------------------------------------------------------
		
	private static void execute_manip(String s)
	{
		if (s.equals("showpos") == true)
			pos = true;
		else if (s.equals("fixed") == true)
		{
			if (df == null)
			{
				df = new DecimalFormat("0.######");
				if (prec != 0)
					df.setMaximumFractionDigits(prec);
				if (point != false)
				{
					if (prec != 0)
						df.setMinimumFractionDigits(prec);
					else
						df.setMinimumFractionDigits(6);
				}
			}
		}
		else if (s.equals("scientific") == true)
		{
			if (df == null)
			{
				df = new DecimalFormat("0.######E00");
				if (prec != 0)
					df.setMaximumFractionDigits(prec);
				if (point != false)
				{
					if (prec != 0)
						df.setMinimumFractionDigits(prec);
					else
						df.setMinimumFractionDigits(6);
				}
			}
		}
		else if (s.length() >= 15)
		{
			if (s.substring(0,13).equals("setprecision(") == true)
			{
				String prec_str = s.substring(13,s.length() - 1);
				int tmp_prec = Integer.parseInt(prec_str);
				if (df != null)
					df.setMaximumFractionDigits(tmp_prec);
				prec = tmp_prec;
			}
		}
		else if (s.equals("showpoint") == true)
		{
			if (df != null)
			{
				if (prec != 0)
					df.setMinimumFractionDigits(prec);
				else
					df.setMinimumFractionDigits(6);
			}
			else
				point = true;
		}
	}
}
