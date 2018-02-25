/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.EventoPersistence;
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
public class EventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    private EventoPersistence persistence;

    /**
     * Devuelve todos los eventos que hay en la base de datos.
     *
     * @return Lista de entidades de tipo evento.
     */
    public List<EventoEntity> getEventos() {
        LOGGER.info("Inicia proceso de consultar todos los eventos");
        List<EventoEntity> eventos = persistence.findAll();
        LOGGER.info("Termina proceso de consultar todos los eventos");
        return eventos;
    }

    /**
     * Busca un evento por ID
     *
     * @param id El id del evento a buscar
     * @return El evento encontrado, null si no lo encuentra.
     */
    public EventoEntity getEvento(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar evento con id={0}", id);
        EventoEntity evento = persistence.find(id);
        if (evento == null) {
            LOGGER.log(Level.SEVERE, "El evento con el id {0} no existe", id);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar evento con id={0}", id);
        return evento;
    }

    /**
     * Se encarga de crear un evento en la base de datos.
     *
     * @param entity Objeto de EventoEntity con los datos nuevos
     * @return Objeto de EventoEntity con los datos nuevos y su ID.
     */
    public EventoEntity createEvento(EventoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un evento ");
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Evento.
     *
     * @param entity Instancia de EventoEntity con los nuevos datos.
     * @return Instancia de EventoEntity con los datos actualizados.
     */
    public EventoEntity updateEvento(EventoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar evento con id={0}", entity.getId());
        EventoEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar evento con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar un evento por ID
     *
     * @param id El ID del evento a eliminar
     */
    public void deleteEvento(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar evento con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar evento con id={0}", id);
    }
}
