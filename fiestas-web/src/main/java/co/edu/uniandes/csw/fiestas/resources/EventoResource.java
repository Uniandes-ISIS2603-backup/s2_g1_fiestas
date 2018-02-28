/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
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
 * URL: /api/eventos
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
@Path("eventos")
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
     * <h1>GET /eventos : Obtener todos los eventos.</h1>
     *
     * <pre>Busca y devuelve todos los eventos que existen en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los eventos de la aplicacion.</code>
     * </pre>
     *
     * @return JSONArray {@link EventoDetailDTO} - Los eventos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EventoDetailDTO> getEventos() {
        return listEntity2DTO(eventoLogic.getEventos());
    }

    /**
     * <h1>GET /eventos/{id} : Obtener evento por id.</h1>
     *
     * <pre>Busca el evento con el id asociado recibido en la URL y lo devuelve.
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
     * @return JSON {@link EventoDetailDTO} - El evento buscado
     */
    @GET
    @Path("{id: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("id") Long id) {
        EventoEntity entity = eventoLogic.getEvento(id);
        if (entity == null) {
            throw new WebApplicationException("El evento no existe", 404);
        }
        return new EventoDetailDTO(entity);
    }

    /**
     * <h1>POST /eventos : Crear un evento.</h1>
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
     * @param evento {@link EventoDetailDTO} - La ciudad que se desea guardar.
     * @return JSON {@link EventoDetailDTO} - El evento guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando ya existe el evento.
     */
    @POST
    public EventoDetailDTO createEvento(EventoDetailDTO evento) throws BusinessLogicException {
        return new EventoDetailDTO(eventoLogic.createEvento(evento.toEntity()));
    }

    /**
     * <h1>PUT /eventos/{id} : Actualizar evento por id.</h1>
     *
     * <pre>Busca el evento con el id asociado recibido en la URL, actualiza os paramteros
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
     * @param evento evento a actualizar en la base de datos
     * @return JSON {@link EventoDetailDTO} - El evento buscado y actuaizado
     */
    @PUT
    @Path("{id: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("id") Long id, EventoDetailDTO evento) {
        EventoEntity entity = evento.toEntity();
        entity.setId(id);
        EventoEntity oldEntity = eventoLogic.getEvento(id);
        if (oldEntity == null) {
            throw new WebApplicationException("El evento no existe", 404);
        }
        return new EventoDetailDTO(eventoLogic.updateEvento(entity));
    }

    /**
     * <h1>DELETE /eventos/{id} : Elimina un evento por id.</h1>
     *
     * <pre>Busca el evento con el id asociado recibido en la URL y lo elimina
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
     * @param id Identificador del evento que se esta buscando. Este debe ser
     * una cadena de dígitos.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteEvento(@PathParam("id") Long id) {
        EventoEntity entity = eventoLogic.getEvento(id);
        if (entity == null) {
            throw new WebApplicationException("El evento no existe", 404);
        }
        eventoLogic.deleteEvento(id);
    }

    /**
     * Conexion con el servicio contratos para un evento
     *
     * @param eventosId El Id del evento buscado
     * @return El servicio de Contratos para ese evento en particulas.
     */
    @Path("{eventosId: \\d+}/contratos")
    public Class<EventoContratosResource> getContratosEventoResource(@PathParam("eventosId") Long eventosId) {
        EventoEntity entity = eventoLogic.getEvento(eventosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /editorials/" + eventosId + " no existe.", 404);
        }
        return EventoContratosResource.class;
    }

}
