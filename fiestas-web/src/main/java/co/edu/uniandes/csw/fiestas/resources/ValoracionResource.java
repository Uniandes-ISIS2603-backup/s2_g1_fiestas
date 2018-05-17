package co.edu.uniandes.csw.fiestas.resources;

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
     * Convierte una lista de ValoracionEntity a una lista de ValoracionDetailDTO.
     *
     * @param entityList Lista de ValoracionEntity a convertir.
     * @return Lista de ValoracionDetailDTO convertida.
     *
     */
    private List<ValoracionDetailDTO> listEntity2DTO(List<ValoracionEntity> entityList) {
        List<ValoracionDetailDTO> list = new ArrayList<>();
        for (ValoracionEntity entity : entityList) {
            list.add(new ValoracionDetailDTO(entity));
        }
        return list;
    }
    /**
     * <h1>POST /valoraciones : Crear un valoracion.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link ValoracionDetailDTO}.
     *
     * Crea una nueva valoracion con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó la nueva valoracion.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe la valoracion.
     * </code>
     * </pre>
     *
     * @param valoracion {@link ValoracionDetailDTO} - La valoracion que se
     * desea guardar.
     * @return JSON {@link ValoracionDetailDTO} - la valoracion guardada con el
     * atributo id autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica que se genera cuando ya existe la valoracion.
     */
    @POST
    public ValoracionDetailDTO createValoracion(ValoracionDetailDTO valoracion) throws BusinessLogicException {
        return new ValoracionDetailDTO(valoracionLogic.createValoracion(valoracion.toEntity()));
    }

    /**
     * <h1>GET /valoraciones : Obtener todas los valoraciones.</h1>
     *
     * <pre>Busca y devuelve todas las valoraciones que existen en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas las valoraciones de la aplicacion.</code>
     * </pre>
     *
     * @return JSONArray {@link ValoracionDetailDTO} - Los valoraciones
     * encontradas en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ValoracionDetailDTO> getValoraciones() {
        return listEntity2DTO(valoracionLogic.getValoraciones());
    }

    /**
     * <h1>GET /valoraciones/{id} : Obtener valoracion por id.</h1>
     *
     * <pre>Busca la valoracion con el id asociado recibido en la URL y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve la valoracion correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe una valoracion con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador de la valoracion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ValoracionDetailDTO} - La valoracion buscado
     * @throws BusinessLogicException{@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} No existe la valoracion
     */
    @GET
    @Path("{id: \\d+}")
    public ValoracionDetailDTO getValoracion(@PathParam("id") Long id) throws BusinessLogicException {
        try
        {
            ValoracionEntity entity=valoracionLogic.getValoracion(id);
            return new ValoracionDetailDTO(entity);
        }
        
        catch (Exception e) {
            throw new WebApplicationException(e.getMessage(), 404);
        }
        

    }

    /**
     * <h1>PUT /valoraciones/{id} : Actualizar valoracion por id.</h1>
     *
     * <pre>Busca la valoracion con el id asociado recibido en la URL, actualiza los paramteros
     * y la devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve la valoracion correspondiente al id, despues de actualizado.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe una valoracion con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador de la valoracion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param valoracion a actualizar
     * @return JSON {@link ValoracionDetailDTO} - La valoracion buscada y
     * actuaizada
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica
     */
    @PUT
    @Path("{id: \\d+}")
    public ValoracionDetailDTO updateValoracion(@PathParam("id") Long id, ValoracionDetailDTO valoracion) throws BusinessLogicException {
        ValoracionEntity entity = valoracion.toEntity();
        entity.setId(id);
        try{
            valoracionLogic.getValoracion(id);
        }
        catch(Exception e){
            throw new WebApplicationException(e.getMessage(),404);
        }
        return new ValoracionDetailDTO(valoracionLogic.updateValoracion(entity));
    }

    /**
     * <h1>DELETE /valoraciones/{id} : Elimina una valoracion por id.</h1>
     *
     * <pre>Busca la valoracion con el id asociado recibido en la URL y lo elimina
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK La valoracion fue eliminada exitosamente
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe una valoracion con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador de la valoracion que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @throws BusinessLogicException{@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} No existe la valoracion
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteValoracion(@PathParam("id") Long id) throws BusinessLogicException {
        ValoracionEntity entity=valoracionLogic.getValoracion(id);
        if (entity == null) {
            throw new WebApplicationException("La valoracion no existe", 404);
        }
        valoracionLogic.deleteValoracion(id);
    }

}