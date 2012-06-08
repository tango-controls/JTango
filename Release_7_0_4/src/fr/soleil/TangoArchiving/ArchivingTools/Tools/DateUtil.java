package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import fr.esrf.Tango.ErrSeverity;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;

//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  DateUtil.
//						(chinkumo) - 3 juin 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.9  2007/02/16 09:50:50  ounsy
// added Oracle-->MySQL date format conversion
//
// Revision 1.8  2006/10/31 16:49:35  ounsy
// minor changes
//
// Revision 1.7  2006/09/05 12:27:59  ounsy
// updated for sampling compatibility
//
// Revision 1.6  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.5.2.2  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.5.2.1  2005/09/09 08:37:44  chinkumo
// Management of the exception that occurs when a string date is misformatted.
//
// Revision 1.5  2005/08/19 14:04:01  chinkumo
// no message
//
// Revision 1.4.6.1  2005/08/01 13:43:16  chinkumo
// The date mask was changed to allow the support of the milliseconds.
//
// Revision 1.4  2005/06/24 12:06:11  chinkumo
// Some constants were moved from fr.soleil.TangoArchiving.ArchivingApi.ConfigConst to fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst.
// This change was reported here.
//
// Revision 1.3  2005/06/14 14:05:53  chinkumo
// no message
//
// Revision 1.1  2005/06/13 09:39:39  chinkumo
// This class manages all the date and time stuff (formats, format transformations, etc...)
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

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
	 * @throws ArchivingException
	 */
	static public long stringToMilli(String date) throws ArchivingException
	{
		ArchivingException archivingException;
		
        boolean isFr = ( date.indexOf("-") != 4 );
        int currentLength = date.length ();
        //String toTheSecond = "yyyy-MM-dd HH:mm:ss";
        String toTheDay = "yyyy-MM-dd";
        
        //System.out.println ( "CLA/stringToMilli/BEFORE/date|"+date+"|" );
        
        if ( isFr )
        {
            
        }
        else
        {
            if ( currentLength == toTheDay.length () )
            {
                date += " 00:00:00";
            }
            if ( currentLength == ( toTheDay.length () + 1 ) )
            {
                date += "00:00:00";
            }
            if ( currentLength == ( toTheDay.length () + 2 ) )
            {
                date += "0:00:00";
            }
            if ( currentLength == ( toTheDay.length () + 3 ) )
            {
                date += ":00:00";
            }
            if ( currentLength == ( toTheDay.length () + 4 ) )
            {
                date += "00:00";
            }
            if ( currentLength == ( toTheDay.length () + 5 ) )
            {
                date += "0:00";
            }
            if ( currentLength == ( toTheDay.length () + 6 ) )
            {
                date += ":00";
            }
            if ( currentLength == ( toTheDay.length () + 7 ) )
            {
                date += "00";
            }
            if ( currentLength == ( toTheDay.length () + 8 ) )
            {
                date += "0";
            }
        }
        
        //System.out.println ( "CLA/stringToMilli/AFTER 1/date|"+date+"|" );
        
        if ( date.indexOf(".") == -1 )
        {
			date = date + ( ".000" );
        }
        //System.out.println ( "CLA/stringToMilli/AFTER 2/date|"+date+"|" );
        
        try
		{
			if ( isFr )
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
			try
            {
                //System.out.println ( "CLA/!!!!!!!!!!!!!!!!!!!!!!/date|"+date+"|" );
                e1.printStackTrace ();
                
                Timestamp ts = Timestamp.valueOf ( date );
                return ts.getTime ();
            }
            catch ( Exception e )
            {
                System.out.println ( "----------------------" );
                e.printStackTrace ();
                System.out.println ( "----------------------" );
                
                String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.DATE_PARSING_EXCEPTION;
                String reason = GlobalConst.DATE_PARSING_EXCEPTION;
                String desc = "Failed while executing DateUtil.stringToMilli() method...";
                archivingException = new ArchivingException(message , reason , ErrSeverity.WARN , desc , "fr.soleil.TangoArchiving.ArchivingTools.Tools.DateUtil" , e1);
                throw archivingException;    
            }
		}
	}

	/**
	 * Return a date string in the french date format (dd-MM-yyyy HH:mm:ss)
	 *
	 * @param date the date string
	 * @return the date string in the french date format (dd-MM-yyyy HH:mm:ss)
	 * @throws ArchivingException
	 */
	static public String stringToDisplayString(String date) throws ArchivingException
	{
        //date = completeDate ( date );
        long _date = stringToMilli(date);
		return milliToString(_date , FR_DATE_PATTERN);
	}

    public static String oracleToMySQL(String string) 
    {   
        try 
        {
            DateHeure dh = new DateHeure ( string , DateHeure.FORMAT_DATE_ORACLE );
            return dh.toString ( DateHeure.FORMAT_DATE_MYSQL );
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}
