/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.test.persistence;

import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import co.edu.uniandes.csw.fiestas.persistence.BonoPersistence;
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
 * @author df.nino10
 */
@RunWith(Arquillian.class)
public class BonoPersistenceTest {

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Bono, el descriptor de la base de
     * datos y el archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BonoEntity.class.getPackage())
                .addPackage(BonoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Inyección de la dependencia a la clase BonoPersistence cuyos métodos se
     * van a probar.
     */
    @Inject
    private BonoPersistence bonoPersistence;

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
        em.createQuery("delete from BonoEntity").executeUpdate();
    }

    /**
     *
     */
    private List<BonoEntity> data = new ArrayList<BonoEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            BonoEntity entity = factory.manufacturePojo(BonoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Bono.
     *
     *
     */
    @Test
    public void createBonoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        BonoEntity newEntity = factory.manufacturePojo(BonoEntity.class);
        BonoEntity result = bonoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        BonoEntity entity = em.find(BonoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMotivo(), entity.getMotivo());
        Assert.assertEquals(newEntity.getDescuento(), entity.getDescuento());

    }

    /**
     * Prueba para consultar la lista de Bonos.
     *
     *
     */
    @Test
    public void getBonosTest() {
        List<BonoEntity> list = bonoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (BonoEntity ent : list) {
            boolean found = false;
            for (BonoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar la lista de Bonos.
     *
     *
     */
    @Test
    public void getBonosByCodigoTest() {
        BonoEntity entity = data.get(0);
        BonoEntity newEntity = bonoPersistence.findByCodigo(entity.getCodigo());
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMotivo(), entity.getMotivo());
        Assert.assertEquals(newEntity.getDescuento(), entity.getDescuento());

    }
    
    /**
     * Prueba para consultar un Bono.
     *
     *
     */
    @Test
    public void getBonoTest() {
        BonoEntity entity = data.get(0);
        BonoEntity newEntity = bonoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMotivo(), entity.getMotivo());
        Assert.assertEquals(newEntity.getDescuento(), entity.getDescuento());

    }

    /**
     * Prueba para eliminar un Bono.
     *
     *
     */
    @Test
    public void deleteBonoTest() {
        BonoEntity entity = data.get(0);
        bonoPersistence.delete(entity.getId());
        BonoEntity deleted = em.find(BonoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Bono.
     *
     *
     */
    @Test
    public void updateBonoTest() {
        BonoEntity entity = data.get(0);

        PodamFactory factory = new PodamFactoryImpl();
        BonoEntity newEntity = factory.manufacturePojo(BonoEntity.class);

        newEntity.setId(entity.getId());

       bonoPersistence.update(newEntity);

        BonoEntity resp = em.find(BonoEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getDescuento(), resp.getDescuento());
        Assert.assertEquals(newEntity.getMotivo(), resp.getMotivo());

    }
}
