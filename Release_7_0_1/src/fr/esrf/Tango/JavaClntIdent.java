package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "JavaClntIdent"
 *	@author JacORB IDL compiler 
 */

public final class JavaClntIdent
	implements org.omg.CORBA.portable.IDLEntity
{
	public JavaClntIdent(){}
	public java.lang.String MainClass = "";
	public long[] uuid;
	public JavaClntIdent(java.lang.String MainClass, long[] uuid)
	{
		this.MainClass = MainClass;
		this.uuid = uuid;
	}
}
