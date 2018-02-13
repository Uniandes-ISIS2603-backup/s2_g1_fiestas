/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import co.edu.uniandes.csw.fiestas.dtos.*;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.List;
import javax.ws.rs.DELETE;
import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

/**
 *
 * @author af.losada
 */
public class ProductoResource 
{
    
    public ProductoResource(){
        
    }
    
    @GET    
    public ProductoDetailDTO getProdcuto(@PathParam("id") Long id){
      return null;
    }
    
    @GET
    @Path("{id: \\d+}")
    public List<ProductoDetailDTO> getProducto(){
      return null;
    }
    
    @POST
    public ProductoDetailDTO createProducto(ProductoDetailDTO producto) throws BusinessLogicException{
        return producto;
    }
    
    @PUT
    @Path("{id: \\d+}")
    public ProductoDetailDTO updateProducto(@PathParam("id") Long id){
        return null;
    }
    
    @DELETE
    @Path("{id: \\d+}")
    public void deleteHorario(@PathParam("id") Long id){
    }
    
}
