package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;

/**
 * ClienteDTO Objeto de transferencia de datos de Cliente. Los DTO contienen las
 * represnetaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "documento": string
 *      "telefono": number
 *      "correo": string
 *      "direccion": string
 *      "login": string
 *   }
 * </pre> Por ejemplo un cliente se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 113742,
 *      "nombre": David,
 *      "documento": 201412734,
 *      "telefono": 3144683924,
 *      "correo": df.nino10@uniandes.edu.co,
 *      "direccion": cll 1#1-1,
 *      "login": df.nino10,
 *   }
 *
 * </pre>
 *
 * @author nm.hernandez10
 */
public class ClienteDTO extends UsuarioDTO
{   
    public ClienteDTO()
    {
        
    }
        
    public ClienteDTO(ClienteEntity entity)
    {
        if (entity != null)
        {            
            super(entity);
        }
    }
    
    public ClienteEntity toEntity()
    {
        ClienteEntity entity = super.toEntity();       
    }
}
