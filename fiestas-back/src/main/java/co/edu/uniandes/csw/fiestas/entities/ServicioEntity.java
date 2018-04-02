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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValoracionEntity> valoraciones;

    @PodamExclude
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoEntity> productos;

    @PodamExclude
    @ManyToMany(mappedBy = "servicios")
    private List<ProveedorEntity> proveedores;

    private String nombre;
    private String descripcion;
    private String tipo;

    /**
     * Obtiene el atributo nombre
     *
     * @return nombre asignado al servicio
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del servicio
     *
     * @param nombre nuevo del servicio
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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

