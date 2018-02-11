/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

import java.util.ArrayList;

/**
 *
 * @author nm.hernandez10
 */
public class UsuarioDetailDTO extends UsuarioDTO
{
    private ArrayList<BlogDTO> blogs;
    
    public UsuarioDetailDTO()
    {
        
    }
    
    public ArrayList<BlogDTO> getBlogs()
    {
        return blogs;
    }
    
    public void setBlogs(ArrayList<BlogDTO> blogs)
    {
        this.blogs = blogs;
    }
}
