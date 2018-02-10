package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;

/**
 * Clase que extiende de {@link ClienteDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido de la ciudad vaya a la documentacion de {@link ClienteDTO}
 */
public class ClienteDetailDTO extends ClienteDTO {
    
    private ArrayList<EventoDTO> eventos;

    /**
     * Constructor por defecto
     */
    public ClienteDetailDTO() {
    }

    public ArrayList getEventos() {
        return eventos;
    }

    public void setClienteDTO(ArrayList<EventoDTO> eventos) {
        this.eventos = eventos;
    }

}
