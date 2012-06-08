package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.hibernate.SessionFactory;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp2Val;

public class Sp2ValDAOImpl extends AbstractValDAO <Sp2Val> 
{
    public Sp2ValDAOImpl(SessionFactory sessionFactory) 
    {
        super (sessionFactory);
    }
    
    @Override
    protected Class<Sp2Val> getValueClass() 
    {
        return Sp2Val.class;
    }
}
