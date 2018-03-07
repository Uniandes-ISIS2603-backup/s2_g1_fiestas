/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.TematicaEntity;
import java.util.ArrayList;
import java.util.List;

/**
* Clase que extiende de {@link TematicaDTO} para manejar los proveedores del negocio con sus relaciones. 
 * Para conocer el contenido de un proveedor vaya a la documentacion de {@link TematicaDTO}.
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
        //El constructor vacio solo se usa para instanciar la clase, sin los atributos inicializados, porque esto hace parte del otro tipo de método constructor.
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
    
     /**
     * Crea un objeto ProductoDetailDTO a partir de un objeto ProductoEntity
     * incluyendo los atributos de ProductoDTO.
     *
     * @param entity Entidad ProductoEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */ 
    public TematicaDetailDTO(TematicaEntity entity) {
        super(entity);
        
        List<ServicioEntity> list = entity.getServicios();
        List<ServicioDTO> list2 = new ArrayList<ServicioDTO>();
        for (ServicioEntity servicioEntity : list) 
        {
            list2.add(new ServicioDTO(servicioEntity));
        }
        
        setServicios(list2);
    }
    
    public TematicaEntity toEntity()
    {
        TematicaEntity entity = super.toEntity();
        List<ServicioDTO> list1 = darServiciosSugeridos();
        ArrayList<ServicioEntity> list2 = new ArrayList<ServicioEntity>();
        for (ServicioDTO servicioDTO : list1) 
        {
            list2.add(servicioDTO.toEntity());
        }
        entity.setServicios(list2);
        return entity;
    }
    
    
}
