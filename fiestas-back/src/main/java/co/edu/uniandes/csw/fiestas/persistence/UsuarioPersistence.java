/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
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
public class UsuarioPersistence
{
    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;
    
    public UsuarioEntity find(String login)
    {
        Query q = em.createNativeQuery("SELECT * FROM USUARIOENTITY WHERE login = '" + login + "'", UsuarioEntity.class);
        UsuarioEntity extraido = null;
        try
        {
            extraido = (UsuarioEntity)q.getResultList().get(0);
        }
        catch(Exception e)
        {
            
        }
        return extraido;
    }
    public List<UsuarioEntity> findAll()
    {
        Query q = em.createQuery("select u from UsuarioEntity u");
        return q.getResultList();
    }
    
    public UsuarioEntity create (UsuarioEntity entity)
    {
        em.persist(entity);
        return entity;
    }
    
    public UsuarioEntity update (UsuarioEntity entity)
    {
        UsuarioEntity extraido = find(entity.getLogin());
        entity.setId(extraido.getId());
        return em.merge(entity);
    }
    
    public void delete (String login){
        UsuarioEntity extraido = find(login);
        em.remove(extraido);
    }
}
