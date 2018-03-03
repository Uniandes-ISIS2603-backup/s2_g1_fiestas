/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;


import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;

/**
 *Clase que extiende de {@link HorarioDTO} para manejar la transformación entre los objetos JSON y las entidades de la base de datos.
 *Para conocer el contenido del horario vaya a la documentación de {@link HorarioDTO}
 *
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "fecha": Date,
 *      "horaInicio": Date,
 *      "horaFin": Date,
 * 
 *     
 *      }
 *   }
 * </pre> Por ejemplo un horario se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 112344,
 *      "fecha: 01/01/12,
 *      "horaInicio": 14:00
 *      "horaFin": 20:00
 * 
 *   }
 *
 * </pre>
 *
 * @author df.nino10
 */

public class HorarioDetailDTO extends HorarioDTO {
    
    
    /**
     * Constructor por defecto
     */
    public HorarioDetailDTO(){
        super();
    }
    
        /**
     * Constructor por defecto
     * @param entity Entidad horario que se usara para generar la clase
     */
    public HorarioDetailDTO(HorarioEntity entity){
        super(entity);
    }

    
    @Override
    public HorarioEntity toEntity(){
        HorarioEntity entity = super.toEntity();
        return entity;
    }
    
}
