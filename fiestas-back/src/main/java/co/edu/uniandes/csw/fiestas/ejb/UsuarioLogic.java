/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

import co.edu.uniandes.csw.fiestas.entities.BlogEntity;
import co.edu.uniandes.csw.fiestas.entities.UsuarioEntity;
import co.edu.uniandes.csw.fiestas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.fiestas.persistence.UsuarioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexion con la persistencia para la entidad de
 * Usuario.
 *
 * @author df.nino10
 */
@Stateless 
public class UsuarioLogic {
    private static final Logger LOGGER = Logger.getLogger(UsuarioLogic.class.getName());
    
    @Inject
    private UsuarioPersistence persistence;
    
    @Inject
    private BlogLogic bLogic;

    public List<UsuarioEntity> getUsuarios(){
        LOGGER.info("Inicia proceso de obtener todos los usuarios del sistema.");
        List<UsuarioEntity> usuarios=persistence.findAll();
        LOGGER.info("Termina proceso de obtener todos los usuarios del sistema.");
        return usuarios;
    }
    
    public UsuarioEntity getUsuario(Long id){
        LOGGER.log(Level.INFO,"Inicia proceso de obtener el usuario con el id dado por parámetro.", id);
        UsuarioEntity usuario=persistence.find(id);
        
        LOGGER.log(Level.INFO,"Termina proceso de obtener el usuario con el id dado por parámetro.", id);
        return usuario; 
    }

    public UsuarioEntity createUsuario(UsuarioEntity usuario){
        
        LOGGER.log(Level.INFO,"Inicia el proceso de crear el usuario");
        return persistence.create(usuario);
    }
    
    public UsuarioEntity updateUsuario(UsuarioEntity usuario)throws BusinessLogicException{
        LOGGER.log(Level.INFO,"Inicia el proceso de actualizar el usuario");
        UsuarioEntity u = persistence.find(usuario.getId());
        if(u==null)
            throw new BusinessLogicException("El usuario que quiere actualizarse no existe.");
        
        persistence.update(usuario);
        LOGGER.log(Level.INFO,"Termina el proceso de actualizar el usuario");
        return usuario;    
    }
    
    public void deleteUsuario(Long id)throws BusinessLogicException{
        LOGGER.log(Level.INFO,"Inicia el proceso de borrar el usuario con el id={0}", id);
        UsuarioEntity u = persistence.find(id);
        if(u==null)
            throw new BusinessLogicException("El usuario que se quiere borrar no existe.");
        persistence.delete(id);
        LOGGER.log(Level.INFO,"Termina el proceso de borrar el usuario con el id={0}", id);
    }

    public List<BlogEntity> getBlogs(Long usuarioId) throws BusinessLogicException {
        UsuarioEntity usuario = getUsuario(usuarioId);
        if(usuario==null)
            throw new BusinessLogicException("El usuario no se encuentra.");
        
        List<BlogEntity> list = usuario.getBlogs();
        List<BlogEntity> list1=bLogic.getBlogs();
        
        for (BlogEntity blogEntity : list) {
            if(list1.contains(blogEntity)){/** Se deja vacío porque nececita revisar toda la lista y luego sí retornar*/}
            else throw new BusinessLogicException("Los blogs en la base de datos y en la lista del usuario no son consistentes.");   
        }
        return list;
    }
    
    
    public BlogEntity getBlog(UsuarioEntity ent, Long blogId) throws BusinessLogicException {
        BlogEntity bE = bLogic.getBlog(blogId);
        if(bE == null)
            throw new BusinessLogicException("El blog a buscar no existe.");
        int index= ent.getBlogs().indexOf(bE);
        if(index<0 && !ent.getBlogs().get(index).equals(bE))
        {
            throw new BusinessLogicException("El blog en el usuario y en la base de datos no es consistente.");
        }
        return bE;
    }

    /**
     * Remplazar blogs de un usuario
     *
     * @param blogs Lista de blogs que serán los del usuario.
     * @param usuarioId El id del usuario que se quiere actualizar.
     * @return La lista de blogs actualizada.
     * @throws BusinessLogicException  - Error de lógica
     */
    public List<BlogEntity> replaceBlogs(Long usuarioId, List<BlogEntity> blogs) throws BusinessLogicException 
    {
        UsuarioEntity usuario = getUsuario(usuarioId);
        if(usuario == null)
        {
            throw new BusinessLogicException("El usuario al que se le quiere reemplazar blogs es nulo");
        }
              
        else if(blogs == null)
        {
            throw new BusinessLogicException("No hay lista nueva");
        }
        
        else if(blogs.isEmpty())
        {
            throw new BusinessLogicException("La lista está vacía");
        }
        else
        {
            usuario.setBlogs(blogs);
        }
        return blogs;
    }

    /**
     * Agrega un blog a un usuario
     * @param blogId Id del blog a asociar
     * @param usuarioId id del usuario a agregar el blog
     * @return Entidad del blog agregado
     * @throws BusinessLogicException  - Error de lógica si no existe el usuario
     */
    public BlogEntity addBlog(Long blogId, Long usuarioId) throws BusinessLogicException {
        UsuarioEntity usuarioEntity = getUsuario(usuarioId);
        BlogEntity blogEntity = bLogic.getBlog(blogId);
        if(usuarioEntity != null && blogEntity != null)
        {
            usuarioEntity.agregarBlog(blogEntity);
            updateUsuario(usuarioEntity);
        }
        else
        {
            throw new BusinessLogicException("El usuario al que se le quiere agregar blog es nulo");
        }        
        return blogEntity;
    }

    /**
     * Borrar un blog de un usuario
     *
     * @param blogId El blog que se desea borrar del usuario.
     * @param usuarioId El usuario de la cual se desea eliminar.
     * @throws BusinessLogicException  - Error de lógica si no existe el usuario
     */
    public void removeBlog(Long blogId, Long usuarioId) throws BusinessLogicException 
    {
        UsuarioEntity usuarioEntity = getUsuario(usuarioId);
        BlogEntity blog = bLogic.getBlog(blogId);
        if(usuarioEntity != null)
        {
            usuarioEntity.removerBlog(blog);
        }
        else
        {
            throw new BusinessLogicException("El usuario al que se le quiere remover el blog es nulo");
        }        
        
    }

    
}
