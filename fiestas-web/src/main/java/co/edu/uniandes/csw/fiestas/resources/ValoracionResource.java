/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.ServicioDetailDTO;
import co.edu.uniandes.csw.fiestas.dtos.ValoracionDetailDTO;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author ls.arias
 */


@Path("valoracion")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ValoracionResource {
    
       @POST
    public ValoracionDetailDTO createValoracion(ValoracionDetailDTO valoracion) throws BusinessLogicException {
        return valoracion;
    }
    @GET
    public List<ValoracionDetailDTO> getValoraciones() {
        return new ArrayList<>();
    }
    @GET
    @Path("{id: \\d+}")
    public ValoracionDetailDTO getValoracion(@PathParam("id") Long id) {
        return null;
    }
     @PUT
    @Path("{id: \\d+}")
    public ValoracionDetailDTO updateValoracion(@PathParam("id") Long id, ValoracionDetailDTO valoracion) throws BusinessLogicException {
        return valoracion;
    }
    @DELETE
    @Path("{id: \\d+}")
     public void deleteValoracion(@PathParam("id") Long id) {
        // Void
    }
    
}
