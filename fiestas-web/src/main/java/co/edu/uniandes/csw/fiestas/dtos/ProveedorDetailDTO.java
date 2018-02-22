/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase que extiende de {@link ProveedorDTO} para manejar los proveedores del negocio con sus relaciones. 
 * Para conocer el contenido de un proveedor vaya a la documentacion de {@link ProveedorDTO}.
 * Sin embargo, la clase en detalle (ProveedorDetail) presenta un atributo en JSON extra que comprende
 * la lista de horarios a los que tiene que asistir para los distintos eventos:
 * 
 * <pre>
 *   {
 *      "id": number,
 *      "nombre": string,
 *      "documento": string,
 *      "telefono": number,
 *      "correo": string,
 *      "direccion": string,
 *      "login": string,
 *      "horarios": [{"id": number,
 *      "fecha": Date,
 *      "horaInicio": Date,
 *      "horaFin": Date
 *      },
 *      ...
 *      ],
 *      "servicio":[{"id": 12,
 *      "descripcion": "Entrega de invitaciones",
 *      "Tipo": "Entrega"
 *      }]
 *   }
 * </pre>
 * Por ejemplo el atributo extra en ProveedorDetail se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 7,
 *      "nombre": "Nicolás Hernández",
 *      "documento": "101010101",
 *      "telefono": 3111234567,
 *      "correo": "nicolashernandez@hotmail.com",
 *      "direccion": "Calle 97 # 6-43",
 *      "login": "nm.hernandez10",
 *      "horarios": [{"id": 2,
 *      "fecha": 14/02/2018,
 *      "horaInicio": 18:00,
 *      "horaFin": 20:00
 *      },
 *      {"id": 1,
 *      "fecha": 17/02/2018,
 *      "horaInicio": 08:00,
 *      "horaFin": 12:00
 *      }
 *      ]
 *   }
 *
 * </pre>
 * 
 * @author nm.hernandez10
 */
public class ProveedorDetailDTO extends ProveedorDTO
{
    private List<HorarioDTO> horarios;
    private ServicioDTO servicio;
    
    /**
     * Constructor por defecto
     */
    public ProveedorDetailDTO()
    {
        
    }
    
    /**
     * @return La lista de horarios del proveedor.
     */
    public List<HorarioDTO> getHorarios()
    {
        return horarios;
    }
    
    /**
     * @param horarios La nueva lista de horarios.
     */
    public void setHorarios(List<HorarioDTO> horarios)
    {
        this.horarios = horarios;
    }
    /**
     * @return El sevicio del proveedor.
     */
    public ServicioDTO getServicio()
    {
        return servicio;
    }
    
    /**
     * @param servicio El nuevo servicio del proveedor..
     */
    public void setHorarios(ServicioDTO servicio)
    {
        this.servicio = servicio;
    }
}
