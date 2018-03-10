package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
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
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author nm.hernandez10
 */
@RunWith(Arquillian.class)
public class ProveedorLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ProveedorLogic proveedorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ProveedorEntity> data = new ArrayList<ProveedorEntity>();

    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ProveedorEntity.class.getPackage())
                .addPackage(ProveedorLogic.class.getPackage())
                .addPackage(ProveedorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");

    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() 
    {
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
    private void clearData() 
    {
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Proveedor
     *
     */
    @Test
    public void createProveedorTest() throws BusinessLogicException {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = proveedorLogic.createProveedor(newEntity);
        Assert.assertNotNull(result);
        ProveedorEntity entidad = em.find(ProveedorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getContraseña(), entidad.getContraseña());
        Assert.assertEquals(newEntity.getCorreo(), entidad.getCorreo());
        Assert.assertEquals(newEntity.getDireccion(), entidad.getDireccion());
        Assert.assertEquals(newEntity.getDocumento(), entidad.getDocumento());
        Assert.assertEquals(newEntity.getLogin(), entidad.getLogin());
        Assert.assertEquals(newEntity.getNombre(), entidad.getNombre());
        Assert.assertEquals(newEntity.getTelefono(), entidad.getTelefono());
        Assert.assertEquals(newEntity.isPenalizado(), entidad.isPenalizado());
    }

    /**
     * Prueba para consultar la lista de provedoores
     */
    @Test
    public void getProveedoresTest() {
        List<ProveedorEntity> lista = proveedorLogic.getProveedores();
        Assert.assertEquals(data.size(), lista.size());
        for (ProveedorEntity entity : lista) {
            boolean encontrado = false;
            for (ProveedorEntity proveedorEntity : data) {
                if (entity.getId().equals(proveedorEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para eliminar un proveedor
     */
    @Test
    public void deleteProveedor() 
    {
        ProveedorEntity entity = data.get(0);
        try
        {
            proveedorLogic.deleteProveedor(entity.getId());
        }
        catch(BusinessLogicException e)
        {
            fail(e.getMessage());
        }
        
        ProveedorEntity deleted = em.find(ProveedorEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un proveedor
     */
    @Test
    public void updateProveedorTest() {
        ProveedorEntity entity = data.get(0);
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);

        newEntity.setId(entity.getId());  
        
        try
        {
            proveedorLogic.updateProveedor(newEntity);
        }
        catch(BusinessLogicException e)
        {
            fail(e.getMessage());
        }
        
        ProveedorEntity entidad = em.find(ProveedorEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getContraseña(), entidad.getContraseña());
        Assert.assertEquals(newEntity.getCorreo(), entidad.getCorreo());
        Assert.assertEquals(newEntity.getDireccion(), entidad.getDireccion());
        Assert.assertEquals(newEntity.getDocumento(), entidad.getDocumento());
        Assert.assertEquals(newEntity.getLogin(), entidad.getLogin());
        Assert.assertEquals(newEntity.getNombre(), entidad.getNombre());
        Assert.assertEquals(newEntity.getTelefono(), entidad.getTelefono());
        Assert.assertEquals(newEntity.isPenalizado(), entidad.isPenalizado());
    }
}
