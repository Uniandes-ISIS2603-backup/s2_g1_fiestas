package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ls.arias
 */
@Entity
public class ValoracionEntity extends BaseEntity implements Serializable{
 
    private String comentario;
    private Integer calificacion;
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ServicioEntity servicio;

    /**
     * Obtiene el servicio correspondiente a la valoracion
     *
     * @return servicio
     */
    public ServicioEntity getServicio() {
        return servicio;
    }
    
    /**
     * Establece el servicio de la valoracion
     *
     * @param servicio nuevo de la valoracion
     */
    public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }
    
    /**
     * Obtiene el atributo comentario
     *
     * @return comentrario asignado al evento
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Obtiene el atributo calificacion
     *
     * @return calificacion asignado al evento
     */
    public Integer getCalificacion() {
        return calificacion;
    }

    /**
     * Establece el comentario de la valoracion
     *
     * @param comentario nuevo de la valoracion
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Establece la calificacion de la valoracion
     *
     * @param calificacion nueva de la calificacion
     */
    public void setCalificacion(Integer calificacion) {
        this.calificacion=calificacion;
    }    
}
