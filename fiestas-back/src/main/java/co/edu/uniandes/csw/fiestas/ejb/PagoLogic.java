package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import co.edu.uniandes.csw.fiestas.enums.Estado;
import co.edu.uniandes.csw.fiestas.enums.MetodoDePago;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.PagoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de Pago.
 *
 * @author cm.amaya10
 */
@Stateless
public class PagoLogic {

    private static final Logger LOGGER = Logger.getLogger(PagoLogic.class.getName());

    @Inject
    private PagoPersistence persistence;

    @Inject
    private EventoLogic eventoLogic;

    /**
     * Devuelve todos los pagos de un evento que hay en la base de datos.
     *
     * @param eventoId identificador del evento
     * @return Lista de entidades de tipo pago.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no se cumple reglas de negocio
     */
    public List<PagoEntity> getPagos(Long eventoId) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de consultar todos los pagos");
        EventoEntity evento = eventoLogic.getEvento(eventoId);
       if (evento.getPagos() == null) {
            throw new BusinessLogicException("El evento que se consulta aún no tiene pagos");
        }
        if (evento.getPagos().isEmpty()) {
            throw new BusinessLogicException("El evento que se consulta aún no tiene pagos");
        }
        LOGGER.info("Termina proceso de consultar todos los pagos");
        return evento.getPagos();
    }

    /**
     * Busca un pago por ID
     *
     * @param id El id del evento a buscar
     * @param eventoId identificador del evento
     * @return El evento encontrado, null si no lo encuentra.
     */
    public PagoEntity getPago(Long eventoId, Long id) {
        return persistence.find(eventoId, id);
    }

    /**
     * Se encarga de crear un pago en la base de datos.
     *
     * @param entity Objeto de PagoEntity con los datos nuevos
     * @param eventoId identificador del evento
     * @return Objeto de PagoEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no se cumple reglas de negocio
     */
    public PagoEntity createPago(Long eventoId, PagoEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un pago ");
        EventoEntity evento = eventoLogic.getEvento(eventoId);
        
        String newEstado = verifyEstado(entity.getEstado());
        if (newEstado == null) {
            throw new BusinessLogicException("No existe el estado ingresado para el pago");
        } else {
            entity.setEstado(newEstado);
        }
        String newMetodo = verifyMetodoPago(entity.getMetodoDePago());
        if (newMetodo == null) {
            throw new BusinessLogicException("No existe el metodo de pago ingresado para el pago");
        } else {
            entity.setMetodoDePago(newMetodo);
        }
        if (entity.isRealizado() && !entity.getEstado().equals(Estado.CONFIRMADO.toString())) {
            throw new BusinessLogicException("Incongruencia entre estado y boolean Realizado");
        }
        entity.setEvento(evento);
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Pago.
     *
     * @param entity Instancia de PagoEntity con los nuevos datos.
     * @param eventoId identificador del evento
     * @return Instancia de PagoEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no se cumple reglas de negocio
     */
    public PagoEntity updatePago(Long eventoId, PagoEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar evento con id={0}", entity.getId());
        EventoEntity evento = eventoLogic.getEvento(eventoId);
        String newEstado = verifyEstado(entity.getEstado());
        if (newEstado == null) {
            throw new BusinessLogicException("No existe el estado ingresado para el pago");
        } else {
            entity.setEstado(newEstado);
        }
        String newMetodo = verifyMetodoPago(entity.getMetodoDePago());
        if (newMetodo == null) {
            throw new BusinessLogicException("No existe el metodo de pago ingresado para el pago");
        } else {
            entity.setMetodoDePago(newMetodo);
        }
        if (entity.isRealizado() && !entity.getEstado().equals(Estado.CONFIRMADO.toString())) {
            throw new BusinessLogicException("Incongruencia entre estado y boolean Realizado");
        }
        entity.setEvento(evento);
        PagoEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar evento con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar un pago por ID
     *@param eventoId identificador del evento
     * @param id El ID del pago a eliminar
     */
    public void deletePago(Long eventoId,Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar pago con id={0}", id);
        PagoEntity old = getPago(eventoId,id);
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar pago con id={0}", id);
    }

    /**
     * Verifica el estado de pago ingresado.
     *
     * @param estado String de estado a evaluar
     * @return la cadena correspondiente si se encuentra coincidencia, null de
     * lo contrario
     */
    public String verifyEstado(String estado) {
        if (estado.equalsIgnoreCase(Estado.CONFIRMADO.toString())) {
            return Estado.CONFIRMADO.toString();
        } else if (estado.equalsIgnoreCase(Estado.EN_REVISION.toString())) {
            return Estado.EN_REVISION.toString();
        } else if (estado.equalsIgnoreCase(Estado.RECHAZADO.toString())) {
            return Estado.RECHAZADO.toString();
        } else {
            return null;
        }
    }

    /**
     * Verifica el metodo de pago ingresado.
     *
     * @param metodo String de metodo de pago a evaluar
     * @return la cadena correspondiente si se encuentra coincidencia, null de
     * lo contrario
     */
    public String verifyMetodoPago(String metodo) {
        if (metodo.equalsIgnoreCase(MetodoDePago.CONSIGNACION.toString())) {
            return MetodoDePago.CONSIGNACION.toString();
        } else if (metodo.equalsIgnoreCase(MetodoDePago.PSE.toString())) {
            return MetodoDePago.PSE.toString();
        } else if (metodo.equalsIgnoreCase(MetodoDePago.TARJETA_CREDITO.toString())) {
            return MetodoDePago.TARJETA_CREDITO.toString();
        } else {
            return null;
        }
    }

}
