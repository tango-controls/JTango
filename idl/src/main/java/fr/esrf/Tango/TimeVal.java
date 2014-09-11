package fr.esrf.Tango;

/**
 * Generated from IDL struct "TimeVal".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class TimeVal
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public TimeVal(){}
	public int tv_sec;
	public int tv_usec;
	public int tv_nsec;
	public TimeVal(int tv_sec, int tv_usec, int tv_nsec)
	{
		this.tv_sec = tv_sec;
		this.tv_usec = tv_usec;
		this.tv_nsec = tv_nsec;
	}
}
