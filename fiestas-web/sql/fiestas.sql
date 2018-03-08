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


insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (100,FALSE,100,100,100000);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (200,TRUE,300,200,250000);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (300,FALSE,200,300,350000);