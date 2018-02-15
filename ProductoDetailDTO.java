/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.fiestas.dtos;

/**
 *
 * @author af.losada
 */
public class ProductoDetailDTO extends ProductoDTO
{

ContratoDTO contrato;
ServicioDTO servicio;
    
/*
Getters
*/
ContratoDTO darContrato()
{
return contrato;
}

ServicioDTO darServicio()
{
return servicio;
}

/*
Setters
*/
void setContrato(ContratoDTO pCont)
{
    contrato = pCont;
}

void setServicio(ServicioDTO pServ)
{
    servicio = pServ;
}
    
}


