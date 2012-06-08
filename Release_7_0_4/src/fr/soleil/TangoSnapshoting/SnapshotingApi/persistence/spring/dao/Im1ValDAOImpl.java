package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.hibernate.SessionFactory;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Im1Val;

public class Im1ValDAOImpl extends AbstractValDAO <Im1Val> 
{
    public Im1ValDAOImpl(SessionFactory sessionFactory) 
    {
        super (sessionFactory);
    }
    
    @Override
    protected Class<Im1Val> getValueClass() 
    {
        return Im1Val.class;
    }
}
