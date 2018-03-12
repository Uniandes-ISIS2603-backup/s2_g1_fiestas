delete from BlogEntity;
delete from ContratoEntity;
delete from EventoEntity;
delete from HorarioEntity;
delete from PagoEntity;
delete from ProductoEntity;
delete from UsuarioEntity;
delete from ServicioEntity;
delete from TematicaEntity;
delete from ValoracionEntity;
delete from ProveedorEntity;
delete from ClienteEntity;


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

insert into ProveedorEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña, penalizado) values(10011, 'Jesús Pradilla', 1012345678, 3212121212, 'j.pradilla@hotmail.com', 'cll.2 #20-32','j.pradilla','hola123', false);
insert into ProveedorEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña, penalizado) values(10012, 'Diana Fernández Galán', 1012345679, 3212121213, 'dianagalan@yahoo.es', 'cll.2 #20-33','df.galan','hola1234', false);
insert into ProveedorEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña, penalizado) values(10013, 'Andrés Felip Segura Hernández', 1012345670, 3212121214, 'andresillo@hotmail.com', 'cll.2 #20-34','af.segura10','hola', true);

insert into ClienteEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña) values(10011, 'Nicolás Hernández', 1012345681, 3112121212, 'nm.hernandez10@uniandes.edu.co', 'cll.2 #25-25','nm.hernandez10', 'chao123');
insert into ClienteEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña) values(10012, 'Sofía Arias', 1012345682, 3112121213, 's.arias@uniandes.edu.co', 'cll.94 # 7-12','df.galan', 'chao1234');
insert into ClienteEntity(id, nombre, documento, telefono, correo, direccion, login, contraseña) values(10013, 'Cristian Amaya', 1012345683, 3112121214, 'cm.amaya11@uniandes.edu.co', 'cll.95 #10-1','cm.amaya11@uniandes.edu.co', 'chao12345');