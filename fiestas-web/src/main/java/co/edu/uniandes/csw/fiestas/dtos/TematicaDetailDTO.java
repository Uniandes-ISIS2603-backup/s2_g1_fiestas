/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.List;

/**
 * TematicaDTO Objeto de transferencia de datos de la entidad de Tematica. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el servidor.
 * <p>
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Integer
 *      "descripcion": String
 *      [ 
 *          { 
 *           "id": Long
             "descripcion": String
             "tipo": String
            }
 *      ]
 *   }
 * </pre>
 * Por ejemplo una entidad de Servicio se representa asi:<br>
 * <pre>
 *
 *   {
 *      "id": 97971354
 *      "descripcion": Boda
 *      [
 *          { "id": 8974262
             "descripcion": Banquete
             "tipo": Comida
            }
 *      ]
 *   }
 *
 * </pre>
 *
 * @author af.losada
 */
public class TematicaDetailDTO extends TematicaDTO
{
    List<ServicioDTO> serviciosSugeridos;
    
    /*
    * El constructor 
    */
    public TematicaDetailDTO()
    {
        
    }
    /***
     * 
     * @return La lista de Servicios sugeridos para esta temática
     */
    public List<ServicioDTO> darServiciosSugeridos()
    {
        return serviciosSugeridos;
    }
    /***
     * 
     * @param lista Es la nueva lista de serivcios sugeridos
     */
    public void setServicios(List<ServicioDTO> lista)
    {
        serviciosSugeridos = lista;
    }
    
}
