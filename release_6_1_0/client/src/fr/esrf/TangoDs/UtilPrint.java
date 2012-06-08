
package fr.esrf.TangoDs;

/**
 * This class is a TANGO core class used to manage the different level of
 * printing in a TANGO device server. Within a device server, there is one
 * object of  this class for each printing level (actually four)
 *
 * @author	$Author$
 * @version	$Revision$
 */
 
public class UtilPrint
{

//
// Define all the println methods
//

/**
 * Print a long.
 *
 * This method prints a long data only if the process trace level is greater
 * or equal to the object print level. Otherwise, it does nothing
 *
 * @param i The long to be printed
 */
 	
	public void println(long i)
	{
	}

/**
 * Print an aray of characters.
 *
 * This method prints an array of characters only if the process trace level
 * is greater
 * or equal to the object print level. Otherwise, it does nothing
 *
 * @param i The array to be printed
 */
 	
	public void println(char i[])
	{
	}

/**
 * Print a String.
 *
 * This method prints a String only if the process trace level is greater
 * or equal to the object print level. Otherwise, it does nothing
 *
 * @param i The String to be printed
 */
 	
	public void println(String i)
	{
	}

/**
 * Print a boolean.
 *
 * This method prints a boolean data only if the process trace level is greater
 * or equal to the object print level. Otherwise, it does nothing
 *
 * @param i The boolean to be printed
 */
 	
	public void println(boolean i)
	{
	}

/**
 * Print a char.
 *
 * This method prints a char data only if the process trace level is greater
 * or equal to the object print level. Otherwise, it does nothing
 *
 * @param i The char to be printed
 */
 	
	public void println(char i)
	{
	}

/**
 * Print a double.
 *
 * This method prints a double data only if the process trace level is greater
 * or equal to the object print level. Otherwise, it does nothing
 *
 * @param i The double to be printed
 */
 	
	public void println(double i)
	{
	}

/**
 * Print a float.
 *
 * This method prints a float data only if the process trace level is greater
 * or equal to the object print level. Otherwise, it does nothing
 *
 * @param i The float to be printed
 */
 	
	public void println(float i)
	{
	}

/**
 * Print an Object.
 *
 * This method prints an Object only if the process trace level is greater
 * or equal to the object print level. Otherwise, it does nothing
 *
 * @param i The Object to be printed
 */
 	
	public void println(Object i)
	{
	}

/**
 * Finish the line.
 *
 */	
	public void println()
	{
	}	
}
