package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.hibernate.SessionFactory;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum2Val;

public class ScNum2ValDAOImpl extends AbstractValDAO <ScNum2Val> 
{
    public ScNum2ValDAOImpl(SessionFactory sessionFactory) 
    {
        super (sessionFactory);
    }
    
    @Override
    protected Class<ScNum2Val> getValueClass() 
    {
        return ScNum2Val.class;
    }
}
