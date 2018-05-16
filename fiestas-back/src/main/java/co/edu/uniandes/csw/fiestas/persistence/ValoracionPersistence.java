/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import java.util.ArrayList;
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
      
       public List<ValoracionEntity> findValoracionesPorProveedor(long idProveedor)
      {
        Query q = em.createNativeQuery("select valoraciones_id from productoEntity_valoracionEntity where productoEntity_id="+idProveedor);
        List<Long>lista=q.getResultList();
        List<ValoracionEntity> listVal = new ArrayList<>();
        for(long id: lista){
            listVal.add(em.find(ValoracionEntity.class, id));
        }
        return listVal;
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
               //Query q = em.createNativeQuery("delete from productoEntity_valoracionEntityy where valoraciones_id="+id);
}
