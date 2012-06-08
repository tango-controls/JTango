package fr.esrf.Tango;
/**
 *	Generated from IDL definition of enum "DevState"
 *	@author JacORB IDL compiler 
 */

public final class DevState
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _ON = 0;
	public static final DevState ON = new DevState(_ON);
	public static final int _OFF = 1;
	public static final DevState OFF = new DevState(_OFF);
	public static final int _CLOSE = 2;
	public static final DevState CLOSE = new DevState(_CLOSE);
	public static final int _OPEN = 3;
	public static final DevState OPEN = new DevState(_OPEN);
	public static final int _INSERT = 4;
	public static final DevState INSERT = new DevState(_INSERT);
	public static final int _EXTRACT = 5;
	public static final DevState EXTRACT = new DevState(_EXTRACT);
	public static final int _MOVING = 6;
	public static final DevState MOVING = new DevState(_MOVING);
	public static final int _STANDBY = 7;
	public static final DevState STANDBY = new DevState(_STANDBY);
	public static final int _FAULT = 8;
	public static final DevState FAULT = new DevState(_FAULT);
	public static final int _INIT = 9;
	public static final DevState INIT = new DevState(_INIT);
	public static final int _RUNNING = 10;
	public static final DevState RUNNING = new DevState(_RUNNING);
	public static final int _ALARM = 11;
	public static final DevState ALARM = new DevState(_ALARM);
	public static final int _DISABLE = 12;
	public static final DevState DISABLE = new DevState(_DISABLE);
	public static final int _UNKNOWN = 13;
	public static final DevState UNKNOWN = new DevState(_UNKNOWN);
	public int value()
	{
		return value;
	}
	public static DevState from_int(int value)
	{
		switch (value) {
			case _ON: return ON;
			case _OFF: return OFF;
			case _CLOSE: return CLOSE;
			case _OPEN: return OPEN;
			case _INSERT: return INSERT;
			case _EXTRACT: return EXTRACT;
			case _MOVING: return MOVING;
			case _STANDBY: return STANDBY;
			case _FAULT: return FAULT;
			case _INIT: return INIT;
			case _RUNNING: return RUNNING;
			case _ALARM: return ALARM;
			case _DISABLE: return DISABLE;
			case _UNKNOWN: return UNKNOWN;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected DevState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
