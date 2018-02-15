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
public class ProductoDTO
{
    long id;
    int precio;
    String descripcion;
    String incluye;
    int personal;
    /*
    Getters
    */
    
    public long darID()
    {
        return id;
    }
    
    public int darPrecio()
    {
        return precio;
    }
    
    public String darDescripcion()
    {
        return descripcion;
    }
    
    public String darIncluidos()
    {
        return incluye;
    }
    
    public int darPersonal()
    {
        return personal;
    }
    
    /*
    Setters
    */
    
    public void setID(long pID)
    {
        id = pID;
    }
    
    public void setPrecio(int pPrecio)
    {
       precio = pPrecio;
    }
    
    public void setDescripción(String pDesc)
    {
        descripcion = pDesc;
    }
    
    public void setIncluidos(String pInc)
    {
        incluye = pInc;
    }
    
    public void setPersonal(int pPer)
    {
        personal = pPer;
    }
}
