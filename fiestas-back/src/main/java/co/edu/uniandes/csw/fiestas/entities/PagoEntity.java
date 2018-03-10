package co.edu.uniandes.csw.fiestas.entities;

import co.edu.uniandes.csw.fiestas.enums.Estado;
import co.edu.uniandes.csw.fiestas.enums.MetodoDePago;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad Pago.
 *
 * @author cm.amaya10
 */
@Entity
public class PagoEntity extends BaseEntity implements Serializable {

    private Boolean realizado;/*Boolean que representa si el pago fur realizado*/
    private String estado;/*Estado actual del pagoo*/
    private String metodoDePago;/*Metodo de pago actual*/
    private int valor;/*Valor del pago*/

    @PodamExclude
    @OneToOne(mappedBy = "pago")
    private EventoEntity evento;

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
     * @return id del metodo de pago actual del pago
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
     * Obtiene el valor a pagar del pago
     *
     * @return valor a pagar
     */
    public int getValor() {
        return valor;
    }

    /**
     * Establece eel valor a pagar del pago
     *
     * @param valor a pagar
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Obtiene el evento relacionado del pago
     *
     * @return evento relacionado del pago
     */
    public EventoEntity getEvento() {
        return evento;
    }

    /**
     * Establece el evento al que corresponde el pago
     *
     * @param evento Entidad del evento a asignar al pago
     */
    public void setEvento(EventoEntity evento) {
        this.evento = evento;
    }
}
