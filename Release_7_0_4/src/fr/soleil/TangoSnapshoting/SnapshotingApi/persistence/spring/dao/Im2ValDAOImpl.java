package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.hibernate.SessionFactory;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Im2Val;

public class Im2ValDAOImpl extends AbstractValDAO <Im2Val> 
{
    public Im2ValDAOImpl(SessionFactory sessionFactory) 
    {
        super (sessionFactory);
    }
    
    @Override
    protected Class<Im2Val> getValueClass() 
    {
        return Im2Val.class;
    }
}
