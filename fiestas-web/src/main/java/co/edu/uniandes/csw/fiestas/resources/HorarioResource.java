/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.HorarioDetailDTO;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**<pre>Clase que implementa el recurso "horarios".
 * URL: /api/horarios
 * </pre>
 * <h2>Anotaciones</h2>
 * 
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio).
 * </pre>
 *
 * @author df.nino10
 */

@Path("horarios")
@Produces ("application/json")
@Consumes ("application/json")
@RequestScoped

public class HorarioResource {
    
    public HorarioResource(){
    }
    
    /**
     * <h1>GET /api/horarios/{id} : Obtener horario por id.</h1>
     * <pre> Busca el horario con el id asociado recibido en la URL y la devielve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el horario correspondiente al id.
     * </code>
     * </pre>
     * @param id Identificador del horario que se esta buscando.Este debe ser una cadena de dígitos.
     * @return JSON {@link HorarioDetailDTO} - El horario buscado
     */
    @GET
    public HorarioDetailDTO getHorario(@PathParam("id") Long id){
      return null;
    }
    
     /**
     * <h1>GET /api/horarios : Obtener todos los horarios.</h1>
     * <pre> Busca y devielve todos los horarios que existen en la aplicación.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el horario correspondiente al id.
     * </code>
     * </pre>
     * @return JSONArray {@link HorarioDetailDTO} - Los horarios encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{id: \\d+}")
    public List<HorarioDetailDTO> getHorario(){
      return null;
    }
    
    /**
     * <h1>POST /api/horarios : Crear un horario.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link HorarioDetailDTO}.
     * 
     * Crea un nuevo horario con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo horario .
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el horario.
     * </code>
     * </pre>
     * @param horario {@link HorarioDetailDTO} - El horario que se desea guardar.
     * @return JSON {@link HorarioDetailDTO}  - El horario guardada con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el horario.
     */
    @POST
    public HorarioDetailDTO createHorario(HorarioDetailDTO horario) throws BusinessLogicException{
        return horario;
    }
    
     /**
     * <h1>PUT /api/horarios/{id} : Actualizar horario con el id dado.</h1>
     * <pre>Cuerpo de petición: JSON {@link HorarioDetailDTO}.
     * 
     * Actualiza el horario con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Actualiza el horario con el id dado con la información enviada como parámetro. Retorna un objeto identico.</code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un horario con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del horario que se desea actualizar.Este debe ser una cadena de dígitos.
     * JSON {@link HorarioDetailDTO} - El horario guardado
     */
    @PUT
    @Path("{id: \\d+}")
    public HorarioDetailDTO updateHorario(@PathParam("id") Long id){
        return null;
    }
    
       /**
     * <h1>DELETE /api/horario/{id} : Borrar horario por id.</h1>
     * 
     * <pre>Borra el horario con el id asociado recibido en la URL.
     * 
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina el horario correspondiente al id dado.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un horario con el id dado.
     * </code>
     * </pre>
     * @param id Identificador del horario que se desea borrar. Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteHorario(@PathParam("id") Long id){
    }
}
