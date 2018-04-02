/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

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
    
    private static final Logger LOGGER = Logger.getLogger(ServicioLogic.class.getName());
    
    @Inject
    private ServicioPersistence persistence;
    
    @Inject 
    private ValoracionLogic valoracionLogic;
    
 
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
     * Obtiene una colección de instancias de ProveedorEntity asociadas a una
     * instancia de Servicio
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @return Colección de instancias de ProveedorEntity asociadas a la instancia
     * de Servicio
     * 
     */
    public List<ProveedorEntity> listProveedores(Long servicioId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la lista de proveedores asocida a un servicio.");
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
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el proveedor con el id dado asociado al servicio");
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
    public ProveedorEntity addProveedor(Long servicioId, Long proveedorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un proveedor al servicio");
        ServicioEntity servicioEntity = getServicio(servicioId);
        ProveedorEntity proveedorEntity = new ProveedorEntity();
        proveedorEntity.setId(proveedorId);
        servicioEntity.getProveedores().add(proveedorEntity);
        return getProveedor(servicioId, proveedorId);
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
    public List<ProveedorEntity> replaceProveedores(Long servicioId, List<ProveedorEntity> list) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la lista de proveedores asociada a un servicio");
        ServicioEntity servicio = getServicio(servicioId);
        
        if (list == null) 
        {
            throw new BusinessLogicException("No hay lista nueva.");
        }
        if (!list.isEmpty()) 
        {
        } 
        else 
        {
            throw new BusinessLogicException("No hay lista nueva o la lista está vacía");
        }
        if (servicio != null)
        {
            servicio.setProveedores(list);
            updateServicio(servicio);
        } 
        else 
        {
            throw new BusinessLogicException("El servicio al que se le quiere reemplazar proveedores es nulo");
        }
        return list;
    }

    /**
     * Desasocia un Proveedor existente de un Servicio existente
     *
     * @param servicioId Identificador de la instancia de Servicio
     * @param proveedoresId Identificador de la instancia de Proveedor
     * 
     */
    public void removeProveedor(Long servicioId, Long proveedoresId) {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el proveedor asociado al servicio");
        ServicioEntity entity = getServicio(servicioId);
        ProveedorEntity proveedoresEntity = new ProveedorEntity();
        proveedoresEntity.setId(proveedoresId);
        entity.getProveedores().remove(proveedoresEntity);
    }
     
    /**
    * Obtiene la lista de los registros de Valoracion que pertenecen a un Servicio.
     *
    * @param servicioid id del Servicio el cual es padre de las Valoraciones.
    * @return Colección de objetos de ValoracionEntity.
    * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si no hay valoraciones
    */
    public List<ValoracionEntity> getValoraciones(Long servicioid) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las valoraciones asociadas al servicio");
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
     * Retorna un valoracion asociado a un servicio
     *
     * @param servicioId El id del servicio a buscar.
     * @param valoracionId El id del valoracion a buscar
     * @return El valoracion encontrado dentro del servicio.
     * @throws BusinessLogicException Si el valoracion no se encuentra en el servicio
     */
    public ValoracionEntity getValoracion(Long servicioId, Long valoracionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la valoracion asociada al servicio");
        List<ValoracionEntity> valoraciones = getServicio(servicioId).getValoraciones();
        ValoracionEntity valoracion = valoracionLogic.getValoracion(valoracionId);
        int index = valoraciones.indexOf(valoracion);
        if (index >= 0) {
            return valoraciones.get(index);
        }
        throw new BusinessLogicException("El valoracion no está asociado al proveedor");
    }
    
}

