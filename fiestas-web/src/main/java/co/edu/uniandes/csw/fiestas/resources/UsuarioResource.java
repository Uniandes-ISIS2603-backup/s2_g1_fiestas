package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.UsuarioDTO;
import co.edu.uniandes.csw.fiestas.ejb.UsuarioLogic;
import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
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
 * <pre>Clase que implementa el recurso "usuarios".
 * URL: /api/usuarios
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "usuarios.</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author nm.hernandez10
 */

@Path("usuarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {
    @Inject
    private UsuarioLogic logic;

    /**
     * <h1>GET /usuarios : Obtener todos los usuarios.</h1>
     * 
     * <pre>Busca y devuelve todos los usuarios que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los usuarios de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link UsuarioDetailDTO} - Los usuarios encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<UsuarioDTO> getUsuarios() {
        return listEntity2DTO(logic.getUsuarios());
    }

    
     /**
     * Convierte una lista de HoraroiEntity a una lista de UsuarioDetailDTO.
     *
     * @param entityList Lista de UsuarioEntity a convertir.
     * @return Lista de UsuarioDetailDTO convertida.
     *
     */
    private List<UsuarioDTO> listEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDTO(entity));
        }
        return list;
    }
    /**
     * <h1>GET /usuarios/{id} : Obtener usuario por id.</h1>
     * 
     * <pre>Busca el usuario con el id asociado recibido en la URL y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el usuario correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un usuario con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del usuario que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link UsuarioDetailDTO} - El usuario buscado
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica si no existe el usuario
     */
    @GET
    @Path("{login: \\.+}")
    public UsuarioDTO getUsuario(@PathParam("login") String login) throws BusinessLogicException{
        UsuarioEntity e = logic.getUsuario(login);
        if(e == null)
        {
            throw new BusinessLogicException("El usuario con el id buscado no existe.");
        }
        return new UsuarioDTO(e);
    }
}
