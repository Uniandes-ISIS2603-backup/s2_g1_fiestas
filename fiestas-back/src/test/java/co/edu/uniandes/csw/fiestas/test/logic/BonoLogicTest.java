/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.BonoLogic;
import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.BonoPersistence;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
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
public class BonoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private BonoLogic bonoLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<BonoEntity> data = new ArrayList<>();   
    
    private List<ProveedorEntity> pData = new ArrayList<>();   

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BonoEntity.class.getPackage())
                .addPackage(BonoLogic.class.getPackage())
                .addPackage(BonoPersistence.class.getPackage())
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
        em.createQuery("delete from BonoEntity").executeUpdate();
        em.createQuery("delete from ProveedorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            BonoEntity entity = factory.manufacturePojo(BonoEntity.class);
            int noOfDays = 8;
            Date dateOfOrder = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOfOrder);
            calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
            Date date = calendar.getTime();
            entity.setExpira(date);
            noOfDays = 1;
            calendar = Calendar.getInstance();
            calendar.setTime(dateOfOrder);
            calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
            date = calendar.getTime();
            entity.setAplicaDesde(date);
            entity.setProveedor(proveedor);
            em.persist(entity);
            ArrayList lista=new ArrayList<>();
            lista.add(entity);
            proveedor.setBonos(lista);
            em.persist(proveedor);
            pData.add(proveedor);
            data.add(entity); 
        }
    }

    /**
     * Prueba para crear un Bono
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createBonoTest() throws BusinessLogicException {
        BonoEntity newEntity = factory.manufacturePojo(BonoEntity.class);
        newEntity.setDescuento(50);
        newEntity.setProveedor(pData.get(0));
        BonoEntity result = bonoLogic.createBono(newEntity);
        Assert.assertNotNull(result);
        BonoEntity entidad = em.find(BonoEntity.class, result.getId());
        assertEquals(result.getId(), entidad.getId());
        assertEquals(result.getDescuento(), entidad.getDescuento());
        assertEquals(result.getMotivo(), entidad.getMotivo());
        assertEquals(result.getCodigo(), entidad.getCodigo());
        assertEquals(result.getProveedor(), entidad.getProveedor());
    }

    /**
     * Prueba para consultar la lista de bonos
     */
    @Test
    public void getBonosTest() {
        List<BonoEntity> lista = bonoLogic.getBonos();
        Assert.assertEquals(data.size(), lista.size());
        for (BonoEntity entity : lista) {
            if(!data.contains(entity))
            fail("Ambas listas deberían contener los mismos bonos");
        }
    }
    
    /**
     * Prueba para consultar la lista de bonos dado un proveedor
     */
    @Test
    public void getBonosPTest() {
        ProveedorEntity proveedor = pData.get(0);
        List<BonoEntity> lista = bonoLogic.getBonos(proveedor.getId());
        Assert.assertEquals(proveedor.getBonos().size(), lista.size());
        for (BonoEntity entity : lista) {
            if(!data.contains(entity))
            fail("Ambas listas deberían contener los mismos bonos");
        }
    }

    /**
     * Se prueba el método para obtener un solo bono.
     */
    @Test
    public void getBonoTest(){
        BonoEntity entidad =data.get(0);
        BonoEntity result = bonoLogic.getBono(entidad.getId());
        assertNotNull(result);
        assertEquals(result.getId(), entidad.getId());
        assertEquals(result.getDescuento(), entidad.getDescuento());
        assertEquals(result.getMotivo(), entidad.getMotivo());
        assertEquals(result.getCodigo(), entidad.getCodigo());
        assertEquals(result.getProveedor(), entidad.getProveedor());
        
    }
    /**
     * Se prueba el método para obtener un solo bono dado el id de proveedor y contrato
     */
    @Test
    public void getBonoPandCTest(){
        BonoEntity entidad = data.get(0);
        ProveedorEntity proveedor =pData.get(0);
        BonoEntity result = bonoLogic.getBono(proveedor.getId());
        assertNotNull(result);
        assertEquals(result.getId(), entidad.getId());
        assertEquals(result.getDescuento(), entidad.getDescuento());
        assertEquals(result.getMotivo(), entidad.getMotivo());
        assertEquals(result.getCodigo(), entidad.getCodigo());
        assertEquals(result.getProveedor(), entidad.getProveedor());
        
    }
    
    /**
     * Prueba para eliminar un bono
     */
    @Test
    public void deleteBono() {
        BonoEntity entity = data.get(0);
        bonoLogic.deleteBono(entity.getId());
        BonoEntity deleted = em.find(BonoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un bono
     */
    @Test
    public void updateBonoTest() {
        BonoEntity ee = data.get(0);
 
        ee.setDescuento(99);
        
        BonoEntity entidad=null;
        try{
        entidad=bonoLogic.updateBono(ee);
        }
        catch(BusinessLogicException e){
            fail(e.getMessage());
        }
               
        BonoEntity result = em.find(BonoEntity.class, ee.getId());
        
        assertEquals(result.getId(), entidad.getId());
        assertEquals(result.getDescuento(), entidad.getDescuento());
        assertEquals(result.getMotivo(), entidad.getMotivo());
        assertEquals(result.getCodigo(), entidad.getCodigo());
        assertEquals(result.getProveedor(), entidad.getProveedor());
    }

}
