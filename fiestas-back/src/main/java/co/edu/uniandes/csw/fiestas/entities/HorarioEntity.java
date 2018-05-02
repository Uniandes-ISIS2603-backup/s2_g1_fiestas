/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 *
 * @author df.nino10
 */
@Entity
public class HorarioEntity extends BaseEntity implements Serializable
{
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Temporal(TemporalType.TIME)
    private Date horaFin;
   

    /**
     * Retorna la fecha del horario
     * @return fecha - la fecha
     */
    public Date getFecha() 
    {
        return fecha;
    }

    /**
     * Asigna la fecha al horario
     * @param fecha - la nueva fecha 
     */
    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    /**
     * Retorna la hora de inicio del horario
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * Asigna la hora de inicio al horario
     * @param horaInicio - la nueva hora de inicio
     */
    public void setHoraInicio(Date horaInicio) 
    { 
        this.horaInicio = horaInicio;
    }

    /**
     * Retorna la hora final del horario
     * @return horaFin - la hora final
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * Asigna la hora final al contrato
     * @param horaFin - la nueva hora final
     */
    public void setHoraFin(Date horaFin) 
    {
        this.horaFin = horaFin;
    }
   
}
