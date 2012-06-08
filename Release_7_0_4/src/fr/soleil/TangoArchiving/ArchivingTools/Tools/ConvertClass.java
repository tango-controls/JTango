package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import java.util.Vector;

public class ConvertClass 
{
	/**
	 * Description : Build a array of String with the given String Vector
	 *
	 * @param my_vector The given String Vector
	 * @return a String type array that contains the differents vector's String type elements <br>
	 */
	public static String[] toStringArray(Vector my_vector)
	{
		String[] my_array;
		my_array = new String[ my_vector.size() ];
		for ( int i = 0 ; i < my_vector.size() ; i++ )
		{
			my_array[ i ] = ( String ) my_vector.elementAt(i);
		}
		return my_array;
	}
	
}
