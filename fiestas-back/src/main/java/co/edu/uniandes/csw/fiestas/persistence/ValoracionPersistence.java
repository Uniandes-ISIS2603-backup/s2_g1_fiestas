/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

 /**
 * Clase que maneja la persistencia para Valoracion.
 * Se conecta a través del Entity Manager de javax.persistance con la base de datos
 * SQL.
 * @author ls.arias
 */
@Stateless
public class ValoracionPersistence {

    private static final Logger LOGGER = Logger.getLogger(ValoracionPersistence.class.getName());

    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;

    /**
     * Crear una reseña
     * 
     * Crea una nueva reseña con la información recibida en la entidad.
     * @param entity La entidad que representa la nueva reseña
     * @return  La entidad creada
     */
    public ValoracionEntity create(ValoracionEntity entity) {
        LOGGER.info("Creando un valoracion nuevo");
        em.persist(entity);
        LOGGER.info("Valoracion creado");
        return entity;
    }

    /**
     * Actualizar una reseña
     * 
     * Actualiza la entidad que recibe en la base de datos
     * @param entity La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public ValoracionEntity update(ValoracionEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando valoracion con id={0}", entity.getId());
        return em.merge(entity);
    }

    /**
     * Eliminar una reseña
     * 
     * Elimina la reseña asociada al ID que recibe
     * @param id El ID de la reseña que se desea borrar
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando valoracion con id={0}", id);
        ValoracionEntity entity = em.find(ValoracionEntity.class, id);
        em.remove(entity);
    }

    /**
     * Busca si hay alguna editorial con el nombre que se envía de argumento
     *
     * @param name: Nombre de la editorial que se está buscando
     * @return null si no existe ninguna editorial con el nombre del argumento.
     * Si existe alguna devuelve la primera.
     */
    /**
     * Buscar una reseña
     * 
     * Busca si hay alguna reseña asociada a un libro y con un ID específico
     * @param servicioid El ID del libro con respecto al cual se busca
     * @param valoracionid El ID de la reseña buscada
     * @return La reseña encontrada o null. Nota: Si existe una o más reseñas 
     * devuelve siempre la primera que encuentra
     */
    public ValoracionEntity find(Long servicioid, Long valoracionid) {
        TypedQuery<ValoracionEntity> q = em.createQuery("select p from ValoracionEntity p where (p.servicio.id = :servicioid) and (p.id = :valoracionid)", ValoracionEntity.class);
        q.setParameter("servicioid", servicioid);
        q.setParameter("valoracionid", valoracionid);
        List<ValoracionEntity> results = q.getResultList();
        ValoracionEntity valoracion = null;
        if (results == null) {
            valoracion = null;
        } else if (results.isEmpty()) {
            valoracion = null;
        } else if (results.size() >= 1) {
            valoracion = results.get(0);
        }

        return valoracion;
    }

}
