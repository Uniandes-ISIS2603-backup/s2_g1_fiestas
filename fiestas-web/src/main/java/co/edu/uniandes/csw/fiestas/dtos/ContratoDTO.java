package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;

/**
 * ContratoDTO Objeto de transferencia de datos de Contratos.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "valor": number,
 *      "tyc": string
 *
 *   }
 * </pre> Por ejemplo una ciudad se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 12345,
 *      "valor": 1500000,
 *      "tyc": "Cancelaciones máximo 15 días antes. Reembolso máximo 80%",
 *
 *   }
 *
 * </pre>
 *
 * @author mc.gonzalez15
 */
public class ContratoDTO {

    /**
     * Identificador del contrato
     */
    private Long id;

    /**
     * Valor del contrato
     */
    private Integer valor;

    /**
     * Condiciones del contrato
     */
    private String tyc;

    /**
     * Estado del contrato
     */
    private String estado;

    /**
     * Método constructor por defecto
     */
    public ContratoDTO() {
//Constructor vacio
    }

    /**
     * Crea un objeto ContratoDTO a partir de un objeto ContratoEntity.
     *
     * @param entity Entidad ContratoEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public ContratoDTO(ContratoEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.valor = entity.getValor();
            this.tyc = entity.getTyc();
        }
    }

    /**
     * Retorna el valor del contrato
     *
     * @return valor el valor actual
     */
    public Integer getValor() {
        return valor;
    }

    /**
     * Asigna el valor del contrato
     *
     * @param valor el nuevo valor
     */
    public void setValor(Integer valor) {
        this.valor = valor;
    }

    /**
     * Retorna el id del contrato
     *
     * @return id la id del contrato
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id del contrato
     *
     * @param id La nueva id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna los terminos y condiciones del contrato
     *
     * @return tyc - los terminos y condiciones
     */
    public String getTyc() {
        return tyc;
    }

    /**
     * Asigna los terminos y condiciones del contrato
     *
     * @param tyc los nuevos terminos y condiciones
     */
    public void setTyc(String tyc) {
        this.tyc = tyc;
    }

    /**
     * Retorna el estado actual del contrato
     *
     * @return estado - el estado actual del contrato
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el estado actual del contrato
     *
     * @param estado el nuevo estado del contrato
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Convierte un objeto ContratoDTO a ContratoEntity.
     *
     * @return Nueva objeto ContratoEntity.
     *
     */
    public ContratoEntity toEntity() {
        ContratoEntity entity = new ContratoEntity();
        entity.setId(this.getId());
        entity.setValor(this.valor);
        entity.setTyc(this.tyc);
        entity.setEstado(this.estado);

        return entity;
    }

}
