//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ArchivingMessConfig.
//						(Chinkumo Jean) -
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.8  2007/03/27 09:19:22  ounsy
// added diary logging in stop_archive_att when an attribute is filtered out of a config
//
// Revision 1.7  2007/01/05 12:47:17  pierrejoseph
// device_in_charge has been supressed.
// Modification of the ArchivingMessConfig object creation.
//
// Revision 1.6  2006/12/21 15:10:36  pierrejoseph
// One unused constructor has been supressed
//
// Revision 1.5  2006/10/11 08:35:18  ounsy
// added a ArchivingMessConfig(String[] archivingMessConfigArray, boolean deviceInChargeUsage) constructor
// commented the ArchivingMessConfig(String[] archivingMessConfigArray) constructor
//
// Revision 1.4  2006/10/05 15:41:04  ounsy
// added the filter() method
//
// Revision 1.3  2006/06/15 15:24:59  ounsy
// -Added a reference to the attributeToDedicatedArchiverHasthable Hasthable, which maps user-defined relations between attributes and dedicated archivers
// -Added a setDeviceInChargeForAttribute, that allows to define this relation on a per-attribute basis
//
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.2.4  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.1.2.3  2005/09/26 08:34:26  chinkumo
// no message
//
// Revision 1.1.2.2  2005/09/16 08:02:12  chinkumo
// The grouped info was included in this object.
//
// Revision 1.1.2.1  2005/09/09 08:21:24  chinkumo
// First commit !
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import java.util.*;

import fr.soleil.TangoArchiving.ArchivingTools.Diary.ILogger;

public class ArchivingMessConfig
{
	//private String device_in_charge = null;
	private boolean grouped = false;
	private Hashtable attribute_list = null;                      // This list contains AttributeLightMode objects types
    
    private Hashtable attributeToDedicatedArchiverHasthable;

    /**
     * Create an object with the simple contructor
     * @return
     */
    static public ArchivingMessConfig basicObjectCreation()
	{
		return new ArchivingMessConfig();
	}
	/**
	 * Default constructor
	 * Creates a new instance of ArchivingMessConfig
	 */
	private ArchivingMessConfig()
	{
		attribute_list = new Hashtable();
        attributeToDedicatedArchiverHasthable = new Hashtable();
	}


	/**
     * Create an object from an array who contains all the attributes information (type,format ...)
     * @return
     */
	static public ArchivingMessConfig creationWithFullInformation(String[] archivingMessConfigArray)
	{
		return new ArchivingMessConfig(archivingMessConfigArray,true);
	}
	
	/**
     * Create an object from an array who does not contain all the attributes information (type,format ...)
     * @return
     */	
	static public ArchivingMessConfig creationWithoutFullInformation(String[] archivingMessConfigArray)
	{
		return new ArchivingMessConfig(archivingMessConfigArray,false);
	}
	/**
	 * This constructor takes several parameters as inputs.
	 *
	 * @param attributeLightModeList the list of attributes
	 * @see #ArchivingMessConfig()
	 */
    private ArchivingMessConfig(String[] archivingMessConfigArray, boolean fullInformation)
    {
        /*for (int i = 0 ; i < archivingMessConfigArray.length ; i ++ )
        {
            System.out.println  ( "ArchivingMessConfig/new/i|"+i+"|archivingMessConfigArray[i]|"+archivingMessConfigArray[i] );    
        }*/
    	
        // Build the list of attributes that belongs to the object
        attribute_list = new Hashtable();
        attributeToDedicatedArchiverHasthable = new Hashtable();
        
        // Grouped 
        grouped = Boolean.getBoolean(archivingMessConfigArray[0]);
        if(fullInformation) addInAttListWithFullInformation(archivingMessConfigArray);
        else addInAttListWithoutFullInformation(archivingMessConfigArray);
    }
    
    private void addInAttListWithFullInformation(String[] archivingMessConfigArray)
    {
		/* Example With full Information :
		* true/false,11,"tango/tangotest/spjz_1/double_spectrum_ro",5,1,0,"archiving/hdbarchiver/01",NULL,4,MODE_P,10000,MODE_D,20000
		* or
		* true/false,9,"tango/tangotest/spjz_1/double_spectrum_ro",5,1,0,"archiving/hdbarchiver/01",NULL,2,MODE_P,10000
		* 
		* true/false,17,"tango/tangotest/spjz_2/double_spectrum_ro",5,1,0,"archiving/tdbarchiver/01",NULL,10,MODE_P,2000,TDB_SPEC,300000,21600000,MODE_D,2000,TDB_SPEC,300000,21600000
		* or
		* true/false,12,"tango/tangotest/spjz_2/double_spectrum_ro",5,1,0,"archiving/tdbarchiver/01",NULL,5,MODE_P,2000,TDB_SPEC,300000,21600000
		*/
        int index;
        for ( index = 1 ; index < archivingMessConfigArray.length ; )
        {
            //System.out.println  ( "ArchivingMessConfig/new/index 0|"+index+"|" );
            int attributeLightModeSize = Integer.parseInt(archivingMessConfigArray[ index ]);
            //System.out.println  ( "ArchivingMessConfig/new/attributeLightModeSize|"+attributeLightModeSize+"|" );
            String[] attributeLightModeArray = new String[ attributeLightModeSize ];
            index++;
            //System.out.println  ( "ArchivingMessConfig/new/index 1|"+index+"|" );
            System.arraycopy(archivingMessConfigArray , index , attributeLightModeArray , 0 , attributeLightModeSize);
 
            add(AttributeLightMode.creationWithFullInformation (attributeLightModeArray));
            
            index = index + attributeLightModeSize;
        }
    }
    
    private void addInAttListWithoutFullInformation(String[] archivingMessConfigArray)
    {
		/* Example With Not full Information :
		* true/false,1,"tango/tangotest/spjz_1/double_spectrum_ro",MODE_P,10000,MODE_D,20000
		* or
		* true/false,1,"tango/tangotest/spjz_1/double_spectrum_ro",MODE_P,10000
		* or
		* true/false,2,"tango/tangotest/spjz_1/double_spectrum_ro","tango/tangotest/spjz_1/double_scalar_ro",MODE_P,10000
		* 
		* true/false,1,"tango/tangotest/spjz_2/double_spectrum_ro",MODE_P,2000,TDB_SPEC,300000,21600000,MODE_D,2000,TDB_SPEC,300000,21600000
		* or
		* true/false,1,"tango/tangotest/spjz_2/double_spectrum_ro",MODE_P,2000,TDB_SPEC,300000,21600000
		*/    
    	
    	int numberOfAttributes = Integer.parseInt(archivingMessConfigArray[1]);
    	int modeSize = archivingMessConfigArray.length - 2 - numberOfAttributes;
        
        // Read all the attributes
        int index;
        for ( index = 0 ; index < numberOfAttributes; index ++ )
        {
            int attributeLightModeSize = modeSize + 1;
 
            String[] attributeLightModeArray = new String[ attributeLightModeSize ];
            
            // Copy of the modes values
            System.arraycopy(archivingMessConfigArray , (2 + numberOfAttributes) , attributeLightModeArray , 1 , modeSize);
            // attribute full_name copy
            attributeLightModeArray[0] = archivingMessConfigArray[index + 2];
            
            add(AttributeLightMode.creationWithoutFullInformation (attributeLightModeArray));
            
         }
    }
   

	public boolean isGrouped()
	{
		return grouped;
	}

	public void setGrouped(boolean grouped)
	{
		this.grouped = grouped;
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

	public String[] getAttributeList()
	{
		Enumeration myEnumeration = attribute_list.keys();
		String[] my_StList = new String[ attribute_list.size() ];
		int i = 0;
		while ( myEnumeration.hasMoreElements() )
		{
			my_StList[ i ] = ( String ) myEnumeration.nextElement();
			i++;
		}
		return my_StList;
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
	 * Sets the ArchivingMessConfig's <I>list of attributes</I> included in the context.
	 *
	 * @param attributeName the <I>list of attributes</I> that are included in the context.
	 */
	public AttributeLightMode getAttribute(String attributeName)
	{
		AttributeLightMode attributeLightMode = ( AttributeLightMode ) attribute_list.get(attributeName);
		return attributeLightMode;
	}

	/**
	 * Sets the ArchivingMessConfig's <I>list of attributes</I> included in the context.
	 *
	 * @param attributeList the <I>list of attributes</I> that are included in the context.
	 */
	public void setAttributeList(ArrayList attributeList)
	{
		for ( int i = 0 ; i < attributeList.size() ; i++ )
		{
			AttributeLightMode attributeLightMode = ( AttributeLightMode ) attributeList.get(i);
			attribute_list.put(attributeLightMode.getAttribute_complete_name() , attributeLightMode);
		}
	}

	public void add(AttributeLightMode attributeLightMode)
	{
		attribute_list.put(attributeLightMode.getAttribute_complete_name() , attributeLightMode);
	}


	public void remove(String attributeLightModeName)
	{
		attribute_list.remove(attributeLightModeName);
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
		String[] archivingMessConfigArray = null;

		Hashtable hashtableArr = new Hashtable();
		int archivingMessConfigSize = 0;

		Enumeration attributeKeys = attribute_list.keys();
		while ( attributeKeys.hasMoreElements() )
		{
			String attributeKey = ( String ) attributeKeys.nextElement();
			AttributeLightMode attributeLightMode = ( AttributeLightMode ) attribute_list.get(attributeKey);
			String[] attributeLightModeArray = attributeLightMode.toArray();

			// index initial + index spécifiant du tableau la taille attributeLightModeArray + place du tableau
			archivingMessConfigSize = archivingMessConfigSize + 1 + attributeLightModeArray.length;
			hashtableArr.put(attributeKey , attributeLightModeArray);
		}

		archivingMessConfigArray = new String[ 1 + archivingMessConfigSize ];
		archivingMessConfigArray[ 0 ] = Boolean.toString(grouped);
		int i = 1;
		attributeKeys = attribute_list.keys();
		while ( attributeKeys.hasMoreElements() )
		{
			String attributeKey = ( String ) attributeKeys.nextElement();
			String[] attributeLightModeArray = ( String[] ) hashtableArr.get(attributeKey);
			archivingMessConfigArray[ i ] = Integer.toString(attributeLightModeArray.length);
			i++;
			System.arraycopy(attributeLightModeArray , 0 , archivingMessConfigArray , i , attributeLightModeArray.length);
			i = i + attributeLightModeArray.length;
		}
		return archivingMessConfigArray;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("");
		sb.append("Configuration : ...").append("\r\n");
		// Write the fields values
		sb.append("\tgrouped = ").append(grouped).append("\r\n");

// Write the attribute list names
		Enumeration my_attribute_list = this.getAttributeListKeys();
		int i = 0;
		while ( my_attribute_list.hasMoreElements() )
		{
			String my_attribute_name = ( String ) my_attribute_list.nextElement();
			AttributeLightMode attributeLightMode = ( AttributeLightMode ) attribute_list.get(my_attribute_name);
			sb.append("[").append(i++).append("] : \r\n").append(attributeLightMode.toString()).append("\r\n");
		}
		return sb.toString();
	}

    public Hashtable getAttributeToDedicatedArchiverHasthable () 
    {
        return this.attributeToDedicatedArchiverHasthable;
    }

    public void setDeviceInChargeForAttribute(AttributeLightMode attributeLightMode, String nextDedicatedArchiver) 
    {
        boolean hasDedicatedArchiver = nextDedicatedArchiver != null && ! nextDedicatedArchiver.equals ( "" );
        if ( hasDedicatedArchiver )
        {
            this.attributeToDedicatedArchiverHasthable.put (  attributeLightMode.getAttribute_complete_name (), nextDedicatedArchiver );
        }
    }

    public void filter(boolean isDedicated, String[] reservedAttributes, ILogger logger) 
    {
        String ret;
        if ( !isDedicated  )
        {
            logger.trace(ILogger.LEVEL_DEBUG, "ArchivingMessConfig/filter/!isDedicated");
            return;
        }
        if ( reservedAttributes == null || reservedAttributes.length == 0  )
        {
            logger.trace(ILogger.LEVEL_DEBUG, "ArchivingMessConfig/filter/reservedAttributes == null || reservedAttributes.length == 0");
            return;
        }
        
        int numberOfReserved = reservedAttributes.length; 
        Vector reservedAttributesList = new Vector ( numberOfReserved );
        for ( int i = 0 ; i < numberOfReserved ; i ++ )
        {
            reservedAttributesList.add ( reservedAttributes [ i ] );
            //System.out.println ( "CLA/filter/reservedAttributes [ i ]|"+reservedAttributes [ i ] );
        }
        
        Enumeration myEnumeration = this.attribute_list.keys();
        while ( myEnumeration.hasMoreElements() )
        {
            String next = ( String ) myEnumeration.nextElement();
            //System.out.println ( "CLA/filter/next|"+next);
            logger.trace(ILogger.LEVEL_DEBUG, "ArchivingMessConfig/filter/next|"+next+"|");
            if ( ! reservedAttributesList.contains ( next ) )
            {
                String msg = "The following attribute is reserved |"+next+"| on a dedicated archiver. No archiving started on this archiver for this attribute.";
                logger.trace(ILogger.LEVEL_ERROR, msg);
                this.remove ( next );
            }
            else
            {
                logger.trace(ILogger.LEVEL_DEBUG, "ArchivingMessConfig/filter/next|"+next+"| OK");
            }
        }
    }
}

