/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.logging.Logger;

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
 *      "calificacion": number,
 *   }
 * </pre>
 * Por ejemplo, una valoracion se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 542,
 *      "comentario": "Me encanta el servicio de fiestas.",
 *      "calificacion": "4.5",
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
 
}
