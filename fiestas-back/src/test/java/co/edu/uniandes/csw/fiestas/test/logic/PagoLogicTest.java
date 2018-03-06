package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.PagoLogic;
import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            PagoEntity entity = factory.manufacturePojo(PagoEntity.class);
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
        PagoEntity result = pagoLogic.createPago(newEntity);
        Assert.assertNotNull(result);
        PagoEntity entidad = em.find(PagoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getMetodoDePago(), entidad.getMetodoDePago());
        Assert.assertEquals(newEntity.getEstado(), entidad.getEstado());
        Assert.assertEquals(newEntity.isRealizado(), entidad.isRealizado());
    }

    /**
     * Prueba para obtener un Pago.
     *
     */
    @Test
    public void getPagoTest() {
        PagoEntity newEntity = data.get(0);
        PagoEntity result = pagoLogic.createPago(newEntity);
        Assert.assertNotNull(result);
        PagoEntity entidad = pagoLogic.getPago(newEntity.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getMetodoDePago(), entidad.getMetodoDePago());
        Assert.assertEquals(newEntity.getEstado(), entidad.getEstado());
        Assert.assertEquals(newEntity.isRealizado(), entidad.isRealizado());
    }

    /**
     * Prueba para consultar la lista de pagos
     */
    @Test
    public void getPagosTest() {
        List<PagoEntity> lista = pagoLogic.getPagos();
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
     * Prueba para eliminar un pago
     */
    @Test
    public void deletePago() {
        PagoEntity entity = data.get(0);
        pagoLogic.deletePago(entity.getId());
        PagoEntity deleted = em.find(PagoEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un pago
     */
    @Test
    public void updatePagoTest() {
        PagoEntity entity = data.get(0);
        PagoEntity newEntity = factory.manufacturePojo(PagoEntity.class);

        newEntity.setId(entity.getId());

        pagoLogic.updatePago(newEntity);

        PagoEntity resp = em.find(PagoEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getEstado(), resp.getEstado());
        Assert.assertEquals(newEntity.isRealizado(), resp.isRealizado());
    }
}
