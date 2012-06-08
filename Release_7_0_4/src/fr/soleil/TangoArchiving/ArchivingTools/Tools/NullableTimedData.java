package fr.soleil.TangoArchiving.ArchivingTools.Tools;

import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevError;

public class NullableTimedData
{
    public Long        time      = null;
    public Object[]    value     = null;
    public AttrQuality qual      = AttrQuality.ATTR_VALID;
    public int         x         = 0;
    public int         y         = 0;
    public int         data_type = -1;
    public DevError[]  err       = null;

    public NullableTimedData ()
    {
        super();
    }
}
