package fr.esrf.TangoDs;

import java.util.Comparator;


class MyComp implements Comparator
{
	public int compare(Object o1,Object o2)
	{
		Command c1 = (Command)(o1);
		Command c2 = (Command)(o2);
		
		return c1.get_name().compareTo(c2.get_name());
	}
	
	public boolean equals(Object o)
	{
		return false;
	}
}
