/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @cliente nm.hernandez10
 */
@Stateless
public class ClienteLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());

    @Inject
    private ClientePersistence persistence;

    @Inject
    private EventoLogic eventoLogic;
    
    /**
     * Obtiene la lista de los registros de Cliente.
     *
     * @return Colección de objetos de ClienteEntity.
     */
    public List<ClienteEntity> getClientes() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los cliente");
        return persistence.findAll();
    }
    
    /**
     * Obtiene los datos de una instancia de Cliente a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ClienteEntity con los datos del Cliente consultado.
     */
    public ClienteEntity getCliente(Long id) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un cliente con id = {0}", id);
        return persistence.find(id);
    }
    
    /**
     * Se encarga de crear un Cliente en la base de datos.
     *
     * @param entity Objeto de ClienteEntity con los datos nuevos
     * @return Objeto de ClienteEntity con los datos nuevos y su ID.
     */
    public ClienteEntity createCliente(ClienteEntity entity) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un cliente");
        
        return persistence.create(entity);
    }
    
    /**
     * Actualiza la información de una instancia de Cliente.
     *
     * @param entity Instancia de ClienteEntity con los nuevos datos.
     * @return Instancia de ClienteEntity con los datos actualizados.
     */
    public ClienteEntity updateCliente(ClienteEntity entity) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un cliente");
        return persistence.update(entity);
    }
    
    /**
     * Elimina una instancia de Cliente de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteCliente(Long id) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un cliente");
        persistence.delete(id);
    }
    
    /**
     * Obtiene una colección de instancias de EventoEntity asociadas a una
     * instancia de Cliente
     *
     * @param id Identificador de la instancia de Cliente
     * @return Colección de instancias de EventoEntity asociadas a la instancia de
     * Cliente
     */
    public List<EventoEntity> listEventos(Long id)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los eventos del cliente con id = {0}", id);
        return getCliente(id).getEventos();
    }
    
    /**
     * Obtiene una instancia de EventoEntity asociada a una instancia de Cliente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param eventosId Identificador de la instancia de Evento
     * @return La entidadd de Libro del cliente
     */
    public EventoEntity getEvento(Long clienteId, Long eventosId) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un evento con id = {0}", eventosId);
        List<EventoEntity> list = getCliente(clienteId).getEventos();
        EventoEntity eventosEntity = new EventoEntity();
        eventosEntity.setId(eventosId);
        int index = list.indexOf(eventosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Evento existente a un Cliente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param eventosId Identificador de la instancia de Evento
     * @return Instancia de EventoEntity que fue asociada a Cliente
     */
    

    /**
     * Desasocia un Evento existente de un Cliente existente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @param eventosId Identificador de la instancia de Evento
     */
    
}
