//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2009/01/13 10:56:47  viguier
// Update to TangoORB 7
//
// Revision 1.2  2008/02/18 15:16:03  ounsy
// adapt WebTangORB with the TangORB modification
//
// Revision 1.1  2007/07/04 12:55:33  ounsy
// creation of 3 sub modules :
// 	- client for the webtangorb classes
// 	- common for the classes used by webtangorb and the tangowebserver
// 	- server for the generic classes of tangowebserver
//
// Revision 1.3  2007/07/02 12:03:46  ounsy
// Correction for tango web access
//
// Revision 3.11  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.10  2005/11/29 05:33:29  pascal_verdier
// extractUChar() and extractUCharArray() methods added.
//
// Revision 3.9  2005/06/13 09:05:18  pascal_verdier
// Minor bugs fixed.
//
// Revision 3.8  2004/12/16 10:16:44  pascal_verdier
// Missing TANGO 5 features added.
//
// Revision 3.7  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.6  2004/05/14 14:21:33  pascal_verdier
// Add timeout at runtime.
// Some little bugs fixed.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
// Asynchronous calls added.
// Logging related methods.
// little bugs fixed.
//
// Revision 2.0  2003/01/09 14:00:37  verdier
// jacORB is now the ORB used.
//
// Revision 1.8  2002/06/26 09:02:17  verdier
// tested with atkpanel on a TACO device
//
// Revision 1.7  2002/04/09 12:21:51  verdier
// IDL 2 implemented.
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;


import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import fr.esrf.webapi.IWebImpl;
import fr.esrf.webapi.WebServerClientUtil;

import org.omg.CORBA.Any;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;

/**
 * Class Description: This class manage data object for Tango device history
 * Data access. <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> DeviceDataHistory[] histo = dev.command_history("ReadCurrent", 10); <Br>
 * for (int i=0 ; i < histo.length ; i++) <Br> { <Br>
 * <ul>
 * Date d = new Date(histo[i].getTime()); <Br>
 * double[] values = histo[i].extractDoubleArray(); <Br>
 * </ul> } <Br>
 * </ul>
 * </i>
 * 
 * @author verdier
 * @version $Revision$
 */

public class DeviceDataHistoryDAOWebImpl implements IDeviceDataHistoryDAO, IWebImpl {
	private Object[] classParam = null;

	/**
	 * History from command history.
	 */
	public static final int COMMAND = 0;

	/**
	 * History from attribute history.
	 */
	public static final int ATTRIBUTE = 1;

	/**
	 * Data source DeviceDataHistory.COMMAND or DeviceDataHistory.ATTRIBUTE
	 */
	public int source;

	/**
	 * Command/Attribute name.
	 */
	public String name;

	/**
	 * true if command/attribute failed.
	 */
	public boolean failed;

	/**
	 * Error list if any in reading Command/Attribute.
	 */
	public DevError[] errors;

	// ===========================================
	/**
	 * Constructor from a DevCmdHistory.
	 */
	// ===========================================
	public void init(DeviceDataHistory deviceDataHistory, String cmdname, DevCmdHistory cmd_hist) throws DevFailed {
		classParam = new Object[] { cmdname, cmd_hist };
	}

	// ===========================================
	/**
	 * Constructor from an AttributeValue.
	 */
	// ===========================================
	public void init(DeviceDataHistory deviceDataHistory, DevAttrHistory att_histo) throws DevFailed {
		classParam = new Object[] { att_histo };
	}

	// ===========================================
	/**
	 * Constructor from an AttributeValue for Device_3Impl.
	 */
	// ===========================================
	public void init(DeviceDataHistory deviceDataHistory, DevAttrHistory_3 att_histo) throws DevFailed {
		classParam = new Object[] { att_histo };
	}

	//===========================================
	/**
	 *	Constructor from an AttributeValue for Device_4Impl.
	 */
	//===========================================
	public void init(DeviceDataHistory deviceDataHistory, String name, int source, TimeVal time) throws DevFailed {
		DevAttrHistory_3 att_histo = new DevAttrHistory_3(false, new AttributeValue_3(null, null, time, name, null, null, null));
		classParam = new Object[] { att_histo };
	}
	//===========================================
	/**
	 *	Constructor from an AttributeValue for Device_4Impl.
	 */
	//===========================================
	public void init(DeviceDataHistory deviceDataHistory, String name, int source, long t) throws DevFailed {
		int	sec  = (int) (t/1000);
		int usec = (int) ((t-1000*sec)*1000);
		TimeVal	time = new TimeVal(sec, usec, 0);
		DevAttrHistory_3 att_histo = new DevAttrHistory_3(false, new AttributeValue_3(null, null, time, name, null, null, null));
		classParam = new Object[] { att_histo };
	}
	// ===========================================
	/**
	 * Return attribute time value.
	 */
	// ===========================================
	public TimeVal getTimeVal(DeviceDataHistory deviceDataHistory) {
		try {
			return (TimeVal) WebServerClientUtil.getResponse(this, classParam, "getTimeVal", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	//===========================================
	/**
	 *	Set attribute time value.
	 */
	//===========================================
	public void setTimeVal(DeviceDataHistory arg0, TimeVal arg1) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setTimeVal", new Object[] { arg1 }, new Class[] { TimeVal.class});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}
	
	// ===========================================
	/**
	 * Return attribute time value in seconds since EPOCH.
	 */
	// ===========================================
	public long getTimeValSec(DeviceDataHistory deviceDataHistory) {
		try {
			return (Long) WebServerClientUtil.getResponse(this, classParam, "getTimeValSec", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * return time in milliseconds since EPOCH to build a Date class.
	 */
	// ===========================================
	public long getTime(DeviceDataHistory deviceDataHistory) {
		try {
			return (Long) WebServerClientUtil.getResponse(this, classParam, "getTime", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}
	
	//===========================================
	/**
	 *	Set AttrQuality if from attribute.
	 */
	//===========================================
	public void setAttrQuality(DeviceDataHistory deviceDataHistory, AttrQuality arg1) throws DevFailed {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setAttrQuality", new Object[] { arg1 }, new Class[] { DevError[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	//===========================================
	/**
	 *	Ret Seturn attribute dim_x if from attribute.
	 */
	//===========================================
	public void setDimX(DeviceDataHistory deviceDataHistory, int arg1) throws DevFailed {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setDimX", new Object[] { arg1 }, new Class[] { DevError[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}
	
	//===========================================
	/**
	 *	Ret Seturn attribute dim_y if from attribute.
	 */
	//===========================================
	public void setDimY(DeviceDataHistory deviceDataHistory, int arg1) throws DevFailed {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setDimY", new Object[] { arg1 }, new Class[] { DevError[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}
	
	// ===========================================
	/**
	 * return AttrQuality if from attribute.
	 */
	// ===========================================
	public AttrQuality getAttrQuality(DeviceDataHistory deviceDataHistory) throws DevFailed {
		return (AttrQuality) WebServerClientUtil.getResponse(this, classParam, "getAttrQuality", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute dim_x if from attribute.
	 */
	// ===========================================
	public int getDimX(DeviceDataHistory deviceDataHistory) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getDimX", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute dim_y if from attribute.
	 */
	// ===========================================
	public int getDimY(DeviceDataHistory deviceDataHistory) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getDimY", new Object[] {}, new Class[] {});

	}
	//===========================================
	/**
	 *	Returns true is attribute failed
	 */
	//===========================================
	public boolean hasFailed(DeviceDataHistory deviceDataHistory) {
		try {
			return (Boolean) WebServerClientUtil.getResponse(this, classParam, "hasFailed", new Object[] {}, new Class[] {});
		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return true;
		}
	}
	//===========================================
	/**
	 *	Set the attribute errors list
	 */
	//===========================================
	public void setErrStack(DeviceDataHistory deviceDataHistory, DevError[] arg1) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setErrStack", new Object[] { arg1 }, new Class[] { DevError[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}
	
	// ********** Extract Methods for basic types *********************

	// ===========================================
	/**
	 * extract method for a CORBA Any.
	 */
	// ===========================================
	public Any extractAny(DeviceDataHistory deviceDataHistory) {
		try {
			return (Any) WebServerClientUtil.getResponse(this, classParam, "extractAny", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a boolean.
	 */
	// ===========================================
	public boolean extractBoolean(DeviceDataHistory deviceDataHistory) {
		try {
			return (Boolean) WebServerClientUtil.getResponse(this, classParam, "extractBoolean", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return false;
		}
	}

	// ===========================================
	/**
	 * extract method for an unsigned char.
	 * 
	 * @return the extracted value.
	 */
	// ===========================================
	public short extractUChar(DeviceDataHistory deviceDataHistory) throws DevFailed {
		return (Short) WebServerClientUtil.getResponse(this, classParam, "extractUChar", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a short.
	 */
	// ===========================================
	public short extractShort(DeviceDataHistory deviceDataHistory) {
		try {
			return (Short) WebServerClientUtil.getResponse(this, classParam, "extractShort", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * extract method for an unsigned short.
	 */
	// ===========================================
	public short extractUShort(DeviceDataHistory deviceDataHistory) {
		try {
			return (Short) WebServerClientUtil.getResponse(this, classParam, "extractUShort", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * extract method for a long.
	 */
	// ===========================================
	public int extractLong(DeviceDataHistory deviceDataHistory) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "extractLong", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * extract method for an unsigned long.
	 */
	// ===========================================
	public int extractULong(DeviceDataHistory deviceDataHistory) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "extractULong", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}
	//===========================================
	/**
	 *	extract method for a long.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public long extractLong64(DeviceDataHistory deviceDataHistory) throws DevFailed {
		try {
			return (Long) WebServerClientUtil.getResponse(this, classParam, "extractLong64", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}
	//===========================================
	/**
	 *	extract method for a long.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public long extractULong64(DeviceDataHistory arg0) throws DevFailed {
		try {
			return (Long) WebServerClientUtil.getResponse(this, classParam, "extractLong64", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}
	
	// ===========================================
	/**
	 * extract method for a float.
	 */
	// ===========================================
	public float extractFloat(DeviceDataHistory deviceDataHistory) {
		try {
			return (Float) WebServerClientUtil.getResponse(this, classParam, "extractFloat", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * extract method for a double.
	 */
	// ===========================================
	public double extractDouble(DeviceDataHistory deviceDataHistory) {
		try {
			return (Double) WebServerClientUtil.getResponse(this, classParam, "extractDouble", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * extract method for a String.
	 */
	// ===========================================
	public String extractString(DeviceDataHistory deviceDataHistory) {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "extractString", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a DevState.
	 */
	// ===========================================
	public DevState extractDevState(DeviceDataHistory deviceDataHistory) {
		try {
			return (DevState) WebServerClientUtil.getResponse(this, classParam, "extractDevState", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}
	//TODO Remove comment for TANGO 7
	/*
	public DevEncoded extractDevEncoded(DeviceDataHistory deviceDataHistory) throws DevFailed {
		try {
			return (DevEncoded) WebServerClientUtil.getResponse(this, classParam, "extractDevEncoded", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}
	*/
	// ********** Extract Methods for sequence types *********************

	// ===========================================
	/**
	 * extract method for a byte Array.
	 */
	// ===========================================
	public boolean[] extractBooleanArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (boolean[]) WebServerClientUtil.getResponse(this, classParam, "extractBooleanArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a byte Array.
	 */
	// ===========================================
	public byte[] extractByteArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (byte[]) WebServerClientUtil.getResponse(this, classParam, "extractByteArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for an unsigned char Array.
	 * 
	 * @return the extracted value.
	 */
	// ===========================================
	public short[] extractUCharArray(DeviceDataHistory deviceDataHistory) throws DevFailed {
		return (short[]) WebServerClientUtil.getResponse(this, classParam, "extractUCharArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a short Array.
	 */
	// ===========================================
	public short[] extractShortArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (short[]) WebServerClientUtil.getResponse(this, classParam, "extractShortArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for an unsigned short Array.
	 */
	// ===========================================
	public short[] extractUShortArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (short[]) WebServerClientUtil.getResponse(this, classParam, "extractUShortArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a long Array.
	 */
	// ===========================================
	public int[] extractLongArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (int[]) WebServerClientUtil.getResponse(this, classParam, "extractLongArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for an unsigned long Array.
	 */
	// ===========================================
	public int[] extractULongArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (int[]) WebServerClientUtil.getResponse(this, classParam, "extractULongArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	public long[] extractLong64Array(DeviceDataHistory deviceDataHistory) throws DevFailed {
		try {
			return (long[]) WebServerClientUtil.getResponse(this, classParam, "extractLong64Array", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}
	
	public long[] extractULong64Array(DeviceDataHistory deviceDataHistory) throws DevFailed {
		try {
			return (long[]) WebServerClientUtil.getResponse(this, classParam, "extractULong64Array", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a float Array.
	 */
	// ===========================================
	public float[] extractFloatArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (float[]) WebServerClientUtil.getResponse(this, classParam, "extractFloatArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a double Array.
	 */
	// ===========================================
	public double[] extractDoubleArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (double[]) WebServerClientUtil.getResponse(this, classParam, "extractDoubleArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a String Array.
	 */
	// ===========================================
	public String[] extractStringArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (String[]) WebServerClientUtil.getResponse(this, classParam, "extractStringArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	//===========================================
	/**
	 *	extract method for an DevState Array.
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 *				or if AttrQuality is ATTR_INVALID.
	 */
	//===========================================
	public DevState[] extractDevStateArray(DeviceDataHistory deviceDataHistory) throws DevFailed {
		try {
			return (DevState[]) WebServerClientUtil.getResponse(this, classParam, "extractDevStateArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	//===========================================
	/**
	 *	extract method for a DevEncoded Array.
	 */
	//===========================================
	//TODO Remove comment for TANGO 7
	/*
	public DevEncoded[] extractDevEncodedArray(DeviceDataHistory deviceDataHistory) throws DevFailed {
		try {
			return (DevEncoded[]) WebServerClientUtil.getResponse(this, classParam, "extractDevEncodedArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}
	*/
	// ===========================================
	/**
	 * extract method for a DevVarLongStringArray.
	 */
	// ===========================================
	public DevVarLongStringArray extractLongStringArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (DevVarLongStringArray) WebServerClientUtil.getResponse(this, classParam, "extractLongStringArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a DevVarDoubleStringArray.
	 */
	// ===========================================
	public DevVarDoubleStringArray extractDoubleStringArray(DeviceDataHistory deviceDataHistory) {
		try {
			return (DevVarDoubleStringArray) WebServerClientUtil.getResponse(this, classParam, "extractDoubleStringArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * Returns the attribute errors list
	 */
	// ===========================================
	public DevError[] getErrStack(DeviceDataHistory deviceDataHistory) {
		try {
			return (DevError[]) WebServerClientUtil.getResponse(this, classParam, "getErrStack", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * Returns the attribute type
	 */
	// ===========================================
	public TypeCode type(DeviceDataHistory deviceDataHistory) {
		try {
			return (TypeCode) WebServerClientUtil.getResponse(this, classParam, "type", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * Return attribute name.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public String getName(DeviceDataHistory deviceDataHistory) {
		try {
			return (String) WebServerClientUtil.getResponse(this, classParam, "getName", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	// ===========================================
	// TODO remove javadoc

	// ===========================================
	/**
	 * Return number of data read.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getNbRead(DeviceDataHistory deviceDataHistory) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "getNbRead", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * Return number of data written.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getNbWritten(DeviceDataHistory deviceDataHistory) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "getNbWritten", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * Return attribute written dim_x.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getWrittenDimX(DeviceDataHistory deviceDataHistory) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "getWrittenDimX", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	// ===========================================
	/**
	 * Return attribute written dim_y.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getWrittenDimY(DeviceDataHistory deviceDataHistory) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "getWrittenDimY", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return 0;
		}
	}

	//===========================================
	/**
	 *	Set attribute written dim_x.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public void setWrittenDimX(DeviceDataHistory deviceDataHistory, int dim) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setWrittenDimX", new Object[] {dim}, new Class[] { int.class});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
		
	}
	//===========================================
	/**
	 *	Set attribute written dim_y.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public void setWrittenDimY(DeviceDataHistory deviceDataHistory, int dim) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setWrittenDimY", new Object[] {dim}, new Class[] { int.class});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
		
	}
	// ===========================================
	/**
	 * Returns attribute Tango type.
	 */
	// ===========================================
	public int getType(DeviceDataHistory deviceDataHistory) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getType", new Object[] {}, new Class[] {});

	}

	public int getDataLength(DeviceDataHistory deviceDataHistory) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getDataLength", new Object[] {}, new Class[] {});
	}
	
	public Object[] getClassParam() {
		return classParam;
	}

	public void setClassParam(Object[] classParam) {
		this.classParam = classParam;
	}

	public void insert(DeviceDataHistory deviceDataHistory, double[] values) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values}, new Class[] { double[].class });	
	}

	public void insert(DeviceDataHistory deviceDataHistory, String[] values) throws DevFailed {
		WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values }, new Class[] { String[].class });	
	}
	public int insert(DeviceDataHistory deviceDataHistory, boolean[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { boolean[].class, int.class });	
	}
	public int insert(DeviceDataHistory deviceDataHistory, short[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { short[].class, int.class });
	}
	public int insert(DeviceDataHistory deviceDataHistory, int[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { int[].class, int.class });
	}
	public int insert(DeviceDataHistory deviceDataHistory, long[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { int[].class, int.class });
	}
	public int insert(DeviceDataHistory deviceDataHistory, float[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { float[].class, int.class });
	}
	public int insert(DeviceDataHistory deviceDataHistory, double[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { double[].class, int.class });
	}
	public int insert(DeviceDataHistory deviceDataHistory, String[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { String[].class, int.class });	
	}
	public int insert(DeviceDataHistory deviceDataHistory, DevState[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { DevState[].class, int.class });	
	}
	//TODO Remove comment for TANGO 7
	/*
	public int insert(DeviceDataHistory deviceDataHistory, DevEncoded[] values, int base) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { values, base}, new Class[] { DevEncoded[].class, int.class });	
	}
	*/
	public int[] insert(DeviceDataHistory deviceDataHistory, DevVarLongStringArray lsa, int[] bases) throws DevFailed {
		return (int[]) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { lsa, bases}, new Class[] { DevVarLongStringArray.class, int[].class });
	}
	public int[] insert(DeviceDataHistory deviceDataHistory, DevVarDoubleStringArray lsa, int[] bases) throws DevFailed {
		return (int[]) WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { lsa, bases}, new Class[] { DevVarDoubleStringArray.class, int[].class });
	}
}
