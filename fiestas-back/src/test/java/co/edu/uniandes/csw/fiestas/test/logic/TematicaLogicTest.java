/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.TematicaLogic;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;
import co.edu.uniandes.csw.fiestas.persistence.TematicaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author af.losada
 */
public class TematicaLogicTest 
{
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TematicaLogic tematicaLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<TematicaEntity> data = new ArrayList<TematicaEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TematicaEntity.class.getPackage())
                .addPackage(TematicaLogic.class.getPackage())
                .addPackage(TematicaPersistence.class.getPackage())
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
        em.createQuery("delete from TematicaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            TematicaEntity entity = factory.manufacturePojo(TematicaEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Tematica
     *
     */
    @Test
    public void createTematicaTest() {
        TematicaEntity newEntity = factory.manufacturePojo(TematicaEntity.class);
        TematicaEntity result = tematicaLogic.createTematica(newEntity);
        Assert.assertNotNull(result);
        TematicaEntity entidad = em.find(TematicaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getDescripcion(), entidad.getDescripcion());
    }

    /**
     * Prueba para consultar la lista de tematicas
     */
    @Test
    public void getTematicasTest() {
        List<TematicaEntity> lista = tematicaLogic.getTematicas();
        Assert.assertEquals(data.size(), lista.size());
        for (TematicaEntity entity : lista) {
            boolean encontrado = false;
            for (TematicaEntity tematicaEntity : data) {
                if (entity.getId().equals(tematicaEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para eliminar un tematica
     */
    @Test
    public void deleteTematica() {
        TematicaEntity entity = data.get(0);
        tematicaLogic.deleteTematica(entity.getId());
        TematicaEntity deleted = em.find(TematicaEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un tematica
     */
    @Test
    public void updateTematicaTest() {
        TematicaEntity entity = data.get(0);
        TematicaEntity newEntity = factory.manufacturePojo(TematicaEntity.class);

        newEntity.setId(entity.getId());

        tematicaLogic.updateTematica(newEntity);

        TematicaEntity resp = em.find(TematicaEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        
        List<ServicioEntity> serv1 = newEntity.getServicios();
        List<ServicioEntity> serv2 = resp.getServicios();
        for (int i = 0; i<serv1.size(); i++) {
            Assert.assertEquals(serv1.get(i), serv2.get(i));
            
        }
        Assert.assertEquals(newEntity.getServicios(), resp.getServicios());
    }
    
}
