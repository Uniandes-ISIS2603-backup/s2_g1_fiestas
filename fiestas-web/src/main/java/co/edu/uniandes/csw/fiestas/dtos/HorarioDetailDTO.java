/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;

/**
 *
 * @author df.nino10
 */
public class HorarioDetailDTO extends HorarioDTO {
    
    private ArrayList<EventoDTO> eventos;
    
    public HorarioDetailDTO(){
    }

    public ArrayList<EventoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<EventoDTO> eventos) {
        this.eventos = eventos;
    }
    
    
    
}
