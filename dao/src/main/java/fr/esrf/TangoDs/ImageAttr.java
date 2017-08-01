//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the TANGO client/server API.
//
// $Author: pascal_verdier $
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,
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
// $Revision: 25297 $
//
//-======================================================================


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
 * @author $Author: pascal_verdier $
 * @version $Revision: 25297 $
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
