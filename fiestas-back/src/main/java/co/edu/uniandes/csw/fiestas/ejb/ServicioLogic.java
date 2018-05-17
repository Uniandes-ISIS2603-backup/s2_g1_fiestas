/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Servicio.
 *
 * @author ls.arias
 */
@Stateless
public class ServicioLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ServicioLogic.class.getName());
    
    @Inject
    private ServicioPersistence persistence;
    
 
     /**
     * Obtiene la lista de los registros de Servicio.
     *
     * @return Colección de objetos de ServicioEntity.
     */
    public List<ServicioEntity> getServicios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los servicios");
        return persistence.findAll();
    }
    
     /**
     * Obtiene los datos de una instancia de Servicio a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ServicioEntity con los datos del Servicio consultado.
     */
    public ServicioEntity getServicio(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el servicio con el id dado");
        return persistence.find(id);
    }
    
    /**
     * Se encarga de crear un Servicio en la base de datos.
     *
     * @param entity Objeto de ServicioEntity con los datos nuevos
     * @return Objeto de ServicioEntity con los datos nuevos y su ID.
     */
    public ServicioEntity createServicio(ServicioEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un servicio");
        return persistence.create(entity);
    }
    
    /**
     * Actualiza la información de una instancia de Servicio.
     *
     * @param entity Instancia de ServicioEntity con los nuevos datos.
     * @return Instancia de ServicioEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public ServicioEntity updateServicio(ServicioEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un servicio");
        if(getServicio(entity.getId()) == null)
        {
            throw new BusinessLogicException("No existe un servicio con dicho id para actualizar");
        }  
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Servicio de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public void deleteServicio(Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un servicio.");
        if(persistence.find(id)==null)
        {
            throw new BusinessLogicException("El servicio que se quiere borrar no existe.");
        }
            
        persistence.delete(id);
    }
    
    /**
     * Obtiene una colección de instancias de ProductoEntity asociadas a una
     * instancia de Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @return Colección de instancias de ProductoEntity asociadas a la instancia
     * de Servicio
     * 
     */
    public List<ProductoEntity> listProductos(Long servicioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la lista de productos asociada a un servicio.");
        return getServicio(servicioId).getProductos();
    }
    
    /**
     * Obtiene una instancia de ProductoEntity asociada a una instancia de Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @param productosId Identificador de la instancia de Producto
     * @return La entidad del Producto asociada al servicio
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public ProductoEntity getProducto(Long servicioId, Long productosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el producto con el id dado asociado al servicio");
        List<ProductoEntity> list = getServicio(servicioId).getProductos();
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setId(productosId);
        int index = list.indexOf(productoEntity);
        if (index >= 0) {
            return list.get(index);
        }
        throw new BusinessLogicException("El producto no está asociado al servicio");
      
    }
    
    
     /**
     * Asocia un Producto existente a un Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @param productoId Identificador de la instancia de Producto
     * @return Instancia de ProductoEntity que fue asociada a Servicio
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     * 
     */
    public ProductoEntity addProducto(Long servicioId, Long productoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un producto al servicio");
        ServicioEntity servicioEntity = getServicio(servicioId);
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setId(productoId);
        servicioEntity.getProductos().add(productoEntity);
        return getProducto(servicioId, productoId);
    }

   
    /**
     * Desasocia un Producto existente de un Servicio existente
     * @param servicioId Identificador de la instancia de Servicio
     * @param productosId Identificador de la instancia de Producto
     * 
     */
    public void removeProducto(Long servicioId, Long productosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el producto asociado al servicio");
        ServicioEntity entity = getServicio(servicioId);
        ProductoEntity productosEntity = new ProductoEntity();
        productosEntity.setId(productosId);
        entity.getProductos().remove(productosEntity);
        updateServicio(entity);
    }
}

