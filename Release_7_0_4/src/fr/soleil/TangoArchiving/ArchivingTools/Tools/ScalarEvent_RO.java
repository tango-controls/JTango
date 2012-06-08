//+======================================================================
// $Source$
//
// Project:      Tango Archiving Service
//
// Description:  Java source code for the class  ScalarEvent_RO.
//						(Chinkumo Jean) - Mar 10, 2004
//
// $Author$
//
// $Revision$
//
// $Log$
// Revision 1.5  2006/10/31 16:54:23  ounsy
// milliseconds and null values management
//
// Revision 1.4  2006/03/10 11:31:00  ounsy
// state and string support
//
// Revision 1.3  2005/11/29 17:11:17  chinkumo
// no message
//
// Revision 1.2.16.2  2005/11/15 13:34:38  chinkumo
// no message
//
// Revision 1.2.16.1  2005/09/09 08:40:09  chinkumo
// The management of the boolean type was added.
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

public class ScalarEvent_RO extends ArchivingEvent
{

	/**
	 * Creates a new instance of DhdbEvent
	 */
	public ScalarEvent_RO()
	{
		super();
	}

	/**
	 * Creates a new instance of DhdbEvent
	 */
	public ScalarEvent_RO(String[] hdbScalarEvent_RO)
	{
		this.setAttribute_complete_name(hdbScalarEvent_RO[ 0 ]);
		this.setTimeStamp(Long.parseLong(hdbScalarEvent_RO[ 1 ]));
		this.setScalarValue(Double.parseDouble(hdbScalarEvent_RO[ 2 ]));
	}

	public void setScalarValue(double value)
	{
		setValue(new Double(value));
	}

	public void setScalarValue(boolean value)
	{
		setValue(new Boolean(value));
	}

    public void setScalarValue(String value)
    {
        setValue(new String(value));
    }

	public Double getScalarValue()
	{
		return (Double)getValue();
	}

	public Boolean getScalarValueB()
	{
		return ( Boolean ) getValue();
	}

    public String getScalarValueS()
    {
        return ( String ) getValue();
    }

	/**
	 * Returns an array representation of the object <I>ArchivingEvent</I>.
	 *
	 * @return an array representation of the object <I>ArchivingEvent</I>.
	 */
	public String[] toArray()
	{
		//double value = ( ( Double ) getValue() ).doubleValue();
		String[] hdbScalarEvent_RO = new String[ 3 ];
		hdbScalarEvent_RO[ 0 ] = getAttribute_complete_name().trim(); //	name
		hdbScalarEvent_RO[ 1 ] = Long.toString(getTimeStamp()).trim(); //	time
        if (getValue() instanceof Double)
        {
            hdbScalarEvent_RO[ 2 ] = Double.toString( ( ( Double ) getValue() ).doubleValue() ).trim(); // value
        }
        else if (getValue() instanceof String)
        {
            hdbScalarEvent_RO[ 2 ] = ( (String)getValue() ).trim(); // value
        }
        else
        {
            hdbScalarEvent_RO[ 2 ] = getValue().toString().trim(); // value
        }
		return hdbScalarEvent_RO;
	}

	public String toString()
	{
		String hdbScalarEvent_RO_String = "";

		hdbScalarEvent_RO_String =
		"Source : \t" + getAttribute_complete_name() + "\r\n" +
		"TimeSt : \t" + getTimeStamp() + "\r\n" +
		"Value : \t" + getValue() + "\r\n";

		return hdbScalarEvent_RO_String;
	}
}

