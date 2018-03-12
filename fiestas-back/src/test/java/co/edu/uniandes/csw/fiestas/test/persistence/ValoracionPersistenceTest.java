package co.edu.uniandes.csw.fiestas.test.persistence;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.persistence.ValoracionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
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
public class ValoracionPersistenceTest {
    
   @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ValoracionEntity.class.getPackage())
                .addPackage(ValoracionPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    
    @Inject
    private ValoracionPersistence valoracionPersistence;

   
    @PersistenceContext
    private EntityManager em;

  
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     *
     * 
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
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

    private List<ValoracionEntity> data = new ArrayList<ValoracionEntity>();
    private List<ServicioEntity> dataServicio = new ArrayList<ServicioEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     * 
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            em.persist(entity);
            dataServicio.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            ValoracionEntity entity = factory.manufacturePojo(ValoracionEntity.class);
            if (i == 0) {
                entity.setServicio(dataServicio.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Valoracion.
     *
     * 
     */
    @Test
    public void createValoracionTest() {

        PodamFactory factory = new PodamFactoryImpl();
        ValoracionEntity newEntity = factory.manufacturePojo(ValoracionEntity.class);
        ValoracionEntity result = valoracionPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ValoracionEntity entity = em.find(ValoracionEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCalificacion(), entity.getCalificacion());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    }

    /**
     * Prueba para consultar un Valoracion.
     *
     * 
     */
    @Test
    public void getValoracionTest() {
        ValoracionEntity entity = data.get(0);
        ValoracionEntity newEntity = valoracionPersistence.find(dataServicio.get(0).getId(), entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getCalificacion(), newEntity.getCalificacion());
        Assert.assertEquals(entity.getComentario(), newEntity.getComentario());
    }

    /**
     * Prueba para eliminar un Valoracion.
     *
     * 
     */
    @Test
    public void deleteValoracionTest() {
        ValoracionEntity entity = data.get(0);
        valoracionPersistence.delete(entity.getId());
        ValoracionEntity deleted = em.find(ValoracionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Valoracion.
     *
     * 
     */
    @Test
    public void updateValoracionTest() {
        ValoracionEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ValoracionEntity newEntity = factory.manufacturePojo(ValoracionEntity.class);

        newEntity.setId(entity.getId());

        valoracionPersistence.update(newEntity);

        ValoracionEntity resp = em.find(ValoracionEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getCalificacion(), resp.getCalificacion());
        Assert.assertEquals(newEntity.getComentario(), resp.getComentario());
    } 
}
