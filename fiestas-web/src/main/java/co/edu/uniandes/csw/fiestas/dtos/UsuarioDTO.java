/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 * UsuarioDTO Objeto tranferencia para los Usuarios. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "documento": string,
 *      "telefono": number,
 *      "correo": string,
 *      "direccion": string,
 *      "login": string
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
    private String documento;
    private Long telefono;
    private String correo;
    private String direccion;
    private String login;

    /**
     * Constructor por defecto
     */
    public UsuarioDTO() 
    {
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
}
