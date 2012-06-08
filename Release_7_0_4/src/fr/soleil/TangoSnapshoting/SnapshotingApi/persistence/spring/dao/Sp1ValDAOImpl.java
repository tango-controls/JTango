package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.hibernate.SessionFactory;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp1Val;

public class Sp1ValDAOImpl extends AbstractValDAO <Sp1Val> 
{
    public Sp1ValDAOImpl(SessionFactory sessionFactory) 
    {
        super (sessionFactory);
    }
    
    @Override
    protected Class<Sp1Val> getValueClass() 
    {
        return Sp1Val.class;
    }
}
