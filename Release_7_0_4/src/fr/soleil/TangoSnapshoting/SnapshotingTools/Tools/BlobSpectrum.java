//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  BlobSpectrum.
//						(Chinkumo Jean) - Mar 26, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.4  2007/04/05 10:16:23  ounsy
// minor changes
//
// Revision 1.3  2006/01/27 13:04:44  ounsy
// organised imports
//
// Revision 1.2  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.1.16.1  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.1  2005/01/26 15:35:37  chinkumo
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

package fr.soleil.TangoSnapshoting.SnapshotingTools.Tools;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;

public class BlobSpectrum implements Clob
{
	public long length() throws SQLException
	{
		return 0;
	}

	public String getSubString(long pos , int length) throws SQLException
	{
		return null;
	}

	public Reader getCharacterStream() throws SQLException
	{
		return null;
	}

	public InputStream getAsciiStream() throws SQLException
	{
		return null;
	}

	public long position(String searchstr , long start) throws SQLException
	{
		return 0;
	}

	public long position(Clob searchstr , long start) throws SQLException
	{
		return 0;
	}

	public int setString(long pos , String str) throws SQLException
	{
		return 0;
	}

	public int setString(long pos , String str , int offset , int len) throws SQLException
	{
		return 0;
	}

	public OutputStream setAsciiStream(long pos) throws SQLException
	{
		return null;
	}

	public Writer setCharacterStream(long pos) throws SQLException
	{
		return null;
	}

	public void truncate(long len) throws SQLException
	{
	}

    public void free() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    public Reader getCharacterStream(long arg0, long arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
