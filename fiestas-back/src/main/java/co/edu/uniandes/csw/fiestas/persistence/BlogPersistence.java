/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Blog. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author mc.gonzalez15
 */

@Stateless
public class BlogPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(BlogPersistence.class.getName());
    
    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;
    
    /**
     * Busca el Blog con el id pasado por parametro.
     * @param id
     * @return el Blog con el id por parámetro o null si no existe.
     */
    public BlogEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando Blog con id={0}", id);
        BlogEntity entity = em.find(BlogEntity.class, id);
        return entity;
    }
    
    /**
     * Busca los Blogs del cliente con el id pasado por parametro.
     * @param id
     * @return lista de Blogs con el id por parámetro.
     */
    public List<BlogEntity> findAllC(Long clienteId) {
        LOGGER.log(Level.INFO, "Consultando Blog cuyo dueño es el cliente con id={0}", clienteId);
        Query q = em.createQuery("SELECT c FROM BLOGENTITY WHERE c.CLIENTE_ID = ?1");
        q.setParameter(1, clienteId);
        List<BlogEntity> bE = q.getResultList();
        return bE;
    }
    
    /**
     * Busca el Blog cuyo título se pasa por parámetro.
     * @param name
     * @return el Blog con el titulo pasado por parámetro o null si no existe
     */
    
    public BlogEntity findByTitulo(String name) {
        LOGGER.log(Level.INFO, "Consultando blog con name= ", name);
        TypedQuery<BlogEntity> q = em.createQuery("select u from BlogEntity u where u.titulo = :name", BlogEntity.class);
        q = q.setParameter("name", name);
        return q.getSingleResult();
    }
    /**
     * Busca todos los Blogs
     * @return lista con todos los blogs en la base de datos.
     */
    public List<BlogEntity> findAll() {
        LOGGER.info("Consultando todos los blogs");
        Query q = em.createQuery("select u from BlogEntity u");
        return q.getResultList();
    }
    
    /**
     * Crea un Blog
     * @param entity blog a guardar
     * @return el blog creado
     */
    public BlogEntity create(BlogEntity entity) {
        LOGGER.info("Creando un blog nuevo");
        em.persist(entity);
        LOGGER.info( "Blog creado");
        return entity;
    }
    
    /**
     * Actualiza el blog pasado por parámetro.
     * @param entity a actualizar
     * @return el blog actualizado
     */
    public BlogEntity update (BlogEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando blog con id={0}", entity.getId());
        return em.merge(entity);
    }
    
    /** 
     * Elimina el blog con el id por parámetro de la base de datos.
     * @param id 
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando blog con id={0}", id);
        BlogEntity entity = em.find(BlogEntity.class, id);
        em.remove(entity);
    }
}
