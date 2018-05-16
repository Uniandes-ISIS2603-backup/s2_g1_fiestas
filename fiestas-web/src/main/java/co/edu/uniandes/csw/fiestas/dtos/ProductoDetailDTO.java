/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
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

private ProveedorDTO proveedor;

private List<ValoracionDTO> valoracion;

/**
* Constructor por defecto
*/
public ProductoDetailDTO()
{
    //El constructor vacio solo se usa para instanciar la clase, sin los atributos inicializados, porque esto hace parte del otro tipo de método constructor. 
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
        servicio = new ServicioDTO(entity.getServicio());
        }
        else
        {
        servicio = null;
        }
        if(entity.getProveedor() != null)
        {
        proveedor = new ProveedorDTO(entity.getProveedor());
        }
        else
        {
        proveedor = null;
        }
    }


/*
Getters
*/
    
    
    /**
     * @return the proveedor
     */
    public ProveedorDTO getProveedor() {
        return proveedor;
    }



/**
 * 
 * @return el Servicio en el cual está el producto 
 */
public ServicioDTO getServicio()
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
     * @param proveedor the proveedor to set
     */
    public void setProveedor(ProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the valoraciones
     */
    public List<ValoracionDTO> getValoracion() {
        return valoracion;
    }

    /**
     * @param valoraciones the valoraciones to set
     */
    public void setValoracion(List<ValoracionDTO> valoracion) {
        this.valoracion = valoracion;
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
        if(this.getProveedor() != null)
        {
        producto.setProveedor(this.getProveedor().toEntity());
        }
        if(this.getValoracion() != null)
        {
            List<ValoracionDTO> actual = this.getValoracion();
            List<ValoracionEntity> rta = new ArrayList<>();
            for(int i = 0; i < actual.size(); i++)
            {
                rta.add(actual.get(i).toEntity());
            }
            producto.setValoraciones(rta);
        }
        if(this.getServicio() != null)
        {
            producto.setServicio(this.getServicio().toEntity());
        }
        return producto;
    }
    
    
    
    
    
}


