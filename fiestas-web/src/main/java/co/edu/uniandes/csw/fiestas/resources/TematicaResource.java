/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import co.edu.uniandes.csw.fiestas.dtos.*;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * <pre>Clase que implementa el recurso "tematica".
 * URL: /api/tematica
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "tematica".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * </pre>
 * @author af.losada
 */
 @Path("tematicas")
 @Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TematicaResource 
{
    
    public TematicaResource(){
       
    }
   
    /**
    * <h1>GET /tematica : Obtener todos las tematicas.</h1>
    * 
    * <pre>Busca y devuelve todos las tematica que existen en la aplicacion.
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK Devuelve todas los productos de la aplicacion.</code> 
    * </pre>
    * @param lista una lista de todas las tematicas
    * @return JSONArray {@link TematicaDetailDTO} - Las tematicas encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
    */ 
    @GET
    public List<TematicaDetailDTO> getTematica(List<TematicaDetailDTO> lista){
      return lista;
    }
   
    /**
    * <h1>GET /tematica/{id} : Obtener tematica por id.</h1>
    * 
    * <pre>Busca la tematica con el id asociado recibido en la URL y lo devuelve.
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK Devuelve el tematica correspondiente al id.
    * </code> 
    * <code style="color: #c7254e; background-color: #f9f2f4;">
    * 404 Not Found No existe una tematica con el id dado.
    * </code> 
    * </pre>
    * @param id Identificador del tematica que se esta buscando. Este debe ser una cadena de dígitos.
    * @return JSON {@link TematicaDetailDTO} - La temática buscada
    */
    @GET
    @Path("{id: \\d+}")
    public TematicaDetailDTO getTematica(@PathParam("id") Long id){
      return null;
    }
   
    /**
    * <h1>POST /tematica : Crear una tematica.</h1>
    * 
    * <pre>Cuerpo de petición: JSON {@link TematicaDetailDTO}.
    * 
    * Crea una nueva con la informacion que se recibe en el cuerpo 
    * de la petición y se regresa un objeto identico con un id auto-generado 
    * por la base de datos.
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK Creó la nueva tematica.
    * </code>
    * <code style="color: #c7254e; background-color: #f9f2f4;">
    * 412 Precodition Failed: Ya existe la tematica.
    * </code>
    * </pre>
    * @param tematica {@link TematicaDetailDTO} - La tematica que se desea guardar.
    * @return JSON {@link TematicaDetailDTO}  - La tematica guardado con el atributo id autogenerado.
    * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la evento.
    */ 
    @POST 
    public TematicaDetailDTO createTematica(TematicaDetailDTO tematica) throws BusinessLogicException{
        return tematica;
    }
    
    /**
    * <h1>PUT /tematica/{id} : Actualizar tematica por id.</h1>
    * 
    * <pre>Busca la tematica con el id asociado recibido en la URL, actualiza los paramteros
    * y la devuelve.
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK Devuelve la tematica correspondiente al id, despues de actualizado.
    * </code> 
    * <code style="color: #c7254e; background-color: #f9f2f4;">
    * 404 Not Found No existe una tematica con el id dado.
    * </code> 
    * </pre>
    * @param id Identificador de la tematica que se esta buscando. Este debe ser una cadena de dígitos.
    * @return JSON {@link TematicaDetailDTO} - La tematica buscada y actuaizada
    */
    @PUT 
    @Path("{id: \\d+}")
    public TematicaDetailDTO updateTematica(@PathParam("id") Long id){
        return null;
    }
    
    /**
    * <h1>DELETE /tematica/{id} : Elimina una tematica por id.</h1>
    * 
    * <pre>Busca la tematica con el id asociado recibido en la URL y lo elimina
    * 
    * Codigos de respuesta:
    * <code style="color: mediumseagreen; background-color: #eaffe0;">
    * 200 OK La tematica fue eliminada exitosamente
    * </code> 
    * <code style="color: #c7254e; background-color: #f9f2f4;">
    * 404 Not Found No existe una tematica con el id dado.
    * </code> 
    * </pre>
    * @param id Identificador de la tematica que se esta buscando. Este debe ser una cadena de dígitos.
    */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteTematica(@PathParam("id") Long id){
    }
}
