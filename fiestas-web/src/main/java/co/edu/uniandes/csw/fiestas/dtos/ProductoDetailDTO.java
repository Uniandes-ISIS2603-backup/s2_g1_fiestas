/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de {@link ProductoDTO} para manejar los proveedores del negocio con sus relaciones. 
 * Para conocer el contenido de un proveedor vaya a la documentacion de {@link ProductoDTO}.
 * <p>
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Integer,
 *      "precio": Integer,
 *      "descripcion": String,
 *      "incluye": String
 *      "personal": Integer
 *      "contratos":
 *      {
 *          "id" : Long
 *      }
 *      "servicio":
 *      {
 *          "id": Long
             "descripcion": String
             "tipo": String
 *      }
 *   }
 * </pre>
 * Por ejemplo una entidad de Servicio se representa asi:<br>
 * <pre>
 *
 *   {
 *      "id": 97971354,
 *      "precio": 80,000,
 *      "descripcion": "Paquete de comida para boda"
 *      "incluye": "Incluye pastel y demás"
 *      "personal": 4
 *      "contratos":
 *      {
 *          "id" :99979846
 *      }
 *      "servicio":
 *      {
 *          "id": 4567672
             "descripcion": "Pasteles y demás"
             "tipo": "Culinaria"
 *      }
 *   }
 *
 * </pre>
 *
 * @author af.losada
 */
public class ProductoDetailDTO extends ProductoDTO
{

private ServicioDTO servicio;

/**
* Constructor por defecto
*/
public ProductoDetailDTO()
{
    //El constructor vacio solo se usa para instanciar la clase, sin los atributos inicializados, porque esto hace parte del otro tipo de método constructor. 
}

/*
Getters
*/


/**
 * 
 * @return el Servicio en el cual está el producto 
 */
public ServicioDTO darServicio()
{
return servicio;
}

/*
Setters
*/

/*
    Cambia el servicio por el ingresado
*/
void setServicio(ServicioDTO pServ)
{
    servicio = pServ;
}
    



    /**
     * Crea un objeto ProductoDetailDTO a partir de un objeto ProductoEntity
     * incluyendo los atributos de ProductoDTO.
     *
     * @param entity Entidad ProductoEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */ 
    public ProductoDetailDTO(ProductoEntity entity) {
        super(entity);
        if(entity.getServicio() != null)
        {
        servicio = (new ServicioDTO(entity.getServicio()));
        }
        else
        {
        servicio = null;
        }
    }

    /**
     * Convierte un objeto ProductoDetailDTO a ProductoEntity incluyendo los
     * atributos de ProductoDTO.
     *
     * @return Nueva objeto ProductoEntity.
     *
     */
    @Override 
    public ProductoEntity toEntity()
    {
        ProductoEntity producto = super.toEntity();
        if(servicio != null)
        producto.setServicio(servicio.toEntity());
        else
        producto.setServicio(new ServicioEntity());
        return producto;
    }
    
    
    
    
    
}


