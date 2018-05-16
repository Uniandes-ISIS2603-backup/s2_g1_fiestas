package co.edu.uniandes.csw.fiestas.entities;

import javax.persistence.Entity;

/**
 *
 * @author df.nino10
 */
@Entity
public class UsuarioEntity extends BaseEntity
{
    private String nombre;
    private String rol;
    private String login;
    private String contrasena;
    private long token;  
    
    /**
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre del usuario a asignar
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) 
    {
        this.login = login;
    }

    /**
     * @return the contraseña
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contraseña to set
     */
    public void setContrasena(String contrasena) 
    {
        this.contrasena = contrasena;
    }  

    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the token
     */
    public long getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(Long token) {
        this.token = token;
    }    
}
