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
import co.edu.uniandes.csw.fiestas.persistence.ValoracionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @valoracion ls.arias
 */
@Stateless
public class ValoracionLogic {
     
    @Inject
    private ValoracionPersistence persistence;
    @Inject
    private ServicioLogic servicioLogic;

    @Inject 
    private ProveedorLogic proveedorLogic;
    
    
    /**
     * Obtiene la lista de los registros de Valoracion que pertenecen a un Servicio.
     *
     * @param servicioid id del Servicio el cual es padre de las Valoraciones.
     * @return Colección de objetos de ValoracionEntity.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public List<ValoracionEntity> getValoracionesServicio(Long servicioid) throws BusinessLogicException {
        ServicioEntity servicio = servicioLogic.getServicio(servicioid);
        if (servicio.getValoraciones() == null) {
            throw new BusinessLogicException("El servicio que consulta aún no tiene valoraciones");
        }
        if (servicio.getValoraciones().isEmpty()) {
            throw new BusinessLogicException("El servicio que consulta aún no tiene valoraciones");
        }
        return servicio.getValoraciones();
    }
    
    /**
     * Obtiene la lista de los registros de Valoracion que pertenecen a un Proveedor.
     *
     * @param proveedorid id del Proveedor el cual es padre de las Valoraciones.
     * @return Colección de objetos de ValoracionEntity.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public List<ValoracionEntity> getValoracionesProveedor(Long proveedorid) throws BusinessLogicException {
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedorid);
        if (proveedor.getValoraciones() == null) {
            throw new BusinessLogicException("El proveedor que consulta aún no tiene valoraciones");
        }
        if (proveedor.getValoraciones().isEmpty()) {
            throw new BusinessLogicException("El proveedor que consulta aún no tiene valoraciones");
        }
        return proveedor.getValoraciones();
    }

    
     /**
     * Obtiene los datos de una instancia de Valoracion a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ValoracionEntity con los datos de la Valoracion consultada.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public ValoracionEntity getValoracion(Long id) throws BusinessLogicException {
        if(persistence.find(id)==null)
        {
            throw new BusinessLogicException("La valoración no existe");
        }
        return persistence.find(id);
    }
    
    /**
     * Se encarga de crear un Valoracion en la base de datos.
     *
     * @param entity Objeto de ValoracionEntity con los datos nuevos
     * @param servicioid id del servicio el cual sera padre del nuevo Valoracion.
     * @return Objeto de ValoracionEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     * 
     */
    public ValoracionEntity createValoracionServicio(Long servicioid, ValoracionEntity entity)throws BusinessLogicException {
        if(entity.getCalificacion()>5.0 || entity.getCalificacion()<1.0)
        {
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
        ServicioEntity servicio = servicioLogic.getServicio(servicioid);
        entity.setServicio(servicio);
        return persistence.create(entity);
    }
    
    /**
     * Se encarga de crear un Valoracion en la base de datos.
     *
     * @param entity Objeto de ValoracionEntity con los datos nuevos
     * @param proveedorid id del proveedor el cual sera padre del nuevo Valoracion.
     * @return Objeto de ValoracionEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     * 
     */
    public ValoracionEntity createValoracionProveedor(Long proveedorid, ValoracionEntity entity)throws BusinessLogicException {
        if(entity.getCalificacion()>5.0 || entity.getCalificacion()<1.0)
        {
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedorid);
        entity.setProveedor(proveedor);
        return persistence.create(entity);
    }
    
   /**
     * Actualiza la información de una instancia de Valoracion.
     *
     * @param entity Instancia de ValoracionEntity con los nuevos datos.
     * @param servicioid id del Servicio el cual sera padre del Valoracion actualizado.
     * @return Instancia de ValoracionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     * 
     */
    public ValoracionEntity updateValoracionServicio(Long servicioid, ValoracionEntity entity) throws BusinessLogicException {
    if(entity.getCalificacion()>5.0 || entity.getCalificacion()<1.0)
        {
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
        ServicioEntity servicio = servicioLogic.getServicio(servicioid);
        entity.setServicio(servicio);
        return persistence.update(entity);
    }
    
     /**
     * Actualiza la información de una instancia de Valoracion.
     *
     * @param entity Instancia de ValoracionEntity con los nuevos datos.
     * @param proveedorid id del Proveedor el cual sera padre del Valoracion actualizado.
     * @return Instancia de ValoracionEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     * 
     */
    public ValoracionEntity updateValoracionProveedor(Long proveedorid, ValoracionEntity entity) throws BusinessLogicException {
    if(entity.getCalificacion()>5.0 || entity.getCalificacion()<1.0)
        {
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedorid);
        entity.setProveedor(proveedor);
        return persistence.update(entity);
    }
    
    /**
     * Elimina una instancia de Valoracion de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteValoracion(Long id) {
        persistence.delete(id);
    }

}
