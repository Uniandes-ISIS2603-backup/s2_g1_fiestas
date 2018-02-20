/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *Entidad Evento
 * @author cm.amaya10
 */
@Entity
public class EventoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id; /*ID del evento */
    @Temporal(TemporalType.DATE)
    private Date fecha; /*Fecha del evento */
    private String descripcion; /*Descripcion del evento */
    private String celebrado; /*Nombre del celebrado del evento */
    private String lugar; /*Lugar del evento */
    private Integer invitados; /*Numero de invitados del evento */

    /**
     * Obtiene el atributo id
     * 
     * @return id asignado al evento
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id al evento
     * 
     * @param id id nuevo del evento
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Obtiene el atributo fecha
     * 
     * @return fecha asignada al evento
     */
    public Date getFecha(){
        return fecha;
    }
    
    /**
     * Establece la fecha del evento
     * 
     * @param fecha nueva del evento
     */
    public void setFecha(Date fecha){
        this.fecha=fecha;
    }
    
    /**
     * Obtiene el atributo descripcion
     * 
     * @return descripcion general del evento
     */
    public String getDescripcion(){
        return descripcion;
    }
    
    /**
     * Establece la descripcion del evento
     * 
     * @param descripcion nueva del evento
     */
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    /**
     * Obtiene el nombre de la persona celebrada
     * 
     * @return nombre de la persona celebrada en el evento
     */
    public String getCelebrado() {
        return celebrado;
    }

    /**
     * Establece el nombre del celebado
     * 
     * @param celebrado, nombre del nuevo celebrado
     */
    public void setCelebrado(String celebrado) {
        this.celebrado = celebrado;
    }
    
    /**
     * Obtiene el lugar del evento
     * 
     * @return lugar asignado del evento
     */
    public String getLugar(){
        return lugar;
    }
    
    /**
     * Establece el lugar del evento
     * 
     * @param lugar nuevo del evento
     */
    public void setLugar(String lugar){
        this.lugar=lugar;
    }
    
    /**
     * Obtiene el numero de los invitados
     * 
     * @return numero de invitados
     */
    public Integer getInvitados(){
        return invitados;
    }
    
    /**
     * Establece el numero de los invitados al evento
     * 
     * @param invitados, numero de invitados que asistiran el evento.
     */
    public void setInvitados(Integer invitados){
        this.invitados=invitados;
    }

}

