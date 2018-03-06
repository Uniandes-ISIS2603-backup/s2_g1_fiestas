/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
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
    
    @Inject
    private ServicioPersistence persistence;
    
    @Inject 
    private ValoracionLogic valoracionLogic;
    
    @Inject
    private ProductoLogic productoLogic;
    
    
     /**
     * Obtiene la lista de los registros de Servicio.
     *
     * @return Colección de objetos de ServicioEntity.
     */
    public List<ServicioEntity> getServicios() {
        return persistence.findAll();
    }
    
     /**
     * Obtiene los datos de una instancia de Servicio a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ServicioEntity con los datos del Servicio consultado.
     */
    public ServicioEntity getServicio(Long id) {
        return persistence.find(id);
    }
    
    /**
     * Se encarga de crear un Servicio en la base de datos.
     *
     * @param entity Objeto de ServicioEntity con los datos nuevos
     * @return Objeto de ServicioEntity con los datos nuevos y su ID.
     */
    public ServicioEntity createServicio(ServicioEntity entity) {
        return persistence.create(entity);
    }
    
    /**
     * Actualiza la información de una instancia de Servicio.
     *
     * @param entity Instancia de ServicioEntity con los nuevos datos.
     * @return Instancia de ServicioEntity con los datos actualizados.
     */
    public ServicioEntity updateServicio(ServicioEntity entity) {
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Servicio de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteServicio(Long id) {
        persistence.delete(id);
    }
    
    /**
     * Obtiene una colección de instancias de ProveedorEntity asociadas a una
     * instancia de Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @return Colección de instancias de ProveedorEntity asociadas a la instancia
     * de Servicio
     * 
     */
    public List<ProveedorEntity> listProveedores(Long servicioId) {
        return getServicio(servicioId).getProveedores();
    }
    
    /**
     * Obtiene una instancia de ProveedorEntity asociada a una instancia de Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @param proveedoresId Identificador de la instancia de Proveedor
     * @return La entidad del Proveedor asociada al servicio
     */
    public ProveedorEntity getProveedor(Long servicioId, Long proveedoresId) {
        List<ProveedorEntity> list = getServicio(servicioId).getProveedores();
        ProveedorEntity proveedoresEntity = new ProveedorEntity();
        proveedoresEntity.setId(proveedoresId);
        int index = list.indexOf(proveedoresEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
    
    
      /**
     * Asocia un Proveedor existente a un Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @param proveedoresId Identificador de la instancia de Proveedor
     * @return Instancia de ProveedorEntity que fue asociada a Servicio
     * 
     */
    public ProveedorEntity addProveedor(Long servicioId, Long proveedoresId) {
        ServicioEntity servicioEntity = getServicio(servicioId);
        ProveedorEntity proveedoresEntity = new ProveedorEntity();
        proveedoresEntity.setId(proveedoresId);
        servicioEntity.getProveedores().add(proveedoresEntity);
        return getProveedor(servicioId, proveedoresId);
    }

    /**
     * Remplaza las instancias de Proveedor asociadas a una instancia de Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @param list Colección de instancias de ProveedorEntity a asociar a instancia
     * de Servicio
     * @return Nueva colección de ProveedorEntity asociada a la instancia de Servicio
     * 
     */
    public List<ProveedorEntity> replaceProveedores(Long servicioId, List<ProveedorEntity> list) {
        ServicioEntity servicioEntity = getServicio(servicioId);
        servicioEntity.setProveedores(list);
        return servicioEntity.getProveedores();
    }

    /**
     * Desasocia un Proveedor existente de un Servicio existente
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @param proveedoresId Identificador de la instancia de Proveedor
     * 
     */
    public void removeProveedor(Long servicioId, Long proveedoresId) {
        ServicioEntity entity = getServicio(servicioId);
        ProveedorEntity proveedoresEntity = new ProveedorEntity();
        proveedoresEntity.setId(proveedoresId);
        entity.getProveedores().remove(proveedoresEntity);
    }
    
        /**
     * Agregar un valoracion al servicio
     *
     * @param valoracionId El id valoracion a guardar
     * @param servicioId El id del servicio en la cual se va a guardar el
     * valoracion.
     * @return El valoracion que fue agregado al servicio.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si no existe la valoracion
     */
    public ValoracionEntity addValoracion(Long valoracionId, Long servicioId) throws BusinessLogicException {
        ServicioEntity servicioEntity = getServicio(servicioId);
        ValoracionEntity valoracionEntity = valoracionLogic.getValoracion(valoracionId);
        servicioEntity.getValoraciones().add(valoracionEntity);
        return valoracionEntity;
    }
    
     /**
    * Obtiene la lista de los registros de Valoracion que pertenecen a un Servicio.
     *
    * @param servicioid id del Servicio el cual es padre de las Valoraciones.
    * @return Colección de objetos de ValoracionEntity.
    * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si no hay valoraciones
    */
    public List<ValoracionEntity> getValoraciones(Long servicioid) throws BusinessLogicException {
        ServicioEntity servicio = getServicio(servicioid);
        if (servicio.getValoraciones() == null) {
           throw new BusinessLogicException("El servicio que consulta aún no tiene valoraciones");
       }
       if (servicio.getValoraciones().isEmpty()) {
           throw new BusinessLogicException("El servicio que consulta aún no tiene valoraciones");
       }
       return servicio.getValoraciones();
    }
    
    
    /**
     * Borrar un valoracion de un servicio
     *
     * @param valoracionId El valoracion que se desea borrar del proveedor.
     * @param servicioId El servicio del cual se desea eliminar.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si no existe la valoracion
     */
    public void removeValoracion(Long valoracionId, Long servicioId) throws BusinessLogicException  {
        ServicioEntity servicio = getServicio(servicioId);
        ValoracionEntity valoracion = valoracionLogic.getValoracion(valoracionId);
        servicio.getValoraciones().remove(valoracion);
    }

    /**
     * Remplazar valoraciones de un servicio
     *
     * @param valoraciones Lista de valoraciones que serán los del proveedor.
     * @param servicioId El id del servicio que se quiere actualizar.
     * @return La lista de valoraciones actualizada.
     */
    public List<ValoracionEntity> replaceValoraciones(Long servicioId, List<ValoracionEntity> valoraciones)
    {
        ServicioEntity servicio = getServicio(servicioId);
        servicio.setValoraciones(valoraciones);
        return valoraciones;
    }
    
    /**
     * Se encarga de agregar una Valoracion al servicio
     *
     * @param productoId id de el nuevo Producto.
     * @param servicioId id del servicio el cual sera padre del nuevo Valoracion.
     * @return Objeto de ProductoEntity con los datos nuevos y su ID.
     * 
     */
    public ProductoEntity addProducto(Long productoId, Long servicioId) {
        ServicioEntity servicio = getServicio(servicioId);
        ProductoEntity entity = productoLogic.getProducto(productoId);
        servicio.getProductos().add(entity);
        entity.setServicio(servicio);
        return entity;
    }
    
        /**
     * Retorna un valoracion asociado a un servicio
     *
     * @param servicioId El id del servicio a buscar.
     * @param valoracionId El id del valoracion a buscar
     * @return El valoracion encontrado dentro del servicio.
     * @throws BusinessLogicException Si el valoracion no se encuentra en el servicio
     */
    public ValoracionEntity getValoracion(Long servicioId, Long valoracionId) throws BusinessLogicException {
        List<ValoracionEntity> valoraciones = getServicio(servicioId).getValoraciones();
        ValoracionEntity valoracion = valoracionLogic.getValoracion(valoracionId);
        int index = valoraciones.indexOf(valoracion);
        if (index >= 0) {
            return valoraciones.get(index);
        }
        throw new BusinessLogicException("El valoracion no está asociado al proveedor");
    }
    
}
