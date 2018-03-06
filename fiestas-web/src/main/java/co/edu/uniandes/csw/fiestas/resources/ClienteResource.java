package co.edu.uniandes.csw.fiestas.resources;


import co.edu.uniandes.csw.fiestas.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ClienteLogic;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "clientes".
 * URL: /api/clientes
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "clientes".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author nm.hernandez10 
 */
@Path("clientes")
@Produces("application/json")

public class ClienteResource 
{

    @Inject
    private ClienteLogic clienteLogic;

    /**
     * Convierte una lista de ClienteEntity a una lista de ClienteDetailDTO.
     *
     * @param entityList Lista de ClienteEntity a convertir.
     * @return Lista de ClienteDetailDTO convertida.
     *
     */
    private List<ClienteDetailDTO> listEntity2DTO(List<ClienteEntity> entityList)
    {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }

    /**
     * <h1>GET /clientes : Obtener todos los clientes.</h1>
     *
     * <pre>Busca y devuelve todos los clientes que existen en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los clientes de la aplicacion.</code>
     * </pre>
     *
     * @return JSONArray {@link ClienteDetailDTO} - Los clientes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClienteDetailDTO> getClientes() {
        return listEntity2DTO(clienteLogic.getClientes());
    }

    /**
     * <h1>GET /clientes/{id} : Obtener cliente por id.</h1>
     *
     * <pre>Busca el cliente con el id asociado recibido en la URL y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el cliente correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un cliente con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del cliente que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("id") Long id) {
        ClienteEntity entity = clienteLogic.getCliente(id);
        if (entity == null) {
            throw new WebApplicationException("El cliente no existe", 404);
        }
        return new ClienteDetailDTO(entity);
    }

    /**
     * <h1>POST /clientes : Crear un cliente.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link ClienteDetailDTO}.
     *
     * Crea un nuevo cliente con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo cliente.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precondition Failed: Ya existe el cliente.
     * </code>
     * </pre>
     *
     * @param cliente {@link ClienteDetailDTO} - La ciudad que se desea guardar.
     * @return JSON {@link ClienteDetailDTO} - El cliente guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando ya existe el cliente.
     */
    @POST
    public ClienteDetailDTO createCliente(ClienteDetailDTO cliente) throws BusinessLogicException {
        return new ClienteDetailDTO(clienteLogic.createCliente(cliente.toEntity()));
    }

    /**
     * <h1>PUT /clientes/{id} : Actualizar cliente por id.</h1>
     *
     * <pre>Busca el cliente con el id asociado recibido en la URL, actualiza os paramteros
     * y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el cliente correspondiente al id, despues de actualizado.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un cliente con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del cliente que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @param cliente cliente a actualizar en la base de datos
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado y actuaizado
     */
    @PUT
    @Path("{id: \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("id") Long id, ClienteDetailDTO cliente) {
        ClienteEntity entity = cliente.toEntity();
        entity.setId(id);
        ClienteEntity oldEntity = clienteLogic.getCliente(id);
        if (oldEntity == null) {
            throw new WebApplicationException("El cliente no existe", 404);
        }
        return new ClienteDetailDTO(clienteLogic.updateCliente(entity));
    }

    /**
     * <h1>DELETE /clientes/{id} : Elimina un cliente por id.</h1>
     *
     * <pre>Busca el cliente con el id asociado recibido en la URL y lo elimina
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK El cliente fue eliminado exitosamente
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un cliente con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del cliente que se esta buscando. Este debe ser
     * una cadena de dígitos.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteCliente(@PathParam("id") Long id) {
        ClienteEntity entity = clienteLogic.getCliente(id);
        if (entity == null) {
            throw new WebApplicationException("El cliente no existe", 404);
        }
        clienteLogic.deleteCliente(id);
    }

    /**
     * <h1>GET /{clientesId}/eventos/ : Obtener todos los eventos de un cliente.</h1>
     *
     * <pre>Busca y devuelve todos los eventos que existen en el cliente.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los eventos del cliente.</code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados
     * en el cliente. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{clientesId: \\d+}/eventos")
    public List<EventoDetailDTO> listEventos(@PathParam("clientesId") Long clientesId) {
        return eventosListEntity2DTO(clienteLogic.getEventos(clientesId));
    }

    /**
     * <h1>PUT /{clientesId}/eventos: Edita loseventos de un cliente..</h1>
     * <pre> Remplaza las instancias de Evento asociadas a una instancia de Editorial
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los eventos del cliente.
     * </code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param eventos JSONArray {@link EventoDetailDTO} El arreglo de
     * eventos nuevo para la cliente.
     * @return JSON {@link EventoDetailDTO} - El arreglo de eventos guardado
     * en la cliente.
     */
    @PUT
    @Path("{clientesId: \\d+}/eventos")
    public List<EventoDetailDTO> replaceEventos(@PathParam("clientesId") Long clientesId, List<EventoDetailDTO> eventos) {
        return eventosListEntity2DTO(clienteLogic.replaceEventos(clientesId, eventosListDTO2Entity(eventos)));
    }
    
        /**
     * <h1>POST /{clientesId}/eventos/{eventosId} : Guarda un
     * evento dentro del cliente.</h1>
     *
     * <pre> Guarda un evento dentro de un cliente con la informacion que
     * recibe el la URL. Se devuelve el evento que se guarda en la cliente.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo evento .
     * </code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param eventoId Identificador del evento que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link EventoDetailDTO} - El evento guardado en la
     * cliente.
     */
    @POST
    @Path("{clientesId: \\d+}/eventos/{eventosId: \\d+}")
    public EventoDetailDTO addEvento(@PathParam("clientesId") Long clientesId, @PathParam("eventosId") Long eventoId) {
        return new EventoDetailDTO(clienteLogic.addEvento(eventoId, clientesId));
    }

        /**
     * <h1>DELETE /{clientesId}/eventos/{eventosId} : Elimina un
     * evento dentro del cliente.</h1>
     *
     * <pre> Elimina la referencia del evento asociado al ID dentro del cliente
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del evento.
     * </code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param eventosId Identificador del evento que se desea guardar. Este
     * debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{clientesId: \\d+}/eventos/{eventosId: \\d+}")
    public void removeEventos(@PathParam("clientesId") Long clientesId, @PathParam("eventosId") Long eventosId) {
        clienteLogic.removeEvento(eventosId, clientesId);
    }
    
        /**
     * <h1>GET /{clientesId}/eventos/{eventosId} : Obtener
     * evento por id del cliente por id.</h1>
     *
     * <pre>Busca el evento con el id asociado dentro del cliente con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el evento correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un evento con el id dado dentro del cliente.
     * </code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param eventosId Identificador del evento que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link EventoDetailDTO} - El evento buscado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica 
     * que se genera cuando no se encuentra la cliente o el evento.
     */
    @GET
    @Path("{clientesId: \\d+}/eventos/{eventosId: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("clientesId") Long clientesId, @PathParam("eventosId") Long eventosId) throws BusinessLogicException {
        return new EventoDetailDTO(clienteLogic.getEvento(clientesId, eventosId));
    }
    
    /**
     * Convierte una lista de EventoEntity a una lista de EventoDetailDTO.
     *
     * @param entityList Lista de EventoEntity a convertir.
     * @return Lista de EventoDetailDTO convertida.
     *
     */
    private List<EventoDetailDTO> eventosListEntity2DTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de EventoDetailDTO a una lista de EventoEntity.
     *
     * @param dtos Lista de EventoDetailDTO a convertir.
     * @return Lista de EventoEntity convertida.
     *
     */
    private List<EventoEntity> eventosListDTO2Entity(List<EventoDetailDTO> dtos) {
        List<EventoEntity> list = new ArrayList<>();
        for (EventoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
