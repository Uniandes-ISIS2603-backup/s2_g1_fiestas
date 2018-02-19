package co.edu.uniandes.csw.fiestas.test.persistence;


import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.persistence.EventoPersistence;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cm.amaya10
 */
@RunWith(Arquillian.class)
public class EventoPersistenceTest {

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Pago, el descriptor de la base de
     * datos y el archivo benas.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase EventoPersistence cuyos métodos se
     * van a probar.
     */
    @Inject
    private EventoPersistence eventoPersistence;

    /**
     * Contexto de Persistencia que se va autilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void setUp() {
        try {
            utx.begin();
            em.joinTransaction();
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
        em.createQuery("delete from EventoEntity").executeUpdate();
    }

    /**
     * Lista donde se guardaran las entidades creadas para las pruebas
     */
    private List<EventoEntity> data = new ArrayList<EventoEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EventoEntity entidad = factory.manufacturePojo(EventoEntity.class);
            em.persist(entidad);
            data.add(entidad);
        }
    }

    /**
     * Prueba para crear un Evento
     */
    @Test
    public void createEventoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity entidad = factory.manufacturePojo(EventoEntity.class);
        EventoEntity creado = eventoPersistence.create(entidad);

        Assert.assertNotNull(creado);

        Assert.assertEquals(creado.getLugar(), entidad.getLugar());
        Assert.assertEquals(creado.getCelebrado(), entidad.getCelebrado());
        Assert.assertEquals(creado.getInvitados(), entidad.getInvitados());
        Assert.assertEquals(entidad.getFecha(), creado.getFecha());
    }
    /**
     * Prueba para obtener la lista de eventos.
     */
    @Test
    public void getEventosTest() {
        List<EventoEntity> lista = eventoPersistence.findAll();
        org.junit.Assert.assertEquals(data.size(), lista.size());
        for (EventoEntity entidad : lista) {
            boolean found = false;
            for (EventoEntity entidad2 : data) {
                if (entidad.getId().equals(entidad2.getId())) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para obtener un evento especifico.
     */
    @Test
    public void getEventoTest() {
        EventoEntity entidad = data.get(0);
        EventoEntity encontrado = eventoPersistence.find(entidad.getId());
        Assert.assertNotNull(encontrado);
        Assert.assertEquals(encontrado.getLugar(), entidad.getLugar());
        Assert.assertEquals(encontrado.getCelebrado(), entidad.getCelebrado());
        Assert.assertEquals(encontrado.getInvitados(), entidad.getInvitados());
        Assert.assertEquals(entidad.getFecha(), encontrado.getFecha());
    }
    
     /**
     * Prueba para eliminar un evento especifico.
     */
    @Test
    public void deleteEventoTest() {
      EventoEntity entidad = data.get(0);
      eventoPersistence.delete(entidad.getId());
      EventoEntity borrado =em.find(EventoEntity.class, entidad.getId());
      Assert.assertNull(borrado);
      
    }
    
     /**
     * Prueba para actualizar un evento.
     */
    @Test
    public void updateEventoTest() {
      EventoEntity entidad = data.get(0);
      PodamFactory factory = new PodamFactoryImpl();
      EventoEntity nuevo = factory.manufacturePojo(EventoEntity.class);
      
      nuevo.setId(entidad.getId());
      
      
      eventoPersistence.update(nuevo);
      EventoEntity actualizado =em.find(EventoEntity.class, entidad.getId());
      
              Assert.assertNotNull(actualizado);
        Assert.assertEquals(actualizado.getLugar(), nuevo.getLugar());
        Assert.assertEquals(actualizado.getCelebrado(), nuevo.getCelebrado());
        Assert.assertEquals(actualizado.getInvitados(), nuevo.getInvitados());
        Assert.assertEquals(nuevo.getFecha(), actualizado.getFecha());
      
    }
}
