package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
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
public class ProveedorLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ProveedorLogic proveedorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ProveedorEntity> data;

    private List<ContratoEntity> contratosData;

    private List<ValoracionEntity> valoracionesData;

    private List<ServicioEntity> serviciosData;

    @Deployment
    public static JavaArchive createDeployment() {
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
    private void clearData() {;
        em.createQuery("delete from ContratoEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from ValoracionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        contratosData = new ArrayList<>();
        valoracionesData = new ArrayList<>();
        serviciosData = new ArrayList<>();
        data = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ProveedorEntity entity = factory.manufacturePojo(ProveedorEntity.class);

            if (i == 0) {
                for (int j = 0; j < 3; j++) {
                    ContratoEntity entityC = factory.manufacturePojo(ContratoEntity.class);
                    entityC.setProveedor(entity);
                    em.persist(entityC);
                    contratosData.add(entityC);
                }
                for (int j = 0; j < 3; j++) {
                    ValoracionEntity entityV = factory.manufacturePojo(ValoracionEntity.class);
                    em.persist(entityV);
                    valoracionesData.add(entityV);
                }
                for (int j = 0; j < 3; j++) {
                    ServicioEntity entityS = factory.manufacturePojo(ServicioEntity.class);
                    em.persist(entityS);
                    serviciosData.add(entityS);
                }
                entity.setPenalizado(false);
                entity.setContratos(contratosData);
                entity.setValoraciones(valoracionesData);
                entity.setServicios(serviciosData);
            } else {
                entity.setPenalizado(true);
            }

            em.persist(entity);
            data.add(entity);
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
        newEntity.setContratos(contratosData);
        newEntity.setValoraciones(valoracionesData);
        newEntity.setServicios(serviciosData);

        ProveedorEntity result = new ProveedorEntity();
        try {
            result = proveedorLogic.createProveedor(newEntity);
        } catch (BusinessLogicException e) {
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
        Assert.assertEquals(newEntity.getServicios().size(), entidad.getServicios().size());
        Assert.assertEquals(newEntity.getContratos().size(), entidad.getContratos().size());
        Assert.assertEquals(newEntity.getValoraciones().size(), entidad.getValoraciones().size());
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
    public void deleteProveedor() {
        ProveedorEntity entity = data.get(0);
        try {
            proveedorLogic.deleteProveedor(entity.getId());
        } catch (BusinessLogicException e) {
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
        newEntity.setPenalizado(false);
        newEntity.setContratos(contratosData);
        newEntity.setValoraciones(valoracionesData);
        newEntity.setServicios(serviciosData);

        newEntity.setId(entity.getId());
        newEntity.setLogin(entity.getLogin());
        try {
            proveedorLogic.updateProveedor(newEntity);
        } catch (BusinessLogicException e) {
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
     * Falla por que un segundo proveedor a crear se va a guardar con id de uno
     * ya existente
     */
    @Test
    public void createProveedorTestFail1() {
        ProveedorEntity newEntity = factory.manufacturePojo(ProveedorEntity.class);
        ProveedorEntity result = new ProveedorEntity();
        try {
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
        newEntity.setContraseña("");

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
        newEntity.setValoraciones(valoracionesData);
        newEntity.setServicios(serviciosData);
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
        newEntity.setValoraciones(valoracionesData);
        newEntity.setServicios(serviciosData);
        
        ProveedorEntity result = new ProveedorEntity();
        try {
            result = proveedorLogic.createProveedor(newEntity);
            newEntity.setContraseña("");
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
    public void addContratoTest() {
        try {
            ProveedorEntity entity = data.get(0);
            ContratoEntity newContrato = factory.manufacturePojo(ContratoEntity.class);
            newContrato.setProveedor(entity);
            
            proveedorLogic.addContrato(newContrato.getId(), entity.getId());
            
            ProveedorEntity result = em.find(ProveedorEntity.class, entity.getId());
            boolean encontrado=false;
            for(ContratoEntity contrato: result.getContratos()){
                if(contrato.getId().equals(newContrato.getId()))
                {
                    encontrado=true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        } catch (BusinessLogicException x) {
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
     * Prueba para remover contrato a un Proveedor.
     *
     * Falla si se reemplaza todos los elementos de la lista y estos son
     * diferentes a la lista despues de ser reemplazada
     */
    @Test
    public void replaceContratosTest() {
        try {
            proveedorLogic.replaceContratos(data.get(0).getId(), contratosData);
            for (ContratoEntity ce : contratosData) {
                if (!em.find(ProveedorEntity.class, data.get(0).getId()).getContratos().contains(ce)) {
                    fail("No está alguno de los contratos reemplazados en la nueva lista del proveedor");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
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
     * Prueba para agregar valoraciones a un Proveedor.
     *
     * Se agregan los tres valoraciones creados a los tres proveedores creados
     */
    @Test
    public void addValoracionTest() {
        try {
            for (int i = 1; i < 3; i++) {
                proveedorLogic.addValoracion(valoracionesData.get(i).getId(), data.get(i).getId());
            }

            Assert.assertEquals(valoracionesData.get(1), em.find(ProveedorEntity.class, data.get(1).getId()).getValoraciones().get(0));
            Assert.assertEquals(valoracionesData.get(2), em.find(ProveedorEntity.class, data.get(2).getId()).getValoraciones().get(0));
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para agregar valoraciones a un Proveedor 1.
     *
     * Falla si se agrega una valoración a un proveedor inexistente
     */
    @Test
    public void addValoracionTestFail1() {
        try {
            proveedorLogic.addValoracion(valoracionesData.get(0).getId(), Long(999999));
            fail("No debe agregarse el valoraciones a un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para agregar valoraciones a un Proveedor 2.
     *
     * Falla si se agrega una valoración a un proveedor que ya tiene ese
     * valoraciones
     */
    @Test
    public void addValoracionTestFail2() {
        try {
            proveedorLogic.addValoracion(valoracionesData.get(0).getId(), data.get(0).getId());
            proveedorLogic.addValoracion(valoracionesData.get(0).getId(), data.get(0).getId());
            fail("No debe agregarse una valoración a un proveedor que ya tiene dicha valoración");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para remover valoraciones a un Proveedor.
     *
     * Falla si se remueve el único valoraciones de un proveedor y éste sigue
     * teniendo valoraciones
     */
    @Test
    public void removeValoracionTest() {
        try {
            proveedorLogic.removeValoracion(valoracionesData.get(0).getId(), data.get(0).getId());
            Assert.assertEquals(2, em.find(ProveedorEntity.class, data.get(0).getId()).getValoraciones().size());
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para remover valoraciones a un Proveedor 1.
     *
     * Falla si se remueve una valoración de un proveedor inexistente
     */
    @Test
    public void removeValoracionTestFail1() {
        try {
            proveedorLogic.removeValoracion(valoracionesData.get(0).getId(), Long(99999999));
            fail("Se removió una valoración de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para remover valoraciones a un Proveedor 2.
     *
     * Falla si se remueve una valoración de un proveedor que no lo tiene
     */
    @Test
    public void removeValoracionTestFail2() {
        try {
            proveedorLogic.removeValoracion(valoracionesData.get(0).getId(), data.get(1).getId());
            fail("Se removió una valoración de un proveedor que no tiene dicha valoración");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para remover valoraciones a un Proveedor.
     *
     * Falla si se reemplaza todos los elementos de la lista y estos son
     * diferentes a la lista despues de ser reemplazada
     */
    @Test
    public void replaceValoracionesTest() {
        try {
            proveedorLogic.replaceValoraciones(data.get(0).getId(), valoracionesData);
            for (ValoracionEntity ee : valoracionesData) {
                if (!em.find(ProveedorEntity.class, data.get(0).getId()).getValoraciones().contains(ee)) {
                    fail("No está alguno de los valoraciones reemplazados en la nueva lista del proveedor");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para remover valoraciones a un Proveedor.
     *
     * Falla si se reemplazan valoraciones de un proveedor inexistente
     */
    @Test
    public void replaceValoracionesTestFail() {
        try {
            proveedorLogic.replaceValoraciones(Long(99999999), valoracionesData);
            fail("Se removió una valoración de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba obtener valoraciones de un Proveedor.
     *
     * Falla si se obtienen valoraciones diferentes a los que se tiene
     */
    @Test
    public void getValoracionesTest() {
        try {
            List<ValoracionEntity> obtenida = proveedorLogic.getValoraciones(data.get(0).getId());
            for (ValoracionEntity ve : obtenida) {
                if (!em.find(ProveedorEntity.class, data.get(0).getId()).getValoraciones().contains(ve)) {
                    fail("Alguna valoración que sí debería estar no está persistida para el proveedor en la lista relación");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para obtener valoraciones de un Proveedor.
     *
     * Falla si se obtienen valoraciones de un proveedor inexistente
     */
    @Test
    public void getValoracionesTestFail() {
        try {
            List<ValoracionEntity> obtenida = proveedorLogic.getValoraciones(Long(99999999));
            fail("Se removió una valoración de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba obtener valoraciones de un Proveedor.
     *
     * Falla si se obtiene valoraciones diferente al que se tiene
     */
    @Test
    public void getValoracionTest() {
        try {
            ValoracionEntity obtenida = proveedorLogic.getValoracion(data.get(0).getId(), valoracionesData.get(0).getId());
            Assert.assertEquals(valoracionesData.get(0), obtenida);
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para obtene valoraciones de un Proveedor 1.
     *
     * Falla si se obtiene una valoración de un proveedor inexistente
     */
    @Test
    public void getValoracionTestFail1() {
        try {
            ValoracionEntity obtenida = proveedorLogic.getValoracion(Long(9999999), valoracionesData.get(0).getId());
            fail("Se removió una valoración de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para obtener valoraciones de un Proveedor 2.
     *
     * Falla si se obtiene una valoración de un proveedor que no lo tiene
     */
    @Test
    public void getValoracionTestFail2() {
        try {
            proveedorLogic.getValoracion(data.get(1).getId(), valoracionesData.get(0).getId());
            fail("Se obtiene una valoración de un proveedor que no tiene dicha valoración");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para agregar servicio a un Proveedor.
     *
     * Se agregan los tres servicios creados a los tres proveedores creados
     */
    @Test
    public void addServicioTest() {
        try {
            for (int i = 1; i < 3; i++) {
                proveedorLogic.addServicio(data.get(i).getId(), serviciosData.get(i).getId());
            }

            Assert.assertEquals(serviciosData.get(1), em.find(ProveedorEntity.class, data.get(1).getId()).getServicios().get(0));
            Assert.assertEquals(serviciosData.get(2), em.find(ProveedorEntity.class, data.get(2).getId()).getServicios().get(0));
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para agregar servicio a un Proveedor 1.
     *
     * Falla si se agrega un servicio a un proveedor inexistente
     */
    @Test
    public void addServicioTestFail1() {
        try {
            proveedorLogic.addServicio(Long(999999), serviciosData.get(0).getId());
            fail("No debe agregarse el servicio a un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para agregar servicio a un Proveedor 2.
     *
     * Falla si se agrega un servicio a un proveedor que ya tiene ese servicio
     */
    @Test
    public void addServicioTestFail2() {
        try {
            proveedorLogic.addServicio(data.get(0).getId(), serviciosData.get(0).getId());
            fail("No debe agregarse un servicio a un proveedor que ya tiene dicho servicio");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para remover servicio a un Proveedor 1.
     *
     * Falla si se remueve el único servicio de un proveedor y éste sigue
     * teniendo servicios
     */
    @Test
    public void removeServicioTest1() {
        try {
            proveedorLogic.removeServicio(data.get(0).getId(), serviciosData.get(0).getId());
            Assert.assertEquals(2, em.find(ProveedorEntity.class, data.get(0).getId()).getServicios().size());
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para remover servicio a un Proveedor 1.
     *
     * Falla si se remueve un servicio de un proveedor inexistente
     */
    @Test
    public void removeServicioTestFail1() {
        try {
            proveedorLogic.removeServicio(serviciosData.get(0).getId(), Long(99999999));
            fail("Se removió un servicio de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para remover servicio a un Proveedor 2.
     *
     * Falla si se remueve un servicio de un proveedor que no lo tiene
     */
    @Test
    public void removeServicioTestFail2() {
        try {
            proveedorLogic.removeServicio(data.get(1).getId(), serviciosData.get(0).getId());
            fail("Se removió un servicio de un proveedor que no tiene dicho servicio");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba para remover servicio a un Proveedor.
     *
     * Falla si se reemplaza todos los elementos de la lista y estos son
     * diferentes a la lista despues de ser reemplazada
     */
    @Test
    public void replaceServiciosTest() {
        try {
            proveedorLogic.replaceServicios(data.get(0).getId(), serviciosData);
            for (ServicioEntity ee : serviciosData) {
                if (!em.find(ProveedorEntity.class, data.get(0).getId()).getServicios().contains(ee)) {
                    fail("No está alguno de los servicios reemplazados en la nueva lista del proveedor");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para remover servicio a un Proveedor.
     *
     * Falla si se reemplazan servicios de un proveedor inexistente
     */
    @Test
    public void replaceServiciosTestFail() {
        try {
            proveedorLogic.replaceServicios(Long(99999999), serviciosData);
            fail("Se removió un servicio de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba obtener servicios de un Proveedor.
     *
     * Falla si se obtienen servicios diferentes a los que se tiene
     */
    @Test
    public void getServiciosTest() {
        try {
            List<ServicioEntity> obtenida = proveedorLogic.getServicios(data.get(0).getId());
            for (ServicioEntity so : obtenida) {
                if (!data.get(0).getServicios().contains(so)) {
                    fail("Alguno de los servicios que debería estar persistido en la lista relación de proveedor no lo está");
                }
            }
            passed();
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para obtener servicios de un Proveedor.
     *
     * Falla si se obtienen servicios de un proveedor inexistente
     */
    @Test
    public void getServiciosTestFail() {
        try {
            List<ServicioEntity> obtenida = proveedorLogic.getServicios(Long(99999999));
            fail("Se removió un servicio de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba obtener servicio de un Proveedor.
     *
     * Falla si se obtiene servicio diferente al que se tiene
     */
    @Test
    public void getServicioTest() {
        try {
            ServicioEntity obtenida = proveedorLogic.getServicio(data.get(0).getId(), serviciosData.get(0).getId());
            Assert.assertEquals(serviciosData.get(0), obtenida);
        } catch (BusinessLogicException x) {
            fail(x.getMessage());
        }
    }

    /**
     * Prueba de falla para obtene servicio de un Proveedor 1.
     *
     * Falla si se obtiene un servicio de un proveedor inexistente
     */
    @Test
    public void getServicioTestFail1() {
        try {
            ServicioEntity obtenida = proveedorLogic.getServicio(serviciosData.get(0).getId(), Long(9999999));
            fail("Se removió un servicio de un proveedor inexistente");
        } catch (BusinessLogicException x) {
            passed();
        }
    }

    /**
     * Prueba de falla para obtener servicio de un Proveedor 2.
     *
     * Falla si se obtiene un servicio de un proveedor que no lo tiene
     */
    @Test
    public void getServicioTestFail2() {
        try {
            proveedorLogic.removeServicio(data.get(1).getId(), serviciosData.get(0).getId());
            fail("Se obtiene un servicio de un proveedor que no tiene dicho servicio");
        } catch (BusinessLogicException x) {
            passed();
        }
    }
}
