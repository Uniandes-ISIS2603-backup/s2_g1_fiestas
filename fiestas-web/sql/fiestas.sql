delete from BlogEntity;
delete from ClienteEntity;
delete from ContratoEntity;
delete from EventoEntity;
delete from HorarioEntity;
delete from PagoEntity;
delete from ProductoEntity:
delete from ProveedorEntity;
delete from ServicioEntity;
delete from TematicaEntity;
Delete from ValoracionEntity;

insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados,id_pago) values(100,'08/03/2018','Cumpleaños Luciana','Luciana Ayala','Calle 44 45-86',25,100);
insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados,id_pago) values(200,'18/03/2018','Bautizo Juan','Juan Jose','Carrera 89 90-86',15,200);
insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados,id_pago) values(300,'28/04/2018','Grado de Maria','Maria Perez','Carrera 46 18-86',45,300);

insert into PagoEntity(id,realizado,estado,metodoDePago,id_evento) values (100,false,'En Proceso','PSE',100);
insert into PagoEntity(id,realizado,estado,metodoDePago,id_evento) values (200,true,'Finalizado','Tarjeta Credito',200);
insert into PagoEntity(id,realizado,estado,metodoDePago,id_evento) values (300,false,'Pago Fallado','PSE',300);
