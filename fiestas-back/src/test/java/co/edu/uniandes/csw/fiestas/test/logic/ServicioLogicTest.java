package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ServicioLogic;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import static com.ctc.wstx.util.DataUtil.Long;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import static org.jboss.arquillian.test.spi.TestResult.passed;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import static org.junit.Assert.fail;
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
public class ServicioLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ServicioLogic servicioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ServicioEntity> data = new ArrayList<>();

    private List<ProductoEntity> productosData = new ArrayList();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
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
        em.createQuery("delete from ProductoEntity").executeUpdate();
        em.createQuery("delete from ServicioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
       List<ProductoEntity> listaProductos = new ArrayList<>();
       for (int i = 0; i < 3; i++) {
            ProductoEntity producto = factory.manufacturePojo(ProductoEntity.class);
            em.persist(producto);
            productosData.add(producto);
            listaProductos.add(producto);
        }

       for (int i = 0; i < 3; i++) 
        {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entity.setProductos(listaProductos);
            em.persist(entity);
            data.add(entity);
        } 
        
    }

    /**
     * Prueba para crear un Servicio
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createServicioTest() throws BusinessLogicException {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        ServicioEntity result = servicioLogic.createServicio(newEntity);
        Assert.assertNotNull(result);
        ServicioEntity entity = em.find(ServicioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
    }

    /**
     * Prueba para consultar la lista de Servicios
     */
    @Test
    public void getServiciosTest() {
        List<ServicioEntity> list = servicioLogic.getServicios();
        Assert.assertEquals(data.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Servicio
     */
    @Test
    public void getServicioTest() {
        ServicioEntity entity = data.get(0);
        ServicioEntity resultEntity = servicioLogic.getServicio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
    }

    /**
     * Prueba para eliminar un Servicio
     */
    @Test
    public void deleteServicioTest() {
        ServicioEntity entity = data.get(0);
        try 
        {
            servicioLogic.deleteServicio(entity.getId());
        } 
        catch (BusinessLogicException e) 
        {
            fail(e.getMessage());
        }

        ServicioEntity deleted = em.find(ServicioEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    
    }

    /**
     * Prueba para actualizar un Servicio
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void updateServicioTest() throws BusinessLogicException {
        
        ServicioEntity entity = data.get(0);
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
        pojoEntity.setProductos(productosData);
        pojoEntity.setId(entity.getId());
        
        pojoEntity.setDescripcion(entity.getDescripcion());

        pojoEntity.setNombre(entity.getNombre());
        pojoEntity.setTipo(entity.getTipo());
        pojoEntity.setProductos(entity.getProductos());
        
        try
        {
            servicioLogic.updateServicio(pojoEntity);
        }
        catch (BusinessLogicException e)
        {
            fail(e.getMessage());
        }
        ServicioEntity resp = em.find(ServicioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
    }
    
    /**
     * Prueba obtener productos de un Servicio.
     * Falla si se obtienen productos diferentes a los que se tiene
     */
    @Test
    public void getProductosTest() {
        List<ProductoEntity> obtenida = servicioLogic.listProductos(data.get(0).getId());
        for (ProductoEntity so : obtenida) {
            if (!data.get(0).getProductos().contains(so)) {
                fail("No se encuentra alguno de los productos del servicio");
            }
        }
        passed();
    }

    /**
     * Prueba para obtener una colección de instancias de Productos asociadas a una
     * instancia Servicio
     */
    @Test
    public void listProductosTest() {
        List<ProductoEntity> list = servicioLogic.listProductos(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para asociar un Producto existente a un Servicio
     *
     * 
     * @throws javax.transaction.NotSupportedException
     * @throws javax.transaction.SystemException
     * @throws javax.transaction.RollbackException
     * @throws javax.transaction.HeuristicMixedException
     * @throws javax.transaction.HeuristicRollbackException
     */
   @Test
    public void addProductoTest() throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
        ServicioEntity servicio = data.get(0);
        ProductoEntity producto = factory.manufacturePojo(ProductoEntity.class);
//        producto.setServicios(data);
        utx.begin();
        em.persist(producto);
        utx.commit();
        servicioLogic.addProducto(servicio.getId(), producto.getId());
       
    }

    /**
     * Prueba para desasociar un Producto existente de un Servicio existente
     *
     * 
     */
    @Test
    public void removeProductosTest() {
        servicioLogic.removeProducto(data.get(0).getId(), productosData.get(0).getId());
        ProductoEntity response = servicioLogic.getProducto(data.get(0).getId(), productosData.get(0).getId());
    }
  
}
