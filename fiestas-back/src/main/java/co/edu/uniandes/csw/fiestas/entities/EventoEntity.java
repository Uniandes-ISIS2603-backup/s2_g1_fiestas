package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad Evento
 *
 * @author cm.amaya10
 */
@Entity
public class EventoEntity extends BaseEntity implements Serializable {

    private String nombre;
    /*Nombre del evento*/
    @Temporal(TemporalType.DATE)
    private Date fecha;
    /*Fecha del evento */
    private String descripcion;
    /*Descripcion del evento */
    private String celebrado;
    /*Nombre del celebrado del evento */
    private String lugar;
    /*Lugar del evento */
    private Integer invitados;
    /*Numero de invitados del evento */

    @PodamExclude
    @OneToMany(mappedBy = "evento")
    private List<ContratoEntity> contratos = new ArrayList<>();

    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private PagoEntity pago;

    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;

    @PodamExclude
    @OneToOne
    private TematicaEntity tematica;

    /**
     * Obtiene el atributo nombre
     *
     * @return nombre asignado al evento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del evento
     *
     * @param nombre nuevo del evento
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el atributo fecha
     *
     * @return fecha asignada al evento
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del evento
     *
     * @param fecha nueva del evento
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el atributo descripcion
     *
     * @return descripcion general del evento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripcion del evento
     *
     * @param descripcion nueva del evento
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el nombre de la persona celebrada
     *
     * @return nombre de la persona celebrada en el evento
     */
    public String getCelebrado() {
        return celebrado;
    }

    /**
     * Establece el nombre del celebado
     *
     * @param celebrado, nombre del nuevo celebrado
     */
    public void setCelebrado(String celebrado) {
        this.celebrado = celebrado;
    }

    /**
     * Obtiene el lugar del evento
     *
     * @return lugar asignado del evento
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Establece el lugar del evento
     *
     * @param lugar nuevo del evento
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * Obtiene el numero de los invitados
     *
     * @return numero de invitados
     */
    public Integer getInvitados() {
        return invitados;
    }

    /**
     * Establece el numero de los invitados al evento
     *
     * @param invitados, numero de invitados que asistiran el evento.
     */
    public void setInvitados(Integer invitados) {
        this.invitados = invitados;
    }

    /**
     * Obtiene el cliente encargado del evento
     *
     * @return cliente encargado del evento
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente encargado del evento
     *
     * @param cliente encargado del evento
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene el pago del evento
     *
     * @return pago correspondiente del evento
     */
    public PagoEntity getPago() {
        return pago;
    }

    /**
     * Establece el pago correspondiente al evento
     *
     * @param pago, pago correspondiente al evento.
     */
    public void setPago(PagoEntity pago) {
        this.pago = pago;
    }

    /**
     * Obtiene la lista de contratos
     *
     * @return lista de contratos
     */
    public List<ContratoEntity> getContratos() {
        return contratos;
    }

    /**
     * Establece la lista de contratos correspondiente al evento
     *
     * @param contratos, lista de contratos correspondiente al evento.
     */
    public void setContratos(List<ContratoEntity> contratos) {
        this.contratos = contratos;
    }

    /**
     * Obtiene la tematica del evento
     *
     * @return tematica del evento
     */
    public TematicaEntity getTematica() {
        return tematica;
    }

    /**
     * Establece la tematica correspondiente al evento
     *
     * @param tematica correspondiente al evento.
     */
    public void setTematica(TematicaEntity tematica) {
        this.tematica = tematica;
    }

}
