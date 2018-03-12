
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
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
 *      "id": number,
 *      "nombre": string,
 *      "documento": string,
 *      "telefono": number,
 *      "correo": string,
 *      "direccion": string,
 *      "login": string
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
 *      "id": 7,
 *      "nombre": "Nicolás Hernández",
 *      "documento": "101010101",
 *      "telefono": 3111234567,
 *      "correo": "nicolashernandez@hotmail.com",
 *      "direccion": "Calle 97 # 6-43",
 *      "login": "nm.hernandez10",
 *      "blogs": [{"id": 1,
 *      "autor": "Laura",
 *      "cuerpo": "Que chevere la comida",
 *      "titulo": "Comida",
 *      "likes": 0
 *      },
 *      {
 *      "id": 2,
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
    private List<BlogDTO> blogs= new ArrayList<>();
    
    /**
     * Constructor por defecto
     */
    public UsuarioDetailDTO()
    {
        
    }
    /**
     * Constructor que transforma una instancia UsuarioEntity a UsuarioDetailDTO.
     * @param entity UsuarioEntity que dará los atributos necesarios al DTO.
     */
    public UsuarioDetailDTO(UsuarioEntity entity) 
    {
        super();
        /**for(BlogEntity u : entity.getBlogs())
        {
            BlogDTO b= new BlogDTO(u);
            blogs.add(b);
        }
        */
    }

    
    /**
     * @return La lista de blogs del usuario.
     */
    public List<BlogDTO> getBlogs()
    {
        return blogs;
    }
    
    /**
     * @param blogs La nueva lista de blogs del usuario.
     */
    public void setBlogs(List<BlogDTO> blogs)
    {
        this.blogs = blogs;
    }
}
