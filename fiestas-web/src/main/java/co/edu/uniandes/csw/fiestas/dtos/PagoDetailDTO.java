/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;


/**
 *
 * @author cm.amaya10
 */
public class PagoDetailDTO extends PagoDTO{
    
    private EventoDTO evento;
    
     /**
     * Constructor por defecto
     */
    public PagoDetailDTO(){
        
    }
    
    public EventoDTO getEvento(){
        return evento;
    }
    
    public void setEvento(EventoDTO evento){
        this.evento=evento;
    }

}
