//+=============================================================================
//
// file :         T2Ttypes.java
//
// description :  java source for the Taco/Tango type conversion. 
//
// project :      TANGO api
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
// Revision 1.2  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.1  2007/08/23 10:04:26  ounsy
// add constant class
//
// Revision 3.6  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.5  2004/03/12 13:15:23  pascal_verdier
// Using JacORB-2.1
//
// Revision 3.0  2003/04/29 08:03:29  pascal_verdier
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
// Revision 1.2  2001/11/08 10:47:27  verdier
// Package added.
//
// Revision 1.1  2001/11/07 14:45:02  verdier
// Initial revision
//
//
// copyleft :     European Synchrotron Radiation Facility
//                BP 220, Grenoble 38043
//                FRANCE
//
//=============================================================================


package fr.esrf.TangoApi;



import fr.esrf.Tango.DevState;
import fr.esrf.TangoDs.TangoConst;

public class T2Ttypes implements TangoConst
{
/**
 *	Here are the TACO type definitions
 */
	protected static final int	D_UNKNOWN_TYPE		=		-1;
	protected static final int	D_VOID_TYPE			=		0;
	protected static final int	D_BOOLEAN_TYPE		=		1;
	protected static final int	D_USHORT_TYPE		=		70;
	protected static final int	D_SHORT_TYPE		=		2;
	protected static final int	D_ULONG_TYPE		=		71;
	protected static final int	D_LONG_TYPE			=		3;
	protected static final int	D_FLOAT_TYPE		=		4;
	protected static final int	D_DOUBLE_TYPE		=		5;
	protected static final int	D_STRING_TYPE		=		6;
	protected static final int	D_INT_FLOAT_TYPE	=		27;
	protected static final int	D_FLOAT_READPOINT	=		7;
	protected static final int	D_STATE_FLOAT_READPOINT=	8;
	protected static final int	D_LONG_READPOINT	=		22;
	protected static final int	D_DOUBLE_READPOINT	=		23;
	protected static final int	D_VAR_CHARARR		=		9;
	protected static final int	D_VAR_STRINGARR		=		24;
	protected static final int	D_VAR_USHORTARR		=		72;
	protected static final int	D_VAR_SHORTARR		=		10;
	protected static final int	D_VAR_ULONGARR		=		69;
	protected static final int	D_VAR_LONGARR		=		11;
	protected static final int	D_VAR_FLOATARR		=		12;
	protected static final int	D_VAR_DOUBLEARR		=		68;
	protected static final int	D_VAR_FRPARR		=		25;
	protected static final int	D_VAR_SFRPARR		=		73;
	protected static final int	D_VAR_LRPARR		=		45;
	protected static final int	D_OPAQUE_TYPE		=		47;

//================================================================
//	Method called from cpp library.
//================================================================
	public static int tangoType(int	 taco_code)
	{
		switch(taco_code)
		{
			case D_VOID_TYPE:
				return Tango_DEV_VOID;
			case D_BOOLEAN_TYPE:
				return Tango_DEV_BOOLEAN;

			case D_SHORT_TYPE:
				return Tango_DEV_SHORT;
			case D_USHORT_TYPE:
				return Tango_DEV_USHORT;

			case D_LONG_TYPE:
				return Tango_DEV_LONG;
			case D_ULONG_TYPE:
				return Tango_DEV_ULONG;

			case D_FLOAT_TYPE:
				return Tango_DEV_FLOAT;
			case D_DOUBLE_TYPE:
				return Tango_DEV_DOUBLE;

			case D_STRING_TYPE:
				return Tango_DEV_STRING;

			//	Sequences Types				
			case D_VAR_CHARARR:
				return Tango_DEVVAR_CHARARRAY;
			case D_OPAQUE_TYPE:
				return Tango_DEVVAR_CHARARRAY;
			case D_VAR_SHORTARR:
				return Tango_DEVVAR_SHORTARRAY;
			case D_VAR_LONGARR:
				return Tango_DEVVAR_LONGARRAY;
			case D_VAR_FLOATARR:
				return Tango_DEVVAR_FLOATARRAY;
			case D_VAR_DOUBLEARR:
				return Tango_DEVVAR_DOUBLEARRAY;
			case D_VAR_STRINGARR:
				return Tango_DEVVAR_STRINGARRAY;

			//	Compound taco types
			case D_STATE_FLOAT_READPOINT:
				return Tango_DEVVAR_FLOATARRAY;
		}
		return -1;
	}
//================================================================
//	Convert Tango type to Taco type
//================================================================
	public static int tacoType(int tango_code)
	{
		switch(tango_code)
		{
			case Tango_DEV_VOID:
				return D_VOID_TYPE;
			case Tango_DEV_BOOLEAN:
				return D_BOOLEAN_TYPE;

			case Tango_DEV_SHORT:
				return D_SHORT_TYPE;
			case Tango_DEV_USHORT:
				return D_USHORT_TYPE;

			case Tango_DEV_LONG:
				return D_LONG_TYPE;
			case Tango_DEV_ULONG:
				return D_ULONG_TYPE;

			case Tango_DEV_FLOAT:
				return D_FLOAT_TYPE;
			case Tango_DEV_DOUBLE:
				return D_DOUBLE_TYPE;

			case Tango_DEV_STRING:
				return D_STRING_TYPE;

			//	Sequences Types				
			case Tango_DEVVAR_CHARARRAY:
				return D_VAR_CHARARR;
			case Tango_DEVVAR_SHORTARRAY:
				return D_VAR_SHORTARR;
			case Tango_DEVVAR_LONGARRAY:
				return D_VAR_LONGARR;
			case Tango_DEVVAR_FLOATARRAY:
				return D_VAR_FLOATARR;
			case Tango_DEVVAR_DOUBLEARRAY:
				return D_VAR_DOUBLEARR;
			case Tango_DEVVAR_STRINGARRAY:
				return D_VAR_STRINGARR;
		}
		return -1;
	}

//================================================================
//================================================================
	protected static final int	DEVUNKNOWN	 =	0;
	protected static final int	DEVOFF		 =	1;
	protected static final int	DEVON		 =	2;
	protected static final int	DEVCLOSE	 =	3;
	protected static final int	DEVOPEN		 =	4;
	protected static final int	DEVINSERTED	 =	7;
	protected static final int	DEVEXTRACTED =	8;
	protected static final int	DEVMOVING	 =	9;
	protected static final int	DEVWARMUP	 =	10;
	protected static final int	DEVINIT		 =	11;
	protected static final int	DEVSTANDBY	 =	12;
	protected static final int	DEVFAULT	 =	23;
	protected static final int	RUNNING      =	44;
	protected static final int	DEVALARM     =	45;
	protected static final int	DEVDISABLED	 =	46;

//================================================================
//================================================================
	public static DevState tangoState(int taco_code)
	{
		switch(taco_code)
		{
			case DEVOFF:
				return DevState.OFF;
			case DEVON:
				return DevState.ON;
			case DEVCLOSE:
				return DevState.CLOSE;
			case DEVOPEN:
				return DevState.OPEN;
			case DEVINSERTED:
				return DevState.INSERT;
			case DEVEXTRACTED:
				return DevState.EXTRACT;
			case DEVMOVING:
				return DevState.MOVING;
			case DEVWARMUP:
			case DEVINIT:
				return DevState.INIT;
			case DEVSTANDBY:
				return DevState.STANDBY;
			case DEVFAULT:
				return DevState.FAULT;
			case RUNNING:
				return DevState.RUNNING;
			case DEVALARM:
				return DevState.ALARM;
			case DEVDISABLED:
				return DevState.DISABLE;
		}
		return DevState.UNKNOWN;
	}
//================================================================
//================================================================
//================================================================
//================================================================
	public static int tangoStateCode(int taco_code)
	{
		DevState	state = tangoState(taco_code);
		return state.value();
	}
}
