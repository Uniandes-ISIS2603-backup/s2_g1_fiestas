
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
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
 *      "servicios":[{"id": number,
 *      "descripcion": String,
 *      "Tipo": String
 *      }, 
 *      ...
 *      ],
 *      "horarios":[{"id": 12,
 *      "descripcion": "Entrega de invitaciones",
 *      "Tipo": "Entrega"
 *      }, 
 *      ...
 *      ],
 *      "valoraciones":[{"id": 12,
 *      "descripcion": "Entrega de invitaciones",
 *      "Tipo": "Entrega"
 *      }, 
 *      ...
 *      ],
 *      "conratos":[{"id": 12,
 *      "descripcion": "Entrega de invitaciones",
 *      "Tipo": "Entrega"
 *      }, 
 *      ...
 *      ]
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
    private List<ServicioDTO> servicios;
    private List<ValoracionDTO> valoraciones;
    private List<ContratoDTO> contratos;    
    
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
    public List<ServicioDTO> getServicios()
    {
        return servicios;
    }
    
    /**
     * @param servicios El nuevo servicio del proveedor..
     */
    public void setServicios(List<ServicioDTO> servicios)
    {
        this.servicios = servicios;
    }

    /**
     * @return the valoraciones
     */
    public List<ValoracionDTO> getValoraciones() {
        return valoraciones;
    }

    /**
     * @param valoraciones the valoraciones to set
     */
    public void setValoraciones(List<ValoracionDTO> valoraciones) {
        this.valoraciones = valoraciones;
    }

    /**
     * @return the contratos
     */
    public List<ContratoDTO> getContratos() {
        return contratos;
    }

    /**
     * @param contratos the contratos to set
     */
    public void setContratos(List<ContratoDTO> contratos) {
        this.contratos = contratos;
    }
    
    public ProveedorDetailDTO(ProveedorEntity entity)
    {
        if (entity != null)
        {
            super(entity); 
            contratos = new ArrayList<ContratoDTO>();
            for(ContratoEntity ent : entity.getContratos())
            {
                ContratoDTO dto = new ContratoDTO(ent);
                contratos.add(dto);
            }
            for(HorarioEntity ent : entity.getHorarios())
            {
                HorarioDTO dto = new HorarioDTO(ent);
                horarios.add(dto);
            }
            for(ServicioEntity ent : entity.getServicios())
            {
                ServicioDTO dto = new ServicioDTO(ent);
                servicios.add(dto);
            }
            for(ValoracionEntity ent : entity.getValoraciones())
            {
                ValoracionDTO dto = new ValoracionDTO(ent);
                valoraciones.add(dto);
            }
        }
    }
    
    public ProveedorEntity toEntity()
    {
        ProveedorEntity entity = super.toEntity();
        for(ContratoEntity dto : getContratos())
        {
            ContratoEntity ent = new ContratoDTO();
            contratos.add(dto);
        }
        for(HorarioEntity ent : entity.getHorarios())
        {
            HorarioDTO dto = new HorarioDTO();
            horarios.add(dto);
        }
        for(ServicioEntity ent : entity.getServicios())
        {
            ServicioDTO dto = new ServicioDTO();
            servicios.add(dto);
        }
        for(ValoracionEntity ent : entity.getValoraciones())
        {
            ValoracionDTO dto = new ValoracionDTO();
            valoraciones.add(dto);
        }
        entity.setContratos();
    }
}
