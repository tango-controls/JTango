package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import java.util.Vector;
import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevVarDoubleStringArray;
import fr.esrf.Tango.ErrSeverity;
import fr.esrf.Tango.TimeVal;
import fr.esrf.TangoDs.TangoConst;
import fr.esrf.TangoDs.TimedAttrData;

//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  DbData.
//						(chinkumo) - 31 août 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.22  2007/05/10 14:59:21  ounsy
// NaN and null distinction
//
// Revision 1.21  2007/02/28 09:46:37  ounsy
// better null values management
//
// Revision 1.20  2006/11/20 09:31:28  ounsy
// minor changes
//
// Revision 1.19  2006/11/09 14:19:57  ounsy
// addded a missing break in setData
//
// Revision 1.18  2006/10/31 16:54:23  ounsy
// milliseconds and null values management
//
// Revision 1.17  2006/08/29 13:56:36  ounsy
// setValue(XXX) : taking care of case where parameter is null
//
// Revision 1.16  2006/07/18 10:42:27  ounsy
// image support
//
// Revision 1.15  2006/05/05 14:07:50  ounsy
// corrected the "No Data" bug on write-only attributes
//
// Revision 1.14  2006/04/05 13:50:50  ounsy
// new types full support
//
// Revision 1.13  2006/03/27 15:20:01  ounsy
// new spectrum types support + better spectrum management + new scalar types full support
//
// Revision 1.12  2006/03/15 16:07:56  ounsy
// states converted to their string value only in GUI
//
// Revision 1.11  2006/03/15 15:11:13  ounsy
// minor changes
//
// Revision 1.10  2006/03/14 12:52:11  ounsy
// corrected the SNAP/spectrums/RW problem
// about the read and write values having the same length
//
// Revision 1.9  2006/03/10 11:31:00  ounsy
// state and string support
//
// Revision 1.8  2006/02/16 10:37:01  ounsy
// code cleaning
//
// Revision 1.7  2006/02/16 09:57:27  ounsy
// No more confusion between table length and dim x
// splitDbData() allows read and write value to be of different sizes.
// splitDbData() [0] allways corresponds to the read value
// splitDbData() [1] allways corresponds to the write value
//
// Revision 1.6  2006/02/15 14:54:49  ounsy
// indexOutOfBounds avoided
//
// Revision 1.5  2006/02/07 11:54:11  ounsy
// removed useless logs
//
// Revision 1.4  2006/02/06 12:42:20  ounsy
// -corrected a bug in the case of successive spectrums with different sizes
// -added RW spectrum support
// -adapted the spitDbData method for spectrum attributes
// -added a debug method "traceShort"
//
// Revision 1.3  2006/01/27 14:13:33  ounsy
// spectrum extraction allowed
//
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.2.3  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.1.2.2  2005/09/26 08:30:08  chinkumo
// no message
//
// Revision 1.1.2.1  2005/09/09 08:21:24  chinkumo
// First commit !
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

public class DbData
{

    String name;
    /**
     * Data type
     */
    int data_type;
    /**
     * Data format
     */
    int data_format;
    /**
     * Writable
     */
    int writable;
    /**
     * Max size for spectrums and images
     */
    int max_x;
    int max_y;
    /**
     * Data found in HDB/TDB
     */
    NullableTimedData[] timedData;

    public DbData(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getData_type()
    {
        return data_type;
    }

    public void setData_type(int data_type)
    {
        this.data_type = data_type;
    }

    public int getData_format()
    {
        return data_format;
    }

    public void setData_format(int data_format)
    {
        this.data_format = data_format;
    }

    public int getWritable()
    {
        return writable;
    }

    public void setWritable(int writable)
    {
        this.writable = writable;
    }

    public int getMax_x()
    {
        return max_x;
    }

    public void setMax_x(int max_x)
    {
        this.max_x = max_x;
    }

    public int getMax_y()
    {
        return max_y;
    }

    public void setMax_y(int max_y)
    {
        this.max_y = max_y;
    }

    public NullableTimedData[] getData()
    {
        return timedData;
    }

    public void setData(NullableTimedData[] data)
    {
        this.timedData = data;
    }

    public void traceShort ()
    {       
        System.out.println ( "traceShort------------------------------------" );
        if ( this.timedData == null )
        {
            System.out.println ( "this.timedAttrData == null" );    
           return;
        }
        if ( this.timedData.length == 0 )
        {
            System.out.println ( "this.timedAttrData.length == 0" );    
           return;
        }
        NullableTimedData aNullableTimedData = this.timedData [ this.timedData.length - 1 ];
        Short [] sh_ptr = (Short [])aNullableTimedData.value;
        for ( int j = 0 ; j < sh_ptr.length ; j ++)
        {
            System.out.println ( "j/"+j+"/sh_ptr[j]/"+sh_ptr[j] );
        }
        System.out.println ( "traceShort------------------------------------" );
    }
    
    public int size()
    {
        return ( timedData != null ) ? timedData.length : 0;
    }

    /**
     * Method used for spectrum extraction
     * @param values a Vector of SpectrumEvent_RO
     * @throws ArchivingException 26 janv. 2006
     */
    public void setData(Vector values) throws ArchivingException
    {
        if (values == null)
        {
            this.timedData = null;
            return;
        }
        int data_size = values.size();
        //System.out.println("values size :"+data_size);
        NullableTimedData[] data = new NullableTimedData[ data_size ];
        switch ( data_format )
        {
            case AttrDataFormat._SPECTRUM:
                
                switch ( writable )
                {
                    case AttrWriteType._WRITE:
                        for (int i = 0; i < data_size; i++)
                        {
                            SpectrumEvent_RO event = (SpectrumEvent_RO)values.get(i);
                            //System.out.println ( "CLA/DbData/setData/this.getMax_x ()/"+this.getMax_x ()+"/event.getDim_x()/"+event.getDim_x() );
                            //added CLA 30/01/06
                            if ( event.getDim_x() > this.getMax_x () )
                            {
                                this.setMax_x ( event.getDim_x() );    
                            }
                            
                            Long time = new Long(event.getTimeStamp());
                            Boolean[] bval;
                            Double[] dval;
                            Float[] fval;
                            Integer[] ival;
                            Short[] sval;
                            String[] stval;
                            switch ( data_type )
                            {
                                case TangoConst.Tango_DEV_SHORT:
                                case TangoConst.Tango_DEV_USHORT:
                                    sval = (Short[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = sval;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    ival = (Integer[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = ival;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    fval = (Float[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = fval;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    bval = (Boolean[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = bval;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    dval = (Double[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = dval;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    stval = (String[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = stval;
                                    data[i].x = event.getDim_x();
                                    break;
                                default:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                            }
                        }
                        //System.out.println("data length :" + data.length );
                        this.setData(data);
                        break;
                    case AttrWriteType._READ:
                        for (int i = 0; i < data_size; i++)
                        {
                            SpectrumEvent_RO event = (SpectrumEvent_RO)values.get(i);
                            //System.out.println ( "CLA/DbData/setData/this.getMax_x ()/"+this.getMax_x ()+"/event.getDim_x()/"+event.getDim_x() );
                            //added CLA 30/01/06
                            if ( event.getDim_x() > this.getMax_x () )
                            {
                                this.setMax_x ( event.getDim_x() );    
                            }
                            
                            Long time = new Long(event.getTimeStamp());
                            Boolean[] bval;
                            Double[] dval;
                            Float[] fval;
                            Integer[] ival;
                            Short[] sval;
                            String[] stval;
                            switch ( data_type )
                            {
                                case TangoConst.Tango_DEV_SHORT:
                                case TangoConst.Tango_DEV_USHORT:
                                    sval = (Short[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = sval;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_STATE:
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    ival = (Integer[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = ival;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    fval = (Float[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = fval;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    bval = (Boolean[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                   data[i].time = time;
                                    data[i].value = bval;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    dval = (Double[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = dval;
                                    data[i].x = event.getDim_x();
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    stval = (String[])event.getValue();
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = stval;
                                    data[i].x = event.getDim_x();
                                    break;
                                default:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                            }
                        }
                        //System.out.println("data length :" + data.length );
                        this.setData(data);
                        break;
                    case AttrWriteType._READ_WITH_WRITE:
                    case AttrWriteType._READ_WRITE:
                    
                    for (int i = 0; i < data_size; i++)
                    {
                        SpectrumEvent_RW event = (SpectrumEvent_RW)values.get(i);
                        
                        if ( event.getDim_x() > this.getMax_x () )
                        {
                            this.setMax_x ( event.getDim_x() );    
                        }
                        
                        Long time = new Long(event.getTimeStamp());
                        Boolean[] bval;
                        Double[] dval;
                        Float[] fval;
                        Integer[] ival;
                        Short[] sval;
                        String[] stval;
                        switch ( data_type )
                        {
                            case TangoConst.Tango_DEV_SHORT:
                            case TangoConst.Tango_DEV_USHORT:
                                sval = (Short[])event.getValue();
                                data[i] = new NullableTimedData();
                                data[i].data_type = data_type;
                               data[i].time = time;
                                data[i].value = sval;
                                data[i].x = event.getDim_x();
                                break;
                            case TangoConst.Tango_DEV_LONG:
                            case TangoConst.Tango_DEV_ULONG:
                                ival = (Integer[])event.getValue();
                                data[i] = new NullableTimedData();
                                data[i].data_type = data_type;
                                data[i].time = time;
                                data[i].value = ival;
                                data[i].x = event.getDim_x();
                                break;
                            case TangoConst.Tango_DEV_FLOAT:
                                fval = (Float[])event.getValue();
                                data[i] = new NullableTimedData();
                                data[i].data_type = data_type;
                                data[i].time = time;
                                data[i].value = fval;
                                data[i].x = event.getDim_x();
                                break;
                            case TangoConst.Tango_DEV_BOOLEAN:
                                bval = (Boolean[])event.getValue();
                                data[i] = new NullableTimedData();
                                data[i].data_type = data_type;
                                data[i].time = time;
                                data[i].value = bval;
                                data[i].x = event.getDim_x();
                                break;
                            case TangoConst.Tango_DEV_DOUBLE:
                                dval = (Double[])event.getValue();
                                data[i] = new NullableTimedData();
                                data[i].data_type = data_type;
                                data[i].time = time;
                                data[i].value = dval;
                                data[i].x = event.getDim_x();
                                break;
                            case TangoConst.Tango_DEV_STRING:
                                stval = (String[])event.getValue();
                                data[i] = new NullableTimedData();
                                data[i].data_type = data_type;
                                data[i].time = time;
                                data[i].value = stval;
                                data[i].x = event.getDim_x();
                              break;
                            default:
                                throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                        }
                    }
                    this.setData(data);
                    break;
                    default:
                        throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
                }
                break;
            case AttrDataFormat._SCALAR:
                //System.out.println("$$$$$scalar format$$$$$");
                switch ( writable )
                {
                    case AttrWriteType._WRITE:
                    case AttrWriteType._READ:
                        //System.out.println("$$$$$scalar format 1 val$$$$$");
                        for (int i = 0; i < data_size; i++)
                        {
                            ScalarEvent_RO event = (ScalarEvent_RO)values.get(i);
                            Long time = new Long(event.getTimeStamp());
                            switch ( data_type )
                            {
                                case TangoConst.Tango_DEV_STATE:
                                    Integer[] ival = new Integer[1];
                                    if (event.getScalarValue() == null)
                                    {
                                        ival[0] = null;
                                    }
                                    else
                                    {
                                        ival[0] = new Integer( (event.getScalarValue()).intValue() );
                                                              }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = ival;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    String[] sval = new String[1];
                                    data[i].time = time;
                                    data[i].value = sval;
                                    data[i].x = 1;
                                    break;
                                default:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                            }
                        }
                        this.setData(data);
                        break;
                    case AttrWriteType._READ_WITH_WRITE:
                    case AttrWriteType._READ_WRITE:
                        //System.out.println("$$$$$scalar format 2 vals$$$$$");
                        for (int i = 0; i < data_size; i++)
                        {
                            ScalarEvent_RW event = (ScalarEvent_RW)values.get(i);
                            Long time = new Long(event.getTimeStamp());
                            switch ( data_type )
                            {
                                case TangoConst.Tango_DEV_STATE:
                                    Integer[] ival = new Integer[2];
                                    if (event.getScalarValueRW()[0] == null)
                                    {
                                        ival[0] = null;
                                    }
                                    else
                                    {
                                        ival[0] = new Integer( event.getScalarValueRW()[0].intValue() );
                                    }
                                    if (event.getScalarValueRW()[1] == null)
                                    {
                                        ival[1] = null;
                                    }
                                    else
                                    {
                                        ival[1] = new Integer( event.getScalarValueRW()[1].intValue() );
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = ival;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    String[] sval = new String[2];
                                    sval = event.getScalarValueRWS();
                                    data[i].time = time;
                                    data[i].value = sval;
                                    data[i].x = 1;
                                    break;
                                default:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                            }
                        }
                        this.setData(data);
                        break;
                    default:
                        throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
                }
                break;
            case AttrDataFormat._IMAGE:
                this.setMax_x(0);
                this.setMax_y(0);
                switch ( writable )
                {
                    case AttrWriteType._READ:
                        for (int i = 0; i < data_size; i++)
                        {
                            ImageEvent_RO event = (ImageEvent_RO)values.get(i);
                            if ( event.getDim_x() > this.getMax_x () )
                            {
                                this.setMax_x ( event.getDim_x() );    
                            }
                            if ( event.getDim_y() > this.getMax_y () )
                            {
                                this.setMax_y ( event.getDim_y() );    
                            }

                            Long time = new Long(event.getTimeStamp());
                            Boolean[] bval;
                            Double[] dval;
                            Float[] fval;
                            Integer[] ival;
                            Short[] sval;
                            String[] stval;
                            switch ( data_type )
                            {
                                case TangoConst.Tango_DEV_SHORT:
                                case TangoConst.Tango_DEV_USHORT:
                                    Short[][] tempsval = (Short[][])event.getValue();
                                    if (tempsval == null || tempsval.length == 0 || tempsval[0].length == 0)
                                    {
                                        sval = new Short[0];
                                    }
                                    else
                                    {
                                        sval = new Short[tempsval.length * tempsval[0].length];
                                        int svalindex = 0;
                                        for (int j = 0; j < tempsval.length; j++)
                                        {
                                            for (int k = 0; k < tempsval[0].length; k++)
                                            {
                                                sval[svalindex++] = tempsval[j][k];
                                            }
                                        }
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = sval;
                                    data[i].x = event.getDim_x();
                                    data[i].y = event.getDim_y();
                                    break;
                                case TangoConst.Tango_DEV_STATE:
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    Integer[][] tempival = (Integer[][])event.getValue();
                                    if (tempival == null || tempival.length == 0 || tempival[0].length == 0)
                                    {
                                        ival = new Integer[0];
                                    }
                                    else
                                    {
                                        ival = new Integer[tempival.length * tempival[0].length];
                                        int ivalindex = 0;
                                        for (int j = 0; j < tempival.length; j++)
                                        {
                                            for (int k = 0; k < tempival[0].length; k++)
                                            {
                                                ival[ivalindex++] = tempival[j][k];
                                            }
                                        }
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = ival;
                                    data[i].x = event.getDim_x();
                                    data[i].y = event.getDim_y();
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    Float[][] tempfval = (Float[][])event.getValue();
                                    if (tempfval == null || tempfval.length == 0 || tempfval[0].length == 0)
                                    {
                                        fval = new Float[0];
                                    }
                                    else
                                    {
                                        fval = new Float[tempfval.length * tempfval[0].length];
                                        int fvalindex = 0;
                                        for (int j = 0; j < tempfval.length; j++)
                                        {
                                            for (int k = 0; k < tempfval[0].length; k++)
                                            {
                                                fval[fvalindex++] = tempfval[j][k];
                                            }
                                        }
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = fval;
                                    data[i].x = event.getDim_x();
                                    data[i].y = event.getDim_y();
                                    break;
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    Boolean[][] tempbval = (Boolean[][])event.getValue();
                                    if (tempbval == null || tempbval.length == 0 || tempbval[0].length == 0)
                                    {
                                        bval = new Boolean[0];
                                    }
                                    else
                                    {
                                        bval = new Boolean[tempbval.length * tempbval[0].length];
                                        int bvalindex = 0;
                                        for (int j = 0; j < tempbval.length; j++)
                                        {
                                            for (int k = 0; k < tempbval[0].length; k++)
                                            {
                                                bval[bvalindex++] = tempbval[j][k];
                                            }
                                        }
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = bval;
                                    data[i].x = event.getDim_x();
                                    data[i].y = event.getDim_y();
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    Double[][] tempdval = (Double[][])event.getValue();
                                    if (tempdval == null || tempdval.length == 0 || tempdval[0].length == 0)
                                    {
                                        dval = new Double[0];
                                    }
                                    else
                                    {
                                        dval = new Double[tempdval.length * tempdval[0].length];
                                        int dvalindex = 0;
                                        for (int j = 0; j < tempdval.length; j++)
                                        {
                                            for (int k = 0; k < tempdval[0].length; k++)
                                            {
                                                dval[dvalindex++] = tempdval[j][k];
                                            }
                                        }
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = dval;
                                    data[i].x = event.getDim_x();
                                    data[i].y = event.getDim_y();
                                    break;
                                case TangoConst.Tango_DEV_STRING:
                                    String[][] tempstval = (String[][])event.getValue();
                                    if (tempstval == null || tempstval.length == 0 || tempstval[0].length == 0)
                                    {
                                        stval = new String[0];
                                    }
                                    else
                                    {
                                        stval = new String[tempstval.length * tempstval[0].length];
                                        int stvalindex = 0;
                                        for (int j = 0; j < tempstval.length; j++)
                                        {
                                            for (int k = 0; k < tempstval[0].length; k++)
                                            {
                                                stval[stvalindex++] = tempstval[j][k];
                                            }
                                        }
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = stval;
                                    data[i].x = event.getDim_x();
                                    data[i].y = event.getDim_y();
                                    break;
                                default:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                            }
                        }

                        this.setData(data);
                        break;
                    case AttrWriteType._WRITE:
                    case AttrWriteType._READ_WITH_WRITE:
                    case AttrWriteType._READ_WRITE:
                    default:
                        throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
                }
                break;
            default:
                throw generateException(GlobalConst.DATA_FORMAT_EXCEPTION , data_format , name);
        }
    }

    public void setData(DevVarDoubleStringArray devVarDoubleStringArray) throws ArchivingException
    {
        if (devVarDoubleStringArray == null)
        {
            this.timedData = null;
            return;
        }
        int data_size = 0;
        if (data_type == TangoConst.Tango_DEV_STRING)
        {
            data_size = devVarDoubleStringArray.dvalue.length;
        }
        else
        {
            data_size = devVarDoubleStringArray.svalue.length;
        }
        NullableTimedData[] data = new NullableTimedData[ data_size ];

        for ( int i = 0 ; i < data.length ; i++ )
        {
            Long time = new Long(0);
            if (data_type == TangoConst.Tango_DEV_STRING)
            {
                time = new Long(( long ) devVarDoubleStringArray.dvalue[ i ]);
            }
            else
            {
                time = new Long(DateUtil.stringToMilli((String) devVarDoubleStringArray.svalue[i]));
            }
            Boolean[] bval;
            Double[] dval;
            Float[] fval;
            Integer[] ival;
            Short[] sval;
            String[] stval;
            switch ( data_format )
            {
                case AttrDataFormat._SCALAR:
                    switch ( writable )
                    {
                        case AttrWriteType._WRITE:
                        case AttrWriteType._READ:
                            switch ( data_type )
                            {
                                case TangoConst.Tango_DEV_SHORT:
                                case TangoConst.Tango_DEV_USHORT:
                                    sval = new Short[ 1 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        sval[0] = null;
                                    }
                                    else
                                    {
                                        sval[ 0 ] = new Short(( short ) devVarDoubleStringArray.dvalue[ i ]);
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = sval;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_STATE:
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    ival = new Integer[ 1 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        ival[0] = null;
                                    }
                                    else
                                    {
                                        ival[ 0 ] = new Integer(( int ) devVarDoubleStringArray.dvalue[ i ]);
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = ival;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    dval = new Double[ 1 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        long l = Double.doubleToRawLongBits(
                                                devVarDoubleStringArray.dvalue[i]
                                        );
                                        if (l == Double.doubleToRawLongBits(GlobalConst.NAN_FOR_NULL) ) {
                                            dval[0] = null;
                                        }
                                        else {
                                            dval[ 0 ] = new Double( devVarDoubleStringArray.dvalue[ i ] );
                                        }
                                    }
                                    else
                                    {
                                        dval[ 0 ] = new Double( devVarDoubleStringArray.dvalue[ i ] );
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = dval;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    fval = new Float[ 1 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        fval[0] = null;
                                    }
                                    else
                                    {
                                        fval[ 0 ] = new Float( ( float ) devVarDoubleStringArray.dvalue[ i ]);
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = fval;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    bval = new Boolean[ 1 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        bval[0] = null;
                                    }
                                    else
                                    {
                                        bval[ 0 ] = new Boolean( ( devVarDoubleStringArray.dvalue[ i ] == 0 ) ? false : true );
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = bval;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_CHAR:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                                case TangoConst.Tango_DEV_UCHAR:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                                case TangoConst.Tango_DEV_STRING:
                                    stval = new String[ 1 ];
                                    if (devVarDoubleStringArray.svalue[ i ] == null || "null".equals(devVarDoubleStringArray.svalue[ i ]))
                                    {
                                        stval[ 0 ] = null;
                                    }
                                    else
                                    {
                                        stval[ 0 ] = devVarDoubleStringArray.svalue[ i ];
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = stval;
                                    data[i].x = 1;
                                    break;
                            }
                            break;
                        case AttrWriteType._READ_WRITE:
                        case AttrWriteType._READ_WITH_WRITE:
                            switch ( data_type )
                            {
                                case TangoConst.Tango_DEV_SHORT:
                                case TangoConst.Tango_DEV_USHORT:
                                    sval = new Short[ 2 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        sval[0] = null;
                                    }
                                    else
                                    {
                                        sval[ 0 ] = new Short( ( short ) devVarDoubleStringArray.dvalue[ i ] );
                                    }
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i + data_size]))
                                    {
                                        sval[1] = null;
                                    }
                                    else
                                    {
                                        sval[ 1 ] = new Short( ( short ) devVarDoubleStringArray.dvalue[ i + data_size ] );
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = sval;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_LONG:
                                case TangoConst.Tango_DEV_ULONG:
                                    ival = new Integer[ 2 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        ival[0] = null;
                                    }
                                    else
                                    {
                                        ival[ 0 ] = new Integer( ( int ) devVarDoubleStringArray.dvalue[ i ] );
                                    }
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i + data_size]))
                                    {
                                        ival[1] = null;
                                    }
                                    else
                                    {
                                        ival[ 1 ] = new Integer( ( int ) devVarDoubleStringArray.dvalue[ i + data_size ] );
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = ival;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_DOUBLE:
                                    dval = new Double[ 2 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        long l = Double.doubleToRawLongBits(
                                                devVarDoubleStringArray.dvalue[i]
                                        );
                                        if (l == Double.doubleToRawLongBits(GlobalConst.NAN_FOR_NULL) ) {
                                            dval[0] = null;
                                        }
                                        else {
                                            dval[ 0 ] = new Double( devVarDoubleStringArray.dvalue[ i ] );
                                        }
                                    }
                                    else
                                    {
                                        dval[ 0 ] = new Double( devVarDoubleStringArray.dvalue[ i ] );
                                    }
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i + data_size]))
                                    {
                                        long l = Double.doubleToRawLongBits(
                                                devVarDoubleStringArray.dvalue[i+data_size]
                                        );
                                        if (l == Double.doubleToRawLongBits(GlobalConst.NAN_FOR_NULL) ) {
                                            dval[1] = null;
                                        }
                                        else {
                                            dval[ 1 ] = new Double( devVarDoubleStringArray.dvalue[ i + data_size ] );
                                        }
                                    }
                                    else
                                    {
                                        dval[ 1 ] = new Double( devVarDoubleStringArray.dvalue[ i + data_size ] );
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = dval;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_FLOAT:
                                    fval = new Float[ 2 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        fval[0] = null;
                                    }
                                    else
                                    {
                                        fval[ 0 ] = new Float( ( float ) devVarDoubleStringArray.dvalue[ i ] );
                                    }
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i + data_size]))
                                    {
                                        fval[1] = null;
                                    }
                                    else
                                    {
                                        fval[ 1 ] = new Float( ( float ) devVarDoubleStringArray.dvalue[ i + data_size ] );
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = fval;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_BOOLEAN:
                                    bval = new Boolean[ 2 ];
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i]))
                                    {
                                        bval[0] = null;
                                    }
                                    else
                                    {
                                        bval[ 0 ] = new Boolean( ( devVarDoubleStringArray.dvalue[ i ] == 0 ) ? false : true );
                                    }
                                    if (Double.isNaN(devVarDoubleStringArray.dvalue[i + data_size]))
                                    {
                                        bval[1] = null;
                                    }
                                    else
                                    {
                                        bval[ 1 ] = new Boolean( ( devVarDoubleStringArray.dvalue[ i + data_size ] == 0 ) ? false : true );
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = bval;
                                    data[i].x = 1;
                                    break;
                                case TangoConst.Tango_DEV_CHAR:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                                case TangoConst.Tango_DEV_UCHAR:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                                case TangoConst.Tango_DEV_STATE:
                                    throw generateException(GlobalConst.DATA_TYPE_EXCEPTION , data_type , name);
                                case TangoConst.Tango_DEV_STRING:
                                    stval = new String[ 2 ];
                                    if (devVarDoubleStringArray.svalue[ i ] == null || "null".equals(devVarDoubleStringArray.svalue[ i ]))
                                    {
                                        stval[ 0 ] = null;
                                    }
                                    else
                                    {
                                        stval[ 0 ] = devVarDoubleStringArray.svalue[ i ];
                                    }
                                    if (devVarDoubleStringArray.svalue[ i + data_size ] == null || "null".equals(devVarDoubleStringArray.svalue[ i + data_size ]))
                                    {
                                        stval[ 1 ] = null;
                                    }
                                    else
                                    {
                                        stval[ 1 ] = devVarDoubleStringArray.svalue[ i + data_size ];
                                    }
                                    data[i] = new NullableTimedData();
                                    data[i].data_type = data_type;
                                    data[i].time = time;
                                    data[i].value = stval;
                                    data[i].x = 1;
                                    break;
                            }
                            break;
                        default:
                            throw generateException(GlobalConst.DATA_WRITABLE_EXCEPTION , writable , name);
                    }
                    break;
                case AttrDataFormat._SPECTRUM:
                case AttrDataFormat._IMAGE:
                default:
                    throw generateException(GlobalConst.DATA_FORMAT_EXCEPTION , data_format , name);
            }
        }
        timedData = data;
    }

    /**
     * This method is used to cope with today attribute tango java limitations (read/write attribute are not supported !!).
     *
     * @return A two DbData object array; the first DbData is for read values; the second one for write values.
     */
    public DbData[] splitDbData() throws DevFailed
    {
        DbData[] argout = new DbData[ 2 ];
        DbData dbData_r = new DbData(name);
        dbData_r.setData_type(data_type);
        dbData_r.setData_format(data_format);
        dbData_r.setWritable(writable);
        dbData_r.setMax_x(max_x);
        dbData_r.setMax_y(max_y);

        DbData dbData_w = new DbData(name);
        dbData_w.setData_type(data_type);
        dbData_w.setData_format(data_format);
        dbData_w.setWritable(writable);
        dbData_w.setMax_x(0);
        dbData_w.setMax_y(max_y);

        if (timedData == null)
        {
            dbData_r.timedData = null;
            dbData_w.timedData = null;
            dbData_r.max_x = 0;
            dbData_r.max_y = 0;
            dbData_w.max_x = 0;
            dbData_w.max_y = 0;
            argout[0] = dbData_r;
            argout[1] = dbData_w;
            return argout;
        }

        NullableTimedData[] timedAttrData_r = new NullableTimedData[ timedData.length ];
        NullableTimedData[] timedAttrData_w = new NullableTimedData[ timedData.length ];
        for ( int i = 0 ; i < timedData.length ; i++ )
        {
            int dimWrite = 0;
            switch ( this.getData_type() )
            {
                case TangoConst.Tango_DEV_USHORT:   
                case TangoConst.Tango_DEV_SHORT:
                    Short[] sh_ptr_init = (Short[])timedData[ i ].value;
                    Short[] sh_ptr_read = null;
                    Short[] sh_ptr_write = null;
                    if (sh_ptr_init != null) {
                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            sh_ptr_read = new Short[ timedData[ i ].x * timedData[ i ].y ];
                            dimWrite = sh_ptr_init.length - (timedData[ i ].x * timedData[ i ].y);
                        }
                        else
                        {
                            sh_ptr_read = new Short[ timedData[ i ].x ];
                            dimWrite = sh_ptr_init.length - timedData[ i ].x;
                            if ( dimWrite > dbData_w.getMax_x() )
                            {
                                dbData_w.setMax_x(dimWrite);
                            }
                        }
     
                        if (dimWrite < 0) dimWrite = 0;
                        sh_ptr_write = new Short[ dimWrite ];
                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            for (int j = 0 ; j < timedData[ i ].x * timedData[ i ].y ; j++)
                            {
                                sh_ptr_read [ j ] = sh_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x * timedData[ i ].y ; j < sh_ptr_init.length ; j ++ )
                            {
                                sh_ptr_write [ j - timedData[ i ].x ] = sh_ptr_init[ j ];
                            }
                        }
                        else
                        {
                            for ( int j = 0 ; j < timedData[ i ].x ; j ++ )
                            {
                                sh_ptr_read [ j ] = sh_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x ; j < sh_ptr_init.length ; j ++ )
                            {
                                sh_ptr_write [ j - timedData[ i ].x ] = sh_ptr_init[ j ];
                            }
                        }
                    }

                    timedAttrData_r[ i ] = new NullableTimedData();
                    timedAttrData_r[i].data_type = data_type;
                    timedAttrData_r[ i ].x = timedData[ i ].x;
                    timedAttrData_r[ i ].y = timedData[ i ].y;
                    timedAttrData_r[ i ].time = timedData[ i ].time;
                    timedAttrData_w[ i ] = new NullableTimedData();
                    timedAttrData_w[i].data_type = data_type;
                    timedAttrData_w[ i ].x = timedData[ i ].x;
                    timedAttrData_w[ i ].y = timedData[ i ].y;
                    timedAttrData_w[ i ].time = timedData[ i ].time;
                    if (writable == AttrWriteType._WRITE)
                    {
                        timedAttrData_r[ i ].value = sh_ptr_write;
                        timedAttrData_w[ i ].value = sh_ptr_read;
                    }
                    else
                    {
                        timedAttrData_r[ i ].value = sh_ptr_read;
                        timedAttrData_w[ i ].value = sh_ptr_write;
                    }
                    break;

                case TangoConst.Tango_DEV_DOUBLE:
                    Double[] db_ptr_init = (Double[])timedData[ i ].value;
                    Double[] db_ptr_read = null;
                    Double[] db_ptr_write = null;
                    if (db_ptr_init != null) {
                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            db_ptr_read = new Double[ timedData[ i ].x * timedData[ i ].y ];
                            dimWrite = db_ptr_init.length - (timedData[ i ].x * timedData[ i ].y);
                        }
                        else
                        {
                            db_ptr_read = new Double[ timedData[ i ].x ];
                            dimWrite = db_ptr_init.length - timedData[ i ].x;
                            if ( dimWrite > dbData_w.getMax_x() )
                            {
                                dbData_w.setMax_x(dimWrite);
                            }
                        }
                        if (dimWrite < 0) dimWrite = 0;
                        db_ptr_write = new Double[ dimWrite ];

                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            for ( int j = 0 ; j < timedData[ i ].x * timedData[ i ].y ; j ++ )
                            {
                                db_ptr_read [ j ] = db_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x * timedData[ i ].y ; j < db_ptr_init.length ; j ++ )
                            {
                                db_ptr_write [ j - timedData[ i ].x ] = db_ptr_init[ j ];
                            }
                        }
                        else
                        {
                            for ( int j = 0 ; j < timedData[ i ].x ; j ++ )
                            {
                                db_ptr_read [ j ] = db_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x ; j < db_ptr_init.length ; j ++ )
                            {
                                db_ptr_write [ j - timedData[ i ].x ] = db_ptr_init[ j ];
                            }
                        }
                    }

                    timedAttrData_r[ i ] = new NullableTimedData();
                    timedAttrData_r[i].data_type = data_type;
                    timedAttrData_r[ i ].x = timedData[ i ].x;
                    timedAttrData_r[ i ].y = timedData[ i ].y;
                    timedAttrData_r[ i ].time = timedData[ i ].time;
                    timedAttrData_w[ i ] = new NullableTimedData();
                    timedAttrData_w[i].data_type = data_type;
                    timedAttrData_w[ i ].x = timedData[ i ].x;
                    timedAttrData_w[ i ].y = timedData[ i ].y;
                    timedAttrData_w[ i ].time = timedData[ i ].time;
                    if (writable == AttrWriteType._WRITE)
                    {
                        timedAttrData_r[ i ].value = db_ptr_write;
                        timedAttrData_w[ i ].value = db_ptr_read;
                    }
                    else
                    {
                        timedAttrData_r[ i ].value = db_ptr_read;
                        timedAttrData_w[ i ].value = db_ptr_write;
                    }
                    break;

                case TangoConst.Tango_DEV_FLOAT:
                    Float[] fl_ptr_init = (Float[])timedData[ i ].value;
                    Float[] fl_ptr_read = null;
                    Float[] fl_ptr_write = null;
                    if (fl_ptr_init != null) {
                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            fl_ptr_read = new Float[ timedData[ i ].x * timedData[ i ].y ];
                            dimWrite = fl_ptr_init.length - (timedData[ i ].x * timedData[ i ].y);
                        }
                        else
                        {
                            fl_ptr_read = new Float[ timedData[ i ].x ];
                            dimWrite = fl_ptr_init.length - timedData[ i ].x;
                            if ( dimWrite > dbData_w.getMax_x() )
                            {
                                dbData_w.setMax_x(dimWrite);
                            }
                        }
                        if (dimWrite < 0) dimWrite = 0;
                        fl_ptr_write = new Float[ dimWrite ];

                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            for ( int j = 0 ; j < timedData[ i ].x * timedData[ i ].y ; j ++ )
                            {
                                fl_ptr_read [ j ] = fl_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x * timedData[ i ].y ; j < fl_ptr_init.length ; j ++ )
                            {
                                fl_ptr_write [ j - timedData[ i ].x ] = fl_ptr_init[ j ];
                            }
                        }
                        else
                        {
                            for ( int j = 0 ; j < timedData[ i ].x ; j ++ )
                            {
                                fl_ptr_read [ j ] = fl_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x ; j < fl_ptr_init.length ; j ++ )
                            {
                                fl_ptr_write [ j - timedData[ i ].x ] = fl_ptr_init[ j ];
                            }
                        }
                    }

                    timedAttrData_r[ i ] = new NullableTimedData();
                    timedAttrData_r[i].data_type = data_type;
                    timedAttrData_r[ i ].x = timedData[ i ].x;
                    timedAttrData_r[ i ].y = timedData[ i ].y;
                    timedAttrData_r[ i ].time = timedData[ i ].time;
                    timedAttrData_w[ i ] = new NullableTimedData();
                    timedAttrData_w[i].data_type = data_type;
                    timedAttrData_w[ i ].x = timedData[ i ].x;
                    timedAttrData_w[ i ].y = timedData[ i ].y;
                    timedAttrData_w[ i ].time = timedData[ i ].time;
                    if (writable == AttrWriteType._WRITE)
                    {
                        timedAttrData_r[ i ].value = fl_ptr_write;
                        timedAttrData_w[ i ].value = fl_ptr_read;
                    }
                    else
                    {
                        timedAttrData_r[ i ].value = fl_ptr_read;
                        timedAttrData_w[ i ].value = fl_ptr_write;
                    }
                    break;

                case TangoConst.Tango_DEV_STATE:
                case TangoConst.Tango_DEV_ULONG:
                case TangoConst.Tango_DEV_LONG:                    
                    Integer[] lg_ptr_init = (Integer[])timedData[ i ].value;
                    Integer[] lg_ptr_read = null;
                    Integer[] lg_ptr_write = null;
                    if (lg_ptr_init != null) {
                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            lg_ptr_read = new Integer[ timedData[ i ].x * timedData[ i ].y ];
                            dimWrite = lg_ptr_init.length - (timedData[ i ].x * timedData[ i ].y);
                        }
                        else
                        {
                            lg_ptr_read = new Integer[ timedData[ i ].x ];
                            dimWrite = lg_ptr_init.length - timedData[ i ].x;
                            if ( dimWrite > dbData_w.getMax_x() )
                            {
                                dbData_w.setMax_x(dimWrite);
                            }
                        }
                        if (dimWrite < 0) dimWrite = 0;
                        lg_ptr_write = new Integer[ dimWrite ];

                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            for ( int j = 0 ; j < timedData[ i ].x * timedData[ i ].y ; j ++ )
                            {
                                lg_ptr_read [ j ] = lg_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x * timedData[ i ].y ; j < lg_ptr_init.length ; j ++ )
                            {
                                lg_ptr_write [ j - timedData[ i ].x ] = lg_ptr_init[ j ];
                            }
                        }
                        else
                        {
                            for ( int j = 0 ; j < timedData[ i ].x ; j ++ )
                            {
                                lg_ptr_read [ j ] = lg_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x ; j < lg_ptr_init.length ; j ++ )
                            {
                                lg_ptr_write [ j - timedData[ i ].x ] = lg_ptr_init[ j ];
                            }
                        }
                    }

                    timedAttrData_r[ i ] = new NullableTimedData();
                    timedAttrData_r[i].data_type = data_type;
                    timedAttrData_r[ i ].x = timedData[ i ].x;
                    timedAttrData_r[ i ].y = timedData[ i ].y;
                    timedAttrData_r[ i ].time = timedData[ i ].time;
                    timedAttrData_w[ i ] = new NullableTimedData();
                    timedAttrData_w[i].data_type = data_type;
                    timedAttrData_w[ i ].x = timedData[ i ].x;
                    timedAttrData_w[ i ].y = timedData[ i ].y;
                    timedAttrData_w[ i ].time = timedData[ i ].time;
                    if (writable == AttrWriteType._WRITE)
                    {
                        timedAttrData_r[ i ].value = lg_ptr_write;
                        timedAttrData_w[ i ].value = lg_ptr_read;
                    }
                    else
                    {
                        timedAttrData_r[ i ].value = lg_ptr_read;
                        timedAttrData_w[ i ].value = lg_ptr_write;
                    }
                    break;

                case TangoConst.Tango_DEV_BOOLEAN:
                    Boolean[] bool_ptr_init = (Boolean[])timedData[ i ].value;
                    Boolean[] bool_ptr_read = null;
                    Boolean[] bool_ptr_write = null;
                    if (bool_ptr_init != null) {
                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            bool_ptr_read = new Boolean[ timedData[ i ].x * timedData[ i ].y ];
                            dimWrite = bool_ptr_init.length - (timedData[ i ].x * timedData[ i ].y);
                        }
                        else
                        {
                            bool_ptr_read = new Boolean[ timedData[ i ].x ];
                            dimWrite = bool_ptr_init.length - timedData[ i ].x;
                            if ( dimWrite > dbData_w.getMax_x() )
                            {
                                dbData_w.setMax_x(dimWrite);
                            }
                        }
                        if (dimWrite < 0) dimWrite = 0;
                        bool_ptr_write = new Boolean[ dimWrite ];

                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            for ( int j = 0 ; j < timedData[ i ].x * timedData[ i ].y ; j ++ )
                            {
                                bool_ptr_read [ j ] = bool_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x * timedData[ i ].y ; j < bool_ptr_init.length ; j ++ )
                            {
                                bool_ptr_write [ j - timedData[ i ].x ] = bool_ptr_init[ j ];
                            }
                        }
                        else
                        {
                            for ( int j = 0 ; j < timedData[ i ].x ; j ++ )
                            {
                                bool_ptr_read [ j ] = bool_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x ; j < bool_ptr_init.length ; j ++ )
                            {
                                bool_ptr_write [ j - timedData[ i ].x ] = bool_ptr_init[ j ];
                            }
                        }
                    }

                    timedAttrData_r[ i ] = new NullableTimedData();
                    timedAttrData_r[i].data_type = data_type;
                    timedAttrData_r[ i ].x = timedData[ i ].x;
                    timedAttrData_r[ i ].y = timedData[ i ].y;
                    timedAttrData_r[ i ].time = timedData[ i ].time;
                    timedAttrData_w[ i ] = new NullableTimedData();
                    timedAttrData_w[i].data_type = data_type;
                    timedAttrData_w[ i ].x = timedData[ i ].x;
                    timedAttrData_w[ i ].y = timedData[ i ].y;
                    timedAttrData_w[ i ].time = timedData[ i ].time;
                    if (writable == AttrWriteType._WRITE)
                    {
                        timedAttrData_r[ i ].value = bool_ptr_write;
                        timedAttrData_w[ i ].value = bool_ptr_read;
                    }
                    else
                    {
                        timedAttrData_r[ i ].value = bool_ptr_read;
                        timedAttrData_w[ i ].value = bool_ptr_write;
                    }
                    break;

                case TangoConst.Tango_DEV_STRING:
                    String[] str_ptr_init = (String[])timedData[ i ].value;
                    String[] str_ptr_read = null;
                    String[] str_ptr_write = null;
                    if (str_ptr_init != null) {
                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            str_ptr_read = new String[ timedData[ i ].x * timedData[ i ].y ];
                            dimWrite = str_ptr_init.length - (timedData[ i ].x * timedData[ i ].y);
                        }
                        else
                        {
                            str_ptr_read = new String[ timedData[ i ].x ];
                            dimWrite = str_ptr_init.length - timedData[ i ].x;
                            if ( dimWrite > dbData_w.getMax_x() )
                            {
                                dbData_w.setMax_x(dimWrite);
                            }
                        }
                        if (dimWrite < 0) dimWrite = 0;
                        str_ptr_write = new String[ dimWrite ];

                        if (data_format == AttrDataFormat._IMAGE)
                        {
                            for ( int j = 0 ; j < timedData[ i ].x * timedData[ i ].y ; j ++ )
                            {
                                str_ptr_read [ j ] = str_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x * timedData[ i ].y ; j < str_ptr_init.length ; j ++ )
                            {
                                str_ptr_write [ j - timedData[ i ].x ] = str_ptr_init[ j ];
                            }
                        }
                        else
                        {
                            for ( int j = 0 ; j < timedData[ i ].x ; j ++ )
                            {
                                //System.out.println("&&&&&&str_ptr_init["+j+"]="+str_ptr_init[ j ]);
                                str_ptr_read [ j ] = str_ptr_init[ j ];
                            }
                            for ( int j = timedData[ i ].x ; j < str_ptr_init.length ; j ++ )
                            {
                                str_ptr_write [ j - timedData[ i ].x ] = str_ptr_init[ j ];
                            }
                        }
                    }

                    timedAttrData_r[ i ] = new NullableTimedData();
                    timedAttrData_r[i].data_type = data_type;
                    timedAttrData_r[ i ].x = timedData[ i ].x;
                    timedAttrData_r[ i ].y = timedData[ i ].y;
                    timedAttrData_r[ i ].time = timedData[ i ].time;
                    timedAttrData_w[ i ] = new NullableTimedData();
                    timedAttrData_w[i].data_type = data_type;
                    timedAttrData_w[ i ].x = timedData[ i ].x;
                    timedAttrData_w[ i ].y = timedData[ i ].y;
                    timedAttrData_w[ i ].time = timedData[ i ].time;
                    if (writable == AttrWriteType._WRITE)
                    {
                        timedAttrData_r[ i ].value = str_ptr_write;
                        timedAttrData_w[ i ].value = str_ptr_read;
                    }
                    else
                    {
                        timedAttrData_r[ i ].value = str_ptr_read;
                        timedAttrData_w[ i ].value = str_ptr_write;
                    }
                    break;
            }
        }
        dbData_r.setData(timedAttrData_r);
        dbData_w.setData(timedAttrData_w);
        switch(writable)
        {
            case AttrWriteType._READ:
                argout[ 0 ] = dbData_r;
                argout[ 1 ] = null;
                break;
            case AttrWriteType._WRITE:
                argout[ 0 ] = null;
                argout[ 1 ] = dbData_w;
                break;
            default:
                argout[ 0 ] = dbData_r;
                argout[ 1 ] = dbData_w;
        }
        return argout;
    }

    private static ArchivingException generateException(String cause , int cause_value , String name)
    {
        String message = GlobalConst.ARCHIVING_ERROR_PREFIX + " : " + cause;
        String reason = "Failed while executing AttributeSupport.checkAttributeSupport()...";
        String desc = cause + " (" + cause_value + ") not supported !! [" + name + "]";
        return new ArchivingException(message , reason , ErrSeverity.WARN , desc , "");
    }

    public TimedAttrData[] getDataAsTimedAttrData()
    {
        TimedAttrData[] attrData = null;
        if (timedData == null)
        {
            return null;
        }
        else
        {
            attrData = new TimedAttrData[timedData.length];
        }
        for(int i = 0; i < timedData.length; i++)
        {
            if (timedData[i] == null)
            {
                attrData[i] = null;
            }
            else
            {
                int sec = 0;
                if (timedData[i].time != null)
                {
                    sec = (int) (timedData[i].time.longValue() / 1000);
                }
                TimeVal timeVal = new TimeVal(sec,0,0);
                switch(this.data_type)
                {
                    case TangoConst.Tango_DEV_BOOLEAN:
                        boolean[] boolval;
                        if (timedData[i].value == null)
                        {
                            boolval = null;
                        }
                        else
                        {
                            boolval = new boolean[timedData[i].value.length];
                            for (int j = 0; j < timedData[i].value.length; j++)
                            {
                                boolean value = false;
                                if( timedData[i].value[j] != null )
                                {
                                    value = ((Boolean)timedData[i].value[j]).booleanValue();
                                }
                                boolval[j] = value;
                            }
                        }
                        attrData[i] = new TimedAttrData(boolval,timeVal);
                        boolval = null;
                        break;
                    case TangoConst.Tango_DEV_SHORT:
                    case TangoConst.Tango_DEV_USHORT:
                        //System.out.println("CLA/DbData/getDataAsTimedAttrData/Tango_DEV_SHORT!!!!!" );
                        short[] sval;
                        if (timedData[i].value == null)
                        {
                            //System.out.println("CLA/DbData/getDataAsTimedAttrData/Tango_DEV_SHORT!!!!! 0" );
                            sval = null;
                        }
                        else
                        {
                            sval = new short[timedData[i].value.length];
                            //System.out.println("CLA/DbData/getDataAsTimedAttrData/Tango_DEV_SHORT!!!!! 1/sval.l/"+sval.length );
                            for (int j = 0; j < timedData[i].value.length; j++)
                            {
                                //System.out.println("CLA/DbData/getDataAsTimedAttrData/Tango_DEV_SHORT!!!!! 2" );
                                short value = 0;
                                if( timedData[i].value[j] != null )
                                {
                                    //System.out.println("CLA/DbData/getDataAsTimedAttrData/Tango_DEV_SHORT!!!!! 3" );
                                    value = ((Short)timedData[i].value[j]).shortValue();
                                }
                                //System.out.println("CLA/DbData/getDataAsTimedAttrData/Tango_DEV_SHORT!!!!! 4" );
                                sval[j] = value;
                                //System.out.println("CLA/DbData/getDataAsTimedAttrData/Tango_DEV_SHORT!!!!! 5/j/"+j+"/sval[j]/"+sval[j] );
                            }
                        }
                        attrData[i] = new TimedAttrData(sval,timeVal);
                        //System.out.println("CLA/DbData/getDataAsTimedAttrData/timeVal.tv_sec/"+timeVal.tv_sec+"/timeVal.tv_nsec/"+timeVal.tv_nsec+"/timeVal.tv_usec/"+timeVal.tv_usec );                        
                        sval = null;
                        break;
                    case TangoConst.Tango_DEV_LONG:
                    case TangoConst.Tango_DEV_ULONG:
                        int[] ival;
                        if (timedData[i].value == null)
                        {
                            ival = null;
                        }
                        else
                        {
                            ival = new int[timedData[i].value.length];
                            for (int j = 0; j < timedData[i].value.length; j++)
                            {
                                int value = 0;
                                if( timedData[i].value[j] != null )
                                {
                                    value = ((Integer)timedData[i].value[j]).intValue();
                                }
                                ival[j] = value;
                            }
                        }
                        attrData[i] = new TimedAttrData(ival,timeVal);
                        ival = null;
                        break;
                    case TangoConst.Tango_DEV_FLOAT:
                        float[] fval;
                        if (timedData[i].value == null)
                        {
                            fval = null;
                        }
                        else
                        {
                            fval = new float[timedData[i].value.length];
                            for (int j = 0; j < timedData[i].value.length; j++)
                            {
                                float value = 0;
                                if( timedData[i].value[j] != null )
                                {
                                    value = ((Float)timedData[i].value[j]).floatValue();
                                }
                                fval[j] = value;
                            }
                        }
                        attrData[i] = new TimedAttrData(fval,timeVal);
                        fval = null;
                        break;
                    case TangoConst.Tango_DEV_DOUBLE:
                        double[] dval;
                        if (timedData[i].value == null)
                        {
                            dval = null;
                        }
                        else
                        {
                            dval = new double[timedData[i].value.length];
                            for (int j = 0; j < timedData[i].value.length; j++)
                            {
                                double value = Double.NaN;
                                if( timedData[i].value[j] != null )
                                {
                                    value = ((Double)timedData[i].value[j]).doubleValue();
                                }
                                dval[j] = value;
                            }
                        }
                        attrData[i] = new TimedAttrData(dval,timeVal);
                        dval = null;
                        break;
                    case TangoConst.Tango_DEV_STRING:
                        String[] strval;
                        if (timedData[i].value == null)
                        {
                            strval = null;
                        }
                        else
                        {
                            strval = new String[timedData[i].value.length];
                            for (int j = 0; j < timedData[i].value.length; j++)
                            {
                                String value = null;
                                if( timedData[i].value[j] != null )
                                {
                                    value = new String((String)timedData[i].value[j]);
                                }
                                strval[j] = value;
                            }
                        }
                        attrData[i] = new TimedAttrData(strval,timeVal);
                        strval = null;
                        break;
                    default:
                        attrData[i] = new TimedAttrData(new DevError[0], timeVal);
                }
                attrData[i].qual = timedData[i].qual;
                
                
                
                attrData[i].x = timedData[i].x;
                attrData[i].y = timedData[i].y;
                attrData[i].data_type = this.data_type;
                timeVal = null;
            }
        }
        return attrData;
    }

}
