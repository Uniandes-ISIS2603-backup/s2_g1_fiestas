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
    
    private static final Logger LOGGER = Logger.getLogger(ValoracionLogic.class.getName());
    
    @Inject
    private ValoracionPersistence persistence;
    @Inject
    private ServicioLogic servicioLogic;
    @Inject
    private ProveedorLogic proveedorLogic;

     /**
     * Obtiene la lista de los registros de Valoracion que pertenecen a un Servicio.
     *
     * @param servicioid id del Servicio el cual es padre de los Valoraciones.
     * @return Colección de objetos de ValoracionEntity.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public List<ValoracionEntity> getValoracionesServicio(Long servicioid) throws BusinessLogicException {
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
    public ValoracionEntity getValoracionServicio(Long servicioId, Long valoracionId) throws BusinessLogicException {
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
    public ValoracionEntity createValoracionServicio(Long servicioId, ValoracionEntity entity) throws BusinessLogicException {
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
    public ValoracionEntity updateValoracionServicio(Long servicioId, ValoracionEntity entity) {
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
    public void deleteValoracionServicio(Long servicioId, Long id) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una valoracion");
        ValoracionEntity old = getValoracionServicio(servicioId, id);
        persistence.delete(old.getId());
    }

    
    /**
     * Agregar un valoracion al proveedor
     *
     * @param valoracionId El id valoracion a guardar
     * @param proveedorId El id del proveedor en la cual se va a guardar el
     * valoracion.
     * @return El valoracion que fue agregado al proveedor.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no existe la valoracion
     */
    public ValoracionEntity addValoracionProveedor(Long valoracionId, Long proveedorId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar una valoración al proveedor con id = {0}", proveedorId);
        if(proveedorLogic.getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para agregar una valoración");
        }
        ProveedorEntity ent = proveedorLogic.getProveedor(proveedorId);
        ValoracionEntity entV = getValoracionProveedor(ent.getId(),valoracionId);
        int index = ent.getValoraciones().indexOf(entV);
        if (index >= 0 && entV.equals(ent.getValoraciones().get(index))) 
        {
            throw new BusinessLogicException("Ya existe dicha valoración en ese proveedor");
        } 
        else 
        {
            ent.agregarValoracion(entV); 
            proveedorLogic.updateProveedor(ent);
            return entV;            
        }
    }

    /**
     * Borrar un valoracion de un proveedor
     *
     * @param valoracionId El valoracion que se desea borrar del proveedor.
     * @param proveedorId El proveedor de la cual se desea eliminar.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no existe la valoracion
     */
    public void removeValoracionProveedor(Long valoracionId, Long proveedorId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una valoración del proveedor con id = {0}", proveedorId);
        ProveedorEntity ent = proveedorLogic.getProveedor(proveedorId);
        if(ent == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para remover valoración");
        }
        ValoracionEntity entV = getValoracionProveedor(ent.getId(),valoracionId);
        int index = ent.getValoraciones().indexOf(entV);
        if (index >= 0) 
        {
            ent.removerValoracion(entV); 
             proveedorLogic.updateProveedor(ent);
        } 
        else 
        {
            throw new BusinessLogicException("El proveedor no tiene ese contrato");
        }
    }

    /**
     * Remplazar valoraciones de un proveedor
     *
     * @param valoraciones Lista de valoraciones que serán los del proveedor.
     * @param proveedorId El id del proveedor que se quiere actualizar.
     * @return La lista de valoraciones actualizada.
     */
    public List<ValoracionEntity> replaceValoracionesProveedor(Long proveedorId, List<ValoracionEntity> valoraciones) throws BusinessLogicException 
    {
        if(proveedorLogic.getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para reemplazar valoraciones");
        }
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedorId);        
        proveedor.setValoraciones(valoraciones);
        proveedorLogic.updateProveedor(proveedor);
        return valoraciones;
    }

    /**
     * Retorna todos los valoraciones asociados a un proveedor
     *
     * @param proveedorId El ID del proveedor buscada
     * @return La lista de valoraciones del proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public List<ValoracionEntity> getValoracionesProveedor(Long proveedorId) throws BusinessLogicException 
    {
        if(proveedorLogic.getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para enlistar valoraciones");
        }
        return proveedorLogic.getProveedor(proveedorId).getValoraciones();
    }

    /**
<<<<<<< HEAD
     * Obtiene los datos de una instancia de Valoracion a partir de su ID.
     *
     * @param proveedorId identificador del proveedor del que se requiere la valoracion
     * @param valoracionId Identificador de la instancia a consultar
     * @return Instancia de ValoracionEntity con los datos de la Valoracion
     * consultada.
     * @throws BusinessLogicException  - Error de lógica si no existe la valoracion
     */
    public ValoracionEntity getValoracionProveedor(Long proveedorId, Long valoracionId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la valoracion con el id dado.");
        if (persistence.find(proveedorId, valoracionId)==null){
            throw new BusinessLogicException("La valoración no existe");
        }
        return persistence.find(proveedorId, valoracionId);
    }
=======
     * Retorna un valoracion asociado a un proveedor
     *
     * @param proveedorId El id del proveedor a buscar.
     * @param valoracionId El id del valoracion a buscar
     * @return El valoracion encontrado dentro del proveedor.
     * @throws BusinessLogicException Si el valoracion no se encuentra en el
     * proveedor
     */
    public ValoracionEntity getValoracionProveedor(Long proveedorId, Long valoracionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un valoracion con id = {0}", valoracionId);
        if(proveedorLogic.getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para obtener valoracion");
        }
        List<ValoracionEntity> list = proveedorLogic.getProveedor(proveedorId).getValoraciones();
        ValoracionEntity valoracionEntity = new ValoracionEntity();
        valoracionEntity.setId(valoracionId);
        int index = list.indexOf(valoracionEntity);
        if (index >= 0 && valoracionEntity.equals(list.get(index))) {
            return list.get(index);
        } else {
            throw new BusinessLogicException("No existe dicha valoración en ese proveedor");
        }
    }   
>>>>>>> origin/master
}
