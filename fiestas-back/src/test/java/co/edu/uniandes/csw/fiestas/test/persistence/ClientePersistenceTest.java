package co.edu.uniandes.csw.fiestas.test.persistence;

import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
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
public class ClientePersistenceTest
{
     /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Cliente, el descriptor de la
     * base de datos y el archivo benas.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase ClientePersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private ClientePersistence clientePersistence;

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
    public void setUp() {
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     *
     */
    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Cliente.
     *
     *
     */
    @Test
    public void createClienteTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clientePersistence.create(newEntity);

        Assert.assertNotNull(result);

        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDocumento(), entity.getDocumento());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
    }

    /**
     * Prueba para consultar la lista de Clientes.
     *
     *
     */
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = clientePersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity ent : list) {
            boolean found = false;
            for (ClienteEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Cliente.
     *
     *
     */
    @Test
    public void getClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = clientePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getDocumento(), entity.getDocumento());
        Assert.assertEquals(newEntity.getDireccion(), entity.getDireccion());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
    }

    /**
     * Prueba para eliminar un Cliente.
     *
     *
     */
    @Test
    public void deleteClienteTest() {
        ClienteEntity entity = data.get(0);
        clientePersistence.delete(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Cliente.
     *
     *
     */
    @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        newEntity.setId(entity.getId());

        clientePersistence.update(newEntity);

        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDocumento(), resp.getDocumento());
        Assert.assertEquals(newEntity.getDireccion(), resp.getDireccion());
        Assert.assertEquals(newEntity.getContrasena(), resp.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), resp.getCorreo());
        Assert.assertEquals(newEntity.getLogin(), resp.getLogin());
        Assert.assertEquals(newEntity.getTelefono(), resp.getTelefono());
    }
}
