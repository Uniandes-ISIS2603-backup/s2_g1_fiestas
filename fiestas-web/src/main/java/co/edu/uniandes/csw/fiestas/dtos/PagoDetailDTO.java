/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 * Clase que extiende de {@link PagoDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del pago vaya a la documentacion de {@link PagoDTO}
 * @author Cristian M. Amaya (cm.amaya10)
 */
public class PagoDetailDTO extends PagoDTO{
    
    private EventoDTO evento;
    
     /**
     * Constructor por defecto
     */
    public PagoDetailDTO(){
        
    }
    
    /**
     * Retorna el evento correspondiente al pago.
     * @return Evento relacionado.
     */
    public EventoDTO getEvento(){
        return evento;
    }
    
    /**
     * Se asigna un evento al pago.
     * @param evento nuevo a asignar
     */
    public void setEvento(EventoDTO evento){
        this.evento=evento;
    }

}
