/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author ls.arias
 */
@Entity 
public class ServicioEntity extends BaseEntity implements Serializable{
    
    private String descripcion;
    private String tipo;
    
    public String getDescripcion()
    {
        return descripcion;
    }
    
    public String getTipo()
    {
        return tipo;
    }
    
    public void setDescripcion(String pDescripcion)
    {
        this.descripcion = pDescripcion;
    }
    
    public void setTipo(String pTipo)
    {
        this.tipo = pTipo;
    }
}
