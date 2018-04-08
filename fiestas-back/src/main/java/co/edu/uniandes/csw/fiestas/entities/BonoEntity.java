package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author df.nino10
 */
@Entity
public class BonoEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;


    /**
     * Contato al cual se aplica el descuento del bono.
     * Es OneToOne porque un bono no es acumulable con otros bonos.
     */
    @PodamExclude
    @OneToOne
    private ContratoEntity contrato;

    /**
     * Porcentaje de descuento
     */
    private double descuento;

    /**
    * Fecha desde la cual aplica el bono
    */
    @Temporal(TemporalType.DATE)
    private Date aplicaDesde;
    
    /**
     * Fecha de expiración del bono
     */
    @Temporal(TemporalType.DATE)
    private Date expira;
    
    /**
     * Razón del bono (puede ser null)
     */
    private String motivo;

    /**
     * @return the proveedor
     */
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the contrato
     */
    public ContratoEntity getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(ContratoEntity contrato) {
        this.contrato = contrato;
    }

    /**
     * @return the descuento
     */
    public double getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the aplicaDesde
     */
    public Date getAplicaDesde() {
        return aplicaDesde;
    }

    /**
     * @param aplicaDesde the aplicaDesde to set
     */
    public void setAplicaDesde(Date aplicaDesde) {
        this.aplicaDesde = aplicaDesde;
    }

    /**
     * @return the expira
     */
    public Date getExpira() {
        return expira;
    }

    /**
     * @param expira the expira to set
     */
    public void setExpira(Date expira) {
        this.expira = expira;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
