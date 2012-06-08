//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ArchivingConfig.
//						(Chinkumo Jean) - Apr 10, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.12.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2  2005/06/14 10:12:11  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.1.4.1  2005/06/13 15:11:50  chinkumo
// Minor changes made to improve the efficiency of the toString method.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
// Revision 1.6  2004/10/07 14:48:01  chinkumo
// Minor change.
//
// Revision 1.5  2004/09/01 13:26:04  chinkumo
// Heading was updated.
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import fr.soleil.TangoArchiving.ArchivingTools.Mode.Mode;

import java.util.*;

public class ArchivingConfig
{
	private String device_in_charge = null;
	private Mode mode = null;
	private Hashtable attribute_list = null;                      // This list contains AttributeLight objects types

	/**
	 * Default constructor
	 * Creates a new instance of ArchivingConfig
	 */
	public ArchivingConfig()
	{
		attribute_list = new Hashtable();
	}

	/**
	 * This constructor takes several parameters as inputs.
	 *
	 * @param attributeList the list of attributes
	 * @see #ArchivingConfig()
	 */
	public ArchivingConfig(ArrayList attributeList)
	{
		attribute_list = new Hashtable();
		setAttributeList(attributeList);
	}

	/**
	 * This constructor takes several parameters as inputs.
	 *
	 * @param archivingConfigArray
	 * @see #ArchivingConfig()
	 */
	public ArchivingConfig(String[] archivingConfigArray)
	{
		int index = 0;
		try
		{
			// Device In Charge
			device_in_charge = archivingConfigArray[ 0 ];
			int modeArraySize = Integer.parseInt(archivingConfigArray[ 1 ]);
			// Build the mode object
			String[] modeArray = new String[ modeArraySize ];
			index = 2;
			for ( int i = 0 ; i < modeArraySize ; i++ )
			{
				modeArray[ i ] = archivingConfigArray[ index ];
				index++;
			}
			mode = new Mode(modeArray);

			// Build the list of attributes that belongs to the object
			attribute_list = new Hashtable();
			for ( int i = index ; i < archivingConfigArray.length ; i = i + 4 )
			{
				AttributeLight attributeLight = new AttributeLight();
				attributeLight.setAttribute_complete_name(archivingConfigArray[ i ]);
				attributeLight.setData_type(Integer.parseInt(archivingConfigArray[ i + 1 ]));
				attributeLight.setData_format(Integer.parseInt(archivingConfigArray[ i + 2 ]));
				attributeLight.setWritable(Integer.parseInt(archivingConfigArray[ i + 3 ]));
				add(attributeLight);
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public String getDevice_in_charge()
	{
		return device_in_charge;
	}

	public void setDevice_in_charge(String device_in_charge)
	{
		this.device_in_charge = device_in_charge;
	}

	public Mode getMode()
	{
		return mode;
	}

	public void setMode(Mode mode)
	{
		this.mode = mode;
	}

	/**
	 * Returns the <I>list of attributes</I> that are included in this archiving configuration.
	 *
	 * @return the <I>list of attributes</I> that are included in the context.
	 */
	public Enumeration getAttributeListKeys()
	{
		Enumeration myEnumeration = attribute_list.keys();
		return myEnumeration;
	}

	/**
	 * Returns the <I>list of attributes</I> that are included in this archiving configuration.
	 *
	 * @return the <I>list of attributes</I> that are included in the context.
	 */
	public Collection getAttributeListValues()
	{
		Collection collection = attribute_list.values();
		return collection;
	}

	/**
	 * Sets the ArchivingConfig's <I>list of attributes</I> included in the context.
	 *
	 * @param attributeName the <I>list of attributes</I> that are included in the context.
	 */
	public AttributeLight getAttribute(String attributeName)
	{
		AttributeLight attributeLight = ( AttributeLight ) attribute_list.get(attributeName);
		return attributeLight;
	}

	/**
	 * Sets the ArchivingConfig's <I>list of attributes</I> included in the context.
	 *
	 * @param attributeList the <I>list of attributes</I> that are included in the context.
	 */
	public void setAttributeList(ArrayList attributeList)
	{
		for ( int i = 0 ; i < attributeList.size() ; i++ )
		{
			AttributeLight attributeLight = ( AttributeLight ) attributeList.get(i);
			attribute_list.put(attributeLight.getAttribute_complete_name() , attributeLight);
		}
	}

	public void add(AttributeLight attributeLight)
	{
		attribute_list.put(attributeLight.getAttribute_complete_name() , attributeLight);
	}


	public void remove(String attributeLightName)
	{
		attribute_list.remove(attributeLightName);
	}

	public void removeAll()
	{
		Enumeration attributeKeys = attribute_list.keys();
		while ( attributeKeys.hasMoreElements() )
		{
			String attributeKey = ( String ) attributeKeys.nextElement();
			attribute_list.remove(attributeKey);

		}
	}

	public int size()
	{
		return attribute_list.size();
	}

	public String[] toArray()
	{
		String[] archivingConfigArray = null;
		try
		{
			int modeArraySize = mode.getArraySizeSmall();
			int size = 2 + modeArraySize + attribute_list.size() * AttributeLight.HDB_ATTRIBUTE_LIGHT_LENGTH;
			archivingConfigArray = new String[ size ];
			// Device In Charge
			archivingConfigArray[ 0 ] = getDevice_in_charge();
			archivingConfigArray[ 1 ] = Integer.toString(modeArraySize);

			String[] modeArray = mode.toArray();
			int k = 2;
			for ( int i = 0 ; i < modeArraySize ; i++ )
			{
				archivingConfigArray[ k ] = modeArray[ i ];
				k++;
			}
			// Attribute List
			Enumeration attributeKeys = attribute_list.keys();
			while ( attributeKeys.hasMoreElements() )
			{
				String attributeKey = ( String ) attributeKeys.nextElement();
				AttributeLight attributeLight = ( AttributeLight ) attribute_list.get(attributeKey);
				String[] attributeLightArray = attributeLight.toArray();
				for ( int i = 0 ; i < attributeLightArray.length ; i++ )
				{
					archivingConfigArray[ k ] = attributeLightArray[ i ];
					k++;
				}
				//k = k + AttributeLight.HDB_ATTRIBUTE_LIGHT_LENGTH;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return archivingConfigArray;

	}

	public String toString()
	{
		StringBuffer hdbConfigString = new StringBuffer("");
		hdbConfigString.append("Configuration : ...").append("\r\n");
		// Write the fields values
		hdbConfigString.append("\tdevice_in_charge = ").append(device_in_charge).append("\r\n");
		// Write the mode configuration
		hdbConfigString.append("\tmode.toString() = \r\n").append(mode.toString()).append("\r\n");
		// Write the attribute list names
		Enumeration my_attribute_list = this.getAttributeListKeys();
		int i = 0;
		while ( my_attribute_list.hasMoreElements() )
		{
			String my_attribute_name = ( String ) my_attribute_list.nextElement();
			AttributeLight AttributeLight = ( AttributeLight ) attribute_list.get(my_attribute_name);
			hdbConfigString.append("[").append(i++).append("] : \r\n").append(AttributeLight.toString()).append("\r\n");
		}
		return hdbConfigString.toString();
	}

}

