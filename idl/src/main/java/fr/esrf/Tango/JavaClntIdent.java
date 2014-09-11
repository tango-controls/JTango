package fr.esrf.Tango;

/**
 * Generated from IDL struct "JavaClntIdent".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
 */

public final class JavaClntIdent
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public JavaClntIdent(){}
	public java.lang.String MainClass = "";
	public long[] uuid;
	public JavaClntIdent(java.lang.String MainClass, long[] uuid)
	{
		this.MainClass = MainClass;
		this.uuid = uuid;
	}
}
