/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.PagoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author cm.amaya10
 */
@Stateless
public class PagoLogic {
    private static final Logger LOGGER = Logger.getLogger(PagoLogic.class.getName());
    
    @Inject
    private PagoPersistence persistence;
    
    @Inject
    private EventoLogic eventoLogic;
    
        /**
     * Devuelve todos los pagos que hay en la base de datos.
     *
     * @return Lista de entidades de tipo pago.
     */
    public List<PagoEntity> getPagos(){
        LOGGER.info("Inicia proceso de consultar todos los pagos");
        List<PagoEntity> pagos =persistence.findAll();
        LOGGER.info("Termina proceso de consultar todos los pagos");
        return pagos;
    }
    
    /**
     * Busca un pago por ID
     *
     * @param id El id del evento a buscar
     * @return El evento encontrado, null si no lo encuentra.
     */
    public PagoEntity getPago(Long id){
        LOGGER.log(Level.INFO, "Inicia proceso de consultar pago con id={0}", id);
        PagoEntity pago=persistence.find(id);
        if(pago==null){
             LOGGER.log(Level.SEVERE, "El pago con el id {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar pago con id={0}", id);
        return pago;
    }
    
     /**
     * Se encarga de crear un pago en la base de datos.
     *
     * @param entity Objeto de PagoEntity con los datos nuevos
     * @return Objeto de PagoEntity con los datos nuevos y su ID.
     */
     public PagoEntity createPago(PagoEntity entity){
        LOGGER.log(Level.INFO, "Inicia proceso de crear un pagoo ");
        return persistence.create(entity);
     }
    
     
    /**
     * Actualiza la información de una instancia de Pago.
     *
     * @param entity Instancia de PagoEntity con los nuevos datos.
     * @return Instancia de PagoEntity con los datos actualizados.
     */
     public PagoEntity updatePago(PagoEntity entity){
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar evento con id={0}", entity.getId());
        PagoEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar evento con id={0}", entity.getId());
        return newEntity;
     }
     
     /**
     * Eliminar un pago por ID
     *
     * @param id El ID del pago a eliminar
     */
    public void deletePago(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar pago con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar pago con id={0}", id);
    }
}
