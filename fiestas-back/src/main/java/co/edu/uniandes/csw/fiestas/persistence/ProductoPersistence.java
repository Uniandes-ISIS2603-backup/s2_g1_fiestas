/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author af.losada
 */
public class ProductoPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(ProductoEntity.class.getName());

    @PersistenceContext(unitName = "CompanyPU")
    protected EntityManager em;

    public ProductoEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando producto con id={0}", id);
        return em.find(ProductoEntity.class, id);
    }

    public ProductoEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando producto con name= ", name);
        TypedQuery<ProductoEntity> q
                = em.createQuery("select u from ProductoEntity u where u.name = :name", ProductoEntity.class);
        q = q.setParameter("name", name);
        return q.getSingleResult();
    }

    public List<ProductoEntity> findAll() {
        LOGGER.info("Consultando todos los producto");
        Query q = em.createQuery("select u from ProductoEntity u");
        return q.getResultList();
    }

    public ProductoEntity create(ProductoEntity entity) {
        LOGGER.info("Creando un producto nuevo");
        em.persist(entity);
        LOGGER.info("Producto creado");
        return entity;
    }

    public ProductoEntity update(ProductoEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando producto con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando producto con id={0}", id);
        ProductoEntity entity = em.find(ProductoEntity.class, id);
        em.remove(entity);
    }
}
