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
// Revision 1.1  2007/07/04 12:55:34  ounsy
// creation of 3 sub modules :
// 	- client for the webtangorb classes
// 	- common for the classes used by webtangorb and the tangowebserver
// 	- server for the generic classes of tangowebserver
//
// Revision 1.3  2007/07/02 12:03:46  ounsy
// Correction for tango web access
//
// Revision 3.11  2007/05/29 08:11:15  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.10  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.9  2005/09/14 07:33:33  pascal_verdier
// Bug fixed in getData() method.
//
// Revision 3.8  2005/08/10 08:11:29  pascal_verdier
// Default value modified in getType() method.
//
// Revision 3.7  2005/06/22 13:28:25  pascal_verdier
// getType() method added.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.1  2003/09/08 11:03:25  pascal_verdier
// *** empty log message ***
//
// Revision 3.0  2003/04/29 08:03:29  pascal_verdier
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
// Revision 1.6  2002/01/09 12:18:15  verdier
// TACO signals can be read as TANGO attribute.
//
// Revision 1.5  2001/12/10 14:19:42  verdier
// TACO JNI Interface added.
// URL syntax used for connection.
// Connection on device without database added.
//
// Revision 1.4  2001/07/04 14:06:05  verdier
// Attribute management added.
//
// Revision 1.3  2001/04/02 08:32:05  verdier
// TangoApi package has users...
//
// Revision 1.1  2001/02/02 13:03:46  verdier
// Initial revision
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
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;

/**
 * Class Description: This class manage data object for Tango device access.
 * 
 * <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> String status; <Br>
 * DeviceProxy dev = new DeviceProxy("sys/steppermotor/1"); <Br>
 * try { <Br>
 * <ul>
 * DeviceData data = dev.command_inout("DevStatus"); <Br>
 * status = data.extractString(); <Br>
 * </ul> } <Br>
 * catch (DevFailed e) { <Br>
 * <ul>
 * status = "Unknown status"; <Br>
 * Except.print_exception(e); <Br>
 * </ul> } <Br>
 * </ul>
 * </i>
 * 
 * @author verdier
 * @version $Revision$
 */

public class DeviceDataDAOWebImpl implements TangoConst, IDeviceDataDAO, IWebImpl {
	private Object[] classParam = null;

	Any any;

	// ===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	// ===========================================================
	public void init(DeviceData deviceData) throws DevFailed {
		classParam = new Object[] {};
	}

	// ===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @param orb
	 *            orb connection id.
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	// ===========================================================
	public void init(DeviceData deviceData, ORB orb) throws DevFailed {
		classParam = new Object[] { orb };
	}

	// ===========================================================
	/**
	 * Constructor for the TgApi Data Object.
	 * 
	 * @param any
	 *            CORBA Any reference to be used in DeviceData.
	 * @throws DevFailed
	 *             if TgApi class not instancied.
	 */
	// ===========================================================
	public void init(DeviceData deviceData, Any any) throws DevFailed {
		classParam = new Object[] { any };
	}

	// ********** Insert Methods for basic types *********************

	// ===========================================
	/**
	 * Insert method for argin is void.
	 */
	// ===========================================
	public void insert(DeviceData deviceData) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is Any (CORBA).
	 */
	// ===========================================
	public void insert(DeviceData deviceData, Any any) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { any }, new Class[] { Any.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is boolean.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, boolean argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { boolean.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is short.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, short argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { short.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is long.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, long argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { long.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is int.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, int argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is float.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, float argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { float.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is double.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, double argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { double.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is String.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, String argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { String.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevState.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, DevState argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { DevState.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ********** Insert Methods for sequence types *********************

	// ===========================================
	/**
	 * Insert method for argin is DevVarCharArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, byte[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { byte[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevVarShortArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, short[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { short[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevVarLongArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, int[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { int[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevVarLongArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, long[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { long[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevVarFloatArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, float[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { float[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevVarDoubleArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, double[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { double[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevVarStringArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, String[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { String[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevVarLongStringArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, DevVarLongStringArray argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { DevVarLongStringArray.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is DevVarDoubleStringArray.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert(DeviceData deviceData, DevVarDoubleStringArray argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { DevVarDoubleStringArray.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ********** Insert Methods for unsigned types *********************

	// ===========================================
	/**
	 * Insert method for argin is unsigned long 64.array
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_u64(DeviceData deviceData, long[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u64", new Object[] { argin }, new Class[] { long[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned long 64.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_u64(DeviceData deviceData, long argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u64", new Object[] { argin }, new Class[] { long.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin int as unsigned short.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_us(DeviceData deviceData, int argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin }, new Class[] { int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned short.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_us(DeviceData deviceData, short argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin }, new Class[] { short.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned short.
	 * 
	 * @param argin
	 *            argin value for next command.
	 * @deprecated use insert_us(short/int argin)
	 */
	// ===========================================
	public void insert_u(DeviceData deviceData, short argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u", new Object[] { argin }, new Class[] { short.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin long ass unsigned int.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_ul(DeviceData deviceData, long argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin }, new Class[] { long.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned int.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_ul(DeviceData deviceData, int argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin }, new Class[] { int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned int.
	 * 
	 * @param argin
	 *            argin value for next command.
	 * @deprecated use insert_ul(int/long argin)
	 */
	// ===========================================
	public void insert_u(DeviceData deviceData, int argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u", new Object[] { argin }, new Class[] { int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin int as unsigned short array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_us(DeviceData deviceData, int[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin }, new Class[] { int[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned short array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_us(DeviceData deviceData, short[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin }, new Class[] { short[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned short array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 * @deprecated use insert_us(short[]/int[] argin)
	 */
	// ===========================================
	public void insert_u(DeviceData deviceData, short[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u", new Object[] { argin }, new Class[] { short[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin long array as unsigned int array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_ul(DeviceData deviceData, long[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin }, new Class[] { long[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned int array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 */
	// ===========================================
	public void insert_ul(DeviceData deviceData, int[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin }, new Class[] { int[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for argin is unsigned int array.
	 * 
	 * @param argin
	 *            argin value for next command.
	 * @deprecated use insert_ul(int[]/long[] argin)
	 */
	// ===========================================
	public void insert_u(DeviceData deviceData, int[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u", new Object[] { argin }, new Class[] { int[].class });

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
	public Any extractAny(DeviceData deviceData) {
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
	public boolean extractBoolean(DeviceData deviceData) {
		try {
			return (Boolean) WebServerClientUtil.getResponse(this, classParam, "extractBoolean", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return false;
		}
	}

	// ===========================================
	/**
	 * extract method for a short.
	 */
	// ===========================================
	public short extractShort(DeviceData deviceData) {
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
	public int extractUShort(DeviceData deviceData) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "extractUShort", new Object[] {}, new Class[] {});

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
	public int extractLong(DeviceData deviceData) {
		try {
			return (Integer) WebServerClientUtil.getResponse(this, classParam, "extractLong", new Object[] {}, new Class[] {});

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
	public long extractLong64(DeviceData deviceData) {
		try {
			return (Long) WebServerClientUtil.getResponse(this, classParam, "extractLong64", new Object[] {}, new Class[] {});

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
	public long extractULong(DeviceData deviceData) {
		try {
			return (Long) WebServerClientUtil.getResponse(this, classParam, "extractULong", new Object[] {}, new Class[] {});

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
	public long extractULong64(DeviceData deviceData) {
		try {
			return (Long) WebServerClientUtil.getResponse(this, classParam, "extractULong64", new Object[] {}, new Class[] {});

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
	public float extractFloat(DeviceData deviceData) {
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
	public double extractDouble(DeviceData deviceData) {
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
	public String extractString(DeviceData deviceData) {
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
	public DevState extractDevState(DeviceData deviceData) {
		try {
			return (DevState) WebServerClientUtil.getResponse(this, classParam, "extractDevState", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ********** Extract Methods for sequence types *********************

	// ===========================================
	/**
	 * extract method for a byte Array.
	 */
	// ===========================================
	public byte[] extractByteArray(DeviceData deviceData) {
		try {
			return (byte[]) WebServerClientUtil.getResponse(this, classParam, "extractByteArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a short Array.
	 */
	// ===========================================
	public short[] extractShortArray(DeviceData deviceData) {
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
	 * 
	 * @return extract value as int array
	 */
	// ===========================================
	public int[] extractUShortArray(DeviceData deviceData) {
		try {
			return (int[]) WebServerClientUtil.getResponse(this, classParam, "extractUShortArray", new Object[] {}, new Class[] {});

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
	public int[] extractLongArray(DeviceData deviceData) {
		try {
			return (int[]) WebServerClientUtil.getResponse(this, classParam, "extractLongArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a long64 Array.
	 */
	// ===========================================
	public long[] extractLong64Array(DeviceData deviceData) {
		try {
			return (long[]) WebServerClientUtil.getResponse(this, classParam, "extractLong64Array", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for an unsigned long Array.
	 * 
	 * @return extract value as long array
	 */
	// ===========================================
	public long[] extractULongArray(DeviceData deviceData) {
		try {
			return (long[]) WebServerClientUtil.getResponse(this, classParam, "extractULongArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a long64 Array.
	 */
	// ===========================================
	public long[] extractULong64Array(DeviceData deviceData) {
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
	public float[] extractFloatArray(DeviceData deviceData) {
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
	public double[] extractDoubleArray(DeviceData deviceData) {
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
	public String[] extractStringArray(DeviceData deviceData) {
		try {
			return (String[]) WebServerClientUtil.getResponse(this, classParam, "extractStringArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * extract method for a DevVarLongStringArray.
	 */
	// ===========================================
	public DevVarLongStringArray extractLongStringArray(DeviceData deviceData) {
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
	public DevVarDoubleStringArray extractDoubleStringArray(DeviceData deviceData) {
		try {
			return (DevVarDoubleStringArray) WebServerClientUtil.getResponse(this, classParam, "extractDoubleStringArray", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	public TypeCode type(DeviceData deviceData) {
		try {
			return (TypeCode) WebServerClientUtil.getResponse(this, classParam, "type", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	// ===========================================
	public int getType(DeviceData deviceData) throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getType", new Object[] {}, new Class[] {});

	}

	public Object[] getClassParam() {
		return classParam;
	}

	public void setClassParam(Object[] classParam) {
		this.classParam = classParam;
	}
}
