package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.BlogLogic;
import co.edu.uniandes.csw.fiestas.ejb.ClienteLogic;
import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ClientePersistence;
import static com.ctc.wstx.util.DataUtil.Long;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jboss.arquillian.test.spi.TestResult;
import static org.jboss.arquillian.test.spi.TestResult.passed;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
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
    
    @Inject
    private EventoLogic eventoLogic;
    
    @Inject
    private BlogLogic blogLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ClienteEntity> data;
    
    private List<EventoEntity> eventosData;
    
    private List<BlogEntity> blogsData;
    
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
        em.createQuery("delete from BlogEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        data = new ArrayList<ClienteEntity>();
        eventosData = new ArrayList<EventoEntity>();
        blogsData = new ArrayList<BlogEntity>();
        for (int i = 0; i < 3; i++)
        {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            
            if (i == 0)
            {
                for (int j = 0; j < 3; j++) {
                    EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
                    BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
                    evento.setCliente(entity);
                    blog.setCliente(entity);
                    em.persist(blog);
                    blogsData.add(blog);
                    em.persist(evento);
                    eventosData.add(evento);
                }
                entity.setEventos(eventosData);
                entity.setBlogs(blogsData);
            }
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
    public void createClienteTest() throws BusinessLogicException
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
        Assert.assertNotNull(result);
        ClienteEntity entidad = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getContrasena(), entidad.getContrasena());
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
        Long idnew= entity.getId();
        newEntity.setId(idnew);
        newEntity.setLogin(entity.getLogin());
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
        Assert.assertEquals(newEntity.getContrasena(), entidad.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entidad.getCorreo());
        Assert.assertEquals(newEntity.getDireccion(), entidad.getDireccion());
        Assert.assertEquals(newEntity.getDocumento(), entidad.getDocumento());
        Assert.assertEquals(newEntity.getLogin(), entidad.getLogin());
        Assert.assertEquals(newEntity.getNombre(), entidad.getNombre());
        Assert.assertEquals(newEntity.getTelefono(), entidad.getTelefono());
    }
    
    /**
     * Prueba fallida para crear un Cliente 1.
     *
     * Falla por que un segundo cliente a crear se va a guardar con id de uno ya existente
     */
    @Test
    public void createClienteTestFail1()
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = new ClienteEntity();
        
        try
        {
            result = clienteLogic.createCliente(newEntity);
            result = clienteLogic.createCliente(newEntity);
            fail("No se debio haber creado el cliente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba fallida para crear un Cliente 2.
     *
     * Falla por que un cliente se crea con una contraseña vacía
     */
    @Test
    public void createClienteTestFail2()
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = new ClienteEntity();
        newEntity.setContrasena("");
        
        try
        {
            result = clienteLogic.createCliente(newEntity);
            fail("No se debio haber creado el cliente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba fallida para crear un Cliente 2.
     *
     * Falla por que un cliente se crea con un login ya existente
     */
    @Test
    public void createClienteTestFail3()
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity newEntity2 = factory.manufacturePojo(ClienteEntity.class);
        newEntity2.setLogin(newEntity.getLogin());
        ClienteEntity result = new ClienteEntity();
        
        try
        {
            result = clienteLogic.createCliente(newEntity);
            result = clienteLogic.createCliente(newEntity2);
            fail("No se debio haber creado el cliente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba fallida para actualizar un Cliente 1.
     *
     * Falla por que un cliente se actualiza cambiando un login
     */
    @Test
    public void updateClienteTestFail1()
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = new ClienteEntity();
        
        try
        {
            result = clienteLogic.createCliente(newEntity);
            newEntity.setLogin("Soy diferente");
            result = clienteLogic.updateCliente(newEntity);
            fail("No se debio haber creado el cliente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba fallida para actualizar un Cliente 2.
     *
     * Falla por que un cliente se actualiza cambiando la contraseña a vacío
     */
    @Test
    public void updateClienteTestFail2()
    {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = new ClienteEntity();
        
        try
        {
            result = clienteLogic.createCliente(newEntity);
            newEntity.setContrasena("");
            result = clienteLogic.updateCliente(newEntity);
            fail("No se debio haber creado el cliente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba para agregar evento a un Cliente.
     *
     * Se agregan los tres eventos creados a los tres clientes creados
     */
    @Test
    public void addEventoTest()
    {
        try
        {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            int noOfDays = 8;
            Date dateOfOrder = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOfOrder);
            calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
            Date date = calendar.getTime();
            evento.setFecha(date);
            evento.setInvitados(10);
            eventoLogic.createEvento(evento);
            clienteLogic.addEvento(evento.getId(), data.get(0).getId());
            data.get(0).addEvento(evento);
            
            Assert.assertTrue(data.get(0).getEventos().contains(evento));
            Assert.assertNotNull(em.find(EventoEntity.class, evento.getId()));
            Assert.assertEquals(evento, em.find(EventoEntity.class, evento.getId()));
        }
        catch (BusinessLogicException x)
        {
            fail(x.getMessage());
        }
    }
    
    /**
     * Prueba de falla para agregar evento a un Cliente 1.
     *
     * Falla si se agrega un evento a un cliente inexistente
     */
    @Test
    public void addEventoTestFail1()
    {
        try
        {
            clienteLogic.addEvento(eventosData.get(0).getId(), Long(999999));
            fail("No debe agregarse el evento a un cliente inexistente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba de falla para agregar evento a un Cliente 2.
     *
     * Falla si se agrega un evento a un cliente que ya tiene ese evento
     */
    @Test
    public void addEventoTestFail2()
    {
        try
        {
            clienteLogic.addEvento(eventosData.get(0).getId(), data.get(0).getId());
            fail("No debe agregarse un evento a un cliente que ya tiene dicho evento");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba para remover evento a un Cliente.
     *
     * Falla si se remueve el único evento de un cliente y éste sigue teniendo eventos
     */
    @Test
    public void removeEventoTest()
    {
        try
        {
            clienteLogic.removeEvento(eventosData.get(0).getId(), data.get(0).getId());
            Assert.assertEquals(2, em.find(ClienteEntity.class, data.get(0).getId()).getEventos().size());
        }
        catch (BusinessLogicException x)
        {
            fail(x.getMessage());
        }
    }
    
    /**
     * Prueba de falla para remover evento a un Cliente 1.
     *
     * Falla si se remueve un evento de un cliente inexistente
     */
    @Test
    public void removeEventoTestFail1()
    {
        try
        {
            clienteLogic.removeEvento(eventosData.get(0).getId(), Long(99999999));
            fail("Se removió un evento de un cliente inexistente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba de falla para remover evento a un Cliente 2.
     *
     * Falla si se remueve un evento de un cliente que no lo tiene
     */
    @Test
    public void removeEventoTestFail2()
    {
        try
        {
            clienteLogic.removeEvento(eventosData.get(0).getId(), data.get(1).getId());
            fail("Se removió un evento de un cliente que no tiene dicho evento");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba para remover evento a un Cliente.
     *
     * Falla si se reemplaza todos los elementos de la lista y estos son diferentes a la lista despues de ser reemplazada
     */
    @Test
    public void replaceEventosTest()
    {
        try
        {
            clienteLogic.replaceEventos(data.get(1).getId(), eventosData);
            for(EventoEntity ee : eventosData)
            {
                if(!em.find(ClienteEntity.class, data.get(0).getId()).getEventos().contains(ee))
                {
                    fail("No está alguno de los eventos reemplazados en la nueva lista del cliente");
                }
            }
            passed();
        }
        catch (BusinessLogicException x)
        {
            fail(x.getMessage());
        }
    }
    
    /**
     * Prueba de falla para remover evento a un Cliente.
     *
     * Falla si se reemplazan eventos de un cliente inexistente
     */
    @Test
    public void replaceEventosTestFail()
    {
        try
        {
            clienteLogic.replaceEventos(Long(99999999),eventosData );
            fail("Se removió un evento de un cliente inexistente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba obtener eventos de un Cliente.
     *
     * Falla si se obtienen eventos diferentes a los que se tiene
     */
    @Test
    public void getEventosTest()
    {
        try
        {
            List<EventoEntity> obtenida = clienteLogic.getEventos(data.get(0).getId());
            for(EventoEntity ee : obtenida)
            {
                if(!data.get(0).getEventos().contains(ee))
                {
                    fail("Algún evento que debe tener en el cliente no está persistido correctamente en la lista relación");
                }
            }
            passed();
        }
        catch (BusinessLogicException x)
        {
            fail(x.getMessage());
        }
    }
    
    /**
     * Prueba de falla para obtener eventos de un Cliente.
     *
     * Falla si se obtienen eventos de un cliente inexistente
     */
    @Test
    public void getEventosTestFail()
    {
        try
        {
            List<EventoEntity> obtenida = clienteLogic.getEventos(Long(99999999));
            fail("Se removió un evento de un cliente inexistente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba obtener evento de un Cliente.
     *
     * Falla si se obtiene evento diferente al que se tiene
     */
    @Test
    public void getEventoTest()
    {
        try
        {
            EventoEntity obtenida = clienteLogic.getEvento(data.get(0).getId(),eventosData.get(0).getId());
            Assert.assertEquals(eventosData.get(0), obtenida);
        }
        catch (BusinessLogicException x)
        {
            fail(x.getMessage());
        }
    }
    
    /**
     * Prueba de falla para obtene evento de un Cliente 1.
     *
     * Falla si se obtiene un evento de un cliente inexistente
     */
    @Test
    public void getEventoTestFail1()
    {
        try
        {
            EventoEntity obtenida = clienteLogic.getEvento( Long(9999999), eventosData.get(0).getId());
            fail("Se removió un evento de un cliente inexistente");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba de falla para obtener evento de un Cliente 2.
     *
     * Falla si se obtiene un evento de un cliente que no lo tiene
     */
    @Test
    public void getEventoTestFail2()
    {
        try
        {
            clienteLogic.getEvento( data.get(1).getId(),eventosData.get(0).getId());
            fail("Se obtiene un evento de un cliente que no tiene dicho evento");
        }
        catch (BusinessLogicException x)
        {
            passed();
        }
    }
    
    /**
     * Prueba obtener blogs de un Cliente.
     *
     * Falla si se obtienen blogs diferentes a los que se tiene
     */
    @Test
    public void getBlogsTest()
    {
        List<BlogEntity> obtenida;
        try {
            obtenida = clienteLogic.getBlogs(data.get(0).getId());
            if(obtenida.size()!=data.get(0).getBlogs().size())
            {
                fail("Algún blog que debe tener en el cliente no está persistido correctamente en la lista relación");
            }
            for(BlogEntity bE : obtenida)
            {
                if(!data.get(0).getBlogs().contains(bE))
                {
                    fail("Algún blog que debe tener en el cliente no está persistido correctamente en la lista relación");
                }
            }
            passed();
        }
        catch (BusinessLogicException ex) {
            fail("No debería fallar.");
        }
    }
    
    /**
     * Prueba para obtener los blogs de un cliente que no tiene blogs.
     *
     * Falla si encuentra blogs en un cliente que no tiene blogs.
     */
    
    @Test
    public void getBlogsFailTest(){
        try{
            List<BlogEntity> obtenida = clienteLogic.getBlogs(data.get(1).getId());
            if(obtenida.size()>0)
            {
                fail("La lista de blogs obtenida no debería contner blogs.");
            }
            passed();
        }
        catch(BusinessLogicException e){
            
        }
    }
    
    /**
     * Prueba para obtener los blogs de un cliente que no existe.
     *
     * Falla si encuentra blogs en un cliente que no existe.
     */
    
    @Test
    public void getBlogsFail2Test(){
        try{
            ClienteEntity clienteNuevo = factory.manufacturePojo(ClienteEntity.class);
            List<BlogEntity> obtenida = clienteLogic.getBlogs(clienteNuevo.getId());
            fail("No deberia encontrar cliente con ese id.");
        }
        catch(BusinessLogicException e){
            TestResult passed = passed();
        }
    }
    
    /**
     * Prueba para obtener un blog.
     *
     * Falla si no encuentra un blog con el id.
     */
    
    @Test
    public void getBlogTest(){
        try{
            BlogEntity blog = blogsData.get(0);
            BlogEntity blogT = clienteLogic.getBlog(blog.getId());
            if(blogT== null)
                fail("Debería encontrar un blog con el id pasado por parámetro.");
            if(!blog.equals(blogT))
                fail("Deberían ser el mismo blog.");
        }
        catch(BusinessLogicException e){
            fail("No debería fallar.");
        }
    }
    
    /**
     * Prueba para obtener un blog que no existe.
     *
     * Falla si encuentra un blog con el id.
     */
    
    @Test
    public void getBlogFailTest(){
        try{
            BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
            BlogEntity blogT = clienteLogic.getBlog(blog.getId());
            if(blogT!= null)
                fail("No debería encontrar un blog con el id pasado por parámetro.");
        }
        catch(BusinessLogicException e){
            passed();
        }
    }
    
    /**
     * Prueba para obtener un blog que hace parte de un cliente.
     *
     * Falla si encuentra un blog pero no hace parte del cliente.
     */
    
    @Test
    public void getBlogCTest(){
        try{
            
            BlogEntity blog = clienteLogic.getBlogC(blogsData.get(0).getId(), data.get(0).getId());
            if(blog== null)
                fail("Debería encontrar un blog con el id pasado por parámetro.");
        }
        catch(BusinessLogicException e){
            fail();
        }
    }
    
    
    /**
     * Prueba para obtener un blog que hace parte de un cliente.
     *
     * Falla si encuentra un blog pero no hace parte del cliente.
     */
    
    @Test
    public void addBlogTest(){
        try{
            
            BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
            data.get(0).getBlogs().add(blog);
            ClienteEntity cliente = data.get(0);
            clienteLogic.addBlog(blog,cliente.getId() );
            
            Assert.assertTrue(data.get(0).getBlogs().contains(blog));
            Assert.assertNotNull(em.find(BlogEntity.class, blog.getId()));
            Assert.assertEquals(blog, em.find(BlogEntity.class, blog.getId()));
        }
        catch(BusinessLogicException e){
            fail();
        }
    }
    
    
    
}