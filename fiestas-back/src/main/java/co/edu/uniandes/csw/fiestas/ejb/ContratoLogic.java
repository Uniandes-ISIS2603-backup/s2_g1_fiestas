/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.persistence.ContratoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author mc.gonzalez15
 */
public class ContratoLogic {

    private static final Logger LOGGER = Logger.getLogger(ContratoLogic.class.getName());

    @Inject
    private ContratoPersistence persistence;

    @Inject
    private EventoLogic eventoLogic;

    /**
     * Obtiene la lista de los registros de Contrato.
     *
     * @return Colección de objetos de ContratoEntity.
     */
    public List<ContratoEntity> getContratos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los contratos");
        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Contrato a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ContratoEntity con los datos del Contrato
     * consultado.
     */
    public ContratoEntity getContrato(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un contrato con id = {0}", id);
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Contrato en la base de datos.
     *
     * @param entity Objeto de ContratoEntity con los datos nuevos
     * @return Objeto de ContratoEntity con los datos nuevos y su ID.
     */
    public ContratoEntity createContrato(ContratoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un contrato ");

        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Contrato.
     *
     * @param entity Instancia de ContratoEntity con los nuevos datos.
     * @return Instancia de ContratoEntity con los datos actualizados.
     */
    public ContratoEntity updateContrato(ContratoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un contrato ");
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Contrato de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteContrato(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un contrato ");
        persistence.delete(id);
    }

}
