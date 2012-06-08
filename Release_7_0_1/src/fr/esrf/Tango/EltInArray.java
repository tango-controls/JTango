package fr.esrf.Tango;

/**
 *	Generated from IDL definition of struct "EltInArray"
 *	@author JacORB IDL compiler 
 */

public final class EltInArray
	implements org.omg.CORBA.portable.IDLEntity
{
	public EltInArray(){}
	public int start;
	public int nb_elt;
	public EltInArray(int start, int nb_elt)
	{
		this.start = start;
		this.nb_elt = nb_elt;
	}
}
