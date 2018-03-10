package co.edu.uniandes.csw.fiestas.resources;
import co.edu.uniandes.csw.fiestas.dtos.ContratoDetailDTO;
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
 * <pre>Clase que implementa el recurso "contratos".
 * URL: /api/contratos
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "Contratos".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author mc.gonzalez15  
 */
@Path("contratos")
@Produces("application/json")

public class ContratoResource {

    /**
     * <h1>GET /contratos : Obtener todos los Contratos.</h1>
     * 
     * <pre>Busca y devuelve todos los contratos que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los contratos de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link ContratoDetailDTO} - Los contratos encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ContratoDetailDTO> getContratos() {
        return new ArrayList<>();
    }

    /**
     * <h1>GET /contratos/{id} : Obtener contrato por id.</h1>
     * 
     * <pre>Busca el contrato con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el contrato correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un contrato con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del contrato que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ContratoDetailDTO getContrato(@PathParam("id") Long id) {
        return null;
    }
    
        /**
     * <h1>POST /contratos : Crear un contrato.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link ContratoDetailDTO}.
     * 
     * Crea un nuevo contrato con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo contrato.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precondition Failed: Ya existe el contrato.
     * </code>
     * </pre>
     * @param contrato {@link ContratoDetailDTO} - La ciudad que se desea guardar.
     * @return JSON {@link ContratoDetailDTO}  - El contrato guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el contrato.
     */
    @POST
    public ContratoDetailDTO createContrato(ContratoDetailDTO contrato) throws BusinessLogicException {
        return contrato;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public ContratoDetailDTO updateContrato(@PathParam("id")Long id) {
        return null;
    }
    
    @DELETE
    @Path("{id:\\d+}")
    public void deleteContrato(@PathParam("id")Long id){
        
    }
}
    
