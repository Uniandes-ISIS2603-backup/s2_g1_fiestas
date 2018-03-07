package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import co.edu.uniandes.csw.fiestas.persistence.TematicaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Tematica.
 *
 * @author af.losada
 */
public class TematicaLogic {

    private static final Logger LOGGER = Logger.getLogger(TematicaLogic.class.getName());

    @Inject
    private TematicaPersistence persistence;

    @Inject
    private ServicioPersistence servicioPer;

    /**
     * Obtiene la lista de los registros de Tematica.
     *
     * @return Colección de objetos de TematicaEntity.
     */
    public List<TematicaEntity> getTematicas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tematicas");
        return persistence.findAll();
    }

    /**
     * Obtiene la Tematica.
     *
     * @param id Id de la tematica
     * @return Objetos de TematicaEntity.
     */
    public TematicaEntity getTematica(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los tematicas");
        return persistence.find(id);
    }

    /**
     * Se encarga de crear una tematica en la base de datos.
     *
     * @param entity Objeto de TematicaEntity con los datos nuevos
     * @return Objeto de TematicaEntity con los datos nuevos y su ID.
     */
    public TematicaEntity createTematica(TematicaEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un tematica ");
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Tematica.
     *
     * @param id
     * @param entity Instancia de TematicaEntity con los nuevos datos.
     * @return Instancia de TematicaEntity con los datos actualizados.
     */
    public TematicaEntity updateTematica( Long id ,TematicaEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar tematica con id={0}", entity.getId());
        TematicaEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar tematica con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar una tematica por ID
     *
     * @param id El ID del tematica a eliminar
     */
    public void deleteTematica(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar tematica con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar tematica con id={0}", id);
    }

    /**
     * Obtiene una colección de instancias de ServicioEntity asociadas a una
     * instancia de Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @return Colección de instancias de ServicioEntity asociadas a la
     * instancia de Tematica
     */
    public List<ServicioEntity> listServicios(Long TematicaId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los libros del autor con id = {0}", TematicaId);
        return getTematica(TematicaId).getServicios();
    }

    /**
     * Obtiene una instancia de ServicioEntity asociada a una instancia de
     * Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @param serviciosId Identificador de la instancia de Servicio
     * @return La entidadd de Servicio de la tematica
     */
    public ServicioEntity getServicio(Long TematicaId, Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un libro con id = {0}", serviciosId);
        List<ServicioEntity> list = getTematica(TematicaId).getServicios();
        ServicioEntity serviciosEntity = new ServicioEntity();
        serviciosEntity.setId(serviciosId);
        int index = list.indexOf(serviciosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Servicio existente a un Tematica
     *
     * @param TematicaId Identificador de la instancia de Tematica
     * @param serviciosId Identificador de la instancia de Servicio
     * @return Instancia de ServicioEntity que fue asociada a Tematica
     */
    public ServicioEntity addServicio(Long TematicaId, Long serviciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un libro al Tematica con id = {0}", TematicaId);
        TematicaEntity tematica = persistence.find(TematicaId);
        ServicioEntity servicio = servicioPer.find(serviciosId);
        tematica.getServicios().add(servicio);
        return servicio;
    }
    
    public ServicioEntity removeServicio(Long TematicaId, Long servicioId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un libro al Tematica con id = {0}", TematicaId);
        TematicaEntity tematica = persistence.find(TematicaId);
        ServicioEntity servicio = servicioPer.find(servicioId);
        tematica.getServicios().remove(servicio);
        return servicio;
    }
    
    public List<ServicioEntity> replaceServicios(List<ServicioEntity> lista, Long TematicaId)
    {
        if(lista.size() != 0)
        {
            persistence.find(TematicaId).setServicios(lista);
        }
        return lista;
    }
}
