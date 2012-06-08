//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  DateUtil.
//						(Chinkumo Jean) - 9 janv. 2006
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2006/02/17 09:25:39  chinkumo
// Minor change : code reformated.
//
// Revision 1.2  2006/01/09 10:52:08  chinkumo
// Exceptions enhancement.
//
// Revision 1.1  2006/01/09 10:45:59  chinkumo
// This class was added to solve the Oracle/MySQL date management difference.
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

import fr.esrf.Tango.ErrSeverity;

import java.util.Date;
import java.text.ParseException;

public class DateUtil
{
	// Globals
	public static final String FR_DATE_PATTERN = "dd-MM-yyyy HH:mm:ss.SSS";
	public static final String US_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	static final java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
	static final java.text.SimpleDateFormat genFormatFR = new java.text.SimpleDateFormat(FR_DATE_PATTERN);
	static final java.text.SimpleDateFormat genFormatUS = new java.text.SimpleDateFormat(US_DATE_PATTERN);

	/**
	 * Cast the given long (number of milliseconds since  January 1, 1970) into a string that follows the given pattern
	 * (either the french one (dd-MM-yyyy HH:mm:ss) or the US one (yyyy-MM-dd HH:mm:ss))
	 *
	 * @param milli   the date in millisecond to cast
	 * @param pattern the target pattern
	 * @return a string representing the date into the given pattern format
	 */
	static public String milliToString(long milli , String pattern)
	{
		Date date;
		calendar.setTimeInMillis(milli);
		date = calendar.getTime();
		if ( pattern.equals(FR_DATE_PATTERN) )
			return genFormatFR.format(date);
		else
			return genFormatUS.format(date);
	}

	/**
	 * Cast a string format date (dd-MM-yyyy HH:mm:ss or yyyy-MM-dd HH:mm:ss) into long (number of milliseconds since  January 1, 1970)
	 *
	 * @param date
	 * @return
	 * @throws fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapshotingException
	 *
	 */
	static public long stringToMilli(String date) throws SnapshotingException
	{
		SnapshotingException archivingException;
		if ( date.indexOf(".") == -1 )
			date = date + ( ".000" );
		try
		{
			if ( date.indexOf("-") != 4 )
			{
				genFormatFR.parse(date);
				return genFormatFR.getCalendar().getTimeInMillis();
			}
			else
			{
				genFormatUS.parse(date);
				return genFormatUS.getCalendar().getTimeInMillis();
			}
		}
		catch ( ParseException e1 )
		{
			String message = fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.GlobalConst.SNAPSHOTING_ERROR_PREFIX + " : " + GlobalConst.DATE_PARSING_EXCEPTION;
			String reason = GlobalConst.DATE_PARSING_EXCEPTION;
			String desc = "Failed while executing DateUtil.stringToMilli() method...";
			archivingException = new SnapshotingException(message , reason , ErrSeverity.WARN , desc , "fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.DateUtil" , e1);
			throw archivingException;
		}
	}

	/**
	 * Return a date string in the french date format (dd-MM-yyyy HH:mm:ss)
	 *
	 * @param date the date string
	 * @return the date string in the french date format (dd-MM-yyyy HH:mm:ss)
	 * @throws fr.soleil.TangoSnapshoting.SnapshotingTools.Tools.SnapshotingException
	 *
	 */
	static public String stringToDisplayString(String date) throws SnapshotingException
	{
		long _date = stringToMilli(date);
		return milliToString(_date , FR_DATE_PATTERN);
	}
}
