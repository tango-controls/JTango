//=============================================================================	
//
// file :		ImageAttr.java
//
// description :	Java source file for the SpectrumAttr class. 
//			This class is created by the device server programmer
//			to define a two dimensions attribute. Afterwards, the
//			Tango core software use it to create the real 
//			object representing the attribute (attribute class)
//
// project :		TANGO
//
// author(s) :		E.Taurel
//
// $Revision$
//
// $Log$
// Revision 3.7  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/05/14 13:47:58  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
//
// Revision 3.5  2004/03/12 14:07:57  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:57  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:24  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:37  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:21  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:54  taurel
// Imported sources
//
// Revision 1.1.1.1  2001/02/27 08:46:20  taurel
// Imported sources
//
//
// copyleft :		European Synchrotron Radiation Facility
//			BP 220, Grenoble 38043
//			FRANCE
//
//=============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;

/**
 * User class to create a two dimensions attribute object.
 *
 * Information from this class and information fetched out from the Tango
 * database allows the Tango core software to create the Attribute object
 * for the attribute created by the user.
 *
 * @author	$Author$
 * @version	$Revision$
 */
 
public class ImageAttr extends SpectrumAttr implements TangoConst
{
	int		max_y;

/**
 * Constructs a newly allocated ImageAttr object.
 * The attribute display type is set to OPERATOR_ATTR.
 *
 * @param 	name	The attribute name
 * @param	type	The attribute data type
 * @param	x	The attribute maximum x dimension
 * @param	y	The attribute maximum y dimension
 *
 */

	public ImageAttr(String  name,int type,int x,int y) throws DevFailed
	{
		super(name,type,x);
		format = AttrDataFormat.IMAGE;
		if (y <= 0)
		{
			Util.out3.println("ImageAttr.ImageAttr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name);
			o.append(": "); 
			o.append(" Maximum y dim. wrongly defined");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),
				      	       "ImageAttr.ImageAttr");
		}
		this.max_y = y;	
	}

/**
 * Constructs a newly allocated ImageAttr object.
 *
 * @param 	name	The attribute name
 * @param	type	The attribute data type
 * @param	x	The attribute maximum x dimension
 * @param	y	The attribute maximum y dimension
 * @param	disp	The attribute display type
 *
 */

	public ImageAttr(String  name,int type,int x,int y,DispLevel disp) throws DevFailed
	{
		super(name,type,x,disp);
		format = AttrDataFormat.IMAGE;
		if (y <= 0)
		{
			Util.out3.println("ImageAttr.ImageAttr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name);
			o.append(": "); 
			o.append(" Maximum y dim. wrongly defined");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),
				      	       "ImageAttr.ImageAttr");
		}
		this.max_y = y;	
	}
		
	int get_max_y()
	{
		return max_y;
	}
	
}
