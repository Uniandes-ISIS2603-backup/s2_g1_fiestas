
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ContratoEntity;
import co.edu.uniandes.csw.fiestas.entities.HorarioEntity;
import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.BonoEntity;
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
 *      "bonos":[{"id": 12,
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
    private List<ProductoDTO> productos = new ArrayList<>();
    private List<BonoDTO> bonos= new ArrayList<>();
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
    public List<ProductoDTO> getProductos()
    {
        return productos;
    }
    
    /**
     * @param productos El nuevo servicio del proveedor..
     */
    public void setProductos(List<ProductoDTO> productos)
    {
        this.productos = productos;
    }

    /**
     * @return the bonos
     */
    public List<BonoDTO> getBonos() {
        return bonos;
    }

    /**
     * @param bonos the bonos to set
     */
    public void setBonos(List<BonoDTO> bonos) {
        this.bonos = bonos;
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
            for(ContratoEntity ent : entity.getContratos())
            {
                ContratoDTO dto = new ContratoDTO(ent);
                contratos.add(dto);
            }
            
            for(ProductoEntity ent : entity.getProductos())
            {
                ProductoDTO dto = new ProductoDTO(ent);
                productos.add(dto);
            }
            
            for(BonoEntity ent : entity.getBonos())
            {
                BonoDTO dto = new BonoDTO(ent);
                bonos.add(dto);
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
        List<BonoEntity> listBonos = new ArrayList<>();
        List<ProductoEntity> listProductos = new ArrayList<>();
        if(!contratos.isEmpty())
        for(ContratoDTO dto : getContratos())
        {
            ContratoEntity ent = dto.toEntity();
            listContratos.add(ent);
        }
        if(!bonos.isEmpty())
        for(BonoDTO dto : getBonos())
        {
            BonoEntity ent = dto.toEntity();
            listBonos.add(ent);
        }
        if(!productos.isEmpty())
        for(ProductoDTO dto : getProductos())
        {
            ProductoEntity ent = dto.toEntity();
            listProductos.add(ent);
        }
        entity.setContratos(listContratos);
        entity.setProductos(listProductos);
        entity.setBonos(listBonos);
        return entity;
    }
}
