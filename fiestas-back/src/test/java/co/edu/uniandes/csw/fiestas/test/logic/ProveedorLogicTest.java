package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
import static com.ctc.wstx.util.DataUtil.Long;
import java.util.ArrayList;
import java.util.Date;
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

    private List<ProveedorEntity> data;

    private List<ContratoEntity> contratosData;

    private List<ProductoEntity> productosData;
    
    private List<UsuarioEntity> usuariosData;

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
        em.createQuery("delete from ContratoEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
        em.createQuery("delete from ProductoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        contratosData = new ArrayList<>();
        productosData = new ArrayList<>();
        usuariosData = new ArrayList<>();
        data = new ArrayList<>();

        for (int i = 0; i < 3; i++) 
        {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);

            if (i == 0) {
                for (int j = 0; j < 3; j++) {
                    ContratoEntity entityC = factory.manufacturePojo(ContratoEntity.class);
                    entityC.setValor((int)(Math.random()*1000000));                    
                    entityC.setProveedor(entity);
                    em.persist(entityC);
                    contratosData.add(entityC);
                }
                for (int j = 0; j < 3; j++) {
                    ProductoEntity entityS = factory.manufacturePojo(ProductoEntity.class);
                    em.persist(entityS);
                    productosData.add(entityS);
                }
                entity.setPenalizado(false);
                entity.setContratos(contratosData);
                entity.setProductos(productosData);
            } 
            else if (i == 1)
            {
                entity.setPenalizado(true);
            }
            else
            {
                entity.setPenalizado(false);
            }

            
            UsuarioEntity nuevoUsuario = proveedorLogic.crearUsuario(entity);           
            data.add(entity);
            em.persist(entity);
            em.persist(nuevoUsuario);
        }
    }

    /**
     * Prueba para crear un Proveedor
     *
     */
    @Test
    public void createProveedorTest() {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        newEntity.setPenalizado(false);
        ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
        contrato.setProveedor(newEntity);
        List<ContratoEntity> contratos = new ArrayList<>();
        contratos.add(contrato);
        
        newEntity.setContratos(contratos);
        newEntity.setProductos(productosData);

        ProveedorEntity result = new ProveedorEntity();
        try 
        {
            result = proveedorLogic.createProveedor(newEntity);
        } 
        catch (BusinessLogicException e) 
        {
            fail(e.getMessage());
        }

        Assert.assertNotNull(result);
        ProveedorEntity entidad = em.find(ProveedorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getContrasena(), entidad.getContrasena());
        Assert.assertEquals(newEntity.getCorreo(), entidad.getCorreo());
        Assert.assertEquals(newEntity.getDireccion(), entidad.getDireccion());
        Assert.assertEquals(newEntity.getDocumento(), entidad.getDocumento());
        Assert.assertEquals(newEntity.getLogin(), entidad.getLogin());
        Assert.assertEquals(newEntity.getNombre(), entidad.getNombre());
        Assert.assertEquals(newEntity.getTelefono(), entidad.getTelefono());
        Assert.assertEquals(newEntity.isPenalizado(), entidad.isPenalizado());
        Assert.assertEquals(newEntity.getValoracion(), entidad.getValoracion());
        Assert.assertEquals(newEntity.getProductos().size(), entidad.getProductos().size());
        Assert.assertEquals(newEntity.getContratos().size(), entidad.getContratos().size());
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
        catch (BusinessLogicException e) 
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
    public void updateProveedorTest() 
    {
        ProveedorEntity entity = data.get(0);
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        newEntity.setPenalizado(false);
        newEntity.setContratos(contratosData);
        newEntity.setProductos(productosData);

        newEntity.setId(entity.getId());
        newEntity.setLogin(entity.getLogin());
        try 
        {
            proveedorLogic.updateProveedor(newEntity);
        } catch (BusinessLogicException e) {
            fail(e.getMessage());
        }

        ProveedorEntity entidad = em.find(ProveedorEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getContrasena(), entidad.getContrasena());
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
     * Falla por que un segundo proveedor a crear se va a guardar con id de uno
     * ya existente
     */
    @Test
    public void createProveedorTestFail1() {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = new ProveedorEntity();
        try 
        {
            result = proveedorLogic.createProveedor(newEntity);
            result = proveedorLogic.createProveedor(newEntity);
            fail("No se debio haber creado el proveedor");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba fallida para crear un Proveedor 2.
     *
     * Falla por que un proveedor se crea con una contraseña vacía
     */
    @Test
    public void createProveedorTestFail2() {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = new ProveedorEntity();
        newEntity.setContrasena("");

        try {
            result = proveedorLogic.createProveedor(newEntity);
            fail("No se debio haber creado el proveedor");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba fallida para actualizar un Proveedor 1.
     *
     * Falla por que un proveedor se actualiza cambiando un login
     */
    @Test
    public void updateProveedorTestFail1() {
        ProveedorEntity entity = data.get(0);
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setLogin("Soy diferente");
        newEntity.setPenalizado(false);
        newEntity.setContratos(contratosData);
        newEntity.setProductos(productosData);
        ProveedorEntity result = new ProveedorEntity();
        try {
            result = proveedorLogic.updateProveedor(newEntity);
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba fallida para actualizar un Proveedor 2.
     *
     * Falla por que un proveedor se actualiza cambiando la contraseña a vacío
     */
    @Test
    public void updateProveedorTestFail2() {
        ProveedorEntity entity = data.get(0);
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        
        
        newEntity.setId(entity.getId());
        newEntity.setPenalizado(false);
        newEntity.setContratos(contratosData);
        newEntity.setProductos(productosData);
        
        ProveedorEntity result = new ProveedorEntity();
        try {
            result = proveedorLogic.createProveedor(newEntity);
            newEntity.setContrasena("");
            result = proveedorLogic.updateProveedor(newEntity);
            fail("No se debio haber creado el proveedor");
        } catch (BusinessLogicException x) {
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
            ContratoEntity newContrato = contratosData.get(0);
            newContrato.setProveedor(data.get(2));            
            newContrato = proveedorLogic.addContrato(newContrato.getId(), data.get(2).getId());
            
           
            ProveedorEntity result = em.find(ProveedorEntity.class, data.get(0).getId());
            boolean encontrado=false;
            for(ContratoEntity contrato: result.getContratos())
            {
                if(contrato.getId() == newContrato.getId())
                {
                    encontrado=true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
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
    public void addContratoTestFail1() {
        try {
            proveedorLogic.addContrato(contratosData.get(0).getId(), Long(999999));
            fail("No debe agregarse el contrato a un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para agregar contrato a un Proveedor 2.
     *
     * Falla si se agrega un contrato a un proveedor que ya tiene ese contrato
     */
    @Test
    public void addContratoTestFail2() {
        try {
            proveedorLogic.addContrato(contratosData.get(0).getId(), data.get(0).getId());
            fail("No debe agregarse un contrato a un proveedor que ya tiene dicho contrato");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para remover contrato a un Proveedor.
     *
     * Falla si se remueve el único contrato de un proveedor y éste sigue
     * teniendo contratos
     */
    @Test
    public void removeContratoTest() {
        try {
            proveedorLogic.removeContrato(contratosData.get(0).getId(), data.get(0).getId());
            Assert.assertEquals(2, em.find(ProveedorEntity.class, data.get(0).getId()).getContratos().size());
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para remover contrato a un Proveedor 1.
     *
     * Falla si se remueve un contrato de un proveedor inexistente
     */
    @Test
    public void removeContratoTestFail1() {
        try {
            proveedorLogic.removeContrato(contratosData.get(0).getId(), Long(99999999));
            fail("Se removió un contrato de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para remover contrato a un Proveedor 2.
     *
     * Falla si se remueve un contrato de un proveedor que no lo tiene
     */
    @Test
    public void removeContratoTestFail2() {
        try {
            proveedorLogic.removeContrato(contratosData.get(0).getId(), data.get(1).getId());
            fail("Se removió un contrato de un proveedor que no tiene dicho contrato");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para remover contrato a un Proveedor.
     *
     * Falla si se reemplazan contratos de un proveedor inexistente
     */
    @Test
    public void replaceContratosTestFail() {
        try {
            proveedorLogic.replaceContratos(Long(99999999), contratosData);
            fail("Se removió un contrato de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba obtener contratos de un Proveedor.
     *
     * Falla si se obtienen contratos diferentes a los que se tiene
     */
    @Test
    public void getContratosTest() {
        try {
            List<ContratoEntity> obtenida = proveedorLogic.getContratos(data.get(0).getId());
            for (ContratoEntity ce : obtenida) {
                if (!em.find(ProveedorEntity.class, data.get(0).getId()).getContratos().contains(ce)) {
                    fail("Algún contrato que sí debería estar no está persistido para el proveedor en la lista relación");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para obtener contratos de un Proveedor.
     *
     * Falla si se obtienen contratos de un proveedor inexistente
     */
    @Test
    public void getContratosTestFail() {
        try {
            List<ContratoEntity> obtenida = proveedorLogic.getContratos(Long(99999999));
            fail("Se removió un contrato de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba obtener contrato de un Proveedor.
     *
     * Falla si se obtiene contrato diferente al que se tiene
     */
    @Test
    public void getContratoTest() {
        try {
            ContratoEntity obtenida = proveedorLogic.getContrato(data.get(0).getId(), contratosData.get(0).getId());
            Assert.assertEquals(contratosData.get(0), obtenida);
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para obtene contrato de un Proveedor 1.
     *
     * Falla si se obtiene un contrato de un proveedor inexistente
     */
    @Test
    public void getContratoTestFail1() {
        try {
            ContratoEntity obtenida = proveedorLogic.getContrato(contratosData.get(0).getId(), Long(9999999));
            fail("Se removió un contrato de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para obtener contrato de un Proveedor 2.
     *
     * Falla si se obtiene un contrato de un proveedor que no lo tiene
     */
    @Test
    public void getContratoTestFail2() {
        try {
            ContratoEntity obtenida = proveedorLogic.getContrato(data.get(1).getId(), contratosData.get(0).getId());
            fail("Se obtiene un contrato de un proveedor que no tiene dicho contrato");
        } catch (BusinessLogicException x) {
            passed();
        }
    }
    
    /**
     * Prueba para agregar producto a un Proveedor.
     *
     * Se agregan los tres productos creados a los tres proveedores creados
     */
    @Test
    public void addProductoTest() {
        try {
            for (int i = 1; i < 3; i++) {
                proveedorLogic.addProducto(data.get(i).getId(), productosData.get(i).getId());
            }

            Assert.assertEquals(productosData.get(1), em.find(ProveedorEntity.class, data.get(1).getId()).getProductos().get(0));
            Assert.assertEquals(productosData.get(2), em.find(ProveedorEntity.class, data.get(2).getId()).getProductos().get(0));
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para agregar producto a un Proveedor 1.
     *
     * Falla si se agrega un producto a un proveedor inexistente
     */
    @Test
    public void addProductoTestFail1() {
        try {
            proveedorLogic.addProducto(Long(999999), productosData.get(0).getId());
            fail("No debe agregarse el producto a un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para agregar producto a un Proveedor 2.
     *
     * Falla si se agrega un producto a un proveedor que ya tiene ese producto
     */
    @Test
    public void addProductoTestFail2() {
        try {
            proveedorLogic.addProducto(data.get(0).getId(), productosData.get(0).getId());
            fail("No debe agregarse un producto a un proveedor que ya tiene dicho producto");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para remover producto a un Proveedor 1.
     *
     * Falla si se remueve el único producto de un proveedor y éste sigue
     * teniendo productos
     */
    @Test
    public void removeProductoTest1() {
        try {
            proveedorLogic.removeProducto(data.get(0).getId(), productosData.get(0).getId());
            Assert.assertEquals(2, em.find(ProveedorEntity.class, data.get(0).getId()).getProductos().size());
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para remover producto a un Proveedor 1.
     *
     * Falla si se remueve un producto de un proveedor inexistente
     */
    @Test
    public void removeProductoTestFail1() {
        try {
            proveedorLogic.removeProducto(productosData.get(0).getId(), Long(99999999));
            fail("Se removió un producto de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para remover producto a un Proveedor 2.
     *
     * Falla si se remueve un producto de un proveedor que no lo tiene
     */
    @Test
    public void removeProductoTestFail2() {
        try {
            proveedorLogic.removeProducto(data.get(1).getId(), productosData.get(0).getId());
            fail("Se removió un producto de un proveedor que no tiene dicho producto");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para remover producto a un Proveedor.
     *
     * Falla si se reemplaza todos los elementos de la lista y estos son
     * diferentes a la lista despues de ser reemplazada
     */
    @Test
    public void replaceProductosTest() {
        try {
            proveedorLogic.replaceProductos(data.get(0).getId(), productosData);
            for (ProductoEntity ee : productosData) {
                if (!em.find(ProveedorEntity.class, data.get(0).getId()).getProductos().contains(ee)) {
                    fail("No está alguno de los productos reemplazados en la nueva lista del proveedor");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para remover producto a un Proveedor.
     *
     * Falla si se reemplazan productos de un proveedor inexistente
     */
    @Test
    public void replaceProductosTestFail() {
        try {
            proveedorLogic.replaceProductos(Long(99999999), productosData);
            fail("Se removió un producto de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba obtener productos de un Proveedor.
     *
     * Falla si se obtienen productos diferentes a los que se tiene
     */
    @Test
    public void getProductosTest() {
        try {
            List<ProductoEntity> obtenida = proveedorLogic.getProductos(data.get(0).getId());
            for (ProductoEntity so : obtenida) {
                if (!data.get(0).getProductos().contains(so)) {
                    fail("Alguno de los productos que debería estar persistido en la lista relación de proveedor no lo está");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para obtener productos de un Proveedor.
     *
     * Falla si se obtienen productos de un proveedor inexistente
     */
    @Test
    public void getProductosTestFail() {
        try {
            List<ProductoEntity> obtenida = proveedorLogic.getProductos(Long(99999999));
            fail("Se removió un producto de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba obtener producto de un Proveedor.
     *
     * Falla si se obtiene producto diferente al que se tiene
     */
    @Test
    public void getProductoTest() {
        try {
            ProductoEntity obtenida = proveedorLogic.getProducto(data.get(0).getId(), productosData.get(0).getId());
            Assert.assertEquals(productosData.get(0), obtenida);
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para obtene producto de un Proveedor 1.
     *
     * Falla si se obtiene un producto de un proveedor inexistente
     */
    @Test
    public void getProductoTestFail1() {
        try {
            ProductoEntity obtenida = proveedorLogic.getProducto(productosData.get(0).getId(), Long(9999999));
            fail("Se removió un producto de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para obtener producto de un Proveedor 2.
     *
     * Falla si se obtiene un producto de un proveedor que no lo tiene
     */
    @Test
    public void getProductoTestFail2() {
        try {
            proveedorLogic.removeProducto(data.get(1).getId(), productosData.get(0).getId());
            fail("Se obtiene un producto de un proveedor que no tiene dicho producto");
        } catch (BusinessLogicException x) {
            passed();
        }
    }    
}
