package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.BlogLogic;
import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
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
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author mc.gonzalez15
 */
@RunWith(Arquillian.class)
public class BlogLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private BlogLogic blogLogic;
    
    @Inject
    private EventoLogic eventoLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<BlogEntity> data = new ArrayList<>();
    
    private List<ClienteEntity> Udata = new ArrayList<>();
    
    private List<EventoEntity> Edata = new ArrayList<>();
    

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BlogEntity.class.getPackage())
                .addPackage(BlogLogic.class.getPackage())
                .addPackage(BlogPersistence.class.getPackage())
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
        em.createQuery("delete from BlogEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
            BlogEntity entity = factory.manufacturePojo(BlogEntity.class); 
            entity.setCliente(cliente);
            entity.setEvento(evento);
            em.persist(evento);
            ArrayList lista=new ArrayList<>();
            lista.add(entity);
            cliente.setBlogs(lista);
            em.persist(cliente);
            Edata.add(evento);
            Udata.add(cliente);
            em.persist(entity);
            BlogEntity prueba = em.find(BlogEntity.class, entity.getId());
            data.add(entity); 
           
        }
    }

    /**
     * Prueba para crear un Blog
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createBlogTest() throws BusinessLogicException {
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);
        BlogEntity result = blogLogic.createBlog(newEntity);
        Assert.assertNotNull(result);
        BlogEntity entidad = em.find(BlogEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getTitulo(), entidad.getTitulo());
        Assert.assertEquals(newEntity.getCuerpo(), entidad.getCuerpo());
        Assert.assertEquals(newEntity.getLikes(), entidad.getLikes());
        Assert.assertEquals(newEntity.getCliente(), entidad.getCliente());
        Assert.assertEquals(newEntity.getEvento(), entidad.getEvento());
    }

    /**
     * Prueba para consultar la lista de blogs
     */
    @Test
    public void getBlogsTest() {
        List<BlogEntity> lista = blogLogic.getBlogs();
        Assert.assertEquals(data.size(), lista.size());
        for (BlogEntity entity : lista) {
            boolean encontrado = false;
            for (BlogEntity blogEntity : data) {
                if (entity.getId().equals(blogEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Se prueba el método para obtener un solo blog.
     */
    @Test
    public void getBlogTest(){
        BlogEntity blogE =data.get(0);
        BlogEntity blogE1 = blogLogic.getBlog(blogE.getId());
        assertNotNull(blogE1);
        Assert.assertEquals(blogE.getId(), blogE1.getId());
        Assert.assertEquals(blogE.getTitulo(), blogE1.getTitulo());
        Assert.assertEquals(blogE.getCuerpo(), blogE1.getCuerpo());
        Assert.assertEquals(blogE.getLikes(), blogE1.getLikes());
        Assert.assertEquals(blogE.getCliente(), blogE1.getCliente());
        Assert.assertEquals(blogE.getEvento(), blogE1.getEvento());
        
    }
    /**
     * Prueba para eliminar un blog
     */
    @Test
    public void deleteBlog() {
        BlogEntity entity = data.get(0);
        blogLogic.deleteBlog(entity.getId());
        BlogEntity deleted = em.find(BlogEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un blog
     */
    @Test
    public void updateBlogTest() {
        BlogEntity entity = data.get(0);
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setEvento(entity.getEvento());
        newEntity.setCliente(entity.getCliente());

        
        
          BlogEntity actualizado=blogLogic.updateBlog(newEntity);
               
        BlogEntity resp = em.find(BlogEntity.class, newEntity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getTitulo(), resp.getTitulo());
        Assert.assertEquals(newEntity.getCuerpo(), resp.getCuerpo());
        Assert.assertEquals(newEntity.getLikes(), resp.getLikes());
        Assert.assertEquals(newEntity.getCliente(), resp.getCliente());
        Assert.assertEquals(newEntity.getEvento(), resp.getEvento());
        
    }
    /**
     * Se prueba consistencia entre los eventos en el blog y los eventos como entidades.
     */
    @Test 
    public void getEventoest(){
        BlogEntity entity = data.get(0);
        EventoEntity eventoE= entity.getEvento();
        EventoEntity eventoE1=eventoLogic.getEvento(eventoE.getId());
        assertNotNull(eventoE1);
        assertEquals(eventoE1.getId(), eventoE.getId());
        assertEquals(eventoE1.getId(), eventoE.getId());
        assertEquals(eventoE1.getCliente(), eventoE.getCliente());
        assertEquals(eventoE1.getDescripcion(), eventoE.getDescripcion());
        assertEquals(eventoE1.getLugar(), eventoE.getLugar());
    }
    
    /**
     * Se prueba consistencia entre los eventos en el blog y los eventos como entidades.
     */
    @Test
    public void getEventoExistenteTTest(){
         BlogEntity entity = data.get(0);
         EventoEntity eventoE = entity.getEvento();
         EventoEntity eventoE1=blogLogic.getEventoExistente(eventoE.getId());
        
        assertNotNull(eventoE1);
        assertEquals(eventoE1.getId(), eventoE.getId());
        assertEquals(eventoE1.getId(), eventoE.getId());
        assertEquals(eventoE1.getCliente(), eventoE.getCliente());
        assertEquals(eventoE1.getDescripcion(), eventoE.getDescripcion());
        assertEquals(eventoE1.getLugar(), eventoE.getLugar());
    }
    
    @Test
    public void addEventoTest(){
        BlogEntity blogE = data.get(0);
        blogE.setEvento(null);
        blogLogic.updateBlog(blogE);
        EventoEntity evento= factory.manufacturePojo(EventoEntity.class);
        
        try{
            blogLogic.addEvento(evento, blogE.getId());      
        }
        catch(BusinessLogicException e){
            fail("No pudo guardarse el evento. Debería haberse guardado.");
        }       
    }
    
    @Test
    public void addEventoFailTest(){
        BlogEntity blogE = data.get(0);
        EventoEntity evento= factory.manufacturePojo(EventoEntity.class);
        
        try{
            blogLogic.addEvento(evento, blogE.getId());
            fail("No pudo guardarse el evento");
        }
        catch(BusinessLogicException e){
        }       
    }
}
