/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;

/**
 *
 * @author ls.arias
 */
public class ServicioDTO {
    
    private long id;
    private String descripcion;
    private String tipo;
    
    public ServicioDTO()
    {
        
    }
    
    public ServicioDTO(ServicioEntity servicio)
    {
        this.id = servicio.getId();
        this.descripcion = servicio.getDescripcion();
        this.tipo = servicio.getTipo();
    }

    public long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public ServicioEntity toEntity()
    {
        ServicioEntity entity = new ServicioEntity();
        entity.setId(this.id);
        entity.setDescripcion(this.descripcion);
        entity.setTipo(this.tipo);
        return entity;
    }
    
}
