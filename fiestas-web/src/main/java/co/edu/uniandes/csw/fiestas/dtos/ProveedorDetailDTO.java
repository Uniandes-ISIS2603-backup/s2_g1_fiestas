
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
 *      "servicios":[{"id": number,
 *      "descripcion": String,
 *      "Tipo": String
 *      }, 
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
 *      
 *   }
 *
 * </pre>
 * 
 * @author nm.hernandez10
 */
public class ProveedorDetailDTO extends ProveedorDTO
{
    private List<ServicioDTO> servicios = new ArrayList<>();
    private List<ValoracionDTO> valoraciones= new ArrayList<>();
    private List<ContratoDTO> contratos= new ArrayList<>();    
    
    /**
     * Constructor por defecto
     */
    public ProveedorDetailDTO()
    {
        
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
    
    /**
     * Convierte un ProveedorEntity en un ProveedorDetailDTO
     * @param entity ProveedorEntity instancia
     */ 
    public ProveedorDetailDTO(ProveedorEntity entity)
    {
        super(entity); 
        if (entity != null)
        {            
            contratos = new ArrayList<>();
            for(ContratoEntity ent : entity.getContratos())
            {
                ContratoDTO dto = new ContratoDTO(ent);
                contratos.add(dto);
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
    
    /**
     * Método que transforma la clase ProveedorDetailDTO a ProveedorEntity.
     * @return ProveedorEntity instancia.
     */
    @Override
    public ProveedorEntity toEntity()
    {
        ProveedorEntity entity = super.toEntity();
        List<ContratoEntity> listContratos = new ArrayList<>();
        List<ValoracionEntity> listValoraciones = new ArrayList<>();
        List<ServicioEntity> listServicios = new ArrayList<>();
        if(!contratos.isEmpty())
        for(ContratoDTO dto : getContratos())
        {
            ContratoEntity ent = dto.toEntity();
            listContratos.add(ent);
        }
        if(!valoraciones.isEmpty())
        for(ValoracionDTO dto : getValoraciones())
        {
            ValoracionEntity ent = dto.toEntity();
            listValoraciones.add(ent);
        }
        if(!servicios.isEmpty())
        for(ServicioDTO dto : getServicios())
        {
            ServicioEntity ent = dto.toEntity();
            listServicios.add(ent);
        }
        entity.setContratos(listContratos);
        entity.setServicios(listServicios);
        entity.setValoraciones(listValoraciones);
        return entity;
    }
}
