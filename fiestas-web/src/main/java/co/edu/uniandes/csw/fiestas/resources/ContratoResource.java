/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * <pre>Clase que implementa el recurso "Contratos".
 * URL: /api/Contratos
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
 * @author df.nino10  
 */
@Path("Contratos")
@Produces("application/json")

public class ContratoResource {

    /**
     * <h1>GET /Contratos : Obtener todos los Contratos.</h1>
     * 
     * <pre>Busca y devuelve todos los Contratos que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los Contratos de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link ContratoDetailDTO} - Los Contratos encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ContratoDetailDTO> getContratos() {
        return new ArrayList<>();
    }

    /**
     * <h1>GET /Contratos/{id} : Obtener Contrato por id.</h1>
     * 
     * <pre>Busca el Contrato con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el Contrato correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un Contrato con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del Contrato que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El Contrato buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ContratoDetailDTO getContrato(@PathParam("id") Long id) {
        return null;
    }
    
        /**
     * <h1>POST /Contratos : Crear un Contrato.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link ContratoDetailDTO}.
     * 
     * Crea un nuevo Contrato con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo Contrato.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el Contrato.
     * </code>
     * </pre>
     * @param contrato {@link ContratoDetailDTO} - La ciudad que se desea guardar.
     * @return JSON {@link ContratoDetailDTO}  - El Contrato guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la ciudad.
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
    
