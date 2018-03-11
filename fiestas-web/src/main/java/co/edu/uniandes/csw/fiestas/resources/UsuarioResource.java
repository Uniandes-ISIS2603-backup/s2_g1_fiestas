package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
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

/**
 * <pre>Clase que implementa el recurso "usuarios".
 * URL: /api/usuarios
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "usuarios.</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author nm.hernandez10
 */

@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {
    @Inject
    private UsuarioLogic logic;

    /**
     * <h1>GET /usuarios : Obtener todos los usuarios.</h1>
     * 
     * <pre>Busca y devuelve todos los usuarios que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los usuarios de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link UsuarioDetailDTO} - Los usuarios encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<UsuarioDetailDTO> getUsuarios() {
        return listEntity2DTO(logic.getUsuarios());
    }

    
     /**
     * Convierte una lista de HoraroiEntity a una lista de UsuarioDetailDTO.
     *
     * @param entityList Lista de UsuarioEntity a convertir.
     * @return Lista de UsuarioDetailDTO convertida.
     *
     */
    private List<UsuarioDetailDTO> listEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }
    /**
     * <h1>GET /usuarios/{id} : Obtener usuario por id.</h1>
     * 
     * <pre>Busca el usuario con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el usuario correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un usuario con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del usuario que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link UsuarioDetailDTO} - El usuario buscado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica si no existe el usuario
     */
    @GET
    @Path("{id: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("id") Long id) throws BusinessLogicException{
        UsuarioEntity e = logic.getUsuario(id);
        if(e == null)
        {
            throw new BusinessLogicException("El usuario con el id buscado no existe.");
        }
        return new UsuarioDetailDTO(e);
    }
    
        /**
     * <h1>POST /usuarios : Crear un usuario.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link UsuarioDetailDTO}.
     * 
     * Crea un nuevo usuario con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo usuario.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el usuario.
     * </code>
     * </pre>
     * @param usuario {@link UsuarioDetailDTO} - La ciudad que se desea guardar.
     * @return JSON {@link UsuarioDetailDTO}  - El usuario guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el usuario.
     */
    @POST
    public UsuarioDetailDTO createUsuario(UsuarioDetailDTO usuario) throws BusinessLogicException {
        if(logic.getUsuario(usuario.getId())!=null)
            throw new BusinessLogicException("El usuario ya existe.");
        logic.createUsuario(usuario.toEntity());
        return usuario;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public UsuarioDetailDTO updateUsuario(@PathParam("id")Long id) throws BusinessLogicException {
        UsuarioEntity ent =logic.getUsuario(id);
        if(ent == null)
            throw new BusinessLogicException("El usuario no existe.");
        logic.updateUsuario(ent);
        return new UsuarioDetailDTO(ent);
    }
    
    @DELETE
    @Path("{id:\\d+}")
    public void deleteUsuario(@PathParam("id")Long id) throws BusinessLogicException{
        UsuarioEntity ent =logic.getUsuario(id);
        if(ent == null)
             throw new BusinessLogicException("El usuario no existe.");
        logic.deleteUsuario(id);
    }
    
    @GET
    @Path("{id:\\d+}/blogs")
    public List<BlogDetailDTO> getBlogsUsuario(@PathParam("id")Long id) throws BusinessLogicException{
        UsuarioEntity ent = logic.getUsuario(id);
        if(ent == null)
            throw new BusinessLogicException("El usuario no existe.");
        return blogListEntity2DTO(logic.getBlogs(id));
    }
    
    @GET
    @Path("{id:\\d+}/blogs/{blogId:\\d+}")
    public BlogDetailDTO getUsuarioBlog(@PathParam("id")Long id, @PathParam("blogId")Long blogId) throws BusinessLogicException
    {
        UsuarioEntity e = logic.getUsuario(id);
        if(e==null)
            throw new BusinessLogicException("El usuario no existe.");
        BlogEntity be= logic.getBlog(e,blogId);
        return new BlogDetailDTO(be);
    }
    
    /**
     * Actualizar blogs en un usuario dado por id.
     * 
     * @param id del usuario donde se reemplazan los blogs
     * @param blogs lista de blogs a actualizar
     * @return lista de blogs actualizado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica
     */
    @PUT
    @Path("{id: \\d+}/blogs")
    public  List<BlogDetailDTO> replaceBlogs(@PathParam("id")Long id,List<BlogDetailDTO> blogs) throws BusinessLogicException
    {
        return blogListEntity2DTO(logic.replaceBlogs(id,blogListDTO2Entity(blogs)));
    }
    
    /**
     * Convierte una lista de BlogEntity a una lista de BlogDetailDTO.
     *
     * @param entityList Lista de BlogEntity a convertir.
     * @return Lista de BlogDetailDTO convertida.
     *
     */
    private List<BlogDetailDTO> blogListEntity2DTO(List<BlogEntity> entityList) {
        List<BlogDetailDTO> list = new ArrayList<>();
        for (BlogEntity entity : entityList) {
            list.add(new BlogDetailDTO(entity));
        }
        return list;
    }
    
     /**
     * Convierte una lista de BlogDetailDTO a una lista de BlogEntity.
     *
     * @param dtos Lista de BlogDetailDTO a convertir.
     * @return Lista de BlogEntity convertida.
     *
     */
    private List<BlogEntity> blogListDTO2Entity(List<BlogDetailDTO> dtos) {
        List<BlogEntity> list = new ArrayList<>();
        for (BlogDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    
    /**
     * <h1>POST /{usuariosId}/blogs/{blogsId} : Guarda un
     * blog dentro del usuario.</h1>
     *
     * <pre> Guarda un blog dentro de un usuario con la informacion que
     * recibe el la URL. Se devuelve el blog que se guarda en el usuario.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo blog .
     * </code>
     * </pre>
     *
     * @param usuariosId Identificador del usuario que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param blogId Identificador del blog que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link BlogDetailDTO} - El blog guardado en la
     * usuario.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica
     */
    @POST
    @Path("{usuariosId: \\d+}/blogs/{blogsId: \\d+}")
    public BlogDetailDTO addBlog(@PathParam("usuariosId") Long usuariosId, @PathParam("blogsId") Long blogId) throws BusinessLogicException {
        return new BlogDetailDTO(logic.addBlog(blogId, usuariosId));
    }
    
    /**
     * <h1>DELETE /{usuarioId}/blogs/{blogId} : Elimina un
     * blog dentro del usuario.</h1>
     *
     * <pre> Elimina la referencia del blog asociado al ID dentro del usuario
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del blog.
     * </code>
     * </pre>
     *
     * @param usuarioId Identificador del usuario que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param blogsId Identificador del blog que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica
     */
    @DELETE
    @Path("{usuarioId: \\d+}/blogs/{blogsId: \\d+}")
    public void removeBlogs(@PathParam("usuarioId") Long usuarioId, @PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        logic.removeBlog(blogsId, usuarioId);
    }
}
