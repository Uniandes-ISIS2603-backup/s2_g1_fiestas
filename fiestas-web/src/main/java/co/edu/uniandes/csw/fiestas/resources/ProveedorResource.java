package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.BonoDTO;
import co.edu.uniandes.csw.fiestas.dtos.BonoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ContratoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ProductoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.ejb.ValoracionLogic;
import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
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
 * Produces/Consumes: indica que los productos definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (producto).
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
     * <h1>GET /{proveedorId}/productos/ : Obtener todos los productos de un
     * proveedor.</h1>
     *
     * <pre>Busca y devuelve todos los productos que existen en el proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los productos del proveedor.</code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link ProductoDetailDTO} - Los productos encontrados
     * en el proveedor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{proveedorId: \\d+}/productos")
    public List<ProductoDetailDTO> getProductos(@PathParam("proveedorId") Long proveedorId)
    {
        try
        {
            return productosListEntity2DTO(proveedorLogic.getProductos(proveedorId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>PUT /{proveedorId}/productos: Edita losproductos de un proveedor..</h1>
     * <pre> Remplaza las instancias de Producto asociadas a una instancia de Proveedor
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los productos del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param productos JSONArray {@link ProductoDetailDTO} El arreglo de
     * productos nuevo para la proveedor.
     * @return JSON {@link ProductoDetailDTO} - El arreglo de productos guardado
     * en la proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @PUT
    @Path("{proveedorId: \\d+}/productos")
    public List<ProductoDetailDTO> replaceProductos(@PathParam("proveedorId") Long proveedorId, List<ProductoDetailDTO> productos)
    {
        try
        {
            return productosListEntity2DTO(proveedorLogic.replaceProductos(proveedorId, productosListDTO2Entity(productos)));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>POST /{proveedorId}/productos/{productosId} : Guarda un producto
     * dentro del proveedor.</h1>
     *
     * <pre> Guarda un producto dentro de un proveedor con la informacion que
     * recibe el la URL. Se devuelve el producto que se guarda en la proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo producto .
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param productoId Identificador del producto que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ProductoDetailDTO} - El producto guardado en la
     * proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @PUT
    @Path("{proveedorId: \\d+}/productos/{productosId: \\d+}")
    public ProductoDetailDTO addProducto(@PathParam("proveedorId") Long proveedorId, @PathParam("productosId") Long productoId)
    {
        try
        {
            return new ProductoDetailDTO(proveedorLogic.addProducto(productoId, proveedorId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>DELETE /{proveedorId}/productos/{productosId} : Elimina un producto
     * dentro del proveedor.</h1>
     *
     * <pre> Elimina la referencia del producto asociado al ID dentro del proveedor
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del producto.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param productoId Identificador del producto que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @DELETE
    @Path("{proveedorId: \\d+}/productos/{productosId: \\d+}")
    public void removeProducto(@PathParam("proveedorId") Long proveedorId, @PathParam("productoId") Long productosId)
    {
        try
        {
            proveedorLogic.removeProducto(productosId, proveedorId);
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * <h1>GET /{proveedorId}/productos/{productosId} : Obtener producto por id
     * del proveedor por id.</h1>
     *
     * <pre>Busca el producto con el id asociado dentro del proveedor con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el producto correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un producto con el id dado dentro del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedorId Identificador del proveedor que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param productosId Identificador del producto que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ProductoDetailDTO} - El producto buscado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la proveedor o el
     * producto.
     */
    @GET
    @Path("{proveedorId: \\d+}/productos/{productosId: \\d+}")
    public ProductoDetailDTO getProducto(@PathParam("proveedorId") Long proveedorId, @PathParam("productosId") Long productosId)
    {
        try
        {
            return new ProductoDetailDTO(proveedorLogic.getProducto(proveedorId, productosId));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(), 404);
        }
    }
    
    /**
     * Convierte una lista de ProductoEntity a una lista de ProductoDetailDTO.
     *
     * @param entityList Lista de ProductoEntity a convertir.
     * @return Lista de ProductoDetailDTO convertida.
     *
     */
    private List<ProductoDetailDTO> productosListEntity2DTO(List<ProductoEntity> entityList) {
        List<ProductoDetailDTO> list = new ArrayList<>();
        for (ProductoEntity entity : entityList) {
            list.add(new ProductoDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de ProductoDetailDTO a una lista de ProductoEntity.
     *
     * @param dtos Lista de ProductoDetailDTO a convertir.
     * @return Lista de ProductoEntity convertida.
     *
     */
    private List<ProductoEntity> productosListDTO2Entity(List<ProductoDetailDTO> dtos) {
        List<ProductoEntity> list = new ArrayList<>();
        for (ProductoDetailDTO dto : dtos) {
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
    
    /**
    * Convierte una lista de BonoEntity a una lista de BonoDTO.
    *
    * @param entityList Lista de BonoEntity a convertir.
    * @return Lista de BonoDTO convertida.
    *
    */
    private List<BonoDTO> bonosListEntity2DTO(List<BonoEntity> bonoList) {
        List<BonoDTO> list = new ArrayList<>();
        for (BonoEntity entity : bonoList) {
            list.add(new BonoDTO(entity));
        }
        return list;
    }
    
    /**
     * <h1>GET /{proveedoresId}/bonos/ : Obtener todos los bonos de un proveedor.</h1>
     *
     * <pre>Busca y devuelve todos los bonos que bonos en el proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los bonos del proveedor.</code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSONArray {@link BonoDTO} - Los bonos encontrados
     * en el proveedor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{proveedoresId: \\d+}/bonos")
    public List<BonoDTO> listBonos(@PathParam("proveedoresId") Long proveedoresId) throws BusinessLogicException
    {       
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedoresId);
        if(proveedor == null)
            throw new WebApplicationException("El proveedor especificado no existe.", 404);
        
        return bonosListEntity2DTO(proveedorLogic.getBonos(proveedoresId));
    }
    
    /**
     * <h1>GET /{proveedoresId}/bonos/{bonoId} : Obtener
     * bono por id del proveedor por id.</h1>
     *
     * <pre>Busca el bono con el id asociado dentro del proveedor con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el bono correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un bono con el id dado dentro del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param bonoId Identificador del bono que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link BonoDTO} - El bono buscado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica 
     * que se genera cuando no se encuentra la proveedor o el bono.
     */
    @GET
    @Path("{proveedoresId: \\d+}/bonos/{bonoId: \\d+}")
    public BonoDTO getBonoC(@PathParam("proveedoresId") Long proveedoresId, @PathParam("eventosId") Long bonoId)
    {
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedoresId);
        if(proveedor == null)
            throw new WebApplicationException("El proveedor no existe", 404);
        
        try{return new BonoDTO(proveedorLogic.getBonoP(bonoId, proveedoresId));}
        catch(BusinessLogicException e){
            throw new WebApplicationException(e.getMessage(),404);
        }
    }
      
    /**
     * <h1>GET /bonos/{bonoId} : Obtener
     * bono por id.</h1>
     *
     * <pre>Busca el bono con el id .
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el bono correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un bono con el id dado.
     * </code>
     * </pre>
     *
     * @param bonoId Identificador del bono que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link BonoDTO} - El bono buscado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica 
     * que se genera cuando no se encuentra el bono.
     */
    @GET
    @Path("bonos/{bonoId: \\d+}")
    public BonoDTO getBono(@PathParam("eventosId") Long bonoId)
    {
        try{return new BonoDTO(proveedorLogic.getBono(bonoId));}
        catch(BusinessLogicException e){
            throw new WebApplicationException(e.getMessage(),404);
        }
    }
    

    /**
     * <h1>POST /{proveedoresId}/bonos/{bonosId} : Guarda un
     * bono dentro del proveedor.</h1>
     *
     * <pre> Guarda un bono dentro de un proveedor con la informacion que
     * recibe el la URL. Se devuelve el bono que se guarda en la proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo bono .
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param bonoDTO El bono que se desea guardar. Este
     * debe ser la representación de un bono en JSON.
     * @return JSON {@link BonoDetailDTO} - El bono guardado en la
     * proveedor.
     */
    @POST
    @Path("{proveedoresId: \\d+}")
    public BonoDTO addBono(@PathParam("proveedoresId") Long proveedoresId, BonoDTO bono)
    {        
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedoresId);
        if (proveedor == null)
            throw new WebApplicationException("El proveedor no existe.", 404);
        try{
            BonoEntity bonoE = bono.toEntity();
            if(bonoE == null)
                throw new WebApplicationException("El bono no se creó correctamente.", 404);            
            return new BonoDTO(proveedorLogic.addBono(bonoE, proveedoresId));
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(e.getMessage(),404);
        }
    }
    
    /*@PUT
    @Path("{proveedoresId: \\d+}/contratos/{contratosId: \\d+}/bonos/{bonosId: \\d+}")
    public BonoDTO setBono2Contrato(@PathParam("proveedoresId") Long proveedoresId, @PathParam("bonosId") Long bonosId,@PathParam("contratosId") Long contratosId) throws BusinessLogicException
    {        
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedoresId);
        ContratoEntity contrato = proveedorLogic.getContrato(proveedoresId, contratosId);
        BonoEntity bono = proveedorLogic.getBonoP(bonosId,proveedoresId);
        if (proveedor == null)
            throw new WebApplicationException("El proveedor no existe.", 404);
        if (contrato == null)
            throw new WebApplicationException("El contrat no existe.", 404);
        if (bono == null)
            throw new WebApplicationException("El bono no existe.", 404);
        
        
        proveedorLogic.setBono2Contrato(bonosId, proveedoresId, contratosId);
        return new BonoDetailDTO(bono);
    }**/
    
        /**
     * <h1>PUT /{proveedoresId}/bonos: Edita los bonos de un proveedor..</h1>
     * <pre> Remplaza las instancias de Evento asociadas a una instancia de Proveedor
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los bonos del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param bonos JSONArray {@link BonoDetailDTO} El arreglo de
     * bonos nuevo para la proveedor.
     * @return JSON {@link EventoDetailDTO} - El arreglo de bonos guardado
     * en la proveedor.
     */
    @PUT
    @Path("{proveedoresId: \\d+}/bonos")
    public List<BonoDTO> replaceBonos(@PathParam("proveedoresId") Long proveedoresId, List<BonoDetailDTO> bonos) 
    {        
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedoresId);
        if(proveedor == null){
            throw new WebApplicationException("El proveedor no existe.", 404);
        }
        try
        {
            return bonosListEntity2DTO(proveedorLogic.replaceBonos(proveedoresId, bonosListDTO2Entity(bonos)));
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(),404);
        }
    }

    private List<BonoEntity> bonosListDTO2Entity(List<BonoDetailDTO> bonos) {
        List<BonoEntity> lista = new ArrayList<>();
        for (BonoDetailDTO bonoD : bonos) {
            BonoEntity ent = bonoD.toEntity();
            lista.add(ent);
        }
        return lista;
    }
    
    /**
     * <h1>DELETE /{proveedoresId}/bonos/{bonoId} : Elimina un
     * bono dentro del proveedor.</h1>
     *
     * <pre> Elimina la referencia del bono asociado al ID dentro del proveedor
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del bono.
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param bonosId Identificador del bono que se desea guardar. Este
     * debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{proveedoresId: \\d+}/bonos/{bonosId: \\d+}")
    public void removeBono(@PathParam("proveedoresId") Long proveedoresId, @PathParam("bonosId") Long bonosId) 
    {
        if (proveedorLogic.getProveedor(proveedoresId)==null)
            throw new WebApplicationException("El proveedor no existe", 404);
        try
        {
            proveedorLogic.removeBono(bonosId, proveedoresId);
        }
        catch(BusinessLogicException e)
        {
            throw new WebApplicationException(e.getMessage(),404);
        }
        
    }
}
