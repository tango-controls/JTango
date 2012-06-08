//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:	source code 
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

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IORDumpUtil {
	// ===============================================================
	// ===============================================================
	public static void printSyntax() {
		System.out.println("IORdump <ior string>      or");
		System.out.println("IORdump -f <ior file name>");
	}

	// ===============================================================
	// ===============================================================
	public static String getIor(String filename) throws FileNotFoundException, SecurityException, IOException {
		FileInputStream fid = new FileInputStream(filename);
		int nb = fid.available();
		byte[] inStr = new byte[nb];
		int	nbread = fid.read(inStr);
		fid.close();
		String	str = "";
		if (nbread>0)
			str = new String(inStr);
		System.out.println(str);
		return str.trim();
	}

	// ===============================================================
	// ===============================================================
	public static void main(String[] args) {
		try
		{
			switch (args.length)
			{
			case 1:
				System.out.println(new fr.esrf.TangoApi.IORdump(args[0]).toString());
				break;
			case 2:
				if (args[0].equals("-f"))
				{
					System.out.println(new fr.esrf.TangoApi.IORdump(null, getIor(args[1])).toString());
					break;
				}
			default:
				printSyntax();
			}
		} catch (DevFailed e) {
			Except.print_exception(e);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
