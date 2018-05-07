package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.EventoPersistence;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Evento.
 *
 * @author cm.amaya10
 */
@Stateless
public class EventoLogic {

    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());

    @Inject
    private EventoPersistence persistence;

    @Inject
    private ContratoLogic contratoLogic;

    @Inject
    private ClienteLogic clienteLogic;

    /**
     * Devuelve todos los eventos de un cliente que hay en la base de datos.
     *
     * @param clienteid identificador del cliente
     * @return Lista de entidades de tipo evento.
     */
    public List<EventoEntity> getEventos(Long clienteid) {
        LOGGER.info("Inicia proceso de consultar todos los eventos");
        ClienteEntity cliente = clienteLogic.getCliente(clienteid);
        LOGGER.info("Termina proceso de consultar todos los eventos");
        return cliente.getEventos();
    }

    /**
     * Busca un evento de un cliente por ID
     *
     * @param id El id del evento a buscar
     * @param clienteid identificador del cliente
     * @return El evento encontrado, null si no lo encuentra.
     */
    public EventoEntity getEvento(Long clienteid, Long id) {
        return persistence.find(clienteid, id);
    }

    /**
     * Se encarga de crear un evento de un cliente en la base de datos.
     *
     * @param clienteid identificador del cliente
     * @param entity Objeto de EventoEntity con los datos nuevos
     * @return Objeto de EventoEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no se cumple reglas de negocio
     */
    public EventoEntity createEvento(Long clienteid, EventoEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un evento ");
        ClienteEntity cliente = clienteLogic.getCliente(clienteid);

        EventoEntity buscado = getEvento(clienteid, entity.getId());
        if (buscado != null) {
            throw new BusinessLogicException("Ya existe el evento con este Id");
        }

        //Primera regla de negocio: El evento debe tener plazo minimo de una semana
        int noOfDays = 7;
        Date dateOfOrder = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = calendar.getTime();

        if (entity.getFecha().before(date)) {
            throw new BusinessLogicException("El evento debe tener un plazo minimo de 7 dias");
        }

        //Segunda regla: El numero de invitados es un numero entero positivo mayor de 0
        if (entity.getInvitados() < 1) {
            throw new BusinessLogicException("El evento tiene un numero de invitados no valido");
        }

        //Tercera regla: El nombre no puede estar vacio
        if (entity.getNombre() == null) {
            throw new BusinessLogicException("El evento debe ter un nombre");
        }

        entity.setCliente(cliente);
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Evento.
     *
     * @param clienteid identificador del cliente
     * @param entity Instancia de EventoEntity con los nuevos datos.
     * @return Instancia de EventoEntity con los datos actualizados.
     * @throws BusinessLogicException - Error de lógica
     */
    public EventoEntity updateEvento(Long clienteid, EventoEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar evento con id={0}", entity.getId());
        ClienteEntity cliente = clienteLogic.getCliente(clienteid);

        //Primera regla de negocio: El evento debe tener plazo minimo de una semana
        int noOfDays = 7;
        Date dateOfOrder = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfOrder);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = calendar.getTime();

        if (entity.getFecha().before(date)) {
            throw new BusinessLogicException("El evento debe tener un plazo minimo de 7 dias");
        }

        //Segunda regla: El numero de invitados es un numero entero positivo mayor de 0
        if (entity.getInvitados() < 1) {
            throw new BusinessLogicException("El evento tiene un numero de invitados no valido");
        }

        //Tercera regla: El nombre no puede estar vacio
        if (entity.getNombre() == null) {
            throw new BusinessLogicException("El evento debe ter un nombre");
        }

        entity.setCliente(cliente);

        EventoEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar evento con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar un evento por ID
     *
     * @param clienteid identificador del cliente
     * @param id El ID del evento a eliminar
     */
    public void deleteEvento(Long clienteid, Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar evento con id={0}", id);
        EventoEntity old = getEvento(clienteid, id);
        persistence.delete(old.getId());
        LOGGER.log(Level.INFO, "Termina proceso de borrar evento con id={0}", id);
    }

    /**
     * Añadir contrato a un evento por id
     *
     * @param contratoId id del contrato a añadir
     * @param eventoId id del evento en el cual se asignara el contrato
     * @param clienteid identificador del cliente
     * @return entidad del contato agregado
     */
    public ContratoEntity addContrato(Long clienteid, Long eventoId, Long contratoId) {
        EventoEntity evento = persistence.find(clienteid, eventoId);
        ContratoEntity contrato = contratoLogic.getContrato(contratoId);
        contrato.setEvento(evento);
        return contrato;
    }

    /**
     * Borrar contrato de un evento
     *
     * @param contratoId id del contrato a borrar
     * @param eventoId id del evento en el cual se borrara el contrato
     * @param clienteid identificador del cliente
     */
    public void removeContrato(Long clienteid, Long eventoId, Long contratoId) {
        EventoEntity evento = persistence.find(clienteid, eventoId);
        ContratoEntity contrato = contratoLogic.getContrato(contratoId);
        contrato.setEvento(null);
        evento.getContratos().remove(contrato);
    }

    /**
     * Reemplazar los contratos de un evento
     *
     * @param eventoId id del evento al que se le cambiaran los contratos
     * @param clienteid identificador del cliente
     * @param contratos Lista de contratos que se quiere actualizar
     * @return Lista de contratos actualizados
     */
    public List<ContratoEntity> replaceContratos(Long clienteid, Long eventoId, List<ContratoEntity> contratos) {
        EventoEntity evento = persistence.find(clienteid, eventoId);
        List<ContratoEntity> listaContratos = evento.getContratos();
        for (ContratoEntity contrato : listaContratos) {
            if (contratos.contains(contrato)) {
                contrato.setEvento(evento);
            } else if (contrato.getEvento() != null && contrato.getEvento().equals(evento)) {
                contrato.setEvento(null);
            }
        }
        return contratos;
    }

    /**
     * Retorna un contrato asociado a un evento
     *
     * @param eventoId El id del evento a buscar
     * @param clienteId identificador del cliente
     * @param contratoId El id del contrato a buscar
     * @return El contrato encontrado en el evento
     * @throws BusinessLogicException Si el contrato no se encuentra en el
     * evento
     */
    public ContratoEntity getContrato(Long clienteId, Long eventoId, Long contratoId) throws BusinessLogicException {
        List<ContratoEntity> contratos = getEvento(clienteId, eventoId).getContratos();
        ContratoEntity contrato = contratoLogic.getContrato(contratoId);
        int index = contratos.indexOf(contrato);
        if (index >= 0) {
            return contratos.get(index);
        }
        throw new BusinessLogicException("El contrato no está asociado al evento");
    }

    /**
     * Retorna todos los contratos del evento
     *
     * @param clienteId identificador del cliente
     * @param eventoId El id del evento a buscar
     * @return la lista de contratos del evento
     */
    public List<ContratoEntity> getContratos(Long clienteId, Long eventoId) {
        return getEvento(clienteId, eventoId).getContratos();
    }

}
