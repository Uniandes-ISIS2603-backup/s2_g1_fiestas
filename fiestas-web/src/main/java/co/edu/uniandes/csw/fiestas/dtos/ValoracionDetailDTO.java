/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import java.util.List;


/**
 * Clase que extiende de {@link ValoracionDTO}
 * @author ls.arias
 * 
 * <pre>
 *   {
 *      "id": number,
 *      "comentario": string,
 *      "calificacion": number
 *   }
 * </pre>
 * Por ejemplo el atributo extra en ServicioDetail se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 524,
 *      "comentario": "Muy buen servicio",
 *      "calificacion": "4.8"
 *   }
 *
 * </pre>
 *
 */
public class ValoracionDetailDTO extends ValoracionDTO {
    
     /**
     * Constructor por defecto
     */
    public ValoracionDetailDTO() {
    }
    
       /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ValoracionEntity toEntity() {
        ValoracionEntity entity = super.toEntity();
        return entity;
    }
}
