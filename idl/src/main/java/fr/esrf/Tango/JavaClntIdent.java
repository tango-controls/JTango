package fr.esrf.Tango;

/**
 * Generated from IDL struct "JavaClntIdent".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at May 14, 2014 1:27:02 PM
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
