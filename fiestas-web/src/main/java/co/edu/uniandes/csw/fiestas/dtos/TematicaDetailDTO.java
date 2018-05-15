package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
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
 * </pre> Por ejemplo una entidad de Producto se representa asi:<br>
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
    
    List<ProductoDTO> productosSugeridos;

    /*
    * El constructor 
     */
    public TematicaDetailDTO() {
        //El constructor vacio solo se usa para instanciar la clase, sin los atributos inicializados, porque esto hace parte del otro tipo de método constructor.
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
        
        List<ProductoEntity> list = entity.getProductos();
        List<ProductoDTO> list2 = new ArrayList<>();
        for (ProductoEntity productoEntity : list) 
        {
            list2.add(new ProductoDTO(productoEntity));
        }
        setProductos(list2);
    }

    /**
     * Convierte un objeto TematicaDetailDTO a TematicaEntity incluyendo los
     * atributos de TematicaDTO.
     *
     * @return Nueva objeto TematicaEntity.
     *
     */
    @Override
    public TematicaEntity toEntity()
    {
        TematicaEntity entity = super.toEntity();
        List<ProductoDTO> list1 = getProductos();
        ArrayList<ProductoEntity> list2 = new ArrayList<>();
        if (list1 != null)
        {
        for (ProductoDTO productoDTO : list1) 
        {
            list2.add(productoDTO.toEntity());
        }
        }
        entity.setProductos(list2);
        return entity;
    }

    /**
     * *
     *
     * @return La lista de Productos sugeridos para esta temática
     */
    public List<ProductoDTO> getProductos() {
        return productosSugeridos;
    }

    /**
     * 
     * @param lista Es la nueva lista de serivcios sugeridos
     */
    public void setProductos(List<ProductoDTO> lista) {
        productosSugeridos = lista;
    }     
    
}
