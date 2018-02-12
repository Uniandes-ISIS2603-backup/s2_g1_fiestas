/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import co.edu.uniandes.csw.fiestas.dtos.PagoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * <pre>Clase que implementa el recurso "pagos".
 * URL: /api/proveedores
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "pagos.</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author cm.amaya10
 */

@Path("eventos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PagoResource {
    
    @GET
    public List<PagoDTO> getPagos(){
        return new ArrayList<PagoDTO>();
    }
    
     @GET
    @Path("{id: \\d+}")
     public PagoDTO getPago(@PathParam("id") Long id) {
        return null;
    }
     
    @POST
    public PagoDTO createPago(PagoDTO pago){
        return pago;
    }
    
    @PUT
    @Path("{id: \\d+}")
     public PagoDTO updatePago(@PathParam("id") Long id){
        return null;
    }
     
     @DELETE
    @Path("{id:\\d+}")
    public void deletePago(@PathParam("id")Long id){
        
    }
}
