/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

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
 *      "login": string
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
 *      "login": "nm.hernandez10"
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
}
