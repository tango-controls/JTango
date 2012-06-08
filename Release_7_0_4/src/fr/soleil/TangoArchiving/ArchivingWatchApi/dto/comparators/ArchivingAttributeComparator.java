package fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ArchivingAttribute;

public class ArchivingAttributeComparator implements Comparator
{
    private Comparator referenceComparator;

    public ArchivingAttributeComparator ()
    {
        referenceComparator = Collator.getInstance(Locale.FRENCH);
    }

    public int compare (Object o1, Object o2)
    {
        boolean doCompare = false;
        if (o1 != null && o2 != null)
        {
            if (o1 instanceof ArchivingAttribute && o2 instanceof ArchivingAttribute)
            {
                doCompare = true;
            }
        }
        
        if ( doCompare )
        {
            String name1 = ((ArchivingAttribute) o1).getCompleteName ();
            String name2 = ((ArchivingAttribute) o2).getCompleteName ();
            return referenceComparator.compare( name1, name2 );            
        }
        else
        {
            return 0;
        }
    }
}