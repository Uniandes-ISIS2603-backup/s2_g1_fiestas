
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author nm.hernandez10
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable
{    
    private String nombre;
    private String documento;
    private Long telefono;
    private String correo;
    private String direccion;
    private String login;
    private String contrasena;
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoEntity> eventos = new ArrayList<EventoEntity>();
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogEntity> blogs = new ArrayList<BlogEntity>();

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
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento)
    {
        this.documento = documento;
    }

    /**
     * @return the telefono
     */
    public Long getTelefono() 
    {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Long telefono) 
    {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) 
    {
        this.correo = correo;
    }

    /**
     * @return the direccion
     */
    public String getDireccion()
    {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
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
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena
     */
    public void setContrasena(String contrasena) 
    {
        this.contrasena = contrasena;
    }
    
    /**
     * @return eventos
     */
    public List<EventoEntity> getEventos() 
    {
        return eventos;
    }

    /**
     * @param eventos los nuevos eventos
     */
    public void setEventos(List<EventoEntity> eventos) {
        this.eventos = eventos;
    }
    
    public void addEvento(EventoEntity e)
    {
        eventos.add(e);
    }
    
    public void removeEvento(EventoEntity e)
    {
        eventos.remove(e);
    }
    
    /**
     * 
     * @return 
     */
    public List<BlogEntity> getBlogs() {
        return blogs;
    }

    /**
     * 
     * @param blogs 
     */
    public void setBlogs(List<BlogEntity> blogs) {
        this.blogs = blogs;
    }
}
