delete from BlogEntity;
delete from ContratoEntity;
delete from EventoEntity;
delete from HorarioEntity;
delete from PagoEntity;
delete from ProductoEntity;
delete from ServicioEntity;
delete from TematicaEntity;
delete from ValoracionEntity;
delete from ProveedorEntity;
delete from ClienteEntity;

insert into ProveedorEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena, penalizado) values(10011, 'Hamburguesería Mc Donalds','https://pbs.twimg.com/profile_images/646210794535956481/UXp3jGpm_400x400.png' , '1012345678', 3212121212, 'mcdonalds@mcdonalds.com', 'cll.2 #20-32','mcdonalds','hola123', 0);
insert into ProveedorEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena, penalizado) values(10012, 'Iluminación de Diana','http://i2.wp.com/recursosaempresas.com/wp-content/uploads/2015/11/salon-fiestas.jpg?resize=600%2C270' ,'1012345679', 3212121213, 'dianagalan@yahoo.es', 'cll.2 #20-33','dianailu','hola1234', 0);
insert into ProveedorEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena, penalizado) values(10013, 'Decoración Pepitos','http://www.glits.mx/ckfinder/userfiles/images/Atmosphere%20Gala%204.jpg' ,'1012345670', 3212121214, 'pepitosdec@hotmail.com', 'cll.2 #20-34','decpep10','hola', 1);

insert into ClienteEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena) values(10011, 'Nicolás Hernández','https://www.psicoactiva.com/blog/wp-content/uploads/2017/07/hombre-soltero-feliz.jpg', '1012345681', 3112121212, 'nm.hernandez10@uniandes.edu.co', 'cll.2 #25-25','nm.hernandez10', 'chao123');
insert into ClienteEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena) values(10012, 'Sofía Arias','https://static.iris.net.co/semana/upload/images/2015/10/23/447377_20029_1.jpg' ,'1012345682', 3112121213, 's.arias@uniandes.edu.co', 'cll.94 # 7-12','df.galan', 'chao1234');
insert into ClienteEntity(id, nombre, imagen, documento, telefono, correo, direccion, login, contrasena) values(10013, 'Cristian Amaya','https://files.merca20.com/uploads/2013/08/shutterstock_130105307.jpg' ,'1012345683', 3112121214, 'cm.amaya11@uniandes.edu.co', 'cll.95 #10-1','cm.amaya11@uniandes.edu.co', 'chao12345');

insert into BonoEntity(id, aplicadesde, codigo, descuento, expira, motivo, proveedor_id)values(10001, '04/22/2018','jihrfu9i3', 15, '05/22/2018', 'Día de san valentin', 10011);
insert into BonoEntity(id, aplicadesde, codigo, descuento, expira, motivo, proveedor_id)values(10002, '04/23/2018','u934hq', 30, '05/22/2018', 'Día de la madre', 10012);
insert into BonoEntity(id, aplicadesde, codigo, descuento, expira, motivo, proveedor_id)values(10003, '04/24/2018','54jkbsdv', 50, '05/22/2018', 'Navidad', 10013);

insert into ServicioEntity(id,descripcion,tipo) values(10019,'Carrito de perros calientes','comida');
insert into ServicioEntity(id,descripcion,tipo) values(10020,'Show de magia para niños','entretenimiento');
insert into ServicioEntity(id,descripcion,tipo) values(10021,'Stripers', 'despedida de soltero');

insert into ValoracionEntity(id,calificacion,comentario) values(10022,5,'Excelente servicio');
insert into ValoracionEntity(id,calificacion,comentario) values(10023,4,'Me encanto');
insert into ValoracionEntity(id,calificacion,comentario) values(10024,2, 'Pesimo servicio');

insert into ProductoEntity(id,nombre,precio,descripcion,incluye,personal) values(10016,'Comida para boda',80000, 'Cena para 30 personas, con entrada, plato fuerte y postre','Incluye meseros',4);
insert into ProductoEntity(id,nombre,precio,descripcion,incluye,personal) values(10017,'Audio para boda',300000, 'DJ para boda','Incluye lo que necesite el dj, más parlantes incluye varios estilos de musica',6);
insert into ProductoEntity(id,nombre,precio,descripcion,incluye,personal) values(10018,'Recreaciones para boda',200000, 'Recreadores para mantener activa a la gente','Incluye lo que necesiten los recreadores',2);

insert into TematicaEntity(id, descripcion) values(10014,'Bodas');
insert into TematicaEntity(id, descripcion) values(10015,'Fiestas Infantiles');
insert into TematicaEntity(id, descripcion) values(10016,'Bautizo');
insert into TematicaEntity(id, descripcion) values(10017,'Grado');

insert into HorarioEntity(id, fecha, horafin, horainicio) values(10001,'08/03/2018','22:00:00', '13:00:00');
insert into HorarioEntity(id, fecha, horafin, horainicio) values(10002,'08/03/2019','22:00:00', '13:00:00');
insert into HorarioEntity(id, fecha, horafin, horainicio) values(10003,'08/03/2028','22:00:00', '13:00:00');

insert into ContratoEntity(id, estado, tyc, valor, horario_id) values(10001, 'Por pagar', 'Sin términos ni condiciones', 2000000000, 10001);
insert into ContratoEntity(id, estado, tyc, valor, horario_id) values(10002, 'Pagada la primera cuota', 'Si el cliente cancela despues de 2 semanas de haber contratado el servicio deberá pagar el 100% del costo', 345000000, 10002);
insert into ContratoEntity(id, estado, tyc, valor, horario_id) values(10003, 'Pagado', 'Sin términos ni condiciones', 20000000, 10003);

insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados,cliente_id,tematica_id) values(10001,'Cumpleaños Luciana','08/03/2018','Cumpleaños Luciana','Luciana Ayala','Calle 44 45-86',25,10011,10015);
insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados,cliente_id,tematica_id) values(10002,'Bautizo Juan','08/04/2018','Bautizo en la Capilla San Nicolas','Juan Jose','Carrera 89 90-86',15,10012,10016);
insert into EventoEntity(id,nombre,fecha,descripcion,celebrado,lugar,invitados,cliente_id,tematica_id) values(10003,'Grado Maria','08/04/2018','Se gradua de derecho en la Javeriana','Maria Perez','Carrera 46 18-86',45,10013,10017);

insert into PagoEntity(id,realizado,estado,metodoDePago,valor,evento_id) values (10001,0,'Rechazado','PSE',100000,10001);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor,evento_id) values (10002,1,'Confirmado','Tarjeta de Credito',250000,10001);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor,evento_id) values (10003,0,'Rechazado','Consignacion',350000,10002);
insert into PagoEntity(id,realizado,estado,metodoDePago,valor,evento_id) values (10004,1,'Confirmado','Consignacion',350000,10002);

insert into BlogEntity(id, titulo, cuerpo, likes, cliente_id, evento_id)values(10001, 'Muy buena fiesta','El servicio estuvo excelente, la pasé muy bien', 10, 10011,10001);
insert into BlogEntity(id, titulo, cuerpo, likes)values(10002, 'Meh','No estuvo mala, pero no fue nada fuera de lo común.', 1);
insert into BlogEntity(id, titulo, cuerpo, likes)values(10003, 'Pésima','Fue un fiasco. La comida, la música, y la decoración.', 1);
