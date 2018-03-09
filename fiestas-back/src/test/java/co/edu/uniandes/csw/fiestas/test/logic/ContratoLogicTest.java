/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ContratoLogic;
import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ContratoPersistence;
import java.util.*;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.*;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author mc.gonzalez15
 */
@RunWith(Arquillian.class)
public class ContratoLogicTest {

    public ContratoLogicTest() {
    }
    private final PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ContratoLogic contratoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ContratoEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ContratoEntity.class.getPackage())
                .addPackage(ContratoLogic.class.getPackage())
                .addPackage(ContratoPersistence.class.getPackage())
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
        em.createQuery("delete from ContratoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

       /** ArrayList<ProductoEntity> x = new ArrayList<>();
        for (int m = 0; m < 3; m++) {
            ProveedorEntity entitya = factory.manufacturePojo(ProveedorEntity.class);
            EventoEntity entityb = factory.manufacturePojo(EventoEntity.class);
            for (int l = 0; l < 2; l++) {
                ProductoEntity entityc = factory.manufacturePojo(ProductoEntity.class);
                x.add(entityc);

            }
            em.persist(entitya);
            em.persist(entityb);
            proveedorData.add(entitya);
            eventoData.add(entityb);
            productoData.add(x);
        }*/

        for (int i = 0; i < 3; i++) {

            ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
             EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            HorarioEntity horario = factory.manufacturePojo(HorarioEntity.class);
            contrato.setEvento(evento);
            contrato.setProveedor(proveedor);
            contrato.setHorario(horario);
            ArrayList<ProductoEntity>productos = new ArrayList<>();
            for (int l = 0; l < 2; l++) {
                ProductoEntity producto = factory.manufacturePojo(ProductoEntity.class);
                productos.add(producto);
                em.persist(producto);
            }
            contrato.setProductos(productos);

            em.persist(contrato);
            em.persist(evento);
            em.persist(horario);
            em.persist(proveedor);
            data.add(contrato);
            System.out.println("Si se añaden elementos a la lista.");
        }
    }

    /**
     * Prueba para crear un Contrato
     *
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @Test
    public void createContratoTest() throws BusinessLogicException {
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);
        ContratoEntity result = contratoLogic.createContrato(newEntity);
        Assert.assertNotNull(result);
        ContratoEntity entidad = em.find(ContratoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entidad.getId());
        Assert.assertEquals(newEntity.getValor(), entidad.getValor());
        Assert.assertEquals(newEntity.getTyc(), entidad.getTyc());
        Assert.assertEquals(newEntity.getProveedor(), entidad.getProveedor());
        Assert.assertEquals(newEntity.getEvento(), entidad.getEvento());
        Assert.assertEquals(newEntity.getProductos(), entidad.getProductos());
        
       
    }

    /**
     * Prueba para consultar la lista de contratos
     */
    @Test
    public void getContratosTest() {
        List<ContratoEntity> lista = contratoLogic.getContratos();
        Assert.assertEquals(data.size(), lista.size());
        for (ContratoEntity entity : lista) {
            boolean encontrado = false;
            for (ContratoEntity contratoEntity : data) {
                if (entity.getId().equals(contratoEntity.getId())) {
                    encontrado = true;
                    break;
                }
            }
            Assert.assertTrue(encontrado);
        }
    }

    /**
     * Prueba para eliminar un contrato
     */
    @Test
    public void deleteContrato() {
        ContratoEntity entity = data.get(0);
        contratoLogic.deleteContrato(entity.getId());
        ContratoEntity deleted = em.find(ContratoEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un contrato
     */
    @Test
    public void updateContratoTest() {
        ContratoEntity entity = data.get(0);
        ContratoEntity newEntity = factory.manufacturePojo(ContratoEntity.class);

        newEntity.setId(entity.getId());

        contratoLogic.updateContrato(newEntity);

        ContratoEntity resp = em.find(ContratoEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getValor(), resp.getValor());
        Assert.assertEquals(newEntity.getTyc(), resp.getTyc());
        Assert.assertEquals(newEntity.getProveedor(), resp.getProveedor());
        Assert.assertEquals(newEntity.getEvento(), resp.getEvento());
        Assert.assertEquals(newEntity.getProductos(), resp.getProductos());
       
    }
}
