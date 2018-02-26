package co.edu.uniandes.csw.fiestas.dtos;

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
 * </pre>
 * Por ejemplo un blog se representa asi:<br>
 * 
 * <pre>
 * 
 *   {
 *      "id": 1520,
 *      "titulo": "Cumpleaños de Sarita",
 *      "cuerpo": "Este cumpleaños fue increible, los niños pasaron súper, tuvimos payasos y carro de perros calientes, recomendados ambos",
 *      "likes": 45
 *   }
 *
 * @author mc.gonzalez15
 */
class BlogDTO {
    

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
     * Constructor
     */ 
    public BlogDTO()
    {
        
    }
     
    /**
     * Retorna el identificador
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Asigna el identificador
     * @param id 
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retorna el titulo
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Asigna el titulo
     * @param titulo 
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Retorna el cuerpo 
     * @return cuerpo
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * Asigna el cuerpo
     * @param cuerpo 
     */
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    /**
     * Retorna el numero de likes
     * @return likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Asigna el numero de likes
     * @param likes 
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }
   
     
}
