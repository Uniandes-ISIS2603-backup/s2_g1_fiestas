package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.PagoEntity;

/**
 * PagoDTO Objeto de transferencia de datos de Pagos.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "realizado": boolean,
 *      "estado": string,
 *      "metodoDePago": string
 *   }
 * </pre> Ejemplo de Pago:<br>
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
    private int valor;

    /**
     * Constructor por defecto
     */
    public PagoDTO() {
        //Constructor vacio
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
            this.valor = entity.getValor();
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
     * Metodo que retorna el ID del pago.
     *
     * @return El ID del pago
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo que asigna el nuevo ID del pago.
     *
     * @param id El nuevo ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo que retorna un booleano que determina si el pago fue realizado.
     *
     * @return boolean representando si el pago fue realizado
     */
    public Boolean getRealizado() {
        return realizado;
    }

    /**
     * Metodo que asigna la variable realizado al pago.
     *
     * @param realizado: boolean a asignar si se realizo el pago exitosamente.
     */
    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }

    /**
     * Metodo que obtiene el estado actual del pago.
     *
     * @return Estado del proceso de pago
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Metodo que asigna el nuevo estado del proceso de pago
     *
     * @param estado estado nuevo del pago
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Metodo que retorna el metodo de pago
     *
     * @return metodo de pago usado en el pago
     */
    public String getMetodoPago() {
        return metodoDePago;
    }

    /**
     * Metodo que asigna el metodo de pago al proceso
     *
     * @param metodo de pago
     */
    public void setMetodoPago(String metodo) {
        this.metodoDePago = metodo;
    }

    /**
     * Metodo que retorna el valor del pago
     *
     * @return valor del pago
     */
    public int getValor() {
        return valor;
    }

    /**
     * Metodo que asigna el valor del pago
     *
     * @param valor del pago
     */
    public void setValor(int valor) {
        this.valor = valor;
    }
}
