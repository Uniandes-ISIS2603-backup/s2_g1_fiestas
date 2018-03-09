package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ValoracionLogic;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ValoracionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 *
 * @author ls.arias
 */
@RunWith(Arquillian.class)
public class ValoracionLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ValoracionLogic ValoracionLogic;

    @PersistenceContext
    private EntityManager em;
      
    @Inject
    private UserTransaction utx;
    
    private List<ValoracionEntity> data = new ArrayList<ValoracionEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ValoracionEntity.class.getPackage())
                .addPackage(ValoracionLogic.class.getPackage())
                .addPackage(ValoracionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
     /**
     * Configuración inicial de la prueba.
     *
     * 
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     * 
     */
    private void clearData() {
        em.createQuery("delete from ValoracionEntity").executeUpdate();
    }
    
     /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     * 
     */
    private void insertData() {    
        for (int i = 0; i < 3; i++) {
            ValoracionEntity entity = factory.manufacturePojo(ValoracionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
 
    /**
     * Prueba para consultar una Valoracion por su id
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    
    @Test
    public void getValoracionTest() throws BusinessLogicException {
        ValoracionEntity entity = data.get(0);
        ValoracionEntity resultEntity = ValoracionLogic.getValoracion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCalificacion(), resultEntity.getCalificacion());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
    }
    
    /**
     * Prueba para eliminar un Valoracion
     *
     * 
     */
    @Test
    public void deleteValoracionTest() {
        ValoracionEntity entity = data.get(0);
        ValoracionLogic.deleteValoracion(entity.getId());
        ValoracionEntity deleted = em.find(ValoracionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
