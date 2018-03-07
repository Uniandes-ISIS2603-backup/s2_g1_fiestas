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
 * Clase que extiende de {@link TematicaDTO} para manejar los proveedores del
 * negocio con sus relaciones. Para conocer el contenido de un proveedor vaya a
 * la documentacion de {@link TematicaDTO}.
 * <p>
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Integer
 *      "descripcion": String
 *      [
 *          {
 *           "id": Long
 * "descripcion": String
 * "tipo": String
 * }
 *      ]
 *   }
 * </pre> Por ejemplo una entidad de Servicio se representa asi:<br>
 * <pre>
 *
 *   {
 *      "id": 97971354
 *      "descripcion": Boda
 *      [
 *          { "id": 8974262
 * "descripcion": Banquete
 * "tipo": Comida
 * }
 *      ]
 *   }
 *
 * </pre>
 *
 * @author af.losada
 */
public class TematicaDetailDTO extends TematicaDTO {
    
    List<ServicioDTO> serviciosSugeridos;

    /*
    * El constructor 
     */
    public TematicaDetailDTO() {
        //Constructor vacio
    }

    /**
     * Crea un objeto TematicaDetailDTO a partir de un objeto TematicaEntity
     * incluyendo los atributos de TematicaDTO.
     *
     * @param entity Entidad TematicaEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public TematicaDetailDTO(TematicaEntity entity) {
        super(entity);
        if (entity != null) {
            serviciosSugeridos = new ArrayList<>();
            for (ServicioEntity servicioEntity : entity.getServicios()) {
                serviciosSugeridos.add(new ServicioDTO(servicioEntity));
            }
        }
    }

    /**
     * Convierte un objeto TematicaDetailDTO a TematicaEntity incluyendo los
     * atributos de TematicaDTO.
     *
     * @return Nueva objeto TematicaEntity.
     *
     */
    @Override
    public TematicaEntity toEntity() {
        TematicaEntity entity = super.toEntity();
        if (serviciosSugeridos != null) {
            List<ServicioEntity> serviciosEntity = new ArrayList<>();
            for (ServicioDTO servicio : serviciosSugeridos) {
                serviciosEntity.add(servicio.toEntity());
            }
            entity.setServicios(serviciosEntity);
        }
        return entity;
    }

    /**
     * *
     *
     * @return La lista de Servicios sugeridos para esta temática
     */
    public List<ServicioDTO> darServiciosSugeridos() {
        return serviciosSugeridos;
    }

    /**
     * 
     * @param lista Es la nueva lista de serivcios sugeridos
     */
    public void setServicios(List<ServicioDTO> lista) {
        serviciosSugeridos = lista;
    }
    
}
