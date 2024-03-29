/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.BonoPersistence;
import co.edu.uniandes.csw.fiestas.persistence.ContratoPersistence;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Bono.
 *
 * @author df.nino10
 */
public class BonoLogic {
    private static final Logger LOGGER = Logger.getLogger(BlogLogic.class.getName());

    @Inject
    private BonoPersistence persistence;
    
    @Inject
    private ContratoPersistence contratoPersistence;
    
    @Inject
    private ProveedorPersistence proveedorPersistence;
    
    /**
     * Obtiene la lista de los registros de Bono.
     *
     * @return Colección de objetos de BonoEntity.
     */
    public List<BonoEntity> getBonos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los autores");
        return persistence.findAll();
    }
    
    /**
     * Obtiene la lista de los registros de Bono a partir de un proveedor.
     *
     * @return Colección de objetos de BonoEntity.
     */
    public List<BonoEntity> getBonos(Long proveedorId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los bonos");
        return persistence.findAllC(proveedorId);
    }

    /**
     * Obtiene los datos de una instancia de Bono a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BonoEntity con los datos del Bono consultado.
     */
    public BonoEntity getBono(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un bono con id = {0}", id);
        return persistence.find(id);
    }
    
    /**
     * Actualiza la información de una instancia de Bono.
     *
     * @param entity Instancia de BonoEntity con los nuevos datos.
     * @return Instancia de BonoEntity con los datos actualizados.
     */
    public BonoEntity updateBono(BonoEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un bono ");
        if(entity.getId()== null)
            throw new BusinessLogicException("El bono debe tener id");
        
        BonoEntity old = getBono(entity.getId());
        
        if(entity.getAplicaDesde()== null)
            throw new BusinessLogicException("El bono debe tener una fecha desde la cual aplica");
        
        if(entity.getExpira()== null)
            throw new BusinessLogicException("El bono debe tener una fecha de expiración.");
        
        if(entity.getAplicaDesde().getDay()!=old.getAplicaDesde().getDay() && entity.getAplicaDesde().getMonth()!=old.getAplicaDesde().getMonth()&& entity.getAplicaDesde().getYear()!=old.getAplicaDesde().getYear())
            throw new BusinessLogicException("No es posible cambiar la fecha desde la cual aplica el bono.");
        
        //if(entity.getAplicaDesde().compareTo(old.getAplicaDesde())!=0)
            //throw new BusinessLogicException("No es posible cambiar la fecha desde la cual aplica el bono.");
        
        if(entity.getExpira().before(old.getExpira()))
            throw new BusinessLogicException("No es posible reucir el rango de aplicación del bono.");
        
        if(entity.getDescuento()<=0 ||entity.getDescuento()>100)
            throw new BusinessLogicException("El bono debería tener un procentaje de descuento razonable.");
        
        if(entity.getMotivo()== null || entity.getMotivo().equals(""))
            throw new BusinessLogicException("El bono debe tener motivo");             
        
        if(entity.getProveedor()== null )
            throw new BusinessLogicException("El bono debe tener proveedor");    
        
        if(!entity.getProveedor().equals(old.getProveedor()) )
            throw new BusinessLogicException("No puede cambiar el proveedor del bono.");  
        
        if(entity.getCodigo()!=null && !entity.getCodigo().equals(old.getCodigo()))
            throw new BusinessLogicException("No puede cambiar el codigo del bono");
                     
        return persistence.create(entity);
    }

    
    /**
     * Se encarga de crear un Bono en la base de datos.
     *
     * @param entity Objeto de BonoEntity con los datos nuevos
     * @return Objeto de BonoEntity con los datos nuevos y su ID.
     */
    public BonoEntity createBono(BonoEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un bono ");
        if(entity.getId()== null)
            throw new BusinessLogicException("El bono debe tener id");
        
        if(entity.getAplicaDesde()== null)
            throw new BusinessLogicException("El bono debe tener una fecha desde la cual aplica");
        
        if(entity.getExpira()== null)
            throw new BusinessLogicException("El bono debe tener una fecha de expiración.");
        
        if(entity.getAplicaDesde().before(new Date()))
            throw new BusinessLogicException("El bono no debería empezar a aplicar antes de su creación.");
        
        if(entity.getExpira().before(new Date()))
            throw new BusinessLogicException("El bono no debería expirar antes de su creación.");
        
        if(entity.getDescuento()<=0)
            throw new BusinessLogicException("El bono debería tener un procentaje de descuento razonable.");
        
        if(entity.getMotivo()== null || entity.getMotivo().equals(""))
            throw new BusinessLogicException("El bono debe tener motivo");              
        
        if(entity.getProveedor()== null )
            throw new BusinessLogicException("El bono debe tener proveedor");    
        
        if(entity.getCodigo()!=null && persistence.findByCodigo(entity.getCodigo())!=null)
            throw new BusinessLogicException("El código del bono ya está asignado a otro bono.");

        if(proveedorPersistence.find((long)entity.getProveedor().getId())==null)
            throw new BusinessLogicException("El proveedor del bono no existe"); 
        
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Bono de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteBono(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un bono ");
        persistence.delete(id);
    }
}
