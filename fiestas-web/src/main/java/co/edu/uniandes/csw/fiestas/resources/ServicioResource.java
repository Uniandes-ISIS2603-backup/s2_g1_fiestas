/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ProductoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ServicioDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ServicioLogic;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
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
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "servicios".
 * URL: /api/servicio
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta
 * "/api" y este recurso tiene la ruta "servicios".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio).
 * </pre>
 *
 * @author ls.arias
 */
@Path("servicio")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ServicioResource {
    
    public ServicioResource() {
        //constructor vacio
    }
    
    @Inject
    private ServicioLogic logic;
    
    /**
     * <h1>POST /servicio : Crear un servicio.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link ServicioDetailDTO}.
     *
     * Crea un nuevo servicio con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo servicio.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el servicio.
     * </code>
     * </pre>
     *
     * @param servicio {@link ServicioDetailDTO} - El servicio que se desea
     * guardar.
     * @return JSON {@link ServicioDetailDTO} - El servicio guardado con el
     * atributo id autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando ya existe el servicio.
     */
    @POST
    public ServicioDetailDTO createServicio(ServicioDetailDTO servicio) throws BusinessLogicException {
        if (logic.getServicio(servicio.getId()) != null) {
            throw new BusinessLogicException("El servicio ya existe.");
        }
        logic.createServicio(servicio.toEntity());
        return servicio;
    }
    
    /**
     * <h1>GET /servicios : Obtener todos los servicios.</h1>
     *
     * <pre>Busca y devuelve todos los servicios que existen en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los servicios de la aplicacion.</code>
     * </pre>
     *
     * @return JSONArray {@link ServicioDetailDTO} - Los servicios encontrados
     * en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ServicioDetailDTO> getServicios() {
        return listEntity2DTO(logic.getServicios());
    }
    
    /**
     * Convierte una lista de ServicioiEntity a una lista de ServicioDetailDTO.
     *
     * @param entityList Lista de ServicioEntity a convertir.
     * @return Lista de ServicioDetailDTO convertida.
     *
     */
    private List<ServicioDetailDTO> listEntity2DTO(List<ServicioEntity> entityList) {
        List<ServicioDetailDTO> list = new ArrayList<>();
        for (ServicioEntity entity : entityList) {
            list.add(new ServicioDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * <h1>GET /servicios/{id} : Obtener servicio por id.</h1>
     *
     * <pre>Busca el servicio con el id asociado recibido en la URL y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el servicio correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un servicio con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del servicio que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @return JSON {@link ServicioDetailDTO} - El servicio buscado
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @GET
    @Path("{id: \\d+}")
    public ServicioDetailDTO getServicio(@PathParam("id") Long id) throws BusinessLogicException {
        ServicioEntity e = logic.getServicio(id);
        if (e == null) {
            throw new WebApplicationException("El servicio con el id buscado no existe.", 404);
        }
        return new ServicioDetailDTO(e);
    }
    
    /**
     * <h1>PUT /servicio/{id} : Actualizar servicio por id.</h1>
     *
     * <pre>Busca el servicio con el id asociado recibido en la URL, actualiza los paramteros
     * y la devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el servicio correspondiente al id, despues de actualizado.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un servicio con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del servicio que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @param servicio {@link ServicioDetailDTO} El servicio que se desea guardar.
     * @return JSON {@link ServicioDetailDTO} - El servicio buscado y actuaizado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el servicio a
     * actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public ServicioDetailDTO updateServicio(@PathParam("id") Long id, ServicioDetailDTO servicio) throws BusinessLogicException {
        ServicioEntity entidad = servicio.toEntity();
        entidad.setId(id);
        ServicioEntity ent = logic.getServicio(id);
        if (ent == null) {
            throw new WebApplicationException("El servicio no existe.", 404);
        }
        return new ServicioDetailDTO(logic.updateServicio(entidad));
    }
    
    /**
     * <h1>DELETE /servicio/{id} : Elimina un servicio por id.</h1>
     *
     * <pre>Busca rl servicio con el id asociado recibido en la URL y lo elimina
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK La servicio fue eliminada exitosamente
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe una servicio con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del servicio que se esta buscando. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteServicio(@PathParam("id") Long id) throws BusinessLogicException {
        ServicioEntity ent = logic.getServicio(id);
        if (ent == null) {
            throw new WebApplicationException("l servicio con el id buscado no existe.", 404);
        }
        logic.deleteServicio(id);
    }
    
    
    
    //Productos
    /**
     * <h1>GET /{servicioId}/productos/ : Obtener todos los productos de un
     * servicio.</h1>
     *
     * <pre>Busca y devuelve todos los productos de un servicio.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los productos del servicio.</code>
     * </pre>
     *
     * @param servicioId Identificador del servicio que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSONArray {@link ProductoDetailDTO} - Los productos encontrados
     * en el servicio. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{servicioId: \\d+}/productos")
    public List<ProductoDetailDTO> getProductos(@PathParam("servicioId") Long servicioId)
    {
        return productosListEntity2DTO(logic.listProductos(servicioId));
    }
    
    /**
     * <h1>POST /{servicioId}/productos/{productosId} : Guarda un producto
     * dentro del servicio.</h1>
     *
     * <pre> Guarda un producto dentro de un servicio con la informacion que
     * recibe el la URL. Se devuelve el producto que se guarda en el servicio.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo producto .
     * </code>
     * </pre>
     *
     * @param servicioId Identificador del servicio que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param productoId Identificador del producto que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ProductoDetailDTO} - El producto guardado en el
     * servicio.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * hay errores de logica
     */
    @PUT
    @Path("{servicioId: \\d+}/productos/{productosId: \\d+}")
    public ProductoDetailDTO addProducto(@PathParam("servicioId") Long servicioId, @PathParam("productosId") Long productoId) throws BusinessLogicException
    {
        return new ProductoDetailDTO(logic.addProducto(servicioId, productoId));
    }
    
    /**
     * <h1>DELETE /{servicioId}/productos/{productosId} : Elimina un producto
     * dentro del servicio.</h1>
     *
     * <pre> Elimina la referencia del producto asociado al ID dentro del servicio
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del producto.
     * </code>
     * </pre>
     *
     * @param servicioId Identificador del servicio que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param productoId Identificador del producto que se desea guardar. Este
     * debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{servicioId: \\d+}/productos/{productoId: \\d+}")
    public void removeProducto(@PathParam("pservicioId") Long servicioId, @PathParam("productoId") Long productoId)
    {
        logic.removeProducto(servicioId, productoId);
        
    }
    
    /**
     * <h1>GET /{servicioId}/productos/{productosId} : Obtener producto por id
     * del servicio por id.</h1>
     *
     * <pre>Busca el producto con el id asociado dentro del servicio con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el producto correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un producto con el id dado dentro del servicio.
     * </code>
     * </pre>
     *
     * @param servicioId Identificador del servicio que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @param productosId Identificador del producto que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ProductoDetailDTO} - El producto buscado
     */
    @GET
    @Path("{servicioId: \\d+}/productos/{productosId: \\d+}")
    public ProductoDetailDTO getProducto(@PathParam("servicioId") Long servicioId, @PathParam("productosId") Long productosId) throws BusinessLogicException
    {
            return new ProductoDetailDTO(logic.getProducto(servicioId, productosId));
        
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

}