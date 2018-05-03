/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.HorarioLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.HorarioPersistence;
import java.util.*;

import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.*;
import org.junit.runner.RunWith;

import uk.co.jemos.podam.api.*;

/**
 *
 * @author mc.gonzalez15
 */
@RunWith(Arquillian.class)
public class HorarioLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private HorarioLogic horarioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<HorarioEntity> data = new ArrayList<>();
    private List<ContratoEntity> dataContrato = new ArrayList<ContratoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HorarioEntity.class.getPackage())
                .addPackage(HorarioLogic.class.getPackage())
                .addPackage(HorarioPersistence.class.getPackage())
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
        em.createQuery("delete from HorarioEntity").executeUpdate();
        em.createQuery("delete from ContratoEntity").executeUpdate();

    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ContratoEntity centity = factory.manufacturePojo(ContratoEntity.class);
            em.persist(centity);
            dataContrato.add(centity);
        }
        
        for (int i = 0; i < 3; i++) {
            HorarioEntity entity = factory.manufacturePojo(HorarioEntity.class);
            dataContrato.get(0).setHorario(entity);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Horario
     *
     *
     */
    @Test
    public void createHorarioTest() {
        HorarioEntity newEntity = factory.manufacturePojo(HorarioEntity.class);

        HorarioEntity newEntity2 = factory.manufacturePojo(HorarioEntity.class);
        
        dataContrato.get(0).setHorario(newEntity2);
        
        //Caso 1: Normal
        try {
            newEntity2.setHoraInicio(new Date(System.currentTimeMillis() + 1000));
            newEntity2.setHoraFin(new Date(System.currentTimeMillis() + 1000000));
            HorarioEntity result = horarioLogic.createHorario(newEntity2, dataContrato.get(0).getId());
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getFecha(), newEntity2.getFecha());
            Assert.assertEquals(result.getHoraFin(), newEntity2.getHoraFin());
            Assert.assertEquals(result.getHoraInicio(), newEntity2.getHoraInicio());
        } catch (Exception e) {
            Assert.fail();
        }

        dataContrato.get(1).setHorario(newEntity);
        //Caso 2: La fecha inicial ya ocurrió
        try {
            newEntity.setHoraInicio(new Date(System.currentTimeMillis() - 1000));
            newEntity.setHoraFin(new Date(System.currentTimeMillis() - 1000000));
            HorarioEntity result = horarioLogic.createHorario(newEntity, dataContrato.get(1).getId());
            Assert.fail();

        } catch (Exception e) {
            //Debería llegar aquí
        }

        //Caso 3: La fecha final es menor a la inicial
        try {
            newEntity.setHoraInicio(new Date(System.currentTimeMillis() + 1000000000));
            newEntity.setHoraFin(new Date(System.currentTimeMillis() + 1000));
            HorarioEntity result = horarioLogic.createHorario(newEntity, dataContrato.get(1).getId());
            Assert.fail();

        } catch (Exception e) {
            //Debería llegar aquí
        }

        //Caso 4: Se intenta crear un horario que ya existe
        try {

            HorarioEntity result = horarioLogic.createHorario(newEntity2, dataContrato.get(0).getId());
            Assert.fail();

        } catch (Exception e) {
            //Debería llegar aquí
        }

    }

    /**
     * Prueba para consultar la lista de Horarios
     *
     *
     */
    @Test
    public void getHorariosTest() throws BusinessLogicException {
        List<HorarioEntity> list = horarioLogic.getHorarios();
        Assert.assertEquals(data.size(), list.size());
        for (HorarioEntity entity : list) {
            boolean found = false;
            for (HorarioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Horario
     *
     *
     */
    @Test
    public void getHorarioTest() {
        HorarioEntity entity = data.get(0);
        HorarioEntity resultEntity = horarioLogic.getHorario(entity.getId(), dataContrato.get(0).getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());

    }

    /**
     * Prueba para eliminar un Horario
     *
     *
     */
    @Test
    public void deleteHorarioTest() {
        HorarioEntity entity = data.get(0);
        horarioLogic.deleteHorario(entity.getId());
        HorarioEntity deleted = em.find(HorarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Horario
     *
     *
     */
    @Test
    public void updateHorarioTest() {
        HorarioEntity entity = data.get(0);
        HorarioEntity pojoEntity = factory.manufacturePojo(HorarioEntity.class);

        pojoEntity.setId(entity.getId());

        horarioLogic.updateHorario(pojoEntity);

        HorarioEntity resp = em.find(HorarioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());

    }
}
