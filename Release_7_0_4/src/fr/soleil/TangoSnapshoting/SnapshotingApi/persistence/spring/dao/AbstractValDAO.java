package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.CompositeId;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Val;

public abstract class AbstractValDAO <V extends Val> implements ValDAO <V>
{
    protected HibernateTemplate hibernateTemplate;
    
    public AbstractValDAO(SessionFactory sessionFactory) 
    {
        this.hibernateTemplate = new HibernateTemplate ( sessionFactory );
    }
    
    public V create(V line) 
    {
        hibernateTemplate.save ( line );
        //throw new RuntimeException(); Uncomment to test correct rollback management 
        return line;
    }
    
    public V findByKey ( CompositeId compositeId ) 
    {
        Class<V> valueClass = this.getValueClass ();
        Object res = hibernateTemplate.get(valueClass, compositeId);
        V line = valueClass.cast(res);
        return line;
    }

    protected abstract Class<V> getValueClass();
}
