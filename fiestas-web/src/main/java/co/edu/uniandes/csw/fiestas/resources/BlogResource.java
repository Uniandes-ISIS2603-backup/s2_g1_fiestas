/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
    
    /**
 * <pre>Clase que implementa el recurso "Blogs".
 * URL: /api/Blogs
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "Blogs".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author mc.gonzalez15  
 */
@Path("Blogs")
@Produces("application/json")

public class BlogResource {

    /**
     * <h1>GET /Blogs : Obtener todos los Blogs.</h1>
     * 
     * <pre>Busca y devuelve todos los Blogs que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los Blogs de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link BlogDetailDTO} - Los Blogs encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<BlogDetailDTO> getBlogs() {
        return new ArrayList<>();
    }

    /**
     * <h1>GET /Blogs/{id} : Obtener Blog por id.</h1>
     * 
     * <pre>Busca el Blog con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el Blog correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un Blog con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del Blog que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link BlogDetailDTO} - El Blog buscado
     */
    @GET
    @Path("{id: \\d+}")
    public BlogDetailDTO getBlog(@PathParam("id") Long id) {
        return null;
    }
    
        /**
     * <h1>POST /Blogs : Crear un Blog.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link CityDetailDTO}.
     * 
     * Crea un nuevo Blog con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo Blog.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el Blog.
     * </code>
     * </pre>
     * @param Blog {@link BlogDetailDT} - La ciudad que se desea guardar.
     * @return JSON {@link BlogDetailDTO}  - El Blog guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la ciudad.
     */
    @POST
    public BlogDetailDTO createBlog(BlogDetailDTO Blog) throws BusinessLogicException {
        return Blog;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public BlogDetailDTO updateBlog(@PathParam("id")Long id) {
        return null;
    }
    
    @DELETE
    @Path("{id:\\d+}")
    public void deleteBlog(@PathParam("id")Long id){
        
    }
    
}
