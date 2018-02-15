package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link ClienteDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido de la ciudad vaya a la documentacion de {@link ClienteDTO}
 */
public class ClienteDetailDTO extends ClienteDTO 
{
    
    private List<EventoDTO> eventos;

    /**
     * Constructor por defecto
     */
    public ClienteDetailDTO() 
    {
        
    }

    public List getEventos() {
        return eventos;
    }

    public void setClienteDTO(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }

}
