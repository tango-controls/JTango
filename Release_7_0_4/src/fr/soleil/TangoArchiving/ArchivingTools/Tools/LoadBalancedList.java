package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import java.util.Enumeration;
import java.util.Hashtable;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.ErrSeverity;

//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  LoadBalancedList.
//						(Chinkumo Jean) - 7 mars 2005
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.12  2006/11/20 09:30:48  ounsy
// corrected addArchiver
//
// Revision 1.11  2006/10/19 12:33:44  ounsy
// the constructor now takes a isHistoric boolean parameter
//
// Revision 1.10  2006/10/11 08:59:40  ounsy
// moved initArchivers() to ArchivingmanagerApi to avoid a cross-reference problem at compile time
//
// Revision 1.9  2006/10/11 08:35:34  ounsy
// minor changes
//
// Revision 1.8  2006/10/09 14:02:19  ounsy
// simplified and corrected the findLightXXXArchiver
//
// Revision 1.7  2006/07/06 09:24:07  ounsy
// corrected a bug in addAtt2Archiver
//
// Revision 1.6  2006/06/15 15:28:02  ounsy
// Modified the findLightXXXArchiver and addAtt2LBL methods so that they don't take into account the dedicated archivers
//
// Revision 1.5  2006/04/11 09:10:45  ounsy
// double archiving protection
//
// Revision 1.4  2006/01/27 13:00:25  ounsy
// organised imports
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.12.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2  2005/06/14 10:12:11  chinkumo
// Branch (tangORBarchiving_1_0_1-branch_0)  and HEAD merged.
//
// Revision 1.1.4.1  2005/06/13 15:05:37  chinkumo
// Methods were commented.
//
// Revision 1.1  2005/03/07 18:21:38  chinkumo
// This class was added to manage the load balancing when distributing attributes on archivers
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

public class LoadBalancedList
{
	Hashtable _lblist;
    boolean historic;

	/**
	 * Initialize an empty LoadBalancedList.
	 * This object contains LoadBalancedArchiver object.
	 * This object is used to manage the archiving load balancing
	 */
	public LoadBalancedList(boolean _historic)
	{
		_lblist = new Hashtable();
        historic = _historic;
	}

	/**
	 * Add an archiver (device) to the LoadBalancedList
	 *
	 * @param archiverName the archiver name
	 * @param scLoad       the given archiver scalar charge
	 * @param spLoad       the given archiver spectrum charge
	 * @param imLoad       the given archiver image charge
	 */
	public void addArchiver(String archiverName , int scLoad , int spLoad , int imLoad)
	{
		/*if ( !_lblist.containsKey(archiverName) ) CLA 16/11/06
		{*/
			LoadBalancedArchiver loadBalancedArchiver = new LoadBalancedArchiver(scLoad , spLoad , imLoad);
			_lblist.put(archiverName , loadBalancedArchiver);
		//}
	}

	/**
	 * Return the light loaded archiver for the given data format (Scalar, Spectrum or Image)
	 *
	 * @param data_format the given data format
	 * @param deviceDefinedAttributeToDedicatedArchiver 
	 * @return the light loaded archiver for the given data format (Scalar, Spectrum or Image)
	 */
	private String findLightArchiver(int data_format)
	{
		String lightArch = "";
        
        switch ( data_format )
		{
			case AttrDataFormat._SCALAR:
				lightArch = findLightScalarArchiver ();
				break;
			case AttrDataFormat._SPECTRUM:
				lightArch = findLightSpectrumArchiver ();
				break;
			case AttrDataFormat._IMAGE:
				lightArch = findLightImageArchiver ();
				break;
		}
		return lightArch;
	}

	/**
	 * Return the light loaded archiver for scalar data format
	 * @param attributeToDedicatedArchiver 
	 *
	 * @return the light loaded archiver for scalar data format
	 */
	private String findLightScalarArchiver()
	{
        String lightArch = "";
		int min_load = -1;
		Enumeration archivers = _lblist.keys();
		while ( archivers.hasMoreElements() )
		{
			String arch = ( String ) archivers.nextElement();
            /*Archiver nextVal = new Archiver ( arch );
            nextVal.load ();*/
            Archiver nextVal;
            try 
            {
                nextVal = Archiver.findArchiver ( arch , historic );
            } 
            catch (ArchivingException e) 
            {
                e.printStackTrace();
                continue;
            }
            
            if ( nextVal != null && nextVal.isDedicated () )
            {
                continue;
            }
            
			if ( !lightArch.equals("") )
			{
				if ( min_load > getScalarLoad(arch) )
				{
					lightArch = arch;
					min_load = getScalarLoad(arch);
				}
			}
			else
			{
				lightArch = arch;
				min_load = getScalarLoad(arch);
			}
		}
		return lightArch;
	}

	/**
	 * Return the light loaded archiver for spectrum data format
	 * @param deviceDefinedAttributeToDedicatedArchiver 
	 *
	 * @return the light loaded archiver for spectrum data format
	 */
	private String findLightSpectrumArchiver()
	{
		String lightArch = "";
		int min_load = -1;
		Enumeration archivers = _lblist.keys();
		while ( archivers.hasMoreElements() )
		{
			String arch = ( String ) archivers.nextElement();
            /*Archiver nextVal = new Archiver ( arch );
            nextVal.load ();*/
            Archiver nextVal;
            try 
            {
                nextVal = Archiver.findArchiver ( arch , historic );
            } 
            catch (ArchivingException e) 
            {
                e.printStackTrace();
                continue;
            }
            
            if ( nextVal != null && nextVal.isDedicated () )
            {
                continue;
            }
            
			if ( !lightArch.equals("") )
			{
				if ( min_load > getSpectrumLoad(arch) )
				{
					lightArch = arch;
					min_load = getSpectrumLoad(arch);
				}
			}
			else
			{
				lightArch = arch;
				min_load = getSpectrumLoad(arch);
			}
		}
		return lightArch;
	}

	/**
	 * Return the light loaded archiver for image data format
	 * @param deviceDefinedAttributeToDedicatedArchiver 
	 *
	 * @return the light loaded archiver for image data format
	 */
	private String findLightImageArchiver()
	{
		String lightArch = "";
		int min_load = -1;
		Enumeration archivers = _lblist.keys();
		while ( archivers.hasMoreElements() )
		{
            String arch = ( String ) archivers.nextElement();
            /*Archiver nextVal = new Archiver ( arch );
            nextVal.load ();*/
            Archiver nextVal;
            try 
            {
                nextVal = Archiver.findArchiver ( arch , historic );
            } 
            catch (ArchivingException e) 
            {
                e.printStackTrace();
                continue;
            }
            
            if ( nextVal != null && nextVal.isDedicated () )
            {
                continue;
            }
            
			if ( !lightArch.equals("") )
			{
				if ( min_load > getImageLoad(arch) )
				{
					lightArch = arch;
					min_load = getImageLoad(arch);
				}
			}
			else
			{
				lightArch = arch;
				min_load = getImageLoad(arch);
			}
		}
		return lightArch;
	}

	/**
	 * Add the given attribute to the LoadBalancedList
	 *
	 * @param att         the attribute name
	 * @param data_format the data format of the given attribute
	 * @param deviceDefinedAttributeToDedicatedArchiver 
	 * @throws ArchivingException 
	 */
	public void addAtt2LBL(String att , int data_format) throws ArchivingException
	{
        String archiverName = findLightArchiver(data_format);
        //System.out.println ( "LoadBalancedList/addAtt2LBL/archiverName|" + archiverName + "|" );
        if ( archiverName == null || archiverName.equals ( "" ) )
        {
            String message = GlobalConst.ARCHIVING_ERROR_PREFIX;
            String reason = GlobalConst.NO_ARC_EXCEPTION;
            String desc = "Failed while executing ArchivingManagerApi.addAtt2LBL() method...";
            desc += "can't find a non-dedicated archiver for attribute|"+att+"|";
            
            throw new ArchivingException(message , reason , ErrSeverity.WARN , desc , "");
        }
        addAtt2Archiver(att , archiverName , data_format);
	}

    /**
     * forces to assign an attribute to an archiver
     * @param att         the attribute name
     * @param arch        the archiver name
     * @param data_format the data format of the given attribute
     */
    public void forceAddAtt2Archiver(String att , String arch , int data_format)
    {
		//System.out.println ( "CLA/forceAddAtt2Archiver/att|"+att+"|arch|"+arch+"|");
        addAtt2Archiver(att , arch , data_format);
    }

	private void addAtt2Archiver(String att , String arch , int data_format)
	{
        //System.out.println ( "CLA/addAtt2Archiver/0");
        if ( _lblist.get(arch)==null )
        {
            addArchiver ( arch , 0 , 0 , 0 );
        }
        
        switch ( data_format )
		{
			case AttrDataFormat._SCALAR:
                ( ( LoadBalancedArchiver ) _lblist.get(arch) ).addScAttribute(att);
				break;
			case AttrDataFormat._SPECTRUM:
				( ( LoadBalancedArchiver ) _lblist.get(arch) ).addSpAttribute(att);
				break;
			case AttrDataFormat._IMAGE:
				( ( LoadBalancedArchiver ) _lblist.get(arch) ).addImAttribute(att);
				break;
		}
	}

	private int getScalarLoad(String arch)
	{
		return ( ( LoadBalancedArchiver ) _lblist.get(arch) ).get_scalarLoad();
	}

	private int getSpectrumLoad(String arch)
	{
		return ( ( LoadBalancedArchiver ) _lblist.get(arch) ).get_spectrumLoad();
	}

	private int getImageLoad(String arch)
	{
		return ( ( LoadBalancedArchiver ) _lblist.get(arch) ).get_imageLoad();
	}

	/**
	 * Returns the attribute list managed by the given archiver
	 *
	 * @param archiver the archiver name
	 * @return the attribute list managed by the given archiver
	 */
	public String[] getArchiverAssignedAtt(String archiver)
	{
		return ( ( LoadBalancedArchiver ) _lblist.get(archiver) ).getAssignedAttributes();
	}

	/**
	 * Returns the list of archivers used in this LoadBanlancedList object
	 *
	 * @return the list of archivers used in this LoadBanlancedList object
	 */
	public Enumeration getArchivers()
	{
		return _lblist.keys();
	}

}
