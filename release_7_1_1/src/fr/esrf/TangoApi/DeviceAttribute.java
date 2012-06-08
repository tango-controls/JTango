//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO clent/server API.
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision$
//
// $Log$
// Revision 1.10  2008/12/19 13:27:52  pascal_verdier
// Data from union for Device_4Impl.
//
// Revision 1.9  2008/12/03 15:40:08  pascal_verdier
// javadoc warnings removed.
//
// Revision 1.8  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.7  2008/09/12 11:20:52  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.6  2008/07/30 11:27:42  pascal_verdier
// insert/extract UChar added
//
// Revision 1.5  2007/08/23 09:42:20  ounsy
// update for synchonize source code with tango/api/java
//
// Revision 1.3  2007/07/02 12:05:38  ounsy
// Minor modification of common classes :
// - add getter and setter
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
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.Tango.factory.TangoFactory;

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

public class DeviceAttribute {
	private IDeviceAttributeDAO deviceattributeDAO = null;


	public DeviceAttribute()
	{
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param attrval
	 *            AttributeValue_4 IDL object.
	 */
	//===========================================
	public DeviceAttribute(AttributeValue_4 attrval) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(attrval);
	}
	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param attrval
	 *            AttributeValue_3 IDL object.
	 */
	//===========================================
	public DeviceAttribute(AttributeValue_3 attrval) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(attrval);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param attrval
	 *            AttributeValue IDL object.
	 */
	//===========================================
	public DeviceAttribute(AttributeValue attrval) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(attrval);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 */
	//===========================================
	public DeviceAttribute(String name) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name);
	}

	//===========================================
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
	//===========================================
	public DeviceAttribute(String name, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, dim_x, dim_y);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, boolean value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, DevState value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, boolean[] value, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value, dim_x, dim_y);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, byte value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, byte[] value, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value, dim_x, dim_y);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, short value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
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
	//===========================================
	public DeviceAttribute(String name, short[] values, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, values, dim_x, dim_y);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, int value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
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
	//===========================================
	public DeviceAttribute(String name, int[] values, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, values, dim_x, dim_y);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, long value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
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
	//===========================================
	public DeviceAttribute(String name, long[] values, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, values, dim_x, dim_y);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, float value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
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
	//===========================================
	public DeviceAttribute(String name, float[] values, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, values, dim_x, dim_y);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, double value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
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
	//===========================================
	public DeviceAttribute(String name, double[] values, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, values, dim_x, dim_y);
	}

	//===========================================
	/**
	 * DeviceAttribute class constructor.
	 * 
	 * @param name
	 *            Attribute name.
	 * @param value
	 *            Attribute value.
	 */
	//===========================================
	public DeviceAttribute(String name, String value) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, value);
	}

	//===========================================
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
	//===========================================
	public DeviceAttribute(String name, String[] values, int dim_x, int dim_y) {
		deviceattributeDAO = TangoFactory.getSingleton().getDeviceAttributeDAO();
		deviceattributeDAO.init(name, values, dim_x, dim_y);
	}

	//===========================================
	//===========================================
	public boolean hasFailed() {
		return deviceattributeDAO.hasFailed();
	}

	//===========================================
	/**
	 * Returns the attribute errors list
	 */
	//===========================================
	public DevError[] getErrStack() {
		return deviceattributeDAO.getErrStack();
	}

	//===========================================
	/**
	 * Set the AttributeValue internal object with input one.
	 * 
	 * @param attrval	 AttributeValue_3 input object
	 */
	//===========================================
	public void setAttributeValue(AttributeValue_3 attrval) {
		deviceattributeDAO.setAttributeValue(attrval);
	}

	//===========================================
	/**
	 * Set the AttributeValue internal object with input one.
	 * 
	 * @param attrval	 AttributeValue input object
	 */
	//===========================================
	public void setAttributeValue(AttributeValue attrval) {
		deviceattributeDAO.setAttributeValue(attrval);
	}

	//===========================================
	// Insert methods
	//===========================================

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(DevState argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin	Attribute values.
	 */
	//===========================================
	public void insert(DevState[] argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
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
	//===========================================
	public void insert(DevState[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(boolean argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(boolean[] argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
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
	//===========================================
	public void insert(boolean[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_uc(byte argin) {
		deviceattributeDAO.insert_uc(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_uc(byte[] argin) {
		deviceattributeDAO.insert_uc(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_uc(short argin) {
		deviceattributeDAO.insert_uc(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_uc(short[] argin) {
		deviceattributeDAO.insert_uc(argin);
	}

	//===========================================
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
	//===========================================
	public void insert_uc(short[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert_uc(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_uc(byte[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert_uc(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(short argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(short[] argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
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
	//===========================================
	public void insert(short[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_us(short argin) {
		deviceattributeDAO.insert_us(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_us(int argin) {
		deviceattributeDAO.insert_us(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_us(short[] argin) {
		deviceattributeDAO.insert_us(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_us(int[] argin) {
		deviceattributeDAO.insert_us(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_us(short[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert_us(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_us(int[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert_us(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(int argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(int[] argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
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
	//===========================================
	public void insert(int[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(long argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(long[] argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
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
	//===========================================
	public void insert(long[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_ul(int argin) {
		deviceattributeDAO.insert_ul(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values as unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_ul(long argin) {
		deviceattributeDAO.insert_ul(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_ul(int[] argin) {
		deviceattributeDAO.insert_ul(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_ul(long[] argin) {
		deviceattributeDAO.insert_ul(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_ul(int[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert_ul(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute valuesas unsigned.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_ul(long[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert_ul(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_u64(long argin) {
		deviceattributeDAO.insert_u64(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert_u64(long[] argin) {
		deviceattributeDAO.insert_u64(argin);
	}

	//===========================================
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
	//===========================================
	public void insert_u64(long[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert_u64(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(float argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(float[] argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
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
	//===========================================
	public void insert(float[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(double argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(double[] argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
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
	//===========================================
	public void insert(double[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert(argin, dim_x, dim_y);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(String argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * Insert method for attribute values.
	 * 
	 * @param argin
	 *            Attribute values.
	 */
	//===========================================
	public void insert(String[] argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
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
	//===========================================
	public void insert(String[] argin, int dim_x, int dim_y) {
		deviceattributeDAO.insert(argin, dim_x, dim_y);
	}
	//===========================================
	/**
	 *	Insert method for attribute values.
	 *
	 *	@param argin	Attribute values.
	 */
	//===========================================
	public void insert(DevEncoded argin) {
		deviceattributeDAO.insert(argin);
	}

	//===========================================
	/**
	 * extract method for an DevState Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public DevState[] extractDevStateArray() throws DevFailed {
		return deviceattributeDAO.extractDevStateArray();
	}

	//===========================================
	/**
	 * extract method for an DevState.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public DevState extractDevState() throws DevFailed {
		return deviceattributeDAO.extractDevState();
	}

	//===========================================
	/**
	 * extract method for an boolean.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public boolean extractBoolean() throws DevFailed {
		return deviceattributeDAO.extractBoolean();
	}

	//===========================================
	/**
	 * extract method for an boolean Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public boolean[] extractBooleanArray() throws DevFailed {
		return deviceattributeDAO.extractBooleanArray();
	}

	//===========================================
	/**
	 * extract method for an unsigned char.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public short extractUChar() throws DevFailed {
		return deviceattributeDAO.extractUChar();
	}

	//===========================================
	/**
	 * extract method for an unsigned char Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public short[] extractUCharArray() throws DevFailed {
		return deviceattributeDAO.extractUCharArray();
	}

	//===========================================
	/**
	 * extract method for an unsigned char Array as a char array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public byte[] extractCharArray() throws DevFailed {
		return deviceattributeDAO.extractCharArray();
	}

	//===========================================
	/**
	 * extract method for a short.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public short extractShort() throws DevFailed {
		return deviceattributeDAO.extractShort();
	}

	//===========================================
	/**
	 * extract method for a short Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public short[] extractShortArray() throws DevFailed {
		return deviceattributeDAO.extractShortArray();
	}

	//===========================================
	/**
	 * extract method for an unsigned short.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public int extractUShort() throws DevFailed {
		return deviceattributeDAO.extractUShort();
	}

	//===========================================
	/**
	 * extract method for an unsigned short Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public int[] extractUShortArray() throws DevFailed {
		return deviceattributeDAO.extractUShortArray();
	}

	//===========================================
	/**
	 * extract method for a long.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public int extractLong() throws DevFailed {
		return deviceattributeDAO.extractLong();
	}

	//===========================================
	/**
	 * extract method for a long Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public int[] extractLongArray() throws DevFailed {
		return deviceattributeDAO.extractLongArray();
	}

	//===========================================
	/**
	 * extract method for a unsigned long.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public long extractULong() throws DevFailed {
		return deviceattributeDAO.extractULong();
	}

	//===========================================
	/**
	 * extract method for a unsigned long.array
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public long[] extractULongArray() throws DevFailed {
		return deviceattributeDAO.extractULongArray();
	}

	//===========================================
	/**
	 * extract method for a long.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public long extractLong64() throws DevFailed {
		return deviceattributeDAO.extractLong64();
	}

	//===========================================
	/**
	 * extract method for a long Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public long[] extractLong64Array() throws DevFailed {
		return deviceattributeDAO.extractLong64Array();
	}

	//===========================================
	/**
	 * extract method for a long.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public long extractULong64() throws DevFailed {
		return deviceattributeDAO.extractULong64();
	}

	//===========================================
	/**
	 * extract method for a long Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public long[] extractULong64Array() throws DevFailed {
		return deviceattributeDAO.extractULong64Array();
	}

	//===========================================
	/**
	 * extract method for a float.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public float extractFloat() throws DevFailed {
		return deviceattributeDAO.extractFloat();
	}

	//===========================================
	/**
	 * extract method for a float Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public float[] extractFloatArray() throws DevFailed {
		return deviceattributeDAO.extractFloatArray();
	}

	//===========================================
	/**
	 * extract method for a double.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public double extractDouble() throws DevFailed {
		return deviceattributeDAO.extractDouble();
	}

	//===========================================
	/**
	 * extract method for a double Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public double[] extractDoubleArray() throws DevFailed {
		return deviceattributeDAO.extractDoubleArray();
	}

	//===========================================
	/**
	 * extract method for a DevState (state attribute).
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public DevState extractState() throws DevFailed {
		return deviceattributeDAO.extractState();
	}

	//===========================================
	/**
	 * extract method for a String.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public String extractString() throws DevFailed {
		return deviceattributeDAO.extractString();
	}

	//===========================================
	/**
	 * extract method for a double Array.
	 * 
	 * @return the extracted value.
	 * @throws DevFailed
	 *             in case of read_attribute failed or if AttrQuality is
	 *             ATTR_INVALID.
	 */
	//===========================================
	public String[] extractStringArray() throws DevFailed {
		return deviceattributeDAO.extractStringArray();
	}

	//===========================================
	/**
	 * Return attribute quality
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public AttrQuality getQuality() throws DevFailed {
		return deviceattributeDAO.getQuality();
	}

	//===========================================
	/**
	 *	extract method for a DevEncoded
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public DevEncoded extractDevEncoded() throws DevFailed
	{
		return deviceattributeDAO.extractDevEncoded();
	}
	//===========================================
	/**
	 *	extract method for a DevEncoded[]
	 *
	 *	@return	the extracted value.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public DevEncoded[] extractDevEncodedArray() throws DevFailed
	{
		return deviceattributeDAO.extractDevEncodedArray();
	}
	//===========================================
	/**
	 *	Return attribute data format (SCALR,
.	 *		SPECTRUM, IMAGE or FMT_UNKNOWN)
	 *	If device is older than Device_4Impl, FMT_UNKNOWN is returned.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	//===========================================
	public AttrDataFormat getDataFormat() throws DevFailed
	{
		return deviceattributeDAO.getDataFormat();
	}
	//===========================================
	/**
	 * Return attribute time value.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public TimeVal getTimeVal() throws DevFailed {
		return deviceattributeDAO.getTimeVal();
	}

	//===========================================
	/**
	 * Return attribute time value in seconds since EPOCH.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public long getTimeValSec() throws DevFailed {
		return deviceattributeDAO.getTimeValSec();
	}

	//===========================================
	/**
	 * Return attribute time value in seconds since EPOCH.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public long getTimeValMillisSec() throws DevFailed {
		return deviceattributeDAO.getTimeValMillisSec();
	}

	//===========================================
	/**
	 * Return attribute name.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public String getName() throws DevFailed {
		return deviceattributeDAO.getName();
	}

	//===========================================
	/**
	 * Return number of data read.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public int getNbRead() throws DevFailed {
		return deviceattributeDAO.getNbRead();
	}
	//===========================================
	/**
	 *	Return number of data read.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	public AttributeDim getReadAttributeDim() throws DevFailed
	{
		return deviceattributeDAO.getReadAttributeDim();
	}

	//===========================================
	/**
	 *	Return number of data write.
	 *	@throws	DevFailed in case of read_attribute failed
	 */
	public AttributeDim getWriteAttributeDim() throws DevFailed
	{
		return deviceattributeDAO.getWriteAttributeDim();
	}


	//===========================================
	/**
	 * Return number of data written.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public int getNbWritten() throws DevFailed {
		return deviceattributeDAO.getNbWritten();
	}

	//===========================================
	/**
	 * Return attribute dim_x.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public int getDimX() throws DevFailed {
		return deviceattributeDAO.getDimX();
	}

	//===========================================
	/**
	 * Return attribute dim_y.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public int getDimY() throws DevFailed {
		return deviceattributeDAO.getDimY();
	}

	//===========================================
	/**
	 * Return attribute written dim_x.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public int getWrittenDimX() throws DevFailed {
		return deviceattributeDAO.getWrittenDimX();
	}

	//===========================================
	/**
	 * Return attribute written dim_y.
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public int getWrittenDimY() throws DevFailed {
		return deviceattributeDAO.getWrittenDimY();
	}

	//===========================================
	/**
	 * Return AttributeValue IDL object.
	 */
	//===========================================
	public AttributeValue getAttributeValueObject_2() {
		return deviceattributeDAO.getAttributeValueObject_2();
	}

	//===========================================
	/**
	 * Return AttributeValue IDL object.
	 */
	//===========================================
	public AttributeValue_3 getAttributeValueObject_3() {
		return deviceattributeDAO.getAttributeValueObject_3();
	}

	//===========================================
	/**
	 * Return AttributeValue IDL object.
	 */
	//===========================================
	public AttributeValue_4 getAttributeValueObject_4() {
		return deviceattributeDAO.getAttributeValueObject_4();
	}

	//===========================================
	/**
	 * return time in milliseconds since 1/1/70
	 * 
	 * @throws DevFailed
	 *             in case of read_attribute failed
	 */
	//===========================================
	public long getTime() throws DevFailed {
		return deviceattributeDAO.getTime();
	}

	//===========================================
	//===========================================
	public int getType() throws DevFailed {
		return deviceattributeDAO.getType();
	}
	//===========================================
	//===========================================
	public IDeviceAttributeDAO getDeviceattributeDAO() {
		return deviceattributeDAO;
	}
}

