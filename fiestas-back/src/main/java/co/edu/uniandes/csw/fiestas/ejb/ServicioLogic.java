/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
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
    
}
