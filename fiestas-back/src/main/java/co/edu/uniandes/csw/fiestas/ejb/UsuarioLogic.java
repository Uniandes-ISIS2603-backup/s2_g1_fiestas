/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.ejb;

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
}
