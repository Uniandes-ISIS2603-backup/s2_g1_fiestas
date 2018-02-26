/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author mc.gonzalez15
 */
@Entity
public class ContratoEntity extends BaseEntity implements Serializable {

    @ManyToOne
    private EventoEntity evento;

    @ManyToOne
    private ProveedorEntity proveedor;

    @OneToMany
    private List<ProductoEntity> producto;

    /**
     * Valor del contrato
     */
    private int valor;

    /**
     * Condiciones del contrato
     */
    private String tyc;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getTyc() {
        return tyc;
    }

    public void setTyc(String tyc) {
        this.tyc = tyc;
    }

    public EventoEntity getEvento() {
        return evento;
    }

    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }

    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    public List<ProductoEntity> getProducto() {
        return producto;
    }

    public void setProducto(List<ProductoEntity> producto) {
        this.producto = producto;
    }

}
