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
 * <pre>Clase que implementa el recurso "blogs".
 * URL: /api/blogs
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "blogs".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author mc.gonzalez15  
 */
@Path("blogs")
@Produces("application/json")

public class BlogResource {

    /**
     * <h1>GET /Blogs : Obtener todos los blogs.</h1>
     * 
     * <pre>Busca y devuelve todos los Blogs que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los blogs de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link BlogDetailDTO} - Los blogs encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<BlogDetailDTO> getBlogs() {
        return new ArrayList<>();
    }

    /**
     * <h1>GET /blogs/{id} : Obtener blog por id.</h1>
     * 
     * <pre>Busca el blog con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el blog correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un blog con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del blog que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link BlogDetailDTO} - El blog buscado
     */
    @GET
    @Path("{id: \\d+}")
    public BlogDetailDTO getBlog(@PathParam("id") Long id) {
        return null;
    }
    
        /**
     * <h1>POST /blogs : Crear un blog.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link BlogDetailDTO}.
     * 
     * Crea un nuevo blog con la informacion que se recibe en el cuerpo 
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
     * @param blog {@link BlogDetailDTO} - El blog que se desea guardar.
     * @return JSON {@link BlogDetailDTO}  - El blog guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el blog.
     */
    @POST
    public BlogDetailDTO createBlog(BlogDetailDTO blog) throws BusinessLogicException {
        return blog;
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
