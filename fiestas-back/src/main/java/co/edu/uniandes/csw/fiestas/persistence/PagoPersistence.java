package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Pago. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author cm.amaya10
 */
@Stateless
public class PagoPersistence {

    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;

    /**
     * Buscar un pago
     *
     * Busca si hay algun pago asociado a un evento y con un ID específico
     *
     * @param eventoId El ID del evento con respecto al cual se busca
     * @param pagoId El ID del pago buscado
     * @return El pago encontrado o null. Nota: Si existe una o más pagos
     * devuelve siempre el primer que encuentra
     */
    public PagoEntity find(Long eventoId, Long pagoId) {
        TypedQuery<PagoEntity> q = em.createQuery("select p from PagoEntity p where (p.evento.id = :eventoid) and (p.id = :pagoid)", PagoEntity.class);
        q.setParameter("eventoid", eventoId);
        q.setParameter("pagoid", pagoId);
        List<PagoEntity> results = q.getResultList();
        PagoEntity pago = null;
        if (results == null) {
            pago = null;
        } else if (results.isEmpty()) {
            pago = null;
        } else if (results.size() >= 1) {
            pago = results.get(0);
        }

        return pago;
    }

    /**
     * Retorna todos los pagos en la base de datos
     *
     * @return devulve todas las entidades pagos de la base de datos
     */
    public List<PagoEntity> findAll() {
        Query q = em.createQuery("select u from PagoEntity u");
        return q.getResultList();
    }

    /**
     * Crear un pago
     *
     * Crea una nuevo pago con la información recibida en la entidad.
     *
     * @param entidad La entidad que representa el nuevo pago
     * @return La entidad creada
     */
    public PagoEntity create(PagoEntity entidad) {
        em.persist(entidad);
        return entidad;
    }

    /**
     * Actualizar un pago
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param entidad La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public PagoEntity update(PagoEntity entidad) {
        return em.merge(entidad);
    }

    /**
     * Eliminar un pago
     *
     * Elimina el pago asociada al ID que recibe
     *
     * @param id El ID del pago que se desea borrar
     */
    public void delete(Long id) {
        PagoEntity entidad = em.find(PagoEntity.class, id);
        em.remove(entidad);
    }
}
