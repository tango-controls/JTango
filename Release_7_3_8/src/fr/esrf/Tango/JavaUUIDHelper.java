package fr.esrf.Tango;
public final class JavaUUIDHelper
{
	private static org.omg.CORBA.TypeCode _type = org.omg.CORBA.ORB.init().create_array_tc(2,org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(24)));
	public static void insert (final org.omg.CORBA.Any any, final long[] s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static long[] extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static org.omg.CORBA.TypeCode type()
	{
		return _type;
	}
	public static String id()
	{
		return "IDL:Tango/JavaUUID:1.0";
	}
	public static long[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		long[] result = new long[2]; // long[]
		_in.read_ulonglong_array(result,0,2);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final long[] s)
	{
		if (s.length != 2)
			throw new org.omg.CORBA.MARSHAL("Incorrect array size");
		out.write_ulonglong_array(s,0,2);
	}
}
