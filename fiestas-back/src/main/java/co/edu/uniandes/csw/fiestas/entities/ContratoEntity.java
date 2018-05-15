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
    /**
     * El evento del contrato
     */
    private EventoEntity evento;
     
    /**
     * El estado del contrato
    */
    private String estado;

    @PodamExclude
    @ManyToOne
    /**
     * El proveedor del contrato
     */
    private ProveedorEntity proveedor;
    
    @PodamExclude
    @OneToOne
    /**
     * El bono del contrato
     */
    private BonoEntity bono;


    @PodamExclude
    @OneToMany
    /**
     * Lista de productos del contrato
     */
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

    /**
     * Retorna el valor del contrato
     * @return valor - el valor del contrato
     */
    public int getValor() {
        return valor;
    }

    /**
     * Asigna el valor del contrato
     * @param valor - el nuevo valor
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Retorna los terminos y condiciones del contrato
     * @return tyc - los terminos y condiciones
     */
    public String getTyc() {
        return tyc;
    }

    /**
     * Asigna los términos y condiciones del contrato 
     * @param tyc - los nuevos terminos y condiciones
     */
    public void setTyc(String tyc) {
        this.tyc = tyc;
    }

    /**
     * Retorna el evento del contrato
     * @return evento - el evento
     */
    public EventoEntity getEvento() {
        return evento;
    }

    /**
     * Asigna el evento del contrato
     * @param evento - el nuevo evento 
     */
    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }

    /**
     * Retorna el proveedor del contrato
     * @return proveedor - el proveedor
     */
    public ProveedorEntity getProveedor() {
        return proveedor;
    }
    
    /**
     * Asigna el proveedor del contrato
     * @param proveedor - el nuevo proveedor
     */
    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Retorna la lista de productos del contrato
     * @return productos - los productos del contrato
     */
    public List<ProductoEntity> getProductos() {
        return productos;
    }

    /**
     * Asigna la lista de productos al contrato
     * @param productos - los productos del contrato
     */
    public void setProductos(List<ProductoEntity> productos) {
        this.productos = productos;
    }

    /**
     * Retorna el horario
     * @return horario - el horario
     */
    public HorarioEntity getHorario() {
        return horario;
    }

    /**
     * Asigna el horario al contrato
     * @param horario - el nuevo horario
     */
    public void setHorario(HorarioEntity horario) {
        this.horario = horario;
    }

    /**
     * Asigna el estado al contrato
     * @param estado - el nuevo estado
     */
    public void setEstado(String estado) {
       this.estado = estado;
    }
    
    /**
     * Retorna el estado del contrato
     * @return el estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Retorna el bono del contrato. 
     * @return bono - el bono del contrato
     */
    public BonoEntity getBono() {
        return bono;
    }

    /**
     * Asigna el bono al contrato-
     * @param bono - el nuevo bono
     */
    public void setBono(BonoEntity bono) {
        this.bono = bono;
    }

    

}
