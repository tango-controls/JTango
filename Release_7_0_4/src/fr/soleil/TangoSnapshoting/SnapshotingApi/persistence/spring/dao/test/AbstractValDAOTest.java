package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;


import org.junit.Test;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.SnapshotPersistenceManager;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.SnapshotPersistenceManagerFactory;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.ValDAO;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Val;

public abstract class AbstractValDAOTest <V extends Val> extends AbstractTransactionalDataSourceSpringContextTests  
{
    protected ValDAO <V> dao;    
    
    protected AbstractValDAOTest ()
    {
        super.setAutowireMode ( AUTOWIRE_BY_NAME );//This means the names of the daughter classes' DAO setters have to be the same bean names as in beans.xml
    }
    
    //Specifies the Spring configuration to load for this test fixture
    protected String[] getConfigLocations() 
    {
        SnapshotPersistenceManagerFactory factory = SnapshotPersistenceManagerFactory.getInstance ();
        SnapshotPersistenceManager manager = factory.getManager ();//will use the default resource name
        return new String[] { "classpath:" + manager.getResourceName () };
    }
    
    @Test 
    public void testInsert ()
    {
        V[] lines = this.buildLines ();
        for ( int i = 0 ; i < lines.length ; i ++ )
        {
            dao.create ( lines [ i ] );  
            V inDB = dao.findByKey ( lines [ i ].getCompositeId() );
            
            this.compare ( lines [ i ] , inDB );
        }
    }

    protected void compare(V original, V copy) 
    {
        super.assertNotNull ( copy );
    }

    protected V[] buildLines() 
    {
        int numberOfLines = 1;
        V [] lines = this.buildEmptyLines ( numberOfLines );
        
        V line = this.buildLine ();
        
        for ( int i = 0 ; i < numberOfLines ; i ++ )
        {
            lines [ i ] = line;
        }
        
        return lines;
    }

    protected abstract V buildLine ();
    protected abstract V[] buildEmptyLines ( int numberOfLines );
}
