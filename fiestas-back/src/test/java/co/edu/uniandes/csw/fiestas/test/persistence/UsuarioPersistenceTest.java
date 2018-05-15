package co.edu.uniandes.csw.fiestas.test.persistence;

import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import co.edu.uniandes.csw.fiestas.persistence.UsuarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
 * @author df.nino10
 */
@RunWith(Arquillian.class)
public class UsuarioPersistenceTest {
    
        /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Usuario, el descriptor de la
     * base de datos y el archivo benas.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase UsuarioPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private UsuarioPersistence usuarioPersistence;

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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     *
     */
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Usuario.
     *
     *
     */
    @Test
    public void createUsuarioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioPersistence.create(newEntity);

        Assert.assertNotNull(result);

        UsuarioEntity entity = em.find(UsuarioEntity.class, result.getId());

        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getRol(), entity.getRol());
        Assert.assertEquals(newEntity.getToken(), entity.getToken());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
    }

    /**
     * Prueba para consultar la lista de Usuarios.
     *
     *
     */
    @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> list = usuarioPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (UsuarioEntity ent : list) {
            boolean found = false;
            for (UsuarioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Usuario.
     *
     *
     */
    @Test
    public void getUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = usuarioPersistence.find(entity.getLogin());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getRol(), entity.getRol());
        Assert.assertEquals(newEntity.getToken(), entity.getToken());
        Assert.assertEquals(newEntity.getLogin(), entity.getLogin());
        Assert.assertEquals(newEntity.getContrasena(), entity.getContrasena());
    }

    /**
     * Prueba para eliminar un Usuario.
     *
     *
     */
    @Test
    public void deleteUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        usuarioPersistence.delete(entity.getLogin());
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Usuario.
     *
     *
     */
    @Test
    public void updateUsuarioTestFail() {
        UsuarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);

        newEntity.setId(entity.getId());

        try
        {
            usuarioPersistence.update(newEntity);
            fail("Debió fallar porque el usuario no persistió antes");
        }
        catch(Exception e)
        {
            passed();
        }
    }
    
    /**
     * Prueba para actualizar un Usuario.
     *
     *
     */
    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setLogin(entity.getLogin());
        
        try
        {
            usuarioPersistence.update(newEntity);
            UsuarioEntity resp = em.find(UsuarioEntity.class, entity.getId());

            Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
            Assert.assertEquals(newEntity.getRol(), resp.getRol());
            Assert.assertEquals(newEntity.getToken(), resp.getToken());
            Assert.assertEquals(newEntity.getLogin(),  resp.getLogin());
            Assert.assertEquals(newEntity.getContrasena(),  resp.getContrasena());            
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }
}