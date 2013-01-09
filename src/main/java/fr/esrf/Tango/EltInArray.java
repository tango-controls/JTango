package fr.esrf.Tango;

/**
 * Generated from IDL struct "EltInArray".
 *
 * @author JacORB IDL compiler V 3.1, 19-Aug-2012
 * @version generated at Dec 11, 2012 4:18:48 PM
 */

public final class EltInArray
	implements org.omg.CORBA.portable.IDLEntity
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	public EltInArray(){}
	public int start;
	public int nb_elt;
	public EltInArray(int start, int nb_elt)
	{
		this.start = start;
		this.nb_elt = nb_elt;
	}
}
