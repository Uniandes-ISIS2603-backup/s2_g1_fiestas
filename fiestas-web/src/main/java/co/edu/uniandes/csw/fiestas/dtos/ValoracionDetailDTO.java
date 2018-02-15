/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;


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

    private ArrayList<ValoracionDTO> eventos;
    
    public ArrayList<ValoracionDTO> getEventos()
    {
        return eventos;
    }

}
