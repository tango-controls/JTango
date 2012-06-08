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
// Revision 1.3  2007/07/02 12:03:46  ounsy
// Correction for tango web access
//
// Revision 3.21  2007/06/07 09:23:56  pascal_verdier
// extractBoolean() added (was removed unfornutatly).
//
// Revision 3.20  2007/05/29 08:11:15  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.19  2007/01/10 10:11:07  pascal_verdier
// DevFailed message syntax fixed.
//
// Revision 3.18  2006/09/19 13:25:29  pascal_verdier
// Access control management added.
//
// Revision 3.17  2006/03/20 13:05:53  pascal_verdier
// extractCharArray() method added.
//
// Revision 3.16  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.15  2005/11/29 05:32:02  pascal_verdier
// Bug in getType() method for UCHAR fixed.
//
// Revision 3.14  2005/05/18 12:46:22  pascal_verdier
// getType() method added.
//
// Revision 3.13  2004/12/16 10:16:44  pascal_verdier
// Missing TANGO 5 features added.
//
// Revision 3.12  2004/12/09 13:41:14  pascal_verdier
// New Attribute types added (insert and extract methods).
//
// Revision 3.11  2004/12/09 12:13:30  pascal_verdier
// If read attribute failed WrongData exception is thrown for all get methods.
//
// Revision 3.10  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.9  2004/11/05 12:38:56  pascal_verdier
// Bug on DeviceAttribute.extract fixed.
//
// Revision 3.8  2004/10/11 12:23:27  pascal_verdier
// Example in header modified.
//
// Revision 3.7  2004/09/17 07:57:03  pascal_verdier
// Attribute for Devive_3Impl (Tango 5) implemented.
//
// Revision 3.6  2004/08/17 08:36:39  pascal_verdier
// An exception is now thrown if quality factor is invalid.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
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
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.AttributeValue;
import fr.esrf.Tango.AttributeValue_3;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DevState;
import fr.esrf.Tango.TimeVal;
import fr.esrf.webapi.IWebImpl;
import fr.esrf.webapi.WebServerClientUtil;

/**
 * Class Description: This class manage data object for Tango device attribute
 * access. <Br>
 * <Br>
 * <Br>
 * <b> Usage example: </b> <Br>
 * <ul>
 * <i> DeviceAttribute devattr = dev.read_attribute("Current"); <Br>
 * if (devattr.hasFailed())<Br> {
 * <ul>
 * Except.print_exception(devattr.getErrStack());
 * </ul>} else <Br> {
 * <ul>
 * double current = devattr.extractDouble(); <Br>
 * System.out.println("Current : " + current);
 * </ul>}
 * </ul>
 * </i>
 * 
 * @author verdier
 * @version $Revision$
 */

public class DeviceAttributeDAOWebImpl implements IDeviceAttributeDAO, IWebImpl {
	private Object[] classParam = null;

	AttributeValue_3 attrval = new AttributeValue_3();

	// ===========================================
	/**
	 * Build a DeviceAttribute IDL object
	 * 
	 * @param name
	 *            Attribute name.
	 */
	// ===========================================
	// TODO remove javadoc
	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param attrval
	 *            AttributeValue_3 IDL object.
	 */
	// ===========================================
	public void init(AttributeValue_3 attrval) {
		classParam = new Object[] { attrval };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param attrval
	 *            AttributeValue IDL object.
	 */
	// ===========================================
	public void init(AttributeValue attrval) {
		classParam = new Object[] { attrval };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 */
	// ===========================================
	public void init(String name) {
		classParam = new Object[] { name };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void init(String name, int dim_x, int dim_y) {
		classParam = new Object[] { name, dim_x, dim_y };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, boolean value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, DevState value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, boolean[] value, int dim_x, int dim_y) {
		classParam = new Object[] { name, value, dim_x, dim_y };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, byte value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, byte[] value, int dim_x, int dim_y) {
		classParam = new Object[] { name, value, dim_x, dim_y };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, short value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param values
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void init(String name, short[] values, int dim_x, int dim_y) {
		classParam = new Object[] { name, values, dim_x, dim_y };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, int value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param values
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void init(String name, int[] values, int dim_x, int dim_y) {
		classParam = new Object[] { name, values, dim_x, dim_y };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, long value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param values
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void init(String name, long[] values, int dim_x, int dim_y) {
		classParam = new Object[] { name, values, dim_x, dim_y };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, float value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param values
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void init(String name, float[] values, int dim_x, int dim_y) {
		classParam = new Object[] { name, values, dim_x, dim_y };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, double value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param values
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void init(String name, double[] values, int dim_x, int dim_y) {
		classParam = new Object[] { name, values, dim_x, dim_y };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	// ===========================================
	public void init(String name, String value) {
		classParam = new Object[] { name, value };
	}

	// ===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param values
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void init(String name, String[] values, int dim_x, int dim_y) {
		classParam = new Object[] { name, values, dim_x, dim_y };
	}

	// ===========================================
	// ===========================================
	public boolean hasFailed() {
		try {
			return (Boolean) WebServerClientUtil.getResponse(this, classParam, "hasFailed", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return false;
		}
	}

	// ===========================================
	/**
	 * Returns the attribute errors list
	 */
	// ===========================================
	public DevError[] getErrStack() {
		try {
			return (DevError[]) WebServerClientUtil.getResponse(this, classParam, "getErrStack", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * Set the AttributeValue internal object with input one.
	 * 
	 * @param val
	 *            AttributeValue_3 input object
	 */
	// ===========================================
	public void setAttributeValue(AttributeValue_3 attrval) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setAttributeValue", new Object[] { attrval }, new Class[] { AttributeValue_3.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Set the AttributeValue internal object with input one.
	 * 
	 * @param val
	 *            AttributeValue input object
	 */
	// ===========================================
	public void setAttributeValue(AttributeValue attrval) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "setAttributeValue", new Object[] { attrval }, new Class[] { AttributeValue.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	// Insert methods
	// ===========================================

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(DevState argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { DevState.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 */
	// ===========================================
	public void insert(DevState[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { DevState[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert(DevState[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin, dim_x, dim_y }, new Class[] { DevState[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(boolean argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { boolean.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 */
	// ===========================================
	public void insert(boolean[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { boolean[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert(boolean[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin, dim_x, dim_y }, new Class[] { boolean[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_uc(byte argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_uc", new Object[] { argin }, new Class[] { byte.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_uc(short argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_uc", new Object[] { argin }, new Class[] { short.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_uc(short[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_uc", new Object[] { argin }, new Class[] { short[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            nb data.in x direction
	 * @param dim_y
	 *            nb data.in y direction
	 */
	// ===========================================
	public void insert_uc(short[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_uc", new Object[] { argin, dim_x, dim_y }, new Class[] { short[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_uc(byte[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_uc", new Object[] { argin, dim_x, dim_y }, new Class[] { byte[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(short argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { short.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(short[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { short[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert(short[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin, dim_x, dim_y }, new Class[] { short[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_us(short argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin }, new Class[] { short.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_us(int argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin }, new Class[] { int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_us(short[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin }, new Class[] { short[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_us(int[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin }, new Class[] { int[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_us(short[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin, dim_x, dim_y }, new Class[] { short[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_us(int[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_us", new Object[] { argin, dim_x, dim_y }, new Class[] { int[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(int argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(int[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { int[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert(int[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin, dim_x, dim_y }, new Class[] { int[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(long argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { long.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(long[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { long[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert(long[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin, dim_x, dim_y }, new Class[] { long[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_ul(int argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin }, new Class[] { int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_ul(long argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin }, new Class[] { long.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_ul(int[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin }, new Class[] { int[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_ul(long[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin }, new Class[] { long[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_ul(int[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin, dim_x, dim_y }, new Class[] { int[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_ul(long[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_ul", new Object[] { argin, dim_x, dim_y }, new Class[] { long[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_u64(long argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u64", new Object[] { argin }, new Class[] { long.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert_u64(long[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u64", new Object[] { argin }, new Class[] { long[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert_u64(long[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert_u64", new Object[] { argin, dim_x, dim_y }, new Class[] { long[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(float argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { float.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(float[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { float[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert(float[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin, dim_x, dim_y }, new Class[] { float[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(double argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { double.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(double[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { double[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert(double[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin, dim_x, dim_y }, new Class[] { double[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	// ===========================================
	public void insert(String argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { String.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 */
	// ===========================================
	public void insert(String[] argin) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin }, new Class[] { String[].class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 * @param dim_x
	 *            array dimention in X
	 * @param dim_y
	 *            array dimention in Y
	 */
	// ===========================================
	public void insert(String[] argin, int dim_x, int dim_y) {
		try {
			WebServerClientUtil.getResponse(this, classParam, "insert", new Object[] { argin, dim_x, dim_y }, new Class[] { String[].class, int.class, int.class });

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
		}
	}

	// ===========================================
	/**
	 * Throws exception if err_list not null.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	// TODO remove javadoc
	// ===========================================
	/**
	 * extract method for an DevState Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public DevState[] extractDevStateArray() throws DevFailed {
		return (DevState[]) WebServerClientUtil.getResponse(this, classParam, "extractDevStateArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for an DevState.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public DevState extractDevState() throws DevFailed {
		return (DevState) WebServerClientUtil.getResponse(this, classParam, "extractDevState", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for an boolean.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public boolean extractBoolean() throws DevFailed {
		return (Boolean) WebServerClientUtil.getResponse(this, classParam, "extractBoolean", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for an boolean Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public boolean[] extractBooleanArray() throws DevFailed {
		return (boolean[]) WebServerClientUtil.getResponse(this, classParam, "extractBooleanArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for an unsigned char.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public short extractUChar() throws DevFailed {
		return (Short) WebServerClientUtil.getResponse(this, classParam, "extractUChar", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for an unsigned char Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public short[] extractUCharArray() throws DevFailed {
		return (short[]) WebServerClientUtil.getResponse(this, classParam, "extractUCharArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for an unsigned char Array as a char array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public byte[] extractCharArray() throws DevFailed {
		return (byte[]) WebServerClientUtil.getResponse(this, classParam, "extractCharArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a short.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public short extractShort() throws DevFailed {
		return (Short) WebServerClientUtil.getResponse(this, classParam, "extractShort", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a short Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public short[] extractShortArray() throws DevFailed {
		return (short[]) WebServerClientUtil.getResponse(this, classParam, "extractShortArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for an unsigned short.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public int extractUShort() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "extractUShort", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for an unsigned short Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public int[] extractUShortArray() throws DevFailed {
		return (int[]) WebServerClientUtil.getResponse(this, classParam, "extractUShortArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a long.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public int extractLong() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "extractLong", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a long Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public int[] extractLongArray() throws DevFailed {
		return (int[]) WebServerClientUtil.getResponse(this, classParam, "extractLongArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a unsigned long.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public long extractULong() throws DevFailed {
		return (Long) WebServerClientUtil.getResponse(this, classParam, "extractULong", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a unsigned long.array
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public long[] extractULongArray() throws DevFailed {
		return (long[]) WebServerClientUtil.getResponse(this, classParam, "extractULongArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a long.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public long extractLong64() throws DevFailed {
		return (Long) WebServerClientUtil.getResponse(this, classParam, "extractLong64", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a long Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public long[] extractLong64Array() throws DevFailed {
		return (long[]) WebServerClientUtil.getResponse(this, classParam, "extractLong64Array", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a long.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public long extractULong64() throws DevFailed {
		return (Long) WebServerClientUtil.getResponse(this, classParam, "extractULong64", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a long Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public long[] extractULong64Array() throws DevFailed {
		return (long[]) WebServerClientUtil.getResponse(this, classParam, "extractULong64Array", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a float.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public float extractFloat() throws DevFailed {
		return (Float) WebServerClientUtil.getResponse(this, classParam, "extractFloat", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a float Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public float[] extractFloatArray() throws DevFailed {
		return (float[]) WebServerClientUtil.getResponse(this, classParam, "extractFloatArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a double.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public double extractDouble() throws DevFailed {
		return (Double) WebServerClientUtil.getResponse(this, classParam, "extractDouble", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a double Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public double[] extractDoubleArray() throws DevFailed {
		return (double[]) WebServerClientUtil.getResponse(this, classParam, "extractDoubleArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a DevState (state attribute).
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public DevState extractState() throws DevFailed {
		return (DevState) WebServerClientUtil.getResponse(this, classParam, "extractState", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a String.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public String extractString() throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "extractString", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * extract method for a double Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	// ===========================================
	public String[] extractStringArray() throws DevFailed {
		return (String[]) WebServerClientUtil.getResponse(this, classParam, "extractStringArray", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute quality
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public AttrQuality getQuality() throws DevFailed {
		return (AttrQuality) WebServerClientUtil.getResponse(this, classParam, "getQuality", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute time value.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public TimeVal getTimeVal() throws DevFailed {
		return (TimeVal) WebServerClientUtil.getResponse(this, classParam, "getTimeVal", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute time value in seconds since EPOCH.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public long getTimeValSec() throws DevFailed {
		return (Long) WebServerClientUtil.getResponse(this, classParam, "getTimeValSec", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute time value in seconds since EPOCH.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public long getTimeValMillisSec() throws DevFailed {
		return (Long) WebServerClientUtil.getResponse(this, classParam, "getTimeValMillisSec", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute name.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public String getName() throws DevFailed {
		return (String) WebServerClientUtil.getResponse(this, classParam, "getName", new Object[] {}, new Class[] {});

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
	public int getNbRead() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getNbRead", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return number of data written.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getNbWritten() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getNbWritten", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute dim_x.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getDimX() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getDimX", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute dim_y.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getDimY() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getDimY", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute written dim_x.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getWrittenDimX() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getWrittenDimX", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return attribute written dim_y.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public int getWrittenDimY() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getWrittenDimY", new Object[] {}, new Class[] {});

	}

	// ===========================================
	/**
	 * Return AttributeValue IDL object.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public AttributeValue getAttributeValueObject_2() {
		try {
			return (AttributeValue) WebServerClientUtil.getResponse(this, classParam, "getAttributeValueObject_2", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * Return AttributeValue IDL object.
	 */
	// ===========================================
	public AttributeValue_3 getAttributeValueObject_3() {
		try {
			return (AttributeValue_3) WebServerClientUtil.getResponse(this, classParam, "getAttributeValueObject_3", new Object[] {}, new Class[] {});

		} catch (DevFailed dfe) {
			dfe.printStackTrace();
			return null;
		}
	}

	// ===========================================
	/**
	 * return time in milliseconds since 1/1/70
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	// ===========================================
	public long getTime() throws DevFailed {
		return (Long) WebServerClientUtil.getResponse(this, classParam, "getTime", new Object[] {}, new Class[] {});

	}

	// ===========================================
	// ===========================================
	public int getType() throws DevFailed {
		return (Integer) WebServerClientUtil.getResponse(this, classParam, "getType", new Object[] {}, new Class[] {});

	}

	public Object[] getClassParam() {
		return classParam;
	}

	public void setClassParam(Object[] classParam) {
		this.classParam = classParam;
	}
}
