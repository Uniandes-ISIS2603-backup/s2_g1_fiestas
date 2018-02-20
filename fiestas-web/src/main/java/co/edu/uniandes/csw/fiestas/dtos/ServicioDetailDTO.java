/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;
import java.util.List;



/**
 * Clase que extiende de {@link ServicioDTO} 
 * 
 * <pre>
 *   {
 *      "id": number,
 *      "descripcion": string,
 *      "tipo": string,
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
 *      "descripcion": "Servicio de catering",
 *      "tipo": "Comida",
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
