package co.edu.uniandes.csw.fiestas.test.persistence;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.persistence.BlogPersistence;
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
 * @author mc.gonzalez15
 */
@RunWith(Arquillian.class)
public class BlogPersistenceTest {

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Blog, el descriptor de la base de
     * datos y el archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BlogEntity.class.getPackage())
                .addPackage(BlogPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase BlogPersistence cuyos métodos se
     * van a probar.
     */
    @Inject
    private BlogPersistence blogPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
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
        em.createQuery("delete from BlogEntity").executeUpdate();
    }

    /**
     *
     */
    private List<BlogEntity> data = new ArrayList<BlogEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            BlogEntity entity = factory.manufacturePojo(BlogEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Blog.
     *
     *
     */
    @Test
    public void createBlogTest() {
        PodamFactory factory = new PodamFactoryImpl();
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);
        BlogEntity result = blogPersistence.create(newEntity);

        Assert.assertNotNull(result);

        BlogEntity entity = em.find(BlogEntity.class, result.getId());

        Assert.assertEquals(newEntity.getTitulo(), entity.getTitulo());
        Assert.assertEquals(newEntity.getCuerpo(), entity.getCuerpo());
        Assert.assertEquals(newEntity.getLikes(), entity.getLikes());

    }

    /**
     * Prueba para consultar la lista de Blogs.
     *
     *
     */
    @Test
    public void getBlogsTest() {
        List<BlogEntity> list = blogPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (BlogEntity ent : list) {
            boolean found = false;
            for (BlogEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Blog.
     *
     *
     */
    @Test
    public void getBlogTest() {
        BlogEntity entity = data.get(0);
        BlogEntity newEntity = blogPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getTitulo(), newEntity.getTitulo());
        Assert.assertEquals(newEntity.getCuerpo(), entity.getCuerpo());
        Assert.assertEquals(newEntity.getLikes(), entity.getLikes());

    }

    /**
     * Prueba para eliminar un Blog.
     *
     *
     */
    @Test
    public void deleteBlogTest() {
        BlogEntity entity = data.get(0);
        blogPersistence.delete(entity.getId());
        BlogEntity deleted = em.find(BlogEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Blog.
     *
     *
     */
    @Test
    public void updateBlogTest() {
        System.out.println("Aqui hay un error");
        BlogEntity entity = data.get(0);

        PodamFactory factory = new PodamFactoryImpl();
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);

        newEntity.setId(entity.getId());

        blogPersistence.update(newEntity);

        BlogEntity resp = em.find(BlogEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getTitulo(), resp.getTitulo());
        Assert.assertEquals(newEntity.getCuerpo(), resp.getCuerpo());
        Assert.assertEquals(newEntity.getLikes(), resp.getLikes());

    }
}
