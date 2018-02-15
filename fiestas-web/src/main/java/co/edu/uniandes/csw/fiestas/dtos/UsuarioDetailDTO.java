
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;
import java.util.List;

/**
* Clase que extiende de {@link UsuarioDTO} para manejar los usuario del negocio con sus relaciones. 
 * Para conocer el contenido de un usuario vaya a la documentacion de {@link UsuarioDTO}.
 * Sin embargo, la clase en detalle (UsuarioDetail) presenta un atributo en JSON extra que comprende
 * la lista de blogs en los que ha participado el usuario:
 * 
 * <pre>
 *   {
 *      ...
 *      "blogs": [{"id": number,
 *      "autor": String,
 *      "cuerpo": String,
 *      "titulo": String,
 *      "likes": number
 *      },
 *      ...
 *      ]
 *   }
 * </pre>
 * Por ejemplo el atributo extra en UsuarioDetail se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *       ...
 *      "blogs": [{"id": 1,
 *      "autor": "Laura",
 *      "cuerpo": "Que chevere la comida",
 *      "titulo": "Comida",
 *      "likes": 0
 *      },
 *      {
 * "id": 2,
 *      "autor": "María",
 *      "cuerpo": "No me gustó el tema",
 *      "titulo": "Tema malo",
 *      "likes": 4
 *      }
 *      ]
 *   }
 *
 * </pre>
 * @author nm.hernandez10
 */
public class UsuarioDetailDTO extends UsuarioDTO
{
    private List<BlogDTO> blogs;
    
    /**
     * Constructor por defecto
     */
    public UsuarioDetailDTO()
    {
        
    }
    
    /**
     * @return La lista de blogs del usuario.
     */
    public List<BlogDTO> getBlogs()
    {
        return blogs;
    }
    
    /**
     * @param La nueva lista de blogs del usuario.
     */
    public void setBlogs(List<BlogDTO> blogs)
    {
        this.blogs = blogs;
    }
}
