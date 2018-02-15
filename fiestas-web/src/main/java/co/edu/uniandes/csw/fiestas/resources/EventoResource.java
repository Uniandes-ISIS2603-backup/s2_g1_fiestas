/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.EventoDetailDTO;
import java.util.ArrayList;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/*
 *
 * @author cm.amaya10
 */

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
@Produces("application/jscoped
public class EventoResource {
    
    /**
     * 
     * @return 
     */
    @GET
    public List<EventoDetailDTO> getEventos(){
        return new ArrayList<EventoDetailDTO>();
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
        return null;
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
        return null;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("id") Long id){
        return null;
    }
    
    
   @DELETE
    @Path("{id: \\d+}")
    public void deleteEvento(@PathParam("id") Long id){
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
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la evento.
     */
    @POST
    public EventoDetailDTO createEvento(EventoDetailDTO evento) throws BusinessLogicException {
        return evento;
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
     * @return JSON {@link EventoDetailDTO} - El evento buscado y actuaizado
     */
    @PUT
    @Path("{id: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("id") Long id) {
        return null;
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
    }

}
