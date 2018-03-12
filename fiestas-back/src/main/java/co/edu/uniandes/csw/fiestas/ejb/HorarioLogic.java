package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.HorarioPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Horario.
 *
 * @author df.nino10
 */
@Stateless
public class HorarioLogic 
{    
    private static final Logger LOGGER= Logger.getLogger(HorarioLogic.class.getName());
    
    @Inject
    private HorarioPersistence persistence;
    
    
            
    public List<HorarioEntity> getHorarios(){
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos los eventos.");
        return persistence.findAll();
    }

    public HorarioEntity getHorario(Long id) {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar el horario con id={0}", id);
        return persistence.find(id);
    }

    public HorarioEntity createHorario(HorarioEntity entity)throws BusinessLogicException {
        LOGGER.info("Inicia proceso de creación del horario con id={0}");
        
        if(persistence.find(entity.getId())!= null)
        {    throw new BusinessLogicException("El horario con el id\""+ entity.getId()+"\" ya existe");
        
        }
        
        if(entity.getHoraInicio().after(entity.getHoraFin()))
        {
            throw new BusinessLogicException("La hora de fin no puede ser antes de la hora de inicio");
        }
        
        if(entity.getHoraInicio().before(new Date()))
        {
            throw new BusinessLogicException("La hora de inicio no puede ser una fecha que ya ocurrió");
        }
        
        
        LOGGER.info("Termina proceso de creación del horario");
        return persistence.create(entity);
    }

    public HorarioEntity updateHorario(HorarioEntity entity) {
         LOGGER.log(Level.INFO,"Inicia proceso de actualización del horario con id={0}", entity.getId());
         HorarioEntity newEntity = persistence.update(entity);
         LOGGER.log(Level.INFO,"Termina proceso de actualización del horario con id={0}", entity.getId());
         return newEntity;
    }

    public void deleteHorario(Long id) 
    {
        LOGGER.log(Level.INFO,"Inicia proceso de actualización del horario con id={0}", id);
        persistence.delete(id);        
    }
}
