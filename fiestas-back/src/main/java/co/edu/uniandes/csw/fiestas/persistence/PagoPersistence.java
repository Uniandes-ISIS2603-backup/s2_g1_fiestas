/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.PagoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cm.amaya10
 */
@Stateless
public class PagoPersistence {

    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;

    public PagoEntity find(Long id) {
        PagoEntity entidad = em.find(PagoEntity.class, id);
        return entidad;
    }

    public List<PagoEntity> findAll() {
        Query q = em.createQuery("select u from PagoEntity u");
        return q.getResultList();
    }

    public PagoEntity create(PagoEntity entidad) {
        em.persist(entidad);
        return entidad;
    }

    public PagoEntity update(PagoEntity entidad) {
        return em.merge(entidad);
    }

    public void delete(Long id) {
        PagoEntity entidad = em.find(PagoEntity.class, id);
        em.remove(entidad);
    }
}
