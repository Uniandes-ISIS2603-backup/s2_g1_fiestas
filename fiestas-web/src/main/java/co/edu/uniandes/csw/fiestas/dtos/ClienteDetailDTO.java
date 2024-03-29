package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link ClienteDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido de el cliente vaya a la documentacion de {@link ClienteDTO} * Al
 * serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "documento": string
 *      "telefono": number
 *      "correo": string
 *      "direccion": string
 *      "login": string
 *   "eventos":
 *      {
 *      "id": number,
 *      "fecha": string,
 *      "descripcion": string,
 *      "celebrado": string,
 *      "lugar": string,
 *      "inventados": number
 *      }
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
 *
 *      "eventos":
 *      [{
 *      "id": 3425,
 *      "fecha": 01/01/12,
 *      "descripcion": "fiesta infantil de carlitos",
 *      "celebrado": carlitos,
 *      "lugar": divercity santa fé,
 *      "inventados": 35
 *      },
 *      "id": 5657,
 *      "fecha": 01/01/12,
 *      "descripcion": "fiesta infantil de Juancito",
 *      "celebrado": Juancito,
 *      "lugar": divercity santa fé,
 *      "inventados": 35]
 *   }
 *
 * </pre>
 */
public class ClienteDetailDTO extends ClienteDTO {

    private List<EventoDTO> eventos;

    /**
     * Constructor por defecto
     */
    public ClienteDetailDTO() {
        super();
    }

    /**
     * Convierte un CLienteEntity en un ClienteDetailDTO
     *
     * @param entity ClienteEntity instancia
     */
    public ClienteDetailDTO(ClienteEntity entity) {
        super(entity);
        if (entity.getEventos() != null) {
            eventos = new ArrayList<>();
            for (EventoEntity ent : entity.getEventos()) {
                EventoDTO dto = new EventoDTO(ent);
                eventos.add(dto);
            }
        }
    }
    
      /**
     * Método que transforma la clase ClienteDetailDTO a ClienteEntity.
     *
     * @return ClienteEntity instancia.
     */
    @Override
    public ClienteEntity toEntity() {
        ClienteEntity entity = super.toEntity();
        if (eventos!=null) {
            List<EventoEntity> listEventos = new ArrayList<>();
            for (EventoDTO dto : getEventos()) {
                EventoEntity ent = dto.toEntity();
                listEventos.add(ent);
            }
            entity.setEventos(listEventos);
        }

        return entity;
    }

    /**
     * @return Los eventos del cliente.
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * @param eventos Los nuevos eventos.
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }
}
