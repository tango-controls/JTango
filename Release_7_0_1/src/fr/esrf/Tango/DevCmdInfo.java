package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "DevCmdInfo"
 *	@author JacORB IDL compiler 
 */

public final class DevCmdInfo
	implements org.omg.CORBA.portable.IDLEntity
{
	public DevCmdInfo(){}
	public java.lang.String cmd_name = "";
	public int cmd_tag;
	public int in_type;
	public int out_type;
	public java.lang.String in_type_desc = "";
	public java.lang.String out_type_desc = "";
	public DevCmdInfo(java.lang.String cmd_name, int cmd_tag, int in_type, int out_type, java.lang.String in_type_desc, java.lang.String out_type_desc)
	{
		this.cmd_name = cmd_name;
		this.cmd_tag = cmd_tag;
		this.in_type = in_type;
		this.out_type = out_type;
		this.in_type_desc = in_type_desc;
		this.out_type_desc = out_type_desc;
	}
}
