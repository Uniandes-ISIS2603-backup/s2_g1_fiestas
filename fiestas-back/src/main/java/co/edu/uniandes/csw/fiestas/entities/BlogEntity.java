package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
* Entidad Blog
* 
* @author mc.gonzalez15
*/
@Entity
public class BlogEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;


    @PodamExclude
    @ManyToOne
    private EventoEntity evento;

    /**
     * Titulo del Blog
     */
    private String titulo;

    /**
     * Cuerpo del Blog
     */
    private String cuerpo;

    /**
     * Número de likes
     */
    private int likes;
    
    /**
     * Número de likes
     */
    private String imagen;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) 
    {
        this.cuerpo = cuerpo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public EventoEntity getEvento() {
        return evento;
    }

    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
