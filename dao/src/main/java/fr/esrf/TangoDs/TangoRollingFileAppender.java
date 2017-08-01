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

// java stuffs
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.xml.XMLLayout;

import java.io.IOException;

/**
 * A class to log to a rolling file.
 */
 
public class TangoRollingFileAppender extends RollingFileAppender implements TangoAppender
{
 /**
  * Construct a newly allocated TangoRollingFileAppender object.
  *
  * @param 	appender_name	This appender name
  * @param 	file_name	The name of the file to log to
  * @param 	rtf	The rolling threshold in Kb
  */
	public TangoRollingFileAppender(String appender_name, 
                                  String file_name,
                                  long rtf) throws IOException
	{
    super(new XMLLayout(), file_name, true);
    setName(appender_name);
    setMaximumFileSize(rtf * 1024);
	}

 /** 
 * Returns true if the appender is (still) valid, false otherwise.
 */
  public boolean isValid() {
   return closed;
  }
  
}
