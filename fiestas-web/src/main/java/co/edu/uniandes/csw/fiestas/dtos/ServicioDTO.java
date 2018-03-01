/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;


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
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param servicio: Es la entidad que se va a convertir a DTO
     */
    public ServicioDTO(ServicioEntity servicio) {
        this.id = servicio.getId();
        this.descripcion = servicio.getDescripcion();
        this.tipo = servicio.getTipo();

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
    
       /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ServicioEntity toEntity() {
        ServicioEntity entity = new ServicioEntity();
        entity.setId(this.id);
        entity.setDescripcion(this.descripcion);
        entity.setTipo(this.tipo);
        return entity;
    }
}
