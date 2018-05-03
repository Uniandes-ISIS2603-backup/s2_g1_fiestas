package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link EventoDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del evento vaya a la documentacion de {@link EventoDTO}
 *
 * @author Cristian M. Amaya (cm.amaya10)
 */
public class EventoDetailDTO extends EventoDTO {

    private List<PagoDTO> pagos;
    private List<ContratoDTO> contratos;
    private TematicaDTO tematica;

    /**
     * Constructor por defecto
     */
    public EventoDetailDTO() {
        super();
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
        if (entity.getTematica() != null) {
            this.tematica = new TematicaDTO(entity.getTematica());
        } else {
            entity.setTematica(null);
        }
        if (entity.getPagos() != null) {
            pagos = new ArrayList<>();
            for (PagoEntity entityPago : entity.getPagos()) {
                pagos.add(new PagoDTO(entityPago));
            }
        }
        if (entity.getContratos() != null) {
            contratos = new ArrayList<>();
            for (ContratoEntity entityContrato : entity.getContratos()) {
                contratos.add(new ContratoDTO(entityContrato));
            }
        }
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
        if (contratos != null) {
            List<ContratoEntity> contratosEntity = new ArrayList<>();
            for (ContratoDTO contrato : contratos) {
                contratosEntity.add(contrato.toEntity());
            }
            entity.setContratos(contratosEntity);
        }
        if (pagos != null) {
            List<PagoEntity> pagosEntity = new ArrayList<>();
            for (PagoDTO pago : pagos) {
                pagosEntity.add(pago.toEntity());
            }
            entity.setPagos(pagosEntity);
        }
        if (this.getTematica() != null) {
            entity.setTematica(tematica.toEntity());
        }
        return entity;
    }

    /**
     * Se retorna los pagos asociado al evento
     *
     * @return Pagos del evento.
     */
    public List<PagoDTO> getPagos() {
        return pagos;
    }

    /**
     * Asigna los pagos al evento
     *
     * @param pagos del evento
     */
    public void setPagos(List<PagoDTO> pagos) {
        this.pagos = pagos;
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
