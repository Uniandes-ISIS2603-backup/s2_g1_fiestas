/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mc.gonzalez15
 */
@Stateless
public class ContratoPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ContratoPersistence.class.getName());

    @PersistenceContext(unitName = "CompanyPU")
    protected EntityManager em;

    public ContratoEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando contrato con id={0}", id);
        return em.find(ContratoEntity.class, id);
    }

    public ContratoEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando contrato con name= ", name);
        TypedQuery<ContratoEntity> q
                = em.createQuery("select u from ContratoEntity u where u.name = :name", ContratoEntity.class);
        q = q.setParameter("name", name);
        return q.getSingleResult();
    }

    public List<ContratoEntity> findAll() {
        LOGGER.info("Consultando todos los contratos");
        Query q = em.createQuery("select u from ContratoEntity u");
        return q.getResultList();
    }

    public ContratoEntity create(ContratoEntity entity) {
        LOGGER.info("Creando un contrato nuevo");
        em.persist(entity);
        LOGGER.info("Contrato creado");
        return entity;
    }

    public ContratoEntity update(ContratoEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando contrato con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando contrato con id={0}", id);
        ContratoEntity entity = em.find(ContratoEntity.class, id);
        em.remove(entity);
    }
    
}
