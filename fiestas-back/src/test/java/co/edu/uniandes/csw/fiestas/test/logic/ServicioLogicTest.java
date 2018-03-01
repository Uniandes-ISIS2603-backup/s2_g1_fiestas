/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ServicioLogic;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
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
 * @author ls.arias
 */
@RunWith(Arquillian.class)
public class ServicioLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ServicioLogic servicioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ServicioEntity> data = new ArrayList<ServicioEntity>();

    private List<ProveedorEntity> proveedorData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ServicioEntity.class.getPackage())
                .addPackage(ServicioLogic.class.getPackage())
                .addPackage(ServicioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     *
     *
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
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from ServicioEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            em.persist(proveedor);
            proveedorData.add(proveedor);
        }
        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entity.setProveedores(proveedorData);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Servicio
     *
     *
     */
    @Test
    public void createServicioTest() throws BusinessLogicException {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        ServicioEntity result = servicioLogic.createServicio(newEntity);
        Assert.assertNotNull(result);
        ServicioEntity entity = em.find(ServicioEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(newEntity.getValoraciones(), entity.getValoraciones());
 
    }

    /**
     * Prueba para consultar la lista de Servicios
     *
     *
     */
    @Test
    public void getServiciosTest() {
        List<ServicioEntity> list = servicioLogic.getServicios();
        Assert.assertEquals(data.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Servicio
     *
     *
     */
    @Test
    public void getServicioTest() {
        ServicioEntity entity = data.get(0);
        ServicioEntity resultEntity = servicioLogic.getServicio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getName(), resultEntity.getName());
        Assert.assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        Assert.assertEquals(entity.getValoraciones(), resultEntity.getValoraciones());
    }

    /**
     * Prueba para eliminar un Servicio
     *
     *
     */
    @Test
    public void deleteServicioTest() {
        ServicioEntity entity = data.get(0);
        servicioLogic.deleteServicio(entity.getId());
        ServicioEntity deleted = em.find(ServicioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Servicio
     *
     *
     */
    @Test
    public void updateServicioTest() throws BusinessLogicException {
        ServicioEntity entity = data.get(0);
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);

        pojoEntity.setId(entity.getId());

        servicioLogic.updateServicio(pojoEntity);

        ServicioEntity resp = em.find(ServicioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getName(), resp.getName());
        Assert.assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(pojoEntity.getValoraciones(), resp.getValoraciones());
    }
    
}
