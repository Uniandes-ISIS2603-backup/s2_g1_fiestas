package co.edu.uniandes.csw.fiestas.test.persistence;


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
public class PagoPersistenceTest {

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Pago, el descriptor de la base de
     * datos y el archivo benas.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoEntity.class.getPackage())
                .addPackage(PagoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase PagoPersistence cuyos métodos se
     * van a probar.
     */
    @Inject
    private PagoPersistence pagoPersistence;

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
        em.createQuery("delete from PagoEntity").executeUpdate();
    }

    /**
     * Lista donde se guardaran las entidades creadas para las pruebas
     */
    private List<PagoEntity> data = new ArrayList<PagoEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PagoEntity entidad = factory.manufacturePojo(PagoEntity.class);
            em.persist(entidad);
            data.add(entidad);
        }
    }

    /**
     * Prueba para crear un Pago
     */
    @Test
    public void createPagoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PagoEntity entidad = factory.manufacturePojo(PagoEntity.class);
        PagoEntity creado = pagoPersistence.create(entidad);

        Assert.assertNotNull(creado);

        Assert.assertEquals(creado.getEstado(), entidad.getEstado());
        Assert.assertEquals(creado.getMetodoDePago(), entidad.getMetodoDePago());
        Assert.assertEquals(creado.getRealizado(), entidad.getRealizado());
    }

    /**
     * Prueba para obtener la lista de pagos.
     */
    @Test
    public void getPagosTest() {
        List<PagoEntity> lista = pagoPersistence.findAll();
        org.junit.Assert.assertEquals(data.size(), lista.size());
        for (PagoEntity entidad : lista) {
            boolean found = false;
            for (PagoEntity entidad2 : data) {
                if (entidad.getId().equals(entidad2.getId())) {
                    found = true;
                    break;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para obtener un pago especifico.
     */
    @Test
    public void getPagoTest() {
        PagoEntity entidad = data.get(0);
        PagoEntity encontrado = pagoPersistence.find(entidad.getId());
        Assert.assertNotNull(encontrado);
        Assert.assertEquals(entidad.getEstado(), encontrado.getEstado());
        Assert.assertEquals(entidad.getMetodoDePago(), encontrado.getMetodoDePago());
        Assert.assertEquals(entidad.getRealizado(), encontrado.getRealizado());
    }

    /**
     * Prueba para eliminar un pago especifico.
     */
    @Test
    public void deletePagoTest() {
        PagoEntity entidad = data.get(0);
        pagoPersistence.delete(entidad.getId());
        PagoEntity borrado = em.find(PagoEntity.class, entidad.getId());
        Assert.assertNull(borrado);
    }

    /**
     * Prueba para actualizar un horario
     */
    @Test
    public void updatePagoTest() {
        PagoEntity entidad = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PagoEntity nuevo = factory.manufacturePojo(PagoEntity.class);

        nuevo.setId(entidad.getId());

        pagoPersistence.update(nuevo);

        PagoEntity actualizada = em.find(PagoEntity.class, entidad.getId());

        Assert.assertEquals(nuevo.getEstado(), actualizada.getEstado());
        Assert.assertEquals(nuevo.getMetodoDePago(), actualizada.getMetodoDePago());
        Assert.assertEquals(nuevo.getRealizado(), actualizada.getRealizado());

    }
}
