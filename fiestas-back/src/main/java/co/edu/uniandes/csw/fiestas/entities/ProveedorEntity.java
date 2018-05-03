package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author nm.hernandez10
 */
@Entity
public class ProveedorEntity extends BaseEntity implements Serializable
{
    private boolean penalizado;
    private String imagen;
    private String nombre;
    private String documento;
    private Long telefono;
    private String correo;
    private String direccion;
    private String login;
    private String contrasena;
    private Double valoracion;
    
    @PodamExclude
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContratoEntity> contratos;
    
    @PodamExclude
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BonoEntity> bonos;
    
    @PodamExclude
    @ManyToMany
    private List<ProductoEntity> productos;
    

    
    /**
     * @return nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre del usuario a asignar
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * @param documento the documento to set
     */
    public void setDocumento(String documento)
    {
        this.documento = documento;
    }
    
    /**
     * @return the telefono
     */
    public Long getTelefono() 
    {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Long telefono) 
    {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) 
    {
        this.correo = correo;
    }

    /**
     * @return the direccion
     */
    public String getDireccion()
    {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) 
    {
        this.login = login;
    }

    /** Comentario para asegurar que el classPath se actualice
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /** Comentario para asegurar que el classPath se actualice
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) 
    {
        this.contrasena = contrasena;
    }

    /**
     * @return the contratos
     */
    public List<ContratoEntity> getContratos() {
        return contratos;
    }

    /**
     * @param contratos the contratos to set
     */
    public void setContratos(List<ContratoEntity> contratos) {
        this.contratos = contratos;
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
     * @return penalizado.
     */
    public boolean isPenalizado() {
        return penalizado;
    }

    /**
     * @param penalizado El nuevo penalizado.
     */
    public void setPenalizado(boolean penalizado) 
    {
        this.penalizado = penalizado;       
    }    

    /**
     * @return the valoraciones
     */
    public Double getValoracion() {
        return valoracion;
    }

    /**
     * @param valoracion the valoraciones to set
     */
    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }
    
    public void agregarProducto(ProductoEntity pProducto)
    {
        productos.add(pProducto);
    }
    
    public void removerProducto(ProductoEntity pProducto)
    {
        productos.remove(pProducto);
    }
    
    public void agregarContrato(ContratoEntity pContrato)
    {
        contratos.add(pContrato);
    }
    
    public void removerContrato(ContratoEntity pContrato)
    {
        contratos.remove(pContrato);
    }

    /**
     * @return the bonos
     */
    public List<BonoEntity> getBonos() {
        return bonos;
    }

    /**
     * @param bonos the bonos to set
     */
    public void setBonos(List<BonoEntity> bonos) {
        this.bonos = bonos;
    }
    
    /**
     * Agrega un bono al proveedor
     * @param bono 
     */
    public void addBono(BonoEntity bono){
        this.bonos.add(bono);
    }
    
    public void removeBono(BonoEntity bono){
        this.bonos.remove(bono);
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
