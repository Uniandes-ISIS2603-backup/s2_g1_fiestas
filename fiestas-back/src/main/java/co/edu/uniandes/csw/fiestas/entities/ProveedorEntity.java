/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

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
public class ProveedorEntity extends UsuarioEntity
{
    private boolean penalizado;
    
    
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
