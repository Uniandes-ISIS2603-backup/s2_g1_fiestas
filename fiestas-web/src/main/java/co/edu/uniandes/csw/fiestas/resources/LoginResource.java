package co.edu.uniandes.csw.fiestas.resources;
import co.edu.uniandes.csw.fiestas.dtos.UsuarioDTO;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class LoginResource {
  
    /**
     * <h1>POST /login : Hace el inicio de sesión de un usuario.</h1>
     *
     * <pre>Cuerpo de petición: JSON {@link UserDTO}.
     * 
     * Se inicia la sesión del usuario que se recibe en el cuerpo de la petición.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Se inició sesión.
     * </code>
     * </pre>
     * @param dto {@link UsusarioDTO} - El usuario que intenta acceder.
     * @return JSON {@link UsuarioDTO} - El usuario que inicia la sesión.
     */
    @POST
    public UsuarioDTO login(UsuarioDTO dto) {
        return dto;
    }

}