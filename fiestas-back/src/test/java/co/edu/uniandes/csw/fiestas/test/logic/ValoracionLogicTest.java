package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ValoracionLogic;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
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
    private ValoracionLogic valoracionLogic;

    
    @PersistenceContext
    private EntityManager em;

 
    @Inject
    private UserTransaction utx;

  
    private List<ValoracionEntity> data = new ArrayList<ValoracionEntity>();

    private List<ServicioEntity> dataServicio = new ArrayList<ServicioEntity>();

    
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
        em.createQuery("delete from ServicioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     * 
     */
    private void insertData() {

        
        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            em.persist(entity);
            dataServicio.add(entity);
        }
        
        for (int i = 0; i < 3; i++) {
            ValoracionEntity entity = factory.manufacturePojo(ValoracionEntity.class);
            
            entity.setServicio(dataServicio.get(1));
            
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Valoracion
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createValoracionTest() throws BusinessLogicException {
        ValoracionEntity newEntity = factory.manufacturePojo(ValoracionEntity.class);
        ValoracionEntity result = valoracionLogic.createValoracion(data.get(0).getServicio().getId(), newEntity);
        Assert.assertNotNull(result);
        ValoracionEntity entity = em.find(ValoracionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCalificacion(), entity.getCalificacion());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    }

    /**
     * Prueba para consultar la lista de Valoraciones
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    
    @Test
    public void getValoracionesTest() throws BusinessLogicException {
        List<ValoracionEntity> list = valoracionLogic.getValoraciones(dataServicio.get(1).getId());        
        Assert.assertEquals(data.size(), list.size());
        for (ValoracionEntity entity : list) {
            boolean found = false;
            for (ValoracionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     
    /**
     * Prueba para consultar un Valoracion
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    
    @Test
    public void getValoracionTest() throws BusinessLogicException {
        ValoracionEntity entity = data.get(0);
        ValoracionEntity resultEntity = valoracionLogic.getValoracion(dataServicio.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCalificacion(), resultEntity.getCalificacion());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
    }

    /**
     * Prueba para eliminar un Valoracion
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
 
    @Test
    public void deleteValoracionTest() throws BusinessLogicException {
        ValoracionEntity entity = data.get(0);
        valoracionLogic.deleteValoracion(dataServicio.get(1).getId(), entity.getId());
        ValoracionEntity deleted = em.find(ValoracionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Valoracion
     *
     * 
     */
 @Test
    public void updateValoracionTest() {
        ValoracionEntity entity = data.get(0);
        ValoracionEntity pojoEntity = factory.manufacturePojo(ValoracionEntity.class);

        pojoEntity.setId(entity.getId());

        valoracionLogic.updateValoracion(dataServicio.get(1).getId(), pojoEntity);

        ValoracionEntity resp = em.find(ValoracionEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCalificacion(), resp.getCalificacion());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
    }
}
