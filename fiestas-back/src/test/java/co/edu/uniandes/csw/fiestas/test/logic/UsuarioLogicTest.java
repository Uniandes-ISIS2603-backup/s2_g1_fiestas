package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.UsuarioPersistence;
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
 * @author df.nino10
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {
    
    PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private UsuarioLogic usuarioLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<UsuarioEntity> data = new ArrayList<>();
    
    public UsuarioLogicTest() {
    }
    
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class).addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage()).addPackage(UsuarioPersistence.class.getPackage())
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() 
    {
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
            ArrayList<BlogEntity> listaBlogs = new ArrayList<>();
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            for (int j = 0; j < 2; j++) {
                BlogEntity blogE = factory.manufacturePojo(BlogEntity.class);
                listaBlogs.add(blogE);
                em.persist(blogE);
            }
            usuario.setBlogs(listaBlogs);
            em.persist(usuario);
            data.add(usuario);
        }
    }
    
     /**
     * Prueba para crear un Usuario
     *
     */
    @Test
    public void createUsuarioTest() throws BusinessLogicException {
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result = usuarioLogic.createUsuario(newEntity);
        Assert.assertNotNull(result);
        UsuarioEntity entidad = em.find(UsuarioEntity.class, result.getId());
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
     * Prueba para consultar la lista de Usuarios
     */
    @Test
    public void getUsuarioesTest() {
        List<UsuarioEntity> lista = usuarioLogic.getUsuarios();
        Assert.assertEquals(data.size(), lista.size());
        for (UsuarioEntity entity : lista) {
            boolean encontrado = false;
            for (UsuarioEntity usuarioEntity : data) {
                if (entity.getId().equals(usuarioEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }
    
    @Test
    public void getUsuarioTest(){
        UsuarioEntity usuario = data.get(0);
        UsuarioEntity usuarioT = usuarioLogic.getUsuario(usuario.getId());
        Assert.assertNotNull(usuarioT);
        Assert.assertEquals(usuario.getId(), usuarioT.getId());
        Assert.assertEquals(usuario.getContraseña(), usuarioT.getContraseña());
        Assert.assertEquals(usuario.getCorreo(), usuarioT.getCorreo());
        Assert.assertEquals(usuario.getDireccion(), usuarioT.getDireccion());
        Assert.assertEquals(usuario.getDocumento(), usuarioT.getDocumento());
        Assert.assertEquals(usuario.getLogin(), usuarioT.getLogin());
        Assert.assertEquals(usuario.getName(), usuarioT.getName());
        Assert.assertEquals(usuario.getTelefono(), usuarioT.getTelefono());
        
    }
    
     /**
     * Prueba para eliminar un usuario.
     */
    @Test
    public void deleteUsuarioTest(){
        UsuarioEntity entity = data.get(0);
        try{
        usuarioLogic.deleteUsuario(entity.getId());
        } 
        catch(BusinessLogicException e){
            fail("No debería fallar al eliminar al usuario.");
        }
        UsuarioEntity deleted = em.find(UsuarioEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }
      /**
     * Prueba para eliminar un usuario.
     */
    @Test
    public void deleteUsuarioFailTest() {
        UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
        try{
        usuarioLogic.deleteUsuario(entity.getId());
            fail("Debería fallar al eliminar el usuario.");
        }
        catch(BusinessLogicException e){
            
        }
    }
    

     /**
     * Prueba para actualizar un usuario
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void updateUsuarioTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);

        newEntity.setId(entity.getId());

        try{
        usuarioLogic.updateUsuario(newEntity);
        }
        catch(BusinessLogicException e){
            fail("No debería fallar al hacer update.");
        }

        UsuarioEntity entidad = em.find(UsuarioEntity.class, entity.getId());
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
     * Prueba para actualizar un usuario
     */
    @Test
    public void updateUsuarioFailTest() {
        UsuarioEntity entity = data.get(0);
        UsuarioEntity newEntity = factory.manufacturePojo(UsuarioEntity.class);
        
        try{
        usuarioLogic.updateUsuario(newEntity);
        fail("Debería fallar al hacer update.");
        }
        catch(BusinessLogicException e){
        }    
    }
    /**
     * 
     */
    @Test
    public void getBlogsTest(){
        UsuarioEntity usuario = data.get(0);
        List<BlogEntity> blogs = usuario.getBlogs();
        
        try{
        List<BlogEntity> blogsT=usuarioLogic.getBlogs(usuario);
        assertNotNull(blogsT);
        Assert.assertEquals(blogsT, blogs);

        }
        catch(BusinessLogicException e){
            fail("No debería fallar al buscar los blogs.");
        }
    }
    
    @Test
    public void getBlogTest(){
        UsuarioEntity usuario = data.get(0);
        BlogEntity blog = usuario.getBlogs().get(0);
        
        try{
            BlogEntity blogT = usuarioLogic.getBlog(usuario, blog.getId());
            assertNotNull(blogT);
            Assert.assertEquals(blogT, blog);
        }
        catch(BusinessLogicException e){
            fail("No debería fallar añ obtener el blog.");
        }
    }
}
