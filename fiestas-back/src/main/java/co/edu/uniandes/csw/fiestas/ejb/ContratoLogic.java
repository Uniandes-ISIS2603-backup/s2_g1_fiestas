/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ContratoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author mc.gonzalez15
 */
public class ContratoLogic {

    private static final Logger LOGGER = Logger.getLogger(ContratoLogic.class.getName());

    @Inject
    private ContratoPersistence persistence;

    @Inject
    private EventoLogic eventoLogic;

    @Inject
    private ProveedorLogic proveedorLogic;

    @Inject
    private ProductoLogic productoLogic;

    /**
     * Obtiene la lista de los registros de Contrato.
     *
     * @return Colección de objetos de ContratoEntity.
     */
    public List<ContratoEntity> getContratos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los contratos");
        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Contrato a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ContratoEntity con los datos del Contrato
     * consultado.
     */
    public ContratoEntity getContrato(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un contrato con id = {0}", id);
        return persistence.find(id);
    }

    /**
     * Se encarga de crear un Contrato en la base de datos.
     *
     * @param entity Objeto de ContratoEntity con los datos nuevos
     * @return Objeto de ContratoEntity con los datos nuevos y su ID.
     */
    public ContratoEntity createContrato(ContratoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un contrato ");

        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Contrato.
     *
     * @param entity Instancia de ContratoEntity con los nuevos datos.
     * @return Instancia de ContratoEntity con los datos actualizados.
     */
    public ContratoEntity updateContrato(ContratoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un contrato ");
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Contrato de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteContrato(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un contrato ");
        persistence.delete(id);
    }
    
          /**
     * Asocia un Evento existente a un Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param eventoId Identificador de la instancia de Evento
     * @return Instancia de EventoEntity que fue asociada a Contrato
     */
    public EventoEntity addEvento(Long contratoId, Long eventoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un evento al contrato con id = {0}", contratoId);
        ContratoEntity entity = this.getContrato(contratoId);
        EventoEntity entityU = eventoLogic.getEvento(eventoId);
        entity.setEvento(entityU);
        return entityU;
    }

    /**
     * Borrar el evento de un contrato
     *
     * @param eventoId El evento que se desea borrar del contrato.
     * @param contratoId El contrato de la cual se desea eliminar.
     */
    public void removeEvento(Long eventoId, Long contratoId) {
        ContratoEntity contratoEntity = getContrato(contratoId);
        EventoEntity evento = eventoLogic.getEvento(eventoId);
        evento.getContratos().remove(contratoEntity);
        contratoEntity.setEvento(null);
    }

    /**
     * Remplazar el evento de un contrato
     *
     * @param evento Nuevo evento del contrato
     * @param contratoId El id del contrato que se quiere actualizar.
     * @return El nuevo evento del contrato
     */
    public EventoEntity replaceEvento(Long contratoId, EventoEntity evento) {
        ContratoEntity contrato = getContrato(contratoId);
        contrato.setEvento(evento);
        return evento;
    }

    /**
     * Retorna el evento asociado a un contrato
     *
     * @param contratoId El id del contrato a buscar.
     * @return El evento encontrado dentro del contrato.
     * @throws BusinessLogicException Si el evento no se encuentra en el
     * contrato
     */
    public EventoEntity getEvento(Long contratoId) throws BusinessLogicException {
        try {
            EventoEntity evento = getContrato(contratoId).getEvento();
            return evento;

        } catch (Exception e) {
            throw new BusinessLogicException("El contrato con id " + contratoId + " no existe");
        }

    }

          /**
     * Asocia un Proveedor existente a un Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param proveedorId Identificador de la instancia de Evento
     * @return Instancia de EventoEntity que fue asociada a Contrato
     */
    public ProveedorEntity addProveedor(Long contratoId, Long proveedorId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un proveedor al contrato con id = {0}", contratoId);
        ContratoEntity entity = this.getContrato(contratoId);
        ProveedorEntity entityU = proveedorLogic.getProveedor(proveedorId);
        entity.setProveedor(entityU);
        return entityU;
    }
    
    /**
     * Borrar el proveedor de un contrato
     *
     * @param proveedorId El proveedor que se desea borrar del contrato.
     * @param contratoId El contrato del cual se desea eliminar.
     */
    public void removeProveedor(Long proveedorId, Long contratoId) {
        ContratoEntity contratoEntity = getContrato(contratoId);
        ProveedorEntity proveedor = proveedorLogic.getProveedor(proveedorId);
        proveedor.getContratos().remove(contratoEntity);
        contratoEntity.setEvento(null);
    }

    /**
     * Remplazar el proveedor de un contrato
     *
     * @param proveedor
     * @param contratoId El id del contrato que se quiere actualizar.
     * @return El nuevo proveedor del contrato
     */
    public ProveedorEntity replaceProveedor(Long contratoId, ProveedorEntity proveedor) {
        ContratoEntity contrato = getContrato(contratoId);
        contrato.setProveedor(proveedor);
        return proveedor;
    }

    /**
     * Retorna el proveedor asociado a un contrato
     *
     * @param contratoId El id del contrato a buscar.
     * @return El proveedor encontrado dentro del contrato.
     * @throws BusinessLogicException Si el proveedor no se encuentra en el
     * contrato
     */
    public ProveedorEntity getProveedor(Long contratoId) throws BusinessLogicException {
        try {
            ProveedorEntity proveedor = getContrato(contratoId).getProveedor();
            return proveedor;

        } catch (Exception e) {
            throw new BusinessLogicException("El contrato con id " + contratoId + " no existe");
        }
    }

    /**
     * Obtiene una colección de instancias de ProductoEntity asociadas a una
     * instancia de Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @return Colección de instancias de ProductoEntity asociadas a la
     * instancia de Contrato
     */
    public List<ProductoEntity> listProductos(Long contratoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos del autor con id = {0}", contratoId);
        return getContrato(contratoId).getProductos();
    }

    /**
     * Obtiene una instancia de ProductoEntity asociada a una instancia de
     * Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param ProductosId Identificador de la instancia de Producto
     * @return La entidadd de Libro del autor
     */
    public ProductoEntity getProducto(Long contratoId, Long ProductosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un producto con id = {0}", ProductosId);
        List<ProductoEntity> list = getContrato(contratoId).getProductos();
        ProductoEntity ProductosEntity = new ProductoEntity();
        ProductosEntity.setId(ProductosId);
        int index = list.indexOf(ProductosEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    /**
     * Asocia un Producto existente a un Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param ProductosId Identificador de la instancia de Producto
     * @return Instancia de ProductoEntity que fue asociada a Contrato
     */
    public ProductoEntity addProducto(Long contratoId, Long ProductosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregar un producto al contrato con id = {0}", contratoId);
        ContratoEntity contrato = this.getContrato(contratoId);
        ProductoEntity producto = productoLogic.getProducto(ProductosId);
        List<ProductoEntity> productos = contrato.getProductos();
        productos.add(producto);
        contrato.setProductos(productos);
        return producto;
    }

    /**
     * Remplaza las instancias de Producto asociadas a una instancia de Contrato
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param list Colección de instancias de ProductoEntity a asociar a
     * instancia de Contrato
     * @return Nueva colección de ProductoEntity asociada a la instancia de
     * Contrato
     */
    public List<ProductoEntity> replaceProductos(Long contratoId, List<ProductoEntity> list) {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar los productos asocidos al contrato con id = {0}", contratoId);
        ContratoEntity contratoEntity = getContrato(contratoId);
        contratoEntity.setProductos(list);
        return contratoEntity.getProductos();
    }

    /**
     * Desasocia un Producto existente de un Contrato existente
     *
     * @param contratoId Identificador de la instancia de Contrato
     * @param ProductosId Identificador de la instancia de Producto
     */
    public void removeProducto(Long contratoId, Long ProductosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un producto del contrato con id = {0}", contratoId);
        ContratoEntity contrato = getContrato(contratoId);
        ProductoEntity producto = getProducto(contratoId, ProductosId);
        contrato.getProductos().remove(producto);

    }

}
