package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;

/**
 * UsuarioDTO Objeto tranferencia para los Usuarios. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "login": string
 *      "contrasena":
 *   }
 * </pre>
 * Por ejemplo un usuario se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 7,
 *      "nombre": "Nicolás Hernández",
 *      "documento": "101010101",
 *      "telefono": 3111234567,
 *      "correo": "nicolashernandez@hotmail.com",
 *      "direccion": "Calle 97 # 6-43",
 *      "login": "nm.hernandez10"
 *   }
 *

 * </pre>
 * @author nm.hernandez10
 */
public class UsuarioDTO 
{
    private Long id;
    private String nombre;
    private String rol;
    private String login;
    private String contrasena;
    private Long token;

    /**
     * Constructor por defecto
     */
    public UsuarioDTO() 
    {  
    }
    
    public UsuarioDTO(UsuarioEntity e)
    { 
        (this.contrasena)=e.getContrasena();
        
        this.login=e.getLogin();
        this.nombre=e.getNombre();
        this.rol = e.getRol();
        this.token = e.getToken();
    }
    
    /**
     * @return El id del usuario.
     */
    public Long getId() 
    {
        return id;
    }

    /**
     * @param id El nuevo id.
     */
    public void setId(Long id) 
    {
        this.id = id;
    }

    /**
     * @return La contraseña del usuario
     */
    public String getContraseña()
    {
        return getContrasena();
    }
    
    /**
     * @param contraseña La nueva contraseña
     */
    public void setContraseña(String contraseña) 
    {
        this.setContrasena(contraseña);
    }

    /**
     * @return El nombre del usuario.
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    /**
     * @return El login de teléfono del usuario.
     */
    public String getLogin() 
    {
        return login;
    }

    /**
     * @param login El nuevo login.
     */
    public void setLogin(String login) 
    {
        this.login = login;
    }
    
    
    
    /**
     * Método que transforma la clase UsuarioDTO a UsuarioEntity.
     * @return UsuarioEntity instancia.
     */
    public UsuarioEntity toEntity()
    {
        UsuarioEntity e = new UsuarioEntity(); 
        e.setContrasena(this.getContrasena());
        e.setRol(this.getRol());
        e.setToken(this.getToken());
        e.setLogin(this.login);
        e.setNombre(this.nombre);
        return e;
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
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the token
     */
    public Long getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(Long token) {
        this.token = token;
    }
}
