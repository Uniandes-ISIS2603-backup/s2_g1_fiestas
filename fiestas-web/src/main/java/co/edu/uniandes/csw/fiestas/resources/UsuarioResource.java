/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

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

import co.edu.uniandes.csw.fiestas.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("usuarios")
@Produces("application/json")

public class UsuarioResource {

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
    public List<UsuarioDetailDTO> getusuarios() {
        return new ArrayList<>();
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
     */
    @GET
    @Path("{id: \\d+}")
    public UsuarioDetailDTO getUsuario(@PathParam("id") Long id) {
        return null;
    }
    
        /**
     * <h1>POST /usuarios : Crear un usuario.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link UsuarioDetailDTO}.
     * 
     * Crea un nuevo usuario con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo usuario.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el usuario.
     * </code>
     * </pre>
     * @param usuario {@link UsuarioDetailDTO} - La ciudad que se desea guardar.
     * @return JSON {@link UsuarioDetailDTO}  - El usuario guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la ciudad.
     */
    @POST
    public UsuarioDetailDTO createUsuario(UsuarioDetailDTO usuario) throws BusinessLogicException {
        return usuario;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public UsuarioDetailDTO updateUsuario(@PathParam("id")Long id) {
        return null;
    }
    
    @DELETE
    @Path("{id:\\d+}")
    public void deleteUsuario(@PathParam("id")Long id){
        
    }
    
}
