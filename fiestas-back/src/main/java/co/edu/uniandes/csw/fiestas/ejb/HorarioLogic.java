/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.HorarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Horario.
 *
 * @author df.nino10
 */
@Stateless
public class HorarioLogic 
{    
    private static final Logger LOGGER= Logger.getLogger(HorarioLogic.class.getName());
    
    @Inject
    private HorarioPersistence persistence;
    
    @Inject
    private EventoLogic eventoLogic;
    
            
    public List<HorarioEntity> getHorarios(){
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos los eventos.");
        return persistence.findAll();
    }

    public HorarioEntity getHorario(Long id) {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar el horario con id={0}", id);
        return persistence.find(id);
    }

    public HorarioEntity createHorario(HorarioEntity entity)throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación del horario con id={0}");
        if(persistence.find(entity.getId())!= null)
            throw new BusinessLogicException("El horario con el id\""+ entity.getId()+"\" ya existe");
        
        LOGGER.info("Termina proceso de creación del horario");
        return persistence.create(entity);
    }

    public HorarioEntity updateHorario(HorarioEntity entity) {
         LOGGER.log(Level.INFO,"Inicia proceso de actualización del horario con id={0}", entity.getId());
         HorarioEntity newEntity = persistence.update(entity);
         LOGGER.log(Level.INFO,"Termina proceso de actualización del horario con id={0}", entity.getId());
         return newEntity;
    }

    public void deleteHorario(Long id) 
    {
        LOGGER.log(Level.INFO,"Inicia proceso de actualización del horario con id={0}", id);
        persistence.delete(id);        
    }
    /**
     * Agregar un evento al horario
     *
     * @param eventoId El id evento a guardar
     * @param horarioId El id del horario en la cual se va a guardar el
     * evento.
     * @return El evento que fue agregado al horario.
     */
    public EventoEntity addEvento(Long eventoId, Long horarioId) throws BusinessLogicException 
    {
        HorarioEntity horarioEntity = getHorario(horarioId);
        EventoEntity eventoEntity = eventoLogic.getEvento(eventoId);
        if(horarioEntity != null)
        {
            horarioEntity.agregarEvento(eventoEntity);
        }
        else
        {
            throw new BusinessLogicException("El horario al que se le quiere agregar evento es nulo");
        }        
        return eventoEntity;
    }

    /**
     * Borrar un evento de un horario
     *
     * @param eventoId El evento que se desea borrar del horario.
     * @param horarioId El horario de la cual se desea eliminar.
     */
    public void removeEvento(Long eventoId, Long horarioId) throws BusinessLogicException 
    {
        HorarioEntity horarioEntity = getHorario(horarioId);
        EventoEntity evento = eventoLogic.getEvento(eventoId);
        int index = horarioEntity.getEventos().indexOf(evento);
        if(horarioEntity != null && index >=0)
        {
            horarioEntity.removerEvento(evento);
        }
        else
        {
            throw new BusinessLogicException("El horario al que se le quiere remover el evento es nulo");
        }        
        
    }

    /**
     * Remplazar eventos de un horario
     *
     * @param eventos Lista de eventos que serán los del horario.
     * @param horarioId El id del horario que se quiere actualizar.
     * @return La lista de eventos actualizada.
     */
    public List<EventoEntity> replaceEventos(Long horarioId, List<EventoEntity> eventos) throws BusinessLogicException 
    {
        HorarioEntity horario = getHorario(horarioId);
        if(horario != null)
        {
            if(eventos.size() ==0 || eventos == null)
            {
                throw new BusinessLogicException("No hay lista nueva de eventos o la lista está vacía");
            }
            else
            {
                horario.setEventos(eventos);
            }            
        }
        else
        {
            throw new BusinessLogicException("El horario al que se le quiere reemplazar eventos es nulo");
        }
        
        return eventos;
    }

    /**
     * Retorna todos los eventos asociados a un horario
     *
     * @param horarioId El ID del horario buscada
     * @return La lista de eventos del horario
     */
    public List<EventoEntity> getEventos(Long horarioId) 
    {
        return getHorario(horarioId).getEventos();
    }

    /**
     * Retorna un evento asociado a un horario
     *
     * @param horarioId El id del horario a buscar.
     * @param eventoId El id del evento a buscar
     * @return El evento encontrado dentro del horario.
     * @throws BusinessLogicException Si el evento no se encuentra en la horario
     */
    public EventoEntity getEvento(Long horarioId, Long eventoId) throws BusinessLogicException {
        List<EventoEntity> eventos = getHorario(horarioId).getEventos();
        EventoEntity evento = eventoLogic.getEvento(eventoId);
        int index = eventos.indexOf(evento);
        if (index >= 0) 
        {
            return eventos.get(index);
        }
        throw new BusinessLogicException("El horario no está asociado al horario");
    }

    /**
     * Obtiene una colección de instancias de EventoEntity asociadas a una
     * instancia de Horario
     *
     * @param horarioId Identificador de la instancia de Horario
     * @return Colección de instancias de EventoEntity asociadas a la instancia de
     * Horario
     *
     */
    public List<EventoEntity> listEventos(Long horarioId) 
    {
        return getHorario(horarioId).getEventos();
    } 
    
}
