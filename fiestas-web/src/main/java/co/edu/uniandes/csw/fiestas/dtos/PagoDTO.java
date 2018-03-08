package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import co.edu.uniandes.csw.fiestas.enums.Estado;
import co.edu.uniandes.csw.fiestas.enums.MetodoDePago;

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
    private Estado estado;
    private MetodoDePago metodoDePago;
    private int valor;

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
    public PagoDTO(PagoEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.estado = entity.getEstado();
            this.metodoDePago = entity.getMetodoDePago();
            this.realizado = entity.isRealizado();
            this.valor=entity.getValor();
        }
    }

    /**
     * Convierte un objeto PagoDTO a PagoEntity.
     *
     * @return Nueva objeto PagoEntity.
     *
     */
    public PagoEntity toEntity() {
        PagoEntity entity = new PagoEntity();
        entity.setId(this.id);
        entity.setEstado(estado);
        entity.setMetodoDePago(metodoDePago);
        entity.setRealizado(this.realizado);
        entity.setValor(valor);
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
    public void setId(Long id) {
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
    public Estado getEstado() {
        return estado;
    }

    /**
     * Asigna el nuevo estado del proceso de pago
     *
     * @param estado nuevo del pago
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Retorna el metodo de pago
     *
     * @return metodo de pago usado en el pago
     */
    public MetodoDePago getMetodoPago() {
        return metodoDePago;
    }

    /**
     * Asigna el metodo de pago al proceso
     *
     * @param metodo de pago
     */
    public void setMetodoPago(MetodoDePago metodo) {
        this.metodoDePago = metodo;
    }
    
        /**
     * Retorna el valor del pago
     *
     * @return valor del pago
     */
    public int getValor(){
        return valor;
    }
   
     /**
     * Asigna el valor del pago
     *
     * @param valor del pago
     */
    public void setValor(int valor){
        this.valor=valor;
    }
}
