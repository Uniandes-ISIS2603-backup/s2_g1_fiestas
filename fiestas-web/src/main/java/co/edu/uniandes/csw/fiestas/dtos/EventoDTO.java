/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.Date;

/**
 *Clase que 
 * @author df.nino10
 * @author Cristian M. Amaya (cm.amaya10=
 */
public class EventoDTO {
     private long id;
     private Date fecha;
     private String descripcion;
     private String celebrado;
     private String lugar;
     private Integer invitados;
     
     public EventoDTO(){
         
     }
     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public void setDescripcion(String descripcion)
    {
        this.descripcion=descripcion;
    }
    
    public String getCelebrado(){
        return celebrado;
    }
    
    public void setCelebrado(String celebrado)
    {
        this.celebrado=celebrado;
    }
    
    public String getLugar(){
        return lugar;
    }
    
    public void setLugar(String lugar)
    {
        this.lugar=lugar;
    }
    
    public Integer getInvitados(){
        return invitados;
    }
    
    public void setInvitados(Integer numInvitados)
    {
        this.invitados=numInvitados;
    }
}
