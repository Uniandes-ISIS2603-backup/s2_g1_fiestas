/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;

/**
 *
 * @author ls.arias
 */
public class ServicioDetailDTO extends ServicioDTO {
     /**
     * Constructor por defecto
     */
    public ServicioDetailDTO() {
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity La entidad de ciudad a partir de la cual se construye el objeto
     */
    public ServicioDetailDTO(ServicioEntity entity) {
        super(entity);
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return  La entidad construida a partir del DTO.
     */
    @Override
    public ServicioEntity toEntity() {
        ServicioEntity servicioE = super.toEntity();
        return servicioE;
    }
    
}
