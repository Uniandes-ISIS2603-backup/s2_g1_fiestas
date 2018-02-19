/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.List;


/**
 * Clase que extiende de {@link ValoracionDTO}
 * @author ls.arias
 */
public class ValoracionDetailDTO extends ValoracionDTO {
    
     /**
     * Constructor por defecto
     */
    public ValoracionDetailDTO() {
    }

    private List<ServicioDTO> servicios;
    
    /**
    * @return La lista de servicios.
    */
    public List<ServicioDTO> getServicios()
    {
        return servicios;
    }
    
    /**
    * @param servicios nuevos servicios 
    */
    public void setServicios(List<ServicioDTO> servicios)
    {
        this.servicios = servicios;
    }

}
