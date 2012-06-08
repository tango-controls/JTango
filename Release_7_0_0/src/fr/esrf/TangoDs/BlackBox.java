//+============================================================================
//
// file :               BlackBox.java
//
// description :        java source code for the BlackBox class. 
//			This class is used to implement the 
//			tango device server black box. There is one
//			black box for each Tango device. This black box
//			keeps info. on all the activities on a device.
//			A client is able to retrieve these data via a Device
//			attribute
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
// Revision 1.4  2009/01/16 12:37:13  pascal_verdier
// IntelliJIdea warnings removed.
//
// Revision 1.3  2008/10/10 11:30:46  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.2  2007/09/25 07:28:38  pascal_verdier
// Blackbox access Synchronized.
//
// Revision 1.1  2007/08/23 08:33:25  ounsy
// updated change from api/java
//
// Revision 3.7  2005/12/02 09:55:02  pascal_verdier
// java import have been optimized.
//
// Revision 3.6  2004/05/14 13:47:56  pascal_verdier
// Compatibility with Tango-2.2.0 cpp
// (polling commands and attibites).
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
// Revision 1.1.1.1  2001/04/04 08:23:52  taurel
// Imported sources
//
// Revision 1.3  2000/04/13 08:22:59  taurel
// Added attribute support
//
// Revision 1.2  2000/02/04 09:09:57  taurel
// Just update revision number
//
// Revision 1.1.1.1  2000/02/04 09:08:23  taurel
// Imported sources
//
//
// copyleft :           European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
//-============================================================================

package fr.esrf.TangoDs;

import fr.esrf.Tango.DevFailed;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class BlackBox implements TangoConst
{
	private BlackBoxElt[]		box;
	private int			insert_elt;
	private int			nb_elt;
	private int			max_elt;
	
	private StringBuffer		elt_str;
	
//	private org.omg.OCI.Current	current;

//+-------------------------------------------------------------------------
//
// method : 		BlackBox 
// 
// description : 	Two constructors for the BlackBox class. The first one
//			does not take any argument and construct a black box
//			with the default depth.
//			The second one create a black box with a depth defined 
//			by the argument.
//
// argument : in : 	- max_size : The black box depth
//
//--------------------------------------------------------------------------

	public BlackBox()
	{
		box = new BlackBoxElt[Tango_DefaultBlackBoxDepth];		
		for (int i = 0;i < Tango_DefaultBlackBoxDepth;i++)
			box[i] = new BlackBoxElt();
		
		insert_elt = 0;
		nb_elt = 0;
		max_elt = Tango_DefaultBlackBoxDepth;

/*********** COMMENTS
 		Util tg = Util.instance();
		org.omg.CORBA.Object base = null;
		try
		{			
			base = tg.get_orb().resolve_initial_references("OCICurrent");
			current = org.omg.OCI.CurrentHelper.narrow(base);
		}
		catch (org.omg.CORBA.ORBPackage.InvalidName ex)
		{
			current = null;
		}
*********/
	}

	public BlackBox(int max_size)
	{
		box = new BlackBoxElt[max_size];
		for (int i = 0;i < max_size;i++)
			box[i] = new BlackBoxElt();
					
		insert_elt = 0;
		nb_elt = 0;
		max_elt = max_size;
		
/******** COMMENTS
 Util tg = Util.instance();
 org.omg.CORBA.Object base = null;
		try
		{
			base = tg.get_orb().resolve_initial_references("OCICurrent");
			current = org.omg.OCI.CurrentHelper.narrow(base);	
		}
		catch (org.omg.CORBA.ORBPackage.InvalidName ex)
		{
			current = null;
		}
********/
	}
	
//+-------------------------------------------------------------------------
//
// method : 		insert_attr
// 
// description : 	This method insert a new element in the black box when
//			this element is a attribute
//
// argument : in : 	- attr : The attribute type
//
//--------------------------------------------------------------------------


	public synchronized void insert_attr(int attr)
	{

//
// Insert elt in the box
//

		box[insert_elt].req_type = Req_Attribute;
		box[insert_elt].attr_type = attr;
		box[insert_elt].op_type = Op_Unknown;
		box[insert_elt].when = new Date();

//
// get client address
//

		get_client_host();
			
//
// manage insert and read indexes
//

		inc_indexes();
	}

	
//+-------------------------------------------------------------------------
//
// method : 		insert_attr
// 
// description : 	This method insert a new element in the black box when
//			this element is a attribute
//
// argument : in : 	- attr : The attribute type
//			- host : The client host
//
//--------------------------------------------------------------------------


	public synchronized void insert_attr(int attr,String host)
	{

//
// Insert elt in the box
//

		box[insert_elt].req_type = Req_Attribute;
		box[insert_elt].attr_type = attr;
		box[insert_elt].op_type = Op_Unknown;
		box[insert_elt].host = host;
		box[insert_elt].when = new Date();
	
//
// manage insert and read indexes
//

		inc_indexes();
	}


//+-------------------------------------------------------------------------
//
// method : 		insert_cmd
// 
// description : 	This method insert a new element in the black box when
//			this element is a call to the operation command_inout
//
// argument : in : 	- c_name : The command name
//
//--------------------------------------------------------------------------


	public synchronized void insert_cmd(String cmd, int version)
	{

//
// Insert elt in the box
//

		box[insert_elt].req_type = Req_Operation;
		box[insert_elt].attr_type = Attr_Unknown;
		if (version==2)
			box[insert_elt].op_type = Op_Command_inout_2;
		else
			box[insert_elt].op_type = Op_Command_inout;
		box[insert_elt].cmd_name = cmd;
		box[insert_elt].when = new Date();
		
//
// get client address
//

		get_client_host();
			
//
// manage insert and read indexes
//

		inc_indexes();
	}


//+-------------------------------------------------------------------------
//
// method : 		insert_cmd
// 
// description : 	This method insert a new element in the black box when
//			this element is a call to the operation command_inout
//
// argument : in : 	- c_name : The command name
//			- host : The client host
//
//--------------------------------------------------------------------------


	public synchronized void insert_cmd(String cmd,String host)
	{

//
// Insert elt in the box
//

		box[insert_elt].req_type = Req_Operation;
		box[insert_elt].attr_type = Attr_Unknown;
		box[insert_elt].op_type = Op_Command_inout;
		box[insert_elt].cmd_name = cmd;
		box[insert_elt].host = host;
		box[insert_elt].when = new Date();
	
//
// manage insert and read indexes
//

		inc_indexes();
	}


//+-------------------------------------------------------------------------
//
// method : 		insert_op
// 
// description : 	This method insert a new element in the black box when
//			this element is a call to an operation which is not
//			the command_inout operation
//
// argument : in : 	- cmd : The operation type
//
//--------------------------------------------------------------------------


	public synchronized void insert_op(int op)
	{

//
// Insert elt in the box
//

		box[insert_elt].req_type = Req_Operation;
		box[insert_elt].attr_type = Attr_Unknown;
		box[insert_elt].op_type = op;
		box[insert_elt].when = new Date();

//
// get client address
//

		get_client_host();
			
//
// manage insert and read indexes
//

		inc_indexes();
		
	}
	
//+-------------------------------------------------------------------------
//
// method : 		insert_op
// 
// description : 	This method insert a new element in the black box when
//			this element is a call to an operation which is not
//			the command_inout operation
//
// argument : in : 	- cmd : The operation type
//			- host : The client host
//
//--------------------------------------------------------------------------


	public synchronized void insert_op(int op,String host)
	{

//
// Insert elt in the box
//

		box[insert_elt].req_type = Req_Operation;
		box[insert_elt].attr_type = Attr_Unknown;
		box[insert_elt].op_type = op;
		box[insert_elt].host = host;
		box[insert_elt].when = new Date();
	
//
// manage insert and read indexes
//

		inc_indexes();
	}
	
	
//+-------------------------------------------------------------------------
//
// method : 		inc_indexes
// 
// description : 	This private method increment the indexes used to access
//			the box itself. This is necessary because the box must
//			be managed as a circular buffer
//
//--------------------------------------------------------------------------


	private void inc_indexes()
	{
		insert_elt++;
		if (insert_elt == max_elt)
			insert_elt = 0;
		
		if (nb_elt != max_elt)
			nb_elt++;
	}

//+-------------------------------------------------------------------------
//
// method : 		get_client_host
// 
// description : 	This private method retrieves the client host IP
//			address (the number). IT USES ORBACUS SPECIFIC
//			CLASSES
//
//--------------------------------------------------------------------------

	private void get_client_host()
	{
/************** COMMENTS
		if (current != null)
		{
			org.omg.OCI.TransportInfo info = current.get_oci_transport_info();
			org.omg.OCI.IIOP.TransportInfo iiopInfo = org.omg.OCI.IIOP.TransportInfoHelper.narrow(info);
		
			if (iiopInfo != null)
			{
				box[insert_elt].host_ip = iiopInfo.remote_addr();
				box[insert_elt].host = new String("Defined");
			}
			else
			{
				box[insert_elt].host = new String("Unknown");
			}
		}
*************/
	}
		
//+-------------------------------------------------------------------------
//
// method : 		build_info_as_str
// 
// description : 	Translate all the info stored in a black box element
//			into a readable string.
//
// argument : in : 	- index : The black box element index
//
//--------------------------------------------------------------------------

	private void build_info_as_str(int index)
	{
		
//
// Convert time to a string
//

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
		sdf.setTimeZone(TimeZone.getTimeZone("ECT"));
		String da = sdf.format(box[index].when);
		
		elt_str = new StringBuffer(da); 
	
//
// Add request type and command name in case of
//

		elt_str.append(" : ");
		if (box[index].req_type == Req_Operation)
		{
			elt_str.append("Operation ");
			switch (box[index].op_type)
			{
			case Op_Command_inout :
				elt_str.append("command_inout (cmd = ");
				elt_str.append(box[index].cmd_name);
				elt_str.append(") ");
				break;
			
			case Op_Ping :
				elt_str.append("ping ");
				break;
			
			case Op_Info :
				elt_str.append("info ");
				break;

			case Op_BlackBox :
				elt_str.append("blackbox ");
				break;

			case Op_Command_list :
				elt_str.append("command_list_query ");
				break;
		
			case Op_Command :
				elt_str.append("command_query ");
				break;

			case Op_Get_Attr_Config :
				elt_str.append("get_attribute_config ");
				break;
			
			case Op_Set_Attr_Config :
				elt_str.append("set_attribute_config ");
				break;
			
			case Op_Read_Attr :
				elt_str.append("read_attributes ");
				break;
			
			case Op_Write_Attr :
				elt_str.append("write_attributes ");
				break;
														
			case Op_Command_inout_2 :
				elt_str.append("command_inout_2 (cmd = ").append(box[index].cmd_name).append(") ");
				break;

			case Op_Command_list_2 :
				elt_str.append("command_list_query_2 ");
				break;

			case Op_Command_2 :
				elt_str.append("command_query_2 ");
				break;

			case Op_Get_Attr_Config_2 :
				elt_str.append("get_attribute_config_2 ");
				break;

			case Op_Read_Attr_2 :
				elt_str.append("read_attributes_2 ");
				break;

			case Op_Command_inout_history_2 :
				elt_str.append("command_inout_history_2 ");
				break;

			case Op_Read_Attr_history_2 :
				elt_str.append("read_attribute_history_2");
				break;

			case Op_Unknown :
				elt_str.append("unknown operation !!!!!");
				return;
			}
		}	
		else if (box[index].req_type == Req_Attribute)
		{
			elt_str.append("Attribute ");
			switch (box[index].attr_type)
			{
			case Attr_Name :
				elt_str.append("name ");
				break;
			
			case Attr_Description :
				elt_str.append("description ");
				break;

			case Attr_Status :
				elt_str.append("status ");
				break;
			
			case Attr_State :
				elt_str.append("state ");
				break;
			
			case Attr_AdmName :
				elt_str.append("adm_name ");
				break;
							
			case Attr_Unknown :
				elt_str.append("unknown attribute !!!!!");
				return;
			}
		}
		else
		{
			elt_str.append("Unknown CORBA request type !!!!!");
			return;
		}
	
//
// Add client host if defined
//

		if (box[index].host.equals("Unknown") != true)
		{
			int[] conv_addr = new int[4];
			for (int i = 0;i < 4;i++)
			{
				if (box[index].host_ip[i] < 0)
					conv_addr[i] = 0xff + (int)(box[index].host_ip[i]) + 1;
				else
					conv_addr[i] = (int)(box[index].host_ip[i]);
			}
			

			StringBuffer host_str = new StringBuffer();
			host_str.append(conv_addr[0]);
			host_str.append(".");
			host_str.append(conv_addr[1]);
			host_str.append(".");
			host_str.append(conv_addr[2]);
			host_str.append(".");
			host_str.append(conv_addr[3]);
			
			elt_str.append("requested from ");			
			InetAddress ad;
			try
			{
				ad = InetAddress.getByName(host_str.toString());				
				elt_str.append(ad.getHostName());
			}
			catch (UnknownHostException ex)
			{
				elt_str.append(host_str.toString());
			}
		}
	}

//+-------------------------------------------------------------------------
//
// method : 		read
// 
// description : 	Read black box element as strings. The newest element
//			is return in the first position
//
// argument : in : 	- index : The number of element to read
//
//--------------------------------------------------------------------------

	public String[] read(int wanted_elt) throws DevFailed
	{

//
// Throw exeception if the wanted element is stupid and if there is no element
// stored in he black box
//

		if (wanted_elt <= 0)
		{
			Except.throw_exception("API_BlackBoxArgument",
					       "Argument to read black box out of range",
					       "BlackBox::read");
		}
		if (nb_elt == 0)
		{
			Except.throw_exception("API_BlackBoxEmpty",
					       "Nothing stored yet in black-box",
					       "BlackBox::read");
		}
			
//
// Limit wanted element to a reasonable value
//

		if (wanted_elt > max_elt)
			wanted_elt = max_elt;
		
		if (wanted_elt > nb_elt)
			wanted_elt = nb_elt;
		
//
// Read black box elements
//

		String[] ret = new String[wanted_elt];
	
		int read_index;
		if (insert_elt == 0)
			read_index = max_elt - 1;
		else
			read_index = insert_elt - 1;
		for (int i = 0;i < wanted_elt;i++)
		{
			build_info_as_str(read_index);
			ret[i] = new String(elt_str);
		
			read_index--;
			if (read_index < 0)
				read_index = max_elt - 1;
		}
		
		return ret;
	}
	
}
