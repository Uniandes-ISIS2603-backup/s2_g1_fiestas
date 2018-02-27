package co.edu.uniandes.csw.fiestas.resources;


import co.edu.uniandes.csw.fiestas.dtos.ClienteDetailDTO;
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
 * <pre>Clase que implementa el recurso "clientes".
 * URL: /api/clientes
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "clientes".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author df.nino10  
 */
@Path("clientes")
@Produces("application/json")

public class ClienteResource {

    /**
     * <h1>GET /clientes : Obtener todos los clientes.</h1>
     * 
     * <pre>Busca y devuelve todos los clientes que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los clientes de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link ClienteDetailDTO} - Los clientes encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ClienteDetailDTO> getClientes() {
        return new ArrayList<>();
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
     * @param id Identificador del cliente que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ClienteDetailDTO} - El cliente buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("id") Long id) {
        return null;
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
     * 412 Precodition Failed: Ya existe el cliente.
     * </code>
     * </pre>
     * @param cliente {@link ClienteDetailDTO} - El cliente que se desea guardar.
     * @return JSON {@link ClienteDetailDTO}  - El cliente guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el cliente.
     */
    @POST
    public ClienteDetailDTO createCliente(ClienteDetailDTO cliente) throws BusinessLogicException {
        return cliente;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("id")Long id) {
        return null;
    }
    
    @DELETE
    @Path("{id:\\d+}")
    public void deleteCliente(@PathParam("id")Long id){
        
    }
    
}
