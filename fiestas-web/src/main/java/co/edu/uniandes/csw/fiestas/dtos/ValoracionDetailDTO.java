/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;


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
        //constructor vacio
    }
    
        
     /**
     * Constructor por medio de entity
     * @param valoracion entidad que se usara para crear el DTO
     */
    public ValoracionDetailDTO(ValoracionEntity valoracion) {
        super(valoracion);
    }
    
       /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    @Override
    public ValoracionEntity toEntity() {
        return super.toEntity();
    }
}
