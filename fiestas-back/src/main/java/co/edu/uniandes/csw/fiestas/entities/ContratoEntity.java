/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author mc.gonzalez15
 */
@Entity
public class ContratoEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @ManyToOne
    private EventoEntity evento;
     
    private String estado;

    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;
    
    @PodamExclude
    @OneToOne(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private BonoEntity bono;


    @PodamExclude
    @OneToMany
    private List<ProductoEntity> productos;
    
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private HorarioEntity horario;

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

    public List<ProductoEntity> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoEntity> productos) {
        this.productos = productos;
    }

    public HorarioEntity getHorario() {
        return horario;
    }

    public void setHorario(HorarioEntity horario) {
        this.horario = horario;
    }

    public void setEstado(String estado) {
       this.estado = estado;
    }
    
    public String getEstado(String estado) {
        return estado;
    }

    /**
     * @return the bono
     */
    public BonoEntity getBono() {
        return bono;
    }

    /**
     * @param bono the bono to set
     */
    public void setBono(BonoEntity bono) {
        this.bono = bono;
    }

    

}
