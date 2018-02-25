/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;
import java.util.Date;

/**
 * HorarioDTO Objeto de transferencia de datos de Horario. Los DTO contienen las
 * represnetaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "fecha: Date,
 *      "horaInicio": Date,
 *      "horaFin": Date
 *   }
 * </pre> Por ejemplo un cliente se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 112344,
 *      "fecha: 01/01/12,
 *      "horaInicio": 14:00,
 *      "horaFin": 20:00
 *   }
 *
 * </pre>
 *
 * @author df.nino10
 */
public class HorarioDTO 
{    
    private long id;
    private Date fecha;
    private Date horaInicio;
    private Date horaFin;
    
    public HorarioDTO(){
    }
    
    /**
     * @return el Id del horario
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id El nuevo Id del horario
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return La fecha del horario.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha La nueva fecha del horario
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    /**
     * @return La fecha del horario.
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

     /**
     * @param horaInicio La nueva hora de inicio
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }
    /**
     * @return La hora de fin del horario.
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin La nueva hora de fin 
     */
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }
    
    
}
