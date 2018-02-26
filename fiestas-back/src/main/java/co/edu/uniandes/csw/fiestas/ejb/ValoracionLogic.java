/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.persistence.ValoracionPersistence;
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
public class ValoracionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ValoracionLogic.class.getName());
    
    @Inject
    private ValoracionPersistence persistence;
    
     /**
     * Obtiene la lista de los registros de Valoracion.
     *
     * @return Colección de objetos de ValoracionEntity.
     */
    public List<ValoracionEntity> getValoraciones() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las valoraciones");
        return persistence.findAll();
    }
    
        /**
     * Obtiene los datos de una instancia de Valoracion a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ValoracionEntity con los datos de la Valoracion consultada.
     */
    public ValoracionEntity getValoracion(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar una valoracion con id = {0}", id);
        return persistence.find(id);
    }
    
    /**
     * Se encarga de crear una Valoracion en la base de datos.
     *
     * @param entity Objeto de ValoracionEntity con los datos nuevos
     * @return Objeto de ValoracionEntity con los datos nuevos y su ID.
     */
    public ValoracionEntity createValoracion(ValoracionEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear una valoracion ");
        
        return persistence.create(entity);
    }
    
    /**
     * Actualiza la información de una instancia de Valoracion.
     *
     * @param entity Instancia de ValoracionEntity con los nuevos datos.
     * @return Instancia de ValoracionEntity con los datos actualizados.
     */
    public ValoracionEntity updateValoracion(ValoracionEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar una valoracion ");
        return persistence.update(entity);
    }
    
    /**
     * Elimina una instancia de Valoracion de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteValoracion(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una valoracion ");
        persistence.delete(id);
    }

}
