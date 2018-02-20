/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ls.arias
 */
@Stateless
public class ValoracionPersistence {
    
     @PersistenceContext(unitName = "FiestasPU")
     protected EntityManager em;
    
     public ValoracionEntity find(long id)
     {
         return em.find(ValoracionEntity.class, id);
     }
     
      public List<ValoracionEntity> findAll()
      {
        Query q = em.createQuery("select u from ValoracionEntity u");
        return q.getResultList();
      }
      
      public ValoracionEntity create (ValoracionEntity entity)
      {
        em.persist(entity);
        return entity;
      }
      
      public ValoracionEntity update (ValoracionEntity entity)
      {
        return em.merge(entity);
      }
      
       public void delete (Long id){
        ValoracionEntity entity = em.find(ValoracionEntity.class, id);
        em.remove(entity);
    }
    
}
