package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.PagoEntity;

/**
 * Clase que extiende de {@link PagoDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del pago vaya a la documentacion de {@link PagoDTO}
 * @author Cristian M. Amaya (cm.amaya10)
 */
public class PagoDetailDTO extends PagoDTO{
    
     /**
     * Constructor por defecto
     */
    public PagoDetailDTO(){
        
    }
    
        /**
     * Crea un objeto PagoDetailDTO a partir de un objeto PagoEntity
     * incluyendo los atributos de PagoDTO.
     *
     * @param entity Entidad PagoEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public PagoDetailDTO(PagoEntity entity){
        super(entity);
    }

}
