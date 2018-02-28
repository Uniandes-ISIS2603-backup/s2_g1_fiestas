/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @proveedor nm.hernandez10
 */
@Stateless
public class ProveedorLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ProveedorLogic.class.getName());

    @Inject
    private ProveedorPersistence persistence;

    @Inject
    private ServicioLogic servicioLogic;
    
    /**
     * Obtiene la lista de los registros de Proveedor.
     *
     * @return Colección de objetos de ProveedorEntity.
     */
    public List<ProveedorEntity> getProveedores() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los proveedores");
        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Proveedor a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ProveedorEntity con los datos del Proveedor consultado.
     */
    public ProveedorEntity getProveedor(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un proveedor con id = {0}", id);
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Proveedor en la base de datos.
     *
     * @param entity Objeto de ProveedorEntity con los datos nuevos
     * @return Objeto de ProveedorEntity con los datos nuevos y su ID.
     */
    public ProveedorEntity createProveedor(ProveedorEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un proveedor ");
        
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Proveedor.
     *
     * @param entity Instancia de ProveedorEntity con los nuevos datos.
     * @return Instancia de ProveedorEntity con los datos actualizados.
     */
    public ProveedorEntity updateProveedor(ProveedorEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un proveedor ");
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Proveedor de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteProveedor(Long id)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un proveedor ");
        persistence.delete(id);
    }

    /**
     * Obtiene una colección de instancias de ServicioEntity asociadas a una
     * instancia de Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @return Colección de instancias de ServicioEntity asociadas a la instancia de
     * Proveedor
     */
    public List<ServicioEntity> listServicios(Long proveedorId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los servicios del proveedor con id = {0}", proveedorId);
        return getProveedor(proveedorId).getServicios();
    }

    /**
     * Obtiene una instancia de ServicioEntity asociada a una instancia de Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param serviciosId Identificador de la instancia de Servicio
     * @return La entidadd de Libro del proveedor
     */
    public ServicioEntity getServicio(Long proveedorId, Long serviciosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un servicio con id = {0}", serviciosId);
        List<ServicioEntity> list = getProveedor(proveedorId).getServicios();
        ServicioEntity serviciosEntity = new ServicioEntity();
        serviciosEntity.setId(serviciosId);
        int index = list.indexOf(serviciosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Servicio existente a un Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param serviciosId Identificador de la instancia de Servicio
     * @return Instancia de ServicioEntity que fue asociada a Proveedor
     */
    public ServicioEntity addServicio(Long proveedorId, Long serviciosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un servicio al proveedor con id = {0}", proveedorId);
        servicioLogic.addProveedor(serviciosId, proveedorId);
        return servicioLogic.getServicio(serviciosId);
    }

    /**
     * Remplaza las instancias de Servicio asociadas a una instancia de Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param list Colección de instancias de ServicioEntity a asociar a instancia
     * de Proveedor
     * @return Nueva colección de ServicioEntity asociada a la instancia de Proveedor
     */
    public List<ServicioEntity> replaceServicios(Long proveedorId, List<ServicioEntity> list) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los servicios asocidos al proveedor con id = {0}", proveedorId);
        ProveedorEntity proveedorEntity = getProveedor(proveedorId);
        List<ServicioEntity> servicioList = servicioLogic.getServicios();
        for (ServicioEntity servicio : servicioList) {
            if (list.contains(servicio)) {
                if (!servicio.getProveedores().contains(proveedorEntity)) {
                    servicioLogic.addProveedor(servicio.getId(), proveedorId);
                }
            } else {
                servicioLogic.removeProveedor(servicio.getId(), proveedorId);
            }
        }
        proveedorEntity.setServicios(list);
        return proveedorEntity.getServicios();
    }

    /**
     * Desasocia un Servicio existente de un Proveedor existente
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param serviciosId Identificador de la instancia de Servicio
     */
    public void removeServicio(Long proveedorId, Long serviciosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un servicio del proveedor con id = {0}", proveedorId);
        servicioLogic.removeProveedor(serviciosId, proveedorId);
    }
}
