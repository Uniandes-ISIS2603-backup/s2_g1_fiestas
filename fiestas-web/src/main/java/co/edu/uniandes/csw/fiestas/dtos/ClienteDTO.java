package co.edu.uniandes.csw.fiestas.dtos;

/**
 * ClienteDTO Objeto de transferencia de datos de Cliente. Los DTO contienen las
 * represnetaciones de los JSON que se transfieren entre el cliente y el
 * servidor.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre: string,
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
 *      "nombre: David,
 *      "documento": 201412734,
 *      "telefono": 3144683924,
 *      "correo": df.nino10@uniandes.edu.co,
 *      "direccion": cll 1#1-1,
 *      "login": df.nino10,
 *   }
 *
 * </pre>
 *
 * @author ISIS2603
 */
public class ClienteDTO extends UsuarioDTO
{
    private long id;
    
    public ClienteDTO()
    {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
