package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.TematicaLogic;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.TematicaPersistence;
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
 * @author af.losada
 */
@RunWith(Arquillian.class)
public class TematicaLogicTest 
{
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TematicaLogic tematicaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<TematicaEntity> data = new ArrayList<TematicaEntity>();
    
    private List<ServicioEntity> serviciosData = new ArrayList<ServicioEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TematicaEntity.class.getPackage())
                .addPackage(TematicaLogic.class.getPackage())
                .addPackage(TematicaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }

    /**
     * Configuración inicial de la prueba.
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
     */
    private void clearData() {
        em.createQuery("delete from TematicaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     */
    private void insertData() 
    {
        List<ServicioEntity> listaServicio = new ArrayList<ServicioEntity>();
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            em.persist(servicio);
            serviciosData.add(servicio);
            listaServicio.add(servicio);
        }
        for (int i = 0; i < 3; i++) 
        {
            TematicaEntity entity = factory.manufacturePojo(TematicaEntity.class);
            entity.setServicios(listaServicio);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Tematica
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createTematicaTest() throws BusinessLogicException {
        TematicaEntity newEntity = factory.manufacturePojo(TematicaEntity.class);
        TematicaEntity result = tematicaLogic.createTematica(newEntity);
        Assert.assertNotNull(result);
        TematicaEntity entity = em.find(TematicaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    /**
     * Prueba para consultar la lista de Tematicas
     *
     * 
     */
    @Test
    public void getTematicasTest() {
        List<TematicaEntity> list = tematicaLogic.getTematicas();
        Assert.assertEquals(data.size(), list.size());
        for (TematicaEntity entity : list) {
            boolean found = false;
            for (TematicaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Tematica
     *
     * 
     */
    @Test
    public void getTematicaTest() {
        TematicaEntity entity = data.get(0);
        TematicaEntity resultEntity = tematicaLogic.getTematica(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
    }

    /**
     * Prueba para eliminar un Tematic
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void deleteTematicaTest() throws BusinessLogicException {
        TematicaEntity entity = data.get(0);
        tematicaLogic.removeServicio(data.get(0).getId(), entity.getId());
        tematicaLogic.deleteTematica(entity.getId());
        TematicaEntity deleted = em.find(TematicaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Tematica
     *
     * 
     */
    @Test
    public void updateTematicaTest() {
        TematicaEntity entity = data.get(0);
        TematicaEntity pojoEntity = factory.manufacturePojo(TematicaEntity.class);

        pojoEntity.setId(entity.getId());

        tematicaLogic.updateTematica(pojoEntity.getId(),pojoEntity);

        TematicaEntity resp = em.find(TematicaEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
    }

    /**
     * Prueba para obtener una instancia de Servicios asociada a una instancia
     * Tematica
     *
     * 
     */
    @Test
    public void getServiciosTest() 
    {
        TematicaEntity entity = data.get(0);
        ServicioEntity servicioEntity = serviciosData.get(0);
        ServicioEntity response = tematicaLogic.getServicio(entity.getId(), servicioEntity.getId());

        Assert.assertEquals(servicioEntity.getId(), response.getId());
        Assert.assertEquals(servicioEntity.getNombre(), response.getNombre());
        Assert.assertEquals(servicioEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(servicioEntity.getProductos(), response.getProductos());
        Assert.assertEquals(servicioEntity.getProveedores(), response.getProveedores());
        Assert.assertEquals(servicioEntity.getTipo(), response.getTipo());
        Assert.assertEquals(servicioEntity.getValoraciones(), response.getValoraciones());
    }

    /**
     * Prueba para obtener una colección de instancias de Servicios asociadas a una
     * instancia Tematica
     *
     * 
     */
    @Test
    public void listServiciosTest() {
        List<ServicioEntity> list = tematicaLogic.listServicios(data.get(0).getId());
        Assert.assertEquals(3, list.size());
    }

    /**
     * Prueba para asociar un Servicios existente a un Tematica
     *
     * 
     */
    @Test
    public void addServiciosTest() {
        TematicaEntity entity = data.get(0);
        ServicioEntity servicioEntity = serviciosData.get(0);
        ServicioEntity response = tematicaLogic.addServicio (entity.getId(),servicioEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(servicioEntity.getId(), response.getId());
    }

    /**
     * Prueba para remplazar las instancias de Servicios asociadas a una instancia
     * de Tematica
     *
     * 
     */
    @Test
    public void replaceServiciosTest() {
        TematicaEntity entity = data.get(0);
        List<ServicioEntity> list = serviciosData.subList(1, 3);
        tematicaLogic.replaceServicios(list, entity.getId());

        entity = tematicaLogic.getTematica(entity.getId());
        Assert.assertTrue(entity.getServicios().contains(serviciosData.get(0)));
        Assert.assertTrue(entity.getServicios().contains(serviciosData.get(1)));
        Assert.assertTrue(entity.getServicios().contains(serviciosData.get(2)));
    }

    /**
     * Prueba para desasociar un Servicio existente de un Tematica existente
     *
     * 
     */
    @Test
    public void removeServiciosTest() 
    {
        tematicaLogic.removeServicio(data.get(0).getId(), serviciosData.get(0).getId());
        ServicioEntity response = tematicaLogic.getServicio(data.get(0).getId(), serviciosData.get(0).getId());

    } 
}
