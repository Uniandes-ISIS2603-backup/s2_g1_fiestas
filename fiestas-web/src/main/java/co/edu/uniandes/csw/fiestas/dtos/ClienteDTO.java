package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;

/**
 * ClienteDTO Objeto de transferencia de datos de Cliente. Los DTO contienen las
 * represnetaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "documento": string
 *      "telefono": number
 *      "correo": string
 *      "direccion": string
 *      "login": string
 *   }
 * </pre> Por ejemplo un cliente se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 113742,
 *      "nombre": David,
 *      "documento": 201412734,
 *      "telefono": 3144683924,
 *      "correo": df.nino10@uniandes.edu.co,
 *      "direccion": cll 1#1-1,
 *      "login": df.nino10,
 *   }
 *
 * </pre>
 *
 * @author nm.hernandez10
 */
public class ClienteDTO
{   
    private long id;
    private String nombre;
    private String documento;
    private Long telefono;
    private String correo;
    private String direccion;
    private String login;
    private String contrasena;
    
    public ClienteDTO()
    {
        //Constructor vacio
    }
    
    /**
     * @return El id del usuario.
     */
    public long getId() 
    {
        return id;
    }

     /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
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
     * Convierte un ClienteEntity en un ClienteDTO
     * @param e ClienteEntity instancia
     */
    public ClienteDTO(ClienteEntity e)
    {
        if (e != null)
        {
            this.id = e.getId();            
            this.contrasena=e.getContrasena();
            this.correo=e.getCorreo();
            this.direccion=e.getDireccion();
            this.documento=e.getDocumento();
            this.login=e.getLogin();
            this.nombre=e.getNombre();
            this.telefono=e.getTelefono();
        }
    }    
    
    /**
     * Método que transforma la clase ClienteDTO a ClienteEntity.
     * @return ClienteEntity instancia.
     */
    public ClienteEntity toEntity()
    {
        ClienteEntity e = new ClienteEntity();
        e.setId(this.getId());
        e.setContrasena(getContrasena());
        e.setCorreo(getCorreo());
        e.setDireccion(getDireccion());
        e.setDocumento(getDocumento());
        e.setLogin(getLogin());
        e.setNombre(getNombre());
        e.setTelefono(getTelefono());
        return e;
    }
}
