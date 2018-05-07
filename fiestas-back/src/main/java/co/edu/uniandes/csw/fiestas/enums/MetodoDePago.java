package co.edu.uniandes.csw.fiestas.enums;

/**
 * Enumeracion metodo de mapo para la clase Pago.
 * @author cm.amaya10
 */
public enum MetodoDePago {
    PSE("PSE"),TARJETA_CREDITO("Tarjeta de Credito"),CONSIGNACION("Consignacion");
    
     private final String value;

    MetodoDePago(final String value) {
        this.value = value;
    }

    /**
     * Retorna el valor asociado al Metodo de Pago
     * @return valor asociado al metodo de pago
     */
    @Override
    public String toString(){
        return value;
    }
}
