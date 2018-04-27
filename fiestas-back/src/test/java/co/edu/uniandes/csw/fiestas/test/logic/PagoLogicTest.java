package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.PagoLogic;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import co.edu.uniandes.csw.fiestas.enums.Estado;
import co.edu.uniandes.csw.fiestas.enums.MetodoDePago;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.PagoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
 * @author cm.amaya10
 */
@RunWith(Arquillian.class)
public class PagoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PagoLogic pagoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PagoEntity> data = new ArrayList<>();
    
    private List<EventoEntity> dataEvento = new ArrayList<EventoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoEntity.class.getPackage())
                .addPackage(PagoLogic.class.getPackage())
                .addPackage(PagoPersistence.class.getPackage())
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
        em.createQuery("delete from PagoEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            em.persist(entity);
            dataEvento.add(entity);
        }
        
        for (int i = 0; i < 3; i++) {
            PagoEntity entity = factory.manufacturePojo(PagoEntity.class);
            entity.setEstado(Estado.EN_REVISION.toString());
            entity.setMetodoDePago(MetodoDePago.CONSIGNACION.toString());
            entity.setRealizado(false);
            entity.setEvento(dataEvento.get(1));
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Pago
     *
     */
    @Test
    public void createPagoTest() {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);
        newEntity.setEstado(Estado.EN_REVISION.toString());
        newEntity.setRealizado(false);
        newEntity.setMetodoDePago(MetodoDePago.CONSIGNACION.toString());

        PagoEntity result = new PagoEntity();
        try {
            result = pagoLogic.createPago(dataEvento.get(0).getId(),newEntity);
        } catch (BusinessLogicException ex) {
            fail(ex.getMessage());
        }

        Assert.assertNotNull(result);
        PagoEntity entidad = em.find(PagoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getMetodoDePago(), entidad.getMetodoDePago());
        Assert.assertEquals(newEntity.getValor(), entidad.getValor());
        Assert.assertEquals(newEntity.getEstado(), entidad.getEstado());
        Assert.assertEquals(newEntity.isRealizado(), entidad.isRealizado());
    }

    /**
     * Prueba de fallo para crear un Pago.
     *
     * No se cumple que el pago para ser realizado, su estado debe ser
     * Completado.
     *
     */
    @Test
    public void createPagoFailTest() {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);
        newEntity.setEstado(Estado.EN_REVISION.toString());
        newEntity.setRealizado(true);
        newEntity.setMetodoDePago(MetodoDePago.CONSIGNACION.toString());
        PagoEntity result = new PagoEntity();
        try {
            result = pagoLogic.createPago(dataEvento.get(0).getId(),newEntity);
            fail("No se debio crear el pago");
        } catch (BusinessLogicException ex) {

        }
    }
    
        /**
     * Prueba de fallo para crear un Pago.
     *
     * No se cumple que el estado exista en sistema
     *
     */
    @Test
    public void createPagoFailTest1() {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);
        newEntity.setEstado("En Proceso");
        newEntity.setRealizado(false);
        newEntity.setMetodoDePago(MetodoDePago.CONSIGNACION.toString());
        PagoEntity result = new PagoEntity();
        try {
            result = pagoLogic.createPago(dataEvento.get(0).getId(),newEntity);
            fail("No se debio crear el pago");
        } catch (BusinessLogicException ex) {

        }
    }
    
        
        /**
     * Prueba de fallo para crear un Pago.
     *
     * No se cumple que el metodo exista en sistema
     *
     */
    @Test
    public void createPagoFailTest2() {
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);
        newEntity.setEstado(Estado.CONFIRMADO.toString());
        newEntity.setRealizado(true);
        newEntity.setMetodoDePago("Efecty");
        PagoEntity result = new PagoEntity();
        try {
            result = pagoLogic.createPago(dataEvento.get(0).getId(),newEntity);
            fail("No se debio crear el pago");
        } catch (BusinessLogicException ex) {

        }
    }

    /**
     * Prueba para consultar la lista de pagos
     */
    @Test
    public void getPagosTest() {
        List<PagoEntity> lista = new ArrayList<>();
        try{
         lista = pagoLogic.getPagos(dataEvento.get(1).getId());    
        }catch(BusinessLogicException ex){
            fail("Debe consegui los pagos del evento");
        }
        Assert.assertEquals(data.size(), lista.size());
        for (PagoEntity entity : lista) {
            boolean encontrado = false;
            for (PagoEntity pagoEntity : data) {
                if (entity.getId().equals(pagoEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para obtener un Pago.
     *
     */
    @Test
    public void getPagoTest() {
            PagoEntity newEntity = data.get(0);
            PagoEntity entidad = pagoLogic.getPago(dataEvento.get(1).getId(),newEntity.getId());
            Assert.assertEquals(newEntity.getId(), entidad.getId());
            Assert.assertEquals(newEntity.getMetodoDePago(), entidad.getMetodoDePago());
            Assert.assertEquals(newEntity.getEstado(), entidad.getEstado());
            Assert.assertEquals(newEntity.isRealizado(), entidad.isRealizado());
    }

    /**
     * Prueba para eliminar un pago
     */
    @Test
    public void deletePago() {
        PagoEntity entity = data.get(0);
        pagoLogic.deletePago(dataEvento.get(1).getId(),entity.getId());
        PagoEntity deleted = em.find(PagoEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba exitosa para actualizar un pago
     */
    @Test
    public void updatePagoTest() {

        PagoEntity entity = data.get(0);
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setEstado(Estado.EN_REVISION.toString());
        newEntity.setRealizado(false);
        newEntity.setMetodoDePago(MetodoDePago.CONSIGNACION.toString());

        try {
            pagoLogic.updatePago(dataEvento.get(1).getId(),newEntity);
        } catch (BusinessLogicException ex) {
            fail(ex.getMessage());
        }
        PagoEntity resp = em.find(PagoEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getMetodoDePago(), resp.getMetodoDePago());
        Assert.assertEquals(newEntity.getEstado(), resp.getEstado());
        Assert.assertEquals(newEntity.isRealizado(), resp.isRealizado());
    }

    /**
     * Prueba fallida para actualizar un pago.
     *
     * Esto por que se pone el pago como finalizado, cuando aun esta en
     * revision.
     */
    @Test
    public void updatePagoFailTest1() {

        PagoEntity entity = data.get(0);
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setEstado(Estado.EN_REVISION.toString());
        newEntity.setRealizado(true);
        newEntity.setMetodoDePago(MetodoDePago.CONSIGNACION.toString());

        try {
            pagoLogic.updatePago(dataEvento.get(1).getId(),newEntity);
            fail("No se debio poder actualizar el pago");
        } catch (BusinessLogicException ex) {

        }
    }

    /**
     * Prueba fallida para actualizar un pago.
     *
     * Esto por que se pone un estado no existente.
     */
    @Test
    public void updatePagoFailTest2() {

        PagoEntity entity = data.get(0);
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setEstado("Aun en revision");
        newEntity.setRealizado(true);
        newEntity.setMetodoDePago(MetodoDePago.CONSIGNACION.toString());
        try {
            pagoLogic.updatePago(dataEvento.get(1).getId(),newEntity);
            fail("No se debio poder actualizar el pago");
        } catch (BusinessLogicException ex) {

        }
    }

    /**
     * Prueba fallida para actualizar un pago.
     *
     * Esto por que se pone un metodo de pago no existente.
     */
    @Test
    public void updatePagoFailTest3() {

        PagoEntity entity = data.get(0);
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setEstado(Estado.CONFIRMADO.toString());
        newEntity.setRealizado(true);
        newEntity.setMetodoDePago("Tarjeta de Debito");
        try {
            pagoLogic.updatePago(dataEvento.get(1).getId(),newEntity);
            fail("No se debio poder actualizar el pago");
        } catch (BusinessLogicException ex) {

        }
    }
}
