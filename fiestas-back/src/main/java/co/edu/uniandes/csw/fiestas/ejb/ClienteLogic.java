package co.edu.uniandes.csw.fiestas.ejb;


import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.ClienteEntity;
import co.edu.uniandes.csw.fiestas.entities.EventoEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.ClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Cliente.
 *
 * @author nm.hernandez10
 */
@Stateless
public class ClienteLogic 
{
    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());

    @Inject
    private ClientePersistence persistence;
    
    @Inject
    private ProveedorLogic proveedorLogic;
    
    @Inject
    private BlogLogic blogLogic;
    
    @Inject
    private UsuarioLogic usuarioLogic;
    
    /**
     * Obtiene la lista de los registros de Cliente.
     *
     * @return Colección de objetos de ClienteEntity.
     */
    public List<ClienteEntity> getClientes() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los cliente");
        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Cliente a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de ClienteEntity con los datos del Cliente consultado.
     */
    public ClienteEntity getCliente(Long id) 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un cliente con id = {0}", id);
        return persistence.find(id);
    }
    
    /** Verifica si existe el login en la base de datos.
    * 
     * @param login
     * @return true si el login que se pasa por parámetro está en la base de datos.
    */
    public boolean loginRepetido(String login){
        return persistence.loginRepetido(login);
    }

    /**
     * Se encarga de crear un Cliente en la base de datos.
     *
     * @param entity Objeto de ClienteEntity con los datos nuevos
     * @return Objeto de ClienteEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException
     */
    public ClienteEntity createCliente(ClienteEntity entity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de crear un cliente");
        if(getCliente(entity.getId()) != null)
        {
            throw new BusinessLogicException("Ya existe un cliente con ese id");
        }
        if(entity.getNombre() == null || entity.getNombre().equals(""))
        {
            throw new BusinessLogicException("No puede crear un cliente sin nombre");
        }
        if(entity.getDocumento() == null || entity.getDocumento().equals(""))
        {
            throw new BusinessLogicException("No puede crear un cliente sin documento");
        }
        if(entity.getLogin() == null || entity.getLogin().equals(""))
        {
            throw new BusinessLogicException("No puede crear un cliente sin login");
        }
        if(persistence.loginRepetido(entity.getLogin()) || proveedorLogic.loginRepetido(entity.getLogin()))
        {
            throw new BusinessLogicException("Ya existe un usuario (cliente o proveedor) con ese mismo login");
        }
        if(entity.getContrasena() == null || entity.getContrasena().equals(""))
        {
            throw new BusinessLogicException("No puede crear un cliente sin contraseña");
        }
        usuarioLogic.createUsuario(crearUsuario(entity));
        return persistence.create(entity);
    }
    
    public UsuarioEntity crearUsuario(ClienteEntity entity)
    {
        UsuarioEntity nuevoUsuario = new UsuarioEntity();
        nuevoUsuario.setContrasena(entity.getContrasena());
        nuevoUsuario.setLogin(entity.getLogin());
        nuevoUsuario.setRol("Cliente");
        nuevoUsuario.setNombre(entity.getNombre());
        nuevoUsuario.setToken(entity.getId());
        return nuevoUsuario;
    }

    /**
     * Actualiza la información de una instancia de Cliente.
     *
     * @param entity Instancia de ClienteEntity con los nuevos datos.
     * @return Instancia de ClienteEntity con los datos actualizados.
     */
    public ClienteEntity updateCliente(ClienteEntity entity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar un cliente");
        if(getCliente(entity.getId()) == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para actualizar");
        }  
        String loginAnterior = getCliente(entity.getId()).getLogin();
        String loginNuevo = entity.getLogin();
        if(!loginAnterior.equals(loginNuevo))
        {
            throw new BusinessLogicException("No puede cambiarse el login del cliente");
        }
        if(entity.getNombre() == null || entity.getNombre().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un cliente sin nombre");
        }
        if(entity.getDocumento() == null || entity.getDocumento().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un cliente sin documento");
        }
        if(entity.getLogin() == null || entity.getLogin().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un cliente sin login");
        }
        if(entity.getContrasena() == null || entity.getContrasena().equals(""))
        {
            throw new BusinessLogicException("No puede actualizar a un cliente sin contraseña");
        }
        usuarioLogic.updateUsuario(usuarioLogic.getUsuario(entity.getLogin()));
        return persistence.update(entity);
    }

    /**
     * Elimina una instancia de Cliente de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     */
    public void deleteCliente(Long id) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar un cliente");
        if(getCliente(id) == null)
        {
            throw new BusinessLogicException("No existe un cliente con dicho id para eliminar");
        }
        usuarioLogic.deleteUsuario(getCliente(id).getLogin());
        persistence.delete(id);
    }

    /**
     * 
     * @param clientesId
     * @return 
     */
    public List<BlogEntity> getBlogs(Long clientesId) throws BusinessLogicException {
       ClienteEntity cliente = getCliente(clientesId);
       if(cliente == null)
           throw new BusinessLogicException("No existe un cliente con dicho id para enlistar blogs");
       return cliente.getBlogs();
    }

    public BlogEntity getBlogC(Long blogId, Long clienteId) throws BusinessLogicException {
       ClienteEntity cliente = getCliente(clienteId);
       BlogEntity blog = blogLogic.getBlog(blogId);
       if(cliente == null)
           throw new BusinessLogicException("No existe el cliente.");
       if(blog == null)
           throw new BusinessLogicException("No existe el cliente.");
       if(blog.getCliente().getId()!=cliente.getId())
           throw new BusinessLogicException("El blog a buscar existe, pero el cliente no corresponde");
       return blog;
    }
    
    
    public BlogEntity getBlog(Long blogId) throws BusinessLogicException {
       BlogEntity blog = blogLogic.getBlog(blogId);
       if(blog==null)
           throw new BusinessLogicException("El blog a buscar no existe.");
       return blog;
    }

    public BlogEntity addBlog(BlogEntity blog, Long clientesId) throws BusinessLogicException {
        ClienteEntity cliente = getCliente(clientesId);
         if(blog== null)
            throw new BusinessLogicException("El blog es vacío.");
        BlogEntity blogT = blogLogic.getBlog(blog.getId());
        if(blogT!=null)
            throw new BusinessLogicException("El blog ya existe.");
        blog.setCliente(cliente);
        blogLogic.createBlog(blog);
        cliente.getBlogs().add(blog);
        updateCliente(cliente);
        return blog;
    }

    public List<BlogEntity> replaceBlogs(Long clientesId, List<BlogEntity> blogs) throws BusinessLogicException {
        ClienteEntity cliente = getCliente(clientesId);
        
        for (BlogEntity blog : blogs) {
            if(blogLogic.getBlog(blog.getId())!=null)
                throw new BusinessLogicException("Se quiere añadir blog que ya existe");
            blog.setCliente(cliente);
            blogLogic.createBlog(blog);
        }
        cliente.setBlogs(blogs);
        updateCliente(cliente);
        return blogs;
    }

    public void removeBlog(Long blogsId, Long clientesId) throws BusinessLogicException {
        ClienteEntity cliente = getCliente(clientesId);
        BlogEntity blog = blogLogic.getBlog(blogsId);
        if(blog==null)
            throw new BusinessLogicException("No se encuentra el blog especificado.");
        if(blog.getCliente().getId()!=cliente.getId())
            throw new BusinessLogicException("No puede eliminarse un blog de otro usuario.");
        cliente.getBlogs().remove(blog);
        updateCliente(cliente);
        blogLogic.deleteBlog(blogsId);
    }

    
}
