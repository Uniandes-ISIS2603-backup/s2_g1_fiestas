/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import co.edu.uniandes.csw.fiestas.persistence.ProductoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author af.losada
 */
public class ProductoLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ProductoLogic.class.getName());
    
    @Inject
    private ProductoPersistence persistence;
    
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
    
    
    
    
    
}
