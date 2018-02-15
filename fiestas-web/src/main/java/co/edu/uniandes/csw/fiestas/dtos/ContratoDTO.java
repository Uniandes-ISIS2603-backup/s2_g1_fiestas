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
private long id;

/**
 * Valor del contrato
 */
private int valor;

/**
 * Condiciones del contrato
 */
private String condiciones;

/**
 * Terminos del contrato
 */
private String terminos;

     
    /**
     * Método constructor
     */
    public ContratoDTO(){
        
    }
     
    /**
     * Retorna el valor del contrato
     * @return valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * Asigna el valor del contrato
     * @param valor 
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Retorna las condiciones del contrato
     * @return condiciones
     */
    public String getCondiciones() {
        return condiciones;
    }

    /**
     * Asigna las condiciones del contrato
     * @param condiciones 
     */
    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    /**
     * Retorna los terminos del contrato
     * @return terminos
     */
    public String getTerminos() {
        return terminos;
    }

    /**
     * Asigna los terminos del contrato
     * @param terminos 
     */
    public void setTerminos(String terminos) {
        this.terminos = terminos;
    }
     
    /**
     * Retorna el id del contrato
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id del contrato
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    
}
