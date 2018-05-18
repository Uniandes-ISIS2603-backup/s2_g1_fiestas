package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ContratoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ProductoDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import java.util.ArrayList;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "eventos".
 * URL: /api/clientes/{idCliente: \\d+}/eventos
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta
 * "/api" y este recurso tiene la ruta "eventos".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio).
 * </pre>
 *
 * @author cm.amaya10
 */
@Path("clientes/{idCliente: \\d+}/eventos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EventoResource {

    @Inject
    private EventoLogic eventoLogic;

    /**
     * Convierte una lista de EventoEntity a una lista de EventoDetailDTO.
     *
     * @param entityList Lista de EventoEntity a convertir.
     * @return Lista de EventoDetailDTO convertida.
     *
     */
    private List<EventoDetailDTO> listEntity2DTO(List<EventoEntity> entityList) {
        List<EventoDetailDTO> list = new ArrayList<>();
        for (EventoEntity entity : entityList) {
            list.add(new EventoDetailDTO(entity));
        }
        return list;
    }

    /**
     * <h1>GET clientes/{idCliente: \\d+}//eventos : Obtener todos los eventos
     * de un cliente.</h1>
     *
     * <pre>Busca y devuelve todos los eventos que existen para un cliente dado en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los eventos de la aplicacion.</code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos(@PathParam("idCliente") Long idCliente) {
        return listEntity2DTO(eventoLogic.getEventos(idCliente));
    }

    /**
     * <h1>GET clientes/{idCliente: \\d+}/eventos/{id} : Obtener evento de un
     * cliente por los ids.</h1>
     *
     * <pre>Busca el evento de un cliente con los ids asociado recibido en la URL y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el evento correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un evento con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del evento que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @param idCliente Identificador del cliente, dueño del evento
     * @return JSON {@link EventoDetailDTO} - El evento buscado
     */
    @GET
    @Path("{id: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("idCliente") Long idCliente, @PathParam("id") Long id) {
        EventoEntity entity = eventoLogic.getEvento(idCliente, id);
        if (entity == null) {
            throw new WebApplicationException("El evento no existe", 404);
        }
        return new EventoDetailDTO(entity);
    }

    /**
     * <h1>POST clientes/{idCliente: \\d+}/eventos : Crear un evento por parte
     * de un cliente.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link EventoDetailDTO}.
     *
     * Crea un nuevo evento con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo evento.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precondition Failed: Ya existe el evento.
     * </code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param evento {@link EventoDetailDTO} - El evento que se desea guardar.
     * @return JSON {@link EventoDetailDTO} - El evento guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando ya existe el evento.
     */
    @POST
    public EventoDetailDTO createEvento(@PathParam("idCliente") Long idCliente, EventoDetailDTO evento) throws BusinessLogicException {
        return new EventoDetailDTO(eventoLogic.createEvento(idCliente, evento.toEntity()));
    }

    /**
     * <h1>PUT clientes/{idCliente: \\d+}/eventos/{id} : Actualizar evento de un
     * cliente por ids.</h1>
     *
     * <pre>Busca el evento de un cliente con los ids asociados recibido en la URL, actualiza os parametros
     * y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el evento correspondiente al id, despues de actualizado.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un evento con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del evento que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @param idCliente Identificador del cliente, dueño del evento
     * @param evento evento a actualizar en la base de datos
     * @return JSON {@link EventoDetailDTO} - El evento buscado y actualizado
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica.
     */
    @PUT
    @Path("{id: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("idCliente") Long idCliente, @PathParam("id") Long id, EventoDetailDTO evento) throws BusinessLogicException {
        EventoEntity entity = evento.toEntity();
        entity.setId(id);
        EventoEntity oldEntity = eventoLogic.getEvento(idCliente, id);
        if (oldEntity == null) {
            throw new WebApplicationException("El evento no existe", 404);
        }
        return new EventoDetailDTO(eventoLogic.updateEvento(idCliente, entity));
    }

    /**
     * <h1>DELETE clientes/{idCliente: \\d+}/eventos/{id} : Elimina un evento de
     * un cliente por los ids.</h1>
     *
     * <pre>Busca el evento de un cliente con los ids asociados recibidos en la URL y lo elimina
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK El evento fue eliminado exitosamente
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un evento con el id dado.
     * </code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param id Identificador del evento que se esta buscando. Este debe ser
     * una cadena de dígitos.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEvento(@PathParam("idCliente") Long idCliente, @PathParam("id") Long id) {
        EventoEntity entity = eventoLogic.getEvento(idCliente, id);
        if (entity == null) {
            throw new WebApplicationException("El evento no existe", 404);
        }
        eventoLogic.deleteEvento(idCliente, id);
    }

    /**
     * <h1>GET clientes/{idCliente: \\d+}/eventos/{eventosId}/contratos/ :
     * Obtener todos los contratos de un evento de un cliente.</h1>
     *
     * <pre>Busca y devuelve todos los contratos que existen en el evento.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los contratos del evento.</code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSONArray {@link ContratoDetailDTO} - Los contratos encontrados
     * en el evento. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{eventosId: \\d+}/contratos")
    public List<ContratoDetailDTO> listContratos(@PathParam("idCliente") Long idCliente, @PathParam("eventosId") Long eventosId) {
        return contratosListEntity2DTO(eventoLogic.getContratos(idCliente, eventosId));
    }

    /**
     * <h1>PUT clientes/{idCliente: \\d+}/eventos/{eventosId}/contratos: Edita
     * los contratos de un evento.</h1>
     * <pre> Reemplaza las instancias de Contrato asociadas a una instancia de Evento de un cliente.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los contratos del evento.
     * </code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratos JSONArray {@link ContratoDetailDTO} El arreglo de
     * contratos nuevo para la evento.
     * @return JSON {@link ContratoDetailDTO} - El arreglo de contratos guardado
     * en la evento.
     */
    @PUT
    @Path("{eventosId: \\d+}/contratos")
    public List<ContratoDetailDTO> replaceContratos(@PathParam("idCliente") Long idCliente, @PathParam("eventosId") Long eventosId, List<ContratoDetailDTO> contratos) {
        return contratosListEntity2DTO(eventoLogic.replaceContratos(idCliente, eventosId, contratosListDTO2Entity(contratos)));
    }

    /**
     * <h1>POST clientes/{idCliente:
     * \\d+}/eventos/{eventosId}/contratos/{contratosId} : Guarda un contrato
     * dentro del evento.</h1>
     *
     * <pre> Guarda un contrato dentro de un evento con la informacion que
     * recibe en la URL. Se devuelve el contrato que se guarda en la evento.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo contrato .
     * </code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratoId Identificador del contrato que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato guardado en la
     * evento.
     */
    @POST
    @Path("{eventosId: \\d+}/contratos/{contratosId: \\d+}")
    public ContratoDetailDTO addContrato(@PathParam("idCliente") Long idCliente, @PathParam("eventosId") Long eventosId, @PathParam("contratosId") Long contratoId) {
        return new ContratoDetailDTO(eventoLogic.addContrato(idCliente, contratoId, eventosId));
    }

    /**
     * <h1>DELETE clientes/{idCliente:
     * \\d+}/eventos/{eventosId}/contratos/{contratosId} : Elimina un contrato
     * dentro del evento.</h1>
     *
     * <pre> Elimina la referencia del contrato asociado al ID dentro del evento
     * con la informacion que recibe en la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del contrato.
     * </code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratosId Identificador del contrato que se desea guardar. Este
     * debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{eventosId: \\d+}/contratos/{contratosId: \\d+}")
    public void removeContratos(@PathParam("idCliente") Long idCliente, @PathParam("eventosId") Long eventosId, @PathParam("contratosId") Long contratosId) {
        eventoLogic.removeContrato(idCliente, contratosId, eventosId);
    }

    /**
     * <h1>GET clientes/{idCliente:
     * \\d+}/eventos/{eventosId}/contratos/{contratosId} : Obtener contrato por
     * id del evento por id.</h1>
     *
     * <pre>Busca el contrato con el id asociado dentro del evento con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el contrato correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un contrato con el id dado dentro del evento.
     * </code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratosId Identificador del contrato que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato buscado
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * -Error de lógica que se genera cuando no se encuentra la evento o el
     * contrato.
     */
    @GET
    @Path("{eventosId: \\d+}/contratos/{contratosId: \\d+}")
    public ContratoDetailDTO getContrato(@PathParam("idCliente") Long idCliente, @PathParam("eventosId") Long eventosId, @PathParam("contratosId") Long contratosId) throws BusinessLogicException {
        return new ContratoDetailDTO(eventoLogic.getContrato(idCliente, eventosId, contratosId));
    }

    /**
     * Convierte una lista de ContratoEntity a una lista de ContratoDetailDTO.
     *
     * @param entityList Lista de ContratoEntity a convertir.
     * @return Lista de ContratoDetailDTO convertida.
     *
     */
    private List<ContratoDetailDTO> contratosListEntity2DTO(List<ContratoEntity> entityList) {
        List<ContratoDetailDTO> list = new ArrayList<>();
        for (ContratoEntity entity : entityList) {
            list.add(new ContratoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ContratoDetailDTO a una lista de ContratoEntity.
     *
     * @param dtos Lista de ContratoDetailDTO a convertir.
     * @return Lista de ContratoEntity convertida.
     *
     */
    private List<ContratoEntity> contratosListDTO2Entity(List<ContratoDetailDTO> dtos) {
        List<ContratoEntity> list = new ArrayList<>();
        for (ContratoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }

    /**
     * Conexión con el servicio de pagos para un evento. {@link PagoResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /pagos que
     * dependen del evento, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las reseñas.
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param eventosId El ID del evento con respecto al cual se accede al
     * servicio.
     * @return El servicio de Pagos para ese pagoo en paricular.
     */
    @Path("{idEvento: \\d+}/pagos")
    public Class<PagoResource> getPagoResource(@PathParam("idCliente") Long idCliente, @PathParam("idEvento") Long eventosId) {
        EventoEntity entity = eventoLogic.getEvento(idCliente, eventosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /eventos/" + eventosId + "/pagos no existe.", 404);
        }
        return PagoResource.class;
    }

    /**
     * <h1>PUT clientes/{idCliente: \\d+}/eventos/{eventosId}/productos: Crea nuevos contratos
     * en un evento.</h1>
     * <pre> Crea nuevos contratos asociadas a una instancia de Evento de un cliente.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los contratos del evento.
     * </code>
     * </pre>
     *
     * @param idCliente Identificador del cliente, dueño del evento
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param productos JSONArray {@link ProductoDetailDTO} El arreglo de
     * productos nuevos para la evento.
     * @return JSON {@link ContratoDetailDTO} - El arreglo de contratos guardado
     * en la evento.
     * @throws BusinessLogicException{@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * -Error de lógica que se genera cuando no se encuentra la evento o el
     * contrato.
     */
    @PUT
    @Path("{eventosId: \\d+}/productos")
    public List<ContratoDetailDTO> createNewContratos(@PathParam("idCliente") Long idCliente, @PathParam("eventosId") Long eventosId, List<ProductoDetailDTO> productos)throws BusinessLogicException  {
        List<ProductoEntity> list = new ArrayList<>();
        for (ProductoDetailDTO dto : productos) {
            list.add(dto.toEntity());
        }
        return contratosListEntity2DTO(eventoLogic.createNewContratos(idCliente, eventosId, list));
    }
}
