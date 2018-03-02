/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * 
 * @author af.losada
 */
@Entity
public class ProductoEntity extends BaseEntity implements Serializable
{

    private Integer precio;
    private String descripcion;
    private String incluye;
    private Integer personal;
    
    @PodamExclude
    @ManyToOne
    private ServicioEntity servicio;
    

    /**
     * @return the precio
     */
    public Integer getPrecio() 
    {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Integer precio) 
    {
        this.precio = precio;
    }

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
     * @return the incluye
     */
    public String getIncluye() 
    {
        return incluye;
    }

    /**
     * @param incluye the incluye to set
     */
    public void setIncluye(String incluye) 
    {
        this.incluye = incluye;
    }

    /**
     * @return the personal
     */
    public Integer getPersonal() 
    {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(Integer personal) 
    {
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
