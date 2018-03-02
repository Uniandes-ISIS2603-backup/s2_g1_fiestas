/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
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
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Evento
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createEventoTest() throws BusinessLogicException {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = eventoLogic.createEvento(newEntity);
        Assert.assertNotNull(result);
        EventoEntity entidad = em.find(EventoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getCelebrado(), entidad.getCelebrado());
        Assert.assertEquals(newEntity.getDescripcion(), entidad.getDescripcion());
        Assert.assertEquals(newEntity.getLugar(), entidad.getLugar());
    }

    /**
     * Prueba para consultar la lista de eventos
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
     * Prueba para eliminar un evento
     */
    @Test
    public void deleteEventoTest() {
        EventoEntity entity = data.get(0);
        eventoLogic.removeContrato(contratosData.get(0).getId(),entity.getId());
        eventoLogic.deleteEvento(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }
    
    @Test
    public void getContratosTest() throws BusinessLogicException{
        EventoEntity entity = data.get(0);
        ContratoEntity contratoEntity= contratosData.get(0);
        ContratoEntity respuesta = eventoLogic.getContrato(entity.getId(), contratoEntity.getId());
        
        Assert.assertEquals(respuesta.getId(), contratoEntity.getId());
        Assert.assertEquals(respuesta.getTyc(), contratoEntity.getTyc());
    }

    /**
     * Prueba para actualizar un evento
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void updateEventoTest() throws BusinessLogicException {
        EventoEntity entity = data.get(0);
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);

        newEntity.setId(entity.getId());

        eventoLogic.updateEvento(newEntity);

        EventoEntity resp = em.find(EventoEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getCelebrado(), resp.getCelebrado());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getLugar(), resp.getLugar());
    }
}
