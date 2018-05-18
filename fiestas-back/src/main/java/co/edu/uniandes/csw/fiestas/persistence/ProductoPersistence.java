/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import java.util.ArrayList;
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
 * @author af.losada
 */
@Stateless
public class ProductoPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(ProductoEntity.class.getName());

    @PersistenceContext(unitName = "FiestasPU")
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
    
    public ProductoEntity update(ProductoEntity entity, ServicioEntity servicio) {
        LOGGER.log(Level.INFO, "Actualizando producto con id={0}", entity.getId());
        entity.setServicio(servicio);
        return em.merge(entity);
    }

    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando producto con id={0}", id);
        ProductoEntity entity = em.find(ProductoEntity.class, id);
        em.remove(entity);
    }
    
    
    
    
    public List<ProductoEntity> findByProveedor(Long proveedorid)
    {
        TypedQuery<ProductoEntity> q = em.createQuery("select p from ProductoEntity p where (p.proveedor.id = :proveedorid)", ProductoEntity.class);
        q.setParameter("proveedorid", proveedorid);
        List<ProductoEntity> results = q.getResultList();
        return results;
    }
    
    public List<ValoracionEntity> findValoracionesPorPrducto(long idProducto)
      {
        Query q = em.createNativeQuery("select id from ValoracionEntity where producto_id="+idProducto);
        List<Long>lista=q.getResultList();
        List<ValoracionEntity> listVal = new ArrayList<>();
        for(long id: lista){
            listVal.add(em.find(ValoracionEntity.class, id));
        }
        return listVal;
      }
}
