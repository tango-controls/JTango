//+============================================================================
//
// file :               AttrProperty.java
//
// description :        Java source code for the AttrProperty class.
//			This class is used to manage couple attribute
//			property name and attribute property value.
//			Both are stored as strings
//
// project :            TANGO
//
// author(s) :          E.Taurel
//
// $Revision$
//
// $Log$
// Revision 3.6  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:57  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:23  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:36  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:20  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:54  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:22:59  taurel
// Added attribute support
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;
 
class AttrProperty implements TangoConst
{

	private String		attr_name;
	private String		attr_value;
	private	int		attr_lg;
	
//+-------------------------------------------------------------------------
//
// method : 		AttrProperty 
// 
// description : 	constructors for AttrProperty class
//
//--------------------------------------------------------------------------

 
	public AttrProperty(String name,String value)
	{
		attr_lg = 0;
		attr_name = name;
		attr_value = value;
		
//
// Property name in lower case letters
//

		attr_name.toLowerCase();
	
//
// For data_type or data_format properties, also change property value to
// lowercase letters
//

		if ((attr_name.equals("data_type") == true) || 
		    (attr_name.equals("data_format") == true))
		{
			attr_value.toLowerCase();
		}
	}
 
	public AttrProperty(String name,int value)
	{
		attr_lg = value;
		attr_name = name;
	}

//+-------------------------------------------------------------------------
//
// method : 		AttrProperty.convert 
// 
// description : 	Convert the property value into a long. The long data
//			is also stored in the AttrProperty class
//
//--------------------------------------------------------------------------

	void convert() throws DevFailed
	{
		try
		{
			attr_lg = Integer.parseInt(attr_value);
		}
		catch (NumberFormatException ex)
		{
			Except.throw_exception("API_AttrOptProp",
					       "Can't convert property value",
					       "AttrProperty.convert");
		}
	}

//+-------------------------------------------------------------------------
//
// method : 		AttrProperty.toString
// 
// description : 	Redefinition of the Object.toString method to
//			print attribute name and value in one go
//
//--------------------------------------------------------------------------

	public String toString()
	{
		StringBuffer s = new StringBuffer("Property name = ");
		s.append(attr_name);
		s.append(", Property value = ");
		s.append(attr_value);
		
		return s.toString();
	}

//+-------------------------------------------------------------------------
//
// Methods to retrieve/set some data members from outside the class and all
// its inherited classes
//
//--------------------------------------------------------------------------

	String get_value()
	{
		return attr_value;
	}
	
	String get_name()
	{
		return attr_name;
	}
	
	int get_lg_value()
	{
		return attr_lg;
	}
}
