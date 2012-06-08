package fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators;

import java.util.Comparator;

import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ArchivingAttribute;
import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ControlResultLine;

public class ControlResultLineComparator implements Comparator 
{

    public ControlResultLineComparator() 
    {
        super();
    }

    public int compare(Object obj1, Object obj2) 
    {
        int ret;
        
        if ( obj1 == null )
        {
            if ( obj2 != null )
            {
                ret = 1;
            }
            else
            {
                ret = 0;
            }
        }
        else if ( obj2 == null )
        {
            ret = -1;
        }
        else
        {
            ret = compareNotNull ( obj1 , obj2 );
        }
        return ret;
    }

    private int compareNotNull(Object obj1, Object obj2) 
    {
        if ( obj1 instanceof ControlResultLine )
        {
            ControlResultLine line1 = (ControlResultLine) obj1;
            ControlResultLine line2 = (ControlResultLine) obj2;
            
            ArchivingAttribute attr1 = line1.getAttribute();
            ArchivingAttribute attr2 = line2.getAttribute();
            
            return compare ( attr1 , attr2 );
        }
        else if ( obj1 instanceof ArchivingAttribute )
        {
            ArchivingAttribute attr1 = (ArchivingAttribute) obj1;
            ArchivingAttribute attr2 = (ArchivingAttribute) obj2;
            
            String firstCriterion = attr1.getCompleteName();
            String secondCriterion = attr2.getCompleteName ();
            
            return this.compareStrings ( firstCriterion , secondCriterion );
        }
        else
        {
            return 0;
        }
    }
    
    public int compareStrings(String name1, String name2) 
    {
        int ret;
        
        if ( name1 == null )
        {
            if ( name2 != null )
            {
                ret = 1;
            }
            else
            {
                ret = 0;
            }
        }
        else if ( name2 == null )
        {
            ret = -1;
        }
        else
        {
            name1 = name1.toLowerCase ();
            name2 = name2.toLowerCase ();
            
            ret = name1.compareTo(name2);
        }
        
        return ret;
    }

}
