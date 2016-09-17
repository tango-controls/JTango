package fr.esrf.Tango;

/**
 * Generated from IDL union "AttrValUnion".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public abstract class AttrValUnionHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(AttrValUnionHelper.class)
			{
				if (_type == null)
				{
			org.omg.CORBA.UnionMember[] members = new org.omg.CORBA.UnionMember[15];
			org.omg.CORBA.Any label_any;
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_BOOL);
			members[0] = new org.omg.CORBA.UnionMember ("bool_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarBooleanArrayHelper.id(), "DevVarBooleanArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_SHORT);
			members[1] = new org.omg.CORBA.UnionMember ("short_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarShortArrayHelper.id(), "DevVarShortArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(2)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_LONG);
			members[2] = new org.omg.CORBA.UnionMember ("long_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarLongArrayHelper.id(), "DevVarLongArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_LONG64);
			members[3] = new org.omg.CORBA.UnionMember ("long64_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarLong64ArrayHelper.id(), "DevVarLong64Array",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(23)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_FLOAT);
			members[4] = new org.omg.CORBA.UnionMember ("float_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarFloatArrayHelper.id(), "DevVarFloatArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(6)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_DOUBLE);
			members[5] = new org.omg.CORBA.UnionMember ("double_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarDoubleArrayHelper.id(), "DevVarDoubleArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(7)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_UCHAR);
			members[6] = new org.omg.CORBA.UnionMember ("uchar_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarCharArrayHelper.id(), "DevVarCharArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(10)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_USHORT);
			members[7] = new org.omg.CORBA.UnionMember ("ushort_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarUShortArrayHelper.id(), "DevVarUShortArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(4)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_ULONG);
			members[8] = new org.omg.CORBA.UnionMember ("ulong_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarULongArrayHelper.id(), "DevVarULongArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(5)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_ULONG64);
			members[9] = new org.omg.CORBA.UnionMember ("ulong64_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarULong64ArrayHelper.id(), "DevVarULong64Array",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(24)))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_STRING);
			members[10] = new org.omg.CORBA.UnionMember ("string_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStringArrayHelper.id(), "DevVarStringArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_STATE);
			members[11] = new org.omg.CORBA.UnionMember ("state_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarStateArrayHelper.id(), "DevVarStateArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.DevStateHelper.id(),"DevState",new String[]{"ON","OFF","CLOSE","OPEN","INSERT","EXTRACT","MOVING","STANDBY","FAULT","INIT","RUNNING","ALARM","DISABLE","UNKNOWN"}))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.DEVICE_STATE);
			members[12] = new org.omg.CORBA.UnionMember ("dev_state_att", label_any, fr.esrf.Tango.DevStateHelper.type(),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_ENCODED);
			members[13] = new org.omg.CORBA.UnionMember ("encoded_att_value", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarEncodedArrayHelper.id(), "DevVarEncodedArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_struct_tc(fr.esrf.Tango.DevEncodedHelper.id(),"DevEncoded",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("encoded_format", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevStringHelper.id(), "DevString",org.omg.CORBA.ORB.init().create_string_tc(0)), null),new org.omg.CORBA.StructMember("encoded_data", org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevVarCharArrayHelper.id(), "DevVarCharArray",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(10)))), null)}))),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.AttributeDataTypeHelper.insert(label_any, fr.esrf.Tango.AttributeDataType.ATT_NO_DATA);
			members[14] = new org.omg.CORBA.UnionMember ("union_no_data", label_any, org.omg.CORBA.ORB.init().create_alias_tc(fr.esrf.Tango.DevBooleanHelper.id(), "DevBoolean",org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(8))),null);
			 _type = org.omg.CORBA.ORB.init().create_union_tc(id(),"AttrValUnion",org.omg.CORBA.ORB.init().create_enum_tc(fr.esrf.Tango.AttributeDataTypeHelper.id(),"AttributeDataType",new String[]{"ATT_BOOL","ATT_SHORT","ATT_LONG","ATT_LONG64","ATT_FLOAT","ATT_DOUBLE","ATT_UCHAR","ATT_USHORT","ATT_ULONG","ATT_ULONG64","ATT_STRING","ATT_STATE","DEVICE_STATE","ATT_ENCODED","ATT_NO_DATA"}), members);
				}
			}
		}
			return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.AttrValUnion s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.AttrValUnion extract (final org.omg.CORBA.Any any)
	{
		org.omg.CORBA.portable.InputStream in = any.create_input_stream();
		try
		{
			return read (in);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (java.io.IOException e)
			{
			throw new RuntimeException("Unexpected exception " + e.toString() );
			}
		}
	}

	public static String id()
	{
		return "IDL:Tango/AttrValUnion:1.0";
	}
	public static AttrValUnion read (org.omg.CORBA.portable.InputStream in)
	{
		AttrValUnion result = new AttrValUnion();
		fr.esrf.Tango.AttributeDataType disc;
		disc = fr.esrf.Tango.AttributeDataType.from_int(in.read_long());
		switch (disc.value ())
		{
			case fr.esrf.Tango.AttributeDataType._ATT_BOOL:
			{
				boolean[] _var;
				_var = fr.esrf.Tango.DevVarBooleanArrayHelper.read(in);
				result.bool_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_SHORT:
			{
				short[] _var;
				_var = fr.esrf.Tango.DevVarShortArrayHelper.read(in);
				result.short_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_LONG:
			{
				int[] _var;
				_var = fr.esrf.Tango.DevVarLongArrayHelper.read(in);
				result.long_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_LONG64:
			{
				long[] _var;
				_var = fr.esrf.Tango.DevVarLong64ArrayHelper.read(in);
				result.long64_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_FLOAT:
			{
				float[] _var;
				_var = fr.esrf.Tango.DevVarFloatArrayHelper.read(in);
				result.float_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_DOUBLE:
			{
				double[] _var;
				_var = fr.esrf.Tango.DevVarDoubleArrayHelper.read(in);
				result.double_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_UCHAR:
			{
				byte[] _var;
				_var = fr.esrf.Tango.DevVarCharArrayHelper.read(in);
				result.uchar_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_USHORT:
			{
				short[] _var;
				_var = fr.esrf.Tango.DevVarUShortArrayHelper.read(in);
				result.ushort_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_ULONG:
			{
				int[] _var;
				_var = fr.esrf.Tango.DevVarULongArrayHelper.read(in);
				result.ulong_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_ULONG64:
			{
				long[] _var;
				_var = fr.esrf.Tango.DevVarULong64ArrayHelper.read(in);
				result.ulong64_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_STRING:
			{
				java.lang.String[] _var;
				_var = fr.esrf.Tango.DevVarStringArrayHelper.read(in);
				result.string_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_STATE:
			{
				fr.esrf.Tango.DevState[] _var;
				_var = fr.esrf.Tango.DevVarStateArrayHelper.read(in);
				result.state_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._DEVICE_STATE:
			{
				fr.esrf.Tango.DevState _var;
				_var=fr.esrf.Tango.DevStateHelper.read(in);
				result.dev_state_att (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_ENCODED:
			{
				fr.esrf.Tango.DevEncoded[] _var;
				_var = fr.esrf.Tango.DevVarEncodedArrayHelper.read(in);
				result.encoded_att_value (_var);
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_NO_DATA:
			{
				boolean _var;
				_var=in.read_boolean();
				result.union_no_data (_var);
				break;
			}
		}
		return result;
	}
	public static void write (org.omg.CORBA.portable.OutputStream out, AttrValUnion s)
	{
		out.write_long (s.discriminator().value ());
		switch (s.discriminator().value ())
		{
			case fr.esrf.Tango.AttributeDataType._ATT_BOOL:
			{
				fr.esrf.Tango.DevVarBooleanArrayHelper.write(out,s.bool_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_SHORT:
			{
				fr.esrf.Tango.DevVarShortArrayHelper.write(out,s.short_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_LONG:
			{
				fr.esrf.Tango.DevVarLongArrayHelper.write(out,s.long_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_LONG64:
			{
				fr.esrf.Tango.DevVarLong64ArrayHelper.write(out,s.long64_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_FLOAT:
			{
				fr.esrf.Tango.DevVarFloatArrayHelper.write(out,s.float_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_DOUBLE:
			{
				fr.esrf.Tango.DevVarDoubleArrayHelper.write(out,s.double_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_UCHAR:
			{
				fr.esrf.Tango.DevVarCharArrayHelper.write(out,s.uchar_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_USHORT:
			{
				fr.esrf.Tango.DevVarUShortArrayHelper.write(out,s.ushort_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_ULONG:
			{
				fr.esrf.Tango.DevVarULongArrayHelper.write(out,s.ulong_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_ULONG64:
			{
				fr.esrf.Tango.DevVarULong64ArrayHelper.write(out,s.ulong64_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_STRING:
			{
				fr.esrf.Tango.DevVarStringArrayHelper.write(out,s.string_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_STATE:
			{
				fr.esrf.Tango.DevVarStateArrayHelper.write(out,s.state_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._DEVICE_STATE:
			{
				fr.esrf.Tango.DevStateHelper.write(out,s.dev_state_att ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_ENCODED:
			{
				fr.esrf.Tango.DevVarEncodedArrayHelper.write(out,s.encoded_att_value ());
				break;
			}
			case fr.esrf.Tango.AttributeDataType._ATT_NO_DATA:
			{
				out.write_boolean(s.union_no_data ());
				break;
			}
		}
	}
}
