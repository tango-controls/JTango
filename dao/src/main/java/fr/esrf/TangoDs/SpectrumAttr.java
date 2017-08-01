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
 * User class to create a one dimension attribute object.
 *
 * Information from this class and information fetched out from the Tango
 * database allows the Tango core software to create the Attribute object
 * for the attribute created by the user.
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25297 $
 */
 
public class SpectrumAttr extends Attr implements TangoConst
{
	int		max_x;

/**
 * Constructs a newly allocated SpectrumAttr object.
 * The attribute display type is set to OPERATOR_ATTR.
 *
 * @param 	name	The attribute name
 * @param	data_type	The attribute data type
 * @param	max_x	The attribute maximum x dimension
 *
 */
	public SpectrumAttr(String name,int data_type,int max_x) throws DevFailed
	{
		super(name,data_type);
		
		format = AttrDataFormat.SPECTRUM;
		if (max_x <= 0)
		{
			Util.out3.println("SpectrumAttr.SpectrumAttr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name);
			o.append(": "); 
			o.append(" Maximum x dim. wrongly defined");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),"SpectrumAttr.SpectrumAttr");
		}
		this.max_x = max_x;	
	}

/**
 * Constructs a newly allocated SpectrumAttr object.
 *
 * @param 	name	The attribute name
 * @param	data_type	The attribute data type
 * @param	max_x	The attribute maximum x dimension
 * @param	disp	The attribute display type
 *
 */
	public SpectrumAttr(String name,int data_type,int max_x,DispLevel disp) throws DevFailed
	{
		super(name,data_type,disp);
		
		format = AttrDataFormat.SPECTRUM;
		if (max_x <= 0)
		{
			Util.out3.println("SpectrumAttr.SpectrumAttr throwing exception");
		
			StringBuffer o = new StringBuffer("Attribute : ");
			o.append(name);
			o.append(": "); 
			o.append(" Maximum x dim. wrongly defined");
			Except.throw_exception("API_AttrWrongDefined",
				      	       o.toString(),"SpectrumAttr.SpectrumAttr");
		}
		this.max_x = max_x;	
	}
			
	int get_max_x()
	{
		return max_x;
	}
	
}

