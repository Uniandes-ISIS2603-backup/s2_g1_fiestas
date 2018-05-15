package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad Producto
 *
 * @author af.losada
 */
@Entity
public class ProductoEntity extends BaseEntity implements Serializable {

   

    private String nombre;
    private Integer precio;
    private String descripcion;
    private String incluye;
    private Integer personal;
    private String imagen;
    private Double valoracionPromedio;

    @PodamExclude
    @ManyToOne
    private ServicioEntity servicio;
    @PodamExclude
    @ManyToOne
    private ProveedorEntity proveedor;
    
    @PodamExclude 
    @OneToMany 
    private List<ValoracionEntity> valoraciones;

    /**
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre a asignar al producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    /**
     * @return the valoraciones
     */
    public List<ValoracionEntity> getValoraciones() {
        return valoraciones;
    }

    /**
     * @param valoraciones the valoraciones to set
     */
    public void setValoraciones(List<ValoracionEntity> valoraciones) {
        this.valoraciones = valoraciones;
    }

    /**
     * @return the valoracionPromedio
     */
    public Double getValoracionPromedio() {
        if(valoracionPromedio == null)
        {
            return 0.0;
        }
        else
        return valoracionPromedio;
    }

    /**
     * @param valoracionPromedio the valoracionPromedio to set
     */
    public void setValoracionPromedio(Double valoracionPromedio) {
        this.valoracionPromedio = valoracionPromedio;
    }
    
    
    
    
}
