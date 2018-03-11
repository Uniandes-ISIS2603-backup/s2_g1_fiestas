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


insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (10001,0,'En Revision','PSE',100000);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (10002,1,'Confirmado','Tarjeta de Credito',250000);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor) values (10003,0,'Rechazado','Consignacion',350000);

insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados) values(10001,'Cumpleaños Luciana','08/03/2018','Cumpleaños Luciana','Luciana Ayala','Calle 44 45-86',25);
insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados) values(10002,'Bautizo Juan','08/04/2018','Bautizo en la Capilla San Nicolas','Juan Jose','Carrera 89 90-86',15);
insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados) values(10003,'Grado Maria','08/04/2018','Se gradua de derecho en la Javeriana','Maria Perez','Carrera 46 18-86',45);


insert into UsuarioEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña) values(10001, 'Armando Bronca Segura', 1020199191, 3144683924, 'a.bronca@uniandes.edu.co', 'cll.1 #1-1','a.bronca', 'desarrollo123');
insert into UsuarioEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña) values(10002, 'Carlos Andres Garte', 1020199192, 3144683925, 'ca.garte@uniandes.edu.co', 'cll.1 #1-1','ca.garte', '1234567');
insert into UsuarioEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña) values(10003, 'Maria Alejandra Niño', 1020199193, 3144683926, 'ma.niño@uniandes.edu.co', 'cll.1 #1-1','ma.niño', 'desarrollo123');

insert into BlogEntity(id, titulo, cuerpo, likes)values(10001, 'Muy buena fiesta','El servicio estuvo excelente, la pasé muy bien', 10);
insert into BlogEntity(id, titulo, cuerpo, likes)values(10002, 'Meh','No estuvo mala, pero no fue nada fuera de lo común.', 1);
insert into BlogEntity(id, titulo, cuerpo, likes)values(10003, 'Pésima','Fue un fiasco. La comida, la música, y la decoración.', 1);