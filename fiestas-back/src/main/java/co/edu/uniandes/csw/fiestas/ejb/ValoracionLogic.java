package co.edu.uniandes.csw.fiestas.ejb;

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

    @Inject
    private ValoracionPersistence persistence;

    /**
     * Devuelve todas las valoraciones que hay en la base de datos
     *
     * @return Lista de entidades de tipo valoracion
     */
    public List<ValoracionEntity> getValoraciones() {
        List<ValoracionEntity> valoraciones = persistence.findAll();
        return valoraciones;
    }

    /**
     * Obtiene los datos de una instancia de Valoracion a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ValoracionEntity con los datos de la Valoracion
     * consultada.
     * @throws BusinessLogicException  - Error de lógica si no existe la valoracion
     */
    public ValoracionEntity getValoracion(Long id) throws BusinessLogicException {
        if (persistence.find(id) == null) {
            throw new BusinessLogicException("La valoración no existe");
        }
        return persistence.find(id);
    }

    /**
     * Se crea una valoracion en la base de datos.
     *
     * @param entity Instancia de ValoracionEntity a crear
     * @return nstancia de ValoracionEntity creada
     * @throws BusinessLogicException - Error de lógica si no se cumple la regla de negocio
     */
    public ValoracionEntity createValoracion(ValoracionEntity entity) throws BusinessLogicException {
        if (entity.getCalificacion() > 5.0 || entity.getCalificacion() < 1.0) {
            throw new BusinessLogicException("La calificación debe estar entre 1.0 y 5.0");
        }
        return persistence.create(entity);
    }

    /**
     * Actualiza la informacion de una valoracion.
     *
     * @param entity Instancia de ValoracionEntity a actualizar
     * @return Instancia de ValoracionEntity actualizada
     */
    public ValoracionEntity updateValoracion(ValoracionEntity entity) {
        ValoracionEntity newValoracion = persistence.update(entity);
        return newValoracion;
    }

    /**
     * Elimina una instancia de Valoracion de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteValoracion(Long id) {
        persistence.delete(id);
    }

}
