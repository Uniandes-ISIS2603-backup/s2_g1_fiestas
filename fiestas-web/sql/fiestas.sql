delete from BlogEntity;
delete from ContratoEntity;
delete from EventoEntity;
delete from HorarioEntity;
delete from PagoEntity;
delete from ProductoEntity;
delete from UsuarioEntity;
delete from ServicioEntity;
delete from TematicaEntity;
Delete from ValoracionEntity;


insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (100,0,100,100,100000);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (200,1,300,200,250000);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (300,0,200,300,350000);

insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados,pago_id) values(100,'08/03/2018','Cumpleaños Luciana','Luciana Ayala','Calle 44 45-86',25,100);
insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados,pago_id) values(200,'08/04/2018','Bautizo Juan','Juan Jose','Carrera 89 90-86',15,200);
insert into EventoEntity(id,fecha,descripcion,celebrado,lugar,invitados,pago_id) values(300,'08/04/2018','Grado de Maria','Maria Perez','Carrera 46 18-86',45,300);


