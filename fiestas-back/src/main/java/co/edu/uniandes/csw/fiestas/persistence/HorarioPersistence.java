/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author df.nino10
 */
@Stateless
public class HorarioPersistence {
    @PersistenceContext(unitName="FiestasPU")
    protected EntityManager em;
    
    public HorarioEntity find(Long id){
        HorarioEntity entity= em.find(HorarioEntity.class, id);
        return entity;
    }
    
    public List<HorarioEntity> findAll(){
        Query q = em.createQuery("select u from HorarioEntity u");
        return q.getResultList();
    }
    
    public HorarioEntity create (HorarioEntity entity){
        em.persist(entity);
        return entity;
    }
    
    public HorarioEntity update (HorarioEntity entity){
        return em.merge(entity);
    }
    
    public void delete (Long id){
        HorarioEntity entity = em.find(HorarioEntity.class, id);
        em.remove(entity);
    } 
}
