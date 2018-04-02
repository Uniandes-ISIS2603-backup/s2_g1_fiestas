package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ServicioLogic;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import static com.ctc.wstx.util.DataUtil.Long;
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
 * @author ls.arias
 */
@RunWith(Arquillian.class)
public class ServicioLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ServicioLogic servicioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ServicioEntity> data = new ArrayList<ServicioEntity>();

    private List<ProveedorEntity> proveedorData = new ArrayList();
    
    private List<ValoracionEntity> valoracionData = new ArrayList();


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     *
     *
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from ProveedorEntity").executeUpdate();
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from ValoracionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
       List<ProveedorEntity> listaProveedor = new ArrayList<ProveedorEntity>();
       for (int i = 0; i < 3; i++) {
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(proveedor);
            proveedorData.add(proveedor);
            listaProveedor.add(proveedor);
        }
       List<ValoracionEntity> listaValoracion = new ArrayList<ValoracionEntity>();
       for (int i = 0; i < 3; i++) {
            ValoracionEntity valoracion = factory.manufacturePojo(ValoracionEntity.class);
            em.persist(valoracion);
            valoracionData.add(valoracion);
            listaValoracion.add(valoracion);
        }
       
        for (int i = 0; i < 3; i++) 
        {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entity.setProveedores(listaProveedor);
            entity.setValoraciones(listaValoracion);
            em.persist(entity);
            data.add(entity);
            
            if (i == 0) {
                proveedorData.get(i).setServicios(data);
            }
            
        } 
        
    }

    /**
     * Prueba para crear un Servicio
     *
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createServicioTest() throws BusinessLogicException {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        ServicioEntity result = servicioLogic.createServicio(newEntity);
        Assert.assertNotNull(result);
        ServicioEntity entity = em.find(ServicioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getValoraciones(), entity.getValoraciones());
 
    }

    /**
     * Prueba para consultar la lista de Servicios
     *
     *
     */
    @Test
    public void getServiciosTest() {
        List<ServicioEntity> list = servicioLogic.getServicios();
        Assert.assertEquals(data.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Servicio
     *
     *
     */
    @Test
    public void getServicioTest() {
        ServicioEntity entity = data.get(0);
        ServicioEntity resultEntity = servicioLogic.getServicio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getValoraciones().size(), resultEntity.getValoraciones().size());
    }

    /**
     * Prueba para eliminar un Servicio
     *
     *
     */
    @Test
    public void deleteServicioTest() {
         ServicioEntity entity = data.get(0);
        try 
        {
            servicioLogic.deleteServicio(entity.getId());
        } 
        catch (BusinessLogicException e) 
        {
            fail(e.getMessage());
        }

        ServicioEntity deleted = em.find(ServicioEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    
    }

    /**
     * Prueba para actualizar un Servicio
     *
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void updateServicioTest() throws BusinessLogicException {
        
        ServicioEntity entity = data.get(0);
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
        pojoEntity.setProveedores(proveedorData);
        pojoEntity.setValoraciones(valoracionData);
        pojoEntity.setId(entity.getId());
        
        pojoEntity.setDescripcion(entity.getDescripcion());

        pojoEntity.setNombre(entity.getNombre());
        pojoEntity.setTipo(entity.getTipo());
        pojoEntity.setProductos(entity.getProductos());
        
        try
        {
            servicioLogic.updateServicio(pojoEntity);
        }
        catch (BusinessLogicException e)
        {
            fail(e.getMessage());
        }
        ServicioEntity resp = em.find(ServicioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
    }
    
    /**
     * Prueba para obtener una instancia de Proveedor asociada a una instancia
     * Servicio
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void getProveedorTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        ProveedorEntity proveedorEntity = proveedorData.get(0);
        ProveedorEntity response = servicioLogic.getProveedor(entity.getId(), proveedorEntity.getId());

        Assert.assertEquals(proveedorEntity.getId(), response.getId());
        Assert.assertEquals(proveedorEntity.getNombre(), response.getNombre());
        Assert.assertEquals(proveedorEntity.getLogin(), response.getLogin());
        Assert.assertEquals(proveedorEntity.getDocumento(), response.getDocumento());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Proveedores asociadas a una
     * instancia Servicio
     *
     * 
     */
    @Test
    public void listProveedoresTest() {
        List<ProveedorEntity> list = servicioLogic.listProveedores(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para asociar un Proveedor existente a un Servicio
     *
     * 
     * @throws javax.transaction.NotSupportedException
     * @throws javax.transaction.SystemException
     * @throws javax.transaction.RollbackException
     * @throws javax.transaction.HeuristicMixedException
     * @throws javax.transaction.HeuristicRollbackException
     */
   @Test
    public void addProveedorTest() throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
        ServicioEntity servicio = data.get(0);
        ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
        proveedor.setServicios(data);
        utx.begin();
        em.persist(proveedor);
        utx.commit();
        servicioLogic.addProveedor(servicio.getId(), proveedor.getId());
       
    }
    
    /**
     * 
     * 
     */
    @Test
    public void replaceProveedoresFailTest(){
        ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
        try{
            servicioLogic.replaceProveedores(servicio.getId(), null);
            fail("El servicio no existe en persistence.");
        }
        catch(BusinessLogicException e)
        {
            
        }
       
    }
    
    /**
     * 
     */
    @Test
    public void replaceProveedoresFailTest1(){
        ServicioEntity servicio = data.get(0);
        try{
            servicioLogic.replaceProveedores(servicio.getId(), null);
            fail("La lista es nula.");
        }
        catch (BusinessLogicException e){
        }
       
    }
    /**
     * 
     */
    @Test
    public void replaceProveedoresFailTest2(){
        ServicioEntity servicio = data.get(0);
        List<ProveedorEntity> lista = new ArrayList<>();
        try{
            servicioLogic.replaceProveedores(servicio.getId(), lista);
            fail("La lista es vacía.");
        }
        catch (BusinessLogicException e){
        }
       
    }
    
    
    /**
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void replaceProveedoresTest() throws BusinessLogicException{
       try {
            servicioLogic.replaceProveedores(data.get(0).getId(), proveedorData);
            for (ProveedorEntity ee : proveedorData) {
                if (!em.find(ServicioEntity.class, data.get(0).getId()).getProveedores().contains(ee)) {
                    fail("No está alguno de los proveedores reemplazados en la nueva lista del servicio");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }    
    
    
    /**
     * Prueba de falla para remover proveedor a un servicio.
     *
     * Falla si se reemplazan proveedores de un servicio inexistente
     */
    @Test
    public void replaceProveedoresTestFail() {
        try {
            servicioLogic.replaceProveedores(Long(99999999), proveedorData);
            fail("Se removió un proveedor de un servicio inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para desasociar un Proveedor existente de un Servicio existente
     *
     * 
     */
    @Test
    public void removeProveedoresTest() {
        servicioLogic.removeProveedor(data.get(0).getId(), proveedorData.get(0).getId());
        ProveedorEntity response = servicioLogic.getProveedor(data.get(0).getId(), proveedorData.get(0).getId());
    }
  
}
