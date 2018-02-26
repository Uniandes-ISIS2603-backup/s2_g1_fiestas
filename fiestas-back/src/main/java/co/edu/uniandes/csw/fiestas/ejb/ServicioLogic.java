/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
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
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un servicio con id = {0}", id);
        return persistence.find(id);
    }
    
    /**
     * Se encarga de crear un Servicio en la base de datos.
     *
     * @param entity Objeto de ServicioEntity con los datos nuevos
     * @return Objeto de ServicioEntity con los datos nuevos y su ID.
     */
    public ServicioEntity createServicio(ServicioEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un servicio ");
        
        return persistence.create(entity);
    }
    
    /**
     * Actualiza la información de una instancia de Servicio.
     *
     * @param entity Instancia de ServicioEntity con los nuevos datos.
     * @return Instancia de ServicioEntity con los datos actualizados.
     */
    public ServicioEntity updateServicio(ServicioEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un servicio ");
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Servicio de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteServicio(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor ");
        persistence.delete(id);
    }
    
    
}
