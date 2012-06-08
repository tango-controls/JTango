package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "TimeVal"
 *	@author JacORB IDL compiler 
 */

public final class TimeVal
	implements org.omg.CORBA.portable.IDLEntity
{
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
