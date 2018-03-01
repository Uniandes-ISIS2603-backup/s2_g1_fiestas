/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;
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
 * @author af.losada
 */
@Stateless
public class TematicaPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(TematicaEntity.class.getName());

    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;

    public TematicaEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando tematica con id={0}", id);
        return em.find(TematicaEntity.class, id);
    }

    public TematicaEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando tematica con name= ", name);
        TypedQuery<TematicaEntity> q
                = em.createQuery("select u from TematicaEntity u where u.name = :name", TematicaEntity.class);
        q = q.setParameter("name", name);
        return q.getSingleResult();
    }

    public List<TematicaEntity> findAll() {
        LOGGER.info("Consultando todos las tematicas");
        Query q = em.createQuery("select u from TematicaEntity u");
        return q.getResultList();
    }

    public TematicaEntity create(TematicaEntity entity) {
        LOGGER.info("Creando una tematica nueva");
        em.persist(entity);
        LOGGER.info("Pematica creada");
        return entity;
    }

    public TematicaEntity update(TematicaEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando tematica con id={0}", entity.getId());
        return em.merge(entity);
    }
    
    public TematicaEntity update(TematicaEntity entity, List<ServicioEntity> servicios) {
        LOGGER.log(Level.INFO, "Actualizando tematica con id={0}", entity.getId());
        entity.setServicio(servicios);
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando tematica con id={0}", id);
        TematicaEntity entity = em.find(TematicaEntity.class, id);
        em.remove(entity);
    }
}
