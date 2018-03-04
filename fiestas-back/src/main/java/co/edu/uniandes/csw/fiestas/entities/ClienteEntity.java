/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.eclipse.persistence.jpa.config.Cascade;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author nm.hernandez10
 */
@Entity
public class ClienteEntity extends UsuarioEntity
{
    //Está vacío porque sólo tiene la relación de eventos extra (comparado con UsuarioEntity) y aún no debe hacerse las relaciones.
    
    @PodamExclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventoEntity> eventos = new ArrayList<EventoEntity>();

    /**
     * @return eventos
     */
    public List<EventoEntity> getEventos() 
    {
        return eventos;
    }

    /**
     * @param eventos los nuevos eventos
     */
    public void setEventos(List<EventoEntity> eventos) {
        this.eventos = eventos;
    }
    
}
