package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import java.util.Date;


/**
 * EventoDTO Objeto de transferencia de datos de Eventos.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre":string,
 *      "fecha": string,
 *      "descripcion": string,
 *      "celebrado": string,
 *      "lugar": string,
 *      "inventados": number
 *   }
 * </pre> Ejemplo de Evento:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 165,
 *      "nombre":"Bautizo de Luciana",
 *      "fecha": "06/12/2017 15:30",
 *      "descripcion": "Bautizo y cumpleaños de mi hija Luciana",
 *      "celebrado": "Luciana",
 *      "lugar": "Iglesia del 20 de Julio",
 *      "inventados": 20 
 *   }
 *
 * </pre>
 *
 * @author Cristian M. Amaya (cm.amaya10)
 */
public class EventoDTO {

    private String nombre;
    private long id;
    private Date fecha;
    private String descripcion;
    private String celebrado;
    private String lugar;
    private Integer invitados;

    /**
     * Constructor por defecto
     */
    public EventoDTO() {
        //Constructor vacio
    }
    
     /**
     * Crea un objeto EventoDTO a partir de un objeto EventoEntity.
     *
     * @param entity Entidad EventoEntity desde la cual se va a crear el nuevo
     * objeto.
     * 
     */
    public EventoDTO(EventoEntity entity){
        if(entity!=null){
            this.id=entity.getId();
            this.nombre=entity.getNombre();
            this.celebrado=entity.getCelebrado();
            this.fecha=entity.getFecha();
            this.descripcion=entity.getCelebrado();
            this.lugar=entity.getLugar();
            this.invitados=entity.getInvitados();
        }
    }
    
     /**
     * Convierte un objeto EventoDTO a EventoEntity.
     *
     * @return Nueva objeto EventoEntity.
     * 
     */
    public EventoEntity toEntity(){
        EventoEntity entity = new EventoEntity();
        entity.setNombre(this.getNombre());
        entity.setId(this.getId());
        entity.setCelebrado(this.getCelebrado());
        entity.setDescripcion(this.getDescripcion());
        entity.setFecha(this.getFecha());
        entity.setLugar(this.getLugar());
        entity.setInvitados(this.getInvitados());
        return entity;
    }

    /**
     * Metodo que retorna el ID del evento
     * 
     * @return El ID del evento
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que asigna un nuevo ID al evento
     * 
     * @param id El nuevo ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Metodo que retorna el nombre del evento
     * 
     * @return El nombre del evento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que asigna el nombre del evento
     * 
     * @param nombre El nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo que retorna la fecha del evento.
     * 
     * @return La fecha del evento
     */
    public Date getFecha() {
        return fecha;
    }
   
    /**
     * Metodo que asigna una nueva fecha al evento.
     * 
     * @param fecha del evento
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Metodo que retorna la descripcion del evento.
     * 
     * @return la descripcion del evento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo que asigna una nueva descripcion del evento.
     * 
     * @param descripcion nueva del evento
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Metodo que retorna el nombre o nombres de los celebrados.
     * 
     * @return la descripcion del evento
     */
    public String getCelebrado() {
        return celebrado;
    }

    /**
     * Metodo que asigna el nombre o nombres de los celebrados.
     *
     * @param celebrado del evento
     */
    public void setCelebrado(String celebrado) {
        this.celebrado = celebrado;
    }

    /**
     * Metodo que retorna el lugar/direccion del evento
     * 
     * @return lugar del evento
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Metodo que asigna un nuevo lugar a el evento
     *
     * @param lugar del evento
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * Metodo que retorna el numero de invitados
     * 
     * @return numeros de invitados del evento
     */
    public Integer getInvitados() {
        return invitados;
    }

     /**
     * Metodo que asigna un nuevo numero de invitados
     *
     * @param numInvitados, numero de invitados del evento
     */
    public void setInvitados(Integer numInvitados) {
        this.invitados = numInvitados;

    }
}
