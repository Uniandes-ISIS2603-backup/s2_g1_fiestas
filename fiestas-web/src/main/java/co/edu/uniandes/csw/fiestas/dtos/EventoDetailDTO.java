package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import java.util.List;

/**
 * Clase que extiende de {@link EventoDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del evento vaya a la documentacion de {@link EventoDTO}
 *
 * @author Cristian M. Amaya (cm.amaya10)
 */
public class EventoDetailDTO extends EventoDTO {

    private ClienteDTO cliente;
    private PagoDTO pago;
    private List<ContratoDTO> contratos;
    private TematicaDTO tematica;

    /**
     * Constructor por defecto
     */
    public EventoDetailDTO() {

    }

    /**
     * Crea un objeto EventoDetailDTO a partir de un objeto EventoEntity
     * incluyendo los atributos de EventoDTO.
     *
     * @param entity Entidad EventoEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public EventoDetailDTO(EventoEntity entity) {
        super(entity);
    }

    /**
     * Convierte un objeto EventoDetailDTO a EventoEntity incluyendo los
     * atributos de EventoDTO.
     *
     * @return Nueva objeto EventoEntity.
     *
     */
    @Override
    public EventoEntity toEntity() {
        EventoEntity entity = super.toEntity();
        return entity;
    }

    /**
     * Se retorna el cliente asociado al evento
     *
     * @return Cliente organizador del evento.
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * Asigna el cliente asociado al evento
     *
     * @param cliente organizador del evento
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Se retorna el pago asociado al evento
     *
     * @return Pago del evento.
     */
    public PagoDTO getPago() {
        return pago;
    }

    /**
     * Asigna el pago al evento
     *
     * @param pago del evento
     */
    public void setPago(PagoDTO pago) {
        this.pago = pago;
    }

    /**
     * Se retorna un contenedor con todos los contratos del evento
     *
     * @return Lista de Contratos asociados al evento.
     */
    public List<ContratoDTO> getContratos() {
        return contratos;
    }

    /**
     * Agragar un contrato a la lista asociada al evento.
     *
     * @param contrato nuevo a agregar
     */
    public void addContrato(ContratoDTO contrato) {
        contratos.add(contrato);
    }

    /**
     * Se retorna la tematica del evento
     *
     * @return Tematica del evento.
     */
    public TematicaDTO getTematica() {
        return tematica;
    }

    /**
     * Asignar la tematica del evento
     *
     * @param tematica del evento
     */
    public void setTematica(TematicaDTO tematica) {
        this.tematica = tematica;

    }
}
