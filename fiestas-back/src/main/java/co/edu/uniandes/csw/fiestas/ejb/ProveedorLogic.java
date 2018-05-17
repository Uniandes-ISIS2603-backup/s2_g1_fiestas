
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ClientePersistence;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
import java.util.ArrayList;
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
    private ProductoLogic productoLogic;
    
    @Inject
    private ClientePersistence clientePersistence;
    
    @Inject
    private ContratoLogic contratoLogic;
    
    @Inject
    private BonoLogic bonoLogic;
    
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
    
    /** Verifica si existe el login en la base de datos.
     *
     * @param login
     *
     * @return true si el login que se pasa por parámetro está en la base de datos.
     */
    public boolean loginRepetido(String login){
        return persistence.loginRepetido(login);
    }
    /**
     * Se encarga de crear un Proveedor en la base de datos.
     *
     * @param entity Objeto de ProveedorEntity con los datos nuevos
     * @return Objeto de ProveedorEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public ProveedorEntity createProveedor(ProveedorEntity entity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un proveedor ");
        if(getProveedor(entity.getId()) != null)
        {
            throw new BusinessLogicException("Ya existe un proveedor con ese id");
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
        if(persistence.loginRepetido(entity.getLogin()) || clientePersistence.loginRepetido(entity.getLogin()))
        {
            throw new BusinessLogicException("Ya existe un usuario (proveedor o proveedor) con ese mismo login");
        }
        if(entity.getContrasena() == null || entity.getContrasena().equals(""))
        {
            throw new BusinessLogicException("No puede crear un proveedor sin contraseña");
        }
        if(entity.getValoracion() > 5 || entity.getValoracion() == null || entity.getValoracion() < 0 )
        {
            throw new BusinessLogicException("No puede crear un proveedor con valoración mayor a 5, valoración negativa o valoración nula");
        }
        calcularValoracion(entity);
        usuarioLogic.createUsuario(crearUsuario(entity));
        return persistence.create(entity);
    }
    
    public UsuarioEntity crearUsuario(ProveedorEntity entity)
    {
        UsuarioEntity nuevoUsuario = new UsuarioEntity();
        nuevoUsuario.setContrasena(entity.getContrasena());
        nuevoUsuario.setLogin(entity.getLogin());
        nuevoUsuario.setRol("Proveedor");
        nuevoUsuario.setNombre(entity.getNombre());
        nuevoUsuario.setToken(entity.getId());
        return nuevoUsuario;
    }
    
    /**
     * Actualiza la información de una instancia de Proveedor.
     *
     * @param entity Instancia de ProveedorEntity con los nuevos datos.
     * @return Instancia de ProveedorEntity con los datos actualizados.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public ProveedorEntity updateProveedor(ProveedorEntity entity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un proveedor ");
        if(entity != null)
        {
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
            if(entity.getContrasena() == null || entity.getContrasena().equals(""))
            {
                throw new BusinessLogicException("No puede actualizar a un proveedor sin contraseña");
            }
            // if(entity.getValoracion() > 5 || entity.getValoracion() == null || entity.getValoracion() <0 )
            //  {
            //      throw new BusinessLogicException("No puede actualizar un proveedor con valoración mayor a 5, valoración negativa o valoración nula");
            //  }
            calcularValoracion(entity);
            usuarioLogic.updateUsuario(usuarioLogic.getUsuario(entity.getLogin()));
            return persistence.update(entity);
        }
        else
        {
            throw new BusinessLogicException("El proveedor que se quiere actualizar no existe");
        }
    }
    
    /**
     * Elimina una instancia de Proveedor de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public void deleteProveedor(Long id) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un proveedor ");
        if(getProveedor(id) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para eliminar");
        }
        usuarioLogic.deleteUsuario(getProveedor(id).getLogin());
        persistence.delete(id);
    }
    
    /**
     * Obtiene una colección de instancias de ProductoEntity asociadas a una
     * instancia de Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @return Colección de instancias de ProductoEntity asociadas a la
     * instancia de Proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public List<ProductoEntity> getProductos(Long proveedorId) throws BusinessLogicException
    {
        
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos del proveedor con id = {0}", proveedorId);
        ProveedorEntity proveedor = getProveedor(proveedorId);
        if(proveedor == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para enlistar productos");
        }
        List<ProductoEntity> productos = productoLogic.getProductos();
        List<ProductoEntity> nuevaList = new ArrayList<>();
        for (ProductoEntity producto : productos) {
            
            ProveedorEntity prov = producto.getProveedor();
            if(producto.getProveedor() != null)
            {
                if(prov.getId().equals(proveedorId))
                {
                    nuevaList.add(producto);
                }
            }
        }
        
        
        return nuevaList;
    }
    
    /**
     * Obtiene una instancia de ProductoEntity asociada a una instancia de
     * Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param productosId Identificador de la instancia de Producto
     * @return La entidadd de Libro del proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no se encuentra el producto en el proveedor
     */
    public ProductoEntity getProducto(Long proveedorId, Long productosId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un producto con id = {0}", productosId);
        if(getProveedor(proveedorId) == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para obtener producto");
        }
        List<ProductoEntity> list = getProveedor(proveedorId).getProductos();
        ProductoEntity productosEntity = new ProductoEntity();
        productosEntity.setId(productosId);
        int index = list.indexOf(productosEntity);
        if (index >= 0 && productosEntity.equals(list.get(index)))
        {
            return list.get(index);
        }
        else
        {
            throw new BusinessLogicException("No existe dicho producto en ese proveedor");
        }
    }
    
    /**
     * Asocia un Producto existente a un Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param productosId Identificador de la instancia de Producto
     * @return Instancia de ProductoEntity que fue asociada a Proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * no existe el producto
     */
    public ProductoEntity addProducto(Long proveedorId, Long productosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un producto al proveedor con id = {0}", proveedorId);
        ProveedorEntity ent = getProveedor(proveedorId);
        
        ProductoEntity entS = productoLogic.getProducto(productosId);
        if(ent == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para agregar producto");
        }
        ent.agregarProducto(entS);
        entS.setProveedor(ent);
        productoLogic.updateProducto(entS);
        updateProveedor(ent);
        return entS;
    }
    
    /**
     * Remplaza las instancias de Producto asociadas a una instancia de
     * Proveedor
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param list Colección de instancias de ProductoEntity a asociar a
     * instancia de Proveedor
     * @return Nueva colección de ProductoEntity asociada a la instancia de
     * Proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     * error de logica
     */
    public List<ProductoEntity> replaceProductos(Long proveedorId, List<ProductoEntity> list) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los productos asocidos al proveedor con id = {0}", proveedorId);
        ProveedorEntity proveedor = getProveedor(proveedorId);
        
        if (list == null)
        {
            throw new BusinessLogicException("No hay lista nueva.");
        }
        if (!list.isEmpty())
        {
        }
        else
        {
            throw new BusinessLogicException("No hay lista nueva o la lista está vacía");
        }
        if (proveedor != null)
        {
            proveedor.setProductos(list);
            updateProveedor(proveedor);
        }
        else
        {
            throw new BusinessLogicException("El proveedor al que se le quiere reemplazar productos es nulo");
        }
        return list;
    }
    
    /**
     * Desasocia un Producto existente de un Proveedor existente
     *
     * @param proveedorId Identificador de la instancia de Proveedor
     * @param productosId Identificador de la instancia de Producto
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException si
     * el proveedor no tiene ese producto
     */
    public void removeProducto(Long proveedorId, Long productosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un producto del proveedor con id = {0}", proveedorId);
        ProveedorEntity ent = getProveedor(proveedorId);
        if(ent == null)
        {
            throw new BusinessLogicException("No existe un proveedor con dicho id para remover producto");
        }
        ProductoEntity entS = productoLogic.getProducto(productosId);
        int index = ent.getProductos().indexOf(entS);
        if (index >= 0)
        {
            ent.removerProducto(entS);
            updateProveedor(ent);
            productoLogic.deleteProducto(productosId);
        }
        else
        {
            throw new BusinessLogicException("El proveedor no tiene ese producto");
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
            ent.agregarContrato(entC);
            updateProveedor(ent);
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
        if (index >= 0)
        {
            ent.removerContrato(entC);
            updateProveedor(ent);
        }
        else
        {
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
            /**if (contratos.contains(contrato))
             * {
             * contrato.setProveedor(proveedor);
             * contratoLogic.updateContrato(contrato);
             * }
             * else if (null != contrato.getProveedor() && contrato.getProveedor().equals(proveedor))
             * {
             * */
            contratoLogic.deleteContrato(contrato.getId());
            //}
        }
        
        for (ContratoEntity contrato : contratos)
        {
            contrato.setProveedor(proveedor);
            contratoLogic.createContrato(contrato);
        }
        proveedor.setContratos(contratos);
        updateProveedor(proveedor);
        return contratos;
    }
    
    /**
     * Retorna todos los contratos asociados a un proveedor
     *
     * @param proveedorId El ID del proveedor buscada
     * @return La lista de contratos del proveedor
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
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
        if (index >= 0)
        {
            return contratos.get(index);
        }
        else
        {
            throw new BusinessLogicException("El contrato no está asociado al proveedor");
        }
    }
    
    /**
     *
     * @param proveedoresId
     * @return
     */
    public List<BonoEntity> getBonos(Long proveedoresId) throws BusinessLogicException {
        ProveedorEntity proveedor = getProveedor(proveedoresId);
        if(proveedor == null)
            throw new BusinessLogicException("No existe un proveedor con dicho id para enlistar bonos");
        return bonoLogic.getBonos(proveedoresId);
    }
    
    public BonoEntity getBonoP(Long bonoId, Long proveedorId) throws BusinessLogicException {
        ProveedorEntity proveedor = getProveedor(proveedorId);
        BonoEntity bono = bonoLogic.getBono(bonoId);
        if(proveedor == null)
            throw new BusinessLogicException("No existe el proveedor.");
        if(bono == null)
            throw new BusinessLogicException("No existe el bono.");
        if(bono.getProveedor().getId()!=proveedor.getId())
            throw new BusinessLogicException("El bono a buscar existe, pero el proveedor no corresponde");
        return bono;
    }
    
    
    public BonoEntity getBono(Long bonoId) throws BusinessLogicException {
        BonoEntity bono = bonoLogic.getBono(bonoId);
        if(bono==null)
            throw new BusinessLogicException("El bono a buscar no existe.");
        return bono;
    }
    
    public BonoEntity addBono(BonoEntity bono, Long proveedoresId) throws BusinessLogicException {
        ProveedorEntity proveedor = getProveedor(proveedoresId);
        if(bono== null)
            throw new BusinessLogicException("El bono es vacío.");
        bono.setProveedor(proveedor);
        bonoLogic.createBono(bono);
        proveedor.addBono(bono);
        updateProveedor(proveedor);
        return bono;
    }
    
    /*public BonoEntity setBono2Contrato(long bonoId, long proveedoresId, long contratoId) throws BusinessLogicException {
    ProveedorEntity proveedor = getProveedor(proveedoresId);
    if(proveedor== null)
    throw new BusinessLogicException("El proveedor no existe.");
    BonoEntity bono=getBono(bonoId);
    if(bono==null)
    throw new BusinessLogicException("El bono a aplicar no existe.");
    ContratoEntity contrato = getContrato(proveedoresId, contratoId);
    if(contrato == null)
    throw new BusinessLogicException("El contrato no existe.");
    if( bonoLogic.getBono(proveedoresId, contrato.getId())!=null)
    {
    throw new BusinessLogicException("El proveedor ya aplicó un bono a ese contrato.");
    }
    bono.setContrato(contrato);
    bonoLogic.updateBono(bono);
    updateProveedor(proveedor);
    contrato.setValor(contrato.getValor()*(1-bono.getDescuento())/100);
    contratoLogic.updateContrato(contrato);
    return bono;
    }**/
    
    public List<BonoEntity> replaceBonos(Long proveedoresId, List<BonoEntity> bonos) throws BusinessLogicException {
        ProveedorEntity proveedor = getProveedor(proveedoresId);
        
        for (BonoEntity bono : bonos) {
            if(bonoLogic.getBono(bono.getId())!=null)
                throw new BusinessLogicException("Se quiere añadir bono que ya existe");
            bono.setProveedor(proveedor);
            bonoLogic.createBono(bono);
        }
        proveedor.setBonos(bonos);
        updateProveedor(proveedor);
        return bonos;
    }
    
    
    public void removeBono(Long bonosId, Long proveedoresId) throws BusinessLogicException {
        ProveedorEntity proveedor = getProveedor(proveedoresId);
        BonoEntity bono = bonoLogic.getBono(bonosId);
        if(bono==null)
            throw new BusinessLogicException("No se encuentra el bono especificado.");
        if(bono.getProveedor().getId()!=proveedor.getId())
            throw new BusinessLogicException("No puede eliminarse un bono de otro proveedor.");
        proveedor.getBonos().remove(bono);
        updateProveedor(proveedor);
        bonoLogic.deleteBono(bonosId);
    }
    
    public void calcularValoracion(ProveedorEntity entity)
    {
        List<ProductoEntity> productos = entity.getProductos();
        
        double valoracionProveedor = 0;
        if(!productos.isEmpty())
        {
            for(ProductoEntity producto : productos)
            {
                if(producto != null)
                {
                    if(producto.getValoracionPromedio()!= null)
                    {
                        valoracionProveedor += producto.getValoracionPromedio();
                    }
                }
            }
            
            
            valoracionProveedor = valoracionProveedor/productos.size();
        }
        else
        {
            valoracionProveedor = 5;
        }
        
        entity.setValoracion(valoracionProveedor);
    }
}
