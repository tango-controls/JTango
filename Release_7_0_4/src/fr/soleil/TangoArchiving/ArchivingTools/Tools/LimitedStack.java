package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import java.util.Enumeration;
import java.util.Vector;

import fr.soleil.TangoArchiving.ArchivingTools.Diary.ILogger;

/**
 * A stack used to store the times at which a control step was executed.
 * Thus, after completion of a full cycle, the top element is the date of the last step,  
 * while the bottom element is the date of the first step.
 * This is used to keep track of when a ControlResult has started and ended.
 * @author CLAISSE 
 */
public class LimitedStack extends Vector
{
    private static final int MAX_SIZE = 5;
    
    /**
     * Default constructor
     */
    public LimitedStack ()
    {
        super();
    }

    /**
     * Adds a date on top of the stack
     * @param item The new date
     * @return The inserted element
     */
    public Long push ( Long item )
    {
        super.removeElement( item );

        if ( this.isFull () )
        {
            super.remove( super.lastElement() );
        }

        super.insertElementAt( item , 0 );
        return item;
    }

    private boolean isFull() 
    {
        return super.size () >= MAX_SIZE;
    }

    public void log() 
    {
        Enumeration enumer = super.elements ();
        System.out.println ( "LimitedStack/log-----IN" );
        while ( enumer.hasMoreElements () )
        {
            Long next = (Long) enumer.nextElement ();
            System.out.println ( "        next/"+next );
        }
        System.out.println ( "LimitedStack/log----OUT" );
    }

    public boolean containsDate ( long date , ILogger logger )  
    {
        /*Long dateL = new Long ( date );
        return super.contains ( dateL );*/
        
        //System.out.println ( "   containsDate/date/"+date+"--------------------------");
        Enumeration enumer = super.elements ();
        while ( enumer.hasMoreElements () )
        {
            Long nextOld = (Long) enumer.nextElement ();
            //System.out.println ( "containsDate/nextOld/"+nextOld);
            boolean matchFound = DBTools.areTimestampsEqual ( date , nextOld.longValue () , logger );
            if ( matchFound )
            {
                return true;
            }
        }
        //System.out.println ( "containsDate/----------------------------------------");
        return false;
    }
}
