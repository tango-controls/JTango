package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.context.SnapshotPersistenceContext;
import fr.soleil.actiongroup.collectiveaction.onattributes.plugin.persistance.AnyAttribute;

public class ImVal extends Val //extend Val instead of SpVal, even if both have dimX in common, because the values are built differently 
{
    private int dimX;
    private int dimY;
    
    public ImVal ()
    {
       
    }
    
    public ImVal(AnyAttribute attribute, SnapshotPersistenceContext context) 
    {
        super ( attribute , context ) ;
        
        this.dimX = attribute.getDimX();
        this.dimY = attribute.getDimY();
    }

    /**
     * @return the dimX
     */
    public int getDimX() {
        return this.dimX;
    }

    /**
     * @param dimX the dimX to set
     */
    public void setDimX(int dimX) {
        this.dimX = dimX;
    }

    /**
     * @return the dimY
     */
    public int getDimY() {
        return this.dimY;
    }

    /**
     * @param dimY the dimY to set
     */
    public void setDimY(int dimY) {
        this.dimY = dimY;
    }
}
