package co.edu.uniandes.csw.fiestas.enums;

/**
 * Enumeracion de estados para la clase Pago.
 *
 * @author cm.amaya10
 */
public enum Estado {
    CONFIRMADO("Confirmado"),CANCELADO("Cancelado"),EN_REVISION("En Revision"), RECHAZADO("Rechazado");

    public static String toSting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private final String value;

    /**
     * Metodo constructor
     * @param value Valor a asignar
     */
    Estado(final String value) {
        this.value = value;
    }

    /**
     * Metodo que retornar el valor asociado al Estado
     * @return retorna el valor asociado al Estado
     */
    @Override
    public String toString(){
        return value;
    }
}
