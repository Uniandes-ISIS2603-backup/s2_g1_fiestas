package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ValoracionDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.ValoracionLogic;
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
 * <pre>Clase que implementa el recurso "valoraciones".
 * URL: /api/valoraciones
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta
 * "/api" y este recurso tiene la ruta "valoraciones".</i>
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
@Path("valoraciones")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ValoracionResource {

    @Inject
    ValoracionLogic valoracionLogic;

    /**
     * <h1>GET /api/servicios/{idServicio}/valoraciones : Obtener todas las valoraciones de un servicio.</h1>
     *
     * <pre>Busca y devuelve todas las valoraciones que existen en un servicio.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas las valoraciones del servicio.</code> 
     * </pre>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un servicio con el id dado.
     * </code>
     * @param idServicio El ID del servicio del cual se buscan las valoraciones
     * @return JSONArray {@link ValoracionDTO} - Las valoraciones encontradas en el servicio. Si no hay ninguna retorna una lista vacía.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se encuentra el servicio.
     */
    @GET
    public List<ValoracionDTO> getValoracions(@PathParam("idServicio") Long idServicio) throws BusinessLogicException {
        return listEntity2DTO(valoracionLogic.getValoraciones(idServicio));
    }

    /**
     * <h1>GET /api/servicios/{idServicio}/valoraciones/{id} : Obtener una valoracion de un servicio.</h1>
     *
     * <pre>Busca y devuelve la valoracion con el ID recibido en la URL, relativa a un
     * servicio.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve la valoracion del servicio.</code> 
     * </pre>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un servicio con el id dado.
     * </code>
     * @param idServicio El ID del servicio del cual se buscan las valoraciones
     * @param id El ID de la valoracion que se busca
     * @return {@link ValoracionDTO} - La valoracion encontradas en el servicio.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se encuentra el servicio.
     */
    @GET
    @Path("{id: \\d+}")
    public ValoracionDTO getValoracion(@PathParam("idServicio") Long idServicio, @PathParam("id") Long id) throws BusinessLogicException {
        ValoracionEntity entity = valoracionLogic.getValoracion(idServicio, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /servicios/" + idServicio + "/valoraciones/" + id + " no existe.", 404);
        }
        return new ValoracionDTO(entity);
    }

    /**
     * <h1>POST /api/servicios/{idServicio}/valoraciones : Crear una valoracion de un servicio.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link ValoracionDTO}.
     * 
     * Crea una nueva valoracion con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó la nueva valoracion .
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe la valoracion.
     * </code>
     * </pre>
     * @param idServicio El ID del servicio del cual se guarda la valoracion
     * @param valoracion {@link ValoracionDTO} - La valoracion que se desea guardar.
     * @return JSON {@link ValoracionDTO}  - La valoracion guardada con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la valoracion.
     */
    @POST
    public ValoracionDTO createValoracion(@PathParam("idServicio") Long idServicio, ValoracionDTO valoracion) throws BusinessLogicException {
        return new ValoracionDTO(valoracionLogic.createValoracion(idServicio, valoracion.toEntity()));
    }

    /**
     * <h1>PUT /api/servicios/{idServicio}/valoraciones/{id} : Actualizar una valoracion de un servicio.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link ValoracionDTO}.
     * 
     * Actualiza una valoracion con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa el objeto actualizado.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se actualizó la valoracion
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: No se pudo actualizar la valoracion
     * </code>
     * </pre>
     * @param idServicio El ID del servicio del cual se guarda la valoracion
     * @param id El ID de la valoracion que se va a actualizar
     * @param valoracion {@link ValoracionDTO} - La valoracion que se desea guardar.
     * @return JSON {@link ValoracionDTO}  - La valoracion actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la valoracion.
     */
    @PUT
    @Path("{id: \\d+}")
    public ValoracionDTO updateValoracion(@PathParam("idServicio") Long idServicio, @PathParam("id") Long id, ValoracionDTO valoracion) throws BusinessLogicException {
        valoracion.setId(id);
        ValoracionEntity entity = valoracionLogic.getValoracion(idServicio, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /servicios/" + idServicio + "/valoraciones/" + id + " no existe.", 404);
        }
        return new ValoracionDTO(valoracionLogic.updateValoracion(idServicio, valoracion.toEntity()));

    }

    /**
     * <h1>DELETE /api/servicios/{idServicio}/valoraciones/{id} : Borrar valoracion por id.</h1>
     *
     * <pre>Borra la valoracion con el id asociado recibido en la URL.
     *
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina la valoracion correspondiente al id dado dentro del servicio.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe una valoracion con el id dado en el servicio.
     * </code>
     * </pre>
     * @param idServicio El ID del servicio del cual se va a eliminar la valoracion.
     * @param id El ID de la valoracion que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando no se puede eliminar la valoracion.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteValoracion(@PathParam("idServicio") Long idServicio, @PathParam("id") Long id) throws BusinessLogicException {
        ValoracionEntity entity = valoracionLogic.getValoracion(idServicio, id);
        if (entity == null) {
            throw new WebApplicationException("El recurso /servicios/" + idServicio + "/valoraciones/" + id + " no existe.", 404);
        }
        valoracionLogic.deleteValoracion(idServicio, id);
    }

    private List<ValoracionDTO> listEntity2DTO(List<ValoracionEntity> entityList) {
        List<ValoracionDTO> list = new ArrayList<>();
        for (ValoracionEntity entity : entityList) {
            list.add(new ValoracionDTO(entity));
        }
        return list;
    }
}
