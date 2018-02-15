
import java.util.Date;


/*
 * EventoDTO Objeto de transferencia de datos de Eventos.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
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
 *      "fecha": "06/12/2017 15:30",
 *      "descripcion": "Bautizo de Luciana",
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

    }

    /**
     * @return El ID del evento
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id El nuevo ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return La fecha del evento
     */

    public Date getFecha() {
        return fecha;
    }
   
    /**
     * @param fecha del evento
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return la descripcion del evento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion nueva del evento
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return la descripcion del evento
     */
    public String getCelebrado() {
        return celebrado;
    }

    /**
     * Asignacion del celebrado
     *
     * @param celebrado del evento
     */
    public void setCelebrado(String celebrado) {
        this.celebrado = celebrado;
    }

    /**
     * @return lugar del evento
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Asignacion del lugar del evento
     *
     * @param lugar del evento
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return numeros de invitados del evento
     */
    public Integer getInvitados() {
        return invitados;
    }

     /**
     * Asignacion del numeros de invitados
     *
     * @param numero de invitados del evento
     */
    public void setInvitados(Integer numInvitados) {
        this.invitados = numInvitados;

    }
}
