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
     * Porcentaje de descuento
     */
    private int descuento;

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
     * Código del bono (puede ser null)
     */
    private String codigo;

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
     * @return the descuento
     */
    public int getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(int descuento) {
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

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
