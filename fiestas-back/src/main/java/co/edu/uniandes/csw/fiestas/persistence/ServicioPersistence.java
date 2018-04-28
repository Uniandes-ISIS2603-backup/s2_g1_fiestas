/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
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
public class ServicioPersistence {
    
     @PersistenceContext(unitName = "FiestasPU")
     protected EntityManager em;
    
     public ServicioEntity find(long id)
     {
         return em.find(ServicioEntity.class, id);
     }
     
      public List<ServicioEntity> findAll()
      {
        Query q = em.createQuery("select u from ServicioEntity u");
        return q.getResultList();
      }
      
      public ServicioEntity create (ServicioEntity entity)
      {
        em.persist(entity);
        return entity;
      }
      
      public ServicioEntity update (ServicioEntity entity)
      {
        return em.merge(entity);
      }
      
       public void delete (Long id){
        ServicioEntity entity = em.find(ServicioEntity.class, id);
        em.remove(entity);
    }
    
}
