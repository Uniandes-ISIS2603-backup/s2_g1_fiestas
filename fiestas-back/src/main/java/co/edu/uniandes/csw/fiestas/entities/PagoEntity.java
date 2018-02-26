/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author cm.amaya10
 */
@Entity
public class PagoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; /*Id del pago*/
    private Boolean realizado; /*Boolean que representa si el pago fur realizado*/
    private String estado;/*Estado actual del pagoo*/
    private String metodoDePago; /*Metodo de pago actual*/
    
    @PodamExclude
    @OneToOne(mappedBy="pago")
    private EventoEntity evento;

    /**
     * Obtiene el atributo id
     *
     * @return id asignado al pago
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id al pago
     *
     * @param id id nuevo del pago
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el boolean que representa si el pago fue realizado
     *
     * @return boolean que es true si el pago fue realizado, false de lo
     * contrario.
     */
    public boolean isRealizado() {
        return realizado;
    }

    /**
     * Establece si el pago fue realizado.
     *
     * @param realizado, boolean que representa si el pago fue realizado.
     */
    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    /**
     * Obtiene el estado actual del pago
     *
     * @return estado actual del pago
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el nuevo estado del pago
     *
     * @param estado nuevo del pago
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el metodo de pago del pago
     *
     * @return metodo de pago actual del pago
     */
    public String getMetodoDePago() {
        return metodoDePago;
    }

    /**
     * Establece el metodo de pago del pago
     *
     * @param metodoDePago Metodo de pago nuevo del pago
     */
    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }
    
     /**
     * Obtiene el evento relacionado del pago
     *
     * @return evento relacionado del pago
     */
    public EventoEntity getEvento(){
        return evento;
    }
    
     /**
     * Establece el evento al que corresponde el pago
     *
     * @param evento Entidad del evento a asignar al pago
     */
    public void setEvento(EventoEntity evento){
        this.evento=evento;
    }
}
