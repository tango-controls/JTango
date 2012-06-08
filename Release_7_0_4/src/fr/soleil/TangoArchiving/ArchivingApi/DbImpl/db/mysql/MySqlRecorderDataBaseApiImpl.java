//Source file: D:\\eclipse\\eclipse\\workspace\\DevArchivage\\javaapi\\fr\\soleil\\TangoArchiving\\ArchivingApi\\DbImpl\\db\\mysql\\MySqlRecorderDataBaseApiImpl.java

package fr.soleil.TangoArchiving.ArchivingApi.DbImpl.db.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.TangoArchiving.ArchivingApi.ConfigConst;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.BasicRecorderDataBaseApiImpl;
import fr.soleil.TangoArchiving.ArchivingApi.DbImpl.IDataBaseApiTools;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.GlobalConst;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.ScalarEvent;
import fr.soleil.TangoArchiving.ArchivingTools.Tools.StringFormater;

public class MySqlRecorderDataBaseApiImpl extends BasicRecorderDataBaseApiImpl 
{

	public MySqlRecorderDataBaseApiImpl(IDataBaseApiTools dataBaseApiTools) 
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
        
/*        StringBuffer tableName = new StringBuffer().append(getDbSchema()).append(".").append(getTableName(attName));
        StringBuffer tableFields = new StringBuffer();
        if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
        {
            tableFields = new StringBuffer().append(ConfigConst.TAB_SCALAR_RO[ 0 ]).append(", ").append(ConfigConst.TAB_SCALAR_RO[ 1 ]);
        }
        else
        { 
            tableFields = new StringBuffer().append(ConfigConst.TAB_SCALAR_RW[ 0 ]).append(", ").append(ConfigConst.TAB_SCALAR_RW[ 1 ]).append(", ").append(ConfigConst.TAB_SCALAR_RW[ 2 ]);
        }
        PreparedStatement preparedStatement;
        query.append("INSERT INTO ").append(tableName).append(" (").append(tableFields).append(")").append(" VALUES").append(" (").append(selectFields).append(")");

        try
        {
            preparedStatement = dbconn.prepareStatement(query.toString());
            lastStatement = preparedStatement;
            if (canceled) return;
            preparedStatement.setTimestamp(1 , timeSt);
            if ( value != null )
            {
                if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
                {
                    switch ( scalarEvent.getData_type() )
                    {
                        case TangoConst.Tango_DEV_STRING:
                            preparedStatement.setString( 2 , StringFormater.formatStringToWrite( ( String ) value ) );
                            break;
                        case TangoConst.Tango_DEV_UCHAR:
                            preparedStatement.setByte(2 , ( ( Byte ) value ).byteValue());
                            break;
                        case TangoConst.Tango_DEV_STATE:
                            preparedStatement.setInt(2 , ( ( DevState ) value ).value());
                            break;
                        case TangoConst.Tango_DEV_ULONG:
                        case TangoConst.Tango_DEV_LONG:
                            preparedStatement.setInt(2 , ( ( Integer ) value ).intValue());
                            break;
                        case TangoConst.Tango_DEV_BOOLEAN:
                            preparedStatement.setBoolean(2 , ( ( Boolean ) value ).booleanValue());
                            break;
                        case TangoConst.Tango_DEV_SHORT:
                        case TangoConst.Tango_DEV_USHORT:
                            preparedStatement.setShort(2 , ( ( Short ) value ).shortValue());
                            break;
                        case TangoConst.Tango_DEV_FLOAT:
                            preparedStatement.setFloat(2 , ( ( Float ) value ).floatValue());
                            break;
                        case TangoConst.Tango_DEV_DOUBLE:
                            preparedStatement.setDouble(2 , ( ( Double ) value ).doubleValue());
                            break;
                        default:
                            preparedStatement.setDouble(2 , ( ( Double ) value ).doubleValue());
                    }
                }
                else
                {
                    switch ( scalarEvent.getData_type() )
                    {
                        case TangoConst.Tango_DEV_ULONG:
                        case TangoConst.Tango_DEV_LONG:
                        case TangoConst.Tango_DEV_STATE:
                            Integer[] intvalue = (Integer[]) value;
                            if (intvalue[0] == null)
                            {
                                preparedStatement.setNull(2, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setInt(2 , intvalue[ 0 ].intValue());
                            }
                            if (intvalue[1] == null)
                            {
                                preparedStatement.setNull(3, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setInt(3 , intvalue[ 1 ].intValue());
                            }
                            intvalue = null;
                            break;
                        case TangoConst.Tango_DEV_STRING:
                            String[] strvalue = (String[]) value;
                            if (strvalue[0] == null)
                            {
                                preparedStatement.setNull(2, Types.VARCHAR);
                            }
                            else
                            {
                                preparedStatement.setString(2 , StringFormater.formatStringToWrite( ( ( String[] ) value )[ 0 ] ));
                            }
                            if (strvalue[1] == null)
                            {
                                preparedStatement.setNull(3, Types.VARCHAR);
                            }
                            else
                            {
                                preparedStatement.setString(3 , StringFormater.formatStringToWrite( ( ( String[] ) value )[ 1 ] ));
                            }
                            strvalue = null;
                            break;
                        case TangoConst.Tango_DEV_UCHAR:
                            Byte[] bytevalue = (Byte[]) value;
                            if (bytevalue[0] == null)
                            {
                                preparedStatement.setNull(2, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setByte(2 , bytevalue[ 0 ].byteValue());
                            }
                            if (bytevalue[1] == null)
                            {
                                preparedStatement.setNull(3, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setByte(3 , bytevalue[ 1 ].byteValue());
                            }
                            bytevalue = null;
                            break;
                        case TangoConst.Tango_DEV_BOOLEAN:
                            Boolean[] boolvalue = (Boolean[]) value;
                            if (boolvalue[0] == null)
                            {
                                preparedStatement.setNull(2, Types.BOOLEAN);
                            }
                            else
                            {
                                preparedStatement.setBoolean(2 , boolvalue[ 0 ].booleanValue());
                            }
                            if (boolvalue[1] == null)
                            {
                                preparedStatement.setNull(3, Types.BOOLEAN);
                            }
                            else
                            {
                                preparedStatement.setBoolean(3 , boolvalue[ 1 ].booleanValue());
                            }
                            boolvalue = null;
                            break;
                        case TangoConst.Tango_DEV_SHORT:
                        case TangoConst.Tango_DEV_USHORT:
                            Short[] shortvalue = (Short[]) value;
                            if (shortvalue[0] == null)
                            {
                                preparedStatement.setNull(2, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setShort(2 , shortvalue[ 0 ].shortValue());
                            }
                            if (shortvalue[1] == null)
                            {
                                preparedStatement.setNull(3, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setShort(3 , shortvalue[ 1 ].shortValue());
                            }
                            shortvalue = null;
                            break;
                        case TangoConst.Tango_DEV_FLOAT:
                            Float[] floatvalue = (Float[]) value;
                            if (floatvalue[0] == null)
                            {
                                preparedStatement.setNull(2, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setFloat(2 , floatvalue[ 0 ].floatValue());
                            }
                            if (floatvalue[1] == null)
                            {
                                preparedStatement.setNull(3, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setFloat(3 , floatvalue[ 1 ].floatValue());
                            }
                            floatvalue = null;
                            break;
                        case TangoConst.Tango_DEV_DOUBLE:
                        default:
                            Double[] doublevalue = (Double[]) value;
                            if (doublevalue[0] == null)
                            {
                                preparedStatement.setNull(2, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setDouble(2 , doublevalue[ 0 ].doubleValue());
                            }
                            if (doublevalue[1] == null)
                            {
                                preparedStatement.setNull(3, Types.NUMERIC);
                            }
                            else
                            {
                                preparedStatement.setDouble(3 , doublevalue[ 1 ].doubleValue());
                            }
                            doublevalue = null;
                    }
                }

            }
            else
            {
                if ( writable == AttrWriteType._READ || writable == AttrWriteType._WRITE )
                {
                    switch ( scalarEvent.getData_type() )
                    {
                        case TangoConst.Tango_DEV_STRING:
                            preparedStatement.setNull(2 , java.sql.Types.VARCHAR);
                            break;
                        case TangoConst.Tango_DEV_STATE:
                        case TangoConst.Tango_DEV_UCHAR:
                        case TangoConst.Tango_DEV_LONG:
                        case TangoConst.Tango_DEV_ULONG:
                        case TangoConst.Tango_DEV_BOOLEAN:
                        case TangoConst.Tango_DEV_SHORT:
                        case TangoConst.Tango_DEV_USHORT:
                        case TangoConst.Tango_DEV_FLOAT:
                        case TangoConst.Tango_DEV_DOUBLE:
                        default:
                            preparedStatement.setNull(2 , java.sql.Types.NUMERIC);
                    }
                }
                else
                {
                    switch ( scalarEvent.getData_type() )
                    {
                        case TangoConst.Tango_DEV_STRING:
                            preparedStatement.setNull(2 , java.sql.Types.VARCHAR);
                            preparedStatement.setNull(3 , java.sql.Types.VARCHAR);
                            break;
                        case TangoConst.Tango_DEV_STATE:
                        case TangoConst.Tango_DEV_UCHAR:
                        case TangoConst.Tango_DEV_LONG:
                        case TangoConst.Tango_DEV_ULONG:
                        case TangoConst.Tango_DEV_BOOLEAN:
                        case TangoConst.Tango_DEV_SHORT:
                        case TangoConst.Tango_DEV_USHORT:
                        case TangoConst.Tango_DEV_FLOAT:
                        case TangoConst.Tango_DEV_DOUBLE:
                        default:
                            preparedStatement.setNull(2 , java.sql.Types.NUMERIC);
                            preparedStatement.setNull(3 , java.sql.Types.NUMERIC);
                    }
                }

            }
            try
            {
                preparedStatement.executeUpdate();
                close(preparedStatement);
            }
            catch ( SQLException e )
            {
                close(preparedStatement);
                throw e;
            }
        }
        catch ( SQLException e )
        {
            String message = "";
            if ( e.getMessage().equalsIgnoreCase(GlobalConst.COMM_FAILURE_ORACLE) || e.getMessage().indexOf(GlobalConst.COMM_FAILURE_MYSQL) != -1 )
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.ADB_CONNECTION_FAILURE;
            else
                message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + GlobalConst.STATEMENT_FAILURE;

            String reason = GlobalConst.INSERT_FAILURE;
            String desc = "Failed while executing DataBaseApi.insert_ScalarData_RO() method...\r\n" + query;
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , this.getClass().getName() , e);
        }
       */
	}
	
	/**
	 * @roseuid 45C9969F00C4
	 */
	protected void prepareQueryInsertModeRecord() 
	{

	}

	/**
	 * @param attribute_name
	 * @throws fr.soleil.TangoArchiving.ArchivingTools.Tools.ArchivingException
	 * @roseuid 45EC21D600A3
	 */
	public void updateModeRecord(String attribute_name) throws ArchivingException 
	{
		System.out.println("SPJZ ==> MySqlRecorderDataBaseApiImpl.updateModeRecord");
	}
}
//void MySqlRecorderDataBaseApiImpl.updateModeRecord(){

//}
/**
 * void MySqlRecorderDataBaseApiImpl.updateModeRecord(java.lang.String){
 *     
 *    }
 */
