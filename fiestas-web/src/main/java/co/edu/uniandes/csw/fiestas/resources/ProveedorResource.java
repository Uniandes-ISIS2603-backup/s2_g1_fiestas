
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ContratoDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.HorarioDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ProveedorDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ProveedorLogic;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
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
     * @return JSONArray {@link ProveedorDetailDTO} - Los proveedores encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ProveedorDetailDTO> getProveedores() {
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
    public ProveedorDetailDTO getProveedor(@PathParam("id") Long id) {
        ProveedorEntity entity = proveedorLogic.getProveedor(id);
        if (entity == null) {
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
     * @param proveedor {@link ProveedorDetailDTO} - La ciudad que se desea guardar.
     * @return JSON {@link ProveedorDetailDTO} - El proveedor guardado con el atributo
     * id autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando ya existe el proveedor.
     */
    @POST
    public ProveedorDetailDTO createProveedor(ProveedorDetailDTO proveedor) throws BusinessLogicException {
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
     * @return JSON {@link ProveedorDetailDTO} - El proveedor buscado y actuaizado
     */
    @PUT
    @Path("{id: \\d+}")
    public ProveedorDetailDTO updateProveedor(@PathParam("id") Long id, ProveedorDetailDTO proveedor) {
        ProveedorEntity entity = proveedor.toEntity();
        entity.setId(id);
        ProveedorEntity oldEntity = proveedorLogic.getProveedor(id);
        if (oldEntity == null) {
            throw new WebApplicationException("El proveedor no existe", 404);
        }
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
    public void deleteProveedor(@PathParam("id") Long id) {
        ProveedorEntity entity = proveedorLogic.getProveedor(id);
        if (entity == null) {
            throw new WebApplicationException("El proveedor no existe", 404);
        }
        proveedorLogic.deleteProveedor(id);
    }

    /**
     * <h1>GET /{proveedoresId}/horarios/ : Obtener todos los horarios de un proveedor.</h1>
     *
     * <pre>Busca y devuelve todos los horarios que existen en el proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los horarios del proveedor.</code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSONArray {@link HorarioDetailDTO} - Los horarios encontrados
     * en el proveedor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{proveedoresId: \\d+}/horarios")
    public List<HorarioDetailDTO> listHorarios(@PathParam("proveedoresId") Long proveedoresId) {
        return horariosListEntity2DTO(proveedorLogic.getHorarios(proveedoresId));
    }

    /**
     * <h1>PUT /{proveedoresId}/horarios: Edita loshorarios de un proveedor..</h1>
     * <pre> Remplaza las instancias de Horario asociadas a una instancia de Editorial
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los horarios del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param horarios JSONArray {@link HorarioDetailDTO} El arreglo de
     * horarios nuevo para la proveedor.
     * @return JSON {@link HorarioDetailDTO} - El arreglo de horarios guardado
     * en la proveedor.
     */
    @PUT
    @Path("{proveedoresId: \\d+}/horarios")
    public List<HorarioDetailDTO> replaceHorarios(@PathParam("proveedoresId") Long proveedoresId, List<HorarioDetailDTO> horarios) throws BusinessLogicException 
    {
        return horariosListEntity2DTO(proveedorLogic.replaceHorarios(proveedoresId, horariosListDTO2Entity(horarios)));
    }
    
        /**
     * <h1>POST /{proveedoresId}/horarios/{horariosId} : Guarda un
     * horario dentro del proveedor.</h1>
     *
     * <pre> Guarda un horario dentro de un proveedor con la informacion que
     * recibe el la URL. Se devuelve el horario que se guarda en la proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo horario .
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param horarioId Identificador del horario que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link HorarioDetailDTO} - El horario guardado en la
     * proveedor.
     */
    @POST
    @Path("{proveedoresId: \\d+}/horarios/{horariosId: \\d+}")
    public HorarioDetailDTO addHorario(@PathParam("proveedoresId") Long proveedoresId, @PathParam("horariosId") Long horarioId) throws BusinessLogicException {
        return new HorarioDetailDTO(proveedorLogic.addHorario(horarioId, proveedoresId));
    }

        /**
     * <h1>DELETE /{proveedoresId}/horarios/{horariosId} : Elimina un
     * horario dentro del proveedor.</h1>
     *
     * <pre> Elimina la referencia del horario asociado al ID dentro del proveedor
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del horario.
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param horariosId Identificador del horario que se desea guardar. Este
     * debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{proveedoresId: \\d+}/horarios/{horariosId: \\d+}")
    public void removeHorarios(@PathParam("proveedoresId") Long proveedoresId, @PathParam("horariosId") Long horariosId) throws BusinessLogicException {
        proveedorLogic.removeHorario(horariosId, proveedoresId);
    }
    
        /**
     * <h1>GET /{proveedoresId}/horarios/{horariosId} : Obtener
     * horario por id del proveedor por id.</h1>
     *
     * <pre>Busca el horario con el id asociado dentro del proveedor con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el horario correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un horario con el id dado dentro del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param horariosId Identificador del horario que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link HorarioDetailDTO} - El horario buscado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la proveedor o el
     * horario.
     */
    @GET
    @Path("{proveedoresId: \\d+}/horarios/{horariosId: \\d+}")
    public HorarioDetailDTO getHorario(@PathParam("proveedoresId") Long proveedoresId, @PathParam("horariosId") Long horariosId) throws BusinessLogicException {
        return new HorarioDetailDTO(proveedorLogic.getHorario(proveedoresId, horariosId));
    }
    
    /**
     * Convierte una lista de HorarioEntity a una lista de HorarioDetailDTO.
     *
     * @param entityList Lista de HorarioEntity a convertir.
     * @return Lista de HorarioDetailDTO convertida.
     *
     */
    private List<HorarioDetailDTO> horariosListEntity2DTO(List<HorarioEntity> entityList) {
        List<HorarioDetailDTO> list = new ArrayList<>();
        for (HorarioEntity entity : entityList) {
            list.add(new HorarioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de HorarioDetailDTO a una lista de HorarioEntity.
     *
     * @param dtos Lista de HorarioDetailDTO a convertir.
     * @return Lista de HorarioEntity convertida.
     *
     */
    private List<HorarioEntity> horariosListDTO2Entity(List<HorarioDetailDTO> dtos) {
        List<HorarioEntity> list = new ArrayList<>();
        for (HorarioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /////////////////////////////////////
    
    
    /**
     * <h1>GET /{proveedoresId}/contratos/ : Obtener todos los contratos de un proveedor.</h1>
     *
     * <pre>Busca y devuelve todos los contratos que existen en el proveedor.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los contratos del proveedor.</code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSONArray {@link ContratoDetailDTO} - Los contratos encontrados
     * en el proveedor. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    @Path("{proveedoresId: \\d+}/contratos")
    public List<ContratoDetailDTO> listContratos(@PathParam("proveedoresId") Long proveedoresId) {
        return contratosListEntity2DTO(proveedorLogic.getContratos(proveedoresId));
    }

    /**
     * <h1>PUT /{proveedoresId}/contratos: Edita loscontratos de un proveedor..</h1>
     * <pre> Remplaza las instancias de Contrato asociadas a una instancia de Editorial
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los contratos del proveedor.
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratos JSONArray {@link ContratoDetailDTO} El arreglo de
     * contratos nuevo para la proveedor.
     * @return JSON {@link ContratoDetailDTO} - El arreglo de contratos guardado
     * en la proveedor.
     */
    @PUT
    @Path("{proveedoresId: \\d+}/contratos")
    public List<ContratoDetailDTO> replaceContratos(@PathParam("proveedoresId") Long proveedoresId, List<ContratoDetailDTO> contratos) throws BusinessLogicException 
    {
        return contratosListEntity2DTO(proveedorLogic.replaceContratos(proveedoresId, contratosListDTO2Entity(contratos)));
    }
    
        /**
     * <h1>POST /{proveedoresId}/contratos/{contratosId} : Guarda un
     * contrato dentro del proveedor.</h1>
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
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratoId Identificador del contrato que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato guardado en la
     * proveedor.
     */
    @POST
    @Path("{proveedoresId: \\d+}/contratos/{contratosId: \\d+}")
    public ContratoDetailDTO addContrato(@PathParam("proveedoresId") Long proveedoresId, @PathParam("contratosId") Long contratoId) throws BusinessLogicException {
        return new ContratoDetailDTO(proveedorLogic.addContrato(contratoId, proveedoresId));
    }

        /**
     * <h1>DELETE /{proveedoresId}/contratos/{contratosId} : Elimina un
     * contrato dentro del proveedor.</h1>
     *
     * <pre> Elimina la referencia del contrato asociado al ID dentro del proveedor
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del contrato.
     * </code>
     * </pre>
     *
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratosId Identificador del contrato que se desea guardar. Este
     * debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{proveedoresId: \\d+}/contratos/{contratosId: \\d+}")
    public void removeContratos(@PathParam("proveedoresId") Long proveedoresId, @PathParam("contratosId") Long contratosId) throws BusinessLogicException {
        proveedorLogic.removeContrato(contratosId, proveedoresId);
    }
    
        /**
     * <h1>GET /{proveedoresId}/contratos/{contratosId} : Obtener
     * contrato por id del proveedor por id.</h1>
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
     * @param proveedoresId Identificador del proveedor que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratosId Identificador del contrato que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato buscado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la proveedor o el
     * contrato.
     */
    @GET
    @Path("{proveedoresId: \\d+}/contratos/{contratosId: \\d+}")
    public ContratoDetailDTO getContrato(@PathParam("proveedoresId") Long proveedoresId, @PathParam("contratosId") Long contratosId) throws BusinessLogicException {
        return new ContratoDetailDTO(proveedorLogic.getContrato(proveedoresId, contratosId));
    }
    
    /**
     * Convierte una lista de ContratoEntity a una lista de ContratoDetailDTO.
     *
     * @param entityList Lista de ContratoEntity a convertir.
     * @return Lista de ContratoDetailDTO convertida.
     *
     */
    private List<ContratoDetailDTO> contratosListEntity2DTO(List<ContratoEntity> entityList) {
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
    private List<ContratoEntity> contratosListDTO2Entity(List<ContratoDetailDTO> dtos) {
        List<ContratoEntity> list = new ArrayList<>();
        for (ContratoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
