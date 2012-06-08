//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description: This class is used to store all the data needed to build
//              an attribute value.
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/08/23 08:32:59  ounsy
// updated change from api/java
//
// Revision 3.4  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.3  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.2  2005/09/07 06:00:54  pascal_verdier
// Methods and fields set to public.
//
// Revision 3.1  2005/06/13 09:08:42  pascal_verdier
// Attribute historic buffer can be filled by trigger.
//
///
// Copyleft 2000 by European Synchrotron Radiation Facility, Grenoble, France
//-======================================================================
package fr.esrf.TangoDs;

/**
 *	This class is used to store all the data needed to build
 *	an attribute value.
 *
 * @author	$Author$
 * @version	$Revision$
 */

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevError;

public class AttrData implements TangoConst, java.io.Serializable
{
	public boolean[] 	bool_ptr;
	public short[] 		sh_ptr;
	public int[]	 	lg_ptr;
	public long[]	 	lg64_ptr;
	public float[] 		fl_ptr;
	public double[] 	db_ptr;
	public String[] 	str_ptr;
	public int			data_type;
	public AttrQuality	qual;
	public int			x;
	public int			y;
	public DevError[]	err;



//===============================================================
// For boolean scalar and spectrum
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(boolean[] p)
	{
		bool_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_BOOLEAN;
	}
	//===============================================================
	//===============================================================
	public AttrData(boolean[] p, AttrQuality q)
	{
		bool_ptr  = p;
		qual = q;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_BOOLEAN;
	}

	//===============================================================
	//===============================================================
	public AttrData(boolean[] p,int nb)
	{
		bool_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_BOOLEAN;
	}	
	//===============================================================
	//===============================================================
	public AttrData(boolean[] p,int nb, AttrQuality q)
	{
		bool_ptr  = p;
		qual = q;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_BOOLEAN;
	}

//===============================================================
// For boolean image
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(boolean[] p,int nb,int nb2)
	{
		bool_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_BOOLEAN;
	}	
	//===============================================================
	//===============================================================
	public AttrData(boolean[] p,int nb,int nb2, AttrQuality q)
	{
		bool_ptr  = p;
		qual = q;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_BOOLEAN;
	}	







//===============================================================
// For short scalar and spectrum
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(short[] p)
	{
		sh_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_SHORT;
	}
	//===============================================================
	//===============================================================
	public AttrData(short[] p, AttrQuality q)
	{
		sh_ptr  = p;
		qual = q;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_SHORT;
	}

	//===============================================================
	//===============================================================
	public AttrData(short[] p,int nb)
	{
		sh_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_SHORT;
	}	
	//===============================================================
	//===============================================================
	public AttrData(short[] p,int nb, AttrQuality q)
	{
		sh_ptr  = p;
		qual = q;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_SHORT;
	}

//===============================================================
// For short image
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(short[] p,int nb,int nb2)
	{
		sh_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_SHORT;
	}	
	//===============================================================
	//===============================================================
	public AttrData(short[] p,int nb,int nb2, AttrQuality q)
	{
		sh_ptr  = p;
		qual = q;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_SHORT;
	}	



//===============================================================
// For int scalar and spectrum
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(int[] p)
	{
		lg_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_LONG;
	}
	//===============================================================
	//===============================================================
	public AttrData(int[] p, AttrQuality q)
	{
		lg_ptr  = p;
		qual = q;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_LONG;
	}

	//===============================================================
	//===============================================================
	public AttrData(int[] p,int nb)
	{
		lg_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_LONG;
	}	
	//===============================================================
	//===============================================================
	public AttrData(int[] p,int nb, AttrQuality q)
	{
		lg_ptr  = p;
		qual = q;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_LONG;
	}

//===============================================================
// For int image
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(int[] p,int nb,int nb2)
	{
		lg_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_LONG;
	}	
	//===============================================================
	//===============================================================
	public AttrData(int[] p,int nb,int nb2, AttrQuality q)
	{
		lg_ptr  = p;
		qual = q;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_LONG;
	}	





//===============================================================
// For long scalar and spectrum
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(long[] p)
	{
		lg64_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_LONG;
	}
	//===============================================================
	//===============================================================
	public AttrData(long[] p, AttrQuality q)
	{
		lg64_ptr  = p;
		qual = q;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_LONG;
	}

	//===============================================================
	//===============================================================
	public AttrData(long[] p,int nb)
	{
		lg64_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_LONG;
	}	
	//===============================================================
	//===============================================================
	public AttrData(long[] p,int nb, AttrQuality q)
	{
		lg64_ptr  = p;
		qual = q;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_LONG;
	}

//===============================================================
// For long image
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(long[] p,int nb,int nb2)
	{
		lg64_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_LONG;
	}	
	//===============================================================
	//===============================================================
	public AttrData(long[] p,int nb,int nb2, AttrQuality q)
	{
		lg64_ptr  = p;
		qual = q;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_LONG;
	}	





//===============================================================
// For float scalar and spectrum
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(float[] p)
	{
		fl_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_FLOAT;
	}
	//===============================================================
	//===============================================================
	public AttrData(float[] p, AttrQuality q)
	{
		fl_ptr  = p;
		qual = q;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_FLOAT;
	}

	//===============================================================
	//===============================================================
	public AttrData(float[] p,int nb)
	{
		fl_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_FLOAT;
	}	
	//===============================================================
	//===============================================================
	public AttrData(float[] p,int nb, AttrQuality q)
	{
		fl_ptr  = p;
		qual = q;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_FLOAT;
	}

//===============================================================
// For float image
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(float[] p,int nb,int nb2)
	{
		fl_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_FLOAT;
	}	
	//===============================================================
	//===============================================================
	public AttrData(float[] p,int nb,int nb2, AttrQuality q)
	{
		fl_ptr  = p;
		qual = q;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_FLOAT;
	}	






//===============================================================
// For double scalar and spectrum
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(double[] p)
	{
		db_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_DOUBLE;
	}
	//===============================================================
	//===============================================================
	public AttrData(double[] p, AttrQuality q)
	{
		db_ptr  = p;
		qual = q;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_DOUBLE;
	}

	//===============================================================
	//===============================================================
	public AttrData(double[] p,int nb)
	{
		db_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_DOUBLE;
	}	
	//===============================================================
	//===============================================================
	public AttrData(double[] p,int nb, AttrQuality q)
	{
		db_ptr  = p;
		qual = q;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_DOUBLE;
	}

//===============================================================
// For double image
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(double[] p,int nb,int nb2)
	{
		db_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_DOUBLE;
	}	
	//===============================================================
	//===============================================================
	public AttrData(double[] p,int nb,int nb2, AttrQuality q)
	{
		db_ptr  = p;
		qual = q;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_DOUBLE;
	}	




//===============================================================
// For String scalar and spectrum
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(String[] p)
	{
		str_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_STRING;
	}
	//===============================================================
	//===============================================================
	public AttrData(String[] p, AttrQuality q)
	{
		str_ptr  = p;
		qual = q;
		x    = p.length;
		y    = 0;
		data_type = Tango_DEV_STRING;
	}

	//===============================================================
	//===============================================================
	public AttrData(String[] p,int nb)
	{
		str_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_STRING;
	}	
	//===============================================================
	//===============================================================
	public AttrData(String[] p,int nb, AttrQuality q)
	{
		str_ptr  = p;
		qual = q;
		x    = nb;
		y    = 0;
		data_type = Tango_DEV_STRING;
	}

//===============================================================
// For String image
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(String[] p,int nb,int nb2)
	{
		str_ptr  = p;
		qual = AttrQuality.ATTR_VALID;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_STRING;
	}	
	//===============================================================
	//===============================================================
	public AttrData(String[] p,int nb,int nb2, AttrQuality q)
	{
		str_ptr  = p;
		qual = q;
		x    = nb;
		y    = nb2;
		data_type = Tango_DEV_STRING;
	}	







//===============================================================
// For error
//===============================================================
	//===============================================================
	//===============================================================
	public AttrData(DevError[] e)
	{
		err = e;
	}
}
