package fr.soleil.TangoArchiving.ArchivingTools.Tools;


/**
 * 
 * @author CLAISSE 
 */
public interface Warnable 
{
    public static final int LOG_LEVEL_DEBUG = 9;
    public static final int LOG_LEVEL_INFO = 7;
    public static final int LOG_LEVEL_WARN = 5;
    public static final int LOG_LEVEL_ERROR = 3;
    public static final int LOG_LEVEL_FATAL = 1;
    
    public void trace ( String msg , int level ) /*throws DevFailed*/;
    public void trace ( String msg , Throwable t , int level ) /*throws DevFailed*/;
}
