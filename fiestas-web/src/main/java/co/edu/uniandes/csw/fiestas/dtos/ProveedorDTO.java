/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 * Clase que extiende de {@link UsuarioDTO} para manejar los proveedores del negocio. Para conocer el
 * contenido de un proveedor vaya a la documentacion de {@link UsuarioDTO}
 * @author nm.hernandez10
 */
public class ProveedorDTO extends UsuarioDTO
{
    private long id;
    
    /**
     * Constructor por defecto
     */
    public ProveedorDTO()
    {
        
    }    
    
    
    /**
     * @return El id del proveedor.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id El nuevo id.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
