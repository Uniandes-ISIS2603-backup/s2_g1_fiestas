package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;

/**
 * Clase que extiende de {@link BlogDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del blog vaya a la documentacion de {@link BlogDTO}
 * @author mc.gonzalez15
 */
public class BlogDetailDTO extends BlogDTO {
    
    /**
     * Cliente dueño del blog
     */
    private ClienteDTO cliente;
    
    /**
     * Evento del que se escribe
     */
    private EventoDTO evento;

    /**
     * Método constructor vacío
     */
    public BlogDetailDTO()
    {
        super();
    }
    
     /**
     * Crea un objeto BlogDetailDTO a partir de un objeto BlogEntity
     * incluyendo los atributos de BlogDTO.
     *
     * @param entity Entidad BlogEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public BlogDetailDTO (BlogEntity entity) 
    {
        super(entity);
        cliente = new ClienteDTO(entity.getCliente());
        evento = new EventoDTO(entity.getEvento());
       
    }

    /**
     * Convierte un objeto BlogDetailDTO a BlogEntity incluyendo los
     * atributos de BlogDTO.
     *
     * @return Nueva objeto BlogEntity.
     *
     */
    @Override
    public BlogEntity toEntity() {
        BlogEntity entity = super.toEntity();
        entity.setCliente(this.cliente.toEntity());
        entity.setEvento(this.evento.toEntity());
        
        return entity;
    }
    
    /**
     * Retorna el usuario 
     * @return usuario
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Asigna el usuario
     * @param cliente - Es el usuario dueño del Blog 
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Retorna el evento
     * @return evento
     */
    public EventoDTO getEvento() {
        return evento;
    }

    /**
     * Asigna el evento

     * @param evento - Es el evento del Blog 

     */
    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }
    
    
    
}
