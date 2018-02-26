/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;
import java.util.List;



/**
 * Clase que extiende de {@link ServicioDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del servicio vaya a la documentacion de {@link ServicioDTO}
 * @author ls.arias
 */
public class ServicioDetailDTO extends ServicioDTO {
     /**
     * Constructor por defecto
     */
    public ServicioDetailDTO() 
    {
        
    }
     private List<ProveedorDTO> proveedores;
     
    /**
     * @return La lista de proveedores del servicio
     */
    public List<ProveedorDTO> getProveedores()
    {
        return proveedores;
    }
    /**
     * @param proveedores La nueva lista de proveedores del servicio
     */
    
    public void setProveedores(List<ProveedorDTO> proveedores)
    {
        this.proveedores = proveedores;
    }
}
