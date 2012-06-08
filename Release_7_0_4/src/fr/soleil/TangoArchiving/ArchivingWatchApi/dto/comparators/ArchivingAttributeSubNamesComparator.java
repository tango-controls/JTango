package fr.soleil.TangoArchiving.ArchivingWatchApi.dto.comparators;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import fr.soleil.TangoArchiving.ArchivingWatchApi.dto.ArchivingAttributeSubName;

public class ArchivingAttributeSubNamesComparator implements Comparator
{
    private Comparator referenceComparator;

    public ArchivingAttributeSubNamesComparator ()
    {
        referenceComparator = Collator.getInstance(Locale.FRENCH);
    }

    public int compare (Object o1, Object o2)
    {
        boolean doCompare = false;
        if (o1 != null && o2 != null)
        {
            if (o1 instanceof ArchivingAttributeSubName && o2 instanceof ArchivingAttributeSubName)
            {
                doCompare = true;
            }
        }
        
        if ( doCompare )
        {
            String name1 = ((ArchivingAttributeSubName) o1).getName ();
            String name2 = ((ArchivingAttributeSubName) o2).getName ();
            return referenceComparator.compare( name1, name2 );            
        }
        else
        {
            return 0;
        }
    }
}