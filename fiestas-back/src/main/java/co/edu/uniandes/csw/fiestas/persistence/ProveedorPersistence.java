/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author nm.hernandez10
 */
@Stateless
public class ProveedorPersistence
{
    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;
    
    public ProveedorEntity find(Long id)
    {
        return em.find(ProveedorEntity.class, id);
    }
    public List<ProveedorEntity> findAll()
    {
        Query q = em.createQuery("select u from ProveedorEntity u");
        return q.getResultList();
    }
    
    public boolean loginRepetido(String login){
        Query q = em.createQuery("select u from ProveedorEntity u where u.login = ?1");
        q.setParameter(1, login); 
        return !q.getResultList().isEmpty();
    }
    
    public ProveedorEntity create (ProveedorEntity entity)
    {
        em.persist(entity);
        return entity;
    }
    
    public ProveedorEntity update (ProveedorEntity entity)
    {
        return em.merge(entity);
    }
    
    public void delete (Long id){
        ProveedorEntity entity = em.find(ProveedorEntity.class, id);
        em.remove(entity);
    }
}
