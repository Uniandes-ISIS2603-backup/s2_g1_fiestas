package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import java.util.List;
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
public class UsuarioPersistence
{
    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;
    
    public UsuarioEntity find(String login)
    {
        TypedQuery<UsuarioEntity> q = em.createQuery("select p from UsuarioEntity p where (p.login = :login)", UsuarioEntity.class);
        q.setParameter("login", login);
        List<UsuarioEntity> results = q.getResultList();
        UsuarioEntity usuario = null;
        if (results == null) {
            usuario = null;
        } else if (results.isEmpty()) {
            usuario = null;
        } else if (results.size() >= 1) {
            usuario = results.get(0);
        }
        return usuario;
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
