/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 * ContratoDTO Objeto de transferencia de datos de Contratos. 
 * 
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "valor": number,
 *      "terminos": string
 *      "condiciones": string
 *   }
 * </pre>
 * Por ejemplo una ciudad se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 12345,
 *      "valor": 1500000,
 *      "terminos": "Cancelaciones máximo 15 días antes. Reembolso máximo 80%",
 *      "condiciones": "No se devuelve dinero después de la fecha límite"
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
     * Método constructor
     */
    public ContratoDTO(){
        
    }
     
    /**
     * Retorna el valor del contrato
     * @return valor el valor actual
     */
    public Integer getValor() {
        return valor;
    }

    /**
     * Asigna el valor del contrato
     * @param valor el nuevo valor
     */
    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
    /**
     * Retorna el id del contrato
     * @return id la id del contrato
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id del contrato
     * @param id La nueva id
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getTyc() {
        return tyc;
    }

    public void setTyc(String tyc) {
        this.tyc = tyc;
    }
     
    
    
}
