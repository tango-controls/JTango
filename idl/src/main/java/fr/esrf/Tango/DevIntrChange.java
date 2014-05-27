package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevIntrChange".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
 */

public final class DevIntrChange
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public DevIntrChange(){}
	public boolean dev_started;
	public fr.esrf.Tango.DevCmdInfo_2[] cmds;
	public fr.esrf.Tango.AttributeConfig_5[] atts;
	public DevIntrChange(boolean dev_started, fr.esrf.Tango.DevCmdInfo_2[] cmds, fr.esrf.Tango.AttributeConfig_5[] atts)
	{
		this.dev_started = dev_started;
		this.cmds = cmds;
		this.atts = atts;
	}
}
