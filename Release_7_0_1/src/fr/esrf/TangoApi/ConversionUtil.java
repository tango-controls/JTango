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
// Revision 1.4  2009/01/16 12:49:34  pascal_verdier
// IntelliJIdea warnings romoved.
// DeviceProxyFactory usage added.
//
// Revision 1.3  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
//
//-======================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.*;
import fr.esrf.TangoDs.Except;
import fr.esrf.TangoDs.TangoConst;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;

/**
 *	Class Description:
 *	This class implements utility methods to convert objects
 *
 * @author  verdier
 * @version  $Revision$
 */


class ConversionUtil
{

	//==========================================================================
	/**
	 *	Convert a DevCmdHistory_4 object (since Device_4Impl) to 
	 *		a DeviceDataHistory array.
	 *
	 *	@param cmdname	Command name.
	 *	@param histo4	Command history object read from a Device_4Impl.
	 *	@return DeviceDataHistory array.
	 */
	//==========================================================================
	static DeviceDataHistory[] 	histo4ToDeviceDataHistoryArray(String cmdname, DevCmdHistory_4 histo4)
			throws DevFailed
	{
		// Check received data validity
		if (histo4.dims.length   != histo4.dims_array.length ||
			histo4.errors.length != histo4.errors_array.length)
		{
			Except.throw_exception("API_WrongHistoryDataBuffer",
					"Data buffer received from server is not valid !",
					"DeviceProxy::from_hist4_2_AttHistory");
		}

		// Get history depth
		int	nb_records = histo4.dates.length;
		//System.out.println(nb_records  + " values");
	
		// Copy date and name in each History list element
		DeviceDataHistory[]	ddh = new DeviceDataHistory[nb_records];

		long	t0 = System.currentTimeMillis();
		for (int i=0; i<nb_records ; i++)
			ddh[i] = new DeviceDataHistory(cmdname, TangoConst.COMMAND, histo4.dates[i]);

		// Copy read dimension
		for (int i=0 ; i<histo4.dims.length ; i++)
		{
			int nb_elt = histo4.dims_array[i].nb_elt;
			int start  = histo4.dims_array[i].start;
			for (int k=0 ; k<nb_elt ; k++)
			{
				ddh[start - k].setDimX(histo4.dims[i].dim_x);
				ddh[start - k].setDimY(histo4.dims[i].dim_y);
			}
		}

		long	t1 = System.currentTimeMillis();
		System.out.println("	Creation --> " + (t1-t0) + " ms");
		System.out.println("	Cmd Type is " + TangoConst.Tango_CmdArgTypeName[histo4.cmd_type]);
		// Copy errors
		for (int i=0 ; i<histo4.errors.length ; i++)
		{
			int nb_elt = histo4.errors_array[i].nb_elt;
			int start  = histo4.errors_array[i].start;
			for (int k=0 ; k<nb_elt ; k++)
				ddh[start - k].setErrStack(histo4.errors[i]);
		}

		// Get data type and data ptr
		t0 = System.currentTimeMillis();
		insertValues(ddh, histo4.value, nb_records, histo4.cmd_type);
		t1 = System.currentTimeMillis();
		System.out.println("	Insertion --> " + (t1-t0) + " ms");
		return ddh;
	}
	//==========================================================================
	/**
	 *	Convert a DevAttrHistory_4 object (since Device_4Impl) to 
	 *		a DeviceDataHistory array.
	 *
	 *	@param histo4	Attribute history object read from a Device_4Impl.
	 *	@return DeviceDataHistory array.
	 */
	//==========================================================================
	static DeviceDataHistory[] 	histo4ToDeviceDataHistoryArray(DevAttrHistory_4 histo4)
			throws DevFailed
	{
		// Check received data validity
		if (histo4.quals.length  != histo4.quals_array.length  ||
			histo4.r_dims.length != histo4.r_dims_array.length ||
			histo4.w_dims.length != histo4.w_dims_array.length ||
			histo4.errors.length != histo4.errors_array.length)
		{
			Except.throw_exception("API_WrongHistoryDataBuffer",
					"Data buffer received from server is not valid !",
					"DeviceProxy::from_hist4_2_AttHistory");
		}

		// Get history depth
		int	nb_records = histo4.dates.length;
		//System.out.println(nb_records  + " values");

		// Copy date and name in each History list element
		String	name = histo4.name;
		DeviceDataHistory[]	ddh = new DeviceDataHistory[nb_records];

		long	t0 = System.currentTimeMillis();
		for (int i=0; i<nb_records ; i++)
			ddh[i] = new DeviceDataHistory(name, TangoConst.ATTRIBUTE, histo4.dates[i]);

		long	t1 = System.currentTimeMillis();
		System.out.println("	Creation --> " + (t1-t0) + " ms");

		// Copy the attribute quality factor
		for (int i=0 ; i<histo4.quals.length ; i++)
		{
			int nb_elt = histo4.quals_array[i].nb_elt;
			int start  = histo4.quals_array[i].start;
			for (int k=0 ; k<nb_elt ; k++)
				ddh[start - k].setAttrQuality(histo4.quals[i]);
		}
		
		// Copy read dimension
		for (int i=0 ; i<histo4.r_dims.length ; i++)
		{
			int nb_elt = histo4.r_dims_array[i].nb_elt;
			int start  = histo4.r_dims_array[i].start;
			for (int k=0 ; k<nb_elt ; k++)
			{
				ddh[start - k].setDimX(histo4.r_dims[i].dim_x);
				ddh[start - k].setDimY(histo4.r_dims[i].dim_y);
			}
		}		

		// Copy write dimension
		for (int i=0 ; i<histo4.w_dims.length ; i++)
		{
			int nb_elt = histo4.w_dims_array[i].nb_elt;
			int start  = histo4.w_dims_array[i].start;

			for (int k=0 ; k<nb_elt ; k++)
			{
				ddh[start - k].setWrittenDimX(histo4.w_dims[i].dim_x);
				ddh[start - k].setWrittenDimY(histo4.w_dims[i].dim_y);
			}
		}

		// Copy errors
		for (int i=0 ; i<histo4.errors.length ; i++)
		{
			int nb_elt = histo4.errors_array[i].nb_elt;
			int start  = histo4.errors_array[i].start;
			for (int k=0 ; k<nb_elt ; k++)
				ddh[start - k].setErrStack(histo4.errors[i]);
		}

		// Get data type and data ptr
		t0 = System.currentTimeMillis();
		insertValues(ddh, histo4.value, nb_records, getType(histo4.value));
		t1 = System.currentTimeMillis();
		System.out.println("	Insertion --> " + (t1-t0) + " ms");
		return ddh;
	}
	//==========================================================================
	//==========================================================================
	static private void insertValues(DeviceDataHistory[] ddh, org.omg.CORBA.Any value, int nb_records, int type)
			throws DevFailed
	{
		switch (type)
		{
		case TangoConst.Tango_DEV_BOOLEAN:
			{
				boolean[]	values =
					DevVarBooleanArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_UCHAR:
			{
				byte[]	argout =
					DevVarCharArrayHelper.extract(value);
				short[]	values = new short[argout.length];
				short	mask = 0xFF;
				for (int i=0 ; i<argout.length ; i++)
					values[i] = (short)(mask & argout[i]);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_SHORT:
		case TangoConst.Tango_DEVVAR_SHORTARRAY:
			{
				short[]	values =
					DevVarShortArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_USHORT:
		case TangoConst.Tango_DEVVAR_USHORTARRAY:
			{
				short[]	values =
					DevVarUShortArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_LONG:
		case TangoConst.Tango_DEVVAR_LONGARRAY:
			{
				int[]	values =
					DevVarLongArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_ULONG:
		case TangoConst.Tango_DEVVAR_ULONGARRAY:
			{
				int[]	values =
					DevVarULongArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_LONG64:
		case TangoConst.Tango_DEVVAR_LONG64ARRAY:
			{
				long[]	values =
					DevVarLong64ArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_ULONG64:
		case TangoConst.Tango_DEVVAR_ULONG64ARRAY:
			{
				long[]	values =
					DevVarULong64ArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_FLOAT:
		case TangoConst.Tango_DEVVAR_FLOATARRAY:
			{
				float[]	values =
					DevVarFloatArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_DOUBLE:
		case TangoConst.Tango_DEVVAR_DOUBLEARRAY:
			{
				double[]	values =
					DevVarDoubleArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_STRING:
		case TangoConst.Tango_DEVVAR_STRINGARRAY:
			{
				String[]	values =
					DevVarStringArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEVVAR_LONGSTRINGARRAY:
			{
				DevVarLongStringArray	values = 
					DevVarLongStringArrayHelper.extract(value);

				int	s_base = values.svalue.length;
				int	l_base = values.lvalue.length;
				int[]	bases = { s_base, l_base };
				for (int i=0; i<nb_records ; i++)
					bases = ddh[i].insert(values, bases);
			}
			break;
		case TangoConst.Tango_DEVVAR_DOUBLESTRINGARRAY:
			{
				DevVarDoubleStringArray	values = 
					DevVarDoubleStringArrayHelper.extract(value);

				int	s_base = values.svalue.length;
				int	d_base = values.dvalue.length;
				int[]	bases = { s_base, d_base };
				for (int i=0; i<nb_records ; i++)
					bases = ddh[i].insert(values, bases);
			}
			break;
		case TangoConst.Tango_DEV_STATE:
			{
				DevState[]	values;
				if (isArray(value))
					values = DevVarStateArrayHelper.extract(value);
				else
					values = new DevState[] { DevStateHelper.extract(value)};
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		case TangoConst.Tango_DEV_ENCODED:
			{
				DevEncoded[]	values = 
					DevVarEncodedArrayHelper.extract(value);
				int	base = values.length;
				for (int i=0; i<nb_records ; i++)
					base = ddh[i].insert(values, base);
			}
			break;
		default:
			Except.throw_wrong_data_exception("Api_TypeCodePackage.BadKind",
						"Bad or unknown type (" + type + ")",
						"ConversionUtil.insertValue()");
			break;
		}
	}
	//===========================================
	/**
	 *	return true if any is an array
	 */
	//===========================================
	private static boolean isArray(org.omg.CORBA.Any any)
	{ 
		boolean retval = true; 
		    
		try 
		{ 
			TypeCode        tc = any.type(); 
			TypeCode        tc_alias = tc.content_type(); 
			//TypeCode        tc_seq   = 
					tc_alias.content_type();
		} 
		catch(org.omg.CORBA.TypeCodePackage.BadKind e) 
		{ 
			//System.out.println(e); 
			retval = false; 
		} 
		return retval; 
	} 
	//==========================================================================
	/**
	 *	Returns attribute Tango type.
	 */
	//==========================================================================
	static public int getType(org.omg.CORBA.Any any) throws DevFailed
	{
		int	type = -1;
		try {
			TypeCode	tc = any.type();

			//	Check if sequence is empty
			if (tc.kind().value() == TCKind._tk_null)
				return TangoConst.Tango_DEV_VOID;

			//	Special case for state
			if (tc.kind().value()==TCKind._tk_enum)
				return TangoConst.Tango_DEV_STATE;
			TypeCode	tc_alias = tc.content_type();
			TypeCode	tc_seq   = tc_alias.content_type();
			TCKind		kind = tc_seq.kind();
			switch(kind.value())
			{
			case TCKind._tk_void:
				type = TangoConst.Tango_DEV_VOID;
				break;
			case TCKind._tk_boolean:
				type = TangoConst.Tango_DEV_BOOLEAN;
				break;
			case TCKind._tk_char:
				type = TangoConst.Tango_DEV_CHAR;
				break;
			case TCKind._tk_octet:
				type = TangoConst.Tango_DEV_UCHAR;
				break;
			case TCKind._tk_short:
				type = TangoConst.Tango_DEV_SHORT;
				break;
			case TCKind._tk_ushort:
				type = TangoConst.Tango_DEV_USHORT;
				break;
			case TCKind._tk_long:
				type = TangoConst.Tango_DEV_LONG;
				break;
			case TCKind._tk_ulong:
				type = TangoConst.Tango_DEV_ULONG;
				break;
			case TCKind._tk_longlong:
				type = TangoConst.Tango_DEV_LONG64;
				break;
			case TCKind._tk_ulonglong:
				type = TangoConst.Tango_DEV_ULONG64;
				break;
			case TCKind._tk_float:
				type = TangoConst.Tango_DEV_FLOAT;
				break;
			case TCKind._tk_double:
				type = TangoConst.Tango_DEV_DOUBLE;
				break;
			case TCKind._tk_string:
				type = TangoConst.Tango_DEV_STRING;
				break;
			case TCKind._tk_enum: 
				type = TangoConst.Tango_DEV_STATE; 
				break; 
			case TCKind._tk_struct:
				type = TangoConst.Tango_DEV_ENCODED; 
				break; 
			}
		}
		catch(org.omg.CORBA.TypeCodePackage.BadKind e)
		{
		e.printStackTrace();
			Except.throw_exception("Api_TypeCodePackage.BadKind",
						"Bad or unknown type ",
						"ConversionUtil.getType()");
		}
		return type;
	}
}
