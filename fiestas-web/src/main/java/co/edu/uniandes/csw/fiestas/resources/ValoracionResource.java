package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ServicioDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDetailDTO;
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
 *
 * @author ls.arias
 */


@Path("valoraciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ValoracionResource {
    
    
     /**
     * <h1>POST /valoraciones : Crear un valoracion.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link ValoracionDetailDTO}.
     * 
     * Crea una nueva valoracion con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó la nueva valoracion.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe la valoracion.
     * </code>
     * </pre>
     * @param valoracion {@link ValoracionDetailDTO} - La valoracion que se desea guardar.
     * @return JSON {@link ValoracionDetailDTO}  - la valoracion guardada con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la valoracion.
     */
       @POST
    public ValoracionDetailDTO createValoracion(ValoracionDetailDTO valoracion) throws BusinessLogicException {
        return valoracion;
    }
    
    /**
     * <h1>GET /valoraciones : Obtener todas los valoraciones.</h1>
     * 
     * <pre>Busca y devuelve todas las valoraciones que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas las valoraciones de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link ValoracionDetailDTO} - Los valoraciones encontradas en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ValoracionDetailDTO> getValoraciones() {
        return new ArrayList<>();
    }
    
    /**
     * <h1>GET /valoraciones/{id} : Obtener valoracion por id.</h1>
     * 
     * <pre>Busca la valoracion con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve la valoracion correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe una valoracion con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador de la valoracion que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ValoracionDetailDTO} - La valoracion buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ValoracionDetailDTO getValoracion(@PathParam("id") Long id) {
        return null;
    }
    
    /**
    * <h1>PUT /valoracion/{id} : Actualizar valoracion por id.</h1>
    * 
    * <pre>Busca la valoracion con el id asociado recibido en la URL, actualiza los paramteros
    * y la devuelve.
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK Devuelve la valoracion correspondiente al id, despues de actualizado.
    * </code> 
    * <code style="color: #c7254e; background-color: #f9f2f4;">
    * 404 Not Found No existe una valoracion con el id dado.
    * </code> 
    * </pre>
    * @param id Identificador de la valoracion que se esta buscando. Este debe ser una cadena de dígitos.
    * @return JSON {@link ValoracionDetailDTO} - La valoracion buscada y actuaizada
    */
     @PUT
    @Path("{id: \\d+}")
    public ValoracionDetailDTO updateValoracion(@PathParam("id") Long id, ValoracionDetailDTO valoracion) throws BusinessLogicException {
        return valoracion;
    }
    
    /**
    * <h1>DELETE /valoracion/{id} : Elimina una valoracion por id.</h1>
    * 
    * <pre>Busca la valoracion con el id asociado recibido en la URL y lo elimina
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK La valoracion fue eliminada exitosamente
    * </code> 
    * <code style="color: #c7254e; background-color: #f9f2f4;">
    * 404 Not Found No existe una valoracion con el id dado.
    * </code> 
    * </pre>
    * @param id Identificador de la valoracion que se esta buscando. Este debe ser una cadena de dígitos.
    */
    @DELETE
    @Path("{id: \\d+}")
     public void deleteValoracion(@PathParam("id") Long id) {
        // Void
    }
    
}
