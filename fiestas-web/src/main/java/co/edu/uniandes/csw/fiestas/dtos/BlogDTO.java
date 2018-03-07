package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;

/**
 * BlogDTO Objeto de transferencia de datos de Blog.
 *
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": number,
 *      "titulo": string,
 *      "cuerpo": string,
 *      "likes": number
 *   }
 * </pre> Por ejemplo un blog se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *      "id": 1520,
 *      "titulo": "Cumpleaños de Sarita",
 *      "cuerpo": "Este cumpleaños fue increible, los niños pasaron súper, tuvimos payasos y carro de perros calientes, recomendados ambos",
 *      "likes": 45
 *   }
 * </pre>
 *
 * @author mc.gonzalez15
 */
public class BlogDTO {

    /**
     * Identificador del Blog
     */
    private long id;

    /**
     * Titulo del Blog
     */
    private String titulo;

    /**
     * Cuerpo del Blog
     */
    private String cuerpo;

    /**
     * Número de likes
     */
    private int likes;

    /**
     * Constructor vacío BlogDTO
     */
    public BlogDTO() {
        //Constructor vacio
    }

    /**
     * Crea un objeto BlogDTO a partir de un objeto BlogEntity.
     *
     * @param entity Entidad BlogEntity desde la cual se va a crear el nuevo
     * objeto.
     *
     */
    public BlogDTO(BlogEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.titulo = entity.getTitulo();
            this.cuerpo = entity.getCuerpo();
            this.likes = entity.getLikes();

        }
    }

    /**
     * Retorna el identificador
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Asigna el identificador
     *
     * @param id a asignar
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el titulo
     *
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Asigna el titulo
     *
     * @param titulo a asignar
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Retorna el cuerpo
     *
     * @return cuerpo
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * Asigna el cuerpo
     *
     * @param cuerpo a asignar
     */
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    /**
     * Retorna el numero de likes
     *
     * @return likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Asigna el numero de likes
     *
     * @param likes a asignar
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Convierte un objeto BlogDTO a BlogEntity.
     *
     * @return Nueva objeto BlogEntity.
     *
     */
    public BlogEntity toEntity() {
        BlogEntity entity = new BlogEntity();
        entity.setId(this.getId());
        entity.setTitulo(this.getTitulo());
        entity.setCuerpo(this.getCuerpo());
        entity.setLikes(this.getLikes());
        return entity;
    }

}
