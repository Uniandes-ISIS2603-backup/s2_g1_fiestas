/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 * ProductoDTO Objeto de transferencia de datos de la entidad de Producto. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el servidor.
 * <p>
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Integer,
 *      "precio": Integer,
 *      "descripcion": String,
 *      "incluye": String
 *      "personal": Integer
 *   }
 * </pre>
 * Por ejemplo una entidad de Servicio se representa asi:<br>
 * <p>
 * <pre>
 *
 *   {
 *      "id": 97971354,
 *      "precio": 80,000,
 *      "descripcion": Kit de comida para boda,
 *      "incluye": Incluye pastel y demás
 *      "personal": 4
 *   }
 *
 * </pre>
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
