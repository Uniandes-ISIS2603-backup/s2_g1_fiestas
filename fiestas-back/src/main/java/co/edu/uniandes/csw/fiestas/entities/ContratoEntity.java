/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author mc.gonzalez15
 */
@Entity
public class ContratoEntity extends BaseEntity implements Serializable{

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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }

    public String getTerminos() {
        return terminos;
    }

    public void setTerminos(String terminos) {
        this.terminos = terminos;
    }


    
    
}
