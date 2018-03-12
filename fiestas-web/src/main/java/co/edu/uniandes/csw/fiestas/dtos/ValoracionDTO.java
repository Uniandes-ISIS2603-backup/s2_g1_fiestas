/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;

/**
 * ValoracionDTO Objeto tranferencia para la valoracion. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "comentario": string,
 *      "calificacion": number
 *   }
 * </pre>
 * Por ejemplo, una valoracion se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 542,
 *      "comentario": "Me encanta el servicio de fiestas.",
 *      "calificacion": "4.5"
 *   }
 *
 * </pre>
 * @author ls.arias
 */
public class ValoracionDTO {

    private long id;
    private String comentario;
    private Integer calificacion;
    
    /**
     * Constructor por defecto
     */
       public ValoracionDTO()
    {
        
    }
       
       /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param valoracion: Es la entidad que se va a convertir a DTO
     */
    public ValoracionDTO(ValoracionEntity valoracion) {
        this.id = valoracion.getId();
        this.comentario = valoracion.getComentario();
        this.calificacion = valoracion.getCalificacion();

    }
    
    /**
     * @return El id de la valoracion.
     */
    public long getId() {
        return id;
    }

    /**
     * @return El comentario del servicio o proveedor.
     */
    public String getComentario() {
        return comentario;
    }
    /**
     * @return La calificacion del servicio o proveedor.
     */
    public Integer getCalificacion() {
        return calificacion;
    }
    /**
     * @param id El nuevo id.
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @param comentario El nuevo comentario.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    /**
     * @param calificacion La nueva calificacion.
     */
    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
    
       /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ValoracionEntity toEntity() {
        ValoracionEntity entity = new ValoracionEntity();
        entity.setId(this.id);
        entity.setComentario(this.comentario);
        entity.setCalificacion(this.calificacion);
        return entity;
    }
    
 
}
