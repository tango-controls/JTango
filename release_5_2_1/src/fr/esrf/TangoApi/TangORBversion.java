//+===========================================================================
// $Source$
//
// Project:   Tango API
//
// Description:  Java source for conversion between Tango/TACO library
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.2  2007/09/13 09:22:32  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.1  2007/08/23 12:30:26  ounsy
// add TangORBVersion
//
// Revision 3.12  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.11  2005/11/30 07:58:38  pascal_verdier
// Bug in toString()  fixed.
//
// Revision 3.10  2005/11/29 05:35:43  pascal_verdier
// api and tango fields was swapped.
//
// Revision 3.9  2004/12/07 09:30:30  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.8  2004/12/06 09:06:57  pascal_verdier
// jarfile field has been set public.
//
// Revision 3.7  2004/11/29 11:45:30  pascal_verdier
// Path separator depends on OS.
//
// Revision 3.6  2004/10/11 12:27:26  pascal_verdier
// Find jar file in CLASSPATH before analysis.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-===========================================================================
//         (c) - Software Engineering Group - ESRF
//============================================================================

package fr.esrf.TangoApi;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoDs.Except;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class TangORBversion implements java.io.Serializable
{
/**
 *	Tango version compatibility.
 */
	public String	Tango  = null;
/**
 *	Java Api version (TangORB version).
 */
	public String	api    = null;
/**
 *	JacORB package version used.
 */
	public String	JacORB = null;
/**
 *	logkit package version used.
 */
	public String	logkit = null;
/**
 *	avalon package version used.
 */
	public String	avalon = null;
/**
 *	log4j package version used.
 */
	public String	log4j  = null;

	public String	jarfile;
	private static final String[]	packages = { 
							"API",
							"Tango",
							"JacORB",
							"logkit",
							"avalon_framework",
							"log4j"
						};

	//========================================================================
	/**
	 *	Constructor analysing classpath to find TangORB jar file.
	 */
	//========================================================================
	public TangORBversion() throws DevFailed, IOException
	{
		//	Get classpath from environment
		String	classpath = System.getProperty("java.class.path");
		String	separator = System.getProperty("path.separator");

		//	Parse for TangORB jar file path
		String	target = "TangORB";
		int		start, end;
		if ((start=classpath.indexOf(target))<0)
			Except.throw_exception("TangORB_NotFound",
							"TangORB jar file not found in CLASSPATH",
							"TangORBversion.TangORBversion()");
		if ((start=classpath.lastIndexOf(separator, start))<0)
			start = 0;
		else
			start++;

		//	Search end
		if ((end=classpath.indexOf(separator, start))<0)
			jarfile = classpath.substring(start);
		else
			jarfile = classpath.substring(start, end);
		//System.out.println(jarfile);

		//	And init Object from jar file
		initObject();
	}
	//========================================================================
	/**
	 *	Constructor initialising object with jar file passed.
	 *	@param filename jar file to initialise object.
	 */
	//========================================================================
	public TangORBversion(String filename) throws DevFailed, IOException
	{
		jarfile = filename;
		initObject();
	}
	//========================================================================
	/**
	 *	Read jar file manifest and fill fields.
	 */
	//========================================================================
	private void initObject() throws DevFailed, IOException
	{
		//	Check if file exists
		if (new File(jarfile).exists() == false)
			Except.throw_exception("FileNotFoundException",
							jarfile + " No such file or directory",
							"TangORBversion.initObject()");

		// Retrieve the manifest file from the jar file
		JarFile		jf = new JarFile(jarfile);
		Manifest	manif = jf.getManifest();

		//	Retrieve the Tango-Version attribute in the Manifest file 
		//	and print the version number (if defined)
		Attributes	attr = manif.getMainAttributes();
		
		//	Get max length
		int	max_length = 0;
		for (int i=0 ; i<packages.length ; i++)
			if (packages[i].length()>max_length)
					max_length = packages[i].length();

		//	Display package versions
		for (int i=0 ; i<packages.length ; i++)
		{
			String target = new String(packages[i] + "-Version");
			String vers = attr.getValue(target);
			/*
			if (vers == null)
				Except.throw_exception("TangORB_ManifestError",
							target + " version not found in manifest.",
							"TangORBversion.initObject()");
			else
			*/
			{
				switch(i)
				{
				case 0:
					api = vers.trim();
					break;
				case 1:
					Tango = vers.trim();
					break;
				case 2:
					JacORB = vers.trim();
					break;
				case 3:
					logkit = vers.trim();
					break;
				case 4:
					avalon = vers.trim();
					break;
				case 5:
					if (vers == null)
						log4j = "version not found in manifest.";
					else
						log4j = vers.trim();
					break;
				}
			}
		}
	}
	//========================================================================
	//========================================================================
	public String toString()
	{
		//	Get max length
		int	max_length = 0;
		for (int i=0 ; i<packages.length ; i++)
			if (packages[i].length()>max_length)
					max_length = packages[i].length();
		String	versStr = " version";
		max_length += versStr.length() + 3;

		//	Display package versions
		String	str = "";
		for (int i=0 ; i<packages.length ; i++)
		{
				str += packages[i] + versStr;
				for (int j=packages[i].length() + versStr.length() ;
						 j<max_length ; j++)
					str += ".";
				switch(i)
				{
				case 0:
					str += api;
					break;
				case 1:
					str += Tango;
					break;
				case 2:
					str += JacORB;
					break;
				case 3:
					str += logkit;
					break;
				case 4:
					str += avalon;
					break;
				case 5:
					str += log4j;
					break;
				}
				if (i<packages.length-1)
					str += "\n";
		}
		return str;
	}
	//========================================================================
	//========================================================================
	public static void main(String[] args)
	{
		try
		{
			TangORBversion	tangORB;
			if (args.length > 0)
				tangORB	= new TangORBversion(args[0]);
			else
				tangORB	= new TangORBversion();
			System.out.println(tangORB);
		}
		catch (DevFailed e)
		{
			Except.print_exception(e);
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.exit(-1);
		}
		System.exit(0);
	}
}
