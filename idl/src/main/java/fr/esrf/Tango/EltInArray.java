package fr.esrf.Tango;

/**
 * Generated from IDL struct "EltInArray".
 *
 * @author JacORB IDL compiler V 3.5
 * @version generated at Sep 5, 2014 10:37:19 AM
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
