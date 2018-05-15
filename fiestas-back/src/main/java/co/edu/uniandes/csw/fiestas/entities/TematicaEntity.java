package co.edu.uniandes.csw.fiestas.entities;

import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author af.losada
 */
@Entity 
public class TematicaEntity  extends BaseEntity implements Serializable
{

    private String descripcion;
    
    private String nombre;
    
    @PodamExclude
    @OneToMany
    private List<ProductoEntity> productos;
    
    
    /**
     * @return the descripcion
     */
    public String getDescripcion() 
    {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) 
    {
        this.descripcion = descripcion;
    }
    
    /**
     * @return the productos
     */
    public List<ProductoEntity> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(List<ProductoEntity> productos) {
        this.productos = productos;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
