package fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.test;


import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dao.ValDAO;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.CompositeId;
import fr.soleil.TangoSnapshoting.SnapshotingApi.persistence.spring.dto.Sp1Val;

public class Sp1ValDAOTest extends AbstractValDAOTest <Sp1Val>  
{
    protected void compare(Sp1Val original, Sp1Val copy) 
    {
        super.compare ( original , copy );
        super.assertEquals ( original.getValue () , copy.getValue () );
        super.assertEquals ( original.getDimX (), copy.getDimX () );
    }
    
    protected Sp1Val buildLine() 
    {
        Sp1Val line = new Sp1Val ();
        
        CompositeId compositeId = new CompositeId ();
        int idAtt = 999;
        int idSnap = 888;
        compositeId.setIdAtt(idAtt);
        compositeId.setIdSnap(idSnap);
        line.setCompositeId(compositeId);
        
        String value = "A,B,C";
        line.setValue(value);
        
        line.setDimX(3);
        
        return line;
    }

    /**
     * @param scNum1ValDAO the scNum1ValDAO to set
     */
    public void setSp1ValDAO(ValDAO<Sp1Val> scNum1ValDAO) 
    {
        super.dao = scNum1ValDAO;
    }

    @Override
    protected Sp1Val[] buildEmptyLines(int numberOfLines) 
    {   
        return new Sp1Val [ numberOfLines ];
    }
}
