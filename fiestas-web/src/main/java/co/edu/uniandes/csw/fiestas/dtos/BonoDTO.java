/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
import java.util.Date;

/**
 *
 * @author df.nino10
 */
public class BonoDTO {
    /**
     * Identificador del Bono
     */
    private long id;

    /**
     * Fecha desde la cual el Bono aplica
     */
    private Date aplicaDesde;

    /**
     * Fecha de expiración del bono
     */
    private Date expira;

    /**
     * Razón del bono
     */
    private String motivo;
    
    /**
     * Descuento en %
     */
    private int descuento;

    /**
     * Constructor vacío BonoDTO
     */
    public BonoDTO() {
        //Constructor vacio
    }

    /**
     * Crea un objeto BonoDTO a partir de un objeto BonoEntity.
     *
     * @param entity Entidad BonoEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public BonoDTO(BonoEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.aplicaDesde = entity.getAplicaDesde();
            this.expira = entity.getExpira();
            this.motivo = entity.getMotivo();

        }
    }

    /**
     * Retorna el identificador
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Asigna el identificador
     *
     * @param id a asignar
     */
    public void setId(long id) {
        this.id = id;
    }

        /**
     * @return the aplicaDesde
     */
    public Date getAplicaDesde() {
        return aplicaDesde;
    }

    /**
     * @param aplicaDesde the aplicaDesde to set
     */
    public void setAplicaDesde(Date aplicaDesde) {
        this.aplicaDesde = aplicaDesde;
    }

    /**
     * @return the expira
     */
    public Date getExpira() {
        return expira;
    }

    /**
     * @param expira the expira to set
     */
    public void setExpira(Date expira) {
        this.expira = expira;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    /**
     * @return the descuento
     */
    public int getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    /**
     * Convierte un objeto BonoDTO a BonoEntity.
     *
     * @return Nuevo objeto BonoEntity.
     *
     */
    public BonoEntity toEntity() {
        BonoEntity entity = new BonoEntity();
        entity.setId(this.getId());
        entity.setAplicaDesde(this.getAplicaDesde());
        entity.setExpira(this.getExpira());
        entity.setMotivo(this.getMotivo());
        return entity;
    }
}