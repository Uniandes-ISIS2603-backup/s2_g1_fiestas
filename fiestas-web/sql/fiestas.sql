delete from BlogEntity;
delete from ClienteEntity;
delete from ContratoEntity;
delete from EventoEntity;
delete from HorarioEntity;
delete from PagoEntity;
delete from ProductoEntity;
delete from ProveedorEntity;
delete from ServicioEntity;
delete from TematicaEntity;
Delete from ValoracionEntity;

insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados) values(100,'08/03/2018','Cumpleaños Luciana','Luciana Ayala','Calle 44 45-86',25);
insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados) values(200,'08/04/2018','Bautizo Juan','Juan Jose','Carrera 89 90-86',15);
insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados) values(300,'08/04/2018','Grado de Maria','Maria Perez','Carrera 46 18-86',45);

insert into PagoEntity(id,realizado,estado,metodoDePago,evento_id) values (100,false,'En Proceso','PSE',100);
insert into PagoEntity(id,realizado,estado,metodoDePago,evento_id) values (200,true,'Finalizado','Tarjeta Credito',200);
insert into PagoEntity(id,realizado,estado,metodoDePago,evento_id) values (300,false,'Pago Fallado','PSE',300);
