package co.edu.uniandes.csw.fiestas.test.persistence;

import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.persistence.HorarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author df.nino10
 */
@RunWith(Arquillian.class)
public class HorarioPersistenceTest {

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Horario, el descriptor de la base
     * de datos y el archivo benas.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HorarioEntity.class.getPackage())
                .addPackage(HorarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase HorarioPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private HorarioPersistence horarioPersistence;

    /**
     * Contexto de Persostencia que se va autilizar para acceder a la Base de
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
     *
     *
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from HorarioEntity").executeUpdate();
    }

    /**
     *
     */
    private List<HorarioEntity> data = new ArrayList<HorarioEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            HorarioEntity entity = factory.manufacturePojo(HorarioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Horario.
     *
     *
     */
    @Test
    public void createHorarioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        HorarioEntity newEntity = factory.manufacturePojo(HorarioEntity.class);
        HorarioEntity result = horarioPersistence.create(newEntity);

        Assert.assertNotNull(result);

        HorarioEntity entity = em.find(HorarioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFecha().getDay(), entity.getFecha().getDay());
        Assert.assertEquals(newEntity.getHoraInicio().getHours(), entity.getHoraInicio().getHours());
        Assert.assertEquals(newEntity.getHoraInicio().getMinutes(), entity.getHoraInicio().getMinutes());
        Assert.assertEquals(newEntity.getHoraFin().getHours(), entity.getHoraFin().getHours());
        Assert.assertEquals(newEntity.getHoraFin().getMinutes(), entity.getHoraFin().getMinutes());
    }

    /**
     * Prueba para consultar la lista de Horarios.
     *
     *
     */
    @Test
    public void getHorariosTest() {
        List<HorarioEntity> list = horarioPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (HorarioEntity ent : list) {
            boolean found = false;
            for (HorarioEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Horario.
     *
     *
     */
    @Test
    public void getHorarioTest() {
        HorarioEntity entity = data.get(0);
        HorarioEntity newEntity = horarioPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFecha().getDay(), entity.getFecha().getDay());
        Assert.assertEquals(newEntity.getHoraInicio().getHours(), entity.getHoraInicio().getHours());
        Assert.assertEquals(newEntity.getHoraInicio().getMinutes(), entity.getHoraInicio().getMinutes());
        Assert.assertEquals(newEntity.getHoraFin().getHours(), entity.getHoraFin().getHours());
        Assert.assertEquals(newEntity.getHoraFin().getMinutes(), entity.getHoraFin().getMinutes());
    }

    /**
     * Prueba para eliminar un Horario.
     *
     *
     */
    @Test
    public void deleteHorarioTest() {
        HorarioEntity entity = data.get(0);
        horarioPersistence.delete(entity.getId());
        HorarioEntity deleted = em.find(HorarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Horario.
     *
     *
     */
    @Test
    public void updateHorarioTest() {
        HorarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        HorarioEntity newEntity = factory.manufacturePojo(HorarioEntity.class);

        newEntity.setId(entity.getId());

        horarioPersistence.update(newEntity);

        HorarioEntity resp = em.find(HorarioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getFecha().getDay(), entity.getFecha().getDay());
        Assert.assertEquals(newEntity.getHoraInicio().getHours(), entity.getHoraInicio().getHours());
        Assert.assertEquals(newEntity.getHoraInicio().getMinutes(), entity.getHoraInicio().getMinutes());
        Assert.assertEquals(newEntity.getHoraFin().getHours(), entity.getHoraFin().getHours());
        Assert.assertEquals(newEntity.getHoraFin().getMinutes(), entity.getHoraFin().getMinutes());
    }
}
