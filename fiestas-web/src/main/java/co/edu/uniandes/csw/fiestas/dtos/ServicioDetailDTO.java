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
public class ServicioDetailDTO extends ServicioDTO {
     /**
     * Constructor por defecto
     */
    public ServicioDetailDTO() {
    }
    
    private ArrayList<ServicioDTO> proveedores;
    
    public ArrayList<ServicioDTO> getProveedores()
    {
        return proveedores;
    }
    
}
