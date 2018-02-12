/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.EventoDetailDTO;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author cm.amaya10
 */

@Path("eventos")
@Produces("application/json")
@RequestScoped
public class EventoResource {
    
    /**
     * 
     * @return 
     */
    @GET
    public List<EventoDetailDTO> getEventos(){
        return new ArrayList<EventoDetailDTO>();
    }
    
    @GET
    @Path("{id: \\d+}")
    public EventoDetailDTO getEvento(@PathParam("id") Long id) {
        return null;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public EventoDetailDTO updateEvento(@PathParam("id") Long id){
        return null;
    }
    
    
   @DELETE
    @Path("{id: \\d+}")
    public void deleteEvento(@PathParam("id") Long id){
    }
    
}
