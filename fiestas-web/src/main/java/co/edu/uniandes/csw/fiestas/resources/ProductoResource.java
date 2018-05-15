/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.resources;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import co.edu.uniandes.csw.fiestas.dtos.*;
import co.edu.uniandes.csw.fiestas.ejb.ProductoLogic;
import co.edu.uniandes.csw.fiestas.ejb.ServicioLogic;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * <pre>Clase que implementa el recurso "productos".
 * URL: /api/productos
 * </pre>
 * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
 * este recurso tiene la ruta "productos".</i>
 *
 * <h2>Anotaciones </h2>
 * <pre>
 * Path: indica la dirección después de "api" para acceder al recurso
 * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
 * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
 * </pre>
 * @author af.losada  
 * @version 1.0
 */
@Path("productos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ProductoResource 
{
    
    @Inject
    ProductoLogic productoLogic;
    
    @Inject
    ServicioLogic servicioLogic;
    
        /**
     * <h1>GET /productos/{id} : Obtener producto por id.</h1>
     * 
     * <pre>Busca el producto con el id asociado recibido en la URL y la devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el producto correspondiente al id.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un producto con el id dado.
     * </code>
     * </pre>
     * @param id Identificador del producto que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ProductoDetailDTO} - El product buscada
     */
    @GET
    @Produces("application/json")
    @Consumes("application/json")

    @Path("{id: \\d+}")
    public ProductoDetailDTO getProducto(@PathParam("id") Long id)
    {
      return new ProductoDetailDTO(productoLogic.getProducto(id));
    }
    
    /**
     * <h1>GET /api/productos : Obtener todas los productos.</h1>
     * 
     * <pre>Busca y devuelve todas los productos que existen en la aplicacion.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve todas los productos de la aplicacion.</code> 
     * </pre>
     * @return JSONArray {@link ProductoDetailDTO} - Los productos encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public List<ProductoDetailDTO> getProductos()
    {
        List<ProductoEntity> lista = productoLogic.getProductos();
        List<ProductoDetailDTO> nuevaList = new ArrayList<>();
        
        for (ProductoEntity productoEntity : lista) 
        {
            nuevaList.add(new ProductoDetailDTO(productoEntity));
        }
        return nuevaList;
    }
    
    /**
     * <h1>POST /api/productos : Crear una producto.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link ProductoDetailDTO}.
     * 
     * Crea uno nuevo productos con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo producto .
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el producto.
     * </code>
     * </pre>
     * @param producto {@link ProductoDetailDTO} - El producto que se desea guardar.
     * @return JSON {@link ProductoDetailDTO}  - El producto guardada con el atributo id autogenerado.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe El producto
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public ProductoDetailDTO createProducto(ProductoDetailDTO producto) throws BusinessLogicException{
        
        productoLogic.createProducto(producto.toEntity());        
        return producto;
    }
    
    /**
     * <h1>PUT /api/producto/{id} : Actualizar producto con el id dado.</h1>
     * <pre>Cuerpo de petición: JSON {@link ProductoDetailDTO}.
     * 
     * Actualiza el producto con el id recibido en la URL con la informacion que se recibe en el cuerpo de la petición.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Actualiza el producto con el id dado con la información enviada como parámetro. Retorna un objeto identico.</code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe el producto con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador de el producto que se desea actualizar.Este debe ser una cadena de dígitos.
     * @param producto {@link ProductoDetailDTO} El producto que se desea guardar.
     * @return JSON {@link ProductoDetailDTO} - El producto guardada.
     * @throws BusinessLogicException {@link co.edu.uniandes.csw.fiestas.mappers.BusinessLogicExceptionMapper} - Error de lógica que se genera al no poder actualizar el productod porque ya existe uno con ese nombre.
     */
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{id: \\d+}")
    public ProductoDetailDTO updateProducto(@PathParam("id") Long id, ProductoDetailDTO producto) throws BusinessLogicException{
         
        productoLogic.updateProducto(producto.toEntity());
        return producto;
    }
    
    /**
     * <h1>DELETE /api/productos/{id} : Borrar producto por id.</h1>
     * 
     * <pre>Borra el producto con el id asociado recibido en la URL.
     * 
     * Códigos de respuesta:<br>
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Elimina el producto correspondiente al id dado.</code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found. No existe un producto con el id dado.
     * </code>
     * </pre>
     * @param id Identificador del producto que se desea borrar. Este debe ser una cadena de dígitos.
     */
    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{id: \\d+}")
    public void deleteProducto(@PathParam("id") Long id)
    {
        productoLogic.deleteProducto(id);
    }
    
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{productoId: \\d+}/servicio/{servicioId: \\d+}")
    public ProductoEntity addServicio(@PathParam("servicioId") Long servicioId,@PathParam("productoId") Long productoId )
    {
        productoLogic.addServicio(productoId, servicioId);
        return productoLogic.getProducto(productoId);
    }
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    @Path("{productoId: \\d+}/proveedor/{proveedorId: \\d+}")
    public ProductoEntity addProveedor(@PathParam("proveedorId") Long proveedorId,@PathParam("productoId") Long productoId )
    {
        productoLogic.addProveedor(productoId, proveedorId);
        return productoLogic.getProducto(productoId);
    }
    
}
