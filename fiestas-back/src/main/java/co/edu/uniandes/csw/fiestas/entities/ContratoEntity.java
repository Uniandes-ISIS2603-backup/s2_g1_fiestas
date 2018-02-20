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
private String tyc;



    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getTyc() {
        return tyc;
    }

    public void setTyc(String tyc) {
        this.tyc = tyc;
    }

   


    
    
}
