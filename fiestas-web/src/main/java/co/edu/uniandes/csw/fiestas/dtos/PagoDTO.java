/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 *
 * @author cm.amaya10
 */
public class PagoDTO {
     private long id;
     private Boolean realizado;
     private String estado;
     private String metodoDePago;
     
     
     public PagoDTO(){
         
}

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
     * @return El ID del evento
     */

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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


    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
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


    public void setEstado(String estado) {
        this.estado = estado;
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


    public void setMetodoPago(String metodo) {
        this.metodoDePago=metodo;

    /**
     * Asigna el metodo de pago al proceso
     *
     * @param metodo de pago
     */
    public void setMetodoPago(String metodo) {
        this.metodoDePago = metodo;

    }
}
