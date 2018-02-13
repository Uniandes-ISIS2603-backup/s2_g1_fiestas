
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
 *      "id": Integer
 *      "descripcion": String
 *   }
 * </pre>
 * Por ejemplo una entidad de Servicio se representa asi:<br>
 * <p>
 * <pre>
 *
 *   {
 *      "id": 97971354
 *      "descripcion": Boda
 *   }
 *
 * </pre>
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


