
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.EventoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
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
 * <pre>Clase que implementa el recurso "proveedores".
 * URL: /api/proveedores
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "proveedores.</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author nm.hernandez10
 */
@Path("proveedores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProveedorResource 
{
    private ProveedorLogic proveedorLogic;
    
    /**
     * Convierte una lista de EventoEntity a una lista de EventoDetailDTO.
     *
     * @param entityList Lista de EventoEntity a convertir.
     * @return Lista de EventoDetailDTO convertida.
     *
     */
    private List<ProveedorDetailDTO> listEntity2DTO(List<ProveedorEntity> entityList) {
        List<ProveedorDetailDTO> list = new ArrayList<>();
        for (ProveedorEntity entity : entityList) {
            list.add(new ProveedorDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * <h1>GET /proveedores : Obtener todos los proveedores.</h1>
     * 
     * <pre>Busca y devuelve todos los proveedores que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los proveedores de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link ProveedorDetailDTO} - Los proveedores encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProveedorDetailDTO> getProveedores() {
        return new ArrayList<>();
    }

    /**
     * <h1>GET /proveedores/{id} : Obtener proveedor por id.</h1>
     * 
     * <pre>Busca el proveedor con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el proveedor correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un proveedor con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del proveedor que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ProveedorDetailDTO} - El proveedor buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ProveedorDetailDTO getProveedor(@PathParam("id") Long id) {
        return null;
    }
    
        /**
     * <h1>POST /proveedores : Crear un proveedor.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link ProveedorDetailDTO}.
     * 
     * Crea un nuevo proveedor con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo proveedor.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el proveedor.
     * </code>
     * </pre>
     * @param proveedor {@link ProveedorDetailDTO} - El proveedor que se desea guardar.
     * @return JSON {@link ProveedorDetailDTO}  - El proveedor guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el proveedor.
     */
    @POST
    public ProveedorDetailDTO createProveedor(ProveedorDetailDTO proveedor) throws BusinessLogicException 
    {
        
    }
    
    @PUT
    @Path("{id: \\d+}")
    public ProveedorDetailDTO updateProveedor(@PathParam("id")Long id) {
        return null;
    }
    
    @DELETE
    @Path("{id:\\d+}")
    public void deleteProveedor(@PathParam("id")Long id){
        
    }
    
}
