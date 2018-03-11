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
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Cliente.
 *
 * @author nm.hernandez10
 */
@Stateless
public class ClienteLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());

    @Inject
    private ClientePersistence persistence;

    @Inject
    private EventoLogic eventoLogic;
    
    //Logic de apoyo para algunas reglas de negocio.
    @Inject
    private UsuarioLogic usuarioLogic;    
    
    
    /**
     * Obtiene la lista de los registros de Cliente.
     *
     * @return Colección de objetos de ClienteEntity.
     */
    public List<ClienteEntity> getClientes() {
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
    public ClienteEntity createCliente(ClienteEntity entity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un cliente");
        if(getCliente(entity.getId()) != null)
        {
            throw new BusinessLogicException("Ya existe un cliente con ese id");
        }
        if(usuarioLogic.repetidoLogin(entity.getLogin()))
        {
            throw new BusinessLogicException("Ya existe un usuario (cliente o cliente) con ese mismo login");
        }
        if(entity.getNombre() == null || entity.getNombre().equals(""))
        {
            throw new BusinessLogicException("No puede crear un cliente sin nombre");
        }
        if(entity.getDocumento() == null || entity.getDocumento().equals(""))
        {
            throw new BusinessLogicException("No puede crear un cliente sin documento");
        }
        if(entity.getLogin() == null || entity.getLogin().equals(""))
        {
            throw new BusinessLogicException("No puede crear un cliente sin login");
        }
        if(entity.getContraseña() == null || entity.getContraseña().equals(""))
        {
            throw new BusinessLogicException("No puede crear un cliente sin contraseña");
        }
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Cliente.
     *
     * @param entity Instancia de ClienteEntity con los nuevos datos.
     * @return Instancia de ClienteEntity con los datos actualizados.
     */
    public ClienteEntity updateCliente(ClienteEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un cliente");
        if(getCliente(entity.getId()) == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para actualizar");
        }  
        String loginAnterior = getCliente(entity.getId()).getLogin();
        String loginNuevo = entity.getLogin();
        if(!loginAnterior.equals(loginNuevo))
        {
            throw new BusinessLogicException("No puede cambiarse el login del cliente");
        }
        if(entity.getNombre() == null || entity.getNombre().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un cliente sin nombre");
        }
        if(entity.getDocumento() == null || entity.getDocumento().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un cliente sin documento");
        }
        if(entity.getLogin() == null || entity.getLogin().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un cliente sin login");
        }
        if(entity.getContraseña() == null || entity.getContraseña().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un cliente sin contraseña");
        }
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Cliente de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteCliente(Long id) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un cliente");
        if(getCliente(id) == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para eliminar");
        }
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
    public EventoEntity addEvento(Long eventoId, Long clienteId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un evento al cliente con id = {0}", clienteId);
        if(getCliente(clienteId) == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para agregar evento");
        }
        ClienteEntity ent = getCliente(clienteId);
        EventoEntity entC = eventoLogic.getEvento(eventoId);
        int index = ent.getEventos().indexOf(entC);
        if (index >= 0 && entC.equals(ent.getEventos().get(index))) 
        {
            throw new BusinessLogicException("Ya existe dicho evento en ese cliente");
        } 
        else 
        {
            ent.addEvento(entC);
            updateCliente(ent);
            return entC;            
        }
    }

    /**
     * Borrar un evento de un cliente
     *
     * @param eventoId El evento que se desea borrar del cliente.
     * @param clienteId El cliente de la cual se desea eliminar.
     */
    public void removeEvento(Long eventoId, Long clienteId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un evento del cliente con id = {0}", clienteId);
        ClienteEntity ent = getCliente(clienteId);
        if(ent == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para remover evento");
        }
        EventoEntity entC = eventoLogic.getEvento(eventoId);
        int index = ent.getEventos().indexOf(entC);
        if (index >= 0) 
        {
            ent.getEventos().remove(entC); 
            updateCliente(ent);
        } 
        else 
        {
            throw new BusinessLogicException("El cliente no tiene ese evento");
        }
    }

    /**
     * Remplazar eventos de un cliente
     *
     * @param eventos Lista de eventos que serán los del cliente.
     * @param clienteId El id del cliente que se quiere actualizar.
     * @return La lista de eventos actualizada.
     */
    public List<EventoEntity> replaceEventos(Long clienteId, List<EventoEntity> eventos) throws BusinessLogicException 
    {
        if(getCliente(clienteId) == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para reemplazar eventos");
        }
        ClienteEntity cliente = getCliente(clienteId);
        List<EventoEntity> eventoList = eventoLogic.getEventos();
        for (EventoEntity evento : eventoList) 
        {
            if (eventos.contains(evento)) 
            {
                evento.setCliente(cliente);
            } 
            else if (null != evento.getCliente() && evento.getCliente().equals(cliente)) 
            {
                eventoLogic.deleteEvento(evento.getId());
            }
        }
        cliente.setEventos(eventos);
        updateCliente(cliente);
        return eventos;
    }

    /**
     * Retorna todos los eventos asociados a un cliente
     *
     * @param clienteId El ID del cliente buscada
     * @return La lista de eventos del cliente
     */
    public List<EventoEntity> getEventos(Long clienteId) throws BusinessLogicException 
    {
        if(getCliente(clienteId) == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para enlistar eventos");
        }
        return getCliente(clienteId).getEventos();
    }

    /**
     * Retorna un evento asociado a un cliente
     *
     * @param clienteId El id del cliente a buscar.
     * @param eventoId El id del evento a buscar
     * @return El evento encontrado dentro del cliente.
     * @throws BusinessLogicException Si el evento no se encuentra en la
     * cliente
     */
    public EventoEntity getEvento(Long clienteId, Long eventoId) throws BusinessLogicException 
    {
        if(getCliente(clienteId) == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para obtener evento");
        }
        List<EventoEntity> eventos = getCliente(clienteId).getEventos();
        EventoEntity evento = eventoLogic.getEvento(eventoId);
        int index = eventos.indexOf(evento);
        if (index >= 0)
        {
            return eventos.get(index);
        }
        else
        {
            throw new BusinessLogicException("El evento no está asociado al cliente");
        }        
    }   
}
