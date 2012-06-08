//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbFactories\\IDataBaseApiUtilities.java

package fr.soleil.TangoArchiving.ArchivingApi.DbFactories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;

public interface IDataBaseApiUtilities 
{
   
   /**
    * @param stmt
    * @throws java.sql.SQLException
    * @roseuid 45ED6C35008A
    */
   public void close(Statement stmt) throws SQLException;
   
   /**
    * @param rset
    * @throws java.sql.SQLException
    * @roseuid 45ED6C3500C9
    */
   public void close(ResultSet rset) throws SQLException;
   
   /**
    * @roseuid 46029413000B
    */
   public void commit() throws ArchivingException;
   
   /**
    * @roseuid 4602942102B2
    */
   public void rollback() throws ArchivingException;
}
