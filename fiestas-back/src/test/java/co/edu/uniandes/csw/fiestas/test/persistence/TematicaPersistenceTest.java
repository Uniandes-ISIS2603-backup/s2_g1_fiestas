package co.edu.uniandes.csw.fiestas.test.persistence;



import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;
import co.edu.uniandes.csw.fiestas.persistence.TematicaPersistence;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * af.losada
 */
@RunWith(Arquillian.class)
public class TematicaPersistenceTest 
{
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Employee, el descriptor de la
     * base de datos y el archivo benas.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TematicaEntity.class.getPackage())
                .addPackage(TematicaPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    /**
     * Inyección de la dependencia a la clase EmployeePersistence cuyos métodos
     * se van a probar.
     */
    @Inject 
    private TematicaPersistence tematicaPersistence;

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
        em.createQuery("delete from TematicaEntity").executeUpdate();
    }

    /**
     *
     */
    private List<TematicaEntity> data = new ArrayList<TematicaEntity>();

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory;
        factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            TematicaEntity entity = factory.manufacturePojo(TematicaEntity.class);
            
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear una Tematica.
     *
     *
     */
    @Test 
    public void createTematicaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        TematicaEntity newEntity = factory.manufacturePojo(TematicaEntity.class);
        TematicaEntity result = tematicaPersistence.create(newEntity);

        Assert.assertNotNull(result);

        TematicaEntity entity = em.find(TematicaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para consultar la lista de Tematicas.
     *
     *
     */
    @Test
    public void getTematicasTest() {
        List<TematicaEntity> list = tematicaPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (TematicaEntity ent : list) {
            boolean found = false;
            for (TematicaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una Tematica.
     *
     *
     */
    @Test 
    public void getTematicaTest() {
        TematicaEntity entity = data.get(0);
        TematicaEntity newEntity = tematicaPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para eliminar una Tematica.
     *
     *
     */
    @Test
    public void deleteTematicaTest() {
        TematicaEntity entity = data.get(0);
        tematicaPersistence.delete(entity.getId());
        TematicaEntity deleted = em.find(TematicaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Tematica.
     *
     *
     */
    @Test
    public void updateTematicaTest() {
        TematicaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TematicaEntity newEntity = factory.manufacturePojo(TematicaEntity.class);

        newEntity.setId(entity.getId());

        tematicaPersistence.update(newEntity);

        TematicaEntity resp = em.find(TematicaEntity.class, entity.getId());

        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
    
}
