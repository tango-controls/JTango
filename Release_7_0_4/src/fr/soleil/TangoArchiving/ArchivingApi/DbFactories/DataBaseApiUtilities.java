//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbFactories\\DataBaseApiUtilities.java

package fr.soleil.TangoArchiving.ArchivingApi.DbFactories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import fr.esrf.Tango.ErrSeverity;
import fr.soleil.TangoArchiving.ArchivingApi.DbConnection.IDataBaseConnection;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;

public abstract class DataBaseApiUtilities implements IDataBaseApiUtilities 
{
	protected IDataBaseConnection m_dbConnection;
	
	protected DataBaseApiUtilities(IDataBaseConnection dbConnection)
	{
		m_dbConnection = dbConnection;
	}
	
	protected abstract void sqlExceptionTreatment(SQLException e, String methodeName) throws ArchivingException;
   
   /**
    * @param stmt
    * @throws java.sql.SQLException
    * @roseuid 45ED5DCC01EF
    */
   public void close(Statement stmt) throws SQLException 
   {
		if ( stmt != null )
		{
			stmt.close ();
			stmt = null;
		}    
   }
   
   /**
    * @param rset
    * @throws java.sql.SQLException
    * @roseuid 45ED5DCC01FE
    */
   public void close(ResultSet rset) throws SQLException 
   {
		if ( rset != null )
		{
			rset.close ();
			rset = null;
		}    
   }
   
   /**
    * @roseuid 460294340006
    */
   public void commit() throws ArchivingException 
   {
       try
		{
    	   if(m_dbConnection.getDbConnection() != null)
    		   m_dbConnection.getDbConnection().commit();
		}
		catch ( SQLException e )
		{
			sqlExceptionTreatment(e, "commit");
		}
   }
   
   /**
    * @roseuid 460294340016
    */
   public void rollback() throws ArchivingException 
   {
	    try
		{
	    	if(m_dbConnection.getDbConnection() != null)
	    		   m_dbConnection.getDbConnection().rollback();
		}
		catch ( SQLException e )
		{
			sqlExceptionTreatment(e, "rollback");
		}
   }
}
