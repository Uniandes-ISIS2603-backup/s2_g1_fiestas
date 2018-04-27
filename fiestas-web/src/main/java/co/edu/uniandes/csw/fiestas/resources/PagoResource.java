package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.ejb.PagoLogic;
import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import co.edu.uniandes.csw.fiestas.dtos.PagoDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * <pre>Clase que implementa el recurso "pagos".
 * URL: /api/eventos/{eventosId: \\d+}/pagos
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta
 * "/api" y este recurso tiene la ruta "pagos.</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio).
 * </pre>
 *
 * @author cm.amaya10
 */
@Path("pagos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PagoResource {

    @Inject
    private PagoLogic pagoLogic;

    /**
     * Convierte una lista de PagoEntity a una lista de PagoDetailDTO.
     *
     * @param entityList Lista de PagoEntity a convertir.
     * @return Lista de PagoDetailDTO convertida.
     *
     */
    private List<PagoDetailDTO> listEntity2DTO(List<PagoEntity> entityList) {
        List<PagoDetailDTO> list = new ArrayList<>();
        for (PagoEntity entity : entityList) {
            list.add(new PagoDetailDTO(entity));
        }
        return list;
    }

    /**
     * <h1>GET /pagos : Obtener todos los pagos.</h1>
     *
     * <pre>Busca y devuelve todos los pagos que existen en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los pagos de la aplicacion.</code>
     * </pre>
     *
     * @return JSONArray {@link PagoDetailDTO} - Los clientes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PagoDetailDTO> getPagos() {
        return listEntity2DTO(pagoLogic.getPagos());
    }

    /**
     * <h1>GET /pagos/{id} : Obtener pago por id.</h1>
     *
     * <pre>Busca el pago con el id asociado recibido en la URL y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el pago correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un pago con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del pago que se esta buscando. Este debe ser una
     * cadena de dígitos.
     * @return JSON {@link PagoDetailDTO} - El pago buscado
     */
    @GET
    @Path("{id: \\d+}")
    public PagoDetailDTO getPago(@PathParam("id") Long id) {
        PagoEntity entity = pagoLogic.getPago(id);
        if (entity == null) {
            throw new WebApplicationException("El pago no existe", 404);
        }
        return new PagoDetailDTO(entity);
    }

    /**
     * <h1>POST /pagos : Crear un pago.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link PagoDetailDTO}.
     *
     * Crea un nuevo pago con la informacion que se recibe en el cuerpo
     * de la petición y se regresa un objeto identico con un id auto-generado
     * por la base de datos.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo pago.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el pago.
     * </code>
     * </pre>
     *
     * @param pago {@link PagoDetailDTO} - El pago que se desea guardar.
     * @return JSON {@link PagoDetailDTO} - El pago guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica 
     */
    @POST
    public PagoDetailDTO createPago(PagoDetailDTO pago) throws BusinessLogicException {
        PagoEntity entity = pagoLogic.getPago(pago.getId());
        if (entity != null) {
            throw new WebApplicationException("El pago existe", 412);
        }
        return new PagoDetailDTO(pagoLogic.createPago(pago.toEntity()));
    }

    /**
     * <h1>PUT /pagos/{id} : Actualizar pago por id.</h1>
     *
     * <pre>Busca el pago con el id asociado recibido en la URL, actualiza os paramteros
     * y lo devuelve.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el pago correspondiente al id, despues de actualizado.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un pago con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del pago que se esta buscando. Este debe ser una
     * cadena de dígitos.
     * @param pago pago a actualizar
     * @return JSON {@link PagoDetailDTO} - El pago buscado y actuaizado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica.
     */
    @PUT
    @Path("{id: \\d+}")
    public PagoDetailDTO updatePago(@PathParam("id") Long id, PagoDetailDTO pago) throws BusinessLogicException {
        PagoEntity entity = pago.toEntity();
        entity.setId(id);
        PagoEntity oldEntity = pagoLogic.getPago(id);
        if (oldEntity == null) {
            throw new WebApplicationException("El pago no existe", 404);
        }
        return new PagoDetailDTO(pagoLogic.updatePago(entity));
    }

    /**
     * <h1>DELETE /pagos/{id} : Elimina un pago por id.</h1>
     *
     * <pre>Busca el pago con el id asociado recibido en la URL y lo elimina
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK El pago fue eliminado exitosamente
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un pago con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del pago que se esta buscando. Este debe ser una
     * cadena de dígitos.
     */
    @DELETE
    @Path("{id:\\d+}")
    public void deletePago(@PathParam("id") Long id) {
        PagoEntity entity = pagoLogic.getPago(id);
        if (entity == null) {
            throw new WebApplicationException("El pago no existe", 404);
        }
        pagoLogic.deletePago(id);
    }
}
