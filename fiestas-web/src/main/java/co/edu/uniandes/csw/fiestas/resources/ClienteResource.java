package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.BlogDTO;
import co.edu.uniandes.csw.fiestas.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ClienteLogic;
import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "clientes".
 * URL: /api/clientes
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta
 * "/api" y este recurso tiene la ruta "clientes".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio).
 * </pre>
 *
 * @author nm.hernandez10
 */
@Path("clientes")
@Produces("application/json")

public class ClienteResource {

    @Inject
    private ClienteLogic clienteLogic;

    /**
     * Convierte una lista de ClienteEntity a una lista de ClienteDetailDTO.
     *
     * @param entityList Lista de ClienteEntity a convertir.
     * @return Lista de ClienteDetailDTO convertida.
     *
     */
    private List<ClienteDetailDTO> listEntity2DTO(List<ClienteEntity> entityList) {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }

    /**
     * <h1>GET /clientes : Obtener todos los clientes.</h1>
     *
     * <pre>Busca y devuelve todos los clientes que existen en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los clientes de la aplicacion.</code>
     * </pre>
     *
     * @return JSONArray {@link ClienteDetailDTO} - Los clientes encontrados en
     * la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClienteDetailDTO> getClientes() {
        return listEntity2DTO(clienteLogic.getClientes());
    }

    /**
     * <h1>GET /clientes/{id} : Obtener cliente por id.</h1>
     *
     * <pre>Busca el cliente con el id asociado recibido en la URL y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el cliente correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un cliente con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del cliente que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("id") Long id) {
        ClienteEntity entity = clienteLogic.getCliente(id);
        if (entity == null) {
            throw new WebApplicationException("El cliente no existe", 404);
        }
        return new ClienteDetailDTO(entity);
    }

    /**
     * <h1>POST /clientes : Crear un cliente.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link ClienteDetailDTO}.
     *
     * Crea un nuevo cliente con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo cliente.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precondition Failed: Ya existe el cliente.
     * </code>
     * </pre>
     *
     * @param cliente {@link ClienteDetailDTO} - La ciudad que se desea guardar.
     * @return JSON {@link ClienteDetailDTO} - El cliente guardado con el
     * atributo id autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando ya existe el cliente.
     */
    @POST
    public ClienteDetailDTO createCliente(ClienteDetailDTO cliente) throws BusinessLogicException {
        try {
            return new ClienteDetailDTO(clienteLogic.createCliente(cliente.toEntity()));
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 412);
        }

    }

    /**
     * <h1>PUT /clientes/{id} : Actualizar cliente por id.</h1>
     *
     * <pre>Busca el cliente con el id asociado recibido en la URL, actualiza os paramteros
     * y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el cliente correspondiente al id, despues de actualizado.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un cliente con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del cliente que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @param cliente cliente a actualizar en la base de datos
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado y actuaizado
     */
    @PUT
    @Path("{id: \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("id") Long id, ClienteDetailDTO cliente) throws BusinessLogicException {
        ClienteEntity entity = cliente.toEntity();
        entity.setId(id);
        if (getCliente(id) == null) {
            throw new WebApplicationException("No existe el cliente a actualizar.", 404);
        }

        return new ClienteDetailDTO(clienteLogic.updateCliente(entity));

    }

    /**
     * <h1>DELETE /clientes/{id} : Elimina un cliente por id.</h1>
     *
     * <pre>Busca el cliente con el id asociado recibido en la URL y lo elimina
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK El cliente fue eliminado exitosamente
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un cliente con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del cliente que se esta buscando. Este debe ser
     * una cadena de dígitos.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteCliente(@PathParam("id") Long id) throws BusinessLogicException {

        clienteLogic.deleteCliente(id);

    }

    /**
     * Convierte una lista de BlogEntity a una lista de BlogDTO.
     *
     * @param entityList Lista de BlogEntity a convertir.
     * @return Lista de BlogDTO convertida.
     *
     */
    private List<BlogDTO> blogsListEntity2DTO(List<BlogEntity> blogList) {
        List<BlogDTO> list = new ArrayList<>();
        for (BlogEntity entity : blogList) {
            list.add(new BlogDTO(entity));
        }
        return list;
    }

    /**
     * <h1>GET /{clientesId}/blogs/ : Obtener todos los blogs de un
     * cliente.</h1>
     *
     * <pre>Busca y devuelve todos los blogs que blogs en el cliente.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los blogs del cliente.</code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link BlogDTO} - Los blogs encontrados en el cliente.
     * Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{clientesId: \\d+}/blogs")
    public List<BlogDTO> listBlogs(@PathParam("clientesId") Long clientesId) throws BusinessLogicException {
        ClienteEntity cliente = clienteLogic.getCliente(clientesId);
        if (cliente == null) {
            throw new WebApplicationException("El cliente especificado no existe.", 404);
        }

        return blogsListEntity2DTO(clienteLogic.getBlogs(clientesId));
    }

    /**
     * <h1>GET /{clientesId}/blogs/{blogId} : Obtener blog por id del cliente
     * por id.</h1>
     *
     * <pre>Busca el blog con el id asociado dentro del cliente con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el blog correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un blog con el id dado dentro del cliente.
     * </code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param blogId Identificador del blog que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @return JSON {@link BlogDTO} - El blog buscado
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando no se encuentra la cliente o el
     * blog.
     */
    @GET
    @Path("{clientesId: \\d+}/blogs/{blogId: \\d+}")
    public BlogDTO getBlogC(@PathParam("clientesId") Long clientesId, @PathParam("eventosId") Long blogId) {
        ClienteEntity cliente = clienteLogic.getCliente(clientesId);
        if (cliente == null) {
            throw new WebApplicationException("El cliente no existe", 404);
        }

        try {
            return new BlogDTO(clienteLogic.getBlogC(blogId, clientesId));
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }

    /**
     * <h1>GET /blogs/{blogId} : Obtener blog por id.</h1>
     *
     * <pre>Busca el blog con el id .
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el blog correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un blog con el id dado.
     * </code>
     * </pre>
     *
     * @param blogId Identificador del blog que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @return JSON {@link BlogDTO} - El blog buscado
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando no se encuentra el blog.
     */
    @GET
    @Path("blogs/{blogId: \\d+}")
    public BlogDTO getBlog(@PathParam("eventosId") Long blogId) {
        try {
            return new BlogDTO(clienteLogic.getBlog(blogId));
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }

    /**
     * <h1>POST /{clientesId}/blogs/{blogsId} : Guarda un blog dentro del
     * cliente.</h1>
     *
     * <pre> Guarda un blog dentro de un cliente con la informacion que
     * recibe el la URL. Se devuelve el blog que se guarda en la cliente.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo blog .
     * </code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param blogDTO El blog que se desea guardar. Este debe ser la
     * representación de un blog en JSON.
     * @return JSON {@link BlogDetailDTO} - El blog guardado en la cliente.
     */
    @POST
    @Path("{clientesId: \\d+}")
    public BlogDTO addBlog(@PathParam("clientesId") Long clientesId, BlogDTO blog) {
        ClienteEntity cliente = clienteLogic.getCliente(clientesId);
        if (cliente == null) {
            throw new WebApplicationException("El cliente no existe.", 404);
        }
        try {
            BlogEntity blogE = blog.toEntity();
            if (blogE == null) {
                throw new WebApplicationException("El blog no se creó correctamente.", 404);
            }
            return new BlogDTO(clienteLogic.addBlog(blogE, clientesId));
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }

    /**
     * <h1>PUT /{clientesId}/blogs: Edita los blogs de un cliente..</h1>
     * <pre> Remplaza las instancias de Evento asociadas a una instancia de Cliente
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los blogs del cliente.
     * </code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param blogs JSONArray {@link BlogDetailDTO} El arreglo de blogs nuevo
     * para la cliente.
     * @return JSON {@link EventoDetailDTO} - El arreglo de blogs guardado en la
     * cliente.
     */
    @PUT
    @Path("{clientesId: \\d+}/blogs")
    public List<BlogDTO> replaceBlogs(@PathParam("clientesId") Long clientesId, List<BlogDetailDTO> blogs) {
        ClienteEntity cliente = clienteLogic.getCliente(clientesId);
        if (cliente == null) {
            throw new WebApplicationException("El cliente no existe.", 404);
        }
        try {
            return blogsListEntity2DTO(clienteLogic.replaceBlogs(clientesId, blogsListDTO2Entity(blogs)));
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }

    private List<BlogEntity> blogsListDTO2Entity(List<BlogDetailDTO> blogs) {
        List<BlogEntity> lista = new ArrayList<>();
        for (BlogDetailDTO blogD : blogs) {
            BlogEntity ent = blogD.toEntity();
            lista.add(ent);
        }
        return lista;
    }

    /**
     * <h1>DELETE /{clientesId}/blogs/{blogId} : Elimina un blog dentro del
     * cliente.</h1>
     *
     * <pre> Elimina la referencia del blog asociado al ID dentro del cliente
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del blog.
     * </code>
     * </pre>
     *
     * @param clientesId Identificador del cliente que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param blogsId Identificador del blog que se desea guardar. Este debe ser
     * una cadena de dígitos.
     */
    @DELETE
    @Path("{clientesId: \\d+}/blogs/{blogsId: \\d+}")
    public void removeBlog(@PathParam("clientesId") Long clientesId, @PathParam("blogsId") Long blogsId) {
        if (clienteLogic.getCliente(clientesId) == null) {
            throw new WebApplicationException("El cliente no existe", 404);
        }
        try {
            clienteLogic.removeBlog(blogsId, clientesId);
        } catch (BusinessLogicException e) {
            throw new WebApplicationException(e.getMessage(), 404);
        }

    }

    /**
     * Conexión con el servicio de evento para un cliente.
     * {@link EventoResource}
     *
     * Este método conecta la ruta de /eventos con las rutas de /pagos que
     * dependen del evento, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de las reseñas.
     *
     * @param idCliente El ID del cliente con respecto al cual se accede al
     * servicio.
     * @return El servicio de Eventos para ese pagoo en paricular.
     */
    @Path("{idCliente: \\d+}/eventos")
    public Class<EventoResource> getEventoResource(@PathParam("idCliente") Long idCliente) {
        ClienteEntity entity = clienteLogic.getCliente(idCliente);
        if (entity == null) {
            throw new WebApplicationException("El recurso /clientes/" + idCliente + "/eventos no existe.", 404);
        }
        return EventoResource.class;
    }
}
