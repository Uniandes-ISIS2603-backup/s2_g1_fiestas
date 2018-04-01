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
    private String nombre;
    private String documento;
    private Long telefono;
    private String correo;
    private String direccion;
    private String login;
    private String contraseña;
    
    @PodamExclude
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContratoEntity> contratos;
    
    @PodamExclude
    @ManyToMany
    private List<ServicioEntity> servicios;
    
    @PodamExclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValoracionEntity> valoraciones;
    
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

    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) 
    {
        this.contraseña = contraseña;
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
     * @return the servicios
     */
    public List<ServicioEntity> getServicios() {
        return servicios;
    }

    /**
     * @param servicios the servicios to set
     */
    public void setServicios(List<ServicioEntity> servicios) {
        this.servicios = servicios;
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
    public List<ValoracionEntity> getValoraciones() {
        return valoraciones;
    }

    /**
     * @param valoraciones the valoraciones to set
     */
    public void setValoraciones(List<ValoracionEntity> valoraciones) {
        this.valoraciones = valoraciones;
    }
    
    public void agregarServicio(ServicioEntity pServicio)
    {
        servicios.add(pServicio);
    }
    
    public void removerServicio(ServicioEntity pServicio)
    {
        servicios.remove(pServicio);
    }
    
    public void agregarContrato(ContratoEntity pContrato)
    {
        contratos.add(pContrato);
    }
    
    public void removerContrato(ContratoEntity pContrato)
    {
        contratos.remove(pContrato);
    }
    
    public void agregarValoracion(ValoracionEntity pValoracion)
    {
        valoraciones.add(pValoracion);
    }
    
    public void removerValoracion(ValoracionEntity pValoracion)
    {
        valoraciones.remove(pValoracion);
    }
}
