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
// Revision 1.4  2007/08/23 08:32:57  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:51:15  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.5  2004/03/12 13:15:21  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:28  pascal_verdier
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
//
// Copyright 2001 by European Synchrotron Radiation Facility, Grenoble, France
//               All Rights Reversed
//-======================================================================

package fr.esrf.TangoApi;




/** 
 *	Class Description:
 *	This class manage data object for Tango device access.
 *
 * @author  verdier
 * @version  $Revision$
 */


public class DbDatum implements java.io.Serializable
{
	public String		name;
	private boolean		is_empty_val = true;
	private String[]	values;


	//===========================================================
	/**
	 *	Default constructor for the dDbDatum Object.
	 *
	 *	@param name	object name.
	 */
	//===========================================================
	public DbDatum(String name)
	{
		this.name = new String(name);
	}


	//**********	Constructors with value added	***************//

	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, boolean argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, short argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, int argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, float argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, double argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, String argin)
	{
		this.name = new String(name);
		insert(argin);
	}



	//**********	Constructors with value added	***************//

	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, byte[] argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, short[] argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, int[] argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, float[] argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, double[] argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param argin	Values.
	 */
	//===========================================================
	public DbDatum(String name, String[] argin)
	{
		this.name = new String(name);
		insert(argin);
	}
	
	
	
	//===========================================================
	/**
	 *	Constructor for the dDbDatum Object.
	 *
	 *	@param name		object name.
	 *	@param strval	Values as string array.
	 *	@param start	Index to start in array
	 *	@param end		Index to stop array.
	 */
	//===========================================================
	public DbDatum(String name, String[] strval, int start, int end)
	{
		this.name = new String(name);
		String[]	tmp;
		tmp = new String[end-start];
		for (int i=0 ; i<end-start ; i++)
			tmp[i] = strval[start+i];
		insert(tmp);
	}

	//=======================================
	/**
	 *	Give the number of values 1 or more if array.
	 */
	//=======================================
	public int size()
	{
		return values.length;
	}	
	


	//**********	Insert Methods for basic types *********************
	
	
	//===========================================
	/**
	 *	Insert method for argin is boolean.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(boolean argin)
	{
		values = new String[1];
		values[0] = String.valueOf(argin);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is short.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(short argin)
	{
		values = new String[1];
		values[0] = String.valueOf(argin);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is long.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(long argin)
	{
		values = new String[1];
		values[0] = String.valueOf(argin);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is int.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(int argin)
	{
		values = new String[1];
		values[0] = String.valueOf(argin);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is float.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(float argin)
	{
		values = new String[1];
		values[0] = String.valueOf(argin);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is double.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(double argin)
	{
		values = new String[1];
		values[0] = String.valueOf(argin);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is String.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(String argin)
	{
		values = new String[1];
		values[0] = new String(argin);
		is_empty_val = false;
	}




	//**********	Insert Methods for sequence types	*********************

	//===========================================
	/**
	 *	Insert method for argin is DevVarCharArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(byte[] argin)
	{
		values = new String[argin.length];
		for (int i=0 ; i<argin.length ; i++)
			values[i] = String.valueOf(argin[i]);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarShortArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(short[] argin)
	{
		values = new String[argin.length];
		for (int i=0 ; i<argin.length ; i++)
			values[i] = String.valueOf(argin[i]);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarLongArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(int[] argin)
	{
		values = new String[argin.length];
		for (int i=0 ; i<argin.length ; i++)
			values[i] = String.valueOf(argin[i]);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarFloatArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(float[] argin)
	{
		values = new String[argin.length];
		for (int i=0 ; i<argin.length ; i++)
			values[i] = String.valueOf(argin[i]);
		is_empty_val = false;
	}
	//===========================================
	/**
	 *	Insert method for argin is DevVarDoubleArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(double[] argin)
	{
		values = new String[argin.length];
		for (int i=0 ; i<argin.length ; i++)
			values[i] = String.valueOf(argin[i]);
		is_empty_val = false;
	}

	//===========================================
	/**
	 *	Insert method for argin is DevVarStringArray.
	 *
	 *	@param argin	argin value for next command.
	 */
	//===========================================
	public void insert(String[] argin)
	{
		values = new String[argin.length];
		for (int i=0 ; i<argin.length ; i++)
			values[i] = new String(argin[i]);
		is_empty_val = false;
	}









	//**********	Extract Methods for basic types	*********************


	//===========================================
	/**
	 *	extract method for a boolean.
	 */
	//===========================================
	public boolean extractBoolean()
	{
		return (values[0].toLowerCase().equals("false")  ||
				values[0].equals("0"))? false : true;
	}
	//===========================================
	/**
	 *	extract method for a short.
	 */
	//===========================================
	public short extractShort()
	{
		return Short.parseShort(values[0]);
	}

	//===========================================
	/**
	 *	extract method for an int.
	 */
	//===========================================
	public int extractLong()
	{
		return Integer.parseInt(values[0]);
	}
	//===========================================
	/**
	 *	extract method for a long.
	 */
	//===========================================
	/***
	public long extractLong()
	{
		return Long.parseLong(values[0]);
	}
	***/
	//===========================================
	/**
	 *	extract method for a float.
	 */
	//===========================================
	public float extractFloat()
	{
		return (new Float(values[0])).floatValue();
		//	Exist only in JDK 1.2
		//return Float.parseFloat(values[0]);
	}
	//===========================================
	/**
	 *	extract method for a double.
	 */
	//===========================================
	public double extractDouble()
	{
		return (new Double(values[0])).doubleValue();
		//	Exist only in JDK 1.2
		//return Double.parseDouble(values[0]);
	}

	//===========================================
	/**
	 *	extract method for a String.
	 */
	//===========================================
	public String extractString()
	{
		return values[0];
	}



	//**********	Extract Methods for sequence types	*********************

	//===========================================
	/**
	 *	extract method for a byte Array.
	 */
	//===========================================
	public byte[] extractByteArray()
	{
		return values[0].getBytes();
	}
	//===========================================
	/**
	 *	extract method for a short Array.
	 */
	//===========================================
	public short[] extractShortArray()
	{
		short[]	argout;
		argout = new short[values.length];
		for (int i=0 ; i<values.length ; i++)
			argout[i] = Short.parseShort(values[i]);
		return argout;
	}
	//===========================================
	/**
	 *	extract method for a long Array.
	 */
	//===========================================
	public int[] extractLongArray()
	{
		int[]	argout;
		argout = new int[values.length];
		for (int i=0 ; i<values.length ; i++)
			argout[i] = Integer.parseInt(values[i]);
		return argout;
	}
	//===========================================
	/**
	 *	extract method for a float Array.
	 */
	//===========================================
	public float[] extractFloatArray()
	{
		float[]	argout;
		argout = new float[values.length];
		for (int i=0 ; i<values.length ; i++)
		{
			argout[i] = (new Float(values[i])).floatValue();
		}
		return argout;
	}
	//===========================================
	/**
	 *	extract method for a double Array.
	 */
	//===========================================
	public double[] extractDoubleArray()
	{
		double[]	argout;
		argout = new double[values.length];
		for (int i=0 ; i<values.length ; i++)
			argout[i] = (new Double(values[i])).doubleValue();
		return argout;
	}

	//===========================================
	/**
	 *	Return the true if the value is empty.
	 */
	//===========================================
	public boolean is_empty()
	{
		return is_empty_val;
	}
	//===========================================
	/**
	 *	extract method for a String Array.
	 */
	//===========================================
	public String[] extractStringArray()
	{
		return values;
	}
	


	//===========================================
	/**
	 *	Format values as String array.
	 */
	//===========================================
	public String[] toStringArray()
	{
		String[]	result;
		result = new String[size()+2];

		result[0] = name;
		result[1] = String.valueOf(size());
		for (int i=0 ; i<size() ; i++)
			result[i+2] = values[i];

		return result;
	}
}
