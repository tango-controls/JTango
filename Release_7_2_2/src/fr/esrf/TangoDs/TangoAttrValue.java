//+============================================================================
//
// file :               TangoAttrValue.java
//
// description :        
//
// project :            TANGO
//
// $Author: :          E.Taurel
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
// Revision 1.3  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.2  2008/09/12 11:27:51  pascal_verdier
// Tango 7 first revision.
//
// Revision 1.1  2007/08/23 08:33:24  ounsy
// updated change from api/java
//
// Revision 3.7  2007/05/29 08:07:43  pascal_verdier
// Long64, ULong64, ULong, UShort and DevState attributes added.
//
// Revision 3.6  2005/06/13 09:08:42  pascal_verdier
// Attribute historic buffer can be filled by trigger.
//
// Revision 3.5  2004/03/12 14:07:56  pascal_verdier
// Use JacORB-2.1
//
// Revision 2.0  2003/01/09 16:02:58  taurel
// - Update release number before using SourceForge
//
// Revision 1.1.1.1  2003/01/09 15:54:39  taurel
// Imported sources into CVS before using SourceForge
//
// Revision 1.6  2001/10/10 08:11:25  taurel
// See Tango WEB pages for list of changes
//
// Revision 1.5  2001/07/04 15:06:38  taurel
// Many changes due to new release
//
// Revision 1.2  2001/05/04 12:03:22  taurel
// Fix bug in the Util.get_device_by_name() method
//
// Revision 1.1.1.1  2001/04/04 08:23:54  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:23:01  taurel
// Added attribute support
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevState;
import fr.esrf.Tango.DevEncoded;


class TangoAttrValue
{
	public DevEncoded[]		enc_value;
	public DevState[]		state_seq;
	public short[]			sh_seq;
	public int[]			lg_seq;
	public long[]			lg64_seq;
	public float[]			fl_seq;
	public double[]			db_seq;
	public String[]			str_seq;
	public boolean[]		bool_seq;

	public TangoAttrValue()
	{
	}
}
