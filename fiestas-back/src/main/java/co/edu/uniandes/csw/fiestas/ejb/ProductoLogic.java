
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.ProductoEntity;
import co.edu.uniandes.csw.fiestas.entities.ProveedorEntity;
import co.edu.uniandes.csw.fiestas.entities.ServicioEntity;
import co.edu.uniandes.csw.fiestas.entities.ValoracionEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ProductoPersistence;
import co.edu.uniandes.csw.fiestas.persistence.ProveedorPersistence;
import co.edu.uniandes.csw.fiestas.persistence.ServicioPersistence;
import co.edu.uniandes.csw.fiestas.persistence.ValoracionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *Clase que implementa la conexion con la persistencia para la entidad de
 * Producto.
 *
 * @author af.losada
 */
@Stateless
public class ProductoLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ProductoLogic.class.getName());
    
    @Inject
    private ProductoPersistence persistence;
    @Inject
    private ServicioLogic perSer;
    private ValoracionLogic perValoracion;
    
    /**
     * Obtiene la lista de los registros de Producto.
     *
     * @return Colección de objetos de ProductoEntity.
     */
    public List<ProductoEntity> getProductos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos");
        return persistence.findAll();
    }
    
    /**
     * Obtiene la Producto.
     *
     * @param id del producto a buscar
     * @return Objetos de ProductoEntity.
     */
    public ProductoEntity getProducto(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los productos");
        return persistence.find(id);
    }  

    /**
     * Se encarga de crear un producto en la base de datos.
     *
     * @param entity Objeto de ProductoEntity con los datos nuevos
     * @return Objeto de ProductoEntity con los datos nuevos y su ID.
     */
    public ProductoEntity createProducto(ProductoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un producto ");
        return persistence.create(entity);
    }

    /**
     * Actualiza la información de una instancia de Producto.
     *
     * @param entity Instancia de ProductoEntity con los nuevos datos.
     * @return Instancia de ProductoEntity con los datos actualizados.
     */
    public ProductoEntity updateProducto(ProductoEntity entity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar producto con id={0}", entity.getId());
        ProductoEntity newEntity = persistence.update(entity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar producto con id={0}", entity.getId());
        return newEntity;
    }

    /**
     * Eliminar un producto por ID
     *
     * @param id El ID del producto a eliminar
     */
    public void deleteProducto(Long id) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar producto con id={0}", id);
        persistence.delete(id);
        LOGGER.log(Level.INFO, "Termina proceso de borrar producto con id={0}", id);
    }
    
    /**
     *  Busca una valoracion y la agrega a la lista de valoraciones
     * @param id
     * @param valoracion 
     */
    public void addValoracion(Long id, ValoracionEntity valoracion) throws BusinessLogicException
    {
        ProductoEntity producto = persistence.find(id);
        ValoracionEntity valoracionP = perValoracion.getValoracion(valoracion.getId());
        List<ValoracionEntity> valoraciones = producto.getValoraciones();
        valoraciones.add(valoracionP);
        producto.setValoraciones(valoraciones);
        calcularValoracionPromedio(id);
    }
    
    /**
     * Calcula la valoracionPromedio cada vez que se llame
     * @param id 
     */
    public void calcularValoracionPromedio(Long id)
    {
        ProductoEntity producto = persistence.find(id);
        List<ValoracionEntity> valoraciones = producto.getValoraciones();
        Double sum = 0.0;
        Double prom = 0.0;
        if(valoraciones.size() > 0)
        {
            for(ValoracionEntity valoracione : valoraciones) {
                sum += valoracione.getCalificacion();
            }
            prom = sum/valoraciones.size();
        }
        producto.setValoracionPromedio(prom);
    }
    /**
     *  Borra la valoracion que le entra por id de la lista de valoraciones del producto
     * @param id
     * @param idProducto 
     */
    public void deleteValoracion (Long id, Long idProducto) 
    {
        ProductoEntity producto = persistence.find(idProducto);
        List<ValoracionEntity> valoraciones = producto.getValoraciones();
        boolean sisa = false;
        for (ValoracionEntity valoracion : valoraciones) 
        {
            if(valoracion.getId().equals(id))
            {
                perValoracion.deleteValoracion(id);
                sisa = !sisa;
                calcularValoracionPromedio(id);
            }
            if(!sisa)
                break;
        }
    }
    
    /**
     * Agrega el servicio al producto
     * @param idServicio
     * @param idProducto 
     */
    public void agregarServicio (Long idServicio, Long idProducto)
    {
        ProductoEntity producto = persistence.find(idProducto);
        ServicioEntity servicio = perSer.getServicio(idServicio);
        producto.setServicio(servicio);
    }    
    
    
    public List<ProductoEntity> findProductosByProveedor(Long idProveedor)
    {
        return persistence.findByProveedor(idProveedor);
    }

    public List<ValoracionEntity> getValoraciones(ProductoEntity producto) 
    {
        return persistence.findValoracionesPorPrducto(producto.getId());
    }
    
}
