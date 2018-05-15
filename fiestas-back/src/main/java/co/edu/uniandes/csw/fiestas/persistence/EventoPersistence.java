package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Evento. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author cm.amaya10
 */
@Stateless
public class EventoPersistence {

    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;

    /**
     * Buscar un evento
     *
     * Busca si hay alguna evento asociada a un cliente y con un ID específico
     *
     * @param clienteid El ID del cliente con respecto al cual se busca
     * @param id El ID del evento buscado
     * @return El evento encontrado o null. Nota: Si existe uno o más eventos
     * devuelve siempre el primer que encuentra
     */
    public EventoEntity find(Long clienteid, Long id) {
        TypedQuery<EventoEntity> q = em.createQuery("select p from EventoEntity p where (p.cliente.id = :clienteid) and (p.id = :id)", EventoEntity.class);
        q.setParameter("clienteid", clienteid);
        q.setParameter("id", id);
        List<EventoEntity> results = q.getResultList();
        EventoEntity evento = null;
        if (results == null) {
            evento = null;
        } else if (results.isEmpty()) {
            evento = null;
        } else if (results.size() >= 1) {
            evento = results.get(0);
        }

        return evento;
    }

    /**
     * Retorna todos los eventos en la base de datos
     * @return devulve todas las entidades eventos de la base de datos
     */
    public List<EventoEntity> findAll() {
        Query q = em.createQuery("select u from EventoEntity u");
        return q.getResultList();
    }

    /**
     * Crea un evento en la base de datos
     *
     * @param entidad objeto evento que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public EventoEntity create(EventoEntity entidad) {
        em.persist(entidad);
        return entidad;
    }

    /**
     * Actualiza una evento.
     *
     * @param entidad: el evento que viene con los nuevos cambios. Por ejemplo
     * el nombre pudo cambiar. En ese caso, se haria uso del método update.
     * @return el evento con los cambios aplicados.
     */
    public EventoEntity update(EventoEntity entidad) {
        return em.merge(entidad);
    }

    /**
     * Borra un evento de la base de datos recibiendo como argumento el id del
     * evento
     *
     * @param id: id correspondiente al evento a borrar.
     */
    public void delete(Long id) {
        EventoEntity entity = em.find(EventoEntity.class, id);
        em.remove(entity);
    }
}
