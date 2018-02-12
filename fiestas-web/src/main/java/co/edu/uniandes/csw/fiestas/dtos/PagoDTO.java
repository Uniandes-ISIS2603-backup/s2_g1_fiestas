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
public class PagoDTO {
     private long id;
     private Boolean realizado;
     private String estado;
     private String metodoDePago;
     
     
     public PagoDTO(){
         
}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Boolean getRealizado() {
        return realizado;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getMetodoPago() {
        return metodoDePago;
    }

    public void setMetodoPago(String metodo) {
        this.metodoDePago=metodo;
    }
}
