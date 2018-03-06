/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ServicioDetailDTO;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
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

/**
  * <pre>Clase que implementa el recurso "servicios".
 * URL: /api/servicios
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta
 * "/api" y este recurso tiene la ruta "servicios".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio).
 * </pre>
 * @author ls.arias
 */

@Path("servicio")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ServicioResource {
    
    
     /**
     * <h1>POST /servicios : Crear un servicio.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link ServicioDetailDTO}.
     * 
     * Crea un nuevo servicio con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo servicio.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el servicio.
     * </code>
     * </pre>
     * @param servicio {@link ServicioDetailDTO} - El servicio que se desea guardar.
     * @return JSON {@link ServicioDetailDTO}  - El servicio guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el servicio.
     */
     @POST
    public ServicioDetailDTO createServicio(ServicioDetailDTO servicio) throws BusinessLogicException {
        return servicio;
    }
    
    /**
     * <h1>GET /servicios : Obtener todos los servicios.</h1>
     * 
     * <pre>Busca y devuelve todos los servicios que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los servicios de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link ServicioDetailDTO} - Los servicios encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ServicioDetailDTO> getServicios() {
        return new ArrayList<>();
    }
    
    /**
     * <h1>GET /servicios/{id} : Obtener servicio por id.</h1>
     * 
     * <pre>Busca el servicio con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el servicio correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un servicio con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del servicio que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ServicioDetailDTO} - El servicio buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ServicioDetailDTO getServicio(@PathParam("id") Long id) {
        return null;
    }
    
    /**
    * <h1>PUT /servicio/{id} : Actualizar servicio por id.</h1>
    * 
    * <pre>Busca el servicio con el id asociado recibido en la URL, actualiza los paramteros
    * y la devuelve.
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK Devuelve el servicio correspondiente al id, despues de actualizado.
    * </code> 
    * <code style="color: #c7254e; background-color: #f9f2f4;">
    * 404 Not Found No existe un servicio con el id dado.
    * </code> 
    * </pre>
    * @param id Identificador del servicio que se esta buscando. Este debe ser una cadena de dígitos.
     * @param servicio servicio a actualizar
    * @return JSON {@link ServicioDetailDTO} - El servicio buscado y actuaizado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica
    */
     @PUT
    @Path("{id: \\d+}")
    public ServicioDetailDTO updateServicio(@PathParam("id") Long id, ServicioDetailDTO servicio) throws BusinessLogicException {
        return servicio;
    }
    
    /**
    * <h1>DELETE /servicio/{id} : Elimina un servicio por id.</h1>
    * 
    * <pre>Busca rl servicio con el id asociado recibido en la URL y lo elimina
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK La servicio fue eliminada exitosamente
    * </code> 
    * <code style="color: #c7254e; background-color: #f9f2f4;">
    * 404 Not Found No existe una servicio con el id dado.
    * </code> 
    * </pre>
    * @param id Identificador del servicio que se esta buscando. Este debe ser una cadena de dígitos.
    */
    @DELETE
    @Path("{id: \\d+}")
     public void deleteServicio(@PathParam("id") Long id) {
        // Void
    }
}
