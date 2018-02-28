/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import java.util.*;

/**
 * Clase que extiende de {@link ContratoDTO} para manejar la transformacion
 * entre los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del contrato vaya a la documentacion de {@link ContratoDTO}
 *
 * @author mc.gonzalez15
 */
public class ContratoDetailDTO extends ContratoDTO {

    /**
     * Proveedor del contrato
     */
    private ProveedorDTO proveedor;

    /**
     * Evento del contrato
     */
    private EventoDTO evento;

    /**
     * Lista de productos
     */
    private List<ProductoDTO> productos;

    /**
     * Método constructor
     */
    public ContratoDetailDTO() {
        super();
    }

    /**
     * Retorna el proveedor
     *
     * @return proveedor
     */
    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    /**
     * Asigna el proveedor
     *
     * @param proveedor el nuevo proveedor
     */
    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * Retorna el evento
     *
     * @return evento
     */
    public EventoDTO getEvento() {
        return evento;
    }

    /**
     * Asigna el evento
     *
     * @param evento el nuevo evento
     */
    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }

    /**
     * Retorna la lista de productos del contrato
     *
     * @return productos
     */
    public List<ProductoDTO> getProductos() {
        return productos;
    }

    /**
     * Asigna la lista de productos del contrato
     *
     * @param productos los nuevos productos
     */
    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    /**
     * Crea un objeto ContratoDetailDTO a partir de un objeto ContratoEntity
     * incluyendo los atributos de ContratoDTO.
     *
     * @param entity Entidad ContratoEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public ContratoDetailDTO(ContratoEntity entity) {
        super(entity);
        if (entity != null) {
            proveedor = new ProveedorDTO(entity.getProveedor());
            evento = new EventoDTO(entity.getEvento());
            productos = new ArrayList<>();

            for (ProductoEntity entityProductos : entity.getProductos()) {
                productos.add(new ProductoDTO(entityProductos));
            }
        }
    }

    /**
     * Convierte un objeto ContratoDetailDTO a ContratoEntity incluyendo los
     * atributos de ContratoDTO.
     *
     * @return Nueva objeto ContratoEntity.
     *
     */
    @Override
    public ContratoEntity toEntity() {
        ContratoEntity entity = super.toEntity();
        entity.setProveedor(this.proveedor.toEntity());
        entity.setEvento(this.evento.toEntity());

        if (productos != null) {
            List<ProductoEntity> productosn = new ArrayList<>();
            for (ProductoDTO dtoProductos : this.getProductos()) {
                productosn.add(dtoProductos.toEntity());
            }

            entity.setProductos(productosn);

        }
        return entity;
    }
}
