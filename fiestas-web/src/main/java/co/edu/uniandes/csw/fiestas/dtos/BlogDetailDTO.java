/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 * Clase que extiende de {@link BlogDTO} para manejar la transformacion entre
 * los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido de la ciudad vaya a la documentacion de {@link BlogDTO}
 * @author mc.gonzalez15
 */
public class BlogDetailDTO {
    
    /**
     * Usuario dueño del blog
     */
    private UsuarioDTO usuario;
    
    /**
     * Evento del que se escribe
     */
    private EventoDTO evento;

    /**
     * Método constructor
     */
    public BlogDetailDTO()
    {
        
    }
    
    /**
     * Retorna el usuario 
     * @return usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * Asigna el usuario

     * @param usuario el nuevo usuario

     * @param usuario - Es el usuario dueño del Blog 

     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna el evento
     * @return evento
     */
    public EventoDTO getEvento() {
        return evento;
    }

    /**
     * Asigna el evento

     * @param evento  el nuevo evento 

     * @param evento - Es el evento del Blog 

     */
    public void setEvento(EventoDTO evento) {
        this.evento = evento;
    }
    
    
    
}
