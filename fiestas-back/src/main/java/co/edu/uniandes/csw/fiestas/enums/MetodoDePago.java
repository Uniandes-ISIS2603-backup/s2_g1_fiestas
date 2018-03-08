package co.edu.uniandes.csw.fiestas.enums;

/**
 * Enumeracion metodo de mapo para la clase Pago.
 * @author cm.amaya10
 */
public enum MetodoDePago {
    PSE(100),TARJETA_CREDITO(200),CONSIGNACION(300);
    
     private int value;

    MetodoDePago(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MetodoDePago parse(int id) {
        MetodoDePago metodo = null;
        for (MetodoDePago item : MetodoDePago.values()) {
            if (item.getValue() == id) {
                metodo = item;
                break;
            }

        }
        return metodo;
    }
}
