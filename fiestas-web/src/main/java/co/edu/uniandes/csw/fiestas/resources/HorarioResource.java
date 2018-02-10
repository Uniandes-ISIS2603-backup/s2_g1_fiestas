/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.HorarioDetailDTO;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author df.nino10
 */
@Path("horarios")
public class HorarioResource {
    
    public HorarioResource(){
    }
    
    @GET
    public HorarioDetailDTO getHorario(@PathParam("id") Long id){
      return null;
    }
    
    @GET
    @Path("{id: \\d+}")
    public List<HorarioDetailDTO> getHorario(){
      return null;
    }
    
    @POST
    public HorarioDetailDTO createHorario(HorarioDetailDTO horario) throws BusinessLogicException{
        return horario;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public HorarioDetailDTO updateHorario(@PathParam("id") Long id){
        return null;
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteHorario(@PathParam("id") Long id){
    }
}
