/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;

/**
 *
 * @author mc.gonzalez15
 */
public class ContratoDetailDTO {
    
    private ProveedorDTO proveedor; 
    private EventoDTO evento;
    private ArrayList<ProductoDTO> productos;

    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    public EventoDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    public ArrayList<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoDTO> productos) {
        this.productos = productos;
    }
    
}
