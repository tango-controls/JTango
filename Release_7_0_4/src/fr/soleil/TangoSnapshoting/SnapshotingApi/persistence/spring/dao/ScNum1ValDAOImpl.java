package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.hibernate.SessionFactory;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScNum1Val;

public class ScNum1ValDAOImpl extends AbstractValDAO <ScNum1Val> 
{
    public ScNum1ValDAOImpl(SessionFactory sessionFactory) 
    {
        super (sessionFactory);
    }
    
    @Override
    protected Class<ScNum1Val> getValueClass() 
    {
        return ScNum1Val.class;
    }
}
