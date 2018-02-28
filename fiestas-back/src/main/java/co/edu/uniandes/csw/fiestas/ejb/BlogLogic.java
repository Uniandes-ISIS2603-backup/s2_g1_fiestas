/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.BlogPersistence;
import java.util.*;
import java.util.logging.*;
import javax.inject.Inject;

/**
 *
 * @author mc.gonzalez15
 */
public class BlogLogic {
    
    private static final Logger LOGGER = Logger.getLogger(BlogLogic.class.getName());

    @Inject
    private BlogPersistence persistence;

    @Inject
    private EventoLogic eventoLogic;
    
    /**
     * Obtiene la lista de los registros de Blog.
     *
     * @return Colección de objetos de BlogEntity.
     */
    public List<BlogEntity> getBlogs() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        return persistence.findAll();
    }
    
    /**
     * Obtiene los datos de una instancia de Blog a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BlogEntity con los datos del Blog consultado.
     */
    public BlogEntity getBlog(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un blog con id = {0}", id);
        return persistence.find(id);
    }
    
    /**
     * Se encarga de crear un Blog en la base de datos.
     *
     * @param entity Objeto de BlogEntity con los datos nuevos
     * @return Objeto de BlogEntity con los datos nuevos y su ID.
     */
    public BlogEntity createBlog(BlogEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un blog ");
        
        return persistence.create(entity);
    }
    
     /**
     * Actualiza la información de una instancia de Blog.
     *
     * @param entity Instancia de BlogEntity con los nuevos datos.
     * @return Instancia de BlogEntity con los datos actualizados.
     */
    public BlogEntity updateBlog(BlogEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un blog ");
        return persistence.update(entity);
    }
    
     /**
     * Elimina una instancia de Blog de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteBlog(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un autor ");
        persistence.delete(id);
    }
    
    /**
     * Asocia un Evento existente a un Blog
     *
     * @param blogId Identificador de la instancia de Blog
     * @param eventoId Identificador de la instancia de Evento
     * @return Instancia de EventoEntity que fue asociada a Blog
     */
    public EventoEntity addEvento(Long blogId, Long eventoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un libro al author con id = {0}", blogId);
        BlogEntity entity = this.getBlog(blogId);
        EventoEntity entityEvento = eventoLogic.getEvento(eventoId);
        entity.setEvento(entityEvento);
        return entityEvento;
    }
    
     /**
     * Borrar el evento de un blog
     *
     * @param blogId El blog de la cual se desea eliminar.
     */
    public void removeEvento(Long blogId) {
        BlogEntity blogEntity = getBlog(blogId);
        blogEntity.setEvento(null);
    }

    /**
     * Remplazar el evento de un blog
     *
     * @param evento Nuevo evento del blog
     * @param blogId El id del blog que se quiere actualizar.
     * @return El nuevo evento del blog
     */
    public EventoEntity replaceEvento(Long blogId, EventoEntity evento) {
        BlogEntity blog = getBlog(blogId);
        blog.setEvento(evento);
        return evento;
    }

    /**
     * Retorna el evento asociado a un blog
     *
     * @param blogId El id del blog a buscar.
     * @return El evento encontrado dentro del blog.
     * @throws BusinessLogicException Si el evento no se encuentra en el
     * blog
     */
    public EventoEntity getEvento(Long blogId) throws BusinessLogicException {
        try {
            EventoEntity evento = getBlog(blogId).getEvento();
            return evento;

        } catch (Exception e) {
            throw new BusinessLogicException("El blog con id " + blogId + " no existe");
        }

    }
    
    
}
