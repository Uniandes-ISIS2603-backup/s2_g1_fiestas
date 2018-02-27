/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

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
 *      "servicios":
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
 *      "servicios":
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

private ContratoDTO contrato;
private ServicioDTO servicio;

/**
* Constructor por defecto
*/
public ProductoDetailDTO()
{
    
}

/*
Getters
*/

/**
 * 
 * @return el Contrato en el cual está el producto 
 */
private ContratoDTO darContrato()
{
 return contrato;
}

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
    Cambia el contrato por el ingresado
*/
void setContrato(ContratoDTO pCont)
{
    contrato = pCont;
}

/*
    Cambia el servicio por el ingresado
*/
void setServicio(ServicioDTO pServ)
{
    servicio = pServ;
}
    
}


