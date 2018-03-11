package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
import static com.ctc.wstx.util.DataUtil.Long;
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
    
    private List<ContratoEntity> contratosData = new ArrayList<ContratoEntity>();
    
    private List<ValoracionEntity> valoracionesData = new ArrayList<ValoracionEntity>();
    
    private List<ServicioEntity> serviciosData = new ArrayList<ServicioEntity>();

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
        try 
        {
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
        em.createQuery("delete from ContratoEntity").executeUpdate();
        em.createQuery("delete from ValoracionEntity").executeUpdate();
        em.createQuery("delete from ServicioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() 
    {
        for (int i = 0; i < 3; i++) {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);
            ContratoEntity entityC = factory.manufacturePojo(ContratoEntity.class);
            ValoracionEntity entityV = factory.manufacturePojo(ValoracionEntity.class);
            ServicioEntity entityS = factory.manufacturePojo(ServicioEntity.class);
            if(i == 0)
            {
                entity.setPenalizado(false);
            }
            else
            {
                entity.setPenalizado(true);
            }
            em.persist(entity);
            em.persist(entityC);
            em.persist(entityV);
            em.persist(entityS);
            data.add(entity);
            contratosData.add(entityC);
            valoracionesData.add(entityV);
            serviciosData.add(entityS);
        }
    }

    /**
     * Prueba para crear un Proveedor
     *
     */
    @Test
    public void createProveedorTest()
    {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = new ProveedorEntity();
        try
        {
            result = proveedorLogic.createProveedor(newEntity);
        }
        catch(BusinessLogicException e)
        {
            fail(e.getMessage());
        }
        
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
        newEntity.setLogin(entity.getLogin());
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
    
    /**
     * Prueba fallida para crear un Proveedor 1.
     *
     * Falla por que un segundo proveedor a crear se va a guardar con id de uno ya existente
     */
    @Test
    public void createProveedorTestFail1() 
    {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);        
        ProveedorEntity result = new ProveedorEntity();
        
        try 
        {
            result = proveedorLogic.createProveedor(newEntity);
            result = proveedorLogic.createProveedor(newEntity);
            fail("No se debio haber creado el proveedor");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba fallida para crear un Proveedor 2.
     *
     * Falla por que un proveedor se crea con una contraseña vacía
     */
    @Test
    public void createProveedorTestFail2() 
    {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);        
        ProveedorEntity result = new ProveedorEntity();
        newEntity.setContraseña("");
        
        try 
        {
            result = proveedorLogic.createProveedor(newEntity);
            fail("No se debio haber creado el proveedor");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba fallida para actualizar un Proveedor 1.
     *
     * Falla por que un proveedor se actualiza cambiando un login
     */
    @Test
    public void updateProveedorTestFail1() 
    {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);        
        ProveedorEntity result = new ProveedorEntity();        
        
        try 
        {
            result = proveedorLogic.createProveedor(newEntity);
            newEntity.setLogin("Soy diferente");
            result = proveedorLogic.updateProveedor(newEntity);
            fail("No se debio haber creado el proveedor");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba fallida para actualizar un Proveedor 2.
     *
     * Falla por que un proveedor se actualiza cambiando la contraseña a vacío
     */
    @Test
    public void updateProveedorTestFail2() 
    {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);        
        ProveedorEntity result = new ProveedorEntity();        
        
        try 
        {
            result = proveedorLogic.createProveedor(newEntity);
            newEntity.setContraseña("");
            result = proveedorLogic.updateProveedor(newEntity);
            fail("No se debio haber creado el proveedor");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba para agregar contrato a un Proveedor.
     *
     * Se agregan los tres contratos creados a los tres proveedors creados
     */
    @Test
    public void addContratoTest() 
    {            
        try 
        {       
            proveedorLogic.addContrato(contratosData.get(0).getId(), data.get(0).getId());          
            Assert.assertEquals(contratosData.get(0), em.find(ProveedorEntity.class, data.get(0).getId()).getContratos().get(0));            
        } 
        catch (BusinessLogicException x) 
        {
            fail(x.getMessage());
        }
    }
    
    /**
     * Prueba de falla para agregar contrato a un Proveedor 1.
     *
     * Falla si se agrega un contrato a un proveedor inexistente
     */
    @Test
    public void addContratoTestFail1() 
    {            
        try 
        {           
            proveedorLogic.addContrato(contratosData.get(0).getId(), Long(999999)); 
            fail("No debe agregarse el contrato a un proveedor inexistente");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba de falla para agregar contrato a un Proveedor 2.
     *
     * Falla si se agrega un contrato a un proveedor que ya tiene ese contrato
     */
    @Test
    public void addContratoTestFail2() 
    {            
        try 
        {           
            proveedorLogic.addContrato(contratosData.get(0).getId(), data.get(0).getId()); 
            proveedorLogic.addContrato(contratosData.get(0).getId(), data.get(0).getId()); 
            fail("No debe agregarse un contrato a un proveedor que ya tiene dicho contrato");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba para remover contrato a un Proveedor.
     *
     * Falla si se remueve el único contrato de un proveedor y éste sigue teniendo contratos
     */
    @Test
    public void removeContratoTest() 
    {       
        try 
        {
            proveedorLogic.addContrato(contratosData.get(0).getId(),data.get(0).getId());
            proveedorLogic.removeContrato(contratosData.get(0).getId(), data.get(0).getId());
            Assert.assertEquals(0, em.find(ProveedorEntity.class, data.get(0).getId()).getContratos().size());
        } 
        catch (BusinessLogicException x) 
        {
            fail(x.getMessage());
        }
    }
    
    /**
     * Prueba de falla para remover contrato a un Proveedor 1.
     *
     * Falla si se remueve un contrato de un proveedor inexistente 
     */
    @Test
    public void removeContratoTestFail1() 
    {            
        try 
        {            
            proveedorLogic.removeContrato(contratosData.get(0).getId(), Long(99999999));
            fail("Se removió un contrato de un proveedor inexistente");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba de falla para remover contrato a un Proveedor 2.
     *
     * Falla si se remueve un contrato de un proveedor que no lo tiene
     */
    @Test
    public void removeContratoTestFail2() 
    {            
        try 
        {            
            proveedorLogic.removeContrato(contratosData.get(0).getId(), data.get(0).getId());
            fail("Se removió un contrato de un proveedor que no tiene dicho contrato");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba para remover contrato a un Proveedor.
     *
     * Falla si se reemplaza todos los elementos de la lista y estos son diferentes a la lista despues de ser reemplazada
     */
    @Test
    public void replaceContratosTest() 
    {       
        try 
        {
            proveedorLogic.replaceContratos(data.get(0).getId(), contratosData);
            for(ContratoEntity ce : contratosData)
            {
                if(!em.find(ProveedorEntity.class, data.get(0).getId()).getContratos().contains(ce))
                {
                    fail("No está alguno de los contratos reemplazados en la nueva lista del proveedor");
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
     * Prueba de falla para remover contrato a un Proveedor.
     *
     * Falla si se reemplazan contratos de un proveedor inexistente 
     */
    @Test
    public void replaceContratosTestFail() 
    {            
        try 
        {            
            proveedorLogic.replaceContratos(Long(99999999),contratosData );
            fail("Se removió un contrato de un proveedor inexistente");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }    
    
    /**
     * Prueba obtener contratos de un Proveedor.
     *
     * Falla si se obtienen contratos diferentes a los que se tiene
     */    
    @Test
    public void getContratosTest() 
    {       
        try 
        {
            List<ContratoEntity> obtenida = proveedorLogic.getContratos(data.get(0).getId());
            Assert.assertEquals(data.get(0).getContratos(), obtenida);            
        } 
        catch (BusinessLogicException x) 
        {
            fail(x.getMessage());
        }
    }
    
    /**
     * Prueba de falla para obtener contratos de un Proveedor.
     *
     * Falla si se obtienen contratos de un proveedor inexistente 
     */
    @Test
    public void getContratosTestFail() 
    {            
        try 
        {            
            List<ContratoEntity> obtenida = proveedorLogic.getContratos(Long(99999999));
            fail("Se removió un contrato de un proveedor inexistente");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }  
    
    /**
     * Prueba obtener contrato de un Proveedor.
     *
     * Falla si se obtiene contrato diferente al que se tiene
     */    
    @Test
    public void getContratoTest() 
    {         
        try 
        {
            proveedorLogic.addContrato(contratosData.get(0).getId(), data.get(0).getId());
            ContratoEntity obtenida = proveedorLogic.getContrato(data.get(0).getId(),contratosData.get(0).getId());
            Assert.assertEquals(contratosData.get(0), obtenida); 
        }
        catch (BusinessLogicException x) 
        {
            fail(x.getMessage());
        }       
    }
    
    /**
     * Prueba de falla para obtene contrato de un Proveedor 1.
     *
     * Falla si se obtiene un contrato de un proveedor inexistente 
     */
    @Test
    public void getContratoTestFail1() 
    {            
        try 
        {            
            ContratoEntity obtenida = proveedorLogic.getContrato(contratosData.get(0).getId(), Long(9999999));
            fail("Se removió un contrato de un proveedor inexistente");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
    
    /**
     * Prueba de falla para obtener contrato de un Proveedor 2.
     *
     * Falla si se obtiene un contrato de un proveedor que no lo tiene
     */
    @Test
    public void getContratoTestFail2() 
    {            
        try 
        {            
            proveedorLogic.removeContrato(contratosData.get(0).getId(), data.get(0).getId());
            fail("Se obtiene un contrato de un proveedor que no tiene dicho contrato");
        } 
        catch (BusinessLogicException x) 
        {
            passed();
        }
    }
}
