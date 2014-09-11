package fr.esrf.Tango;

/**
 * Generated from IDL struct "DevIntrChange".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
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
