package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
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
     * Agregar un evento al cliente
     *
     * @param eventoId El id evento a guardar
     * @param clienteId El id del cliente en la cual se va a guardar el
     * evento.
     * @return El evento que fue agregado al cliente.
     */
    public EventoEntity addEvento(Long eventoId, Long clienteId) {
        ClienteEntity clienteEntity = getCliente(clienteId);
        EventoEntity eventoEntity = eventoLogic.getEvento(eventoId);
        eventoEntity.setCliente(clienteEntity);
        return eventoEntity;
    }

    /**
     * Borrar un evento de un cliente
     *
     * @param eventoId El evento que se desea borrar del cliente.
     * @param clienteId El cliente de la cual se desea eliminar.
     */
    public void removeEvento(Long eventoId, Long clienteId) {
        ClienteEntity clienteEntity = getCliente(clienteId);
        EventoEntity evento = eventoLogic.getEvento(eventoId);
        evento.setCliente(null);
        clienteEntity.getEventos().remove(evento);
    }

    /**
     * Remplazar eventos de un cliente
     *
     * @param eventos Lista de eventos que serán los del cliente.
     * @param clienteId El id del cliente que se quiere actualizar.
     * @return La lista de eventos actualizada.
     */
    public List<EventoEntity> replaceEventos(Long clienteId, List<EventoEntity> eventos) {
        ClienteEntity cliente = getCliente(clienteId);
        List<EventoEntity> eventoList = eventoLogic.getEventos();
        for (EventoEntity evento : eventoList) {
            if (eventos.contains(evento)) {
                evento.setCliente(cliente);
            } else if (evento.getCliente() != null && evento.getCliente().equals(cliente)) {
                evento.setCliente(null);
            }
        }
        return eventos;
    }

    /**
     * Retorna todos los eventos asociados a un cliente
     *
     * @param clienteId El ID del cliente buscada
     * @return La lista de eventos del cliente
     */
    public List<EventoEntity> getEventos(Long clienteId) {
        return getCliente(clienteId).getEventos();
    }

    /**
     * Retorna un evento asociado a un cliente
     *
     * @param clienteId El id del cliente a buscar.
     * @param eventoId El id del evento a buscar
     * @return El evento encontrado dentro del cliente.
     * @throws BusinessLogicException Si el evento no se encuentra en la cliente
     */
    public EventoEntity getEvento(Long clienteId, Long eventoId) throws BusinessLogicException {
        List<EventoEntity> eventos = getCliente(clienteId).getEventos();
        EventoEntity evento = eventoLogic.getEvento(eventoId);
        int index = eventos.indexOf(evento);
        if (index >= 0) {
            return eventos.get(index);
        }
        throw new BusinessLogicException("El evento no está asociado al cliente");
    }

    /**
     * Obtiene una colección de instancias de EventoEntity asociadas a una
     * instancia de Cliente
     *
     * @param clienteId Identificador de la instancia de Cliente
     * @return Colección de instancias de EventoEntity asociadas a la instancia de
     * Cliente
     *
     */
    public List<EventoEntity> listEventos(Long clienteId) 
    {
        return getCliente(clienteId).getEventos();
    }    
}
