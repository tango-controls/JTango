//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ILogger.
//						(Claisse Laurent) - 5 juil. 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.2  2006/07/18 15:16:49  ounsy
// added a new setTraceLevel(String) method
//
// Revision 1.1  2006/06/16 09:24:03  ounsy
// moved from the TdbArchiving project
//
// Revision 1.1  2006/06/08 08:34:49  ounsy
// creation
//
// Revision 1.2  2005/11/29 18:28:12  chinkumo
// no message
//
// Revision 1.1.2.2  2005/09/14 15:41:32  chinkumo
// Second commit !
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================
package fr.soleil.TangoArchiving.ArchivingTools.Diary;

import java.io.IOException;

public interface ILogger
{
    public static final int LEVEL_CRITIC = 1;
    public static final int LEVEL_ERROR = 3;
    public static final int LEVEL_WARNING = 5;
    public static final int LEVEL_INFO = 7;
    public static final int LEVEL_DEBUG = 9;

    public static final String CRITIC = "CRITIC";
    public static final String ERROR = "ERROR";
    public static final String WARNING = "WARNING";
    public static final String INFO = "INFO";
    public static final String DEBUG = "DEBUG";

    /**
     * @return 8 juil. 2005
     */
    public int getTraceLevel ();

    /**
     * @param level 8 juil. 2005
     */
    public void setTraceLevel ( int level );
    public void setTraceLevel(String string);

    /**
     * @param level
     * @param o     8 juil. 2005
     */
    public void trace ( int level , Object o );

    /**
     * @param path
     * @throws IOException 8 juil. 2005
     */
    public void initDiaryWriter ( String path , String archiver ) throws IOException;

    /**
     * 8 juil. 2005
     */
    public void close ();

    /**
     * @param level_s
     * @return 8 juil. 2005
     */
    public int getTraceLevel ( String level_s );
}
