/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.BonoDetailDTO;
import co.edu.uniandes.csw.fiestas.ejb.BonoLogic;
import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author df.nino10
 */
@Path("/bonos")
@Produces("application/json")

public class BonoResource {
    @Inject
    private BonoLogic logic;


        /**
     * <h1>GET /bonos : Obtener todos los bonos.</h1>
     *
     * <pre>Busca y devuelve todos los bonos que existen en la aplicacion.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los bonos de la aplicacion.</code>
     * </pre>
     *
     * @return JSONArray {@link BonoDetailDTO} - Los bonos encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<BonoDetailDTO> getBonos() 
    {
        return listEntity2DTO(logic.getBonos());
    }
    /**
     * <h1>GET /bono/ : Obtener bono por id.</h1>
     * 
     * <pre>Busca el bono con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el bono correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un bono con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del bono que se esta buscando. Este debe ser una cadena de dígitos.
     * @param eventoId
     * @return JSON {@link BonoDetailDTO} - El bono buscado
     */
    @GET
    @Path("{id: \\d+}")
    public BonoDetailDTO getBono(@PathParam("id") Long id) {
        BonoEntity bE=logic.getBono(id);
        if(bE==null)
            throw new WebApplicationException("El bono no existe", 404);
        return new BonoDetailDTO(bE);
    }
    
    /**
     * <h1>POST /bonos : Crear un bono.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link BonoDetailDTO}.
     * 
     * Crea un nuevo bono con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo Bono.
     * </code>S
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el Bono.
     * </code>
     * </pre>
     * @param bono {@link BonoDetailDTO} - El bono que se desea guardar.
     * @return JSON {@link BonoDetailDTO}  - El bono guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe el bono.
     */
    @POST
    public BonoDetailDTO createBono(BonoDetailDTO bono) throws BusinessLogicException {
        return new BonoDetailDTO(logic.createBono(bono.toEntity()));
    }
    
    /**
     * <h1>PUT /api/bonos/{id} : Actualizar autor con el id dado.</h1>
     * <pre>Cuerpo de petición: JSON {@link AuthorDetailDTO}.
     *
     * Actualiza el autor con el id recibido en la URL con la información que se recibe en el cuerpo de la petición.
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Actualiza el autor con el id dado con la información enviada como parámetro. Retorna un objeto identico.</code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un autor con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del autor que se desea actualizar. Este debe ser una cadena de dígitos.
     * @param author {@link AuthorDetailDTO} El autor que se desea guardar.
     * @return JSON {@link AuthorDetailDTO} - El autor guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} - Error de lógica que se genera cuando no se encuentra el libro a actualizar.
     */
    @PUT
    @Path("{id: \\d+}")
    public BonoDetailDTO updateBono(@PathParam("id") Long id, BonoDetailDTO bono) {
        BonoEntity entity = bono.toEntity();
        entity.setId(id);
        BonoEntity oldEntity = logic.getBono(id);
        if (oldEntity == null) {
            throw new WebApplicationException("El bono no existe", 404);
        }
        if(entity.getProveedor()==null)entity.setProveedor(oldEntity.getProveedor());
        if(entity.getCodigo()==null)entity.setCodigo(oldEntity.getCodigo());
        try{
        return new BonoDetailDTO(logic.updateBono(entity));
        }
        catch(BusinessLogicException e){
            throw new WebApplicationException(e.getMessage(), 412);
        }
    }
    /**
     * <h1>DELETE /bonos/{id} : Elimina un bono por id.</h1>
     *
     * <pre>Busca el bono con el id asociado recibido en la URL y lo elimina
     *
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK El bono fue eliminado exitosamente
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un bono con el id dado.
     * </code>
     * </pre>
     *
     * @param id Identificador del bono que se esta buscando. Este debe ser
     * una cadena de dígitos.
     */
    @DELETE
    @Path("{id:\\d+}")
    public void deleteBono(@PathParam("id")Long id){
        BonoEntity entity = logic.getBono(id);
        if (entity == null) {
            throw new WebApplicationException("El bono no existe", 404);
        }
       logic.deleteBono(id);
    }
    
    /**
     * Convierte una lista de BonoEntity a una lista de BonoDetailDTO.
     *
     * @param entityList Lista de BonoEntity a convertir.
     * @return Lista de BonoDetailDTO convertida.
     * 
     */
    private List<BonoDetailDTO> listEntity2DTO(List<BonoEntity> entityList) {
        List<BonoDetailDTO> list = new ArrayList<>();
        for (BonoEntity entity : entityList) {
            list.add(new BonoDetailDTO(entity));
        }
        return list;
    }
    
}
