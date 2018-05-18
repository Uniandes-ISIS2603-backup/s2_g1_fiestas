/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author ls.arias
 */
@Entity
public class ServicioEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoEntity> productos;

    private String nombre;
    private String descripcion;
    private String tipo;
    private String imagen;

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

     public List<ProductoEntity> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoEntity> productos) {
        this.productos = productos;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}

