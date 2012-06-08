package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.TangoDs.TangoConst;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.context.SnapshotPersistenceContext;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.ClasspathDAOBeansLoader;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.DAOBeansLoader;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Im1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Im2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr2Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp1Val;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp2Val;
import fr.soleil.actiongroup.collectiveaction.onattributes.plugin.context.PersistenceContext;
import fr.soleil.actiongroup.collectiveaction.onattributes.plugin.persistance.AnyAttribute;

public class SpringSnapshotPersistenceManagerImpl implements SnapshotPersistenceManager 
{
    private DAOBeansLoader beans;
    
    SpringSnapshotPersistenceManagerImpl ( String beansFileName )
    {
        this.beans = new ClasspathDAOBeansLoader ( beansFileName );
    }
    
    public void store(AnyAttribute attribute, PersistenceContext persistenceContext) throws Exception 
    {
        SnapshotPersistenceContext context = (SnapshotPersistenceContext) persistenceContext;
        
        switch ( attribute.getFormat () )//a remplacer par un AnyAttribute.getManager si possible??
        {
            case AttrDataFormat._SCALAR:
                if ( attribute.getWritable () == AttrWriteType._READ )
                {
                    if ( attribute.getType () == TangoConst.Tango_DEV_STRING )
                    {
                        beans.getScStr1ValDAO().create ( new ScStr1Val ( attribute , context ) );
                    }
                    else
                    {
                        beans.getScNum1ValDAO().create ( new ScNum1Val ( attribute , context ) );
                    }
                }
                else
                {
                    if ( attribute.getType () == TangoConst.Tango_DEV_STRING )
                    {
                        beans.getScStr2ValDAO().create ( new ScStr2Val ( attribute , context ) );
                    }
                    else
                    {
                        beans.getScNum2ValDAO().create ( new ScNum2Val ( attribute , context ) );
                    }
                }
            break;
            
            case AttrDataFormat._SPECTRUM:
                if ( attribute.getWritable () == AttrWriteType._READ )
                {
                    beans.getSp1ValDAO().create ( new Sp1Val ( attribute , context ) );
                }
                else
                {
                    beans.getSp2ValDAO().create ( new Sp2Val ( attribute , context ) );
                }
                
            break;
            
            case AttrDataFormat._IMAGE:
                if ( attribute.getWritable () == AttrWriteType._READ )
                {
                    beans.getIm1ValDAO().create ( new Im1Val ( attribute , context ) );
                }
                else
                {
                    beans.getIm2ValDAO().create ( new Im2Val ( attribute , context ) );
                }
            break;
        }
    }
    
    /**
     * @return the beansFileName
     */
    public String getResourceName() 
    {
        return this.beans.getResourceName();
    }
}
