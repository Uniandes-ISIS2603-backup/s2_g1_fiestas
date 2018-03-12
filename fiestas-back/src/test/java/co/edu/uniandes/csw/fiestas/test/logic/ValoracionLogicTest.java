package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ValoracionLogic;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ValoracionPersistence;
import static com.ctc.wstx.util.DataUtil.Long;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import static org.jboss.arquillian.test.spi.TestResult.passed;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
public class ValoracionLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ValoracionLogic valoracionLogic;

    
    @PersistenceContext
    private EntityManager em;

 
    @Inject
    private UserTransaction utx;

  
    private List<ValoracionEntity> data = new ArrayList<ValoracionEntity>();

    private List<ServicioEntity> dataServicio = new ArrayList<ServicioEntity>();

    private List<ProveedorEntity> dataProveedor = new ArrayList<ProveedorEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ValoracionEntity.class.getPackage())
                .addPackage(ValoracionLogic.class.getPackage())
                .addPackage(ValoracionPersistence.class.getPackage())
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
        em.createQuery("delete from ValoracionEntity").executeUpdate();
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     * 
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ValoracionEntity entity = factory.manufacturePojo(ValoracionEntity.class);
            entity.setCalificacion(4);
            em.persist(entity);
            data.add(entity);
        }
        
        for (int j = 0; j < 3; j++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entity.setValoraciones(data);
            em.persist(entity);
                       dataServicio.add(entity);
                       
            ProveedorEntity pEntity = factory.manufacturePojo(ProveedorEntity.class);
            pEntity.setValoraciones(data);
            em.persist(pEntity);
            dataProveedor.add(pEntity);
        }
    }

    /**
     * Prueba para crear un Valoracion
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createValoracionServicioTest() throws BusinessLogicException {
        ValoracionEntity newEntity = factory.manufacturePojo(ValoracionEntity.class);
        newEntity.setCalificacion(4);
        ValoracionEntity result = valoracionLogic.createValoracionServicio(data.get(0).getServicio().getId(), newEntity);
        Assert.assertNotNull(result);
        ValoracionEntity entity = em.find(ValoracionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCalificacion(), entity.getCalificacion());
        Assert.assertEquals(newEntity.getComentario(), entity.getComentario());
    }

    /**
     * Prueba para consultar la lista de Valoraciones
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    
    @Test
    public void getValoracionesServicioTest() throws BusinessLogicException {
        List<ValoracionEntity> list = valoracionLogic.getValoracionesServicio(dataServicio.get(1).getId());        
        Assert.assertEquals(data.size(), list.size());
        for (ValoracionEntity entity : list) {
            boolean found = false;
            for (ValoracionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     
    /**
     * Prueba para consultar un Valoracion
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    
    @Test
    public void getValoracionServicioTest() throws BusinessLogicException {
        ValoracionEntity entity = data.get(0);
        ValoracionEntity resultEntity = valoracionLogic.getValoracionServicio(dataServicio.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getCalificacion(), resultEntity.getCalificacion());
        Assert.assertEquals(entity.getComentario(), resultEntity.getComentario());
    }

    /**
     * Prueba para eliminar un Valoracion
     *
     * 
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
 
    @Test
    public void deleteValoracionTest() throws BusinessLogicException {
        ValoracionEntity entity = data.get(0);
        valoracionLogic.deleteValoracionServicio(dataServicio.get(1).getId(), entity.getId());
        ValoracionEntity deleted = em.find(ValoracionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Valoracion
     *
     * 
     */
 @Test
    public void updateValoracionServicioTest() {
        ValoracionEntity entity = data.get(0);
        ValoracionEntity pojoEntity = factory.manufacturePojo(ValoracionEntity.class);

        pojoEntity.setId(entity.getId());

        valoracionLogic.updateValoracionServicio(dataServicio.get(1).getId(), pojoEntity);

        ValoracionEntity resp = em.find(ValoracionEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCalificacion(), resp.getCalificacion());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());
    }
    
      /**
     * Prueba para agregar valoraciones a un Proveedor.
     *
     * Se agregan los tres valoraciones creados a los tres proveedores creados
     */
    @Test
    public void addValoracionProveedorTest() {
        try {
            for (int i = 1; i < 3; i++) {
                valoracionLogic.addValoracionProveedor(data.get(i).getId(), dataProveedor.get(i).getId());
            }

            Assert.assertEquals(data.get(1), em.find(ProveedorEntity.class, dataProveedor.get(1).getId()).getValoraciones().get(0));
            Assert.assertEquals(data.get(2), em.find(ProveedorEntity.class, dataProveedor.get(2).getId()).getValoraciones().get(0));
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
    public void addValoracionProveedorTestFail1() {
        try {
            valoracionLogic.addValoracionProveedor(data.get(0).getId(), Long(999999));
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
    public void addValoracionProveedorTestFail2() {
        try {
            valoracionLogic.addValoracionProveedor(data.get(0).getId(), dataProveedor.get(0).getId());
            valoracionLogic.addValoracionProveedor(data.get(0).getId(), dataProveedor.get(0).getId());
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
    public void removeValoracionProveedorTest() {
        try {
            valoracionLogic.removeValoracionProveedor(data.get(0).getId(), dataProveedor.get(0).getId());
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
    public void removeValoracionProveedorTestFail1() {
        try {
            valoracionLogic.removeValoracionProveedor(data.get(0).getId(), Long(99999999));
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
    public void removeValoracionProveedorTestFail2() {
        try {
            valoracionLogic.removeValoracionProveedor(data.get(0).getId(), data.get(1).getId());
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
    public void replaceValoracionesProveedorTest() {
        try {
            valoracionLogic.replaceValoracionesProveedor(dataProveedor.get(0).getId(), data);
            for (ValoracionEntity ee : data) {
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
    public void replaceValoracionesProveedorTestFail() {
        try {
            valoracionLogic.replaceValoracionesProveedor(Long(99999999), data);
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
    public void getValoracionesProveedorTest() {
        try {
            List<ValoracionEntity> obtenida = valoracionLogic.getValoracionesProveedor(dataProveedor.get(0).getId());
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
    public void getValoracionesProveedorTestFail() {
        try {
            List<ValoracionEntity> obtenida = valoracionLogic.getValoracionesProveedor(Long(99999999));
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
    public void getValoracionProveedorTest() {
        try {
            ValoracionEntity obtenida = valoracionLogic.getValoracionProveedor(dataProveedor.get(0).getId(), data.get(0).getId());
            Assert.assertEquals(data.get(0), obtenida);
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
    public void getValoracionProveedorTestFail1() {
        try {
            ValoracionEntity obtenida = valoracionLogic.getValoracionProveedor(Long(9999999), data.get(0).getId());
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
    public void getValoracionProveedorTestFail2() {
        try {
            valoracionLogic.getValoracionProveedor(dataProveedor.get(1).getId(), data.get(0).getId());
            fail("Se obtiene una valoración de un proveedor que no tiene dicha valoración");
        } catch (BusinessLogicException x) {
            passed();
        }
    }
}
