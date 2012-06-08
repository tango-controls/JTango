//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbImpl\\db\\oracle\\OracleRecorderDataBaseApiImpl.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.oracle;

import java.sql.Timestamp;

import fr.esrf.Tango.AttrWriteType;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.BasicRecorderDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IDataBaseApiTools;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent;

public class OracleRecorderDataBaseApiImpl extends BasicRecorderDataBaseApiImpl 
{

	/**
	 * @roseuid 45EC2126037A
	 */
	public OracleRecorderDataBaseApiImpl(IDataBaseApiTools dataBaseApiTools) 
	{
		super(dataBaseApiTools);
	}


	public void insert_ScalarData(ScalarEvent scalarEvent) throws ArchivingException
	{
        //if (canceled) return;
        String attName = scalarEvent.getAttribute_complete_name().trim();
        long time = scalarEvent.getTimeStamp();
        Timestamp timeSt = new Timestamp(time);
        int writable = scalarEvent.getWritable();
        //int idPosition;

        Object value = scalarEvent.getValue();
        StringBuffer query = new StringBuffer();
        
        String selectFields = "";
        // Create and execute the SQL query string
        // Build the query string
        if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
        {
            selectFields = "?, ?";
        }
        else
        {
            selectFields = "?, ?, ?";
        }
	}
	/**
	 * @roseuid 45C9969C0027
	 */
	protected void prepareQueryInsertModeRecord() 
	{

	}

	/**
	 * @param attribute_name
	 * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
	 * @roseuid 45EC21CB0391
	 */
	public void updateModeRecord(String attribute_name) throws ArchivingException 
	{
		System.out.println("SPJZ ==> OracleRecorderDataBaseApiImpl.updateModeRecord");
	}
}
