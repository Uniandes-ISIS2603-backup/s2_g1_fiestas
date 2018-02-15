/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 *
 * @author af.losada
 */
public class TematicaDTO 
{
    
    private long id;
    private String descripcion;
    
    /*
    Getters
    */
    public String darDescripcion()
    {
        return descripcion;
    }
    
    public long darID()
    {
        return id;
    }
    
    /*
    Setters
    */
    
    public void setID(long pID)
    {
        id = pID;
    }
    
    public void setDescripcion(String pDesc)
    {
        descripcion = pDesc;
    }
}
