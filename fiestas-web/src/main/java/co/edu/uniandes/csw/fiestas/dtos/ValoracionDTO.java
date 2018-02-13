/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
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
    
    public ValoracionDTO(ValoracionEntity valoracion)
    {
        this.id = valoracion.getId();
        this.comentario = valoracion.getComentario();
        this.calificacion = valoracion.getCalificacion();
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
    
 public ValoracionEntity toEntity() {
        ValoracionEntity entity = new ValoracionEntity();
        entity.setId(this.id);
        entity.setComentario(this.comentario);
        entity.setCalificacion(this.calificacion);
        return entity;
    }
    
    
    
}
