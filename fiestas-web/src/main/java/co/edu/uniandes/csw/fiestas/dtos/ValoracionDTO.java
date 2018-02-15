/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.logging.Logger;

/**
 *
 * @author ls.arias
 */
public class ValoracionDTO {

    private long id;
    private String comentario;
    private Integer calificacion;
    
       public ValoracionDTO()
    {
        
    }
    

    public long getId() {
        return id;
    }

    public String getComentario() {
        return comentario;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
 
}
