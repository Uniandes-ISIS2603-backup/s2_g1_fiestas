package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ProductoLogic;
import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ProductoPersistence;
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

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ProductoEntity> data = new ArrayList<>();
    
    private List<ProveedorEntity> dataProv = new ArrayList<>();
    private List<ValoracionEntity> dataVal = new ArrayList<>();

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
        em.createQuery("delete from ValoracionEntity").executeUpdate();        
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ValoracionEntity valoracion =  factory.manufacturePojo(ValoracionEntity.class);
            em.persist(valoracion);
            dataVal.add(valoracion);
        }
        for (int i = 0; i < 3; i++) {
            ProductoEntity entity = factory.manufacturePojo(ProductoEntity.class);
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            if(i==0)
                entity.setValoraciones(dataVal);
            em.persist(proveedor);
            em.persist(entity);
            data.add(entity);
            dataProv.add(proveedor);
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
    public void deleteProducto() throws BusinessLogicException {
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
     * Prueba para actualizar un producto
     */
    @Test
    public void getValoracionesTest() {
        ProductoEntity entity = data.get(0);
        List<ValoracionEntity>vals=productoLogic.getValoraciones(entity);
        Assert.assertEquals(vals.size(), dataVal.size());
        for(ValoracionEntity val: dataVal){
            Assert.assertTrue(vals.contains(val));
        }
    }
}
