package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ContratoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ServicioDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.ejb.ValoracionLogic;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

/**
 * <pre>Clase que implementa el recurso "proveedores".
 * URL: /api/proveedores
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta
 * "/api" y este recurso tiene la ruta "proveedores.</i>
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
@Path("proveedores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProveedorResource {
    
    @Inject
    private ProveedorLogic proveedorLogic;
    
    /**
     * Convierte una lista de ProveedorEntity a una lista de ProveedorDetailDTO.
     *
     * @param entityList Lista de ProveedorEntity a convertir.
     * @return Lista de ProveedorDetailDTO convertida.
     *
     */
    private List<ProveedorDetailDTO> listEntity2DTO(List<ProveedorEntity> entityList)
    {
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
     *
     * @return JSONArray {@link ProveedorDetailDTO} - Los proveedores
     * encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProveedorDetailDTO> getProveedores()
    {
        return listEntity2DTO(proveedorLogic.getProveedores());
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
     *
     * @param id Identificador del proveedor que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @return JSON {@link ProveedorDetailDTO} - El proveedor buscado
     */
    @GET
    @Path("{id: \\d+}")
    public ProveedorDetailDTO getProveedor(@PathParam("id") Long id)
    {
        ProveedorEntity entity = proveedorLogic.getProveedor(id);
        if (entity == null)
        {
            throw new WebApplicationException("El proveedor no existe", 404);
        }
        return new ProveedorDetailDTO(entity);
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
     * 412 Precondition Failed: Ya existe el proveedor.
     * </code>
     * </pre>
     *
     * @param proveedor {@link ProveedorDetailDTO} - La ciudad que se desea
     * guardar.
     * @return JSON {@link ProveedorDetailDTO} - El proveedor guardado con el
     * atributo id autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando ya existe el proveedor.
     */
    @POST
    public ProveedorDetailDTO createProveedor(ProveedorDetailDTO proveedor) throws BusinessLogicException
    {
        
        return new ProveedorDetailDTO(proveedorLogic.createProveedor(proveedor.toEntity()));
        
    }
    
    /**
     * <h1>PUT /proveedores/{id} : Actualizar proveedor por id.</h1>
     *
     * <pre>Busca el proveedor con el id asociado recibido en la URL, actualiza os paramteros
     * y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el proveedor correspondiente al id, despues de actualizado.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un proveedor con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del proveedor que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @param proveedor proveedor a actualizar en la base de datos
     * @return JSON {@link ProveedorDetailDTO} - El proveedor buscado y
     * actuaizado
     */
    @PUT
    @Path("{id: \\d+}")
    public ProveedorDetailDTO updateProveedor(@PathParam("id") long id, ProveedorDetailDTO proveedor) throws BusinessLogicException
    {
        ProveedorEntity entity = proveedor.toEntity();
        entity.setId(id);
        
        return new ProveedorDetailDTO(proveedorLogic.updateProveedor(entity));       
    }
    
    /**
     * <h1>DELETE /proveedores/{id} : Elimina un proveedor por id.</h1>
     *
     * <pre>Busca el proveedor con el id asociado recibido en la URL y lo elimina
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK El proveedor fue eliminado exitosamente
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un proveedor con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del proveedor que se esta buscando. Este debe ser
     * una cadena de dígitos.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteProveedor(@PathParam("id") Long id)
    {
        try
        {
            proveedorLogic.deleteProveedor(id);
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    
    
    /**
     * <h1>GET /{proveedorId}/contratos/ : Obtener todos los contratos de un
     * proveedor.</h1>
     *
     * <pre>Busca y devuelve todos los contratos que existen en el proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los contratos del proveedor.</code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link ContratoDetailDTO} - Los contratos encontrados
     * en el proveedor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{proveedorId: \\d+}/contratos")
    public List<ContratoDetailDTO> listContratos(@PathParam("proveedorId") Long proveedorId)
    {
        try
        {
            return contratosListEntity2DTO(proveedorLogic.getContratos(proveedorId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>PUT /{proveedorId}/contratos: Edita loscontratos de un
     * proveedor..</h1>
     * <pre> Remplaza las instancias de Contrato asociadas a una instancia de Editorial
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los contratos del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param contratos JSONArray {@link ContratoDetailDTO} El arreglo de
     * contratos nuevo para la proveedor.
     * @return JSON {@link ContratoDetailDTO} - El arreglo de contratos guardado
     * en la proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si hay errores de logica
     */
    @PUT
    @Path("{proveedorId: \\d+}/contratos")
    public List<ContratoDetailDTO> replaceContratos(@PathParam("proveedorId") Long proveedorId, List<ContratoDetailDTO> contratos)
    {
        try
        {
            return contratosListEntity2DTO(proveedorLogic.replaceContratos(proveedorId, contratosListDTO2Entity(contratos)));
        }
        catch(BusinessLogicException e)
        {
            if(e.getMessage().equals("El proveedor está penalizado, no puede adquirir contratos"))
            {
                throw new WebApplicationException(e.getMessage(), 412);
            }
            else
            {
                throw new WebApplicationException(e.getMessage(), 404);
            }
        }
    }
    
    /**
     * <h1>POST /{proveedorId}/contratos/{contratoId} : Guarda un contrato
     * dentro del proveedor.</h1>
     *
     * <pre> Guarda un contrato dentro de un proveedor con la informacion que
     * recibe el la URL. Se devuelve el contrato que se guarda en la proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo contrato .
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param contratoId Identificador del contrato que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato guardado en la
     * proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hubo algun error de logica
     */
    @POST
    @Path("{proveedorId: \\d+}/contratos/{contratoId: \\d+}")
    public ContratoDetailDTO addContrato(@PathParam("proveedorId") Long proveedorId, @PathParam("contratoId") Long contratoId)
    {
        try
        {
            return new ContratoDetailDTO(proveedorLogic.addContrato(contratoId, proveedorId));
        }
        catch(BusinessLogicException e)
        {
            if(e.getMessage().equals("El proveedor está penalizado, no puede adquirir contratos"))
            {
                throw new WebApplicationException(e.getMessage(), 412);
            }
            else
            {
                throw new WebApplicationException(e.getMessage(), 404);
            }
        }
    }
    
    /**
     * <h1>GET /{proveedorId}/contratos/{contratoId} : Obtener contrato por id
     * del proveedor por id.</h1>
     *
     * <pre>Busca el contrato con el id asociado dentro del proveedor con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el contrato correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un contrato con el id dado dentro del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param contratoId Identificador del contrato que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato buscado
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando no se encuentra la proveedor o el
     * contrato.
     */
    @GET
    @Path("{proveedorId: \\d+}/contratos/{contratoId: \\d+}")
    public ContratoDetailDTO getContrato(@PathParam("proveedorId") Long proveedorId, @PathParam("contratoId") Long contratoId)
    {
        try
        {
            return new ContratoDetailDTO(proveedorLogic.getContrato(proveedorId, contratoId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
        
    }
    
    /**
     * Convierte una lista de ContratoEntity a una lista de ContratoDetailDTO.
     *
     * @param entityList Lista de ContratoEntity a convertir.
     * @return Lista de ContratoDetailDTO convertida.
     *
     */
    private List<ContratoDetailDTO> contratosListEntity2DTO(List<ContratoEntity> entityList)
    {
        List<ContratoDetailDTO> list = new ArrayList<>();
        for (ContratoEntity entity : entityList) {
            list.add(new ContratoDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de ContratoDetailDTO a una lista de ContratoEntity.
     *
     * @param dtos Lista de ContratoDetailDTO a convertir.
     * @return Lista de ContratoEntity convertida.
     *
     */
    private List<ContratoEntity> contratosListDTO2Entity(List<ContratoDetailDTO> dtos)
    {
        List<ContratoEntity> list = new ArrayList<>();
        for (ContratoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * <h1>GET /{proveedorId}/valoraciones/ : Obtener todos los valoraciones de
     * un proveedor.</h1>
     *
     * <pre>Busca y devuelve todos los valoraciones que existen en el proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los valoraciones del proveedor.</code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link ValoracionDetailDTO} - Los valoraciones
     * encontrados en el proveedor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{proveedorId: \\d+}/valoraciones")
    public List<ValoracionDTO> listValoraciones(@PathParam("proveedorId") Long proveedorId)
    {
        try
        {
            return valoracionesListEntity2DTO(proveedorLogic.getValoraciones(proveedorId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>PUT /{proveedorId}/valoraciones: Edita losvaloraciones de un proveedor..</h1>
     * <pre> Remplaza las instancias de Valoracion asociadas a una instancia de Proveedor
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los valoraciones del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param valoraciones JSONArray {@link ValoracionDetailDTO} El arreglo de
     * valoraciones nuevo para la proveedor.
     * @return JSON {@link ValoracionDetailDTO} - El arreglo de valoraciones
     * guardado en la proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @PUT
    @Path("{proveedorId: \\d+}/valoraciones")
    public List<ValoracionDTO> replaceValoraciones(@PathParam("proveedorId") Long proveedorId, List<ValoracionDTO> valoraciones)
    {
        try
        {
            return valoracionesListEntity2DTO(proveedorLogic.replaceValoraciones(proveedorId, valoracionesListDTO2Entity(valoraciones)));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>POST /{proveedorId}/valoraciones/{valoracionId} : Guarda un
     * valoracion dentro del proveedor.</h1>
     *
     * <pre> Guarda un valoracion dentro de un proveedor con la informacion que
     * recibe el la URL. Se devuelve el valoracion que se guarda en la proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo valoracion .
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param valoracionId Identificador del valoracion que se desea guardar.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ValoracionDetailDTO} - El valoracion guardado en la
     * proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @POST
    @Path("{proveedorId: \\d+}/valoraciones/{valoracionId: \\d+}")
    public ValoracionDTO addValoracion(@PathParam("proveedorId") Long proveedorId, @PathParam("valoracionId") Long valoracionId)
    {
        try
        {
            return new ValoracionDTO(proveedorLogic.addValoracion(valoracionId, proveedorId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>DELETE /{proveedorId}/valoraciones/{valoracionId} : Elimina un
     * valoracion dentro del proveedor.</h1>
     *
     * <pre> Elimina la referencia del valoracion asociado al ID dentro del proveedor
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del valoracion.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param valoracionId Identificador del valoracion que se desea guardar.
     * Este debe ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @DELETE
    @Path("{proveedorId: \\d+}/valoraciones/{valoracionId: \\d+}")
    public void removeValoraciones(@PathParam("proveedorId") Long proveedorId, @PathParam("valoracionId") Long valoracionId)
    {
        try
        {
            proveedorLogic.removeValoracion(valoracionId, proveedorId);
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>GET /{proveedorId}/valoraciones/{valoracionId} : Obtener valoracion
     * por id del proveedor por id.</h1>
     *
     * <pre>Busca el valoracion con el id asociado dentro del proveedor con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el valoracion correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un valoracion con el id dado dentro del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param valoracionId Identificador del valoracion que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ValoracionDetailDTO} - El valoracion buscado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} -Error de lógica
     * que se genera cuando no se encuentra la proveedor o el
     * valoracion.
     */
    @GET
    @Path("{proveedorId: \\d+}/valoraciones/{valoracionId: \\d+}")
    public ValoracionDTO getValoracion(@PathParam("proveedorId") Long proveedorId, @PathParam("valoracionId") Long valoracionId)
    {
        try
        {
            return new ValoracionDTO(proveedorLogic.getValoracion(proveedorId, valoracionId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * Convierte una lista de ValoracionEntity a una lista de
     * ValoracionDetailDTO.
     *
     * @param entityList Lista de ValoracionEntity a convertir.
     * @return Lista de ValoracionDetailDTO convertida.
     *
     */
    private List<ValoracionDTO> valoracionesListEntity2DTO(List<ValoracionEntity> entityList)
    {
        List<ValoracionDTO> list = new ArrayList<>();
        for (ValoracionEntity entity : entityList) {
            list.add(new ValoracionDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de ValoracionDetailDTO a una lista de
     * ValoracionEntity.
     *
     * @param dtos Lista de ValoracionDetailDTO a convertir.
     * @return Lista de ValoracionEntity convertida.
     *
     */
    private List<ValoracionEntity> valoracionesListDTO2Entity(List<ValoracionDTO> dtos)
    {
        List<ValoracionEntity> list = new ArrayList<>();
        for (ValoracionDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * <h1>GET /{proveedorId}/servicios/ : Obtener todos los servicios de un
     * proveedor.</h1>
     *
     * <pre>Busca y devuelve todos los servicios que existen en el proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los servicios del proveedor.</code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link ServicioDetailDTO} - Los servicios encontrados
     * en el proveedor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{proveedorId: \\d+}/servicios")
    public List<ServicioDetailDTO> getServicios(@PathParam("proveedorId") Long proveedorId)
    {
        try
        {
            return serviciosListEntity2DTO(proveedorLogic.getServicios(proveedorId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>PUT /{proveedorId}/servicios: Edita losservicios de un proveedor..</h1>
     * <pre> Remplaza las instancias de Servicio asociadas a una instancia de Proveedor
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los servicios del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param servicios JSONArray {@link ServicioDetailDTO} El arreglo de
     * servicios nuevo para la proveedor.
     * @return JSON {@link ServicioDetailDTO} - El arreglo de servicios guardado
     * en la proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @PUT
    @Path("{proveedorId: \\d+}/servicios")
    public List<ServicioDetailDTO> replaceServicios(@PathParam("proveedorId") Long proveedorId, List<ServicioDetailDTO> servicios)
    {
        try
        {
            return serviciosListEntity2DTO(proveedorLogic.replaceServicios(proveedorId, serviciosListDTO2Entity(servicios)));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>POST /{proveedorId}/servicios/{serviciosId} : Guarda un servicio
     * dentro del proveedor.</h1>
     *
     * <pre> Guarda un servicio dentro de un proveedor con la informacion que
     * recibe el la URL. Se devuelve el servicio que se guarda en la proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo servicio .
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param servicioId Identificador del servicio que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ServicioDetailDTO} - El servicio guardado en la
     * proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @POST
    @Path("{proveedorId: \\d+}/servicios/{serviciosId: \\d+}")
    public ServicioDetailDTO addServicio(@PathParam("proveedorId") Long proveedorId, @PathParam("serviciosId") Long servicioId)
    {
        try
        {
            return new ServicioDetailDTO(proveedorLogic.addServicio(servicioId, proveedorId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>DELETE /{proveedorId}/servicios/{serviciosId} : Elimina un servicio
     * dentro del proveedor.</h1>
     *
     * <pre> Elimina la referencia del servicio asociado al ID dentro del proveedor
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del servicio.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param servicioId Identificador del servicio que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @DELETE
    @Path("{proveedorId: \\d+}/servicios/{serviciosId: \\d+}")
    public void removeServicio(@PathParam("proveedorId") Long proveedorId, @PathParam("servicioId") Long serviciosId)
    {
        try
        {
            proveedorLogic.removeServicio(serviciosId, proveedorId);
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>GET /{proveedorId}/servicios/{serviciosId} : Obtener servicio por id
     * del proveedor por id.</h1>
     *
     * <pre>Busca el servicio con el id asociado dentro del proveedor con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el servicio correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un servicio con el id dado dentro del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param serviciosId Identificador del servicio que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ServicioDetailDTO} - El servicio buscado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la proveedor o el
     * servicio.
     */
    @GET
    @Path("{proveedorId: \\d+}/servicios/{serviciosId: \\d+}")
    public ServicioDetailDTO getServicio(@PathParam("proveedorId") Long proveedorId, @PathParam("serviciosId") Long serviciosId)
    {
        try
        {
            return new ServicioDetailDTO(proveedorLogic.getServicio(proveedorId, serviciosId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * Convierte una lista de ServicioEntity a una lista de ServicioDetailDTO.
     *
     * @param entityList Lista de ServicioEntity a convertir.
     * @return Lista de ServicioDetailDTO convertida.
     *
     */
    private List<ServicioDetailDTO> serviciosListEntity2DTO(List<ServicioEntity> entityList) {
        List<ServicioDetailDTO> list = new ArrayList<>();
        for (ServicioEntity entity : entityList) {
            list.add(new ServicioDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de ServicioDetailDTO a una lista de ServicioEntity.
     *
     * @param dtos Lista de ServicioDetailDTO a convertir.
     * @return Lista de ServicioEntity convertida.
     *
     */
    private List<ServicioEntity> serviciosListDTO2Entity(List<ServicioDetailDTO> dtos) {
        List<ServicioEntity> list = new ArrayList<>();
        for (ServicioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
    
    /**
     * Remover un contrato de un proveedor.
     *
     * @param asyncResponse Elemento de la clase AsyncResponse
     * @param proveedorId id del proveedor que tiene el contrato
     * @param contratoId id del contrato a remover
     */
    @DELETE
    @Path(value = "{proveedorId: \\d+}/contratos/{contratoId: \\d+}")
    public void removeContratos(@Suspended final AsyncResponse asyncResponse, @PathParam(value = "proveedorId") final Long proveedorId, @PathParam(value = "contratoId") final Long contratoId) {
        executorService.submit
                        (
                                new Runnable()
                                {
                                    public void run()
                                    {
                                        doRemoveContratos(proveedorId, contratoId);
                                        asyncResponse.resume(javax.ws.rs.core.Response.ok().build());
                                    }
                                }
                        );
    }
    
    private void doRemoveContratos(@PathParam("proveedorId") Long proveedorId, @PathParam("contratoId") Long contratoId)
    {
        try
        {
            proveedorLogic.removeContrato(contratoId, proveedorId);
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
}
