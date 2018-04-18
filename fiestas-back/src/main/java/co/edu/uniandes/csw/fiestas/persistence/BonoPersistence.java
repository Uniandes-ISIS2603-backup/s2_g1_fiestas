/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
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
 * @author df.nino10
 */
@Stateless
public class BonoPersistence {
    
   private static final Logger LOGGER = Logger.getLogger(BonoPersistence.class.getName());
   
    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;
    
    public BonoEntity find(long id) {
        LOGGER.log(Level.INFO, "Consultando Bono con id={0}", id);
         BonoEntity entity = em.find(BonoEntity.class, id);
        return entity;
    }
    
    public List<BonoEntity> findAllC(long proveedorId) {
        LOGGER.log(Level.INFO, "Consultando Bono cuyo dueño es el proveedor con id={0}", proveedorId);
        Query q = em.createQuery("SELECT c FROM BONOENTITY WHERE c.PROVEEDOR_ID = ?1");
        q.setParameter(1, proveedorId);
        List<BonoEntity> bE = q.getResultList();
        return bE;
    }
    
    public BonoEntity findByCodigo(String codigo) {
        LOGGER.log(Level.INFO, "Consultando Bono cuyo dueño es el proveedor con id={0}", codigo);
        TypedQuery<BonoEntity> q = em.createQuery("select u from BonoEntity u where u.codigo = :cod", BonoEntity.class);
        q = q.setParameter("cod", codigo);
        List<BonoEntity> bE = q.getResultList();
        return bE.get(0);
    }
    
    public BonoEntity findAllPandC(long proveedorId, long contratoId) {
        Query q = em.createQuery("SELECT c FROM BONOENTITY WHERE c.PROVEEDOR_ID = ?1 AND c.CONTRATO_ID=?2");
        q.setParameter(1, proveedorId);
        q.setParameter(2, contratoId);
        List<BonoEntity> bE = q.getResultList();
        return bE.get(0);
    }
        
    /**
     * Método para encontrar los bonos de un proveedor con diferentes motivos.
     * @param proveedorId
     * @return 
     */    
    public List<BonoEntity> findAllCByMotivo(Long proveedorId) {
        LOGGER.log(Level.INFO, "Consultando Bono cuyo dueño es el proveedor con id={0}", proveedorId);
        Query q = em.createQuery("SELECT c FROM BONOENTITY WHERE c.PROVEEDOR_ID = ?1 AND c.MOTIVO in (SELECT u.MOTIVO FROM BONOENTITY GROUP BY c.MOTIVO HAVING COUNT(c.MOTIVO)=1)");
        q.setParameter(1, proveedorId);
        List<BonoEntity> bE = q.getResultList();
        return bE;
    }
        
    /**
     * return review;
     * @param name
     * @return 
     */

    public BonoEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando blog con name= ", name);
        TypedQuery<BonoEntity> q = em.createQuery("select u from BonoEntity u where u.name = :name", BonoEntity.class);
        q = q.setParameter("name", name);
        return q.getSingleResult();
    }

    public List<BonoEntity> findAll() {
        LOGGER.info("Consultando todos los bonos");
        Query q = em.createQuery("select u from BonoEntity u");
        return q.getResultList();
    }

    public BonoEntity create(BonoEntity entity) {
        LOGGER.info("Creando un bono nuevo");
        em.persist(entity);
        LOGGER.info( "Bono creado");
        return entity;
    }

    public BonoEntity update (BonoEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando bono con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando bono con id={0}", id);
        BonoEntity entity = em.find(BonoEntity.class, id);
        em.remove(entity);
    }
    
    
}

