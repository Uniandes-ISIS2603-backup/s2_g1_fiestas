package co.edu.uniandes.csw.fiestas.test.persistence;



import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author af.losada
 */
@RunWith(Arquillian.class)
public class ProductoPersistenceTest 
{
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Employee, el descriptor de la
     * base de datos y el archivo benas.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProductoEntity.class.getPackage())
                .addPackage(ProductoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Inyección de la dependencia a la clase ProductoPersistence cuyos métodos
     * se van a probar.
     */
    @Inject 
    private ProductoPersistence productoPersistence;

    /**
     * Contexto de Persostencia que se va autilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject 
    UserTransaction utx; 

    /**
     * Configuración inicial de la prueba.
     *
     *
     */
    @Before 
    public void setUp() 
    {
        try 
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
            try 
            {
                utx.rollback();
            } 
            catch (Exception e1) 
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     */
    private void clearData() 
    {
        em.createQuery("delete from ProductoEntity").executeUpdate();
    }

    /**
     *
     */
    private List<ProductoEntity> data = new ArrayList<ProductoEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory;
        factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ProductoEntity entity = factory.manufacturePojo(ProductoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Producto.
     *
     *
     */
    @Test 
    public void createProductoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);
        ProductoEntity result = productoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ProductoEntity entity = em.find(ProductoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getIncluye(), entity.getIncluye());
        Assert.assertEquals(newEntity.getPersonal(), entity.getPersonal());
        Assert.assertEquals(newEntity.getPrecio(), entity.getPrecio());
    }

    /**
     * Prueba para consultar la lista de Productos.
     *
     *
     */
    @Test
    public void getProductosTest() {
        List<ProductoEntity> list = productoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ProductoEntity ent : list) {
            boolean found = false;
            for (ProductoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Producto.
     *
     *
     */
    @Test 
    public void getProductoTest() {
        ProductoEntity entity = data.get(0);
        ProductoEntity newEntity = productoPersistence.find(entity.getId());
        
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getIncluye(), entity.getIncluye());
        Assert.assertEquals(newEntity.getPersonal(), entity.getPersonal());
        Assert.assertEquals(newEntity.getPrecio(), entity.getPrecio());
    }

    /**
     * Prueba para eliminar un Employee.
     *
     *
     */
    @Test
    public void deleteProductoTest() {
        ProductoEntity entity = data.get(0);
        productoPersistence.delete(entity.getId());
        ProductoEntity deleted = em.find(ProductoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Employee.
     *
     *
     */
    @Test
    public void updateProductoTest() {
        ProductoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ProductoEntity newEntity = factory.manufacturePojo(ProductoEntity.class);

        newEntity.setId(entity.getId());

        productoPersistence.update(newEntity);

        ProductoEntity resp = em.find(ProductoEntity.class, entity.getId());

        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
}
