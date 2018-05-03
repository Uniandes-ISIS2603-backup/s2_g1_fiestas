package co.edu.uniandes.csw.fiestas.enums;

/**
 * Enumeracion de estados para la clase Pago y Contrato.
 *
 * @author cm.amaya10
 */
public enum Estado {
    CONFIRMADO("Confirmado"), EN_REVISION("En Revision"), RECHAZADO("Rechazado");

    public static String toSting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private final String value;

    /**
     * 
     * @param value 
     */
    Estado(final String value) {
        this.value = value;
    }

    /**
     * 
     * @return string
     */
    @Override
    public String toString(){
        return value;
    }
}
