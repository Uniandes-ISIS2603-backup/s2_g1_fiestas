/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.enums;

/**
 * Enumeracion de estados para la clase Pago.
 *
 * @author cm.amaya10
 */
public enum Estado {
    CONFIRMADO(300), EN_REVISION(100), RECHAZADO(200);

    private int value;

    Estado(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Estado parse(int id) {
        Estado estado = null;
        for (Estado item : Estado.values()) {
            if (item.getValue() == id) {
                estado = item;
                break;
            }

        }
        return estado;
    }

}
