package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;
import co.edu.uniandes.csw.fiestas.persistence.ProductoPersistence;
import co.edu.uniandes.csw.fiestas.persistence.TematicaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Tematica.
 *
 * @author af.losada
 */
public class TematicaLogic {

    private static final Logger LOGGER = Logger.getLogger(TematicaLogic.class.getName());

    @Inject
    private TematicaPersistence persistence;

    @Inject
    private ProductoPersistence productoPer;

    /**
     * Obtiene la lista de los registros de Tematica.
     *
     * @return Colección de objetos de TematicaEntity.
     */
    public List<TematicaEntity> getTematicas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tematicas");
        return persistence.findAll();
    }

    /**
     * Obtiene la Tematica.
     *
     * @param id Id de la tematica
     * @return Objetos de TematicaEntity.
     */
    public TematicaEntity getTematica(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tematicas");
        return persistence.find(id);
    }

    /**
     * Se encarga de crear una tematica en la base de datos.
     *
     * @param entity Objeto de TematicaEntity con los datos nuevos
     * @return Objeto de TematicaEntity con los datos nuevos y su ID.
     */
    public TematicaEntity createTematica(TematicaEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un tematica ");
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Tematica.
     *
     * @param id
     * @param entity Instancia de TematicaEntity con los nuevos datos.
     * @return Instancia de TematicaEntity con los datos actualizados.
     */
    public TematicaEntity updateTematica( Long id ,TematicaEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar tematica con id={0}", entity.getId());
        TematicaEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar tematica con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar una tematica por ID
     *
     * @param id El ID del tematica a eliminar
     */
    public void deleteTematica(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar tematica con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar tematica con id={0}", id);
    }

    /**
     * Obtiene una colección de instancias de ProductoEntity asociadas a una
     * instancia de Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @return Colección de instancias de ProductoEntity asociadas a la
     * instancia de Tematica
     */
    public List<ProductoEntity> listProductos(Long TematicaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros del autor con id = {0}", TematicaId);
        return getTematica(TematicaId).getProductos();
    }

    /**
     * Obtiene una instancia de ProductoEntity asociada a una instancia de
     * Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @param productosId Identificador de la instancia de Producto
     * @return La entidadd de Producto de la tematica
     */
    public ProductoEntity getProducto(Long TematicaId, Long productosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un libro con id = {0}", productosId);
        List<ProductoEntity> list = getTematica(TematicaId).getProductos();
        ProductoEntity productosEntity = new ProductoEntity();
        productosEntity.setId(productosId);
        int index = list.indexOf(productosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Producto existente a un Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @param productosId Identificador de la instancia de Producto
     * @return Instancia de ProductoEntity que fue asociada a Tematica
     */
    public ProductoEntity addProducto(Long TematicaId, Long productosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un libro al Tematica con id = {0}", TematicaId);
        TematicaEntity tematica = persistence.find(TematicaId);
        ProductoEntity producto = productoPer.find(productosId);
        tematica.getProductos().add(producto);
        return producto;
    }
    
    public ProductoEntity removeProducto(Long TematicaId, Long productoId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un libro al Tematica con id = {0}", TematicaId);
        TematicaEntity tematica = persistence.find(TematicaId);
        ProductoEntity producto = productoPer.find(productoId);
        tematica.getProductos().remove(producto);
        return producto;
    }
    
    public List<ProductoEntity> replaceProductos(List<ProductoEntity> lista, Long TematicaId)
    {
        if(lista.size() != 0)
        {
            persistence.find(TematicaId).setProductos(lista);
        }
        return lista;
    }
}
