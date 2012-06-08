package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto;

import java.util.Map;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.context.SnapshotPersistenceContext;
import fr.soleil.actiongroup.collectiveaction.onattributes.plugin.persistance.AnyAttribute;

public class Val 
{
    private CompositeId compositeId;
    
    public Val ()
    {
        this.compositeId = new CompositeId ();
    }

    public Val(AnyAttribute attribute, SnapshotPersistenceContext context) 
    {
        this ();
        
        int snapId = context.getSnapId ();
        Map<String, Integer> attributeIds = context.getAttributeIds ();
        int attributeId = attributeIds.get ( attribute.getCompleteName () );
        
        this.compositeId.setIdSnap(snapId);
        this.compositeId.setIdAtt(attributeId);
    }

    /**
     * @return the compositeId
     */
    public CompositeId getCompositeId() {
        return this.compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(CompositeId compositeId) {
        this.compositeId = compositeId;
    }
}
