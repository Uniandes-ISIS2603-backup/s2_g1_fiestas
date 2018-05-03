/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.persistence;

import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Horario. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author df.nino10
 */
@Stateless
public class HorarioPersistence {
    @PersistenceContext(unitName="FiestasPU")
    protected EntityManager em;
    
    /**
     * Buscar un horario
     *
     * Busca si hay algun horario asociado a un Contrato y con un ID específico
     *
     * @param ContratoId El ID del Contrato con respecto al cual se busca
     * @param horarioId El ID del horario buscado
     * @return El horario encontrado o null. Nota: Si existe una o más horarios
     * devuelve siempre el primer que encuentra
     */
    public HorarioEntity find(Long contratoId, long horarioId){
        TypedQuery<HorarioEntity> q = em.createQuery("select p from HorarioEntity p where (p.contrato.id = :contratoid) and (p.id = :horarioid)", HorarioEntity.class);
        q.setParameter("contratoid", contratoId);
        q.setParameter("horarioid", horarioId);
        List<HorarioEntity> results = q.getResultList();
        HorarioEntity horario = null;
        if (results == null) {
            horario = null;
        } else if (results.isEmpty()) {
            horario = null;
        } else if (results.size() >= 1) {
            horario = results.get(0);
        }

        return horario;
    }
    
    /**
     * Buscar todos los horario
     *
     * Busca todos los horarios del sistema
     *
     * @return Lista con todos los horarios.
     */
    public List<HorarioEntity> findAll(){
        Query q = em.createQuery("select u from HorarioEntity u");
        return q.getResultList();
    }
    
    /**
     * Crear un horario
     *
     * Crea una nuevo horario con la información recibida en la entidad.
     *
     * @param entidad La entidad que representa el nuevo horario
     * @return La entidad creada
     */
    public HorarioEntity create (HorarioEntity entity){
        em.persist(entity);
        return entity;
    }
    
    /**
     * Actualizar un horario
     *
     * Actualiza la entidad que recibe en la base de datos
     *
     * @param entidad La entidad actualizada que se desea guardar
     * @return La entidad resultante luego de la acutalización
     */
    public HorarioEntity update (HorarioEntity entity){
        return em.merge(entity);
    }
    
    /**
     * Eliminar un horario
     *
     * Elimina el horario asociada al ID que recibe
     *
     * @param id El ID del horario que se desea borrar
     */
    public void delete (Long id){
        HorarioEntity entity = em.find(HorarioEntity.class, id);
        em.remove(entity);
    } 
}
