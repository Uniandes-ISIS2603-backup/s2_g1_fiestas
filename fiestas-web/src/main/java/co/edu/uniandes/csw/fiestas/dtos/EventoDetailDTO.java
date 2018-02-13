/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;

/**
 *
 * @author cm.amaya10
 */
public class EventoDetailDTO extends EventoDTO{
    
    private ClienteDTO cliente;
    private PagoDTO pago;
    private ArrayList<ContratoDTO> contratos;
    private TematicaDTO tematica;
     /**
     * Constructor por defecto
     */   
    public EventoDetailDTO(){
        
    }
 
    public ClienteDTO getCliente(){
        return cliente;
    }
    
    public void setCliente(ClienteDTO cliente){
        this.cliente=cliente;
    }
    
    public PagoDTO getPago(){
        return pago;
    }
    
    public void setPago(PagoDTO pago){
        this.pago=pago;
    }
    
        
     public ArrayList<ContratoDTO> getContratos()
    {
        return contratos;
    }
    
    public void setContratos(ArrayList<ContratoDTO> contratos)
    {
        this.contratos = contratos;
    }
    
        
    public TematicaDTO getTematica(){
        return tematica;
    }
    
    public void setTematica(TematicaDTO tematica){
        this.tematica=tematica;
    }
}
