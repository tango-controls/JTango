package fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.Archiver;

public class ArchiversComparator implements Comparator
{
    private Comparator referenceComparator;

    public ArchiversComparator ()
    {
        referenceComparator = Collator.getInstance(Locale.FRENCH);
    }

    public int compare (Object o1, Object o2)
    {
        boolean doCompare = false;
        if (o1 != null && o2 != null)
        {
            if (o1 instanceof Archiver && o2 instanceof Archiver)
            {
                doCompare = true;
            }
        }
        
        if ( doCompare )
        {
            String name1 = ((Archiver) o1).getName ();
            String name2 = ((Archiver) o2).getName ();
            return referenceComparator.compare( name1, name2 );            
        }
        else
        {
            return 0;
        }
    }
}