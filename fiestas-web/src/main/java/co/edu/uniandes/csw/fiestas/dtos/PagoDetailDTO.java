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
    
    private EventoDTO evento;
    
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
        this.evento=new EventoDTO(entity.getEvento());
    }
     
     /**
     * Convierte un objeto PagoDetailDTO a PagoEntity incluyendo los
     * atributos de PagoDTO.
     *
     * @return Nueva objeto PagoEntity.
     *
     */   
    @Override
    public PagoEntity toEntity(){
        PagoEntity entity=super.toEntity();
        EventoEntity eventoEntity= this.evento.toEntity();
        entity.setEvento(eventoEntity);
        return entity;
    }
    
    /**
     * Retorna el evento correspondiente al pago.
     * @return Evento relacionado.
     */

    public EventoDTO getEvento(){
        return evento;
    }
    

    /**
     * Se asigna un evento al pago.
     * @param evento nuevo a asignar
     */

    public void setEvento(EventoDTO evento){
        this.evento=evento;
    }

}
