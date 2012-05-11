/**
 * Copyright (C) :     2004
 *
 *     European Synchrotron Radiation Facility
 *     BP 220, Grenoble 38043
 *     FRANCE
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.esrf.Tango;

/**
 *	Generated from IDL definition of union "ClntIdent"
 *	@author JacORB IDL compiler 
 */

public final class ClntIdentHelper
{
	private static org.omg.CORBA.TypeCode _type;
	public static void insert (final org.omg.CORBA.Any any, final fr.esrf.Tango.ClntIdent s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static fr.esrf.Tango.ClntIdent extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:Tango/ClntIdent:1.0";
	}
	public static ClntIdent read (org.omg.CORBA.portable.InputStream in)
	{
		ClntIdent result = new ClntIdent ();
		fr.esrf.Tango.LockerLanguage disc = fr.esrf.Tango.LockerLanguage.from_int(in.read_long());
		switch (disc.value ())
		{
			case fr.esrf.Tango.LockerLanguage._CPP:
			{
				int _var;
				_var=in.read_ulong();
				result.cpp_clnt (_var);
				break;
			}
			case fr.esrf.Tango.LockerLanguage._JAVA:
			{
				fr.esrf.Tango.JavaClntIdent _var;
				_var=fr.esrf.Tango.JavaClntIdentHelper.read(in);
				result.java_clnt (_var);
				break;
			}
		}
		return result;
	}
	public static void write (org.omg.CORBA.portable.OutputStream out, ClntIdent s)
	{
		out.write_long (s.discriminator().value ());
		switch (s.discriminator().value ())
		{
			case fr.esrf.Tango.LockerLanguage._CPP:
			{
				out.write_ulong(s.cpp_clnt ());
				break;
			}
			case fr.esrf.Tango.LockerLanguage._JAVA:
			{
				fr.esrf.Tango.JavaClntIdentHelper.write(out,s.java_clnt ());
				break;
			}
		}
	}
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			org.omg.CORBA.UnionMember[] members = new org.omg.CORBA.UnionMember[2];
			org.omg.CORBA.Any label_any;
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.LockerLanguageHelper.insert(label_any, fr.esrf.Tango.LockerLanguage.CPP);
			members[1] = new org.omg.CORBA.UnionMember ("cpp_clnt", label_any, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(5)),null);
			label_any = org.omg.CORBA.ORB.init().create_any ();
			fr.esrf.Tango.LockerLanguageHelper.insert(label_any, fr.esrf.Tango.LockerLanguage.JAVA);
			members[0] = new org.omg.CORBA.UnionMember ("java_clnt", label_any, fr.esrf.Tango.JavaClntIdentHelper.type(),null);
			 _type = org.omg.CORBA.ORB.init().create_union_tc(id(),"ClntIdent",fr.esrf.Tango.LockerLanguageHelper.type(), members);
		}
		return _type;
	}
}
