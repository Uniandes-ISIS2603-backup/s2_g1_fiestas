/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;

/**
 *
 * @author ls.arias
 */
public class ValoracionDetailDTO extends ValoracionDTO {
    
     /**
     * Constructor por defecto
     */
    public ValoracionDetailDTO() {
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity La entidad de ciudad a partir de la cual se construye el objeto
     */
    public ValoracionDetailDTO(ValoracionEntity entity) {
        super(entity);
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return  La entidad construida a partir del DTO.
     */
    @Override
    public ValoracionEntity toEntity() {
        ValoracionEntity valoracionE = super.toEntity();
        return valoracionE;
    }

    
}
