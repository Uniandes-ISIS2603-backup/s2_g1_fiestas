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


insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (100,0,'En Revision','PSE',100000);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (200,1,'Confirmado','Tarjeta de Credito',250000);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (300,0,'Rechazado','Consignacion',350000);

insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados) values(100,'Cumpleaños Luciana','08/03/2018','Cumpleaños Luciana','Luciana Ayala','Calle 44 45-86',25);
insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados) values(200,'Bautizo Juan','08/04/2018','Bautizo en la Capilla San Nicolas','Juan Jose','Carrera 89 90-86',15);
insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados) values(300,'Grado Maria','08/04/2018','Se gradua de derecho en la Javeriana','Maria Perez','Carrera 46 18-86',45);


