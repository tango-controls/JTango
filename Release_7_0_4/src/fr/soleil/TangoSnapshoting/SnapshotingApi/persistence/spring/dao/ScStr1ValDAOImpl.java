package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.hibernate.SessionFactory;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.ScStr1Val;

public class ScStr1ValDAOImpl extends AbstractValDAO <ScStr1Val> 
{
    public ScStr1ValDAOImpl(SessionFactory sessionFactory) 
    {
        super (sessionFactory);
    }
    
    @Override
    protected Class<ScStr1Val> getValueClass() 
    {
        return ScStr1Val.class;
    }
}
