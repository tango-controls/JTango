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
// Revision 1.6  2009/01/16 12:42:58  pascal_verdier
// IntelliJIdea warnings removed.
// Bug in State attribute and Quality extraction fixed.
//
// Revision 1.5  2008/10/10 11:28:31  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.4  2008/07/30 11:26:03  pascal_verdier
// insert/extract UChar added
//
// Revision 1.3  2008/01/10 15:39:42  ounsy
// Resolving a multi connection problem when accessing to a device
//
// Revision 1.2  2007/12/13 10:58:01  pascal_verdier
// TCKind._tk_octet is now recognized as a Tango_DEVVAR_CHARARRAY
//
// Revision 1.1  2007/08/23 09:41:21  ounsy
// Add default impl for tangorb
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
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;


/**
 *	Class Description:
 *	This class manage data object for Tango device access.
 *
 *	<Br><Br>
 *	<Br><b> Usage example: </b> <Br>
 *	<ul><i>
 *		String	status; <Br>
 *		DeviceProxy	dev = DeviceProxyFactory.get("sys/steppermotor/1"); <Br>
 *		try { <Br>	<ul>
 *			DeviceData	data = dev.command_inout("DevStatus"); <Br>
 *			status = data.extractString(); <Br>	</ul>
 *		} <Br>
 *		catch (DevFailed e) { <Br>	<ul>
 *			status = "Unknown status"; <Br>
 *			Except.print_exception(e); <Br>	</ul>
 *		} <Br>
 *	</ul></i>
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DeviceDataDAODefaultImpl implements TangoConst, IDeviceDataDAO
{

	public DeviceDataDAODefaultImpl()
	{
	}	
	
	//===========================================================
	/**
	 *	Constructor for the TgApi Data Object.
	 *
	 *	@throws DevFailed if TgApi class not instancied.
	 */
	//===========================================================
	public void init(DeviceData deviceData) throws DevFailed
	{
		deviceData.setAny(ApiUtil.get_orb().create_any());
	}
	
	//===========================================================
	/**
	 *	Constructor for the TgApi Data Object.
	 *
	 *	@param orb	orb connection id.
	 *	@throws DevFailed if TgApi class not instancied.
	 */
	//===========================================================
	public void init(DeviceData deviceData, ORB orb) throws DevFailed
	{
		deviceData.setAny(orb.create_any());
	}
	//===========================================================
	/**
	 *	Constructor for the TgApi Data Object.
	 *
	 *	@param any	CORBA Any reference to be used in DeviceData.
	 *	@throws DevFailed if TgApi class not instancied.
	 */
	//===========================================================
	public void init(DeviceData deviceData, Any any) throws DevFailed
	{
		deviceData.setAny(any);
	}


	//**********	Insert Methods for basic types *********************
	
	
	//===========================================
	/**
	 *	Insert method for argin is void.
	 */
	//===========================================
	public void insert(DeviceData deviceData)
	{
	}
	//===========================================
	/**
	 *	Insert method for argin is Any (CORBA).
	 */
	//===========================================
	public void insert(DeviceData deviceData, Any any)
	{
		deviceData.setAny(any);
	}
	//===========================================
	/**
	 *	Insert method for argin is boolean.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, boolean argin)
	{
		DevBooleanHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is short.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, short argin)
	{
		DevShortHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is long (64 bits)
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, long argin)
	{
		DevLong64Helper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is int.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, int argin)
	{
		DevLongHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is float.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, float argin)
	{
		DevFloatHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is double.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, double argin)
	{
		DevDoubleHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is String.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, String argin)
	{
		DevStringHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is DevState.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, DevState argin)
	{
		DevStateHelper.insert(deviceData.getAny(), argin);
	}

	//**********	Insert Methods for sequence types	*********************

	//===========================================
	/**
	 *	Insert method for argin is DevVarCharArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, byte[] argin)
	{
		DevVarCharArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarShortArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, short[] argin)
	{
		DevVarShortArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarLongArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, int[] argin)
	{
		DevVarLongArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is long array (64 bits).
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, long[] argin)
	{
		DevVarLong64ArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarFloatArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, float[] argin)
	{
		DevVarFloatArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarDoubleArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, double[] argin)
	{
		DevVarDoubleArrayHelper.insert(deviceData.getAny(), argin);
	}

	//===========================================
	/**
	 *	Insert method for argin is DevVarStringArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, String[] argin)
	{
		DevVarStringArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarLongStringArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, DevVarLongStringArray argin)
	{
		DevVarLongStringArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarDoubleStringArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(DeviceData deviceData, DevVarDoubleStringArray argin)
	{
		DevVarDoubleStringArrayHelper.insert(deviceData.getAny(), argin);
	}



	//**********	Insert Methods for unsigned types	*********************

	//===========================================
	/**
	 *	Insert method for argin is unsigned long 64.array
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_u64(DeviceData deviceData, long[] argin)
	{
		DevVarULong64ArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned long 64.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_u64(DeviceData deviceData, long argin)
	{
		DevULong64Helper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin int as unsigned char.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_uc(DeviceData deviceData, short argin)
	{
		byte	val = (byte)(argin & 0xFF);
		DevUCharHelper.insert(deviceData.getAny(), val);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned char.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_uc(DeviceData deviceData, byte argin)
	{
		DevUCharHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin int as unsigned short.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_us(DeviceData deviceData, int argin)
	{
		short	val = (short)(argin & 0xFFFF);
		DevUShortHelper.insert(deviceData.getAny(), val);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned short.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_us(DeviceData deviceData, short argin)
	{
		DevUShortHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned short.
	 *
	 *	@param argin	argin value for next command.
	 *	@deprecated use insert_us(short/int argin)
	 */
	//===========================================
	public void insert_u(DeviceData deviceData, short argin)
	{
		DevUShortHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin long ass unsigned int.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_ul(DeviceData deviceData, long argin)
	{
		int		val = (int)(argin & 0xFFFFFFFF);
		DevULongHelper.insert(deviceData.getAny(), val);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned int.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_ul(DeviceData deviceData, int argin)
	{
		DevULongHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned int.
	 *
	 *	@param argin	argin value for next command.
	 *	@deprecated use insert_ul(int/long argin)
	 */
	//===========================================
	public void insert_u(DeviceData deviceData, int argin)
	{
		DevULongHelper.insert(deviceData.getAny(), argin);
	}

	//===========================================
	/**
	 *	Insert method for argin int as unsigned short array.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_us(DeviceData deviceData, int[] argin)
	{
		short[]	val = new short[argin.length];
		for (int i=0 ; i<argin.length ; i++)
			val[i] = (short)(argin[i] & 0xFFFF);

		DevVarUShortArrayHelper.insert(deviceData.getAny(), val);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned short array.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_us(DeviceData deviceData, short[] argin)
	{
		DevVarUShortArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned short array.
	 *
	 *	@param argin	argin value for next command.
	 *	@deprecated use insert_us(short[]/int[] argin)
	 */
	//===========================================
	public void insert_u(DeviceData deviceData, short[] argin)
	{
		DevVarUShortArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin long array as unsigned int array.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_ul(DeviceData deviceData, long[] argin)
	{
		int[]	val = new int[argin.length];
		for (int i=0 ; i<argin.length ; i++)
			val[i] = (int)(argin[i] & 0xFFFFFFFF);

		DevVarULongArrayHelper.insert(deviceData.getAny(), val);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned int array.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert_ul(DeviceData deviceData, int[] argin)
	{
		DevVarULongArrayHelper.insert(deviceData.getAny(), argin);
	}
	//===========================================
	/**
	 *	Insert method for argin is unsigned int array.
	 *
	 *	@param argin	argin value for next command.
	 *	@deprecated use insert_ul(int[]/long[] argin)
	 */
	//===========================================
	public void insert_u(DeviceData deviceData, int[] argin)
	{
		DevVarULongArrayHelper.insert(deviceData.getAny(), argin);
	}



	//**********	Extract Methods for basic types	*********************


	//===========================================
	/**
	 *	extract method for a CORBA Any.
	 */
	//===========================================
	public Any extractAny(DeviceData deviceData)
	{
		return deviceData.getAny();
	}
	//===========================================
	/**
	 *	extract method for a boolean.
	 */
	//===========================================
	public boolean extractBoolean(DeviceData deviceData)
	{
		return DevBooleanHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a short.
	 */
	//===========================================
	public short extractShort(DeviceData deviceData)
	{
		return DevShortHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for an unsigned char.
	 */
	//===========================================
	public short extractUChar(DeviceData deviceData)
	{
		byte	tmp = DevUCharHelper.extract(deviceData.getAny());
		short	val = (short)(0xFF & tmp);
		return val;
	}
	//===========================================
	/**
	 *	extract method for an unsigned short.
	 */
	//===========================================
	public int extractUShort(DeviceData deviceData)
	{
		short	tmp = DevUShortHelper.extract(deviceData.getAny());
		int		val = 0xFFFF & tmp;
		return val;
	}
	//===========================================
	/**
	 *	extract method for a long.
	 */
	//===========================================
	public int extractLong(DeviceData deviceData)
	{
		return DevLongHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a long.
	 */
	//===========================================
	public long extractLong64(DeviceData deviceData)
	{
		return DevLong64Helper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for an unsigned long.
	 */
	//===========================================
	public long extractULong(DeviceData deviceData)
	{
		int		tmp = DevULongHelper.extract(deviceData.getAny());
		long	mask = (long) 0x7fffffff;
		mask += ((long)1 << 31);
		long	val = (long) tmp & mask;
		return val;
	}
	//===========================================
	/**
	 *	extract method for an unsigned long.
	 */
	//===========================================
	public long extractULong64(DeviceData deviceData)
	{
		return DevULong64Helper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a float.
	 */
	//===========================================
	public float extractFloat(DeviceData deviceData)
	{
		return DevFloatHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a double.
	 */
	//===========================================
	public double extractDouble(DeviceData deviceData)
	{
		return DevDoubleHelper.extract(deviceData.getAny());
	}

	//===========================================
	/**
	 *	extract method for a String.
	 */
	//===========================================
	public String extractString(DeviceData deviceData)
	{
		return DevStringHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a DevState.
	 */
	//===========================================
	public DevState extractDevState(DeviceData deviceData)
	{
		return DevStateHelper.extract(deviceData.getAny());
	}



	//**********	Extract Methods for sequence types	*********************


	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	//===========================================
	public byte[] extractByteArray(DeviceData deviceData)
	{
		return DevVarCharArrayHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a short Array.
	 */
	//===========================================
	public short[] extractShortArray(DeviceData deviceData)
	{
		return DevVarShortArrayHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for an unsigned short Array.
	 *	@return extract value as int array
	 */
	//===========================================
	public int[] extractUShortArray(DeviceData deviceData)
	{
		short[]	argout = DevVarUShortArrayHelper.extract(deviceData.getAny());
		int[]	val = new int[argout.length];
		for (int i=0 ; i<argout.length ; i++)
			val[i] = 0xFFFF & argout[i];
		return val;
	}
	//===========================================
	/**
	 *	extract method for a long Array.
	 */
	//===========================================
	public int[] extractLongArray(DeviceData deviceData)
	{
		return DevVarLongArrayHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a long64 Array.
	 */
	//===========================================
	public long[] extractLong64Array(DeviceData deviceData)
	{
		return DevVarLong64ArrayHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for an unsigned long Array.
	 *	@return extract value as long array
	 */
	//===========================================
	public long[] extractULongArray(DeviceData deviceData)
	{
		int[]	argout = DevVarULongArrayHelper.extract(deviceData.getAny());
		long[]	val = new long[argout.length];
		long	mask = (long) 0x7fffffff;
		mask += ((long)1 << 31);

		for (int i=0 ; i<argout.length ; i++)
			val[i]  = (long) argout[i] & mask;

		return val;
	}
	//===========================================
	/**
	 *	extract method for a long64 Array.
	 */
	//===========================================
	public long[] extractULong64Array(DeviceData deviceData)
	{
		return DevVarULong64ArrayHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a float Array.
	 */
	//===========================================
	public float[] extractFloatArray(DeviceData deviceData)
	{
		return DevVarFloatArrayHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a double Array.
	 */
	//===========================================
	public double[] extractDoubleArray(DeviceData deviceData)
	{
		return DevVarDoubleArrayHelper.extract(deviceData.getAny());
	}

	//===========================================
	/**
	 *	extract method for a String Array.
	 */
	//===========================================
	public String[] extractStringArray(DeviceData deviceData)
	{
		if (deviceData.getAny()==null)	System.out.println("any = null !!");
		return DevVarStringArrayHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a DevVarLongStringArray.
	 */
	//===========================================
	public DevVarLongStringArray extractLongStringArray(DeviceData deviceData)
	{
		if (deviceData.getAny()==null)	System.out.println("any = null !!");
		return DevVarLongStringArrayHelper.extract(deviceData.getAny());
	}
	//===========================================
	/**
	 *	extract method for a DevVarDoubleStringArray.
	 */
	//===========================================
	public DevVarDoubleStringArray extractDoubleStringArray(DeviceData deviceData)
	{
		return DevVarDoubleStringArrayHelper.extract(deviceData.getAny());
	}


	public TypeCode type(DeviceData deviceData)
	{
		return deviceData.getAny().type();
	}
	//===========================================
	//===========================================
	public int getType(DeviceData deviceData) throws DevFailed
	{
		int	type = Tango_DEV_VOID;
		boolean		is_array  = false;
		boolean		is_struct = false;
		TypeCode	tc = deviceData.getAny().type();
		TCKind		kind;
		try {

			//	Special case for state
			if (tc.kind().value()==TCKind._tk_enum)
				return Tango_DEV_STATE;

			//	Check if struct
			if (tc.kind().value()==TCKind._tk_struct)
			{
				is_struct = true;
				//	Get first element of struct
				tc = tc.member_type(0);
			}

			//	Check if array
			if (tc.kind().value()==TCKind._tk_alias)
			{
				TypeCode	tc_alias = tc.content_type();
				if (tc_alias.kind().value()==TCKind._tk_sequence)
				{
					TypeCode	tc_seq   = tc_alias.content_type();
					kind = tc_seq.kind();
					is_array = true;
				}
				else
					kind = tc_alias.kind();
			}
			else
				kind = tc.kind();

			//	Set Tango type from TypeCode
			switch(kind.value())
			{
			case TCKind._tk_void:
				type = Tango_DEV_VOID;
				break;

			case TCKind._tk_boolean:
				type = Tango_DEV_BOOLEAN;
				break;

			case TCKind._tk_char:
			case TCKind._tk_octet:				
				if (is_array)
					type = Tango_DEVVAR_CHARARRAY;
				else
					type = Tango_DEV_CHAR;
				break;

			case TCKind._tk_short:
				if (is_array)
					type = Tango_DEVVAR_SHORTARRAY;
				else
					type = Tango_DEV_SHORT;
				break;

			case TCKind._tk_ushort:
				if (is_array)
					type = Tango_DEVVAR_USHORTARRAY;
				else
					type = Tango_DEV_USHORT;
				break;

			case TCKind._tk_long:
				if (is_struct)
					type = Tango_DEVVAR_LONGSTRINGARRAY;
				else
				if (is_array)
					type = Tango_DEVVAR_LONGARRAY;
				else
					type = Tango_DEV_LONG;
				break;

			case TCKind._tk_ulong:
				if (is_array)
					type = Tango_DEVVAR_ULONGARRAY;
				else
					type = Tango_DEV_ULONG;
				break;

			case TCKind._tk_longlong:
				if (is_array)
					type = Tango_DEVVAR_LONG64ARRAY;
				else
					type = Tango_DEV_LONG64;
				break;

			case TCKind._tk_ulonglong:
				if (is_array)
					type = Tango_DEVVAR_ULONG64ARRAY;
				else
					type = Tango_DEV_ULONG64;
				break;

			case TCKind._tk_float:
				if (is_array)
					type = Tango_DEVVAR_FLOATARRAY;
				else
					type = Tango_DEV_FLOAT;
				break;

			case TCKind._tk_double:
				if (is_struct)
					type = Tango_DEVVAR_DOUBLESTRINGARRAY;
				else
				if (is_array)
					type = Tango_DEVVAR_DOUBLEARRAY;
				else
					type = Tango_DEV_DOUBLE;
				break;

			case TCKind._tk_string:
				if (is_array)
					type = Tango_DEVVAR_STRINGARRAY;
				else
					type = Tango_DEV_STRING;
				break;
			}
		}
		catch(org.omg.CORBA.TypeCodePackage.BadKind e)
		{
			e.printStackTrace();
			Except.throw_exception("Api_TypeCodePackage.BadKind",
						"Bad or unknown type ",
						"DeviceAttribute.getType()");
		}
		catch(org.omg.CORBA.TypeCodePackage.Bounds e)
		{
			Except.throw_exception("Api_TypeCodePackage.BadKind",
						"Bad or unknown type ",
						"DeviceAttribute.getType()");
		}
		return type;
	}
}

