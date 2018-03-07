/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;

/**
 * ProductoDTO Objeto de transferencia de datos de la entidad de Producto. Los DTO contienen las
 * representaciones de los JSON que se transfieren entre el cliente y el servidor.
 * <p>
 * Al serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *      "id": Integer,
 *      "precio": Integer,
 *      "descripcion": String,
 *      "incluye": String
 *      "personal": Integer
 *   }
 * </pre>
 * Por ejemplo una entidad de Servicio se representa asi:<br>
 * <pre>
 *
 *   {
 *      "id": 97971354,
 *      "precio": 80,000,
 *      "descripcion": Kit de comida para boda,
 *      "incluye": Incluye pastel y demás
 *      "personal": 4
 *   }
 *
 * </pre>
 *
 * @author af.losada
 */
public class ProductoDTO
{
    private long id;
    private int precio;
    private String descripcion;
    private String incluye;
    private int personal;
    
    /**
     * Constructor por defecto
     */
    public ProductoDTO()
    {
        //Solamente instancia el producto, pero no se le asignan valores porque esto hace parte del trabajo que tiene que hacer otra clase. 
    }
    
    /*
    Getters
    */
    
    /**
     * @return El ID del producto
     */
    public long getID()
    {
        return id;
    }
    
    /**
     * @return El precio del producto
     */
    public int getPrecio()
    {
        return precio;
    }
    
    /**
     * @return La descripción del producto
     */
    public String getDescripcion()
    {
        return descripcion;
    }
    
    /**
     * @return Lo que incluye el producto
     */
    public String getIncluidos()
    {
        return incluye;
    }
    
    /**
     * @return La cantidad de personal del producto
     */
    public int getPersonal()
    {
        return personal;
    }
    
    /*
    Setters
    */
   
    /**
     * @param pID El nuevo ID
     */
    public void setID(long pID)
    {
        id = pID;
    }
    
    /**
     * @param pPrecio El nuevo precio
     */
    public void setPrecio(int pPrecio)
    {
       precio = pPrecio;
    }
    
    /**
     * @param pDesc La nueva descripcion
     */
    public void setDescripcion(String pDesc)
    {
        descripcion = pDesc;
    }
    
    /**
     * @param pInc El nuevo texto de lo incluido
     */
    public void setIncluidos(String pInc)
    {
        incluye = pInc;
    }
    
    /**
     * @param pPer El nuevo numero de personal
     */
    public void setPersonal(int pPer)
    {
        personal = pPer;
    }
    
    
    /**
     * Constructor a partir de la entidad
     * @param productoE  La entidad del libro
     */
    public ProductoDTO(ProductoEntity productoE) {
        if (productoE != null) {
            this.id = productoE.getId();
            this.descripcion = productoE.getDescripcion();
            this.incluye = productoE.getIncluye();
            this.personal = productoE.getPersonal();
            this.precio = productoE.getPrecio();
            
        }
    }

    /**
     * Método para transformar el DTO a una entidad.
     * @return La entidad del libro asociado.
     */
    public ProductoEntity toEntity() {

        ProductoEntity productoE = new ProductoEntity();
        productoE.setId(this.id);
        productoE.setDescripcion(this.descripcion);
        productoE.setIncluye(this.incluye);
        productoE.setPersonal(this.personal);
        productoE.setPrecio(this.precio);
     
        return productoE;
    }

}
