/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ContratoDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.EventoLogic;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * <pre>Clase que implementa el recurso "eventos/{id}/contratos".
 * URL: /api/eventos/{eventosId}/contratos
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta
 * "/api" y este recurso tiene la ruta "eventos/{eventosId}/contratos".</i>
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
@Path("eventos/{eventosId: \\d+}/contratos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventoContratosResource {

    @Inject
    private EventoLogic eventoLogic;

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

    /**
     * <h1>GET /api/eventos/{eventosId}/contratos : Obtener todos los contratos
     * de un evento.</h1>
     *
     * <pre>Busca y devuelve todos los contratos que existen en el evento.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todos los contratos del evento.</code>
     * </pre>
     *
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSONArray {@link ContratoDetailDTO} - Los contratos encontrados
     * en el evento. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ContratoDetailDTO> listContratos(@PathParam("eventosId") Long eventosId) {
        return contratosListEntity2DTO(eventoLogic.getContratos(eventosId));
    }

    /**
     * <h1>GET /api/eventos/{eventosId}/contratos/{contratosId} : Obtener
     * contrato por id del evento por id.</h1>
     *
     * <pre>Busca el contrato con el id asociado dentro del evento con id asociado.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el contrato correspondiente al id.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un contrato con el id dado dentro del evento.
     * </code>
     * </pre>
     *
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratosId Identificador del contrato que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato buscado
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la evento o el
     * contrato.
     */
    @GET
    @Path("{contratosId: \\d+}")
    public ContratoDetailDTO getContratos(@PathParam("eventosId") Long eventosId, @PathParam("contratosId") Long contratosId) throws BusinessLogicException {
        return new ContratoDetailDTO(eventoLogic.getContrato(eventosId, contratosId));
    }

    /**
     * <h1>POST /api/eventos/{eventosId}/contratos/{contratosId} : Guarda un
     * contrato dentro del evento.</h1>
     *
     * <pre> Guarda un contrato dentro de un evento con la informacion que
     * recibe el la URL. Se devuelve el contrato que se guarda en la evento.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó el nuevo contrato .
     * </code>
     * </pre>
     *
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratosId Identificador del contrato que se desea guardar. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ContratoDetailDTO} - El contrato guardado en la
     * evento.
     */
    @POST
    @Path("{contratosId: \\d+}")
    public ContratoDetailDTO addContratos(@PathParam("eventosId") Long eventosId, @PathParam("contratosId") Long contratosId) {
        return new ContratoDetailDTO(eventoLogic.addContrato(contratosId, eventosId));
    }

    /**
     * <h1>PUT /api/eventos/{eventosId}/contratos/{contratosId} : Edita los
     * contratos de un evento..</h1>
     * una evento
     * <pre> Remplaza las instancias de Contrato asociadas a una instancia de Editorial
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Guardó los contratos del evento.
     * </code>
     * </pre>
     *
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratos JSONArray {@link ContratoDetailDTO} El arreglo de
     * contratos nuevo para la evento.
     * @return JSON {@link ContratoDetailDTO} - El arreglo de contratos guardado
     * en la evento.
     */
    @PUT
    public List<ContratoDetailDTO> replaceContratos(@PathParam("eventosId") Long eventosId, List<ContratoDetailDTO> contratos) {
        return contratosListEntity2DTO(eventoLogic.replaceContratos(eventosId, contratosListDTO2Entity(contratos)));
    }

    /**
     * <h1>DELETE /api/eventos/{eventosId}/contratos/{contratosId} : Elimina un
     * contrato dentro del evento.</h1>
     *
     * <pre> Elimina la referencia del contrato asociado al ID dentro del evento
     * con la informacion que recibe el la URL.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se eliminó la referencia del contrato.
     * </code>
     * </pre>
     *
     * @param eventosId Identificador del evento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @param contratosId Identificador del contrato que se desea guardar. Este
     * debe ser una cadena de dígitos.
     */
    @DELETE
    @Path("{contratosId: \\d+}")
    public void removeContratos(@PathParam("eventosId") Long eventosId, @PathParam("contratosId") Long contratosId) {
        eventoLogic.removeContrato(contratosId, eventosId);
    }
}
