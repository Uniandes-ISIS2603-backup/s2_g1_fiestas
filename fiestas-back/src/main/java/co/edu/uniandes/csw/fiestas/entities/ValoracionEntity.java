package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author ls.arias
 */
@Entity
public class ValoracionEntity extends BaseEntity implements Serializable{
 
    private String comentario;
    private Integer calificacion;
    
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

