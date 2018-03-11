package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ProductoLogic;
import co.edu.uniandes.csw.fiestas.ejb.ServicioLogic;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.persistence.ProductoPersistence;
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
public class ProductoLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ProductoLogic productoLogic;
    
    @Inject
    private ServicioLogic servicioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ProductoEntity> data = new ArrayList<ProductoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProductoEntity.class.getPackage())
                .addPackage(ProductoLogic.class.getPackage())
                .addPackage(ProductoPersistence.class.getPackage())
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ProductoEntity entity = factory.manufacturePojo(ProductoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Producto
     *
     */
    @Test
    public void createProductoTest() {
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        ProductoEntity result = productoLogic.createProducto(newEntity);
        Assert.assertNotNull(result);
        ProductoEntity entidad = em.find(ProductoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entidad.getDescripcion());
    }

    /**
     * Prueba para consultar la lista de productos
     */
    @Test
    public void getProductosTest() {
        List<ProductoEntity> lista = productoLogic.getProductos();
        Assert.assertEquals(data.size(), lista.size());
        for (ProductoEntity entity : lista) {
            boolean encontrado = false;
            for (ProductoEntity productoEntity : data) {
                if (entity.getId().equals(productoEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }
    
    /**
     * Prueba para eliminar un producto
     */
    @Test
    public void deleteProducto() {
        ProductoEntity entity = data.get(0);
        productoLogic.deleteProducto(entity.getId());
        ProductoEntity deleted = em.find(ProductoEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un producto
     */
    @Test
    public void updateProductoTest() {
        ProductoEntity entity = data.get(0);
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);

        newEntity.setId(entity.getId());

        productoLogic.updateProducto(newEntity);

        ProductoEntity resp = em.find(ProductoEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
    }
    
    
    /**
     * Prueba para actualizar los servicios
     */
    @Test
    public void setServicioTest()
    {
        ProductoEntity entity = data.get(0);
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);        
        productoLogic.createProducto(newEntity);
        
        ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
        servicioLogic.createServicio(newServicio);
        
        productoLogic.setServicio(entity.getId(), newServicio.getId());
        productoLogic.setServicio(newEntity.getId(), newServicio.getId());
        
        Assert.assertEquals(entity.getServicio(), newEntity.getServicio());
    }
    
    /**
     * Prueba para revisar el servicio
     */
    @Test
    public void getServicioTest()
    {
        ProductoEntity entity = data.get(0);
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        productoLogic.createProducto(newEntity);
        
        ServicioEntity newServicio = factory.manufacturePojo(ServicioEntity.class);
        servicioLogic.createServicio(newServicio);
        
        productoLogic.setServicio(entity.getId(), newServicio.getId());
        productoLogic.setServicio(newEntity.getId(), newServicio.getId());
        
        
        Assert.assertEquals(entity.getServicio(), newEntity.getServicio());
    }

}
