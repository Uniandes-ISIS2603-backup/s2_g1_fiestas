/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ServicioDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ServicioLogic;
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

/**
  * <pre>Clase que implementa el recurso "servicios".
 * URL: /api/servicios
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
 * @author ls.arias
 */

@Path("servicio")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ServicioResource {
    
    public ServicioResource(){
        
    }
    
    @Inject
    private ServicioLogic logic;
     /**
     * <h1>POST /servicios : Crear un servicio.</h1>
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
     * @param servicio {@link ServicioDetailDTO} - El servicio que se desea guardar.
     * @return JSON {@link ServicioDetailDTO}  - El servicio guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el servicio.
     */
     @POST
    public ServicioDetailDTO createServicio(ServicioDetailDTO servicio) throws BusinessLogicException {
        if(logic.getServicio(servicio.getId())!=null)
            throw new BusinessLogicException("El servicio ya existe.");
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
     * @return JSONArray {@link ServicioDetailDTO} - Los servicios encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
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
     * @param id Identificador del servicio que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ServicioDetailDTO} - El servicio buscado
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    @GET
    @Path("{id: \\d+}")
    public ServicioDetailDTO getServicio(@PathParam("id") Long id) throws BusinessLogicException {
        ServicioEntity e = logic.getServicio(id);
        if(e == null)
        {
            throw new BusinessLogicException("El servicio con el id buscado no existe.");
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
    * @param id Identificador del servicio que se esta buscando. Este debe ser una cadena de dígitos.
     * @param servicio servicio a actualizar
    * @return JSON {@link ServicioDetailDTO} - El servicio buscado y actuaizado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica
    */
     @PUT
    @Path("{id: \\d+}")
    public ServicioDetailDTO updateServicio(@PathParam("id") Long id, ServicioDetailDTO servicio) throws BusinessLogicException {
       ServicioEntity ent =logic.getServicio(id);
        if(ent == null)
            throw new BusinessLogicException("El servicio no existe.");
        logic.updateServicio(ent);
        return new ServicioDetailDTO(ent);
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
    * @param id Identificador del servicio que se esta buscando. Este debe ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
    */
    @DELETE
    @Path("{id: \\d+}")
     public void deleteServicio(@PathParam("id") Long id) throws BusinessLogicException {
         ServicioEntity ent =logic.getServicio(id);
        if(ent == null)
             throw new BusinessLogicException("El servicio no existe.");
        logic.deleteServicio(id);
    }
     
     
     
     //Proveedores

    /**
     *
     * @param id
     * @return
     * @throws BusinessLogicException
     */    
     @GET
    @Path("{id:\\d+}/proveedores")
    public List<ProveedorDetailDTO> getProveedoresServicio(@PathParam("id")Long id) throws BusinessLogicException{
        ServicioEntity ent = logic.getServicio(id);
        if(ent == null)
            throw new BusinessLogicException("El servicio no existe.");
        return proveedorListEntity2DTO(logic.listProveedores(id));
    }
    
    /**
     *
     * @param id
     * @param proveedorId
     * @return
     * @throws BusinessLogicException
     */
    @GET
    @Path("{id:\\d+}/proveedores/{proveedorId:\\d+}")
    public ProveedorDetailDTO getServicioProveedor(@PathParam("id")Long id, @PathParam("proveedorId")Long proveedorId) throws BusinessLogicException
    {
        ServicioEntity e = logic.getServicio(id);
        if(e==null)
            throw new BusinessLogicException("El servicio no existe.");
        ProveedorEntity be= logic.getProveedor(e.getId(), proveedorId);
        return new ProveedorDetailDTO(be);
    }
    
    /**
     * Actualizar proveedores en un servicio dado por id.
     * 
     * @param id del servicio donde se reemplazan los proveedores
     * @param proveedores lista de proveedores a actualizar
     * @return lista de proveedores actualizado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica
     */
    @PUT
    @Path("{id: \\d+}/proveedores")
    public  List<ProveedorDetailDTO> replaceProveedores(@PathParam("id")Long id,List<ProveedorDetailDTO> proveedores) throws BusinessLogicException
    {
        return proveedorListEntity2DTO(logic.replaceProveedores(id,proveedorListDTO2Entity(proveedores)));
    }
    
    /**
     * Convierte una lista de ProveedorEntity a una lista de ProveedorDetailDTO.
     *
     * @param entityList Lista de ProveedorEntity a convertir.
     * @return Lista de ProveedorDetailDTO convertida.
     *
     */
    private List<ProveedorDetailDTO> proveedorListEntity2DTO(List<ProveedorEntity> entityList) {
        List<ProveedorDetailDTO> list = new ArrayList<>();
        for (ProveedorEntity entity : entityList) {
            list.add(new ProveedorDetailDTO(entity));
        }
        return list;
    }
    
     /**
     * Convierte una lista de ProveedorDetailDTO a una lista de ProveedorEntity.
     *
     * @param dtos Lista de ProveedorDetailDTO a convertir.
     * @return Lista de ProveedorEntity convertida.
     *
     */
    private List<ProveedorEntity> proveedorListDTO2Entity(List<ProveedorDetailDTO> dtos) {
        List<ProveedorEntity> list = new ArrayList<>();
        for (ProveedorDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    
    /**
     * <h1>POST /{serviciosId}/proveedores/{proveedoresId} : Guarda un
     * proveedor dentro del servicio.</h1>
     *
     * <pre> Guarda un proveedor dentro de un servicio con la informacion que
     * recibe el la URL. Se devuelve el proveedor que se guarda en el servicio.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo proveedor .
     * </code>
     * </pre>
     *
     * @param serviciosId Identificador del servicio que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param proveedorId Identificador del proveedor que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ProveedorDetailDTO} - El proveedor guardado en la
     * servicio.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica
     */
    @POST
    @Path("{serviciosId: \\d+}/proveedores/{proveedoresId: \\d+}")
    public ProveedorDetailDTO addProveedor(@PathParam("serviciosId") Long serviciosId, @PathParam("proveedoresId") Long proveedorId) throws BusinessLogicException {
        return new ProveedorDetailDTO(logic.addProveedor(proveedorId, serviciosId));
    }
    
    /**
     * <h1>DELETE /{servicioId}/proveedores/{proveedorId} : Elimina un
     * proveedor dentro del servicio.</h1>
     *
     * <pre> Elimina la referencia del proveedor asociado al ID dentro del servicio
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del proveedor.
     * </code>
     * </pre>
     *
     * @param servicioId Identificador del servicio que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param proveedoresId Identificador del proveedor que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica
     */
    @DELETE
    @Path("{servicioId: \\d+}/proveedores/{proveedoresId: \\d+}")
    public void removeProveedores(@PathParam("servicioId") Long servicioId, @PathParam("proveedoresId") Long proveedoresId) throws BusinessLogicException {
        logic.removeProveedor(proveedoresId, servicioId);
    }
    
    
    
    //Valoraciones
    
    /**
     *
     * @param id
     * @return
     * @throws BusinessLogicException
     */    
     @GET
    @Path("{id:\\d+}/proveedores")
    public List<ValoracionDetailDTO> getPValoracionesServicio(@PathParam("id")Long id) throws BusinessLogicException{
        ServicioEntity ent = logic.getServicio(id);
        if(ent == null)
            throw new BusinessLogicException("El servicio no existe.");
        return valoracionListEntity2DTO(logic.getValoraciones(id));
    }
    private List<ValoracionDetailDTO> valoracionListEntity2DTO(List<ValoracionEntity> entityList) {
        List<ValoracionDetailDTO> list = new ArrayList<>();
        for (ValoracionEntity entity : entityList) {
            list.add(new ValoracionDetailDTO(entity));
        }
        return list;
    }
    
     /**
     * Convierte una lista de ValoracionDetailDTO a una lista de ValoracionEntity.
     *
     * @param dtos Lista de ValoracionDetailDTO a convertir.
     * @return Lista de ValoracionEntity convertida.
     *
     */
    private List<ValoracionEntity> valoracionListDTO2Entity(List<ValoracionDetailDTO> dtos) {
        List<ValoracionEntity> list = new ArrayList<>();
        for (ValoracionDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}
