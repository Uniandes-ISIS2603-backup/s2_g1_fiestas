package co.edu.uniandes.csw.fiestas.test.persistence;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.persistence.ContratoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
 * @author mc.gonzalez15
 */
@RunWith(Arquillian.class)
public class ContratoPersistenceTest {

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Contrato, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ContratoEntity.class.getPackage())
                .addPackage(ContratoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase ContratoPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private ContratoPersistence ContratoPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
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
        em.createQuery("delete from ContratoEntity").executeUpdate();
    }

    /**
     *
     */
    private List<ContratoEntity> data = new ArrayList<ContratoEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ContratoEntity entity = factory.manufacturePojo(ContratoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Contrato.
     *
     *
     */
    @Test
    public void createContratoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        ContratoEntity result = ContratoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ContratoEntity entity = em.find(ContratoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getTyc(), entity.getTyc());
        Assert.assertEquals(newEntity.getValor(), entity.getValor());
    }

    /**
     * Prueba para consultar la lista de Contratos.
     *
     *
     */
    @Test
    public void getContratosTest() {
        List<ContratoEntity> list = ContratoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ContratoEntity ent : list) {
            boolean found = false;
            for (ContratoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Contrato.
     *
     *
     */
    @Test
    public void getContratoTest() {
        ContratoEntity entity = data.get(0);
        ContratoEntity newEntity = ContratoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getTyc(), entity.getTyc());
        Assert.assertEquals(newEntity.getValor(), entity.getValor());

    }

    /**
     * Prueba para eliminar un Contrato.
     *
     *
     */
    @Test
    public void deleteContratoTest() {
        ContratoEntity entity = data.get(0);
        ContratoPersistence.delete(entity.getId());
        ContratoEntity deleted = em.find(ContratoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Contrato.
     *
     *
     */
    @Test
    public void updateContratoTest() {
        System.out.println("Aqui hay un error");
        ContratoEntity entity = data.get(0);

        PodamFactory factory = new PodamFactoryImpl();
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);

        newEntity.setId(entity.getId());

        ContratoPersistence.update(newEntity);

        ContratoEntity resp = em.find(ContratoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getTyc(), resp.getTyc());
        Assert.assertEquals(newEntity.getValor(), resp.getValor());

    }
}
