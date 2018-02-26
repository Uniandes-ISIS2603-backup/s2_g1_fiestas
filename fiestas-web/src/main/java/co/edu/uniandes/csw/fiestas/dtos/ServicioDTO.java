/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;


/**
 * ServicioDTO Objeto tranferencia para el servicio.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "descripcion": string,
 *      "tipo": string,
 *   }
 * </pre>
 * Por ejemplo, un servicio se representa asi:<br>
 * 
 * <pre>
 *   {
 *      "id": 542,
 *      "decripcion": "Carrito de perros calientes",
 *      "tipo": "comida",
 *   }
 *
 * </pre>
 * @author ls.arias
 */
public class ServicioDTO {
    
    private long id;
    private String descripcion;
    private String tipo;
    
    /**
     * Constructor por defecto
     */
    public ServicioDTO()
    {
        
    }
    
    /**
     * @return El id del servicio.
     */
    public long getId() {
        return id;
    }
    
    /**
     * @return La descripcion del servicio.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return El tipo de servicio.
     */
    public String getTipo() {
        return tipo;
    }
    
     /**
     * @param id El nuevo id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
    * @param descripcion La nueva descripcion.
    */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
    * @param tipo El nuevo tipo del servicio.
    */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
