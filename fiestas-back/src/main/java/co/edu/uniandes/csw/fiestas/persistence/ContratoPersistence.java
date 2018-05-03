/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Clase que maneja la persistencia para Contrato. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author mc.gonzalez15
 */
@Stateless
public class ContratoPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ContratoPersistence.class.getName());

    @PersistenceContext(unitName = "FiestasPU")
    protected EntityManager em;
    
    /**
     * Buscar un contrato
     *
     * Busca si hay algun contrato asociado a un evento y con un ID específico
     *
     * 
     * @param id El ID del contrato buscado
     * @return El contrato encontrado o null. Nota: Si existe una o más contratos
     * devuelve siempre el primer que encuentra
     */
    public ContratoEntity find(Long id) {
        LOGGER.log(Level.INFO, "Consultando contrato con id={0}", id);
        return em.find(ContratoEntity.class, id);
    }

    /**
     * Buscar todos los contrato
     *
     * Busca todos los contratos del sistema
     *
     * @return Lista con todos los contratos.
     */
    public List<ContratoEntity> findAll() {
        LOGGER.info("Consultando todos los contratos");
        Query q = em.createQuery("select u from ContratoEntity u");
        return q.getResultList();
    }

    /**
     * Crear un contrato
     *
     * Crea una nuevo contrato con la información recibida en la entidad.
     *
     * @param entity La entidad que representa el nuevo contrato
     * @return La entidad creada
     */
    public ContratoEntity create(ContratoEntity entity) {
        LOGGER.info("Creando un contrato nuevo");
        em.persist(entity);
        LOGGER.info("Contrato creado");
        return entity;
    }

    /**
     * Actualizar un contrato
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param entity La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public ContratoEntity update(ContratoEntity entity) {
        LOGGER.log(Level.INFO, "Actualizando contrato con id={0}", entity.getId());
        return em.merge(entity);
        
    }

    /**
     * Eliminar un contrato
     *
     * Elimina el contrato asociada al ID que recibe
     *
     * @param id El ID del contrato que se desea borrar
     */
    public void delete(Long id) {
        LOGGER.log(Level.INFO, "Borrando contrato con id={0}", id);
        ContratoEntity entity = em.find(ContratoEntity.class, id);
        em.remove(entity);
    }
    
}
