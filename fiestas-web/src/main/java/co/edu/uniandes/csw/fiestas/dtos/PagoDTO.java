
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.PagoEntity;


/**
 * EventoDTO Objeto de transferencia de datos de Eventos.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "realizado": boolean,
 *      "estado": string,
 *      "metodoDePago": string
 *   }
 * </pre> Ejemplo de Evento:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1651,
 *      "realizado": false,
 *      "estado": "En Proceso",
 *      "metodoDePago": "PSE"
 *   }
 *
 * </pre>
 *
 * @author Cristian M. Amaya (cm.amaya10)
 */
public class PagoDTO {

    private long id;
    private Boolean realizado;
    private String estado;
    private String metodoDePago;

    /**
     * Constructor por defecto
     */
    public PagoDTO() {

    }

     /**
     * Crea un objeto PagoDTO a partir de un objeto PagoEntity.
     *
     * @param entity Entidad PagoEntity desde la cual se va a crear el nuevo
     * objeto.
     * 
     */
    public PagoDTO(PagoEntity entity){
         if(entity!=null){
             this.id=entity.getId();
             this.estado=entity.getEstado();
             this.metodoDePago=entity.getMetodoDePago();
             this.realizado=entity.isRealizado();
         }
    }

    /**
     * Convierte un objeto PagoDTO a PagoEntity.
     *
     * @return Nueva objeto PagoEntity.
     * 
     */
    public PagoEntity toEntity(){
        PagoEntity entity=new PagoEntity();
        entity.setId(this.id);
        entity.setEstado(this.estado);
        entity.setMetodoDePago(this.metodoDePago);
        entity.setRealizado(this.realizado);
        return entity;
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
    public void setId(Long id) 
    {
        this.id = id;
    }

    /**
     * @return boolean representando si el pago fue realizado
     */

    public Boolean getRealizado() {
        return realizado;
    }


     /**
     * @param realizado: boolean a asignar si se realizo el pago exitosamente.
     */
    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }

    /**
     * @return Estado del proceso de pago
     */

    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el nuevo estado del proceso de pago
     *
     * @param estado nuevo del pago
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Retorna el metodo de pago
     *
     * @return metodo de pago usado en el pago
     */
    public String getMetodoPago() {
        return metodoDePago;
    }

    /**
     * Asigna el metodo de pago al proceso
     *
     * @param metodo de pago
     */
    public void setMetodoPago(String metodo) {
        this.metodoDePago = metodo;

    }
}
