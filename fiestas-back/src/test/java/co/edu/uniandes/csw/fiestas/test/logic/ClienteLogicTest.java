package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ClienteLogic;
import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ClientePersistence;
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
public class ClienteLogicTest 
{
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteLogic clienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Cliente
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createClienteTest() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
        Assert.assertNotNull(result);
        ClienteEntity entidad = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getContraseña(), entidad.getContraseña());
        Assert.assertEquals(newEntity.getCorreo(), entidad.getCorreo());
        Assert.assertEquals(newEntity.getDireccion(), entidad.getDireccion());
        Assert.assertEquals(newEntity.getDocumento(), entidad.getDocumento());
        Assert.assertEquals(newEntity.getLogin(), entidad.getLogin());
        Assert.assertEquals(newEntity.getNombre(), entidad.getNombre());
        Assert.assertEquals(newEntity.getTelefono(), entidad.getTelefono());
    }

    /**
     * Prueba para consultar la lista de clientes
     */
    @Test
    public void getClientesTest() {
        List<ClienteEntity> lista = clienteLogic.getClientes();
        Assert.assertEquals(data.size(), lista.size());
        for (ClienteEntity entity : lista) {
            boolean encontrado = false;
            for (ClienteEntity clienteEntity : data) {
                if (entity.getId().equals(clienteEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para eliminar un cliente
     */
    @Test
    public void deleteCliente() {
        ClienteEntity entity = data.get(0);
        
        try
        {
           clienteLogic.deleteCliente(entity.getId());
        }
        catch(BusinessLogicException e)
        {
            fail(e.getMessage());
        }
        
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un cliente
     */
    @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        newEntity.setId(entity.getId());        

        try
        {
           clienteLogic.updateCliente(newEntity);
        }
        catch(BusinessLogicException e)
        {
            fail(e.getMessage());
        }
        ClienteEntity entidad = em.find(ClienteEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getContraseña(), entidad.getContraseña());
        Assert.assertEquals(newEntity.getCorreo(), entidad.getCorreo());
        Assert.assertEquals(newEntity.getDireccion(), entidad.getDireccion());
        Assert.assertEquals(newEntity.getDocumento(), entidad.getDocumento());
        Assert.assertEquals(newEntity.getLogin(), entidad.getLogin());
        Assert.assertEquals(newEntity.getNombre(), entidad.getNombre());
        Assert.assertEquals(newEntity.getTelefono(), entidad.getTelefono());
    }
}
