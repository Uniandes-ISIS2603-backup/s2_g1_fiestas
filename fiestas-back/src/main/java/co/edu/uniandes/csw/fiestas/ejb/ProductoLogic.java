
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.persistence.ProductoPersistence;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *Clase que implementa la conexion con la persistencia para la entidad de
 * Producto.
 *
 * @author af.losada
 */
@Stateless
public class ProductoLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ProductoLogic.class.getName());
    
    @Inject
    private ProductoPersistence persistence;
    
    @Inject
    private ServicioPersistence perSer;
    
    /**
     * Obtiene la lista de los registros de Producto.
     *
     * @return Colección de objetos de ProductoEntity.
     */
    public List<ProductoEntity> getProductos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos");
        return persistence.findAll();
    }
    
    /**
     * Obtiene la Producto.
     *
     * @param id del producto a buscar
     * @return Objetos de ProductoEntity.
     */
    public ProductoEntity getProducto(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos");
        return persistence.find(id);
    }  

    /**
     * Se encarga de crear un producto en la base de datos.
     *
     * @param entity Objeto de ProductoEntity con los datos nuevos
     * @return Objeto de ProductoEntity con los datos nuevos y su ID.
     */
    public ProductoEntity createProducto(ProductoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un producto ");
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Producto.
     *
     * @param entity Instancia de ProductoEntity con los nuevos datos.
     * @return Instancia de ProductoEntity con los datos actualizados.
     */
    public ProductoEntity updateProducto(ProductoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar producto con id={0}", entity.getId());
        ProductoEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar producto con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar un producto por ID
     *
     * @param id El ID del producto a eliminar
     */
    public void deleteProducto(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar producto con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar producto con id={0}", id);
    }
    
    /**
     * Busca el servicio de este producto
     * @param id
     * @return ServicioEntity de este producto
     */
    public ServicioEntity getServicio(Long id) 
    {
        ProductoEntity faind = persistence.find(id);
        return faind.getServicio();
    }
    
    /**
     * Cambia el servicio del producto
     * @param id id producto    
     * @param idSer servicio
     * @return 
     */
    public ServicioEntity addServicio(Long id, Long idSer) 
    {
        ServicioEntity find = perSer.find(idSer);
        ProductoEntity faind = persistence.find(id);
        faind.setServicio(find);
        return find;
    }
}
