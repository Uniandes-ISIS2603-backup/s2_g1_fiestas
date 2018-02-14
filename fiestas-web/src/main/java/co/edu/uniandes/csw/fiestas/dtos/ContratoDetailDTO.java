/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *Clase que extiende de {@link ContratoDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido de la ciudad vaya a la documentacion de {@link ContratoDTO}
 * @author mc.gonzalez15
 */
public class ContratoDetailDTO {
    
    /**
     * Proveedor del contrato
     */
    private ProveedorDTO proveedor; 
    
    /**
     * Evento del contrato
     */
    private EventoDTO evento;
    
    /**
     * Lista de productos 
     */
    private List<ProductoDTO> productos;

    /**
     * Método constructor
     */
    public ContratoDetailDTO()
    {
        
    }
    /**
     * Retorna el proveedor
     * @return proveedor
     */
    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    /**
     * Asigna el proveedor
     * @param proveedor 
     */
    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Retorna el evento
     * @return evento
     */
    public EventoDTO getEvento() {
        return evento;
    }

    /**
     * Asigna el evento
     * @param evento 
     */
    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    /**
     * Retorna la lista de productos del contrato
     * @return productos
     */
    public List<ProductoDTO> getProductos() {
        return productos;
    }

    /**
     * Asigna la lista de productos del contrato
     * @param productos 
     */
    public void setProductos(ArrayList<ProductoDTO> productos) {
        this.productos = productos;
    }
    
}
