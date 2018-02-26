/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.List;


/**
 * Clase que extiende de {@link ValoracionDTO}
 * @author ls.arias
 * 
 * <pre>
 *   {
 *      "id": number,
 *      "comentario": string,
 *      "calificacion": number,
 *      "servicios": [{"id": 1,
 *      "descripcion": string,
 *      "tipo": string,
 *      },
 *      ...
 *      ]
 *   }
 * </pre>
 * Por ejemplo el atributo extra en ServicioDetail se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 524,
 *      "comentario": "Muy buen servicio",
 *      "calificacion": "4.8",
 *      "servicios": [{"id": 123,
 *      "descripcion": "Mago frances que sabe hacer figuras con globos",
 *      "tipo": "entretenimiento",
 *      },
 *      {"id": 124,
 *      "descripcion": "carrito de perros calientes",
 *      "tipo": "comida",
 *      ]
 *   }
 *
 * </pre>
 *
 */
public class ValoracionDetailDTO extends ValoracionDTO {
    
     /**
     * Constructor por defecto
     */
    public ValoracionDetailDTO() {
    }

    private List<ServicioDTO> servicios;
    
    /**
    * @return La lista de servicios.
    */
    public List<ServicioDTO> getServicios()
    {
        return servicios;
    }
    
    /**
    * @param servicios nuevos servicios 
    */
    public void setServicios(List<ServicioDTO> servicios)
    {
        this.servicios = servicios;
    }

}
