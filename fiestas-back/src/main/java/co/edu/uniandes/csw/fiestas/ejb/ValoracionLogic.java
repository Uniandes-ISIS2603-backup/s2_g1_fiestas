package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
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
     
    @Inject
    private ValoracionPersistence persistence;
    
    
     /**
     * Obtiene los datos de una instancia de Valoracion a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ValoracionEntity con los datos de la Valoracion consultada.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public ValoracionEntity getValoracion(Long id) throws BusinessLogicException {
        if(persistence.find(id)==null)
        {
            throw new BusinessLogicException("La valoración no existe");
        }
        return persistence.find(id);
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
