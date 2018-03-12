package co.edu.uniandes.csw.fiestas.entities;

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
public class ClienteEntity extends UsuarioEntity
{
    //Está vacío porque sólo tiene la relación de eventos extra (comparado con UsuarioEntity) y aún no debe hacerse las relaciones.
    
    @PodamExclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoEntity> eventos = new ArrayList<EventoEntity>();
    
    @PodamExclude
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogEntity> blogs = new ArrayList<BlogEntity>();

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
