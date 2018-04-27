package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad Producto
 *
 * @author af.losada
 */
@Entity
public class ProductoEntity extends BaseEntity implements Serializable {
    

    private String nombree;
    private Integer precio;
    private String descripcion;
    private String incluye;
    private Integer personal;

    @PodamExclude
    @ManyToOne
    private ServicioEntity servicio;

    /**
     * @return nombre del producto
     */
    public String getNombre() {
        return nombree;
    }

    /**
     * @param nombre a asignar al producto
     */
    public void setNombre(String nombre) {
        this.nombree = nombre;
    }

    /**
     * @return the precio
     */
    public Integer getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the incluye
     */
    public String getIncluye() {
        return incluye;
    }

    /**
     * @param incluye the incluye to set
     */
    public void setIncluye(String incluye) {
        this.incluye = incluye;
    }

    /**
     * @return the personal
     */
    public Integer getPersonal() {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(Integer personal) {
        this.personal = personal;
    }

    /**
     * @return the servicio
     */
    public ServicioEntity getServicio() {
        return servicio;
    }

    /**
     * @param servicio the servicio to set
     */
    public void setServicio(ServicioEntity servicio) {
        this.servicio = servicio;
    }

    
    
    
    
    
    
}
