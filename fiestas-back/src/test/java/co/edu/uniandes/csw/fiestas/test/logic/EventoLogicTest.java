package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import co.edu.uniandes.csw.fiestas.enums.Estado;
import co.edu.uniandes.csw.fiestas.enums.MetodoDePago;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author cm.amaya10
 */
@RunWith(Arquillian.class)
public class EventoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private EventoLogic eventoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    private List<PagoEntity> pagosData = new ArrayList<PagoEntity>();

    private List<ContratoEntity> contratosData = new ArrayList<ContratoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
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
        em.createQuery("delete from ContratoEntity").executeUpdate();
        em.createQuery("delete from PagoEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();

    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
            em.persist(contrato);
            contratosData.add(contrato);

            PagoEntity pagoEntity = factory.manufacturePojo(PagoEntity.class);
            pagoEntity.setEstado(Estado.EN_REVISION.toString());
            pagoEntity.setMetodoDePago(MetodoDePago.CONSIGNACION.toString());
            pagoEntity.setRealizado(false);
            em.persist(pagoEntity);
            pagosData.add(pagoEntity);
        }
        for (int i = 0; i < 3; i++) {
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);

            int noOfDays = 8;
            Date dateOfOrder = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOfOrder);
            calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
            Date date = calendar.getTime();
            entity.setFecha(date);
            entity.setInvitados(10);
            em.persist(entity);
            data.add(entity);

            if (i == 0) {
                contratosData.get(i).setEvento(entity);
                pagosData.get(i).setEvento(entity);
            }
        }
    }

    /**
     * Prueba exitosa para crear un Evento.
     */
    @Test
    public void createEventoTest() {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);

        int noOfDays = 8;
        Date dateOfOrder = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = calendar.getTime();
        newEntity.setFecha(date);
        newEntity.setInvitados(10);

        EventoEntity result = new EventoEntity();
        try {
            result = eventoLogic.createEvento(newEntity);
        } catch (BusinessLogicException ex) {
            fail(ex.getMessage());
        }
        Assert.assertNotNull(result);
        EventoEntity entidad = em.find(EventoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getCelebrado(), entidad.getCelebrado());
        Assert.assertEquals(newEntity.getDescripcion(), entidad.getDescripcion());
        Assert.assertEquals(newEntity.getLugar(), entidad.getLugar());
    }

    /**
     * Prueba fallida para crear un Evento duplicado.
     */
    @Test
    public void createDuplicateEventoTest() {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);

        int noOfDays = 8;
        Date dateOfOrder = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = calendar.getTime();
        newEntity.setFecha(date);
        newEntity.setInvitados(10);

        EventoEntity result = new EventoEntity();
        try {
            result = eventoLogic.createEvento(newEntity);
        } catch (BusinessLogicException ex) {
            fail(ex.getMessage());
        }

        try {
            result = eventoLogic.createEvento(newEntity);
            fail("No se puede crear duplicado");
        } catch (BusinessLogicException ex) {

        }
    }

    /**
     * Prueba fallida para crear un Evento 1.
     *
     * Falla por que el evento o cumple el plazo
     */
    @Test
    public void createEventoTestFail1() {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setFecha(new Date());
        newEntity.setInvitados(10);
        EventoEntity result;
        try {
            result = eventoLogic.createEvento(newEntity);
            fail("No se debio haber creado el evento");
        } catch (BusinessLogicException ex) {

        }
    }

    /**
     * Prueba fallida para crear un Evento 2.
     *
     * Falla por que el evento tiene un numero de invitados negativo.
     */
    @Test
    public void createEventoTestFail2() {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);

        int noOfDays = 8;
        Date dateOfOrder = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = calendar.getTime();
        newEntity.setFecha(date);
        newEntity.setInvitados(-1);

        EventoEntity result;
        try {
            result = eventoLogic.createEvento(newEntity);
            fail("No se debio haber creado el evento");
        } catch (BusinessLogicException ex) {

        }
    }

    /**
     * Prueba para consultar la lista de eventos.
     */
    @Test
    public void getEventosTest() {
        List<EventoEntity> lista = eventoLogic.getEventos();
        Assert.assertEquals(data.size(), lista.size());
        for (EventoEntity entity : lista) {
            boolean encontrado = false;
            for (EventoEntity eventoEntity : data) {
                if (entity.getId().equals(eventoEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para eliminar un evento.
     */
    @Test
    public void deleteEventoTest() {
        EventoEntity entity = data.get(0);
        eventoLogic.removeContrato(contratosData.get(0).getId(), entity.getId());
        eventoLogic.deleteEvento(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba de agregar un contrato de un evento.
     */
    @Test
    public void addContratoTest() {
        EventoEntity entity = data.get(0);
        ContratoEntity contratoEntity = contratosData.get(0);
        ContratoEntity creado = eventoLogic.addContrato(contratoEntity.getId(), entity.getId());
        Assert.assertEquals(creado.getId(), contratoEntity.getId());
        Assert.assertEquals(creado.getTyc(), contratoEntity.getTyc());
    }

    /**
     * Prueba de obtener un contrato de un evento.
     */
    @Test
    public void getContratoTest() {
        EventoEntity entity = data.get(0);
        ContratoEntity contratoEntity = contratosData.get(0);
        ContratoEntity respuesta = new ContratoEntity();
        try {
            respuesta = eventoLogic.getContrato(entity.getId(), contratoEntity.getId());
        } catch (BusinessLogicException ex) {
            fail(ex.getMessage());
        }
        Assert.assertEquals(respuesta.getId(), contratoEntity.getId());
        Assert.assertEquals(respuesta.getTyc(), contratoEntity.getTyc());
    }

    /**
     * Prueba de obtener la lista de contratos de un evento.
     */
    @Test
    public void getContratosTest() {
        EventoEntity entity = data.get(0);
        ContratoEntity contratoEntity = contratosData.get(0);
        List<ContratoEntity> respuesta = eventoLogic.getContratos(entity.getId());
        Assert.assertEquals(respuesta.get(0).getId(), contratoEntity.getId());
        Assert.assertEquals(respuesta.get(0).getTyc(), contratoEntity.getTyc());
    }

    /**
     * Prueba para reemplzar la lista de contratos de un evento.
     */
    @Test
    public void replaceContratosTest() {
        EventoEntity entity = data.get(0);
        ContratoEntity contratoEntity = contratosData.get(0);
        ContratoEntity otherContratoEntity = contratosData.get(1);

        List<ContratoEntity> respuesta = eventoLogic.replaceContratos(entity.getId(), contratosData);
        Assert.assertEquals(respuesta.get(0).getId(), contratoEntity.getId());
        Assert.assertEquals(respuesta.get(0).getTyc(), contratoEntity.getTyc());
        Assert.assertEquals(respuesta.get(1).getId(), otherContratoEntity.getId());
        Assert.assertEquals(respuesta.get(1).getTyc(), otherContratoEntity.getTyc());
    }

    /**
     * Prueba para eliminar un contrato
     */
    @Test
    public void removeContratoTest() {
        EventoEntity entity = data.get(0);
        ContratoEntity contratoEntity = contratosData.get(0);
        eventoLogic.removeContrato(contratoEntity.getId(), entity.getId());
        try {
            ContratoEntity respuesta = eventoLogic.getContrato(entity.getId(), contratoEntity.getId());
            fail("No deberia encontrar el contrato en el evento");
        } catch (BusinessLogicException ex) {

        }
    }

    /**
     * Prueba Exitosa para actualizar un evento.
     *
     * Se actualiza un evento cumpliendo las reglas de negocio
     */
    @Test
    public void updateEventoTest() {
        EventoEntity entity = data.get(0);
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);

        int noOfDays = 8;
        Date dateOfOrder = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = calendar.getTime();
        newEntity.setFecha(date);
        newEntity.setId(entity.getId());

        newEntity.setInvitados(10);

        try {
            eventoLogic.updateEvento(newEntity);
        } catch (BusinessLogicException ex) {
            fail(ex.getMessage());
        }

        EventoEntity resp = em.find(EventoEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getCelebrado(), resp.getCelebrado());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getLugar(), resp.getLugar());
    }

    /**
     * Prueba fallida para actualizar un evento
     *
     * No se cumple la primera regla de negocio
     */
    @Test
    public void updateFailEventoTest() {
        EventoEntity entity = data.get(0);
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        Date date = new Date();
        newEntity.setFecha(date);
        newEntity.setId(entity.getId());
        try {
            eventoLogic.updateEvento(newEntity);
            fail("El evento se actualizo no cumpliendo las reglas de negocio");
        } catch (BusinessLogicException ex) {
        }
    }

    /**
     * Prueba de agregar un pago de un evento.
     */
    @Test
    public void addPagoTest() {
        EventoEntity entity = data.get(0);
        PagoEntity pagoEntity = pagosData.get(0);
        PagoEntity creado = eventoLogic.addPago(pagoEntity.getId(), entity.getId());
        Assert.assertEquals(creado.getId(), pagoEntity.getId());
        Assert.assertEquals(creado.getValor(), pagoEntity.getValor());
    }

    /**
     * Prueba de obtener un pago de un evento.
     */
    @Test
    public void getPagoTest() {
        EventoEntity entity = data.get(0);
        PagoEntity pagoEntity = pagosData.get(0);
        PagoEntity respuesta = new PagoEntity();
        try {
            respuesta = eventoLogic.getPago(entity.getId(), pagoEntity.getId());
        } catch (BusinessLogicException ex) {
            fail(ex.getMessage());
        }
        Assert.assertEquals(respuesta.getId(), pagoEntity.getId());
        Assert.assertEquals(respuesta.getValor(), pagoEntity.getValor());
    }

    /**
     * Prueba de obtener la lista de pagos de un evento.
     */
    @Test
    public void getPagosTest() {
        EventoEntity entity = data.get(0);
        PagoEntity pagoEntity = pagosData.get(0);
        List<PagoEntity> respuesta = eventoLogic.getPagos(entity.getId());
        Assert.assertEquals(respuesta.get(0).getId(), pagoEntity.getId());
        Assert.assertEquals(respuesta.get(0).getValor(), pagoEntity.getValor());
    }

    /**
     * Prueba para reemplzar la lista de pagos de un evento.
     */
    @Test
    public void replacePagosTest() {
        EventoEntity entity = data.get(0);
        PagoEntity pagoEntity = pagosData.get(0);
        PagoEntity otherPagoEntity = pagosData.get(1);

        List<PagoEntity> respuesta = eventoLogic.replacePagos(entity.getId(), pagosData);
        Assert.assertEquals(respuesta.get(0).getId(), pagoEntity.getId());
        Assert.assertEquals(respuesta.get(0).getValor(), pagoEntity.getValor());
        Assert.assertEquals(respuesta.get(1).getId(), otherPagoEntity.getId());
        Assert.assertEquals(respuesta.get(1).getValor(), otherPagoEntity.getValor());
    }

    /**
     * Prueba para eliminar un pago
     */
    @Test
    public void removePagoTest() {
        EventoEntity entity = data.get(0);
        PagoEntity pagoEntity = pagosData.get(0);
        eventoLogic.removePago(pagoEntity.getId(), entity.getId());
        try {
            PagoEntity respuesta = eventoLogic.getPago(entity.getId(), pagoEntity.getId());
            fail("No deberia encontrar el pago en el evento");
        } catch (BusinessLogicException ex) {

        }
    }

}
