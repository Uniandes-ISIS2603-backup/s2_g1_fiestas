/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.EventoDTO;
import co.edu.uniandes.csw.fiestas.ejb.BlogLogic;
import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
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
import javax.ws.rs.WebApplicationException;
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
@Path("/blogs")
@Produces("application/json")

public class BlogResource {
    private BlogLogic logic;


    /**
     * <h1>GET /blog/ : Obtener blog por id.</h1>
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
    public BlogDetailDTO getBlog(@PathParam("id") Long id, @PathParam("id") Long eventoId) {
        BlogEntity bE=logic.getBlog(id);
        if(bE==null)
            throw new WebApplicationException("El blog no existe", 404);
        return new BlogDetailDTO(bE);
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
        return new BlogDetailDTO(logic.createBlog(blog.toEntity()));
    }
    
    /**
     * <h1>PUT /api/blogs/{id} : Actualizar autor con el id dado.</h1>
     * <pre>Cuerpo de petición: JSON {@link AuthorDetailDTO}.
     *
     * Actualiza el autor con el id recibido en la URL con la información que se recibe en el cuerpo de la petición.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Actualiza el autor con el id dado con la información enviada como parámetro. Retorna un objeto identico.</code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un autor con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del autor que se desea actualizar. Este debe ser una cadena de dígitos.
     * @param author {@link AuthorDetailDTO} El autor que se desea guardar.
     * @return JSON {@link AuthorDetailDTO} - El autor guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra el libro a actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public BlogDetailDTO updateBlog(@PathParam("id") Long id, BlogDetailDTO blog) {
        BlogEntity entity = blog.toEntity();
        entity.setId(id);
        BlogEntity oldEntity = logic.getBlog(id);
        if (oldEntity == null) {
            throw new WebApplicationException("El blog no existe", 404);
        }
        entity.setEvento(oldEntity.getEvento());
        return new BlogDetailDTO(logic.updateBlog(entity));
    }
    /**
     * 
     * @param id 
     */
    @DELETE
    @Path("{id:\\d+}")
    public void deleteBlog(@PathParam("id")Long id){
        BlogEntity entity = logic.getBlog(id);
        if (entity == null) {
            throw new WebApplicationException("El blog no existe", 404);
        }
       logic.deleteBlog(id);
    }
    
    /**
     * Convierte una lista de BlogEntity a una lista de BlogDetailDTO.
     *
     * @param entityList Lista de BlogEntity a convertir.
     * @return Lista de BlogDetailDTO convertida.
     * 
     */
    private List<BlogDetailDTO> listEntity2DTO(List<BlogEntity> entityList) {
        List<BlogDetailDTO> list = new ArrayList<>();
        for (BlogEntity entity : entityList) {
            list.add(new BlogDetailDTO(entity));
        }
        return list;
    }
    
   /**
    * 
    * @param eventoId
    * @param id 
    */
    public void addEvento(@PathParam("id")Long eventoId, @PathParam("id")Long id) {
        EventoEntity eE=logic.getEventoExistente(eventoId);
        try{
            logic.addEvento(eE, id);
        }
        catch(BusinessLogicException e){
            throw  new WebApplicationException(e.getMessage(),404);
        }
    }
    
    
     public EventoDTO getEvento(Long idBlog) {
        EventoEntity eE=logic.getEvento(idBlog);
        return new EventoDTO(eE);
    }
}
