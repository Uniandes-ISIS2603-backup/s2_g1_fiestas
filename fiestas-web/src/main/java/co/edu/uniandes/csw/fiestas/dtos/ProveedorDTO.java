package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;

/**
 * Clase que extiende de {@link UsuarioDTO} para manejar los proveedores del negocio. Para conocer el
 * contenido de un proveedor vaya a la documentacion de {@link UsuarioDTO} más un atributo extra que corresponde
 * 
 * * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "documento": string,
 *      "telefono": number,
 *      "correo": string,
 *      "direccion": string,
 *      "login": string,
 *      "penalizado": boolean
 *   }
 * </pre>
 * Por ejemplo un proveedor se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 7,
 *      "nombre": "Nicolás Hernández",
 *      "documento": "101010101",
 *      "telefono": 3111234567,
 *      "correo": "nicolashernandez@hotmail.com",
 *      "direccion": "Calle 97 # 6-43",
 *      "login": "nm.hernandez10",
 *      "penalizado": False
 *   }
 *
 * </pre>
 * @author nm.hernandez10
 */
public class ProveedorDTO extends UsuarioDTO
{    
    
    /**
     * Constructor por defecto
     */
    public ProveedorDTO()
    {
        
        
        
    }      
    private boolean penalizado;

    /**
     * @return the penalizado
     */
    public boolean isPenalizado() {
        return penalizado;
    }

    /**
     * @param penalizado the penalizado to set
     */
    public void setPenalizado(boolean penalizado) {
        this.penalizado = penalizado;
    }
    
    public ProveedorDTO(ProveedorEntity entity)
    {
        if (entity != null)
        {
            super(entity);
            this.penalizado = entity.isPenalizado();             
        }
    }
    
    public ProveedorEntity toEntity()
    {
        ProveedorEntity entity = super.toEntity(); 
        return entity;
    }
}
