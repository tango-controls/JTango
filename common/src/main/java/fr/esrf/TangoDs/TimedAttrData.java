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
// $Revision: 25296 $
//
//-======================================================================


package fr.esrf.TangoDs;

/**
 *	This class inherits from the AttrData class and adds 
 *	a date to all the data contains in the AttrData class
 *
 * @author $Author: pascal_verdier $
 * @version $Revision: 25296 $
 */

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevError;
import fr.esrf.Tango.TimeVal;

public class TimedAttrData extends AttrData implements java.io.Serializable
{
	public TimeVal	t_val;


//===============================================================
// Miscellaneous constructors for boolean scalar attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date (nb seconds since EPOCH)
	 */	
	//===============================================================
	public TimedAttrData(boolean[] p_data, int when)
	{
		super(p_data);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data,AttrQuality qual, int when)
	{
		super(p_data,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}


	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, TimeVal when)
	{
		super(p_data);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, AttrQuality qual, TimeVal when)
	{
		super(p_data, qual);
		t_val = when;
	}

//===============================================================
// Miscellaneous constructors for boolean spectrum attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, int x, int when)
	{
		super(p_data, x);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, int x, AttrQuality qual, int when)
	{
		super(p_data,x,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}
	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, int x, TimeVal when)
	{
		super(p_data, x);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, int x, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, qual);
		t_val = when;
	}


//===============================================================
//	Miscellaneous constructors for boolean image attribute
//===============================================================

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, int x, int y, int when)
	{
		super(p_data, x, y);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}  

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, int x, int y, AttrQuality qual, int when)
	{
		super(p_data, x, y, qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, int x, int y, TimeVal when)
	{
		super(p_data, x, y);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(boolean[] p_data, int x, int y, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, y, qual);
		t_val = when;
	}







//===============================================================
// Miscellaneous constructors for short scalar attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date (nb seconds since EPOCH)
	 */	
	//===============================================================
	public TimedAttrData(short[] p_data, int when)
	{
		super(p_data);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data,AttrQuality qual, int when)
	{
		super(p_data,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}


	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, TimeVal when)
	{
		super(p_data);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, AttrQuality qual, TimeVal when)
	{
		super(p_data, qual);
		t_val = when;
	}

//===============================================================
// Miscellaneous constructors for short spectrum attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, int x, int when)
	{
		super(p_data, x);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, int x, AttrQuality qual, int when)
	{
		super(p_data,x,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}
	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, int x, TimeVal when)
	{
		super(p_data, x);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, int x, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, qual);
		t_val = when;
	}


//===============================================================
//	Miscellaneous constructors for short image attribute
//===============================================================

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, int x, int y, int when)
	{
		super(p_data, x, y);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}  

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, int x, int y, AttrQuality qual, int when)
	{
		super(p_data, x, y, qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, int x, int y, TimeVal when)
	{
		super(p_data, x, y);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(short[] p_data, int x, int y, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, y, qual);
		t_val = when;
	}






//===============================================================
// Miscellaneous constructors for int scalar attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date (nb seconds since EPOCH)
	 */	
	//===============================================================
	public TimedAttrData(int[] p_data, int when)
	{
		super(p_data);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data,AttrQuality qual, int when)
	{
		super(p_data,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}


	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, TimeVal when)
	{
		super(p_data);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, AttrQuality qual, TimeVal when)
	{
		super(p_data, qual);
		t_val = when;
	}

//===============================================================
// Miscellaneous constructors for int spectrum attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, int x, int when)
	{
		super(p_data, x);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, int x, AttrQuality qual, int when)
	{
		super(p_data,x,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}
	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, int x, TimeVal when)
	{
		super(p_data, x);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, int x, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, qual);
		t_val = when;
	}


//===============================================================
//	Miscellaneous constructors for int image attribute
//===============================================================

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, int x, int y, int when)
	{
		super(p_data, x, y);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}  

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, int x, int y, AttrQuality qual, int when)
	{
		super(p_data, x, y, qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, int x, int y, TimeVal when)
	{
		super(p_data, x, y);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(int[] p_data, int x, int y, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, y, qual);
		t_val = when;
	}






//===============================================================
// Miscellaneous constructors for long scalar attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Polonger to the attribute value
	 * @param when The date (nb seconds since EPOCH)
	 */	
	//===============================================================
	public TimedAttrData(long[] p_data, int when)
	{
		super(p_data);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Polonger to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data,AttrQuality qual, int when)
	{
		super(p_data,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}


	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Polonger to the attribute value
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, TimeVal when)
	{
		super(p_data);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Polonger to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, AttrQuality qual, TimeVal when)
	{
		super(p_data, qual);
		t_val = when;
	}

//===============================================================
// Miscellaneous constructors for long spectrum attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Polonger to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, int x, int when)
	{
		super(p_data, x);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Polonger to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, int x, AttrQuality qual, int when)
	{
		super(p_data,x,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}
	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Polonger to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, int x, TimeVal when)
	{
		super(p_data, x);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Polonger to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, int x, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, qual);
		t_val = when;
	}


//===============================================================
//	Miscellaneous constructors for long image attribute
//===============================================================

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Polonger to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, int x, int y, int when)
	{
		super(p_data, x, y);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}  

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Polonger to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, int x, int y, AttrQuality qual, int when)
	{
		super(p_data, x, y, qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Polonger to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, int x, int y, TimeVal when)
	{
		super(p_data, x, y);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory polonged to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Polonger to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(long[] p_data, int x, int y, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, y, qual);
		t_val = when;
	}






//===============================================================
// Miscellaneous constructors for float scalar attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date (nb seconds since EPOCH)
	 */	
	//===============================================================
	public TimedAttrData(float[] p_data, int when)
	{
		super(p_data);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data,AttrQuality qual, int when)
	{
		super(p_data,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}


	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, TimeVal when)
	{
		super(p_data);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, AttrQuality qual, TimeVal when)
	{
		super(p_data, qual);
		t_val = when;
	}

//===============================================================
// Miscellaneous constructors for float spectrum attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, int x, int when)
	{
		super(p_data, x);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, int x, AttrQuality qual, int when)
	{
		super(p_data,x,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}
	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, int x, TimeVal when)
	{
		super(p_data, x);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, int x, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, qual);
		t_val = when;
	}


//===============================================================
//	Miscellaneous constructors for float image attribute
//===============================================================

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, int x, int y, int when)
	{
		super(p_data, x, y);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}  

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, int x, int y, AttrQuality qual, int when)
	{
		super(p_data, x, y, qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, int x, int y, TimeVal when)
	{
		super(p_data, x, y);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(float[] p_data, int x, int y, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, y, qual);
		t_val = when;
	}





//===============================================================
// Miscellaneous constructors for double scalar attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date (nb seconds since EPOCH)
	 */	
	//===============================================================
	public TimedAttrData(double[] p_data, int when)
	{
		super(p_data);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data,AttrQuality qual, int when)
	{
		super(p_data,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}


	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, TimeVal when)
	{
		super(p_data);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, AttrQuality qual, TimeVal when)
	{
		super(p_data, qual);
		t_val = when;
	}

//===============================================================
// Miscellaneous constructors for double spectrum attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, int x, int when)
	{
		super(p_data, x);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, int x, AttrQuality qual, int when)
	{
		super(p_data,x,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}
	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, int x, TimeVal when)
	{
		super(p_data, x);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, int x, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, qual);
		t_val = when;
	}


//===============================================================
//	Miscellaneous constructors for double image attribute
//===============================================================

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, int x, int y, int when)
	{
		super(p_data, x, y);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}  

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, int x, int y, AttrQuality qual, int when)
	{
		super(p_data, x, y, qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, int x, int y, TimeVal when)
	{
		super(p_data, x, y);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(double[] p_data, int x, int y, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, y, qual);
		t_val = when;
	}






//===============================================================
// Miscellaneous constructors for String scalar attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date (nb seconds since EPOCH)
	 */	
	//===============================================================
	public TimedAttrData(String[] p_data, int when)
	{
		super(p_data);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data,AttrQuality qual, int when)
	{
		super(p_data,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}


	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, TimeVal when)
	{
		super(p_data);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, AttrQuality qual, TimeVal when)
	{
		super(p_data, qual);
		t_val = when;
	}

//===============================================================
// Miscellaneous constructors for String spectrum attribute
//===============================================================
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, int x, int when)
	{
		super(p_data, x);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}	

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, int x, AttrQuality qual, int when)
	{
		super(p_data,x,qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}
	
	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, int x, TimeVal when)
	{
		super(p_data, x);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, int x, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, qual);
		t_val = when;
	}


//===============================================================
//	Miscellaneous constructors for String image attribute
//===============================================================

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, int x, int y, int when)
	{
		super(p_data, x, y);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}  

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, int x, int y, AttrQuality qual, int when)
	{
		super(p_data, x, y, qual);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 * The attribute quality factor will be set to ATTR_VALID
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, int x, int y, TimeVal when)
	{
		super(p_data, x, y);
		t_val = when;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object.
	 *
	 * The memory pointed to by the <i>p_data</i> parameter will not be freed
	 *
	 * @param p_data Pointer to the attribute value
	 * @param x The attribute x length
	 * @param y The attribute y length
	 * @param qual The attribute quality factor
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(String[] p_data, int x, int y, AttrQuality qual, TimeVal when)
	{
		super(p_data, x, y, qual);
		t_val = when;
	}




	
//===============================================================
//	Miscellaneous constructors for errors
//===============================================================

	//===============================================================
	/**
	 * Create a new TimedAttrData object for errors.
	 *
	 * The created TimedAttrData is used to store attribute errors
	 * in the attribute history stack
	 *
	 * @param errors The error stack
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(DevError[] errors, int when)
	{
		super(errors);
		t_val.tv_sec = when;
		t_val.tv_usec = 0;
	}

	//===============================================================
	/**
	 * Create a new TimedAttrData object for errors.
	 *
	 * The created TimedAttrData is used to store attribute errors
	 * in the attribute history stack
	 *
	 * @param errors The error stack
	 * @param when The date
	 */
	//===============================================================
	public TimedAttrData(DevError[] errors, TimeVal when)
	{
		super(errors);
		t_val = when;
	}

}
