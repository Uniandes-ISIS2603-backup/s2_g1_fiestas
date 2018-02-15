/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.List;
/**
 *Clase que extiende de {@link HorarioDTO} para manejar la transformación entre los objetos JSON y las entidades de la base de datos.
 *Para conocer el contenido del horario vaya a la documentación de {@link HorarioDTO}
 * @author df.nino10
 *
 
 * @author df.nino10
 */

public class HorarioDetailDTO extends HorarioDTO {
    
    private List<EventoDTO> eventos;
    
    /**
     * Constructor por defecto
     */
    public HorarioDetailDTO(){
    }

    /**
     * @return lista de eventos
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

     /**
     * @param nueva lista de eventos.
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }
    
    
    
}
