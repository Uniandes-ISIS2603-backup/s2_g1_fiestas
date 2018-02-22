/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.List;
/**
 *Clase que extiende de {@link HorarioDTO} para manejar la transformación entre los objetos JSON y las entidades de la base de datos.
 *Para conocer el contenido del horario vaya a la documentación de {@link HorarioDTO}
 *
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "fecha": Date,
 *      "horaInicio": Date,
 *      "horaFin": Date,
 * 
 *      "proveedor": 
 *      {
 *      "id": number,
 *      "nombre": string,
 *      "documento": string
 *      "telefono": number
 *      "correo": string
 *      "direccion": string
 *      "login": string
 *      }
 *      "agenda":
 *      {
 *      "id": number,
 *      "fecha": string,
 *      "descripcion": string,
 *      "celebrado": string,
 *      "lugar": string,
 *      "inventados": number
 *      }
 *   }
 * </pre> Por ejemplo un cliente se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 112344,
 *      "fecha: 01/01/12,
 *      "horaInicio": 14:00
 *      "horaFin": 20:00
 * "proveedor": 
 *      {
 *      "id": 1244,
 *      "nombre": Rimax,
 *      "documento": 433443
 *      "telefono": 34335
 *      "correo": frerfe@hotmail.com
 *      "direccion": cll.234#1212-43
 *      "login": frerfe
 *      }
 *      "agenda":
 *      {
 *      "id": 3425,
 *      "fecha": 01/01/12,
 *      "descripcion": "fiesta infantil de carlitos",
 *      "celebrado": carlitos,
 *      "lugar": divercity santa fé,
 *      "inventados": 35
 *      }
 *   }
 *
 * </pre>
 *
 * @author df.nino10
 */

public class HorarioDetailDTO extends HorarioDTO {
    
    private List<EventoDTO> eventos;
    
    /**
     * Constructor por defecto
     */
    public HorarioDetailDTO(){
    }

    /**
     * @return lista de eventos
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

     /**
     * @param eventos La lista de eventos.
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }
    
    
    
}
