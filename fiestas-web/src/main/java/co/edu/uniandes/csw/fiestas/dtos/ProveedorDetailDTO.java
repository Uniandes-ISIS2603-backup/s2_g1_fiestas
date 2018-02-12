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
public class ProveedorDetailDTO extends ProveedorDTO
{
    private ArrayList<HorarioDTO> horarios;
    
    public ProveedorDetailDTO()
    {
        
    }
    
    public ArrayList<HorarioDTO> getHorarios()
    {
        return horarios;
    }
    
    public void setBlogs(ArrayList<HorarioDTO> horarios)
    {
        this.horarios = horarios;
    }
}
