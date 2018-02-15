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
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import static javax.ws.rs.HttpMethod.DELETE;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

 /**
+ * <pre>Clase que implementa el recurso "producto".
+ * URL: /api/productos
+ * </pre>
+ * <i>Note que la aplicación (definida en {@link RestConfig}) define la ruta "/api" y
+ * este recurso tiene la ruta "producto".</i>
  *
+ * <h2>Anotaciones </h2>
+ * <pre>
+ * Path: indica la dirección después de "api" para acceder al recurso
+ * Produces/Consumes: indica que los servicios definidos en este recurso reciben y devuelven objetos en formato JSON
+ * RequestScoped: Inicia una transacción desde el llamado de cada método (servicio). 
+ * </pre>
  * @author af.losada
  */
 
 @Path("productos")
 @Produces("application/json") 
 @RequestScoped
public class ProductoResource 
{
    
    public ProductoResource(){
        
    }
    
    +/**
+     * <h1>GET /eventos/{id} : Obtener producto por id.</h1>
+     * 
+     * <pre>Busca el producto con el id asociado recibido en la URL y lo devuelve.
+     * 
+     * Codigos de respuesta:
+     * <code style="color: mediumseagreen; background-color: #eaffe0;">
+     * 200 OK Devuelve el producto correspondiente al id.
+     * </code> 
+     * <code style="color: #c7254e; background-color: #f9f2f4;">
+     * 404 Not Found No existe un producto con el id dado.
+     * </code> 
+     * </pre>
+     * @param id Identificador del producto que se esta buscando. Este debe ser una cadena de dígitos.
+     * @return JSON {@link ProductoDetailDTO} - El evento buscado
+     */
    
    @GET    
    public ProductoDetailDTO getProdcuto(@PathParam("id") Long id){
      return null;
    }
    
     /**
+      /**
+     * <h1>GET /eventos : Obtener todos los productos.</h1>
+     * 
+     * <pre>Busca y devuelve todos los productos que existen en la aplicacion.
      * 
-     * @return 
+     * Codigos de respuesta:
+     * <code style="color: mediumseagreen; background-color: #eaffe0;">
+     * 200 OK Devuelve todas los producto de la aplicacion.</code> 
+     * </pre>
+     * @return JSONArray {@link ProductoDetailDTO} - Los producto encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
      */
    
    @GET
    @Path("{id: \\d+}")
    public List<ProductoDetailDTO> getProducto(){
      return null;
    }
    
   /**
     * <h1>POST /eventos : Crear un producto.</h1>
     * 
     * <pre>Cuerpo de petición: JSON {@link ProductoDetailDTO}.
     * 
     * Crea un nuevo producto con la informacion que se recibe en el cuerpo 
     * de la petición y se regresa un objeto identico con un id auto-generado 
     * por la base de datos.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Creó el nuevo producto.
     * </code>
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 412 Precodition Failed: Ya existe el producto.
     * </code>
     * </pre>
     * @param evento {@link ProductoDetailDT} - La ciudad que se desea guardar.
     * @return JSON {@link ProductoDetailDTO}  - El producto guardado con el atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} - Error de lógica que se genera cuando ya existe la evento.
     */
    
    @POST
    public ProductoDetailDTO createProducto(ProductoDetailDTO producto) throws BusinessLogicException{
        return producto;
    }
   
    /**
     * <h1>PUT /eventos/{id} : Actualizar producto por id.</h1>
     * 
     * <pre>Busca el producto con el id asociado recibido en la URL, actualiza os paramteros
     * y lo devuelve.
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK Devuelve el producto correspondiente al id, despues de actualizado.
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un producto con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del producto que se esta buscando. Este debe ser una cadena de dígitos.
     * @return JSON {@link ProductoDetailDTO} - El evento buscado y actuaizado
     */
    
    @PUT 
    @Path("{id: \\d+}")
    public ProductoDetailDTO updateProducto(@PathParam("id") Long id){
        return null;
    }
    
   /**
     * <h1>DELETE /producto/{id} : Elimina un producto por id.</h1>
     * 
     * <pre>Busca el producto con el id asociado recibido en la URL y lo elimina
     * 
     * Codigos de respuesta:
     * <code style="color: mediumseagreen; background-color: #eaffe0;">
     * 200 OK El producto fue eliminado exitosamente
     * </code> 
     * <code style="color: #c7254e; background-color: #f9f2f4;">
     * 404 Not Found No existe un producto con el id dado.
     * </code> 
     * </pre>
     * @param id Identificador del producto que se esta buscando. Este debe ser una cadena de dígitos.
     */
    
    @DELETE 
    @Path("{id: \\d+}")
    public void deleteProducto(@PathParam("id") Long id){
    }
    
}
