package fr.soleil.TangoArchiving.ArchivingWatchApi.datasources.db;

/**
 * An implementation that really loads data from the HDB physical database
 * @author CLAISSE 
 */
public class TDBReader extends BufferedDBReader 
{
    TDBReader() 
    {
        super();
    }

    protected boolean isHistoric() 
    {
        return false;
    }
}
