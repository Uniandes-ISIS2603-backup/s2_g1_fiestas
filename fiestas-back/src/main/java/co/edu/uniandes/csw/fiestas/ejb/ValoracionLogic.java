package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ValoracionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Valoracion.
 *
 * @author ls.arias
 */
@Stateless
public class ValoracionLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ValoracionLogic.class.getName());
    
    @Inject
    private ValoracionPersistence persistence;
    @Inject
    private ServicioLogic servicioLogic;

     /**
     * Obtiene la lista de los registros de Valoracion que pertenecen a un Servicio.
     *
     * @param servicioid id del Servicio el cual es padre de los Valoraciones.
     * @return Colección de objetos de ValoracionEntity.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public List<ValoracionEntity> getValoraciones(Long servicioid) throws BusinessLogicException {
        LOGGER.info("Inicia proceso de consultar todos los valoraciones");
        ServicioEntity servicio = servicioLogic.getServicio(servicioid);
        if (servicio.getValoraciones() == null) {
            throw new BusinessLogicException("El servicio que consulta aún no tiene valoraciones");
        }
        if (servicio.getValoraciones().isEmpty()) {
            throw new BusinessLogicException("El servicio que consulta aún no tiene valoraciones");
        }
        return servicio.getValoraciones();
    }
    
    
    /**
     * Obtiene los datos de una instancia de Valoracion a partir de su ID.
     *
     * @param servicioId identificador del servicio del que se requiere la valoracion
     * @param valoracionId Identificador de la instancia a consultar
     * @return Instancia de ValoracionEntity con los datos de la Valoracion
     * consultada.
     * @throws BusinessLogicException  - Error de lógica si no existe la valoracion
     */
    public ValoracionEntity getValoracion(Long servicioId, Long valoracionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la valoracion con el id dado.");
        if (persistence.find(servicioId, valoracionId)==null){
            throw new BusinessLogicException("La valoración no existe");
        }
        return persistence.find(servicioId, valoracionId);
    }

    /**
     * Se crea una valoracion en la base de datos.
     *
     * @param servicioId
     * @param entity Instancia de ValoracionEntity a crear
     * @return nstancia de ValoracionEntity creada
     * @throws BusinessLogicException - Error de lógica si no se cumple la regla de negocio
     */
    public ValoracionEntity createValoracion(Long servicioId, ValoracionEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear una valoracion");
        if (entity.getCalificacion() > 5.0 || entity.getCalificacion() < 1.0) {
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
        ServicioEntity servicio = servicioLogic.getServicio(servicioId);
        entity.setServicio(servicio);
        return persistence.create(entity);
    }

    /**
     * Actualiza la informacion de una valoracion.
     *
     * @param servicioId
     * @param entity Instancia de ValoracionEntity a actualizar
     * @return Instancia de ValoracionEntity actualizada
     */
    public ValoracionEntity updateValoracion(Long servicioId, ValoracionEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar una valoracion.");
        ServicioEntity servicio = servicioLogic.getServicio(servicioId);
        entity.setServicio(servicio);
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Valoracion de la base de datos.
     *
     * @param servicioId
     * @param id Identificador de la instancia a eliminar.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public void deleteValoracion(Long servicioId, Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una valoracion");
        ValoracionEntity old = getValoracion(servicioId, id);
        persistence.delete(old.getId());
    }

    public ValoracionEntity createValoracion(ValoracionEntity toEntity) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
