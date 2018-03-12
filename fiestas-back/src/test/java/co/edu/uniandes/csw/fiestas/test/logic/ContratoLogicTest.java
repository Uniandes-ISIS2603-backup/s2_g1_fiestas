package co.edu.uniandes.csw.fiestas.test.logic;

import co.edu.uniandes.csw.fiestas.ejb.ContratoLogic;
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

        for (int i = 0; i < 3; i++) {
            ContratoEntity contrato = factory.manufacturePojo(ContratoEntity.class);
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            ProveedorEntity proveedor = factory.manufacturePojo(ProveedorEntity.class);
            HorarioEntity horario = factory.manufacturePojo(HorarioEntity.class);
            contrato.setEvento(evento);
            contrato.setProveedor(proveedor);
            contrato.setHorario(horario);
            ArrayList<ProductoEntity> productos = new ArrayList<>();
            for (int l = 0; l < 10; l++) {
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
        HorarioEntity horario = factory.manufacturePojo(HorarioEntity.class);
        horario.setHoraInicio(new Date(2018, 5, 20, 20, 15, 0));
        horario.setHoraFin(new Date(2018, 5, 20, 20, 22, 0));

        newEntity.setValor(2);

        ContratoEntity newEntity2 = factory.manufacturePojo(ContratoEntity.class);
        HorarioEntity horario2 = factory.manufacturePojo(HorarioEntity.class);

        //Caso 1: Normal
        try {

            ContratoEntity result = contratoLogic.createContrato(newEntity, horario);

            ContratoEntity entidad = em.find(ContratoEntity.class, result.getId());
            Assert.assertEquals(newEntity.getId(), entidad.getId());
            Assert.assertEquals(newEntity.getValor(), entidad.getValor());
            Assert.assertEquals(newEntity.getTyc(), entidad.getTyc());
            Assert.assertEquals(newEntity.getProveedor(), entidad.getProveedor());
            Assert.assertEquals(newEntity.getEvento(), entidad.getEvento());
            Assert.assertEquals(newEntity.getProductos(), entidad.getProductos());

        } catch (Exception e) {

            Assert.fail("No debería lanzar excepcion " + e.getMessage());

        }

        //Caso 2: Los términos y condiciones están vacíos.
        try {
            horario2.setHoraInicio(new Date(2018, 5, 20, 20, 15, 0));
            horario2.setHoraFin(new Date(2018, 5, 20, 20, 22, 0));
            newEntity2.setTyc("");
            ContratoEntity result = contratoLogic.createContrato(newEntity2, horario2);

            Assert.fail("No debería llegar aquí");

        } catch (Exception e) {

            //Debería llegar aquí
        }

        //Caso 3: El valor es negativo
        try {
            newEntity2.setValor(-1);
            ContratoEntity result = contratoLogic.createContrato(newEntity2, horario2);

            Assert.fail("No debería llegar aquí");

        } catch (Exception e) {

            //Debería llegar aquí
        }

        //Caso 4: El contrato con id ya existe.
        try {
            ContratoEntity result = contratoLogic.createContrato(newEntity, horario);

            Assert.fail("No debería llegar aquí");

        } catch (Exception e) {

            //Debería llegar aquí
        }

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

    /**
     * Prueba para la lista de productos
     */
    @Test
    public void listProductosTest() {

        ContratoEntity entity = data.get(0);

        List<ProductoEntity> productos = contratoLogic.listProductos(entity.getId());

        for (int i = 0; i < productos.size(); i++) {
            if (!productos.contains(entity.getProductos().get(i))) {
                Assert.fail("Deberían contener los mismos productos.");
            }
        }

    }

    /**
     * Prueba para obtener un producto
     */
    @Test
    public void getProductoTest() {

        ContratoEntity entity = data.get(0);
        ProductoEntity p = entity.getProductos().get(0);

        ProductoEntity producto = contratoLogic.getProducto(entity.getId(), p.getId());

        Assert.assertEquals(p, producto);
    }

    /**
     * Prueba para agregar un producto
     */
    @Test
    public void addProductoTest() {

        ContratoEntity entity = data.get(0);
        ProductoEntity p = data.get(1).getProductos().get(0);

        ProductoEntity producto = contratoLogic.addProducto(entity.getId(), p.getId());

        
        if(!producto.equals(p))
        {
            Assert.fail();
        }
    }

    /**
     * Prueba para reemplazar los productos
     */
    @Test
    public void replaceProductosTest() {

        ContratoEntity entity = data.get(0);
        ArrayList<ProductoEntity> productosNuevos = new ArrayList<>();

        for(int i = 0; i<10; i++)
        {
            productosNuevos.add(factory.manufacturePojo(ProductoEntity.class));
        }
        
        List<ProductoEntity> productos = contratoLogic.replaceProductos(entity.getId(), productosNuevos);
        
        if(!productos.containsAll(productosNuevos))
        {
            Assert.fail();
        }

    }
    
        /**
     * Prueba para remover un producto
     */
    @Test
    public void removeProductoTest() {

        ContratoEntity entity = data.get(0);
        ProductoEntity p = entity.getProductos().get(0);
        
        contratoLogic.removeProducto(entity.getId() , p.getId());
        
        if(entity.getProductos().contains(p))
        {
            Assert.fail();
        }

    }
    
        /**
     * Prueba para reemplazar un horario
     */
    @Test
    public void replaceHorarioTest() {

        ContratoEntity entity = data.get(0);
        HorarioEntity nuevo = factory.manufacturePojo(HorarioEntity.class);

        HorarioEntity hor = contratoLogic.replaceHorario(entity.getId(), nuevo);

        Assert.assertEquals(nuevo, hor);
    }
}
