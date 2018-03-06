/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ls.arias
 */
@Entity
public class ServicioEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValoracionEntity> valoraciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoEntity> productos = new ArrayList<>();

    @PodamExclude
    @ManyToMany
    private List<ProveedorEntity> proveedores = new ArrayList<>();

    private String descripcion;
    private String tipo;

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setDescripcion(String pDescripcion) {
        this.descripcion = pDescripcion;
    }

    public void setTipo(String pTipo) {
        this.tipo = pTipo;
    }

    public List<ValoracionEntity> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<ValoracionEntity> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public List<ProductoEntity> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoEntity> productos) {
        this.productos = productos;
    }

    public List<ProveedorEntity> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<ProveedorEntity> proveedores) {
        this.proveedores = proveedores;
    }

}
