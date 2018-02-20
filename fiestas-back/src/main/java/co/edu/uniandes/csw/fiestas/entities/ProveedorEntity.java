/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.beans.PropertyChangeSupport;
import javax.persistence.Entity;

/**
 *
 * @author nm.hernandez10
 */
@Entity
public class ProveedorEntity extends UsuarioEntity
{
    private boolean penalizado;

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
}
