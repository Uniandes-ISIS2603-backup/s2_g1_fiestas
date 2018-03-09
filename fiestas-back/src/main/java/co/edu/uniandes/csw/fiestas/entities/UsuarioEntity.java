package co.edu.uniandes.csw.fiestas.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author df.nino10
 */
@Entity
public class UsuarioEntity extends BaseEntity 
{
    private String nombre;
    private String documento;
    private Long telefono;
    private String correo;
    private String direccion;
    private String login;
    private String contraseña;
    @PodamExclude
    @OneToMany(mappedBy = "usuario", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<BlogEntity> blogs= new ArrayList<>();
    
    
    /**
     * @return nombre del usuario
     */
    public String getNombre() {
        return documento;
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

    public List<BlogEntity> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<BlogEntity> blogs) {
        this.blogs = blogs;
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
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) 
    {
        this.contraseña = contraseña;
    }  

    public void agregarBlog(BlogEntity blogEntity) {
        blogs.add(blogEntity);
    }

    public void removerBlog(BlogEntity blog) {
        blogs.remove(blog);
    }
    
}
