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
 * Clase que extiende de {@link ServicioDTO} para manejar la transformacion
 * entre los objetos JSON y las Entidades de la base de datos. Para conocer el
 * contenido del servicio vaya a la documentacion de {@link ServicioDTO}
 *
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "descripcion": string,
 *      "tipo": string,
 *      "productos": [{"id": 1,
 *      "nombre": string,
 *      "precio": number,
 *      "descripcion": string,
 *      "incluye": string,
 *      "personal": Integer
 *      },
 *      ...
 *      ]
 *   }
 * </pre> Por ejemplo el atributo extra en ServicioDetail se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 524,
 *      "nombre": "orquesta la 104",
 *      "descripcion": "Orquesta profesional con amplio repertorio y la mejor trompetista de todos los tiempos",
 *      "tipo": "Entretenimiento",
 *      "productos": [{"id": 123,
 *      "nombre": "orquesta profesional",
 *      "precio": 500000,
 *      "descripcion": "orquesta profesional por 4 horas",
 *      "incluye": "Músicos profesionales con amplio repertorio de música tropical ",
 *      "personal": 15
 *      },
 *      {{"id": 124,
 *      "nombre": "Escenario orquesta profesional",
 *      "precio": 120000,
 *      "descripcion": "Tarima de 20x20",
 *      "incluye": "Montaje del escenario",
 *      "personal": 5
 *      }
 *      ]
 *   }
 *
 * </pre>
 *
 * @author ls.arias
 */
public class ServicioDetailDTO extends ServicioDTO {

    private List<ProductoDTO> productos;

    /**
     * Constructor por defecto
     */
    public ServicioDetailDTO() {
        //constructor vacio
    }

    /**
     * Constructor por defecto
     *
     * @param e Entidad Servicio para crear el DetailDTO
     */
    public ServicioDetailDTO(ServicioEntity e) {
        super(e);
        if (e != null) {
            productos = new ArrayList<>();
            for (ProductoEntity entityProducto : e.getProductos()) {
                productos.add(new ProductoDTO(entityProducto));
            }

        }
    }

    /**
     * @return La lista de productos del servicio
     */
    public List<ProductoDTO> getProductos() {
        return productos;
    }

    /**
     * @param productos La nueva lista de productos del servicio
     */

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    /**
     * Convertir DTO a Entity
     * @return Un Entity con los valores del DTO
     */
    @Override
    public ServicioEntity toEntity() {
        ServicioEntity entity = super.toEntity();
        if (productos != null) {
            List<ProductoEntity> listProductos = new ArrayList<>();
            for (ProductoDTO dto : getProductos()) {
                ProductoEntity ent = dto.toEntity();
                listProductos.add(ent);
            }

            entity.setProductos(listProductos);
        }
        return entity;
    }

}
