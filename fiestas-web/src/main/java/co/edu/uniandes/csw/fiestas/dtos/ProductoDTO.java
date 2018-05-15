package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;

/**
 * ProductoDTO Objeto de transferencia de datos de la entidad de Producto. Los
 * DTO contienen las representaciones de los JSON que se transfieren entre el
 * cliente y el servidor.
 * <p>
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Integer,
 *      "nombre": String,
 *      "precio": Integer,
 *      "descripcion": String,
 *      "incluye": String
 *      "personal": Integer
 *   }
 * </pre> Por ejemplo una entidad de Servicio se representa asi:<br>
 * <pre>
 *
 *   {
 *      "id": 97971354,
 *      "nombre":"Servicio de comida para boda",
 *      "precio": 80,000,
 *      "descripcion": "Cena para 30 personas, con entrada, plato fuerte y postre",
 *      "incluye": "Incluye meseros, tambien opciones vegetarianas",
 *      "personal": 4
 *   }
 *
 * </pre>
 *
 * @author af.losada
 */
public class ProductoDTO {

    private long id;
    private String nombre;
    private int precio;
    private String descripcion;
    private String incluye;
    private int personal;
    private String imagen;

    /**
     * Constructor por defecto
     */
    public ProductoDTO() {
        //Solamente instancia el producto, pero no se le asignan valores porque esto hace parte del trabajo que tiene que hacer otra clase. 
    }

    
    /**
     * Constructor a partir de la entidad
     *
     * @param productoE La entidad del libro
     */
    public ProductoDTO(ProductoEntity productoE) {
        if (productoE != null) {
            this.id = productoE.getId();
            this.nombre=productoE.getNombre();
            this.descripcion = productoE.getDescripcion();
            this.incluye = productoE.getIncluye();
            this.personal = productoE.getPersonal();
            this.precio = productoE.getPrecio();
            this.imagen = productoE.getImagen();

        }
    }
    /*
    Getters
     */
    /**
     * @return El ID del producto
     */
    public long getID() {
        return id;
    }

    /**
     * @return El nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return El precio del producto
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * @return La descripción del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return Lo que incluye el producto
     */
    public String getIncluidos() {
        return incluye;
    }

    /**
     * @return La cantidad de personal del producto
     */
    public int getPersonal() {
        return personal;
    }

    /*
    Setters
     */
    /**
     * @param pID El nuevo ID
     */
    public void setID(long pID) {
        id = pID;
    }

    /**
     * @param pNombre El nuevo nombre
     */
    public void setNombre(long pNombre) {
        id = pNombre;
    }

    /**
     * @param pPrecio El nuevo precio
     */
    public void setPrecio(int pPrecio) {
        precio = pPrecio;
    }

    /**
     * @param pDesc La nueva descripcion
     */
    public void setDescripcion(String pDesc) {
        descripcion = pDesc;
    }

    /**
     * @param pInc El nuevo texto de lo incluido
     */
    public void setIncluidos(String pInc) {
        incluye = pInc;
    }

    /**
     * @param pPer El nuevo numero de personal
     */
    public void setPersonal(int pPer) {
        personal = pPer;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    /**
     * Método para transformar el DTO a una entidad.
     *
     * @return La entidad del libro asociado.
     */
    public ProductoEntity toEntity() {

        ProductoEntity productoE = new ProductoEntity();
        productoE.setId(this.id);
        productoE.setNombre(this.nombre);
        productoE.setDescripcion(this.descripcion);
        productoE.setIncluye(this.incluye);
        productoE.setPersonal(this.personal);
        productoE.setPrecio(this.precio);
        productoE.setImagen(this.imagen);

        return productoE;
    }

}
