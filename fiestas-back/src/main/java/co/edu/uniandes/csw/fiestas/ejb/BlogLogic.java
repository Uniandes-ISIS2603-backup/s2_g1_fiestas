package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.BlogPersistence;
import co.edu.uniandes.csw.fiestas.persistence.ClientePersistence;
import java.util.*;
import java.util.logging.*;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Blog.
 *
 * @author mc.gonzalez15
 */
public class BlogLogic {

    private static final Logger LOGGER = Logger.getLogger(BlogLogic.class.getName());

    @Inject
    private BlogPersistence persistence;
    
    @Inject
    private EventoLogic logicEvento;
    
    @Inject 
    private ClientePersistence clientePersistence;
    
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
     * Obtiene la lista de los registros de Blog.
     *
     * @return Colección de objetos de BlogEntity.
     */
    public List<BlogEntity> getBlogs(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los blogs");
        return persistence.findAllC(clienteId);
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
    public BlogEntity createBlog(BlogEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un blog ");
        if(entity.getId()== null)
            throw new BusinessLogicException("El blog debe tener id");
        
        if(entity.getTitulo()== null && entity.getTitulo().equals(""))
            throw new BusinessLogicException("El blog debe tener título");
        
        if(entity.getCuerpo()== null && entity.getCuerpo().equals(""))
            throw new BusinessLogicException("El blog debe tener cuerpo");
        
        if(entity.getCliente()== null )
            throw new BusinessLogicException("El blog debe tener cliente");        
        
        if(entity.getEvento()== null )
            throw new BusinessLogicException("El blog debe tener evento");       
        
        if(logicEvento.getEvento(entity.getEvento().getId())==null)
            throw new BusinessLogicException("El evento del blog no existe");

        if(clientePersistence.find((long)entity.getCliente().getId())==null)
            throw new BusinessLogicException("El cliente del blog no existe");              
        
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Blog.
     *
     * @param entity Instancia de BlogEntity con los nuevos datos.
     * @return Instancia de BlogEntity con los datos actualizados.
     */
    public BlogEntity updateBlog(BlogEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un blog ");
        
        
        
        if(entity.getTitulo()== null && entity.getTitulo().equals(""))
            throw new BusinessLogicException("El blog debe tener título");
        
        if(entity.getCuerpo()== null && entity.getCuerpo().equals(""))
            throw new BusinessLogicException("El blog debe tener cuerpo");
        
        if(entity.getCliente()== null )
            throw new BusinessLogicException("El blog debe tener cliente");        
        
        if(entity.getEvento()== null )
            throw new BusinessLogicException("El blog debe tener evento");       
        
        if(logicEvento.getEvento(entity.getEvento().getId())==null)
            throw new BusinessLogicException("El evento del blog no existe");

        if(clientePersistence.find(entity.getCliente().getId())==null)
            throw new BusinessLogicException("El cliente del blog no existe");    
        
        if(clientePersistence.find(entity.getCliente().getId())!=entity.getCliente())
            throw new BusinessLogicException("No es posible cambiar el cliente del blog.");    
        
        if(logicEvento.getEvento(entity.getEvento().getId())!=entity.getEvento())
            throw new BusinessLogicException("No es posible cambiar el evento del blog");
        
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
     * 
     * @param id
     * @return 
     */
    public EventoEntity getEventoExistente(Long id){
        return logicEvento.getEvento(id);
    }
    
    public EventoEntity getEvento(Long id){
        BlogEntity bE=persistence.find(id);
        return bE.getEvento();
    }
    
    
    public void addEvento(EventoEntity eE, Long id) throws BusinessLogicException{
        BlogEntity bE = getBlog(id);
        if(bE.getEvento()==null){
        bE.setEvento(eE);
        persistence.update(bE);
        }
        else 
            throw new BusinessLogicException("El blog ya tiene evento.");
    }
}
