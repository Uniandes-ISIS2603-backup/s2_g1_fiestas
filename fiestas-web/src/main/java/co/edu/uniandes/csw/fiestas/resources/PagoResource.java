package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.ejb.PagoLogic;
import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import co.edu.uniandes.csw.fiestas.dtos.PagoDTO;
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
@Path("eventos/{idEvento: \\d+}/pagos")
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
     * @return Lista de PagoDTO convertida.
     *
     */
    private List<PagoDTO> listEntity2DTO(List<PagoEntity> entityList) {
        List<PagoDTO> list = new ArrayList<>();
        for (PagoEntity entity : entityList) {
            list.add(new PagoDTO(entity));
        }
        return list;
    }

    /**
     * <h1>GET api/eventos/{idEvento}/pagos : Obtener todos los pagos de un
     * evento.</h1>
     *
     * <pre>Busca y devuelve todos los pagos que existen en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los pagos de la aplicacion.</code>
     * </pre>
     *
     * @param idEvento El ID del evento del cual se buscan los pago
     * @return JSONArray {@link PagoDTO} - Los clientes encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PagoDTO> getPagos(@PathParam("idEvento") Long idEvento){
        return listEntity2DTO(pagoLogic.getPagos(idEvento));
    }

    /**
     * <h1>GET api/eventos/{idEvento}/pagos/{id} : Obtener pago por id.</h1>
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
     * @param idEvento El ID del evento del cual se buscan los pago
     * @return JSON {@link PagoDTO} - El pago buscado
     */
    @GET
    @Path("{id: \\d+}")
    public PagoDTO getPago(@PathParam("idEvento") Long idEvento, @PathParam("id") Long id) {
        PagoEntity entity = pagoLogic.getPago(idEvento, id);
        if (entity == null) {
            throw new WebApplicationException("El pago no existe", 404);
        }
        return new PagoDTO(entity);
    }

    /**
     * <h1>POST api/eventos/{idEvento}/pagos : Crear un pago.</h1>
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
     * @param idEvento El ID del evento del cual se buscan los pago
     * @param pago {@link PagoDTO} - El pago que se desea guardar.
     * @return JSON {@link PagoDTO} - El pago guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica
     */
    @POST
    public PagoDTO createPago(@PathParam("idEvento") Long idEvento, PagoDTO pago) throws BusinessLogicException {
        return new PagoDTO(pagoLogic.createPago(idEvento, pago.toEntity()));
    }

    /**
     * <h1>PUT api/eventos/{idEvento}/pagos/{id} : Actualizar pago por id.</h1>
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
     * @param idEvento El ID del evento del cual se buscan los pago
     * @param id Identificador del pago que se esta buscando. Este debe ser una
     * cadena de dígitos.
     * @param pago pago a actualizar
     * @return JSON {@link PagoDTO} - El pago buscado y actuaizado
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica.
     */
    @PUT
    @Path("{id: \\d+}")
    public PagoDTO updatePago(@PathParam("idEvento") Long idEvento, @PathParam("id") Long id, PagoDTO pago) throws BusinessLogicException {
        pago.setId(id);
        PagoEntity oldEntity = pagoLogic.getPago(idEvento, id);
        if (oldEntity == null) {
            throw new WebApplicationException("El pago no existe", 404);
        }
        return new PagoDTO(pagoLogic.updatePago(idEvento, pago.toEntity()));
    }

    /**
     * <h1>DELETE api/eventos/{idEvento}/pagos/{id} : Elimina un pago de un
     * evento por id.</h1>
     *
     * <pre>Busca el pago con el id y evento asociado recibido en la URL y lo elimina
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
     * @param idEvento El ID del evento del cual se buscan los pago
     * @param id Identificador del pago que se esta buscando. Este debe ser una
     * cadena de dígitos.
     * @throws BusinessLogicException
     * {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper}
     * - Error de lógica.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deletePago(@PathParam("idEvento") Long idEvento, @PathParam("id") Long id) throws BusinessLogicException {
        PagoEntity entity = pagoLogic.getPago(idEvento, id);
        if (entity == null) {
            throw new WebApplicationException("El pago no existe", 404);
        }
        pagoLogic.deletePago(idEvento, id);
    }
}
