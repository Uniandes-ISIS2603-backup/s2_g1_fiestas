package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ContratoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de contrato.
 * 
 * @author mc.gonzalez15
 */
public class ContratoLogic {

    private static final Logger LOGGER = Logger.getLogger(ContratoLogic.class.getName());

    @Inject
    /**
     * Inyección de la clase de lógica de contrato
     */
    private ContratoPersistence persistence;

    @Inject
    /**
     * Inyección de la clase de lógica de producto
     */
    private ProductoLogic productoLogic;
    

   
    /**
     * Obtiene la lista de los registros de Contrato.
     *
     * @return Colección de objetos de ContratoEntity.
     */
    public List<ContratoEntity> getContratos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los contratos");
        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Contrato a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ContratoEntity con los datos del Contrato
     * consultado.
     */
    public ContratoEntity getContrato(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un contrato con id = {0}", id);
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Contrato en la base de datos.
     *
     * @param entity Objeto de ContratoEntity con los datos nuevos
     * @return Objeto de ContratoEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public ContratoEntity createContrato(ContratoEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un contrato ");
        
        if(entity.getValor() < 0)
        {
            throw new BusinessLogicException("El valor del contrato no puede ser menor a cero");
        }
        
        if(entity.getTyc().isEmpty() || entity.getTyc().equals(" "))
        {
            throw new BusinessLogicException("Los términos y condiciones del contrato no pueden estar vacíos.");
        }

        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Contrato.
     *
     * @param entity Instancia de ContratoEntity con los nuevos datos.
     * @return Instancia de ContratoEntity con los datos actualizados.
     */
    public ContratoEntity updateContrato(ContratoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un contrato ");
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Contrato de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteContrato(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un contrato ");
        persistence.delete(id);
    }
   
    /**
     * Obtiene una colección de instancias de ProductoEntity asociadas a una
     * instancia de Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @return Colección de instancias de ProductoEntity asociadas a la
     * instancia de Contrato
     */
    public List<ProductoEntity> listProductos(Long contratoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos del autor con id = {0}", contratoId);
        return getContrato(contratoId).getProductos();
    }

    /**
     * Obtiene una instancia de ProductoEntity asociada a una instancia de
     * Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param ProductosId Identificador de la instancia de Producto
     * @return La entidadd de Libro del autor
     */
    public ProductoEntity getProducto(Long contratoId, Long ProductosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un producto con id = {0}", ProductosId);
        List<ProductoEntity> list = getContrato(contratoId).getProductos();
        ProductoEntity productosEntity = new ProductoEntity();
        productosEntity.setId(ProductosId);
        int index = list.indexOf(productosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Producto existente a un Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param ProductosId Identificador de la instancia de Producto
     * @return Instancia de ProductoEntity que fue asociada a Contrato
     */
    public ProductoEntity addProducto(Long contratoId, Long ProductosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un producto al contrato con id = {0}", contratoId);
        ContratoEntity contrato = this.getContrato(contratoId);
        ProductoEntity producto = productoLogic.getProducto(ProductosId);
        List<ProductoEntity> productos = contrato.getProductos();
        productos.add(producto);
        contrato.setProductos(productos);
        return producto;
    }

    /**
     * Remplaza las instancias de Producto asociadas a una instancia de Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param list Colección de instancias de ProductoEntity a asociar a
     * instancia de Contrato
     * @return Nueva colección de ProductoEntity asociada a la instancia de
     * Contrato
     */
    public List<ProductoEntity> replaceProductos(Long contratoId, List<ProductoEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los productos asocidos al contrato con id = {0}", contratoId);
        ContratoEntity contratoEntity = getContrato(contratoId);
        contratoEntity.setProductos(list);
        return contratoEntity.getProductos();
    }

    /**
     * Desasocia un Producto existente de un Contrato existente
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param ProductosId Identificador de la instancia de Producto
     */
    public void removeProducto(Long contratoId, Long ProductosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un producto del contrato con id = {0}", contratoId);
        ContratoEntity contrato = getContrato(contratoId);
        ProductoEntity producto = getProducto(contratoId, ProductosId);
        contrato.getProductos().remove(producto);
    }
    
    /**
     * Actualiza la informacion de un Horario existente de un Contrato Existente
     * 
     * @param contratoId Identificador de la instancia de Contrato
     * @param entity Identificador de la instancia de Producto
     * @return 
     */
    public HorarioEntity replaceHorario(Long contratoId, HorarioEntity entity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar el horario del contrato con id = {0}", contratoId);
        ContratoEntity contrato = getContrato(contratoId);
        contrato.setHorario(entity);
        return contrato.getHorario();
        
    }
    
    public HorarioEntity getHorario(Long id)
    {
        HorarioEntity he = getContrato(id).getHorario();
        return he;
    }
    
    public BonoEntity getBono(Long id){
        BonoEntity bE=getContrato(id).getBono();
        return bE;
    }
    
    
    public void setBono(BonoEntity eE, Long idC) throws BusinessLogicException{
        ContratoEntity cE = getContrato(idC);
        if(cE==null)
            throw new BusinessLogicException("El contrato es nulo.");
        BonoEntity bE = getBono(idC);
        if(eE==null)
            throw new BusinessLogicException("El bono a aplicar es nulo.");
        if(cE.getBono()!=null)
            throw new BusinessLogicException("El contrato ya tine aplcado un bono.");
        cE.setBono(bE);
        persistence.update(cE);
        
    }
    
    
}
