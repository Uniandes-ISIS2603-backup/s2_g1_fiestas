/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import java.util.ArrayList;
import java.util.List;



/**
 * Clase que extiende de {@link ServicioDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del servicio vaya a la documentacion de {@link ServicioDTO}
 * 
 * <pre>
 *   {
 *      "id": number,
 *      "comentario": string,
 *      "calificacion": string,
 *      "proveedores": [{"id": 1,
 *      "nombre": string,
 *      "documento": string,
 *      "telefono": number,
 *      "correo": string,
 *      "direccion": string,
 *      },
 *      ...
 *      ]
 *   }
 * </pre>
 * Por ejemplo el atributo extra en ServicioDetail se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 524,
 *      "comentario": "Servicio de catering",
 *      "calificacion": "Comida",
 *      "proveedores": [{"id": 123,
 *      "nombre": "Pepito Perez",
 *      "documento": "1254699749",
 *      "telefono": 84645589,
 *      "correo": "pepito@hotmail.com",
 *      "direccion": "avenida feliz #85-24",
 *      },
 *      {"id": 124,
 *      "nombre": "Juanito Perez",
 *      "documento": "8752646467",
 *      "telefono": 98755589,
 *      "correo": "juanito@hotmail.com",
 *      "direccion": "avenida retiro #86-41", 
 *      ]
 *   }
 *
 * </pre>
 *
 * @author ls.arias
 */
public class ServicioDetailDTO extends ServicioDTO {
     /**
     * Constructor por defecto
     */
    public ServicioDetailDTO() 
    {
        
    }
    /**
     * Constructor por defecto
     */
    public ServicioDetailDTO(ServicioEntity e) 
    {
        super(e);
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
    
       /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ServicioEntity toEntity() {
        ServicioEntity entity = super.toEntity();
        List<ProveedorEntity> listProveedores = new ArrayList<ProveedorEntity>();
        for(ProveedorDTO dto : getProveedores())
        {
            ProveedorEntity ent = dto.toEntity();
            listProveedores.add(ent);
        }
        
        entity.setProveedores(listProveedores);
        return entity;
    }
    
}
