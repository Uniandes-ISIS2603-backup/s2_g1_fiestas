package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;

/**
 * Clase que extiende de {@link UsuarioDTO} para manejar los proveedores del negocio. Para conocer el
 * contenido de un proveedor vaya a la documentacion de {@link UsuarioDTO} más un atributo extra que corresponde
 * 
 * * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "documento": string,
 *      "telefono": number,
 *      "correo": string,
 *      "direccion": string,
 *      "login": string,
 *      "penalizado": boolean
 *   }
 * </pre>
 * Por ejemplo un proveedor se representa asi:<br>
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
 *      "login": "nm.hernandez10",
 *      "penalizado": False
 *   }
 *
 * </pre>
 * @author nm.hernandez10
 */
public class ProveedorDTO
{    
    private long id;
    private String nombre;
    private String documento;
    private Long telefono;
    private String correo;
    private String direccion;
    private String login;
    private String contrasena;
    private boolean penalizado;   
    
    /**
     * @return El id del usuario.
     */
    public long getId() 
    {
        return id;
    }

    /**
     * @param id El nuevo id.
     */
    public void setId(long id) 
    {
        this.id = id;
    }

    /**
     * @return La contrasena del usuario
     */
    public String getContrasena()
    {
        return contrasena;
    }
    
    /**
     * @param contrasena La nueva contraseña
     */
    public void setContrasena(String contrasena) 
    {
        this.contrasena = contrasena;
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
     * @return El documento del usuario.
     */
    public String getDocumento()
    {
        return documento;
    }

    /**
     * @param documento El nuevo documento.
     */
    public void setDocumento(String documento)
    {
        this.documento = documento;
    }

    /**
     * @return El número de teléfono del usuario.
     */
    public Long getTelefono() 
    {
        return telefono;
    }

    /**
     * @param telefono El nuevo número de teléfono.
     */
    public void setTelefono(Long telefono) 
    {
        this.telefono = telefono;
    }

    /**
     * @return El correo del usuario.
     */
    public String getCorreo()
    {
        return correo;
    }

    /**
     * @param correo El nuevo correo.
     */
    public void setCorreo(String correo) 
    {
        this.correo = correo;
    }

    /**
     * @return La dirección del usuario.
     */
    public String getDireccion() 
    {
        return direccion;
    }

    /**
     * @param direccion La nueva dirección.
     */
    public void setDireccion(String direccion) 
    {
        this.direccion = direccion;
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
     * Constructor por defecto
     */
    public ProveedorDTO()
    {
        //Constructor vacio
    }      
   

    /**
     * @return the penalizado
     */
    public boolean isPenalizado() 
    {
        return penalizado;
    }

    /**
     * @param penalizado the penalizado to set
     */
    public void setPenalizado(boolean penalizado) 
    {
        this.penalizado = penalizado;
    }
    
    /**
     * Convierte un ProveedorEntity en un ProveedorDTO
     * @param e ClienteEntity instancia
     */
    public ProveedorDTO(ProveedorEntity e)
    {        
        if (e != null)
        {    
            this.contrasena=e.getContrasena();
            this.correo=e.getCorreo();
            this.direccion=e.getDireccion();
            this.documento=e.getDocumento();
            this.login=e.getLogin();
            this.nombre=e.getNombre();
            this.telefono=e.getTelefono();
            this.penalizado = e.isPenalizado();             
        }
    }
    
    /**
     * Método que transforma la clase ProveedorDTO a ProveedorEntity.
     * @return ProveedorEntity instancia.
     */
    public ProveedorEntity toEntity()
    {
        ProveedorEntity e = new ProveedorEntity(); 
        e.setId(this.id);
        e.setContrasena(getContrasena());
        e.setCorreo(getCorreo());
        e.setDireccion(getDireccion());
        e.setDocumento(getDocumento());
        e.setLogin(getLogin());
        e.setNombre(getNombre());
        e.setTelefono(getTelefono());
        e.setPenalizado(isPenalizado());
        return e;
    }
}
