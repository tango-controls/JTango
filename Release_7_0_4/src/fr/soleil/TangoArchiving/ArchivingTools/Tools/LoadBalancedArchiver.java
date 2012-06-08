//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  LoadBalancedArchiver.
//						(Chinkumo Jean) - Jun 6, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2006/04/11 09:10:45  ounsy
// double archiving protection
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
// Revision 1.1.4.1  2005/06/13 15:06:51  chinkumo
// Methods were commented.
//
// Revision 1.1  2005/03/07 18:19:39  chinkumo
// This class was added to manage the load balancing when distributing attributes on archivers
//
// Revision 1.2  2005/01/26 15:35:37  chinkumo
// Ultimate synchronization before real sharing.
//
// Revision 1.1  2004/12/06 17:39:56  chinkumo
// First commit (new API architecture).
//
//
// copyleft :	Synchrotron SOLEIL
//					L'Orme des Merisiers
//					Saint-Aubin - BP 48
//					91192 GIF-sur-YVETTE CEDEX
//
//-======================================================================

package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import java.util.Vector;

public class LoadBalancedArchiver
{
	private int _scalarLoad;
	private int _spectrumLoad;
	private int _imageLoad;
	private Vector _assignedAttributes;

	/**
	 * Initialize an empty LoadBalancedArchiver object
	 */
	public LoadBalancedArchiver()
	{
		_scalarLoad = 0;
		_spectrumLoad = 0;
		_imageLoad = 0;
		_assignedAttributes = new Vector();
	}

	/**
	 * Initialize a LoadBalancedArchiver object with the given load informations
	 *
	 * @param _scalarLoad   scalar attribute load
	 * @param _spectrumLoad spectrum attribute load
	 * @param _imageLoad    image attribute load
	 */
	public LoadBalancedArchiver(int _scalarLoad , int _spectrumLoad , int _imageLoad)
	{
		this._scalarLoad = _scalarLoad;
		this._spectrumLoad = _spectrumLoad;
		this._imageLoad = _imageLoad;
		_assignedAttributes = new Vector();
	}

	/**
	 * Return the image load
	 *
	 * @return the image load
	 */
	public int get_imageLoad()
	{
		return _imageLoad;
	}

	/**
	 * Set the image load
	 *
	 * @param _imageLoad the image load
	 */
	public void set_imageLoad(int _imageLoad)
	{
		this._imageLoad = _imageLoad;
	}

	/**
	 * Return the scalar load
	 *
	 * @return
	 */
	public int get_scalarLoad()
	{
		return _scalarLoad;
	}

	/**
	 * Set the scalar load
	 *
	 * @param _scalarLoad the scalar load
	 */
	public void set_scalarLoad(int _scalarLoad)
	{
		this._scalarLoad = _scalarLoad;
	}

	/**
	 * Return the spectrum load
	 *
	 * @return
	 */
	public int get_spectrumLoad()
	{
		return _spectrumLoad;
	}

	/**
	 * Set the spectrum load
	 *
	 * @param _spectrumLoad the spectrum load
	 */
	public void set_spectrumLoad(int _spectrumLoad)
	{
		this._spectrumLoad = _spectrumLoad;
	}

	/**
	 * Assign the given scalar attribute to the LoadBalancedArchiver
	 *
	 * @param att the given scalar attribute
	 */
	public void addScAttribute(String att)
	{
        String toAdd = new String(att);
        if (! _assignedAttributes.contains(toAdd))
        {
            _assignedAttributes.add(toAdd);
            _scalarLoad++;
        }
	}

	/**
	 * Assign the given spectrum attribute to the LoadBalancedArchiver
	 *
	 * @param att the given spectrum attribute
	 */
	public void addSpAttribute(String att)
	{
        String toAdd = new String(att);
        if (! _assignedAttributes.contains(toAdd))
        {
            _assignedAttributes.add(toAdd);
            _spectrumLoad++;
        }
	}

	/**
	 * Assign the given image attribute to the LoadBalancedArchiver
	 *
	 * @param att the given image attribute
	 */
	public void addImAttribute(String att)
	{
        String toAdd = new String(att);
        if (! _assignedAttributes.contains(toAdd))
        {
            _assignedAttributes.add(toAdd);
            _imageLoad++;
        }
	}

	/**
	 * Return the assigned attribute list
	 *
	 * @return the assigned attribute list
	 */
	public String[] getAssignedAttributes()
	{
		String[] res = new String[ _assignedAttributes.size() ];
		for ( int i = 0 ; i < _assignedAttributes.size() ; i++ )
		{
			res[ i ] = ( String ) _assignedAttributes.elementAt(i);
		}
		return res;
	}

	public String toString()
	{
		StringBuffer archiverLoadStr = new StringBuffer();
		archiverLoadStr.append("Scalar Load   = ").append(_scalarLoad).append("\r\n");
		archiverLoadStr.append("Spectrum Load = ").append(_spectrumLoad + "\r\n");
		archiverLoadStr.append("Image Load    = ").append(_imageLoad + "\r\n");
		return archiverLoadStr.toString();
	}
}
