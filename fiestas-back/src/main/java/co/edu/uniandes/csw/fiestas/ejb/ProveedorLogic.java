/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Proveedor.
 *
 * @author nm.hernandez10
 */
@Stateless
public class ProveedorLogic 
{

    private static final Logger LOGGER = Logger.getLogger(ProveedorLogic.class.getName());

    @Inject
    private ProveedorPersistence persistence;

    @Inject
    private ServicioLogic servicioLogic;

    @Inject
    private ValoracionLogic valoracionLogic;

    @Inject
    private ContratoLogic contratoLogic;

    //Logic de apoyo para algunas reglas de negocio.
    @Inject
    private UsuarioLogic usuarioLogic;   
    
    /**
     * Obtiene la lista de los registros de Proveedor.
     *
     * @return Colección de objetos de ProveedorEntity.
     */
    public List<ProveedorEntity> getProveedores() 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los proveedores");
        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Proveedor a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ProveedorEntity con los datos del Proveedor
     * consultado.
     */
    public ProveedorEntity getProveedor(Long id)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un proveedor con id = {0}", id);
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Proveedor en la base de datos.
     *
     * @param entity Objeto de ProveedorEntity con los datos nuevos
     * @return Objeto de ProveedorEntity con los datos nuevos y su ID.
     */
    public ProveedorEntity createProveedor(ProveedorEntity entity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un proveedor ");
        if(getProveedor(entity.getId()) != null)
        {
            throw new BusinessLogicException("Ya existe un proveedor con ese id");
        }
        if(usuarioLogic.repetidoLogin(entity.getLogin()))
        {
            throw new BusinessLogicException("Ya existe un usuario (proveedor o cliente) con ese mismo login");
        }
        if(entity.getNombre() == null || entity.getNombre().equals(""))
        {
            throw new BusinessLogicException("No puede crear un proveedor sin nombre");
        }
        if(entity.getDocumento() == null || entity.getDocumento().equals(""))
        {
            throw new BusinessLogicException("No puede crear un proveedor sin documento");
        }
        if(entity.getLogin() == null || entity.getLogin().equals(""))
        {
            throw new BusinessLogicException("No puede crear un proveedor sin login");
        }
        if(entity.getContraseña() == null || entity.getContraseña().equals(""))
        {
            throw new BusinessLogicException("No puede crear un proveedor sin contraseña");
        }
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Proveedor.
     *
     * @param entity Instancia de ProveedorEntity con los nuevos datos.
     * @return Instancia de ProveedorEntity con los datos actualizados.
     */
    public ProveedorEntity updateProveedor(ProveedorEntity entity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un proveedor ");
        if(getProveedor(entity.getId()) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para actualizar");
        }  
        String loginAnterior = getProveedor(entity.getId()).getLogin();
        String loginNuevo = entity.getLogin();
        if(!loginAnterior.equals(loginNuevo))
        {
            throw new BusinessLogicException("No puede cambiarse el login del proveedor");
        }
        if(entity.getNombre() == null || entity.getNombre().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un proveedor sin nombre");
        }
        if(entity.getDocumento() == null || entity.getDocumento().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un proveedor sin documento");
        }
        if(entity.getLogin() == null || entity.getLogin().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un proveedor sin login");
        }
        if(entity.getContraseña() == null || entity.getContraseña().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un proveedor sin contraseña");
        }
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Proveedor de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteProveedor(Long id) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un proveedor ");
        if(getProveedor(id) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para eliminar");
        }
        persistence.delete(id);
    }

    /**
     * Obtiene una colección de instancias de ServicioEntity asociadas a una
     * instancia de Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @return Colección de instancias de ServicioEntity asociadas a la
     * instancia de Proveedor
     */
    public List<ServicioEntity> listServicios(Long proveedorId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los servicios del proveedor con id = {0}", proveedorId);
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para enlistar servicios");
        }
        return getProveedor(proveedorId).getServicios();
    }

    /**
     * Obtiene una instancia de ServicioEntity asociada a una instancia de
     * Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param serviciosId Identificador de la instancia de Servicio
     * @return La entidadd de Libro del proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no se encuentra el servicio en el proveedor
     */
    public ServicioEntity getServicio(Long proveedorId, Long serviciosId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un servicio con id = {0}", serviciosId);
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para obtener servicio");
        }
        List<ServicioEntity> list = getProveedor(proveedorId).getServicios();
        ServicioEntity serviciosEntity = new ServicioEntity();
        serviciosEntity.setId(serviciosId);
        int index = list.indexOf(serviciosEntity);
        if (index >= 0 && serviciosEntity.equals(list.get(index))) {
            return list.get(index);
        } else {
            throw new BusinessLogicException("No existe dicho servicio en ese proveedor");
        }
    }

    /**
     * Asocia un Servicio existente a un Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param serviciosId Identificador de la instancia de Servicio
     * @return Instancia de ServicioEntity que fue asociada a Proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no existe el servicio
     */
    public ServicioEntity addServicio(Long proveedorId, Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un servicio al proveedor con id = {0}", proveedorId);
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para agregar servicio");
        }
        ProveedorEntity ent = getProveedor(proveedorId);
        ServicioEntity entS = servicioLogic.getServicio(serviciosId);
        int index = ent.getServicios().indexOf(entS);
        if (index >= 0 && entS.equals(ent.getServicios().get(index))) 
        {
            throw new BusinessLogicException("Ya existe dicho servicio en ese proveedor");
        } 
        else 
        {
            ent.agregarServicio(entS); 
            return entS;            
        }

    }

    /**
     * Remplaza las instancias de Servicio asociadas a una instancia de
     * Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param list Colección de instancias de ServicioEntity a asociar a
     * instancia de Proveedor
     * @return Nueva colección de ServicioEntity asociada a la instancia de
     * Proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     * error de logica
     */
    public List<ServicioEntity> replaceServicios(Long proveedorId, List<ServicioEntity> list) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los servicios asocidos al proveedor con id = {0}", proveedorId);
        ProveedorEntity proveedor = getProveedor(proveedorId);
        
         if (list == null) {
            throw new BusinessLogicException("No hay lista nueva.");
        }
        if (!list.isEmpty()) {
        } else {
            throw new BusinessLogicException("No hay lista nueva o la lista está vacía");
        }
        if (proveedor != null) {
            proveedor.setServicios(list);
        } else {
            throw new BusinessLogicException("El proveedor al que se le quiere reemplazar servicios es nulo");
        }
        return list;
    }

    /**
     * Desasocia un Servicio existente de un Proveedor existente
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param serviciosId Identificador de la instancia de Servicio
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * el proveedor no tiene ese servicio
     */
    public void removeServicio(Long proveedorId, Long serviciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un servicio del proveedor con id = {0}", proveedorId);
        ProveedorEntity ent = getProveedor(proveedorId);
        if(ent == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para remover servicio");
        }
        ServicioEntity entS = servicioLogic.getServicio(serviciosId);
        int index = ent.getServicios().indexOf(entS);
        if (index >= 0) {
            ent.removerServicio(entS);
            if (ent.getServicios().isEmpty()) 
            {
                deleteProveedor(proveedorId);
            }
        } else {
            throw new BusinessLogicException("El proveedor no tiene ese servicio");
        }
    }

  /**
     * Agregar un contrato al proveedor
     *
     * @param contratoId El id contrato a guardar
     * @param proveedorId El id del proveedor en la cual se va a guardar el
     * contrato.
     * @return El contrato que fue agregado al proveedor.
     */
    public ContratoEntity addContrato(Long contratoId, Long proveedorId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un contrato al proveedor con id = {0}", proveedorId);
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para agregar contrato");
        }
        ProveedorEntity ent = getProveedor(proveedorId);
        ContratoEntity entC = contratoLogic.getContrato(contratoId);
        int index = ent.getContratos().indexOf(entC);
        if(ent.isPenalizado())
        {
            throw new BusinessLogicException("El proveedor está penalizado, no puede adquirir contratos");
        }
        if (index >= 0 && entC.equals(ent.getContratos().get(index))) 
        {
            throw new BusinessLogicException("Ya existe dicho contrato en ese proveedor");
        } 
        else 
        {
            ent.getContratos().add(entC); 
            return entC;            
        }
    }

    /**
     * Borrar un contrato de un proveedor
     *
     * @param contratoId El contrato que se desea borrar del proveedor.
     * @param proveedorId El proveedor de la cual se desea eliminar.
     */
    public void removeContrato(Long contratoId, Long proveedorId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un contrato del proveedor con id = {0}", proveedorId);
        ProveedorEntity ent = getProveedor(proveedorId);
        if(ent == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para remover contrato");
        }
        if(ent.isPenalizado())
        {
            throw new BusinessLogicException("El proveedor está penalizado, no puede adquirir contratos");
        }
        ContratoEntity entC = contratoLogic.getContrato(contratoId);
        int index = ent.getContratos().indexOf(entC);
        if (index >= 0) {
            ent.getContratos().remove(entC);            
        } else {
            throw new BusinessLogicException("El proveedor no tiene ese contrato");
        }
    }

    /**
     * Remplazar contratos de un proveedor
     *
     * @param contratos Lista de contratos que serán los del proveedor.
     * @param proveedorId El id del proveedor que se quiere actualizar.
     * @return La lista de contratos actualizada.
     */
    public List<ContratoEntity> replaceContratos(Long proveedorId, List<ContratoEntity> contratos) throws BusinessLogicException 
    {
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para reemplazar contratos");
        }
        ProveedorEntity proveedor = getProveedor(proveedorId);
        List<ContratoEntity> contratoList = contratoLogic.getContratos();
        for (ContratoEntity contrato : contratoList) 
        {
            if (contratos.contains(contrato)) 
            {
                contrato.setProveedor(proveedor);
            } 
            else if (null != contrato.getProveedor() && contrato.getProveedor().equals(proveedor)) 
            {
                contratoLogic.deleteContrato(contrato.getId());
            }
        }
        proveedor.setContratos(contratos);
        return contratos;
    }

    /**
     * Retorna todos los contratos asociados a un proveedor
     *
     * @param proveedorId El ID del proveedor buscada
     * @return La lista de contratos del proveedor
     */
    public List<ContratoEntity> getContratos(Long proveedorId) throws BusinessLogicException 
    {
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para enlistar contratos");
        }
        return getProveedor(proveedorId).getContratos();
    }

    /**
     * Retorna un contrato asociado a un proveedor
     *
     * @param proveedorId El id del proveedor a buscar.
     * @param contratoId El id del contrato a buscar
     * @return El contrato encontrado dentro del proveedor.
     * @throws BusinessLogicException Si el contrato no se encuentra en la
     * proveedor
     */
    public ContratoEntity getContrato(Long proveedorId, Long contratoId) throws BusinessLogicException 
    {
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para obtener contrato");
        }
        List<ContratoEntity> contratos = getProveedor(proveedorId).getContratos();
        ContratoEntity contrato = contratoLogic.getContrato(contratoId);
        int index = contratos.indexOf(contrato);
        if (index >= 0) {
            return contratos.get(index);
        }
        throw new BusinessLogicException("El contrato no está asociado al proveedor");
    }

    /**
     * Obtiene una colección de instancias de ContratoEntity asociadas a una
     * instancia de Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @return Colección de instancias de ContratoEntity asociadas a la
     * instancia de Proveedor
     *
     */
    public List<ContratoEntity> listContratos(Long proveedorId) throws BusinessLogicException 
    {
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para enlistar contratos");
        }
        return getProveedor(proveedorId).getContratos();
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
    public ValoracionEntity addValoracion(Long valoracionId, Long proveedorId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar una valoración al proveedor con id = {0}", proveedorId);
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para agregar una valoración");
        }
        ProveedorEntity ent = getProveedor(proveedorId);
        ValoracionEntity entV = valoracionLogic.getValoracion(valoracionId);
        int index = ent.getValoraciones().indexOf(entV);
        if (index >= 0 && entV.equals(ent.getValoraciones().get(index))) 
        {
            throw new BusinessLogicException("Ya existe dicha valoración en ese proveedor");
        } 
        else 
        {
            ent.getValoraciones().add(entV); 
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
    public void removeValoracion(Long valoracionId, Long proveedorId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar una valoración del proveedor con id = {0}", proveedorId);
        ProveedorEntity ent = getProveedor(proveedorId);
        if(ent == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para remover valoración");
        }
        ValoracionEntity entV = valoracionLogic.getValoracion(valoracionId);
        int index = ent.getValoraciones().indexOf(entV);
        if (index >= 0) 
        {
            ent.getValoraciones().remove(entV);            
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
    public List<ValoracionEntity> replaceValoraciones(Long proveedorId, List<ValoracionEntity> valoraciones) throws BusinessLogicException 
    {
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para reemplazar valoraciones");
        }
        ProveedorEntity proveedor = getProveedor(proveedorId);        
        proveedor.setValoraciones(valoraciones);
        return valoraciones;
    }

    /**
     * Retorna todos los valoraciones asociados a un proveedor
     *
     * @param proveedorId El ID del proveedor buscada
     * @return La lista de valoraciones del proveedor
     */
    public List<ValoracionEntity> getValoraciones(Long proveedorId) throws BusinessLogicException 
    {
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para enlistar valoraciones");
        }
        return getProveedor(proveedorId).getValoraciones();
    }

    /**
     * Retorna un valoracion asociado a un proveedor
     *
     * @param proveedorId El id del proveedor a buscar.
     * @param valoracionId El id del valoracion a buscar
     * @return El valoracion encontrado dentro del proveedor.
     * @throws BusinessLogicException Si el valoracion no se encuentra en el
     * proveedor
     */
    public ValoracionEntity getValoracion(Long proveedorId, Long valoracionId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un valoracion con id = {0}", valoracionId);
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para obtener valoracion");
        }
        List<ValoracionEntity> list = getProveedor(proveedorId).getValoraciones();
        ValoracionEntity valoracionEntity = new ValoracionEntity();
        valoracionEntity.setId(valoracionId);
        int index = list.indexOf(valoracionEntity);
        if (index >= 0 && valoracionEntity.equals(list.get(index))) {
            return list.get(index);
        } else {
            throw new BusinessLogicException("No existe dicha valoración en ese proveedor");
        }
    }

    /**
     * Obtiene una colección de instancias de ValoracionEntity asociadas a una
     * instancia de Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @return Colección de instancias de ValoracionEntity asociadas a la
     * instancia de Proveedor
     *
     */
    public List<ValoracionEntity> listValoraciones(Long proveedorId) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos las valoraciones del proveedor con id = {0}", proveedorId);
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para enlistar valoraciones");
        }
        return getProveedor(proveedorId).getValoraciones();
    }
}
