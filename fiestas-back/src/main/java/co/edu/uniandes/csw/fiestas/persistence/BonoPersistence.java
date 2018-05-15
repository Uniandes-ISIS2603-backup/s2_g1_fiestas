/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Bono. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * 
 * @author df.nino10
 */
@Stateless
public class BonoPersistence {
    
   private static Logger LOGGER = Logger.getLogger(BonoPersistence.class.getName());
   
    @PersistenceContext(unitName = "FiestasPU")
    private EntityManager em;
    
    /**
     * Encuentra el bono con el id pasado por parámetro. 
     * @param id
     * @return bono con el id pasdo por parámetro o null si no lo encuentra.
     */
    public BonoEntity find(long id) {
        getLOGGER().log(Level.INFO, "Consultando Bono con id={0}", id);
         BonoEntity entity = getEm().find(BonoEntity.class, id);
        return entity;
    }
    
    /**
     * Encuentra los bonos del proveedor cuyo id se pasa por parámetro.
     * @param proveedorId
     * @return lista de bonos. 
     */
    public List<BonoEntity> findAllC(long proveedorId) {
        getLOGGER().log(Level.INFO, "Consultando Bono cuyo dueño es el proveedor con id={0}", proveedorId);
        Query q = getEm().createNativeQuery("SELECT * FROM BONOENTITY WHERE PROVEEDOR_ID = ?1", BonoEntity.class);
        q.setParameter(1, proveedorId);
        List<BonoEntity> bE = q.getResultList();
        return bE;
    }
    
    /**
     * Encuentra el bono con el código especificado por parámetro. 
     * @param codigo del bono a buscar 
     * @return bono con el código especificado.
     */
    public BonoEntity findByCodigo(String codigo) {
        getLOGGER().log(Level.INFO, "Consultando Bono cuyo dueño es el proveedor con id={0}", codigo);
        TypedQuery<BonoEntity> q = getEm().createQuery("select u from BonoEntity u where u.codigo = :cod", BonoEntity.class);
        q = q.setParameter("cod", codigo);
        List<BonoEntity> bE = q.getResultList();
        return bE.get(0);
    }
        
    /**
     * Método para encontrar los bonos de un proveedor con diferentes motivos.
     * @param proveedorId
     * @return 
     */    
    public List<BonoEntity> findAllCByMotivo(Long proveedorId) {
        getLOGGER().log(Level.INFO, "Consultando Bono cuyo dueño es el proveedor con id={0}", proveedorId);
        Query q = getEm().createQuery("SELECT c FROM BONOENTITY WHERE c.PROVEEDOR_ID = ?1 AND c.MOTIVO in (SELECT u.MOTIVO FROM BONOENTITY GROUP BY c.MOTIVO HAVING COUNT(c.MOTIVO)=1)");
        q.setParameter(1, proveedorId);
        List<BonoEntity> bE = q.getResultList();
        return bE;
    }
        
    /**
     * Encuentra el bono con el nombre pasado por parámetro.
     * @param name del bono a buscar.
     * @return bono con el nombre buscado o null si no lo encuentra.
     */

    public BonoEntity findByName(String name) {
        getLOGGER().log(Level.INFO, "Consultando blog con name= ", name);
        TypedQuery<BonoEntity> q = getEm().createQuery("select u from BonoEntity u where u.name = :name", BonoEntity.class);
        q = q.setParameter("name", name);
        return q.getSingleResult();
    }

    /**
     * Retorna la lista de todos los bonos pasados en la base de datos.
     * @return lista de bonos
     */
    public List<BonoEntity> findAll() {
        getLOGGER().info("Consultando todos los bonos");
        Query q = getEm().createQuery("select u from BonoEntity u");
        return q.getResultList();
    }

    /**
     * Crea y agrega un bono a la base de datos. 
     * @param entity a guardar en la base de datos.
     * @return el bono creado.
     */
    public BonoEntity create(BonoEntity entity) {
        getLOGGER().info("Creando un bono nuevo");
        getEm().persist(entity);
        getLOGGER().info( "Bono creado");
        return entity;
    }

    /**
     * Actualiza un bono pasado por parámetro.
     * @param entity bono a actualizar
     * @return el bono actualizado
     */
    public BonoEntity update (BonoEntity entity) {
        getLOGGER().log(Level.INFO, "Actualizando bono con id={0}", entity.getId());
        return getEm().merge(entity);
    }

    /**
     * Elimina el bono con el id pasado por parámetro.
     * @param id 
     */
    public void delete(Long id) {
        getLOGGER().log(Level.INFO, "Borrando bono con id={0}", id);
        BonoEntity entity = getEm().find(BonoEntity.class, id);
        getEm().remove(entity);
    }

    /**
     * @return the LOGGER
     */
    public static Logger getLOGGER() {
        return LOGGER;
    }

    /**
     * @param aLOGGER the LOGGER to set
     */
    public static void setLOGGER(Logger aLOGGER) {
        LOGGER = aLOGGER;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    
}

