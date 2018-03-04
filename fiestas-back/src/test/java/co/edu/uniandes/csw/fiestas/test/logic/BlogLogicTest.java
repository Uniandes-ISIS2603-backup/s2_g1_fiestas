/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.BlogLogic;
import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
    private UsuarioLogic usuarioLogic;
    
    @Inject 
    private EventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<BlogEntity> data = new ArrayList<>();
    
    private List<UsuarioEntity> Udata = new ArrayList<>();
    
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            
            Edata.add(evento);
            Udata.add(usuario);
           
        }
        
        for(int l = 0; l<3;l++)
        {
            BlogEntity entity = factory.manufacturePojo(BlogEntity.class);
            entity.setUsuario(Udata.get(l));
            entity.setEvento(Edata.get(l));
            em.persist(entity);
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
        Assert.assertEquals(newEntity.getUsuario(), entidad.getUsuario());
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
     * Prueba para eliminar un blog
     */
    @Test
    public void deleteBlog() {
        BlogEntity entity = data.get(0);
        blogLogic.deleteBlog(entity.getId());
        BlogEntity deleted = em.find(BlogEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un blog
     */
    @Test
    public void updateBlogTest() {
        BlogEntity entity = data.get(0);
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);

        newEntity.setId(entity.getId());

        blogLogic.updateBlog(newEntity);

        BlogEntity resp = em.find(BlogEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getTitulo(), resp.getTitulo());
        Assert.assertEquals(newEntity.getCuerpo(), resp.getCuerpo());
        Assert.assertEquals(newEntity.getLikes(), resp.getLikes());
        Assert.assertEquals(newEntity.getUsuario(), resp.getUsuario());
        Assert.assertEquals(newEntity.getEvento(), resp.getEvento());
        
    }
}
