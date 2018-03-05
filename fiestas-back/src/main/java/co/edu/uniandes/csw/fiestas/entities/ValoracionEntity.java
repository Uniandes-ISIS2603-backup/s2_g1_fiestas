/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author ls.arias
 */
@Entity
public class ValoracionEntity extends BaseEntity implements Serializable{
    
    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ProveedorEntity proveedor;

    @PodamExclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ServicioEntity servicio;
    
    private String comentario;
    private Integer calificacion;
    
    public ProveedorEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorEntity proveedor) {
        this.proveedor = proveedor;
    }

    public String getComentario() {
        return comentario;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setCalificacion(Integer calificacion) {
        
    }    

    public ServicioEntity getServicio() {
        return servicio;
    }

    public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }
}
