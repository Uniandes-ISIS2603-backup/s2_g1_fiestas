/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.BonoEntity;

/**
 *
 * @author df.nino10
 */
public class BonoDetailDTO extends BonoDTO{
     /**
     * Proveedor dueño del bono
     */
    private ProveedorDTO proveedor;
    
    /**
     * Contrato al que se aplica
     */
    private ContratoDTO contrato;

    /**
     * Método constructor vacío
     */
    public BonoDetailDTO()
    {
        super();
    }
    
     /**
     * Crea un objeto BonoDetailDTO a partir de un objeto BonoEntity
     * incluyendo los atributos de BonoDTO.
     *
     * @param entity Entidad BonoEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public BonoDetailDTO (BonoEntity entity) 
    {
        super(entity);
        if(entity.getProveedor()!=null)proveedor = new ProveedorDTO(entity.getProveedor());
        if(entity.getContrato()!=null)contrato = new ContratoDTO(entity.getContrato());
       
    }

    /**
     * Convierte un objeto BonoDetailDTO a BonoEntity incluyendo los
     * atributos de BonoDTO.
     *
     * @return Nueva objeto BonoEntity.
     *
     */
    @Override
    public BonoEntity toEntity() {
        BonoEntity entity = super.toEntity();
        if(this.getProveedor() !=null) entity.setProveedor(this.getProveedor().toEntity());
        if(this.getContrato() !=null) entity.setContrato(this.getContrato().toEntity());
        
        return entity;
    }

    /**
     * @return the proveedor
     */
    public ProveedorDTO getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the contrato
     */
    public ContratoDTO getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }
    
    
}
